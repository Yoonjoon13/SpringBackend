package com.example.springbackend.board;

import com.example.springbackend.board.model.BoardDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@CrossOrigin(
        origins = "http://localhost:5173",
        allowedHeaders = "*",
        allowCredentials = "true",
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE}
)
@RequestMapping("/board")
@RestController
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @PostMapping("/create")
    public ResponseEntity<BoardDto.BoardRes> create(@RequestBody BoardDto.BoardReq dto) {
        BoardDto.BoardRes response = boardService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/list")
    public ResponseEntity<List<BoardDto.BoardRes>> list() {
        List<BoardDto.BoardRes> response = boardService.list();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/list/{username}")
    public ResponseEntity<List<BoardDto.BoardRes>> listByUser(@PathVariable String username) {
        List<BoardDto.BoardRes> response = boardService.listByUser(username);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/modify/{idx}")
    public ResponseEntity<BoardDto.BoardRes> modify(@PathVariable Long idx, @RequestBody BoardDto.BoardReq dto) {
        BoardDto.BoardRes response = boardService.modify(idx, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{idx}")
    public ResponseEntity<Void> delete(@PathVariable Long idx, @RequestParam String username) {
        boardService.delete(idx, username);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleBadRequest(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}
