package kholmychev.danil.bonusService.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

	private final UserDetailsService userDetailsService;
	
	@Autowired
	public SecurityConfig(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}
	
	
//	@Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//            .authorizeHttpRequests(authz -> authz
//                .requestMatchers("/api/auth/login", "/api/auth/register").permitAll()
//                .requestMatchers("/api/admin").hasAnyRole("ADMIN")
//                .anyRequest().authenticated()
//            )
//            .csrf(csrf -> csrf.disable())
//            .formLogin(form -> form
//                .disable()
//            )
//            .httpBasic(httpBasic -> httpBasic.disable());
//
//        return http.build();
//    }
	
	@Bean
    public SecurityFilterChain chain(HttpSecurity http) {
        return http.authorizeHttpRequests(authz -> authz
        		.requestMatchers("/**").permitAll()
        		)
        		.csrf(csrf -> csrf.disable())
        		.build();
	}
	
	@Bean
	public PasswordEncoder getPasswordEncoder()
	{
		return new BCryptPasswordEncoder();
	}
	
}
