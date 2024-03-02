package com.muni.app.dtos;

import lombok.Data;

@Data
public class OtpValidationRequest {
    private String email;
    private String password;
    private int otp;
}
