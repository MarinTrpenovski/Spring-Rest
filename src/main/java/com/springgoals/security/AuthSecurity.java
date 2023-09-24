package com.springgoals.security;

import com.springgoals.filter.AuthenFilter;
import com.springgoals.filter.CustomAccessDeniedHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;


@EnableWebSecurity
@EnableGlobalMethodSecurity
public class AuthSecurity extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/", "/home","/auth").permitAll()
                .antMatchers("/api/user").hasAuthority("Admin")
                .anyRequest().authenticated()
                .antMatchers("/api").hasAnyAuthority("Admin", "User")
                //.antMatchers("/api").hasAnyAuthority("View", "Edit")
                .anyRequest().authenticated()
                .and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler()) ;

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
    @Bean
    public CustomAccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }
}
