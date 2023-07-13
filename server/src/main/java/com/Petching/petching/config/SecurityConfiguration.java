package com.Petching.petching.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity(debug=false)
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return
                http.headers().frameOptions().sameOrigin()
                        .and()
                        .csrf().disable()
                        .cors(withDefaults())
                        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                        .and()
                        .formLogin().disable()
                        .httpBasic().disable()
                        .authorizeHttpRequests(authorize->authorize
                                .anyRequest().permitAll()
                        ).build();
    }


    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.addAllowedOriginPattern("http://localhost:3000"); // FE 개발 시 allowed
        configuration.addAllowedOriginPattern("http://localhost:8080");
        configuration.addAllowedOriginPattern("https://www.petching.net");
        configuration.addAllowedOriginPattern("https://petching.net");
        configuration.addAllowedOriginPattern("https://server.petching.net");
        configuration.addAllowedHeader("*");
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.addExposedHeader("Authorization");
        configuration.addExposedHeader("Refresh");
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
