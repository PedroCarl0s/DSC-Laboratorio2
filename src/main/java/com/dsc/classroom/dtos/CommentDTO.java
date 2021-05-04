package com.dsc.classroom.dtos;

import javax.validation.constraints.NotNull;

public class CommentDTO {

    @NotNull
    private String comment;

    public CommentDTO() {

    }

    public CommentDTO(String comment) {
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}