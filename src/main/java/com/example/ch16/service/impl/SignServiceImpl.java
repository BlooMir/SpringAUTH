package com.example.ch16.service.impl;

import com.example.ch16.config.security.JwtTokenProvider;
import com.example.ch16.dto.CommonResponse;
import com.example.ch16.dto.SignInResultDto;
import com.example.ch16.dto.SignUpResultDto;
import com.example.ch16.repository.UserRepository;
import com.example.ch16.service.SignService;
import com.example.ch16.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class SignServiceImpl implements SignService {

    public UserRepository userRepository;
    public JwtTokenProvider jwtTokenProvider;
    public PasswordEncoder passwordEncoder;

    @Autowired
    public SignServiceImpl(UserRepository userRepository, JwtTokenProvider jwtTokenProvider, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public SignUpResultDto signUp(String id, String password, String name, String email, String role) {
        System.out.println("[signUp]");
        User user;
        if(role.equalsIgnoreCase("admin")){
            user = User.builder()
                    .uid(id)
                    .name(name)
                    .email(email)
                    .password(passwordEncoder.encode(password))
                    .roles(Collections.singletonList("ROLE_ADMIN")).build();
        } else {
            user = User.builder()
                    .uid(id)
                    .name(name)
                    .email(email)
                    .password(passwordEncoder.encode(password))
                    .roles(Collections.singletonList("ROLE_ADMIN")).build();
        }
        User savedUser = userRepository.save(user);
        SignUpResultDto signUpResultDto = new SignUpResultDto();
        if(!savedUser.getName().isEmpty()){
            setSuccessResult(signUpResultDto);
        } else {
            setFailResult(signUpResultDto);
        }
        return signUpResultDto;
    }

    @Override
    public SignInResultDto signIn(String id, String password) throws RuntimeException {
        User user = userRepository.getByUid(id);
        if(!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException();
        }
        SignInResultDto signInResultDto = SignInResultDto.builder()
                .token(jwtTokenProvider.createToken(
                        String.valueOf(user.getUid()), user.getRoles())).build();
        setSuccessResult(signInResultDto);

        return signInResultDto;
    }

    private void setSuccessResult(SignUpResultDto signUpResultDto){
        signUpResultDto.setSuccese(true);
        signUpResultDto.setCode(CommonResponse.SUCCESS.getCode());
        signUpResultDto.setMsg(CommonResponse.SUCCESS.getMsg());
    }
    private void setFailResult(SignUpResultDto signUpResultDto){
        signUpResultDto.setSuccese(false);
        signUpResultDto.setCode(CommonResponse.FAIL.getCode());
        signUpResultDto.setMsg(CommonResponse.FAIL.getMsg());
    }
}
