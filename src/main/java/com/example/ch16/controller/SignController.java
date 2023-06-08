package com.example.ch16.controller;

import com.example.ch16.dto.SignInResultDto;
import com.example.ch16.dto.SignUpResultDto;
import com.example.ch16.service.SignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sign-api")
public class SignController {

    private final SignService signService;

    @Autowired
    public SignController(SignService signService) {
        this.signService = signService;
    }

    @PostMapping("/sign-in")
    public SignInResultDto signIn(@RequestParam String id, @RequestParam String password) throws RuntimeException {
        SignInResultDto signInResultDto = signService.signIn(id, password);
        if(signInResultDto.getCode() == 0) {
            System.out.println("[정상 로그인]" + signInResultDto.getToken());
        }
        return signInResultDto;
    }
    @PostMapping("/sign-up")
    public SignUpResultDto signUP(
             @RequestParam String id,
             @RequestParam String password,
             @RequestParam String name,
             @RequestParam String email,
             @RequestParam String role
    ) throws RuntimeException {
        SignUpResultDto signUpResultDto = signService.signUp(id, password, name, email, role);
        return signUpResultDto;
    }


    @GetMapping("/exception")
    public void exception() throws RuntimeException {
        throw new RuntimeException("접근이 금지되었습니다.");
    }
}
