package com.cda.winit.auth.application;

import com.cda.winit.auth.domain.entity.enumeration.Role;
import com.cda.winit.auth.domain.service.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;
    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final AccessDeniedHandler accessDeniedHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configure(http))
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/api/auth/register","/api/auth/login").permitAll()
                        .requestMatchers("/api/v1/demo/users-only").hasAnyRole(Role.USER.name()) /* ROLE_USER */
                        .requestMatchers("/api/v1/demo/admin-only").hasAnyRole(Role.ADMIN.name()) /* ROLE_ADMIN */
                        .anyRequest().authenticated()
                )
                .csrf((csrf) -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()) // You can disable csrf protection by removing this line
                        .ignoringRequestMatchers("/register", "/login")
                        .disable()  // Décommentez pour désactiver en entier la protection CSRF en développement
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)//
                )
                .exceptionHandling((exception) ->  exception
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler)
        )
                .authenticationProvider(authenticationProvider)

                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
