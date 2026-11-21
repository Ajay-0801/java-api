package auth.Auth.config;


import auth.Auth.Services.CustomDataDetailsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;




@Configuration
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(
            CustomDataDetailsService customDataDetailsService,
            PasswordEncoder passwordEncoder
    ){
        DaoAuthenticationProvider provider=new DaoAuthenticationProvider(customDataDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,DaoAuthenticationProvider provider)
            throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth->
                        auth.requestMatchers("/auth/login").permitAll()
                                .anyRequest().authenticated()
                ).sessionManagement(session->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                ).oauth2ResourceServer(oauth2 ->
                oauth2.jwt(jwt ->
                        jwt.jwtAuthenticationConverter(
                                jwtAuthenticationConverter()
                        )
                )
        );
        return http.build();
    }
    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter(){
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter
                = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName(
                "authorities"
        );
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("");
        JwtAuthenticationConverter  jwtAuthenticationConverter
                = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(
                jwtGrantedAuthoritiesConverter
        );
        return jwtAuthenticationConverter;
    }
    @Bean
    public AuthenticationManager authenticationManager(DaoAuthenticationProvider provider){
        return new ProviderManager(provider);
    }
    @Bean
    public SecretKey secretKey(@Value("${jwt.secretkey}") String secret){
        byte[] decode= Base64.getDecoder().decode(secret);
        return new SecretKeySpec(decode,"HmacSHA256");
    }
    @Bean
    public JwtEncoder jwtEncoder(
            SecretKey secretKey) {

        return NimbusJwtEncoder
                .withSecretKey(secretKey)
                .algorithm(MacAlgorithm.HS256)
                .build();
    }
    @Bean
    public NimbusJwtDecoder jwtDecoder(SecretKey secretKey,@Value("${jwt.issuer}") String
                                 issuer){

        NimbusJwtDecoder decoder=NimbusJwtDecoder.withSecretKey(secretKey).macAlgorithm(
                MacAlgorithm.HS256
        ).build();
        decoder.setJwtValidator(JwtValidators.createDefaultWithIssuer(
                issuer
        ));
        return decoder;
    }

}


