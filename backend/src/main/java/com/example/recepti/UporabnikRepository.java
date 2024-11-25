package com.example.recepti;

import org.springframework.data.repository.CrudRepository;

public interface UporabnikRepository extends CrudRepository<Uporabnik, Integer> {
    Uporabnik findByUporabniskoIme(String uporabniskoIme);
    Uporabnik findByEmail(String email);

}
