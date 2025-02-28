/*package com.azienda.IntegrationTest;

import com.azienda.DTO.AziendaRequestDTO;
import com.azienda.DTO.AziendaRequestPatchDTO;
import com.azienda.DTO.AziendaResponseDTO;
import com.azienda.Entity.Azienda;
import com.azienda.Repository.AziendaRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AziendaControllerIntegrationTest {

    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    private AziendaRequestDTO request, badRequest;
    private Azienda aziendaTest;
    private AziendaRequestPatchDTO patchDto, badPatch;
    private List<Azienda> listaAziende = new ArrayList<>();
    private AziendaRepo aziendaRepository;

    @BeforeEach
    void setup(WebApplicationContext webApplicationContext) {

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        aziendaRepository = webApplicationContext.getBean(AziendaRepo.class);

        listaAziende.add(aziendaTest);

        objectMapper = new ObjectMapper();

        aziendaTest = new Azienda("1", 232, "394JE0I3323E", 2, "PPA");

        badRequest = new AziendaRequestDTO(-2, "PSN21", 0, "");

        request = new AziendaRequestDTO(23.3, "PSOE9384ON21", 2, "AAA");

        patchDto = new AziendaRequestPatchDTO("PPPP", 23, 3234);

        badPatch = new AziendaRequestPatchDTO("", -2, -12);
    }

    @Test
    void insert() throws Exception {

        String id = "1";
        AziendaResponseDTO responseDTO = new AziendaResponseDTO(id, request.getNettoAnnuale(), request.getPIVA(), request.getNumeroPersonale(), request.getNomeAzienda());

        String requestJson = objectMapper.writeValueAsString(request);

        aziendaRepository.save(aziendaTest);

        mockMvc.perform(post("/aziende")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists());

        System.out.println("Response DTO nome: " + responseDTO.getNomeAzienda());
    }

    @Test
    void getByID() throws Exception {
        aziendaRepository.findById(aziendaTest.getId());

        mockMvc.perform(get("/aziende/" + aziendaTest.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nettoAnnuale").value(aziendaTest.getNettoAnnuale()))
                .andExpect(jsonPath("$.piva").value(aziendaTest.getPIVA()))
                .andExpect(jsonPath("$.numeroPersonale").value(aziendaTest.getNumeroPersonale()))
                .andExpect(jsonPath("$.nomeAzienda").value(aziendaTest.getNomeAzienda()));
    }

    @Test
    void getAll() throws Exception {
        listaAziende = aziendaRepository.findAll();

        mockMvc.perform(get("/aziende")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

   @Test
    void update() throws Exception {

        String requestJson = objectMapper.writeValueAsString(request);

        mockMvc.perform(put("/aziende/" + aziendaTest.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().string("Modifica Effettuata"));

        aziendaRepository.save(aziendaTest);
    }

    @Test
    void update_InvalidInput() throws Exception {
        String badRequestJson = objectMapper.writeValueAsString(badRequest);

        mockMvc.perform(put("/aziende/" + aziendaTest.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(badRequestJson))
                .andExpect(status().isBadRequest());

        aziendaRepository.save(aziendaTest);
    }

    @Test
    void patch_InvalidInput() throws Exception {

        String patchInvalid = objectMapper.writeValueAsString(badPatch);

        mockMvc.perform(put("/aziende/" + aziendaTest.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(patchInvalid))
                .andExpect(status().isBadRequest());

        aziendaRepository.save(aziendaTest);
    }

    @Test
    void patch_ValidInput() throws Exception {
        String patchJson = objectMapper.writeValueAsString(patchDto);

        mockMvc.perform(patch("/aziende/" + aziendaTest.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(patchJson))
                .andExpect(status().isOk())
                .andExpect(content().string("Modifica effettuata"));

        aziendaRepository.save(aziendaTest);
    }

    @Test
    void getByID_NotFound() throws Exception {
        String id = "non_esistente";
        aziendaRepository.findById(id);

        mockMvc.perform(get("/aziende/" + id))
                .andExpect(status().isNotFound());
       // assertThat(aziendaRepository.findById(aziendaTest.getId()).isEmpty());
    }

    @Test
    void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/aziende/" + aziendaTest.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string("Eliminazione Effettuata"));

        aziendaRepository.delete(aziendaTest);
        assertThat(aziendaRepository.findById(aziendaTest.getId()).isEmpty()).isTrue();

    }

    @Test
    void insert_InvalidInput() throws Exception {

        String badInsertJson = objectMapper.writeValueAsString(badRequest);

        mockMvc.perform(post("/aziende")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(badInsertJson))
                .andExpect(status().isBadRequest());

        // non so se usare il save ----> aziendaRepository.save(aziendaTest);
    }
}*/