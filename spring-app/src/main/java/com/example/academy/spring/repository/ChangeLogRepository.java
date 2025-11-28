package com.example.academy.spring.repository;

import com.example.academy.spring.model.ChangeLog;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChangeLogRepository extends JpaRepository<ChangeLog, Long> {
    List<ChangeLog> findTop50ByOrderByCreatedAtDesc();
}
