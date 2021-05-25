package com.dsc.classroom.services;

import com.dsc.classroom.dtos.CommentDTO;
import com.dsc.classroom.exceptions.DisciplineNotExistsException;
import com.dsc.classroom.models.Comment;
import com.dsc.classroom.models.Discipline;
import com.dsc.classroom.models.DisciplineLike;
import com.dsc.classroom.models.User;
import com.dsc.classroom.repositories.DisciplineLikeRepository;
import com.dsc.classroom.repositories.DisciplineRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DisciplineService {

    private DisciplineRepository disciplineRepository;
    private DisciplineLikeRepository likeRepository;
    private UserService userService;

    @Autowired
    public DisciplineService(DisciplineRepository disciplineRepository, 
            UserService userService, DisciplineLikeRepository likeRepository) {
        this.disciplineRepository = disciplineRepository;
        this.userService = userService;
        this.likeRepository = likeRepository;
    }

    public Iterable<Discipline> addDisciplines(List<Discipline> disciplines) {
        return this.disciplineRepository.saveAll(disciplines);
    }

    public List<Discipline> getDisciplines() {
        return this.disciplineRepository.findAll();
    }

    public Discipline getDiscipline(Long id) throws DisciplineNotExistsException {
        Optional<Discipline> discipline = this.disciplineRepository.findById(id);

        return discipline.orElseThrow(() ->
                new DisciplineNotExistsException("The discipline with ID " + id + " not exists!"));
    }

    public Discipline addLike(Long disciplineId, String userId) throws DisciplineNotExistsException {
        Discipline discipline = getDiscipline(disciplineId);
        Optional<User> user = this.userService.findByEmail(userId);

        if (user.isPresent()) {
            DisciplineLike like = new DisciplineLike();
            like.setUser(user.get());
            like.setDiscipline(discipline);

            this.likeRepository.save(like);
        }

        return discipline;
    }

    public List<Discipline> getRankingByNotes() {
        return this.disciplineRepository.findByOrderByNoteDesc();
    }

    public List<Discipline> getRankingByLikes() {
        return this.disciplineRepository.findByOrderByLikesDesc();
    }

    private double calculateNote(double olderNote, double newNote) {
        if (olderNote == 0.0) {
            return newNote;
        }
        return (olderNote + newNote) / 2;
    }

    public Discipline updateDisciplineNote(Long id, double newNote) throws DisciplineNotExistsException {
        Discipline discipline = getDiscipline(id);
        double finalNote = calculateNote(discipline.getNote(), newNote);
        discipline.setNote(finalNote);

        return this.disciplineRepository.save(discipline);
    }

    public Discipline addComment(Long id, CommentDTO commentDTO) throws DisciplineNotExistsException {
        Discipline discipline = getDiscipline(id);
        Comment comment = new Comment(commentDTO.getComment());
        discipline.getComments().add(comment);

        return this.disciplineRepository.save(discipline);
    }

}