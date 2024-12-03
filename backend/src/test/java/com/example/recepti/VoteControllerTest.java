package com.example.recepti;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

@WebMvcTest(VoteController.class)  // Test only the VoteController
public class VoteControllerTest {

    @Autowired
    private MockMvc mockMvc;  // Mocking the HTTP requests

    @MockBean
    private VoteService voteService;  // Mocking the service layer

    @MockBean
    private ReceptRepository receptRepository;  // Mocking the repository layer

    @MockBean
    private KuharskiIzzivRepository kuharskiIzzivRepository;  // Mocking the repository layer

    @Test
    public void testGlasuj_Success() throws Exception {
        // Simuliramo Recept in KuharskiIzziv da se kao vrnejo iz repository
        Recept recept = new Recept();  // Ustvarimo mock Recept objekt
        KuharskiIzziv kuharskiIzziv = new KuharskiIzziv();  // Ustvarimo mock KuharskiIzziv objekt

        // Mockiramo tudi metode iz repizitorija,da vrnejo objekte
        when(receptRepository.findById(2)).thenReturn(Optional.of(recept));
        when(kuharskiIzzivRepository.findById(3)).thenReturn(Optional.of(kuharskiIzziv));

        // Simuliramo uspešno glasovanje
        doNothing().when(voteService).glasuj(any(Uporabnik.class), eq(recept), eq(kuharskiIzziv));

        // izvedemo POST request
        mockMvc.perform(post("/glasovi/glasuj")
                        .param("uporabnikId", "1")
                        .param("receptId", "2")
                        .param("izzivId", "3"))
                .andExpect(status().isOk()); // pricakujemo OK status (200)
    }

    @Test
    public void testGlasuj_Failed() throws Exception {
        // Simuliramo Recept in KuharskiIzziv da se kao vrnejo iz repository
        Recept recept = new Recept();  // mock Recept
        KuharskiIzziv kuharskiIzziv = new KuharskiIzziv();  //  mock KuharskiIzziv


        when(receptRepository.findById(2)).thenReturn(Optional.of(recept));
        when(kuharskiIzzivRepository.findById(3)).thenReturn(Optional.of(kuharskiIzziv));

        // Simuliramo failure ce uporabnik je ze glasoval
        doThrow(new IllegalStateException("Uporabnik je že glasoval!")).when(voteService).glasuj(any(Uporabnik.class), eq(recept), eq(kuharskiIzziv));

        // izvedemo POST request
        mockMvc.perform(post("/glasovi/glasuj")
                        .param("uporabnikId", "1")
                        .param("receptId", "2")
                        .param("izzivId", "3"))
                .andExpect(status().isBadRequest()); // pricakujemo BadRequest status (400)
    }
}
