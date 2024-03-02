package com.muni.app.services;


import com.muni.app.dtos.OtpValidationRequest;
import com.muni.app.dtos.UserRequest;
import com.muni.app.dtos.UserResponse;
import com.muni.app.models.MunicipalityEntity;
import com.muni.app.models.UserEntity;
import com.muni.app.repositories.MunicipalityRepository;
import com.muni.app.repositories.UserRepository;
import com.muni.app.utils.EmailService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Random;

@Service
@Async
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    Environment environment;
    @Autowired
    EmailService emailService;
    @Autowired
    MunicipalityRepository municipalityRepository;
    ModelMapper modelMapper = new ModelMapper();

    @Override
    public UserResponse saveUser(UserRequest userRequest) {
        if(userRequest.getEmail() == null){
            throw new RuntimeException("Parameter email is not found in request..!!");
        } else if(userRequest.getPassword() == null){
            throw new RuntimeException("Parameter password is not found in request..!!");
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = userRequest.getPassword();
        String encodedPassword = encoder.encode(rawPassword);
        var random = new Random();
        var otp = Integer.parseInt(String.format("%05d", random.nextInt(99999)));
        var municipality = municipalityRepository.findByNameAndProvince(userRequest.getMunicipality(), userRequest.getProvince());
        UserEntity user = modelMapper.map(userRequest, UserEntity.class);
        user.setPassword(encodedPassword);
        user.setEmail(userRequest.getEmail());
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setMobile(userRequest.getMobile());
        user.setMunicipality(municipality);
        user.setOtp(otp);
        UserEntity savedUser = userRepository.save(user);
//        emailService.sendEmail(user.getEmail(), "OTP", String.valueOf(otp));
        return modelMapper.map(savedUser, UserResponse.class);
    }

    @Override
    public UserResponse getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetail = (UserDetails) authentication.getPrincipal();
        String usernameFromAccessToken = userDetail.getUsername();
        UserEntity user = userRepository.findByEmail(usernameFromAccessToken);
        UserResponse userResponse = modelMapper.map(user, UserResponse.class);
        return userResponse;
    }

    @Override
    public List<UserResponse> getAllUser() {
        List<UserEntity> users = userRepository.findAll();
        Type setOfDTOsType = new TypeToken<List<UserResponse>>(){}.getType();
        List<UserResponse> userResponses = modelMapper.map(users, setOfDTOsType);
        return userResponses;
    }

    @Override
    public UserResponse validateOtp(OtpValidationRequest request) {
        UserEntity user = userRepository.findByEmailAndOtp(request.getEmail(), request.getOtp());
        if(null == user) {
            throw new UsernameNotFoundException("Sorry");
        }
        return modelMapper.map(user, UserResponse.class);
    }
}
