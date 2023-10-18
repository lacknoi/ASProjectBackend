package imp.as.accountservice.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {
	@Autowired
	private JwtUtil jwtUtil;
	
	@Override
    protected void doFilterInternal(HttpServletRequest request,  HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
		final String jwtAuthToken = request.getHeader("Authorization");
		final String referer = request.getHeader("referer");
		
		String path = request.getRequestURI();
		
		if(!path.contains("api-docs") && !path.contains("swagger")
				&& !referer.contains("api-docs") && !referer.contains("swagger")) {
	        if (jwtAuthToken != null && jwtAuthToken.startsWith("Bearer ")) {
	        	String jwtToken = jwtAuthToken.substring(7);
	        	
	        	try {
	        		boolean res = jwtUtil.validateToken(jwtToken);
	        		
	        		if(!res) {
	        			System.out.println("token expires...!");
	                    throw new RuntimeException("un authorized access to application");
	        		}
	        	} catch (Exception e) {
	                System.out.println("invalid access...!");
	                throw new RuntimeException("un authorized access to application");
	        	}
	        }else {
	        	throw new RuntimeException("missing authorization header");
	        }
		}
        
        chain.doFilter(request, response);
    }
}
