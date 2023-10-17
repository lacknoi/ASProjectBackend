package imp.as.authservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import imp.as.authservice.dto.request.AuthRequest;
import imp.as.authservice.dto.response.ApiResponse;
import imp.as.authservice.dto.response.AuthResponse;
import imp.as.authservice.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController extends AbsController{
	@Autowired
	private AuthService service;

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@PostMapping("/register")
    public ResponseEntity<ApiResponse> addNewUser(@RequestBody AuthRequest user) {
        return responseOK(service.saveUser(user));
    }
	    
	@PostMapping("/token")
    public ResponseEntity<ApiResponse> getToken(@RequestBody AuthRequest authRequest) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authenticate.isAuthenticated()) {
            String token = service.generateToken(authRequest.getUsername());
            
            return responseOK(AuthResponse.builder()
            							.token(token)
            							.username(authRequest.getUsername())
            							.build());
        } else {
            throw new RuntimeException("invalid access");
        }
    }
	
	@GetMapping("/validate")
    public String validateToken(@RequestParam("token") String token) {
        service.validateToken(token);
        return "Token is valid";
    }
}
