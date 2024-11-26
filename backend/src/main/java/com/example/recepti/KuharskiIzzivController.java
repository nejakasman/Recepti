package com.example.recepti;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/challenges")
public class KuharskiIzzivController {

    @Autowired
    private KuharskiIzzivService kuharskiIzzivService;

    // Get vse izzive
    @GetMapping
    public List<KuharskiIzziv> getAllChallenges() {
        return kuharskiIzzivService.getAllChallenges();
    }

    // Get izziv po ID
    @GetMapping("/{id}")
    public ResponseEntity<KuharskiIzziv> getChallengeById(@PathVariable int id) {
        return kuharskiIzzivService.getChallengeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ustvari nov izziv
    @PostMapping
    public KuharskiIzziv createChallenge(@RequestBody KuharskiIzziv kuharskiIzziv) {
        return kuharskiIzzivService.createChallenge(kuharskiIzziv);
    }

    // posodobi izziv
    @PutMapping("/{id}")
    public ResponseEntity<KuharskiIzziv> updateChallenge(@PathVariable int id, @RequestBody KuharskiIzziv updatedChallenge) {
        try {
            return ResponseEntity.ok(kuharskiIzzivService.updateChallenge(id, updatedChallenge));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // zbrise izziv
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChallenge(@PathVariable int id) {
        kuharskiIzzivService.deleteChallenge(id);
        return ResponseEntity.noContent().build();
    }
}
