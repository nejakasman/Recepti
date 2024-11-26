package com.example.recepti;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class VoteService {

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private KuharskiIzzivRepository kuharskiIzzivRepository;

    @Autowired
    private ReceptRepository receptRepository;

    @Autowired
    private UporabnikRepository uporabnikRepository;

    // glasovnaje za recept na izzivu
    public Vote voteForRecipe(Long KuharskiIzzivId, Long receptId, Long uporabnikId, int points) {
        KuharskiIzziv challenge = kuharskiIzzivRepository.findById(KuharskiIzzivId)
                .orElseThrow(() -> new RuntimeException("Challenge not found"));

        Recept recipe = receptRepository.findById(receptId)
                .orElseThrow(() -> new RuntimeException("Recipe not found"));

       Uporabnik uporabnik = uporabnikRepository.findById(uporabnikId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Check if the user has already voted for this recipe in this challenge
        Optional<Vote> existingVote = voteRepository.findByUporabnikAndChallengeAndRecept(uporabnik, challenge, recipe)

        if (existingVote.isPresent()) {
            throw new RuntimeException("Ste že glasovali.");
        }

        // Create a new vote
        Vote vote = new Vote();
        vote.setUporabnik(uporabnik);
        vote.setRecept(recipe);
        vote.setChallenge(challenge);
        vote.setGlasovanje(points);

        return voteRepository.save(vote);
    }

    // Get rezultate glasovanja za izziv
    public List<Vote> getVotesForChallenge(Long KuharskiIzzivId) {
        KuharskiIzziv challenge = kuharskiIzzivRepository.findById(KuharskiIzzivId)
                .orElseThrow(() -> new RuntimeException("Challenge not found"));

        return voteRepository.findByChallenge(challenge);
    }

    // Get najboljši recept na podlagi glasovanja-točk
    public Recept getBestRecipeForChallenge(Long KuharskiIzzivId) {
        KuharskiIzziv challenge = kuharskiIzzivRepository.findById(KuharskiIzzivId)
                .orElseThrow(() -> new RuntimeException("Izziv ni najden"));

        // Get the recipe with the most points
        return voteRepository.findByChallenge(challenge).stream()
                .collect(Collectors.groupingBy(vote -> vote.getRecept(), Collectors.summingInt(Vote::getGlasovanje)))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElseThrow(() -> new RuntimeException("Ni najdeno glasov"));
    }
}
