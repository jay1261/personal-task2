package com.sparta.spartascheduler.entitiy;

import com.sparta.spartascheduler.dto.CommentRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Entity
@Getter
@Table(name = "comment")
@NoArgsConstructor
public class Comment extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    private String username;

    @Setter
    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;


    public Comment(CommentRequestDto requestDto, String username) {
        this.contents = requestDto.getContents();
        this.username = username;
    }

    public void update(CommentRequestDto requestDto) {
        this.contents = requestDto.getContents();
    }
}
