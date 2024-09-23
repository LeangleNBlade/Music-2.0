package com.boot.music.config;

import com.boot.music.handler.AuthenticationSuccessHandlerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Configuration
public class WebSecurityConfig {

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Bean
    SecurityFilterChain configure(HttpSecurity http) throws Exception {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));
        corsConfiguration.setAllowedOriginPatterns(List.of("*"));
        corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PUT", "OPTIONS", "PATCH", "DELETE"));
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setExposedHeaders(List.of("Authorization"));

        http.cors()
                .configurationSource(request -> corsConfiguration)
                .and()
                .csrf()
                .disable()
                .authenticationProvider(authenticationProvider);

        http.authorizeHttpRequests(auth -> {
                        auth
                                .requestMatchers("/admin/**")
                                .hasAuthority("ROLE_ADMIN")
                                .requestMatchers("/song/**")
                                .hasAnyAuthority("ROLE_ADMIN", "ROLE_USER")
                                .requestMatchers("/test/**")
                                .hasAnyAuthority("ROLE_ADMIN", "ROLE_USER")
                                .anyRequest().permitAll();
//                                .anyRequest().authenticated();
                        }
                )
                .formLogin(login ->
                        login
                                .loginPage("/home")
                                .loginProcessingUrl("/login")
//                                .defaultSuccessUrl("/home", true)
                                .successHandler(new AuthenticationSuccessHandlerImpl())
                                .permitAll()
                )
                .logout(logout -> logout.logoutSuccessUrl("/home").permitAll()
                );

        return http.build();
    }
}
