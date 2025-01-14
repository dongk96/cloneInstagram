package com.example.cloneinstagram.love.repository;

import com.example.cloneinstagram.love.entity.CommentLove;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentLoveRepository extends JpaRepository<CommentLove, Long> {
    void deleteByCommentIdAndMemberId(Long commentId, Long memberId);

    @Query("select case when (count(cl) > 0) then true else false end from CommentLove cl where cl.comment.id = :commentId and cl.member.id = :memberId")
    boolean findCommentLoveCheck(@Param("commentId") Long commentId, @Param("memberId") Long memberId);
}
