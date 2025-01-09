//package com.example.recepti;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.util.List;
//import java.util.Optional;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.eq;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@WebMvcTest(ReceptiController.class)
//class ReceptiControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private ReceptRepository receptRepository;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    private Recept sampleRecept;
//
//    @BeforeEach
//    void setUp() {
//        sampleRecept = new Recept();
//        sampleRecept.setId(1);
//        sampleRecept.setIme("Test Recept");
//        sampleRecept.setSestavine(List.of("Sestavina 1", "Sestavina 2"));
//        sampleRecept.setNavodila(List.of("Korak 1", "Korak 2"));
//        sampleRecept.setOpis("Opis recepta");
//    }
//
//    // Test za brisanje recepta
//    @Test
//    void testDeleteRecept_Success() throws Exception {
//        Mockito.when(receptRepository.findById(1)).thenReturn(Optional.of(sampleRecept));
//
//        mockMvc.perform(delete("/recepti/1"))
//                .andExpect(status().isNoContent());  // Očekujemo status 204 za uspešno brisanje
//
//        Mockito.verify(receptRepository, Mockito.times(1)).deleteById(1);
//    }
//
//
//    @Test
//    void testDeleteRecept_NotFound() throws Exception {
//        Mockito.when(receptRepository.findById(1)).thenReturn(Optional.empty());
//
//        mockMvc.perform(delete("/recepti/1"))
//                .andExpect(status().isNotFound())  // Očekujemo status 404, ko recept ne obstaja
//                .andExpect(content().string("Recept z ID 1 ni bil najden."));  // Lahko dodate tudi sporočilo, če želite
//
//        Mockito.verify(receptRepository, Mockito.never()).deleteById(any());
//    }
//
//
//}
//
