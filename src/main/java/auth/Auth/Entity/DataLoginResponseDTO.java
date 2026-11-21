package auth.Auth.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DataLoginResponseDTO{
    @Builder.Default
    private String name="Not Used";
    private String email;
    @Builder.Default
    private String message="User Found";
}
