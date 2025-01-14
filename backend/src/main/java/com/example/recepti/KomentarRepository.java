package com.example.recepti;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KomentarRepository extends JpaRepository<Komentar, Integer> {
    // Poišče vse komentarje za določen recept
    List<Komentar> findByRecept(Recept recept);

}
