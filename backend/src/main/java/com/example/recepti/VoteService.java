package com.example.recepti;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoteService {

    @Autowired
    private VoteRepository glasRepository;

    public void glasuj(Uporabnik uporabnik, Recept recept, KuharskiIzziv kuharskiIzziv) {
        if (glasRepository.existsByUporabnikAndReceptAndKuharskiIzziv(uporabnik, recept, kuharskiIzziv)) {
            throw new IllegalStateException("Uporabnik je že glasoval za ta recept v tem izzivu!");
        }
        Vote glas = new Vote(uporabnik, recept, kuharskiIzziv);
        glasRepository.save(glas);
    }
    public void odstraniGlas(Uporabnik uporabnik, Recept recept, KuharskiIzziv kuharskiIzziv) {
        Vote glas = glasRepository.findByUporabnikAndReceptAndKuharskiIzziv(uporabnik, recept, kuharskiIzziv)
                .orElseThrow(() -> new IllegalStateException("Glas ne obstaja."));
        glasRepository.delete(glas);
    }

    public int prestejGlasove(Recept recept, KuharskiIzziv kuharskiIzziv) {
        return glasRepository.countByReceptAndKuharskiIzziv(recept, kuharskiIzziv);
    }

    public List<Vote> pridobiGlasove(KuharskiIzziv kuharskiIzziv) {
        return glasRepository.findByKuharskiIzziv(kuharskiIzziv);
    }

}
