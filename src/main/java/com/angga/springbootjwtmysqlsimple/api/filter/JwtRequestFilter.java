package com.angga.springbootjwtmysqlsimple.api.filter;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.angga.springbootjwtmysqlsimple.api.services.UserService;
import com.angga.springbootjwtmysqlsimple.api.util.JwtUtil;

/**
 * JWT request filter used for filtering JWT
 * 
 * @author Angga Bayu Sejati<anggabs86@gmail.com>
 */
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

	@Autowired
	UserService userSrv;

	@Autowired
	private JwtUtil jwtUtil;

	/**
	 * Filtering request and then continues to response
	 * 
	 * @param request     HttpServletRequest
	 * @param response    HttpServletResponse
	 * @param filterChain FilterChain
	 * 
	 * @return void
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		// This is `naive` solution for prevent the JWT auth :)))
		var reqPath = request.getRequestURI();
		// if ("/hello-world".equals(reqPath)) {
		// 	filterChain.doFilter(request, response);
		// 	return;
		// }

		if ("/auth".equals(reqPath)) {
			filterChain.doFilter(request, response);
			return;
		}

		if ("/register".equals(reqPath)) {
			filterChain.doFilter(request, response);
			return;
		}

		final String authHeader = request.getHeader("Authorization");
		var userHeader = "";
		var jwtHeader = "";

		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			jwtHeader = authHeader.substring(7);
			userHeader = jwtUtil.extractUsername(jwtHeader);
		}

		if (userHeader != null && SecurityContextHolder.getContext().getAuthentication() == null) {

			var userDetail = userSrv.loadUserByUsername(userHeader);
			if (jwtUtil.validateToken(jwtHeader, userDetail)) {
				UsernamePasswordAuthenticationToken passwordAuth = new UsernamePasswordAuthenticationToken(
						userDetail,
						null,
						userDetail.getAuthorities());
				passwordAuth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(passwordAuth);
			}
		}

		filterChain.doFilter(request, response);
	}
}
