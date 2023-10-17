package imp.as.authservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import imp.as.authservice.dto.request.AuthRequest;
import imp.as.authservice.entity.UserCredential;
import imp.as.authservice.repository.UserCredentialRepository;

@Service
public class AuthService {

    @Autowired
    private UserCredentialRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    public String saveUser(AuthRequest authRequest) {
    	UserCredential credential = new UserCredential();
    	credential.setName(authRequest.getUsername());
    	credential.setEmail(authRequest.getEmail());
        credential.setPassword(passwordEncoder.encode(authRequest.getPassword()));
        repository.save(credential);
        return "user added to the system";
    }

    public String generateToken(String username) {
        return jwtService.generateToken(username);
    }

    public void validateToken(String token) {
        jwtService.validateToken(token);
    }


}
