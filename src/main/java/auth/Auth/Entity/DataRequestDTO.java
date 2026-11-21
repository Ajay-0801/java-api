package auth.Auth.Entity;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

@Validated
@Data
public class DataRequestDTO {
 @NotBlank
 private String password;
 @NotBlank
 @Email
 private String email;
 @NotBlank
 private String name;
}
