package com.sprta.hanghae992.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;



@Setter
@Getter
@AllArgsConstructor
public class SignupRequestDto {

    //영문자(소문자),숫자만 입력 그리고 4글자~10글자로 입력 (한글만 입력 불가)
    @Pattern(regexp = "^[a-z0-9]{4,10}$")
//    @Pattern(regexp = "^[^ㄱ-ㅎ가-힣]*$")  //한글 입력 불가
//    @NotNull(message = "올바른 형식이 아닙니다.")
//    @NotBlank(message = "올바른 형식이 아닙니다.")
    private String username;


    //영문자(대소문자),숫자만 입력 8~15글자로 입력 (한글만 입력 불가)
    @Pattern(regexp = "^[a-zA-Z0-9]{8,15}$")
//    @Pattern(regexp = "^[^ㄱ-ㅎ가-힣]*$")   //한글 입력 불가
//    @NotNull(message = "올바른 형식이 아닙니다.")
//    @NotBlank(message = "올바른 형식이 아닙니다.")
    private String password;


}








