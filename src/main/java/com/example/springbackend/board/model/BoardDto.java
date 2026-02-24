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
        private String userName;
        private String name;
        private String author;
        private String email;
        private Long userId;

        public String getAnyUsername() {
            if (this.username != null && !this.username.isBlank()) {
                return this.username;
            }
            if (this.userName != null && !this.userName.isBlank()) {
                return this.userName;
            }
            if (this.name != null && !this.name.isBlank()) {
                return this.name;
            }
            if (this.author != null && !this.author.isBlank()) {
                return this.author;
            }
            return null;
        }

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
