package com.example.recepti;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/glasovi")
public class VoteController {

    @Autowired
    private VoteService voteService;

    @Autowired
    private ReceptRepository receptRepository;

    @Autowired
    private KuharskiIzzivRepository kuharskiIzzivRepository;

    @PostMapping("/glasuj")
    public String glasuj(@RequestParam int uporabnikId, @RequestParam int receptId, @RequestParam int izzivId) {
        Uporabnik uporabnik = new Uporabnik();
        uporabnik.setId(uporabnikId);  // Simulate retrieval; replace with actual fetching logic

        Recept recept = receptRepository.findById(receptId)
                .orElseThrow(() -> new IllegalArgumentException("Recept ne obstaja!"));

        KuharskiIzziv izziv = kuharskiIzzivRepository.findById(izzivId)
                .orElseThrow(() -> new IllegalArgumentException("Kuharski izziv ne obstaja!"));

        voteService.glasuj(uporabnik, recept, izziv);
        return "Glas uspešno dodan!";
    }

    @DeleteMapping("/odstrani")
    public String odstraniGlas(@RequestParam int uporabnikId, @RequestParam int receptId, @RequestParam int izzivId) {
        Uporabnik uporabnik = new Uporabnik();
        uporabnik.setId(uporabnikId); // Simulate retrieval; replace with actual fetching logic

        Recept recept = receptRepository.findById(receptId)
                .orElseThrow(() -> new IllegalArgumentException("Recept ne obstaja!"));

        KuharskiIzziv izziv = kuharskiIzzivRepository.findById(izzivId)
                .orElseThrow(() -> new IllegalArgumentException("Kuharski izziv ne obstaja!"));

        voteService.odstraniGlas(uporabnik, recept, izziv);
        return "Glas uspešno odstranjen!";
    }

    @GetMapping("/zmagovalec")
    public Recept zmagovalec(@RequestParam int izzivId) {
        KuharskiIzziv izziv = kuharskiIzzivRepository.findById(izzivId)
                .orElseThrow(() -> new IllegalArgumentException("Kuharski izziv ne obstaja!"));

        return izziv.izracunajZmagovalca(voteService);
    }

}
