package com.example.springbackend.board;

import com.example.springbackend.board.model.Board;
import com.example.springbackend.board.model.BoardDto;
import com.example.springbackend.user.UserRepository;
import com.example.springbackend.user.userModel.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    public BoardDto.BoardRes create(BoardDto.BoardReq request) {
        if (request == null) {
            request = new BoardDto.BoardReq();
        }

        User user = resolveUser(request);

        Board board = request.toEntity(user);
        Board saved = boardRepository.save(board);
        return BoardDto.BoardRes.from(saved);
    }

    public List<BoardDto.BoardRes> list() {
        return boardRepository.findAllWithUser().stream()
                .map(BoardDto.BoardRes::from)
                .toList();
    }

    public List<BoardDto.BoardRes> listByUser(String username) {
        return boardRepository.findAllByUser_Name(username).stream()
                .map(BoardDto.BoardRes::from)
                .toList();
    }

    public BoardDto.BoardRes modify(Long idx, BoardDto.BoardReq request) {
        Board board = boardRepository.findById(idx)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));

        if (board.getUser() == null) {
            throw new IllegalArgumentException("작성자가 없는 게시글은 수정할 수 없습니다.");
        }

        String writer = request.getAnyUsername();
        if (writer == null || !board.getUser().getName().equals(writer)) {
            throw new IllegalArgumentException("작성자만 수정할 수 있습니다.");
        }

        if (request.getTitle() != null) {
            board.setTitle(request.getTitle());
        }
        if (request.getContents() != null) {
            board.setContents(request.getContents());
        }

        Board saved = boardRepository.save(board);
        return BoardDto.BoardRes.from(saved);
    }

    public void delete(Long idx, String username) {
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("삭제는 작성자(username) 기준으로만 가능합니다.");
        }

        Board board = boardRepository.findById(idx)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));

        if (board.getUser() == null || !board.getUser().getName().equals(username)) {
            throw new IllegalArgumentException("작성자만 삭제할 수 있습니다.");
        }

        boardRepository.deleteById(idx);
    }

    private User resolveUser(BoardDto.BoardReq request) {
        String username = request.getAnyUsername();
        if (username != null) {
            return userRepository.findByName(username)
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
        }

        if (request.getEmail() != null && !request.getEmail().isBlank()) {
            return userRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이메일입니다."));
        }

        if (request.getUserId() != null) {
            return userRepository.findById(request.getUserId())
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
        }

        return null;
    }
}
