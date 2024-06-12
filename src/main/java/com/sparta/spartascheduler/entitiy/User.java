package com.sparta.spartascheduler.entitiy;


import com.sparta.spartascheduler.dto.UserRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "user")
@NoArgsConstructor
public class User extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String nickname;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    public User(UserRequestDto requestDto, String encoded, UserRoleEnum userRoleEnum) {
        this.nickname = requestDto.getNickname();
        this.username = requestDto.getUsername();
        this.password = encoded;
        this.role = userRoleEnum;
    }
}
