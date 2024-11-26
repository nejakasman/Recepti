package com.example.recepti;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KomentarRepository extends JpaRepository<Komentar, Integer> {
    // Poišče vse komentarje za določen recept
    List<Komentar> findByRecept(Recept recept);

    // Poišče vse komentarje, ki jih je napisal določen uporabnik
    List<Komentar> findByUporabnik(Uporabnik uporabnik);

    // Poišče komentarje za določen recept z oceno večjo od določenega števila
    List<Komentar> findByReceptAndOcenaGreaterThan(Recept recept, float ocena);
}
