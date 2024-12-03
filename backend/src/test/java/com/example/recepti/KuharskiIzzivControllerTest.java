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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(KuharskiIzzivController.class)
class KuharskiIzzivControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private KuharskiIzzivRepository kuharskiIzzivRepository;

    @BeforeEach
    void setUp() {
        Mockito.reset(kuharskiIzzivRepository);
    }

    @Test
    @DisplayName("Prikaz vseh kuharskih izzivov")
    public void testGetAllIzzivi_Success() throws Exception {
        // Mock podatki
        KuharskiIzziv izziv1 = new KuharskiIzziv( "Izziv 1", "Opis 1", LocalDate.now());
        KuharskiIzziv izziv2 = new KuharskiIzziv("Izziv 2", "Opis 2", LocalDate.now());

        Mockito.when(kuharskiIzzivRepository.findAll()).thenReturn(List.of(izziv1, izziv2));

        mockMvc.perform(MockMvcRequestBuilders.get("/kuharski-izziv"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].naziv").value("Izziv 1"))
                .andExpect(jsonPath("$[1].naziv").value("Izziv 2"));
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
}
