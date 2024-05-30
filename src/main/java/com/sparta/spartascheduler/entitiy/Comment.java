package com.sparta.spartascheduler.entitiy;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

}
