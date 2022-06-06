package com.aoede.commons.base.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.aoede.commons.base.component.BaseComponent;

@Configuration
@EnableTransactionManagement
@EnableJpaAuditing
public class JpaEntityConfiguration extends BaseComponent {

}



