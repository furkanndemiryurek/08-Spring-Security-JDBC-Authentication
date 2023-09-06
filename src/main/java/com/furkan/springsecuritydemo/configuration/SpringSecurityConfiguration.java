package com.furkan.springsecuritydemo.configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;
import java.net.http.HttpClient;

@Configuration
public class SpringSecurityConfiguration {

    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource){
        return new JdbcUserDetailsManager(dataSource);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.authorizeHttpRequests( configurer ->
                        configurer
                                .requestMatchers("/").hasAnyRole("EMPLOYEE")
                                .requestMatchers("/leaders/**").hasAnyRole("MANAGER")
                                .requestMatchers("/systems/**").hasAnyRole("ADMIN")
                                .anyRequest().authenticated())
                .exceptionHandling(configurer ->
                        configurer.accessDeniedPage("/access-denied"))
                .formLogin(form -> form
                        .loginPage("/showMyLoginPage")
                        .loginProcessingUrl("/authenticateTheUser")
                        .permitAll()
                ).logout(logout -> logout.permitAll());
        return http.build();
    }



}
