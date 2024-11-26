package com.example.recepti;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    Optional<Vote> findByUporabnikAndChallengeAndRecept(Uporabnik uporabnik, KuharskiIzziv challenge, Recept recept);
    List<Vote> findByChallenge(KuharskiIzziv challenge);
}
