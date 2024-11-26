package com.example.recepti;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@CrossOrigin
@RestController
@RequestMapping("/kuharski-izziv")  // Prefiks za vse URL-je v tem kontrolerju
public class KuharskiIzzivController {

    Logger logger = Logger.getLogger(KuharskiIzzivController.class.getName());

    @Autowired
    private KuharskiIzzivRepository kuharskiIzzivRepository;

    // Prikaz vseh kuharskih izzivov
    @GetMapping
    public List<KuharskiIzziv> getAllIzzivi() {
        logger.info("Getting all Kuharski Izziv data");
        return kuharskiIzzivRepository.findAll();
    }

    // Prikaz kuharskega izziva po ID-ju
    @GetMapping("/{id}")
    public ResponseEntity<KuharskiIzziv> getIzzivById(@PathVariable("id") int id) {
        logger.info("Get Kuharski Izziv by id: " + id);
        Optional<KuharskiIzziv> izziv = kuharskiIzzivRepository.findById(id);
        return izziv.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Dodajanje novega kuharskega izziva
    @PostMapping
    public ResponseEntity<String> dodajIzziv(@RequestBody KuharskiIzziv kuharskiIzziv) {
        logger.info("Adding new Kuharski Izziv: " + kuharskiIzziv);

        // Preverimo, ali so vsi potrebni podatki prisotni
        if (kuharskiIzziv.getNaziv() == null || kuharskiIzziv.getOpis() == null || kuharskiIzziv.getTrajanjeDo() == null) {
            return ResponseEntity.badRequest().body("Vsi podatki (naziv, opis, trajanje) so obvezni.");
        }

        kuharskiIzzivRepository.save(kuharskiIzziv);
        return ResponseEntity.ok("Kuharski izziv uspešno dodan");
    }

    // Posodabljanje kuharskega izziva
    @PutMapping("/{id}")
    public ResponseEntity<String> urediIzziv(@PathVariable("id") int id, @RequestBody KuharskiIzziv kuharskiIzziv) {
        logger.info("Updating Kuharski Izziv with id: " + id);

        Optional<KuharskiIzziv> existingIzziv = kuharskiIzzivRepository.findById(id);
        if (existingIzziv.isPresent()) {
            KuharskiIzziv updatedIzziv = existingIzziv.get();
            updatedIzziv.setNaziv(kuharskiIzziv.getNaziv());
            updatedIzziv.setOpis(kuharskiIzziv.getOpis());
            updatedIzziv.setTrajanjeDo(kuharskiIzziv.getTrajanjeDo());
            kuharskiIzzivRepository.save(updatedIzziv);
            return ResponseEntity.ok("Kuharski izziv uspešno posodobljen");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Brisanje kuharskega izziva
    @DeleteMapping("/{id}")
    public ResponseEntity<String> izbrisiIzziv(@PathVariable("id") int id) {
        logger.info("Deleting Kuharski Izziv with id: " + id);

        Optional<KuharskiIzziv> existingIzziv = kuharskiIzzivRepository.findById(id);
        if (existingIzziv.isPresent()) {
            kuharskiIzzivRepository.deleteById(id);
            return ResponseEntity.ok("Kuharski izziv uspešno izbrisan");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Iskanje kuharskih izzivov po imenu
    @GetMapping("/search")
    public List<KuharskiIzziv> searchIzziv(@RequestParam String naziv) {
        logger.info("Searching for Kuharski Izziv with name containing: " + naziv);
        return kuharskiIzzivRepository.findByNazivContaining(naziv);
    }
}
