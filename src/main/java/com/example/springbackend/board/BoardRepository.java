package com.example.springbackend.board;

import com.example.springbackend.board.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    @Query("SELECT b FROM Board b JOIN FETCH b.user u WHERE u.name = :username")
    List<Board> findAllByUser_Name(@Param("username") String username);

    @Query("SELECT b FROM Board b LEFT JOIN FETCH b.user")
    List<Board> findAllWithUser();
}
