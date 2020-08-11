package com.stackroute.productservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * This class is to provide the security configuration for the web application
 * which will be used by Admins to manage products
 * <p>
 * **TODO**
 * Add appropriate annotation to this class for creation of Beans
 * Implement interface WebSecurityConfigurerAdapter
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    /**
     * TODO**
     * Inject UserDetailsService
     */
    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * TODO**
     * Override method configure(AuthenticationManagerBuilder auth) and
     * configure the userDetailsService and passwordEncoder
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                // enable in memory based authentication with a user named
                // "user" and "admin"
                .inMemoryAuthentication().withUser("user").password("password").roles("USER").and()
                .withUser("admin").password("password").roles("USER", "ADMIN");
    }

    @Bean
    @Override
    public UserDetailsService userDetailsServiceBean() throws Exception {
        return super.userDetailsServiceBean();
    }
    /**
     * TODO**
     * Create a PasswordEncoder bean for encrypting using BCrypt
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * TODO**
     * Override method configure(HttpSecurity httpSecurity) to achieve the following
     * Any requests to the Rest endpoints of product service should be allowed without authentication
     * Any request for using web application by Admin should be authenticated
     * Use Spring's Default login for Form login
     * login processing url should be /admin/processlogin
     * After successful login, the page should be redirected to default url /admin
     * logout should be allowed for all.
     * logout url should be configured as /admin/logout
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers( "/admin").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/admin/processlogin")
                .loginProcessingUrl("/admin")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/admin/logout")
                .permitAll();
    }

    /**
     * TODO**
     * Override method configure(WebSecurity web) to ignore
     * the paths of used by Swagger api like /swagger-resources/**, etc
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/swagger-resources/**");
    }

}
