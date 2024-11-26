//package com.example.recepti;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class KuharskiIzzivService {
//
//    @Autowired
//    private KuharskiIzzivRepository kuharskiIzzivRepository;
//
//    @Autowired
//    private ReceptRepository receptRepository;
//
//    // izpise vse izzive
//    public List<KuharskiIzziv> getAllChallenges() {
//        return kuharskiIzzivRepository.findAll();
//    }
//
//    // pridobi izziv po ID
//    public Optional<KuharskiIzziv> getChallengeById(int id) {
//        return kuharskiIzzivRepository.findById(id);
//    }
//
//    // doda izziv
//    public KuharskiIzziv createChallenge(KuharskiIzziv kuharskiIzziv) {
//        return kuharskiIzzivRepository.save(kuharskiIzziv);
//    }
//
//    // update-a izziv
//    public KuharskiIzziv updateChallenge(int id, KuharskiIzziv updatedChallenge) {
//        return kuharskiIzzivRepository.findById(id).map(existingChallenge -> {
//            existingChallenge.setNaziv(updatedChallenge.getNaziv());
//            existingChallenge.setOpis(updatedChallenge.getOpis());
//            existingChallenge.setTrajanjeDo(updatedChallenge.getTrajanjeDo());
//            return kuharskiIzzivRepository.save(existingChallenge);
//        }).orElseThrow(() -> new RuntimeException("Challenge not found"));
//    }
//
//    // zbrise izziv
//    public void deleteChallenge(int id) {
//        kuharskiIzzivRepository.deleteById(id);
//    }
//
//    // doda recept na izziv
//    public KuharskiIzziv addRecipeToChallenge(int challengeId, int recipeId) {
//        KuharskiIzziv challenge = kuharskiIzzivRepository.findById(challengeId)
//                .orElseThrow(() -> new RuntimeException("Challenge not found"));
//
//        Recept recipe = receptRepository.findById(recipeId)
//                .orElseThrow(() -> new RuntimeException("Recipe not found"));
//
//        // acosicacija recepta in izziva
//        recipe.setKuharskiIzziv(challenge);
//        receptRepository.save(recipe);
//
//        //odkomentiraj ko bo recepti///////// challenge.getRecepti().add(recipe);
//
//        return kuharskiIzzivRepository.save(challenge);
//    }
//
//    // Odstrani recept iz izziva
//    public KuharskiIzziv removeRecipeFromChallenge(int challengeId, int recipeId) {
//        KuharskiIzziv challenge = kuharskiIzzivRepository.findById(challengeId)
//                .orElseThrow(() -> new RuntimeException("Challenge not found"));
//
//        Recept recipe = receptRepository.findById(recipeId)
//                .orElseThrow(() -> new RuntimeException("Recipe not found"));
//
//        // zbrise recept-asociacijo
//        //odkomentiraj ko bo recepti/////////challenge.getRecepti().remove(recipe);
//        recipe.setKuharskiIzziv(null);
//
//        // shrani ustvarjene entitete
//        receptRepository.save(recipe);
//        return kuharskiIzzivRepository.save(challenge);
//    }
//}



