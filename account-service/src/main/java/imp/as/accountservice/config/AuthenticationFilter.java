package imp.as.accountservice.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class AuthenticationFilter implements Filter {
	@Autowired
	private JwtUtil jwtUtil;
	
	@Override
    public void doFilter(ServletRequest request,ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        
        System.out.println("--doFilter--");
        
        final String jwtAuthToken = httpServletRequest.getHeader("Authorization");
        
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
        
        chain.doFilter(request, response);
    }
}
