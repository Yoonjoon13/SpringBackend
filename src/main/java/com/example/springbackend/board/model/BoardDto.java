package com.example.springbackend.board.model;

import com.example.springbackend.user.userModel.User;
import lombok.Getter;

public class BoardDto {

    @Getter
    public static class BoardReq {
        private String title;
        private String contents;
        private String username;

        public Board toEntity(User user) {
            return Board.builder()
                    .title(this.title)
                    .contents(this.contents)
                    .user(user)
                    .watched(0L)
                    .build();
        }
    }
}
