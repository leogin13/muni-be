package com.muni.app.services;


import java.util.List;

import com.muni.app.dtos.OtpValidationRequest;
import com.muni.app.dtos.UserResponse;
import com.muni.app.dtos.UserRequest;


public interface UserService {

    UserResponse saveUser(UserRequest userRequest);

    UserResponse getUser();

    List<UserResponse> getAllUser();

    UserResponse validateOtp(OtpValidationRequest request);
}
