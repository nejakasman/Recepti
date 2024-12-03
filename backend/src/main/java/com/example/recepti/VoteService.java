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

    public Vote voteForRecipe(int kuharskiIzzivId, int receptId, int uporabnikId, int points) {
        KuharskiIzziv challenge = kuharskiIzzivRepository.findById(kuharskiIzzivId)
                .orElseThrow(() -> new RuntimeException("Izziv z ID " + kuharskiIzzivId + " ni najden"));

        Recept recipe = receptRepository.findById(receptId)
                .orElseThrow(() -> new RuntimeException("Recept z ID " + receptId + " ni najden"));

        Uporabnik uporabnik = uporabnikRepository.findById(uporabnikId)
                .orElseThrow(() -> new RuntimeException("Uporabnik z ID " + uporabnikId + " ni najden"));

        Optional<Vote> existingVote = voteRepository.findByUporabnikAndChallengeAndRecept(uporabnik, challenge, recipe);

        if (existingVote.isPresent()) {
            throw new RuntimeException("Ste že glasovali za ta recept v tem izzivu.");
        }

        Vote vote = new Vote();
        vote.setUporabnik(uporabnik);
        vote.setRecept(recipe);
        vote.setChallenge(challenge);
        vote.setGlasovanje(points);

        return voteRepository.save(vote);
    }

    public List<Vote> getVotesForChallenge(int kuharskiIzzivId) {
        KuharskiIzziv challenge = kuharskiIzzivRepository.findById(kuharskiIzzivId)
                .orElseThrow(() -> new RuntimeException("Izziv z ID " + kuharskiIzzivId + " ni najden"));

        return voteRepository.findByChallenge(challenge);
    }

    // najboljši recept
    public Recept getBestRecipeForChallenge(int kuharskiIzzivId) {
        KuharskiIzziv challenge = kuharskiIzzivRepository.findById(kuharskiIzzivId)
                .orElseThrow(() -> new RuntimeException("Izziv z ID " + kuharskiIzzivId + " ni najden"));

        // recept z največ točkami
        return voteRepository.findByChallenge(challenge).stream()
                .collect(Collectors.groupingBy(Vote::getRecept, Collectors.summingInt(Vote::getGlasovanje)))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElseThrow(() -> new RuntimeException("Ni glasov za ta izziv."));
    }
}
