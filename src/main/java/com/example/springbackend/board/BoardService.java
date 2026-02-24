package com.example.springbackend.board;

import com.example.springbackend.board.model.Board;
import com.example.springbackend.board.model.BoardDto;
import com.example.springbackend.user.UserRepository;
import com.example.springbackend.user.userModel.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    public BoardDto.BoardRes create(BoardDto.BoardReq request) {
        User user = userRepository.findByName(request.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        Board board = request.toEntity(user);
        Board saved = boardRepository.save(board);
        return BoardDto.BoardRes.from(saved);
    }

    public List<BoardDto.BoardRes> list() {
        return boardRepository.findAll().stream()
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

        if (!board.getUser().getName().equals(request.getUsername())) {
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
        Board board = boardRepository.findById(idx)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));

        if (!board.getUser().getName().equals(username)) {
            throw new IllegalArgumentException("작성자만 삭제할 수 있습니다.");
        }

        boardRepository.deleteById(idx);
    }
}
