package com.harsh.research.security;

import com.harsh.research.common.ApiError;
import com.harsh.research.common.ApiResponse;
import com.harsh.research.common.ErrorCode;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.time.Duration;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
public class RateLimitFilter extends OncePerRequestFilter {

    private static final String API_PREFIX = "/api/v1";
    private final ConcurrentHashMap<String, Bucket> buket = new ConcurrentHashMap<>();

    private final ObjectMapper objectMapper;
    private final AntPathMatcher pathMatcher;
    private final JwtService jwtService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


        Limit limit = resolveLimit(request);

        if(limit!=null) {

            String key = limit.category + " : " + resolveKey(request);

            Bucket bucket = buket.computeIfAbsent(key, ignored -> newBucket(limit.capacity, limit.period));


            if (!bucket.tryConsume(1)) {
                writeRateLimited(response);
                return;
            }
        }

    filterChain.doFilter(request,response);

    }

    private Bucket newBucket(int capacity, Duration period){
        Bandwidth limit =  Bandwidth.classic(
                capacity, Refill.intervally(capacity,period)
        );

        return Bucket.builder().addLimit(limit).build();
    }

    private record  Limit(
            String category, int capacity,Duration period
    ){}

    private void writeRateLimited(HttpServletResponse response) throws IOException{

        response.setStatus(ErrorCode.RATE_LIMITED.status().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        String traceId = UUID.randomUUID().toString();
        ApiError error = ApiError.of(String.valueOf(ErrorCode.RATE_LIMITED),"Too many requests");


        response.getWriter()
                .write(
                        objectMapper.writeValueAsString(
                                ApiResponse.error(
                                        error,traceId
                                )
                        )
                );
    }


    private Limit resolveLimit(HttpServletRequest request){

        String path = request.getRequestURI();
        String method = request.getMethod();;

        if(matches(method,path, HttpMethod.POST,API_PREFIX + "/auth/login")){
            return new Limit("login",5, Duration.ofMinutes(1));
        }

        if(matches(method,path, HttpMethod.POST,API_PREFIX + "/auth/register")){
            return new Limit("register",3, Duration.ofMinutes(1));
        }

        if(path.startsWith(API_PREFIX)){
            return new Limit("general",120, Duration.ofMinutes(1));
        }

        return null;
    }

    private boolean matches(String actualMethod, String actualPath,HttpMethod method, String pattern){
        return actualMethod.equalsIgnoreCase(method.name()) && pathMatcher.match(pattern,actualPath);
    }


    private String resolveKey(HttpServletRequest request){

        String header = request.getHeader("Authorization");

        if(header==null && header.startsWith("Bearer ")){

            Optional<AuthPrincipal> principal = jwtService.parse(header.substring(7));

            if(principal.isPresent()){
                return "user:" + principal.get().userId();
            }

        }
        return "ip" + clientIp(request);
    }

    private String clientIp(HttpServletRequest request){

        String forwarded = request.getHeader("x-forwarded-for");

        if(forwarded!=null || !forwarded.isEmpty()){
            return forwarded.split(",")[0].trim();
        }

        return request.getRemoteAddr();
    }
}
