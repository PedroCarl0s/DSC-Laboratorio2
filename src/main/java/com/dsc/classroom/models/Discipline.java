package com.dsc.classroom.models;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "disciplines")
public class Discipline {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    private double note;

    @OneToMany(mappedBy = "discipline", cascade = CascadeType.ALL)
    List<Comment> comments;

    @OneToMany(mappedBy = "discipline", cascade = CascadeType.ALL)
    private List<DisciplineLike> likes;

    public Discipline() {
    }

    public Discipline(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getNote() {
        return note;
    }

    public void setNote(double note) {
        this.note = note;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<DisciplineLike> getLikes() {
        return likes;
    }

    public void setLikes(List<DisciplineLike> likes) {
        this.likes = likes;
    }
    
}