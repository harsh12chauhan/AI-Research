package com.harsh.research.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing  // inherited the jpa mapping, no seperate table for base-entity
public class JpaAuditingConfig {

}
