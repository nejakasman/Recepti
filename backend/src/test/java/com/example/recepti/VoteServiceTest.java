//Testiramo funkcijo glasuj, ki zagotovi, da uporabnik ne more glasovati dvakrat za isti recept v istem izzivu.
package com.example.recepti;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class VoteServiceTest {

    @Mock
    private VoteRepository voteRepository;

    @InjectMocks
    private VoteService voteService;

    private Uporabnik uporabnik;
    private Recept recept;
    private KuharskiIzziv kuharskiIzziv;

    @BeforeEach
    public void setUp() {
        // Inicializacija mock objektov
        MockitoAnnotations.openMocks(this);

        uporabnik = new Uporabnik();
        uporabnik.setId(1); // Nastavimo ID uporabnika

        recept = new Recept();
        recept.setId(2); // Nastavimo ID recepta

        kuharskiIzziv = new KuharskiIzziv();
        kuharskiIzziv.setId(3); // Nastavimo ID izziva
    }

    @Test
    public void testGlasuj_Success() {
        // Simuliraj, da glas še ni oddan
        when(voteRepository.existsByUporabnikAndReceptAndKuharskiIzziv(uporabnik, recept, kuharskiIzziv)).thenReturn(false);

        // Pokličemo glasovanje
        voteService.glasuj(uporabnik, recept, kuharskiIzziv);

        // Preverimo, da je metoda save() klicana, saj je glasovanje uspešno
        verify(voteRepository, times(1)).save(any(Vote.class));
    }

    @Test
    public void testGlasuj_AlreadyVoted() {
        // Simuliraj, da je uporabnik že glasoval za recept v tem izzivu
        when(voteRepository.existsByUporabnikAndReceptAndKuharskiIzziv(uporabnik, recept, kuharskiIzziv)).thenReturn(true);

        // Poskusimo glasovati znova in preverimo, da se vrže napaka
        assertThrows(IllegalStateException.class, () -> voteService.glasuj(uporabnik, recept, kuharskiIzziv));
    }
}
