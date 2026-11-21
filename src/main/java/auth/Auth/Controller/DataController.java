package auth.Auth.Controller;

import auth.Auth.Entity.DataLoginDTO;
import auth.Auth.Entity.DataLoginResponseDTO;
import auth.Auth.Entity.DataRequestDTO;
import auth.Auth.Entity.DataResponseDTO;
import auth.Auth.Services.DataService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class DataController {
    DataService dataService;
    DataController(DataService dataService){
        this.dataService=dataService;
    }
    //Testing
    @GetMapping("/test")
    public String test(){
        return "Everything is working fine";
    }
    @PostMapping("/create")
    public ResponseEntity<DataResponseDTO> Create( @Valid @RequestBody DataRequestDTO Data){
        return dataService.create(Data);
    }
    @GetMapping("/login")
    public ResponseEntity<DataLoginResponseDTO> Login(@RequestBody DataLoginDTO data){
        return dataService.Login(data);
    } 
}
