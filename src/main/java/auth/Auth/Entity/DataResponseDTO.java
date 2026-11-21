package auth.Auth.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DataResponseDTO {
    private String name;
    private String email;
    @Builder.Default
    private String message="User is successfully registered";
}
