package com.muni.app.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtResponseDTO {

    private String accessToken;
    private String firstName;
    private String lastName;
    private MunicipalityResponse municipality;
    private String token;
    private boolean isAdmin;
}
