package com.dsc.classroom.controllers;

import com.dsc.classroom.dtos.CommentDTO;
import com.dsc.classroom.exceptions.DisciplineNotExistsException;
import com.dsc.classroom.models.Comment;
import com.dsc.classroom.models.Discipline;
import com.dsc.classroom.services.CommentService;
import com.dsc.classroom.services.DisciplineService;
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

    private DisciplineService disciplineService;
    private CommentService commentService;

    @Autowired
    public DisciplineController(DisciplineService disciplineService, CommentService commentService) {
        this.disciplineService = disciplineService;
        this.commentService = commentService;
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

    @PostMapping("/{id}/likes")
    public ResponseEntity<Discipline> addLike(@Valid @PathVariable("id") Long id) {
        try {
            return new ResponseEntity<>(disciplineService.addLike(id), HttpStatus.OK);
        } catch (DisciplineNotExistsException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{id}/comments")
    public ResponseEntity<Discipline> addComment(@Valid @PathVariable("id") Long id, @Valid @RequestBody CommentDTO commentDTO) {
        try {
            Discipline discipline = disciplineService.getDiscipline(id);
            Comment comment = new Comment(commentDTO.getComment());
            comment.setDiscipline(discipline);
            commentService.addComment(comment);

            return new ResponseEntity<>(discipline, HttpStatus.OK);
        } catch (DisciplineNotExistsException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{id}/note")
    public ResponseEntity<Discipline> updateNote(@Valid @PathVariable("id") Long id,
             @Valid @RequestBody ObjectNode json) {
        try {
            double newNote = json.get("note").asDouble();
            return new ResponseEntity<>(disciplineService.updateDisciplineNote(id, newNote), HttpStatus.OK);
        } catch (DisciplineNotExistsException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
