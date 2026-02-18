package kholmychev.danil.bonusService.config;

import kholmychev.danil.bonusService.security.JwtAuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

	private final UserDetailsService userDetailsService;
	
	@Autowired
	public SecurityConfig(UserDetailsService userDetailsService, JwtAuthFilter jwtAuthFilter) {
		this.userDetailsService = userDetailsService;
		this.jwtAuthFilter = jwtAuthFilter;
	}
	
	
	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(authz -> authz
        		.requestMatchers("/api/auth/login", "/api/auth/register").permitAll()
        		.requestMatchers(HttpMethod.PATCH, "/api/cards/up-balance", "/api/cards/down-balance", "/api/cards/refund-bonus").hasAnyRole("ADMIN")
        		.requestMatchers(HttpMethod.GET, "/api/cards/**").hasAnyRole("USER", "ADMIN")
        		.anyRequest().authenticated()
        		)
        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
        .httpBasic(httpBasic -> httpBasic.disable())
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }
	
//	@Bean
//    public SecurityFilterChain chain(HttpSecurity http) {
//        return http.authorizeHttpRequests(authz -> authz
//        		.requestMatchers("/**").permitAll()
//        		)
//        		.csrf(csrf -> csrf.disable())
//        		.build();
//	}
	
	@Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
	
	@Bean
	public PasswordEncoder getPasswordEncoder()
	{
		return new BCryptPasswordEncoder();
	}
	
}
