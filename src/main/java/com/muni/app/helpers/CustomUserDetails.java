package com.muni.app.helpers;


import com.muni.app.dtos.MunicipalityResponse;
import com.muni.app.models.MunicipalityEntity;
import com.muni.app.models.UserEntity;
import com.muni.app.models.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomUserDetails extends UserEntity implements UserDetails {

    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String mobile;
    private MunicipalityEntity municipality;
    Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(UserEntity user) {
        this.email = user.getEmail();
        this.password= user.getPassword();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.mobile = user.getMobile();
        this.municipality = new MunicipalityEntity(
                user.getMunicipality().getId(),
                user.getMunicipality().getName(),
                user.getMunicipality().getProvince()
        );
        List<GrantedAuthority> auths = new ArrayList<>();

        for(UserRole role : user.getRoles()){

            auths.add(new SimpleGrantedAuthority(role.getName().toUpperCase()));
        }
        this.authorities = auths;
    }
    @Override
    public String getFirstName() {
        return firstName;
    }
    @Override
    public void setFirstName(String fistName) {
        this.firstName = fistName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String getMobile() {
        return mobile;
    }

    @Override
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Override
    public MunicipalityEntity getMunicipality() {
        return municipality;
    }

    public void setMunicipality(MunicipalityEntity municipality) {
        this.municipality = municipality;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
