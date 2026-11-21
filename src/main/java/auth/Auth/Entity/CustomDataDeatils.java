package auth.Auth.Entity;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class CustomDataDeatils implements UserDetails {
    DataEntity dataEntity;
    public CustomDataDeatils(DataEntity dataEntity){
        this.dataEntity=dataEntity;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }
    @Override
    public @Nullable String getPassword() {
        return dataEntity.getPassword();
    }

    @Override
    public String getUsername() {
        return dataEntity.getEmail();
    }
}
