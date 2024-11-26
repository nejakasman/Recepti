<<<<<<< Updated upstream
package com.example.recepti;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UporabnikRepository extends JpaRepository<Uporabnik, Integer> {

    // Poišči uporabnika po uporabniškem imenu
    Uporabnik findByUporabniskoIme(String uporabniskoIme);

    // Poišči uporabnika po emailu
    Uporabnik findByEmail(String email);
=======
package com.example.recepti;public interface UporabnikRepository {
    ScopedValue findById(Long uporabnikId);
>>>>>>> Stashed changes
}

