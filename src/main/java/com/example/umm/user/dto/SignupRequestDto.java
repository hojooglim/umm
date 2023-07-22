package com.example.umm.user.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class SignupRequestDto {
    @NotNull(message = "email 필수 입니다.")
    @Pattern(regexp = "[a-zA-z0-9]+@[a-zA-z]+[.]+[a-zA-z.]+",message = "email 형식이 맞지 않습니다.")
    String email;

    @NotNull(message = "password 필수 입니다.")
    @Pattern(regexp = "^[a-zA-Z0-9!@#$%^&*()]*$", message = "password는 알파벳 대소문자(a~z, A~Z), 숫자(0~9)만 입력 가능합니다.")
    @Size(min=7,max=16)
    String password;

    String nickname;
}
