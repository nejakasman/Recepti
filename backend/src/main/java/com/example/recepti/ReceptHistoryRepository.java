package com.example.recepti;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReceptHistoryRepository extends JpaRepository<ReceptHistory, Integer> {
    List<ReceptHistory> findByReceptId(int receptId);
}

