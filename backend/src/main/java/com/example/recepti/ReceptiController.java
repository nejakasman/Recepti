package com.example.recepti;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@CrossOrigin
@RestController
@RequestMapping("/api/recepti")
public class ReceptiController {

    Logger logger = Logger.getLogger(ReceptiController.class.getName());


    @Autowired
    ReceptRepository repository;

    @GetMapping("/recepti")
    public Iterable<Recept> getAllRecept(){
        logger.info("Getting all Recept data");
        logger.info("First recept in list: " + repository.findAll().iterator().next());

        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Recept> getReceptById(@PathVariable("id") int id) {
        logger.info("Get recept by id: " + id);
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/kategorije")
    public ResponseEntity<List<String>> getKategorije() {
        logger.info("Pridobivamo vse kategorije receptov.");
        List<String> kategorije = Arrays.stream(Kategorija.values())
                .map(Enum::name)
                .toList();
        return ResponseEntity.ok(kategorije);
    }

    @PostMapping("/dodaj")
    public ResponseEntity<Recept> createRecept(@RequestBody Recept recept) {
        logger.info("Dodajamo recept: " + recept.getIme());
        Recept newRecept = new Recept(
                recept.getIme(),
                recept.getNavodila(),
                recept.getSestavine(),
                recept.getOpis(),
                recept.getPorcije(),
                recept.getCasPriprave(),
                recept.getKategorija()
        );
        Recept savedRecept = repository.save(newRecept);
        return ResponseEntity.ok(savedRecept);
    }

    /*
    @DeleteMapping("/recepti/{id}")
    public ResponseEntity<String> deleteRecept(@PathVariable("id") int id) {
        logger.info("Deleting recept with id: " + id);
        // Preverimo, če recept z določenim ID-jem obstaja
        Optional<Recept> recept = repository.findById(id);
        if (!recept.isPresent()) {
            // Če recept ne obstaja, vrnemo 404
            return ResponseEntity
                    .status(404)
                    .body("Recept z ID " + id + " ni bil najden.");
            /*return ResponseEntity.notFound().build();
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build(); // Vrne 204 No Content
    } */

    @GetMapping("/search")
    public List<Recept> searchRecept(@RequestParam String ime) {
        logger.info("Iščemo recept z imenom, ki vključuje: " + ime);
        return repository.findByImeContaining(ime);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Recept> updateRecept(@PathVariable("id") int id, @RequestBody Recept updatedRecept) {
        return repository.findById(id)
                .map(recept -> {
                    logger.info("Posodabljamo recept z ID: " + id);
                    recept.setIme(updatedRecept.getIme());
                    recept.setSestavine(updatedRecept.getSestavine());
                    recept.setNavodila(updatedRecept.getNavodila());
                    recept.setCasPriprave(updatedRecept.getCasPriprave());
                    recept.setPorcije(updatedRecept.getPorcije());
                    recept.setOpis(updatedRecept.getOpis());
                    recept.setKategorija(updatedRecept.getKategorija());
                    return ResponseEntity.ok(repository.save(recept));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/sestavine")
    public ResponseEntity<?> getPreracunaneSestavine(@PathVariable("id") int id, @RequestParam int porcije) {
        if (porcije <= 0) {
            return ResponseEntity.badRequest().body("Parameter 'porcije' mora biti pozitivno celo število.");
        }

        return repository.findById(id)
                .map(recept -> {
                    List<String> preracunaneSestavine = recept.getSestavine().stream()
                            .map(sestavina -> {
                                String[] parts = sestavina.split(" ", 2);
                                if (parts.length > 1) {
                                    try {
                                        double originalKolicina = Double.parseDouble(parts[0].replaceAll("[^\\d.]", ""));
                                        String novaSestavina = (originalKolicina * porcije) + " " + parts[1];
                                        return novaSestavina;
                                    } catch (NumberFormatException e) {
                                        return sestavina;
                                    }
                                }
                                return sestavina;
                            })
                            .toList();
                    return ResponseEntity.ok(preracunaneSestavine);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }





}
