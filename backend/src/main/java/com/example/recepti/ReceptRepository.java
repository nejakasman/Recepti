package com.example.recepti;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface ReceptRepository extends CrudRepository<Recept, Integer> {
    List<Recept> findByImeContaining(String ime);
}
