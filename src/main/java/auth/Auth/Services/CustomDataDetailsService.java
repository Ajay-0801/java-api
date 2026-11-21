package auth.Auth.Services;

import auth.Auth.Entity.CustomDataDeatils;
import auth.Auth.Entity.DataEntity;
import auth.Auth.Repository.DataRepo;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class CustomDataDetailsService implements UserDetailsService {
    private final DataRepo dataRepo;
    CustomDataDetailsService(DataRepo dataRepo){
        this.dataRepo=dataRepo;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        DataEntity data = dataRepo.findByEmail(username)
                .orElseThrow(()->new RuntimeException("User not found"));
        return new CustomDataDeatils(data);
    }
}
