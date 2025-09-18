package com.unam.dwb.auth.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.unam.dwb.auth.filter.JwtAuthFilter;
import com.unam.dwb.auth.filter.UsernameOrCorreoAuthenticationProvider;

@Configuration
public class SecurityConfig {
	
	@Autowired
	private JwtAuthFilter jwtFilter;
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http, CorsConfig corsConfig) throws Exception {
	
		http.csrf(AbstractHttpConfigurer::disable)
		.authorizeHttpRequests(
				auth -> auth
				.requestMatchers("/error", "/swagger-ui/**", "/v3/api-docs/**", "/actuator/info", "/actuator/health").permitAll()
				.requestMatchers(HttpMethod.POST, "/usuario").permitAll()
				.requestMatchers(HttpMethod.POST, "/login").permitAll()
				.requestMatchers(HttpMethod.GET, "/usuario").hasAuthority("ADMIN")
				)
		.cors(cors -> cors.configurationSource(corsConfig))
		.httpBasic(Customizer.withDefaults())
		.formLogin(form -> form.disable())
		.sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
			
		return http.build();
	}
	
	@Bean
    AuthenticationManager authenticationManager(UsernameOrCorreoAuthenticationProvider provider) {
        return new ProviderManager(List.of(provider));
    }
	
	@Bean
	BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
