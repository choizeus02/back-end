package com.example.modemate.Repository;

import com.example.modemate.domain.Counselor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CounselorRepository extends JpaRepository<Counselor, Long> {

    @Query("SELECT c FROM Counselor c WHERE c.name LIKE %:query% OR c.category LIKE %:query%")
    List<Counselor> findByNameOrCategoryContaining(@Param("query") String query);

    Counselor findByName(@Param("name") String name);
}
