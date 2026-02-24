package com.example.springbackend.board.model;

import com.example.springbackend.user.userModel.User;
import lombok.Builder;
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
                    .watched(0L)
                    .user(user)
                    .build();
        }
    }

    @Builder
    @Getter
    public static class BoardRes {
        private Long idx;
        private String title;
        private String contents;
        private Long watched;
        private String username;

        public static BoardRes from(Board entity) {
            return BoardRes.builder()
                    .idx(entity.getIdx())
                    .title(entity.getTitle())
                    .contents(entity.getContents())
                    .watched(entity.getWatched())
                    .username(entity.getUser() != null ? entity.getUser().getName() : null)
                    .build();
        }
    }
}
