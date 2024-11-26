package com.example.recepti;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/uporabnik/kuharski-izziv")
public class KuharskiIzzivPrijavaController {

    @Autowired
    private KuharskiIzzivRepository kuharskiIzzivRepository;

    @Autowired
    private ReceptRepository receptRepository;

    @Autowired
    private UporabnikRepository uporabnikRepository;

    // Prijava na kuharski izziv (uporabnik odda recept)
    @PostMapping("/prijava/{izzivId}")
    public ResponseEntity<String> prijavaNaIzziv(@PathVariable int izzivId, @RequestBody Recept recept, @RequestHeader("uporabnikId") Integer uporabnikId) {
        // Preveri, ali izziv obstaja
        KuharskiIzziv kuharskiIzziv = kuharskiIzzivRepository.findById(izzivId).orElse(null);
        if (kuharskiIzziv == null) {
            return ResponseEntity.status(404).body("Kuharski izziv ni najden");
        }

        // Preveri, ali uporabnik obstaja
        Uporabnik uporabnik = uporabnikRepository.findById(uporabnikId).orElse(null);
        if (uporabnik == null) {
            return ResponseEntity.status(404).body("Uporabnik ni najden");
        }

        // Nastavi uporabnika in izziv za recept
        recept.setUporabnik(uporabnik);
        recept.setKuharskiIzziv(kuharskiIzziv);

        // Shranjevanje recepta v bazo
        receptRepository.save(recept);
        return ResponseEntity.ok("Recept uspešno oddan na kuharski izziv");
    }

    // Seznam vseh kuharskih izzivov
    @GetMapping
    public List<KuharskiIzziv> seznamIzzivov() {
        return kuharskiIzzivRepository.findAll();
    }
}
