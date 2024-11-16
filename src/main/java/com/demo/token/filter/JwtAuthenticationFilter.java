package com.demo.token.filter;

import java.io.IOException;
import java.io.PrintWriter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.demo.token.serviceImpl.JwtService;
import com.demo.token.serviceImpl.UserDetailsServiceImp;
import com.demo.token.validation.ValidPath;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	/**
	 * JWtService responsible for generating and validating token used to extract
	 * userName from token and validated token which is tampered or not
	 */
	@Autowired
	private JwtService jwtService;

	/**
	 * UserDetailsServiceImp is responsible for loading user details Used to load
	 * user details from DB by using user credentials from request(userName)
	 */
	@Autowired
	private UserDetailsServiceImp userDetailsService;

	/**
	 * ValidPath is a utility or service that verifies and validates the request
	 * paths or endpoints to ensure if users entered correct path
	 */
	@Autowired
	private ValidPath validPath;

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		String path = request.getRequestURI();
		// Exclude login endpoint from JWT validation
		return path.equals("/api/user/login");
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {

		String path = request.getRequestURI();

		if (!validPath.isValid(path)) {
			sendErrorResponse(response, "Invalid url, pleach check it");
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		final String authorizationHeader = request.getHeader("Authorization");

		if (authorizationHeader != null) {
			System.out.println("Authorization Header: " + authorizationHeader);

			String username = null;
			String jwt = null;
			try {
				if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
					jwt = authorizationHeader.substring(7); // Remove prefix from token header "Bearer "
					username = jwtService.extractUserName(jwt);
					System.out.println("Extracted JWT: " + jwt);
					System.out.println("Extracted Username: " + username);
				}

				if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
					var userDetails = this.userDetailsService.loadUserByUsername(username);
					boolean isValid = jwtService.validateToken(jwt, userDetails);
					System.out.println("Token Validity: " + isValid);
					if (isValid) {
						var usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails,
								null, userDetails.getAuthorities());
						usernamePasswordAuthenticationToken
								.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
						SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
					} else {
						sendErrorResponse(response, "Invalid token or expired");
						return;
					}
				}
				chain.doFilter(request, response);
			} catch (JwtException ex) {
				// Handle JWT specific exceptions like signature or token expiration
				sendErrorResponse(response, "Invalid token or expired");
				System.err.println("JWT SignatureException: " + ex.getMessage());
			} catch (Exception ex) {
				// Log other exceptions
				System.err.println("Exception: " + ex.getMessage());
				sendErrorResponse(response, "An error occurred while processing the token");
			}
		} else {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // Set the status code to 400
			sendErrorResponse(response, "Missing Token at Header");
			System.err.println("Authorization Header: " + authorizationHeader);
			return;
		}

	}

	private void sendErrorResponse(HttpServletResponse response, String message) throws IOException {
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.setContentType("application/json"); // Set the response content type to JSON
		PrintWriter out = response.getWriter();
		out.write("{\"error\": \"" + message + "\", \"details\": \"Bad request or unauthorized access\"}");
		out.flush();
	}
}
