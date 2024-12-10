package com.example.recepti;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.*;

import com.example.recepti.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

class DodajReceptKuharskemuIzzivuTest {

    @Mock
    private KuharskiIzzivRepository kuharskiIzzivRepository;

    @Mock
    private ReceptRepository receptRepository;

    @InjectMocks
    private KuharskiIzzivController kuharskiIzzivController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Uspešno dodajanje recepta k izzivu")
    void testDodajReceptDoIzzivaSuccess() {
        int izzivId = 1;
        int receptId = 1;

        KuharskiIzziv izziv = new KuharskiIzziv();
        izziv.setId(izzivId);

        Recept recept = new Recept();
        recept.setId(receptId);

        when(kuharskiIzzivRepository.findById(izzivId)).thenReturn(Optional.of(izziv));
        when(receptRepository.findById(receptId)).thenReturn(Optional.of(recept));

        ResponseEntity<String> response = kuharskiIzzivController.dodajReceptDoIzziva(izzivId, receptId);

        assertEquals(OK, response.getStatusCode());
        assertEquals("Recept uspešno dodan k izzivu.", response.getBody());
        verify(receptRepository).save(recept);
        assertEquals(izziv, recept.getKuharskiIzziv());
    }

    @Test
    @DisplayName("Dodajanje recepta k neobstoječemu izzivu")
    void testDodajReceptDoIzzivaIzzivNotFound() {
        int izzivId = 1;
        int receptId = 1;

        when(kuharskiIzzivRepository.findById(izzivId)).thenReturn(Optional.empty());

        ResponseEntity<String> response = kuharskiIzzivController.dodajReceptDoIzziva(izzivId, receptId);

        assertEquals(NOT_FOUND, response.getStatusCode());
        assertEquals("Kuharski izziv z ID 1 ni bil najden.", response.getBody());
        verify(receptRepository, never()).findById(any());
        verify(receptRepository, never()).save(any());
    }

    @Test
    @DisplayName("Dodajanje recepta k neobstoječemu receptu")
    void testDodajReceptDoIzzivaReceptNotFound() {
        int izzivId = 1;
        int receptId = 1;

        KuharskiIzziv izziv = new KuharskiIzziv();
        izziv.setId(izzivId);

        when(kuharskiIzzivRepository.findById(izzivId)).thenReturn(Optional.of(izziv));
        when(receptRepository.findById(receptId)).thenReturn(Optional.empty());

        ResponseEntity<String> response = kuharskiIzzivController.dodajReceptDoIzziva(izzivId, receptId);

        assertEquals(NOT_FOUND, response.getStatusCode());
        assertEquals("Recept z ID 1 ni bil najden.", response.getBody());
        verify(receptRepository, never()).save(any());
    }
}
