package com.dsc.classroom.controllers;

import com.dsc.classroom.dtos.CommentDTO;
import com.dsc.classroom.exceptions.DisciplineNotExistsException;
import com.dsc.classroom.models.Comment;
import com.dsc.classroom.models.Discipline;
import com.dsc.classroom.models.User;
import com.dsc.classroom.services.CommentService;
import com.dsc.classroom.services.DisciplineService;
import com.dsc.classroom.services.JWTService;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/api/disciplines")
public class DisciplineController {

    private JWTService jwtService;
    private DisciplineService disciplineService;
    private CommentService commentService;

    @Autowired
    public DisciplineController(DisciplineService disciplineService, CommentService commentService, JWTService jwtService) {
        this.disciplineService = disciplineService;
        this.commentService = commentService;
        this.jwtService = jwtService;
    }

    @GetMapping
    public ResponseEntity<List<Discipline>> getDisciplines() {
        return new ResponseEntity<>(disciplineService.getDisciplines(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Discipline> getDiscipline(@Valid @PathVariable("id") Long id) {
        try {
            return new ResponseEntity<>(disciplineService.getDiscipline(id), HttpStatus.OK);
        } catch (DisciplineNotExistsException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/ranking/notes")
    public ResponseEntity<List<Discipline>> getRankingByNotes() {
        return new ResponseEntity<>(disciplineService.getRankingByNotes(), HttpStatus.OK);
    }

    @GetMapping("/ranking/likes")
    public ResponseEntity<List<Discipline>> getRankingByLikes() {
        return new ResponseEntity<>(disciplineService.getRankingByLikes(), HttpStatus.OK);
    }

    @PostMapping("/{disciplineId}/likes")
    public ResponseEntity<Discipline> addLike(@RequestHeader("Authorization") String token,
            @Valid @PathVariable("disciplineId") Long disciplineId) throws Exception {
        try {
            User user = this.jwtService.getUserId(token);
            return new ResponseEntity<>(disciplineService.addLike(disciplineId, user.getEmail()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{id}/comments")
    public ResponseEntity<Discipline> addComment(@RequestHeader("Authorization") String token,
            @Valid @PathVariable("id") Long id, 
            @Valid @RequestBody CommentDTO commentDTO) throws Exception {
        try {
            User existingUser = this.jwtService.getUserId(token);
            Discipline discipline = disciplineService.getDiscipline(id);
            Comment comment = new Comment(commentDTO.getComment());
            comment.setDiscipline(discipline);
            comment.setUser(existingUser);
            commentService.addComment(comment);

            return new ResponseEntity<>(discipline, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{disciplineId}/{commentId}")
    public ResponseEntity deleteComment(@RequestHeader("Authorization") String token,
        @PathVariable("disciplineId") Long disciplineId, 
        @PathVariable("commentId") Long commentId) throws Exception {

        try {
            User existingUser = this.jwtService.getUserId(token);
            Discipline existingDiscipline = this.disciplineService.getDiscipline(disciplineId);
            Comment existingComment = this.commentService.getCommentById(commentId);

            this.commentService.delete(existingComment, existingDiscipline, existingUser);
            return new ResponseEntity<>(HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @PatchMapping("/{id}/note")
    public ResponseEntity<Discipline> updateNote(@RequestHeader("Authorization") String token,
            @Valid @PathVariable("id") Long id,
            @Valid @RequestBody ObjectNode json) throws Exception {

        try {
            this.jwtService.getUserId(token);
            double newNote = json.get("note").asDouble();
            return new ResponseEntity<>(disciplineService.updateDisciplineNote(id, newNote), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}