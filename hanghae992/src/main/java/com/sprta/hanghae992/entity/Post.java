package com.sprta.hanghae992.entity;

import com.sprta.hanghae992.dto.PostRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Post extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private String contents;


    @Column(nullable = false)
    private Long userId;

    @Column
    private String username;

    public Post(PostRequestDto postRequestDto, Long userId, String username) {   //게시글 작성
        this.userId = userId;
        this.username = username;
        this.title = postRequestDto.getTitle();
        this.contents = postRequestDto.getContents();
    }

    public void update(PostRequestDto postRequestDto) {    //메모 수정
        this.contents = postRequestDto.getContents();
        this.title = postRequestDto.getTitle();
    }
}
