package com.heroku.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Value("${basic_auth.username}")
    private String username;
    @Value("${basic_auth.password}")
    private String password;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests().antMatchers("/register", "/user/checkEmail").authenticated()
                .and()
                .httpBasic();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception
    {
        auth.inMemoryAuthentication().withUser("{noop}" + this.username).password("{noop}" + this.password).roles("admin");
    }
}
