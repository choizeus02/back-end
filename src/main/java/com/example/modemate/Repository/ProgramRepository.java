package com.example.modemate.Repository;

import com.example.modemate.domain.Program;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProgramRepository extends JpaRepository<Program, Long> {
    List<Program> findByNameContainingIgnoreCase(String name);

    @Query("SELECT p FROM Program p LEFT JOIN p.keyWord k WHERE p.name LIKE %:search% OR k.word LIKE %:search%")
    List<Program> findByNameOrKeyWord(@Param("search") String search);

}