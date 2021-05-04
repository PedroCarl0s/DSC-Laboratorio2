package com.dsc.classroom.services;

import com.dsc.classroom.dtos.CommentDTO;
import com.dsc.classroom.models.Comment;
import com.dsc.classroom.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    private CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public Comment addComment(Comment comment) {
        return this.commentRepository.save(comment);
    }
}