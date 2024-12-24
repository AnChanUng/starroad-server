package com.kb04.starroad.Dto.board;

import com.kb04.starroad.Entity.Board;
import lombok.*;
import java.util.Date;
import java.util.List;

@Builder
@Getter
@AllArgsConstructor
public class BoardResponseDto {
    private int no;
    private String title;
    private Date regdate;
    private String content;
    private int likes;
    private byte[] image;
    private int commentNum;
    private String type;
    private String detailType;
    private String imageBase64;
    private String memberId;
    private List<CommentDto> comments;

    public static BoardResponseDto from(Board board) {
        return BoardResponseDto.builder()
                .no(board.getNo())
                .title(board.getTitle())
                .regdate(board.getRegdate())
                .content(board.getContent())
                .likes(board.getLikes())
                .image(board.getImage())
                .commentNum(board.getCommentNum())
                .type(board.getType())
                .detailType(board.getDetailType())
                .imageBase64(board.toBoardResponseDto().getImageBase64())
                .memberId(board.getMember().getId())
                .comments(board.toBoardResponseDto().getComments())
                .build();
    }
}
