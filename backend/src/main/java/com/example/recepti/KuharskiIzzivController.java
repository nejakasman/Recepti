package com.example.recepti;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@CrossOrigin
@RestController
@RequestMapping("/kuharski-izziv")
public class KuharskiIzzivController {

    Logger logger = Logger.getLogger(KuharskiIzzivController.class.getName());

    @Autowired
    private KuharskiIzzivRepository kuharskiIzzivRepository;
    @Autowired
    private ReceptRepository receptRepository;

    // Prikaz vseh kuharskih izzivov
    @GetMapping
    public List<KuharskiIzziv> getAllIzzivi() {
        logger.info("Pridobivanje podatkov za vse KuharskeIzzive");
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
        logger.info("Dodajanje Kuharskega Izziva: " + kuharskiIzziv);

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
            updatedIzziv.setTrajanjeOd(kuharskiIzziv.getTrajanjeOd());
            kuharskiIzzivRepository.save(updatedIzziv);
            return ResponseEntity.ok("Kuharski izziv uspešno posodobljen");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Brisanje kuharskega izziva
    @DeleteMapping("/{id}")
    public ResponseEntity<String> izbrisiIzziv(@PathVariable("id") int id) {
        logger.info("Brisanje Kuharskega Izziva z id: " + id);

        Optional<KuharskiIzziv> existingIzziv = kuharskiIzzivRepository.findById(id);
        if (existingIzziv.isPresent()) {
            kuharskiIzzivRepository.deleteById(id);
            return ResponseEntity.ok("Kuharski izziv uspešno zbrisan");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Iskanje kuharskih izzivov po imenu
    @GetMapping("/search")
    public List<KuharskiIzziv> searchIzziv(@RequestParam String naziv) {
        logger.info("Iskanje Kuharskega Izziva z imenom: " + naziv);
        return kuharskiIzzivRepository.findByNazivContaining(naziv);
    }

    @PostMapping("/{izzivId}/dodaj-recept")
    public ResponseEntity<String> dodajReceptDoIzziva(
            @PathVariable int izzivId,
            @RequestParam int receptId) {

        try {
            // Poiščemo kuharski izziv
            Optional<KuharskiIzziv> optionalIzziv = kuharskiIzzivRepository.findById(izzivId);
            if (optionalIzziv.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Kuharski izziv z ID " + izzivId + " ni bil najden.");
            }

            // Poiščemo recept
            Optional<Recept> optionalRecept = receptRepository.findById(receptId);
            if (optionalRecept.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Recept z ID " + receptId + " ni bil najden.");
            }

            // Povežemo recept z izzivom
            Recept recept = optionalRecept.get();
            KuharskiIzziv izziv = optionalIzziv.get();
            recept.setKuharskiIzziv(izziv);
            receptRepository.save(recept); // Posodobimo recept

            return ResponseEntity.ok("Recept uspešno dodan k izzivu.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Napaka: " + e.getMessage());
        }
    }

}


