package com.example.springbackend.board.model;

import com.example.springbackend.user.userModel.User;
import jakarta.persistence.*;
import lombok.*;

//보드 정보 : 이메일, 아이디, 시간,

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "BOARD")
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDX")
    private Long idx;

    @Column(name = "TITLE", length = 100)
    private String title;

    @Column(name = "CONTENTS", length = 300)
    private String contents;

    @Column(name = "WATCHED")
    private Long watched;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(
            name = "NAME",
            referencedColumnName = "NAME",
            foreignKey = @ForeignKey(name = "fk_board_user_name")
    )

    private User user;

}

