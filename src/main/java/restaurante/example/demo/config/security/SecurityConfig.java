/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurante.example.demo.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsConfiguration;
import restaurante.example.demo.config.security.filters.JwtTokenValidator;
import restaurante.example.demo.config.security.jwt.JwtUtils;
import restaurante.example.demo.service.implementation.user.UserDetailServiceImpl;
import java.util.Arrays;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserDetailServiceImpl userDetailService;

    @Autowired
    private JwtUtils jwtUtils;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            .csrf(csrf -> csrf.disable()) // Deshabilitar CSRF para endpoints públicos
            .cors(cors -> cors.configurationSource(this.corsConfiguration()))
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                // Public Endpoints
                .requestMatchers("/api/v1/super/**").permitAll()     
                .requestMatchers("/api/v1/customer/**").permitAll()     
                .requestMatchers("/api/v1/auth/**").permitAll()             
                .requestMatchers("/api/v1/cart/**").permitAll()
                .requestMatchers("/api/v1/reservation/**").permitAll()
                .requestMatchers("/api/v1/mesa/**").permitAll()
                .requestMatchers("/api/v1/customer/**").permitAll()
                // Private Endpoints (Roles & Authorities)                    
                 .requestMatchers("/api/v1/employee/**").hasAnyRole(SecurityRoles.ADMIN,SecurityRoles.SUPER)
                 .requestMatchers("/api/v1/admin/**").hasAnyRole(SecurityRoles.SUPER,SecurityRoles.ADMIN)      
                 .requestMatchers("/api/v1/menu/**").hasAnyRole(SecurityRoles.ADMIN,SecurityRoles.SUPER)
                 .requestMatchers("/api/v1/category/**").hasAnyRole(SecurityRoles.ADMIN,SecurityRoles.SUPER)
                 .requestMatchers("/api/v1/product/**").hasAnyRole(SecurityRoles.ADMIN,SecurityRoles.SUPER)
                .requestMatchers("/api/v1/order/**").hasRole(SecurityRoles.CLIENT)  
                // Default Policy
                .anyRequest().denyAll()
            )
            .addFilterBefore(new JwtTokenValidator(jwtUtils), BasicAuthenticationFilter.class)
            .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailService);
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfiguration() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(
                "http://localhost:5173"  
        ));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L); // Tiempo de cache para config CORS

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
    
}

        
                                                                