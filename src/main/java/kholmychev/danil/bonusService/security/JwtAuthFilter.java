package kholmychev.danil.bonusService.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kholmychev.danil.bonusService.providers.JwtTokenProvider;
import kholmychev.danil.bonusService.services.UsersDetailsService;

@Component
public class JwtAuthFilter extends OncePerRequestFilter{

	private final UsersDetailsService usersDetailsService;
	private final JwtTokenProvider jwtTokenProvider;
	
	@Autowired
	public JwtAuthFilter(UsersDetailsService usersDetailsService, JwtTokenProvider jwtTokenProvider) {
		this.usersDetailsService = usersDetailsService;
		this.jwtTokenProvider = jwtTokenProvider;
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		final String requestTokenHeader = request.getHeader("Authorization");
		
		String username = null;
		String token = null;
		
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer "))
		{
			token = requestTokenHeader.substring(7);
			try
			{
				username = jwtTokenProvider.getUsernameFromToken(token);
			}
			catch (Exception e)
			{
				System.out.println("Unable to get JWT Token");
			}
		}
		
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null)
		{
			UserDetails userDetails = usersDetailsService.loadUserByUsername(username);
			
			if (jwtTokenProvider.validateToken(token, userDetails))
			{
				UsernamePasswordAuthenticationToken authToken = 
						new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
		}
		filterChain.doFilter(request, response);
		
	}
}
