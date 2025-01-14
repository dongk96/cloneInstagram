package com.example.cloneinstagram.love.service;

import com.example.cloneinstagram.board.entity.Board;
import com.example.cloneinstagram.comment.entity.Comment;
import com.example.cloneinstagram.comment.repository.CommentRepository;
import com.example.cloneinstagram.common.ResponseMsgDto;
import com.example.cloneinstagram.exception.CustomException;
import com.example.cloneinstagram.exception.ErrorCode;
import com.example.cloneinstagram.love.entity.BoardLove;
import com.example.cloneinstagram.love.entity.CommentLove;
import com.example.cloneinstagram.love.repository.CommentLoveRepository;
import com.example.cloneinstagram.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentLoveService {

    private final CommentRepository commentRepository;
    private final CommentLoveRepository commentLoveRepository;

    // 댓글 좋아요
    @Transactional
    public ResponseMsgDto<Void> commentLove(Long id, Member member) {
        if (commentLoveRepository.findCommentLoveCheck(id, member.getId())) {
            commentLoveRepository.deleteByCommentIdAndMemberId(id, member.getId());
            return ResponseMsgDto.setSuccess(HttpStatus.OK.value(), "좋아요 취소 성공", null);
        } else {
            Comment comment = findCommentById(id);
            CommentLove commentLove = new CommentLove(comment, member);
            commentLoveRepository.save(commentLove);
            return ResponseMsgDto.setSuccess(HttpStatus.OK.value(), "좋아요 등록 성공", null);
        }
    }

    // 댓글 존재 확인 메서드
    public Comment findCommentById(Long id){
        return commentRepository.findById(id).orElseThrow(
                () -> new CustomException(ErrorCode.COMMENT_NOT_FOUND)
        );
    }
}
