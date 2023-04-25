package com.sprta.hanghae992.service;

import com.sprta.hanghae992.dto.LoginRequestDto;
import com.sprta.hanghae992.dto.MsgResponseDto;
import com.sprta.hanghae992.dto.PostResponseDto;
import com.sprta.hanghae992.dto.SignupRequestDto;
import com.sprta.hanghae992.entity.User;
import com.sprta.hanghae992.jwt.JwtUtil;
import com.sprta.hanghae992.repository.UserRepository;
import com.sun.net.httpserver.Authenticator;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;
import java.util.regex.Pattern;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final JwtUtil jwtUtil;


    @Transactional
    public MsgResponseDto signup(SignupRequestDto signupRequestDto) {

        String username = signupRequestDto.getUsername();
        String password = signupRequestDto.getPassword();



            // 회원 중복 확인
            Optional<User> found = userRepository.findByUsername(username);

            if (found.isPresent()) {
                throw new IllegalArgumentException("중복된 사용자가 존재합니다.");

            }
//            if(!Pattern.matches("^[a-z0-9]{4,10}$", username) || !Pattern.matches("^[a-zA-Z0-9]{8,15}$", password)){
//            // throw new IllegalStateException("회원가입 양식 조건에 맞지 않습니다.");
//                return new MsgResponseDto("회원가입 양식 조건에 맞지 않습니다.", HttpStatus.BAD_REQUEST);
//            }

            User user = new User(username, password);
            userRepository.save(user);
            return new MsgResponseDto("회원가입 성공",HttpStatus.OK) ;
        }










    @Transactional(readOnly = true)
    public MsgResponseDto login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        String username = loginRequestDto.getUsername();
        String password = loginRequestDto.getPassword();



        // 사용자 확인
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("등록된 사용자가 없습니다.")
        );


        // 비밀번호 확인
        if (!user.getPassword().equals(password)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUsername()));

            return new MsgResponseDto("로그인 성공",HttpStatus.OK);
        }

    }

