package com.harsh.research.security;

import org.jspecify.annotations.Nullable;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.UUID;

@Component
public class CurrentUserArgumentResolver implements HandlerMethodArgumentResolver {


    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(CurrentUser.class)
                && (parameter.getParameter().equals(AuthPrincipal.class))
                || parameter.getParameterType().equals(UUID.class);
    }

    @Override
    public @Nullable Object resolveArgument(MethodParameter parameter, @Nullable ModelAndViewContainer mavContainer, NativeWebRequest webRequest, @Nullable WebDataBinderFactory binderFactory) throws Exception {

        Authentication auth  = SecurityContextHolder.getContext().getAuthentication();

        if(auth == null || (!(auth.getPrincipal() instanceof AuthPrincipal principal))){
            throw new Exception("not authenticated");
        }

        if(parameter.getParameterType().equals(UUID.class)){
            return principal.userId();
        }

        return principal;
    }
}
