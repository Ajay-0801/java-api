package auth.Auth.Services;

import auth.Auth.Entity.*;
import auth.Auth.Repository.DataRepo;
import org.jspecify.annotations.Nullable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DataService {
    DataRepo dataRepo;
    PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
    DataService(DataRepo dataRepo){
        this.dataRepo=dataRepo;
    }
    public ResponseEntity<DataResponseDTO> create(DataRequestDTO data){
        DataEntity dataEntity=maptoData(data);
        dataRepo.save(dataEntity);
        System.out.println(maptoResponsedto(dataEntity));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(maptoResponsedto(dataEntity));
    }
    public ResponseEntity<DataLoginResponseDTO> Login(DataLoginDTO data){
        Optional<DataEntity> d = dataRepo.findByEmail(data.getEmail());
        if(d.isEmpty() || !passwordEncoder.matches(
                data.getPassword(), d.get().getPassword()
        ))throw new RuntimeException("User not found");
        return ResponseEntity.status(HttpStatus.FOUND)
                .body(maptoLogindata(d.get()));
    }
    private DataEntity maptoData(DataRequestDTO data){

        return DataEntity.builder()
                .email(data.getEmail())
                .name(data.getName())
                .password(passwordEncoder.encode(data.getPassword())).build();
    }
    private DataLoginResponseDTO maptoLogindata(DataEntity data){
        return DataLoginResponseDTO.builder()
                .name(data.getName())
                .email(data.getEmail()).build();
    }
    private DataResponseDTO maptoResponsedto(DataEntity data){
        return DataResponseDTO.builder()
                .name(data.getName())
                .email(data.getEmail())
                .build();
    }

}
