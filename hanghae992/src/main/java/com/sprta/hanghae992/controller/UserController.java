package com.sprta.hanghae992.controller;

import com.sprta.hanghae992.dto.LoginRequestDto;
import com.sprta.hanghae992.dto.MsgResponseDto;
import com.sprta.hanghae992.dto.SignupRequestDto;
import com.sprta.hanghae992.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class UserController {

    private final UserService userService;


    @PostMapping("/signup")
    public MsgResponseDto signup(@Valid @RequestBody SignupRequestDto signupRequestDto) {
        return userService.signup(signupRequestDto);
    }

    @ResponseBody
    @PostMapping("/login")
    public MsgResponseDto login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        return userService.login(loginRequestDto, response);
    }
}
