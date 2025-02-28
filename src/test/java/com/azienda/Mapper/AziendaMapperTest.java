package com.azienda.Mapper;

import com.azienda.DTO.AziendaRequestDTO;
import com.azienda.DTO.AziendaRequestPatchDTO;
import com.azienda.DTO.AziendaResponseDTO;
import com.azienda.Entity.Azienda;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AziendaMapperTest {

    @InjectMocks
    private AziendaMapper aziendaMapper;
    private EasyRandom easyRandom;

    @BeforeEach
    void setUp() {
        easyRandom = new EasyRandom();
    }
//da fare toEntity Normale
    @Test
    void testEntityToResponse() {
        Azienda azienda = new Azienda();
        AziendaRequestDTO requestDTO = new AziendaRequestDTO(23.5, "AP34ON230912", 3, "aa");
        azienda.setId("1");
        azienda.setNomeAzienda(requestDTO.getNomeAzienda());
        azienda.setNumeroPersonale(requestDTO.getNumeroPersonale());
        azienda.setNettoAnnuale(requestDTO.getNettoAnnuale());
        azienda.setPIVA(requestDTO.getPIVA());

        AziendaResponseDTO aziendaDto = aziendaMapper.entityToResponseDTO(azienda);

        assertNotNull(aziendaDto);
        assertNotNull(aziendaDto.getId());

        assertEquals(azienda.getPIVA(), aziendaDto.getPIVA());
        assertEquals(azienda.getNomeAzienda(), aziendaDto.getNomeAzienda());
    }

    @Test
    void testUpdateEntityFromDTO() {
        Azienda existingAzienda = easyRandom.nextObject(Azienda.class);
        AziendaRequestDTO aziendaDto = easyRandom.nextObject(AziendaRequestDTO.class);

        existingAzienda.setId("1");

        aziendaMapper.updateEntityFromDTO(aziendaDto, existingAzienda);

        assertNotNull(existingAzienda.getId());
        assertNotNull(existingAzienda.getNomeAzienda());
        assertNotNull(existingAzienda.getPIVA());

        assertNotEquals(aziendaDto.getPIVA(), existingAzienda.getPIVA());
    }

    @Test
    void testToEntity() {
        AziendaRequestDTO aziendaRequestDTO = new AziendaRequestDTO();
        aziendaRequestDTO.setNomeAzienda("Test Azienda");
        aziendaRequestDTO.setNumeroPersonale(100);
        aziendaRequestDTO.setPIVA("12345678901");
        aziendaRequestDTO.setNettoAnnuale(500000);

        Azienda azienda = aziendaMapper.toEntity(aziendaRequestDTO);

        assertNotNull(azienda);
        assertEquals("Test Azienda", azienda.getNomeAzienda());
        assertEquals(100, azienda.getNumeroPersonale());
        assertEquals("12345678901", azienda.getPIVA());
        assertEquals(500000, azienda.getNettoAnnuale());
    }


    @Test
    void EntityToResponse() {
        Azienda azienda = new Azienda();
        AziendaRequestDTO requestDTO = new AziendaRequestDTO(23.5, "AP34ON230912", 3, "aa");
        azienda.setId("1");
        azienda.setNomeAzienda(requestDTO.getNomeAzienda());
        azienda.setNumeroPersonale(requestDTO.getNumeroPersonale());
        azienda.setNettoAnnuale(requestDTO.getNettoAnnuale());
        azienda.setPIVA(requestDTO.getPIVA());

        AziendaResponseDTO aziendaDto = aziendaMapper.entityToResponseDTO(azienda);

        assertNotNull(aziendaDto);
        assertNotNull(aziendaDto.getId());
        assertEquals(azienda.getPIVA(), aziendaDto.getPIVA());
        assertEquals(azienda.getNomeAzienda(), aziendaDto.getNomeAzienda());
    }


    @Test
    void testUpdateEntityFromPatchDTO() {
        Azienda existingAzienda = easyRandom.nextObject(Azienda.class);
        AziendaRequestPatchDTO patchDTO = easyRandom.nextObject(AziendaRequestPatchDTO.class);

        existingAzienda.setId("1");

        aziendaMapper.updateEntityFromPatchDTO(patchDTO, existingAzienda);

        assertNotNull(existingAzienda.getId());
        assertNotNull(existingAzienda.getNomeAzienda());
        assertNotNull(existingAzienda.getPIVA());
    }

    @Test
    void testToList() {
        Azienda azienda1 = new Azienda("1", 23.4, "PAEI37402912", 3, "aa");
        Azienda azienda2 = new Azienda("2", 21.4, "PUENT2940382", 1, "BB");
        List<Azienda> aziendaList = List.of(azienda1, azienda2);

        List<AziendaResponseDTO> aziendaResponseDTOList = aziendaMapper.toList(aziendaList);

        assertNotNull(aziendaResponseDTOList);
        assertEquals(aziendaList.size(), aziendaResponseDTOList.size());
    }

    @Test
    void testToList_withEmptyList() {
        List<Azienda> emptyList = List.of();

        List<AziendaResponseDTO> responseDTOList = aziendaMapper.toList(emptyList);

        assertNotNull(responseDTOList);
        assertEquals(0, responseDTOList.size());
    }
}