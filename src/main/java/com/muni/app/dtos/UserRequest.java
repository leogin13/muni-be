package com.muni.app.dtos;

import com.muni.app.models.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String mobile;
    private String password;
    private String region;
    private String province;
    private String municipality;
    private String brgy;
    private String deviceToken;
    private String os;
}
