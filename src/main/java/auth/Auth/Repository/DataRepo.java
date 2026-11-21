package auth.Auth.Repository;

import auth.Auth.Entity.DataEntity;
import auth.Auth.Entity.DataLoginResponseDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DataRepo extends JpaRepository<DataEntity,Long> {
    Optional<DataEntity> findByEmail(String email);
}
