package com.azienda.Service;

import com.azienda.DTO.AziendaRequestDTO;
import com.azienda.DTO.AziendaRequestPatchDTO;
import com.azienda.DTO.AziendaResponseDTO;
import com.azienda.Entity.Azienda;
import com.azienda.Exceptions.IdNotFoundException;
import com.azienda.Mapper.AziendaMapper;
import com.azienda.Repository.AziendaRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AziendaServiceTest {

    @Mock
    private AziendaMapper aziendaMapper;

    @Mock
    private AziendaRepo aziendaRepo;

    @InjectMocks
    private AziendaService aziendaService;

    @Test
    void getAll() {
        List<Azienda> listaAzienda = new ArrayList<>();
        listaAzienda.add(new Azienda("1", 23.3, "PPP", 2, "AA"));

        when(aziendaRepo.findAll()).thenReturn(listaAzienda);

        List<AziendaResponseDTO> aziendeResponse = aziendaService.getAll();
        assertNotNull(aziendeResponse);
        verify(aziendaRepo, times(1)).findAll();
    }

    @Test
    void insert() {
        AziendaRequestDTO request = new AziendaRequestDTO(23.5, "PEEJ", 2, "ANNA");
        Azienda azienda = new Azienda("1", request.getNettoAnnuale(), request.getPIVA(), request.getNumeroPersonale(), request.getNomeAzienda());
        AziendaResponseDTO aziendaResponseDTO = new AziendaResponseDTO(azienda.getId(), azienda.getNettoAnnuale(), azienda.getPIVA(), azienda.getNumeroPersonale(), azienda.getNomeAzienda());

        when(aziendaMapper.toEntity(any(AziendaRequestDTO.class))).thenReturn(azienda);
        when(aziendaRepo.save(any(Azienda.class))).thenReturn(azienda);
        when(aziendaMapper.entityToResponseDTO(any(Azienda.class))).thenReturn(aziendaResponseDTO);

        AziendaResponseDTO insertedAzienda = aziendaService.insert(request);

        assertNotNull(insertedAzienda);
        assertEquals(request.getNomeAzienda(), insertedAzienda.getNomeAzienda());
        assertEquals(request.getNumeroPersonale(), insertedAzienda.getNumeroPersonale());
        assertEquals(request.getPIVA(), insertedAzienda.getPIVA());
        assertTrue(insertedAzienda.getNettoAnnuale() >= 1);

        verify(aziendaMapper, times(1)).toEntity(any(AziendaRequestDTO.class));
        verify(aziendaRepo, times(1)).save(any(Azienda.class));
        verify(aziendaMapper, times(1)).entityToResponseDTO(any(Azienda.class));
    }

    @Test
    void update() {
        AziendaRequestDTO request = new AziendaRequestDTO(23.5, "PEEJ", 54464, "ANNA");
        Azienda existingAzienda = new Azienda("1", 2.3, "PEEJ", 2, "PINO");

        when(aziendaRepo.findById(existingAzienda.getId())).thenReturn(Optional.of(existingAzienda));

        doAnswer(invocation -> {
            AziendaRequestDTO dto = invocation.getArgument(0);
            Azienda entity = invocation.getArgument(1);
            entity.setNomeAzienda(dto.getNomeAzienda());
            entity.setNumeroPersonale(dto.getNumeroPersonale());
            entity.setNettoAnnuale(dto.getNettoAnnuale());
            return null;
        }).when(aziendaMapper).updateEntityFromDTO(request, existingAzienda);

        when(aziendaRepo.save(existingAzienda)).thenReturn(existingAzienda);

        aziendaService.update(existingAzienda.getId(), request);

        assertEquals(request.getNomeAzienda(), existingAzienda.getNomeAzienda());
        assertEquals(request.getNumeroPersonale(), existingAzienda.getNumeroPersonale());
        assertEquals(request.getNettoAnnuale(), existingAzienda.getNettoAnnuale());

        verify(aziendaRepo, times(1)).findById(existingAzienda.getId());
        verify(aziendaMapper, times(1)).updateEntityFromDTO(request, existingAzienda);
        verify(aziendaRepo, times(1)).save(existingAzienda);
    }

    @Test
    void updateParziale() {
        AziendaRequestPatchDTO patch = new AziendaRequestPatchDTO("PP5", 54464, 343.34);
        Azienda existingAzienda = new Azienda("1", 2.3, "PEEJ", 2, "PINO");
        AziendaResponseDTO expectedResponseDTO = new AziendaResponseDTO(existingAzienda.getId(), existingAzienda.getNettoAnnuale(), existingAzienda.getPIVA(), existingAzienda.getNumeroPersonale(), existingAzienda.getNomeAzienda());

        when(aziendaRepo.findById(existingAzienda.getId())).thenReturn(Optional.of(existingAzienda));

        doAnswer(invocation -> {
            AziendaRequestPatchDTO patchDTO = invocation.getArgument(0);
            Azienda entity = invocation.getArgument(1);
            entity.setNomeAzienda(patchDTO.getNomeAzienda());
            entity.setNumeroPersonale(patchDTO.getNumeroPersonale());
            entity.setNettoAnnuale(patchDTO.getNettoAnnuale());
            return null;
        }).when(aziendaMapper).updateEntityFromPatchDTO(patch, existingAzienda);

        when(aziendaRepo.save(existingAzienda)).thenReturn(existingAzienda);

        when(aziendaMapper.entityToResponseDTO(existingAzienda)).thenReturn(expectedResponseDTO);

        AziendaResponseDTO result = aziendaService.updateParziale(existingAzienda.getId(), patch);

        verify(aziendaRepo, times(1)).findById(existingAzienda.getId());
        verify(aziendaMapper, times(1)).updateEntityFromPatchDTO(patch, existingAzienda);
        verify(aziendaRepo, times(1)).save(existingAzienda);
        verify(aziendaMapper, times(1)).entityToResponseDTO(existingAzienda);

        assertEquals(expectedResponseDTO, result);
    }

    @Test
    void delete() {
        Azienda existingAzienda = new Azienda("1", 2.3, "PEEJ", 2, "PINO");

        aziendaRepo.deleteById(existingAzienda.getId());

        verify(aziendaRepo, times(1)).deleteById(existingAzienda.getId());
    }

    @Test
    void delete_throws_IdNotFoundException_when_azienda_not_found() {
        String nonExistentId = "99";  // ID non esistente

        when(aziendaRepo.existsById(nonExistentId)).thenReturn(false);

        assertThrows(IdNotFoundException.class, () -> aziendaService.delete(nonExistentId));
    }

    @Test
    void update_throws_IdNotFoundException_when_azienda_not_found() {
        AziendaRequestDTO request = new AziendaRequestDTO(23.5, "PEEJ", 54464, "ANNA");
        String nonExistentId = "99";  // ID non esistente

        when(aziendaRepo.findById(nonExistentId)).thenReturn(Optional.empty());
        assertThrows(IdNotFoundException.class, () -> aziendaService.update(nonExistentId, request));
    }

    @Test
    void getByID_NotFound() {
        String id = "1";

        assertThrows(IdNotFoundException.class, () -> aziendaService.getByID(id), "IdNotFoundException lanciato");
    }

    @Test
    void getByID_Success() {
        String id = "1";
        Azienda existingAzienda = new Azienda(id, 23.5, "PIVA123", 10, "Nome Azienda");
        AziendaResponseDTO responseDTO = new AziendaResponseDTO(id, 23.5, "PIVA123", 10, "Nome Azienda");

        when(aziendaRepo.findById(id)).thenReturn(Optional.of(existingAzienda));
        when(aziendaMapper.entityToResponseDTO(existingAzienda)).thenReturn(responseDTO);
        AziendaResponseDTO response = aziendaService.getByID("1");


        assertNotNull(response);
        assertEquals("Nome Azienda", response.getNomeAzienda());
        assertEquals("1", response.getId());

        assertEquals(responseDTO, response);
        verify(aziendaRepo, times(1)).findById(id);
        verify(aziendaMapper, times(1)).entityToResponseDTO(existingAzienda);
    }

    @Test
    void updateParziale_throws_IdNotFoundException_when_azienda_not_found() {
        String nonExistentId = "99";
        AziendaRequestPatchDTO patch = new AziendaRequestPatchDTO("PP5", 54464, 343.34);

        when(aziendaRepo.findById(nonExistentId)).thenReturn(Optional.empty());

        IdNotFoundException exception = assertThrows(IdNotFoundException.class, () -> aziendaService.updateParziale(nonExistentId, patch));

        assertEquals("Azienda non trovata con il codice: " + nonExistentId, exception.getMessage());
    }
}
