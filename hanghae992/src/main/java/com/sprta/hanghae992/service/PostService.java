package com.sprta.hanghae992.service;

import com.sprta.hanghae992.dto.PostRequestDto;
import com.sprta.hanghae992.dto.PostResponseDto;
import com.sprta.hanghae992.entity.Post;
import com.sprta.hanghae992.entity.User;
import com.sprta.hanghae992.jwt.JwtUtil;
import com.sprta.hanghae992.repository.PostRepository;
import com.sprta.hanghae992.repository.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    //게시글 작성
    @Transactional
    public PostResponseDto createPost(PostRequestDto postRequestDto, HttpServletRequest httpServletRequest) {
        // Request에서 Token 가져오기
        String token = jwtUtil.resolveToken(httpServletRequest);
        Claims claims;

        // 토큰이 있는 경우에만 게시글 추가 가능
        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }

            // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );
            Post post = postRepository.saveAndFlush(new Post(postRequestDto, user.getId(),user.getUsername()));
            return new PostResponseDto(post);
        }
        else { return null; }
    }

    //게시글 전체 조회
    @Transactional
    public List<PostResponseDto> getPost() {
        List<Post> postList = postRepository.findAllByOrderByModifiedAtDesc();
        List<PostResponseDto> postResponseDtoList = new ArrayList();
        for(Post post : postList){
            postResponseDtoList.add(new PostResponseDto(post));
        }
        return postResponseDtoList;
    }

    //게시글 수정
    @Transactional
    public PostResponseDto updatePost(Long id, PostRequestDto postRequestDto, HttpServletRequest httpServletRequest) {

        String token = jwtUtil.resolveToken(httpServletRequest);
        Claims claims;

        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }

            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );

            Post post= postRepository.findByIdAndUserId(id, user.getId()).orElseThrow(
                    () -> new NullPointerException("해당 게시글은 존재하지 않습니다.")
            );

            post.update(postRequestDto);
            PostResponseDto postResponseDto = new PostResponseDto(post);
            return postResponseDto;
        }
        else { return null;}
    }

    @Transactional
    public Message deleteMemo(Long id, HttpServletRequest httpServletRequest) {
        String token = jwtUtil.resolveToken(httpServletRequest);
        Claims claims;

        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }

            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );

            Post post = postRepository.findByIdAndUserId(id, user.getId()).orElseThrow(
                    () -> new NullPointerException("해당 게시글은 존재하지 않습니다.")
            );
            postRepository.deleteById(id);
            Message Sucess = new Message("게시글 삭제 성공",200);
            return Sucess;
        } else {Message Fail = new Message("게시글 삭제 실패",404);
            return Fail;}
    }

    //게시글 상세조회
    public PostResponseDto oneGetPost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
        PostResponseDto postResponseDto = new PostResponseDto(post);
        return postResponseDto;
    }
}
