package com.dsc.classroom.services;

import java.util.Optional;

import com.dsc.classroom.models.Comment;
import com.dsc.classroom.models.Discipline;
import com.dsc.classroom.models.User;
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
    
    public Comment getCommentById(Long id) throws Exception {
        Optional<Comment> comment = this.commentRepository.findById(id);
        
        return comment.orElseThrow(() -> new Exception("The comment with id" + id + "not exists!"));
    }

    private void disciplineIsOwner(Comment comment, Long disciplineId) throws Exception {
        if (!comment.getDiscipline().getId().equals(disciplineId)) {
            throw new Exception("The discipline with ID " + disciplineId + "is not owner of comment!");
        }
    }

    private void userIsOwner(Comment comment, String userId) throws Exception {
        if (!comment.getUser().getEmail().equals(userId)) {
            throw new Exception("The user with ID " + userId + "is not owner of comment!");
        }
    }

    public void delete(Comment comment, Discipline discipline, User user) throws Exception {
        disciplineIsOwner(comment, discipline.getId());
        userIsOwner(comment, user.getEmail());

        this.commentRepository.delete(comment);
    }

}