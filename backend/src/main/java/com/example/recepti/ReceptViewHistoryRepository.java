package com.example.recepti;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReceptViewHistoryRepository extends JpaRepository<ReceptViewHistory, Integer> {
    List<ReceptViewHistory> findByReceptId(int receptId);
}

