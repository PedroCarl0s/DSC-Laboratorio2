package com.dsc.classroom.repositories;

import com.dsc.classroom.models.Discipline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DisciplineRepository extends JpaRepository<Discipline, Long> {

    List<Discipline> findByOrderByNoteDesc();
    List<Discipline> findByOrderByLikesDesc();
}
