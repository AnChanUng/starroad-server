package com.kb04.starroad.Service;

import com.kb04.starroad.Dto.board.CommentDto;
import com.kb04.starroad.Entity.Board;
import com.kb04.starroad.Entity.Comment;
import com.kb04.starroad.Entity.Member;
import com.kb04.starroad.Repository.BoardRepository;
import com.kb04.starroad.Repository.CommentRepository;
import com.kb04.starroad.Repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public int createComment(String content, int boardNo, int currentUserNo) {
        CommentDto newComment = new CommentDto();

        newComment.setContent(content);
        newComment.setRegdate(new java.util.Date());
        newComment.setMember(memberRepository.findByNo(currentUserNo));
        newComment.setBoard(boardRepository.findByNo(boardNo));;

        Comment comment = newComment.toEntity();

        commentRepository.save(comment);
        return comment.getBoard().getNo();
    }

    public CommentDto getCommentById(int commentNo) {
        Comment comment = commentRepository.findById(commentNo).orElseThrow(() -> new IllegalArgumentException("해당 댓글이 없습니다"));
        return comment.toCommentDto();
    }
    public CommentDto getUpdateComment(int no) {
        Optional<Comment> commentOptional = commentRepository.findByNo(no);
        if (commentOptional.isPresent()) {
            return commentOptional.get().toCommentDto();
        } else {
            throw new NoSuchElementException("No Comment found with given id");
        }
    }
    @Transactional
    public void updateComment(CommentDto commentDto) {

        Optional<Comment> optionalcomment = commentRepository.findByNo(commentDto.getNo());

        Comment comment2 = optionalcomment.get();
        comment2.update(commentDto.getContent());
    }

    @Transactional
    public int deleteComment(int commentNo) {
        Optional<Comment> comment = commentRepository.findById(commentNo);
        int boardNo = comment.get().getBoard().getNo();
        Board board = boardRepository.findByNo(boardNo);
        board.setCommentNum(board.getCommentNum() - 1);
        boardRepository.save(board);
        commentRepository.deleteByNo(commentNo);
        return boardNo;
    }

    public Optional<Comment> findByNo(int commentNo) {
        return commentRepository.findById(commentNo);
    }

    @Transactional
    public void increaseCommentCount(int boardNo) {
        Optional<Board> optionalBoard = boardRepository.findById(boardNo);
        if (optionalBoard != null) {
            Board board = optionalBoard.get();
            int currentCount = board.getCommentNum();
            board.setCommentNum(currentCount + 1);
            System.out.println("board.getCommentNum(): " + board.getCommentNum());
            boardRepository.save(board);
            System.out.println("board.getCommentNum(): " + board.getCommentNum());
        }
    }

    public boolean canUpdate(int no, String currentUserId) {
        Optional<Comment> commentOptional = findByNo(no);

        if(commentOptional.isPresent()) {
            Comment comment = commentOptional.get();
            Member writer = comment.getMember();

            if (writer != null) {
                String writerId = writer.getId();
                return currentUserId != null && currentUserId.equals(writerId);
            }
        }
        return false;
    }

    public boolean canDelete(Integer no, String currentUserId) {
        Optional<Comment> commentOptional = findByNo(no);

        if(commentOptional.isPresent()) {
            Comment comment = commentOptional.get();
            Member writer = comment.getMember();

            return writer.getId().equals(currentUserId);
        }
        return false;
    }
}