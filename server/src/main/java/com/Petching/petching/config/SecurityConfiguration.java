package com.Petching.petching.config;

import com.Petching.petching.login.handler.UserAccessDeniedHandler;
import com.Petching.petching.login.handler.UserAuthenticationEntryPoint;
import com.Petching.petching.login.handler.UserAuthenticationFailureHandler;
import com.Petching.petching.login.handler.UserAuthenticationSuccessHandler;
import com.Petching.petching.login.jwt.filter.JwtAuthenticationProcessingFilter;
import com.Petching.petching.login.jwt.filter.JwtVerificationFilter;
import com.Petching.petching.login.jwt.service.JwtService;
import com.Petching.petching.login.jwt.util.CustomAuthorityUtils;
import com.Petching.petching.login.oauth.OAuth2UserSuccessHandler;
import com.Petching.petching.login.service.UserLoginService;
import com.Petching.petching.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration @RequiredArgsConstructor
@EnableWebSecurity(debug=false)
public class SecurityConfiguration {
    private final JwtService jwtService;
    private final CustomAuthorityUtils authorityUtils;
    private final OAuth2UserSuccessHandler oAuth2UserSuccessHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return
                http.headers().frameOptions().sameOrigin()
                        .and()
                        .csrf().disable()
                        .cors(withDefaults())
                        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                        .and()
                        .exceptionHandling()
                        .authenticationEntryPoint(new UserAuthenticationEntryPoint())
                        .accessDeniedHandler(new UserAccessDeniedHandler())
                        .and()
                        .apply(new CustomFilterConfigurer())
                        .and()
                        .formLogin().disable()
                        .httpBasic().disable()
                        .authorizeHttpRequests()
                        .antMatchers("/users/sign-up").permitAll()
                        .antMatchers("/users/check-id").permitAll()
                        .antMatchers("/users/check-nick").permitAll()
                        .antMatchers(HttpMethod.GET, "/users/**").permitAll()
                        .antMatchers("/users/login").permitAll()
                        .antMatchers("/users/**").hasRole("USER")

                        .antMatchers(HttpMethod.GET, "/careposts/**").permitAll()
                        .antMatchers("/careposts/**").hasRole("USER")

                        .antMatchers(HttpMethod.GET, "/boards/**").permitAll()
                        .antMatchers("/boards/**").hasRole("USER")
                        .antMatchers(HttpMethod.GET, "ws/chats/**").permitAll()
                        .antMatchers(HttpMethod.POST, "ws/chats/**").permitAll()
                        .antMatchers("/ws/**", "/sub/**", "/pub/**").permitAll()

                        .anyRequest().permitAll()
                        .and()
                        .oauth2Login()
                        .successHandler(oAuth2UserSuccessHandler)
                        .and()
                        .build();
    }


    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.addAllowedOriginPattern("http://localhost:3000"); // FE 개발 시 allowed
        configuration.addAllowedOriginPattern("http://localhost:8080");
        configuration.addAllowedOriginPattern("https://www.petching.net");
        configuration.addAllowedOriginPattern("https://petching.net");
        configuration.addAllowedOriginPattern("https://server.petching.net");
        configuration.addAllowedOriginPattern("*");

        configuration.addAllowedHeader("*");
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.addExposedHeader("Authorization");
        configuration.addExposedHeader("Refresh");
        configuration.addExposedHeader(HttpHeaders.LOCATION);
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    public class CustomFilterConfigurer extends AbstractHttpConfigurer<CustomFilterConfigurer, HttpSecurity> {
        @Override
        public void configure(HttpSecurity builder) throws Exception {
            AuthenticationManager authenticationManager = builder.getSharedObject(AuthenticationManager.class);

            JwtAuthenticationProcessingFilter jwtAuthenticationFilter = new
                    JwtAuthenticationProcessingFilter(authenticationManager, jwtService);
            jwtAuthenticationFilter.setFilterProcessesUrl("/users/login");
            jwtAuthenticationFilter.setAuthenticationSuccessHandler(new UserAuthenticationSuccessHandler());
            jwtAuthenticationFilter.setAuthenticationFailureHandler(new UserAuthenticationFailureHandler());

            JwtVerificationFilter jwtVerificationFilter = new JwtVerificationFilter(jwtService, authorityUtils);

            builder.addFilter(jwtAuthenticationFilter)
                    .addFilterAfter(jwtVerificationFilter, JwtAuthenticationProcessingFilter.class)
                    .addFilterAfter(jwtVerificationFilter, OAuth2LoginAuthenticationFilter.class);
        }

    }
}
