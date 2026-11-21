package auth.Auth.Services;


import auth.Auth.Entity.DataEntity;
import auth.Auth.Entity.DataLoginDTO;
import auth.Auth.Entity.DataLoginResponseDTO;
import auth.Auth.Repository.DataRepo;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private DataRepo userRepository;
    private PasswordEncoder passwordEncoder;

    public AuthService(
            DataRepo userRepository,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<DataLoginResponseDTO> register(
            DataLoginDTO registerRequestDto) {

        DataEntity user = new DataEntity();
        user.setEmail(registerRequestDto.getEmail());

        String encodedPassword =
                passwordEncoder.encode(registerRequestDto.getPassword());

        user.setPassword(encodedPassword);
        userRepository.save(user);

       DataLoginResponseDTO responseDto = new
               DataLoginResponseDTO();

        responseDto.setEmail(user.getEmail());
        responseDto.setMessage("User saved Successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);

    }
}
