package com.example.ch16.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SignInResultDto extends SignUpResultDto{

    private String token;

    @Builder
    public SignInResultDto(boolean succese, int code, String msg, String token) {
        super(succese, code, msg);
        this.token = token;
    }

}
