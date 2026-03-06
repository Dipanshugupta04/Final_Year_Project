package com.example.MEDICINE.Service;

import com.example.MEDICINE.Model.User;

// import com.example.MEDICINE.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
     private AuthenticationManager authenticationManager;
    // @Autowired
      private JWTService jwtService;
    
    // private static final String SECRET_KEY = "mysecretkey";

  

    public String login(User user) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())
        );
        if(authentication.isAuthenticated()){
            return jwtService.GenerateToken(user.getEmail());
        }
     return null;

        

        
        // User user = userRepository.findByEmail(request.getEmail())
        //         .orElseThrow(() -> new RuntimeException("User not found"));
        // String token = Jwts.builder()
        //         .Subject(user.getEmail())
        //         .IssuedAt(new Date())
        //         .Expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
        //         .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
        //         .compact();
        // return new LoginResponse(user.getEmail(), token);
    }
}
