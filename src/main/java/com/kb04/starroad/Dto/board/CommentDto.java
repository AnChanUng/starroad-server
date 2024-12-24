package com.kb04.starroad.Dto.board;
import com.kb04.starroad.Entity.Board;
import com.kb04.starroad.Entity.Comment;
import com.kb04.starroad.Entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import java.util.Date;
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {

    private int no;
    private Board board;
    private Member member;
    private Date regdate;
    private String content;

    public Comment toEntity() {
        return Comment.builder()
                .regdate(regdate)
                .board(board)
                .member(member)
                .content(content)
                .status('Y')
                .build();
    }

    public static CommentDto from(Comment comment) {
        return CommentDto.builder()
                .no(comment.getNo())
                .board(comment.getBoard())
                .member(comment.getMember())
                .regdate(comment.getRegdate())
                .content(comment.getContent())
                .build();
    }
}