package com.example.recepti;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Integer> {
    List<Vote> findByKuharskiIzziv(KuharskiIzziv kuharskiIzziv);
    int countByReceptAndKuharskiIzziv(Recept recept, KuharskiIzziv kuharskiIzziv);
    boolean existsByUporabnikAndReceptAndKuharskiIzziv(Uporabnik uporabnik, Recept recept, KuharskiIzziv kuharskiIzziv);

    Optional<Vote> findByUporabnikAndReceptAndKuharskiIzziv(Uporabnik uporabnik, Recept recept, KuharskiIzziv kuharskiIzziv);
}
