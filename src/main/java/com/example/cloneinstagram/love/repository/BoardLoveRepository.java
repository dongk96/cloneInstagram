package com.example.cloneinstagram.love.repository;

import com.example.cloneinstagram.love.entity.BoardLove;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BoardLoveRepository extends JpaRepository<BoardLove, Long> {
    void deleteByBoardIdAndMemberId(Long boardId, Long memberId);

    @Query("select case when (count(bl) > 0) then true else false end from BoardLove bl where bl.board.id = :boardId and bl.member.id = :memberId")
    boolean findBoardLoveCheck(@Param("boardId") Long boardId, @Param("memberId") Long memberId);
}
