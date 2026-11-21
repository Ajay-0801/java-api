package auth.Auth.Services;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class JwtService {
    private JwtEncoder jwtEncoder;
    @Value("${jwt.issuer}")
    private String jwtissuer;
    @Value("${jwt.expiry}")
    private long expiry;

    JwtService(JwtEncoder jwtEncoder){
        this.jwtEncoder=jwtEncoder;
    }


    public String generateToken(Authentication auth) {
        Instant now = Instant.now();
        List<String> authorities = auth.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        JwtClaimsSet claim = JwtClaimsSet.builder()
                .issuedAt(now)
                .issuer(jwtissuer)
                .expiresAt(now.plusSeconds(expiry))
                .subject(auth.getName())
                .claim("authorities", authorities)
                .build();

        Jwt jwt = jwtEncoder.encode(
                JwtEncoderParameters.from(claim)
        );

        return jwt.getTokenValue();
    }


}
