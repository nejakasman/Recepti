package com.example.recepti;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(KuharskiIzzivController.class)
class KuharskiIzzivControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private KuharskiIzzivRepository kuharskiIzzivRepository;

    @MockBean
    private ReceptRepository receptRepository;

    @BeforeEach
    void setUp() {
        Mockito.reset(kuharskiIzzivRepository, receptRepository);
    }

    @ParameterizedTest
    @ValueSource(strings = {"Izziv 1", "Izziv 2"})
    @DisplayName("Dodajanje novega kuharskega izziva")
    public void testDodajIzziv_Success(String naziv) throws Exception {
        KuharskiIzziv izziv = new KuharskiIzziv();
        izziv.setNaziv(naziv);
        izziv.setOpis("Opis novega izziva");
        izziv.setTrajanjeDo(LocalDate.now().plusDays(10));

        Mockito.when(kuharskiIzzivRepository.save(Mockito.any(KuharskiIzziv.class))).thenReturn(izziv);

        mockMvc.perform(MockMvcRequestBuilders.post("/kuharski-izziv")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"naziv\":\"" + naziv + "\",\"opis\":\"Opis novega izziva\",\"trajanjeDo\":\"2024-12-31\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Kuharski izziv uspešno dodan"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "Opis novega izziva"})
    @DisplayName("Negativni test za dodajanje Kuharskega Izziva z manjkajočimi podatki")
    public void testDodajIzziv_MissingFields(String naziv) throws Exception {
        String bodyContent = String.format("{\"naziv\":\"%s\", \"opis\":\"Opis novega izziva\", \"trajanjeDo\":\"2024-12-31\"}", naziv);

        mockMvc.perform(MockMvcRequestBuilders.post("/kuharski-izziv")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bodyContent))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Vsi podatki (naziv, opis, trajanje) so obvezni."));
    }

    @Test
    @DisplayName("Pozitiven test za brisanje kuharskega izziva")
    public void testIzbrisiIzziv_Success() throws Exception {
        KuharskiIzziv izziv = new KuharskiIzziv();
        izziv.setId(1);
        izziv.setNaziv("Test Izziv");
        izziv.setOpis("Opis izziva");
        izziv.setTrajanjeDo(LocalDate.now().plusDays(10));

        Mockito.when(kuharskiIzzivRepository.findById(1)).thenReturn(Optional.of(izziv));

        mockMvc.perform(MockMvcRequestBuilders.delete("/kuharski-izziv/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Kuharski izziv uspešno zbrisan"));

        Mockito.verify(kuharskiIzzivRepository, Mockito.times(1)).deleteById(1);
    }

    @Test
    @DisplayName("Negativni test za brisanje kuharskega izziva - Izziv ne obstaja")
    public void testIzbrisiIzziv_NotFound() throws Exception {
        Mockito.when(kuharskiIzzivRepository.findById(1)).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.delete("/kuharski-izziv/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Kuharski izziv z ID 1 ni bil najden."));

        Mockito.verify(kuharskiIzzivRepository, Mockito.never()).deleteById(Mockito.anyInt());  // Preverimo, da deleteById ni bila poklicana
    }
}
