package com.csye6220.eduverse.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    private final CustomAuthenticationSuccessHandler authenticationSuccessHandler;

    private final CustomAuthenticationFailureHandler authenticationFailureHandler;

    @Autowired
    public AppSecurityConfig(CustomUserDetailsService userDetailsService, CustomAuthenticationSuccessHandler authenticationSuccessHandler, CustomAuthenticationFailureHandler authenticationFailureHandler) {
        this.userDetailsService = userDetailsService;
        this.authenticationSuccessHandler = authenticationSuccessHandler;
        this.authenticationFailureHandler = authenticationFailureHandler;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(registry -> {
                        registry.requestMatchers("/home", "/register/**", "/css/**", "/js/**", "/images/**").permitAll();
                        registry.requestMatchers("/","/courses", "/profile","/courses/*/announcements", "/courses/*/assignments", "/courses/*/files", "/courses/*/people", "/courses/*/grades").hasAnyRole("INSTRUCTOR", "STUDENT");
                        registry.requestMatchers("/courses/*/announcements/*/start-assignment").hasRole("STUDENT");
                        registry.requestMatchers("/courses/create","/courses/*/announcements/create", "/courses/*/assignments/create", "/courses/*/files/upload", "/courses/*/announcements/*/edit", "/courses/*/announcements/*/delete", "/courses/*/assignments/*/edit", "/courses/*/assignments/*/delete", "/courses/*/assignments/*/submissions", "/courses/*/grade/*").hasRole("INSTRUCTOR");
                        registry.anyRequest().authenticated();
                })
                .formLogin(httpSecurityFormLoginConfigurer -> {
                    httpSecurityFormLoginConfigurer
                            .loginPage("/login")
                            .successHandler(authenticationSuccessHandler)
                            .failureHandler(authenticationFailureHandler)
                            .failureUrl("/login?error=true")
                            .permitAll();
                })
                .logout(logout -> logout.logoutUrl("/logout")
                        .permitAll())
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return userDetailsService;
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }
}
