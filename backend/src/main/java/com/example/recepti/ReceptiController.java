package com.example.recepti;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@CrossOrigin
@RestController
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

    @GetMapping("/recepti/{id}")
    public ResponseEntity<Recept> getReceptById(@PathVariable("id") int id) {
        logger.info("Get recept by id: " + id);
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @PostMapping("/recepti/dodaj")
    public Recept postRecept(@RequestBody Recept recept) {
        logger.info("Post recept " + recept);
        Recept newRecept = new Recept(recept.getIme(), recept.getSestavine(), recept.getNavodila(), recept.getOpis());
        repository.save(newRecept);
        return newRecept;
    }

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
            /*return ResponseEntity.notFound().build();*/
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build(); // Vrne 204 No Content
    }

    @GetMapping("/recepti/search")
    public List<Recept> searchRecept(@RequestParam String ime) {
        logger.info("Searching for recept with name containing: " + ime);
        return repository.findByImeContaining(ime);
    }

    @PutMapping("/recepti/update/{id}")
    public Recept updateRecept(@PathVariable("id") int id, @RequestBody Recept updatedRecept) {
        return repository.findById(id)
                .map(recept -> {
                    recept.setIme(updatedRecept.getIme());
                    recept.setSestavine(updatedRecept.getSestavine());
                    recept.setNavodila(updatedRecept.getNavodila());
                    return repository.save(recept);
                })
                .orElseThrow(() -> new RuntimeException("Recept with id " + id + " not found"));
    }



}
