package com.example.recepti;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KuharskiIzzivRepository extends JpaRepository<KuharskiIzziv, Integer> {
    List<KuharskiIzziv> findByNazivContaining(String naziv);
}