package com.example.modemate.Repository;

import com.example.modemate.domain.KeyWord;
import com.example.modemate.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KeyWordRepository extends JpaRepository<KeyWord, Long> {
}
