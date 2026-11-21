package auth.Auth.Controller;

import auth.Auth.Entity.DataLoginDTO;
import auth.Auth.Entity.DataLoginResponseDTO;
import auth.Auth.Services.JwtService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {
    private final JwtService jwtService;
    public AuthenticationManager authenticationManager;
    AuthController(JwtService jwtService
                   ,AuthenticationManager authenticationManager){
        this.jwtService=jwtService;
        this.authenticationManager=authenticationManager;
    }
    @PostMapping("/login")
    public DataLoginResponseDTO login(@RequestBody DataLoginDTO dataLoginDTO){
        Authentication authenticationRequest= UsernamePasswordAuthenticationToken.unauthenticated(
                dataLoginDTO.getEmail(), dataLoginDTO.getPassword()
        );
        Authentication authentication=authenticationManager.authenticate(authenticationRequest);
        String token = jwtService.generateToken(authentication);
        return DataLoginResponseDTO.builder()
                .name(dataLoginDTO.getEmail())
                .message(token).build();
    }
}
