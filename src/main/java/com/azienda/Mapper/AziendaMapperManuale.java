package com.azienda.Mapper;

import com.azienda.DTO.AziendaRequestDTO;
import com.azienda.DTO.AziendaRequestPatchDTO;
import com.azienda.DTO.AziendaResponseDTO;
import com.azienda.Entity.Azienda;
import com.azienda.Repository.AziendaRepo;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AziendaMapperManuale {
    public final AziendaRepo aziendaRepo;

    public AziendaMapperManuale(AziendaRepo aziendaRepo) {
        this.aziendaRepo = aziendaRepo;
    }

    public Azienda toEntity(AziendaRequestDTO aziendaRequestDTO) {
        Azienda azienda = new Azienda();
        azienda.setNomeAzienda(aziendaRequestDTO.getNomeAzienda());
        azienda.setNumeroPersonale(aziendaRequestDTO.getNumeroPersonale());
        azienda.setPIVA(aziendaRequestDTO.getPIVA());
        azienda.setNettoAnnuale(aziendaRequestDTO.getNettoAnnuale());
        return azienda;
    }

    public void updateEntityFromDTO(AziendaRequestDTO dto, Azienda entity) {
        if (dto.getNomeAzienda() != null) {
            entity.setNomeAzienda(dto.getNomeAzienda());
        }
        if (dto.getNumeroPersonale() >= 0) {
            entity.setNumeroPersonale(dto.getNumeroPersonale());
        }
       if (dto.getNettoAnnuale() != 0) {
            entity.setNettoAnnuale(dto.getNettoAnnuale());
        }
    }

    public AziendaResponseDTO entityToResponseDTO(Azienda azienda){//nel service da richiamare
        AziendaResponseDTO aziendaResponseDTO = new AziendaResponseDTO();
        aziendaResponseDTO.setId(azienda.getId());
        aziendaResponseDTO.setNomeAzienda(azienda.getNomeAzienda());
        aziendaResponseDTO.setNumeroPersonale(azienda.getNumeroPersonale());
        aziendaResponseDTO.setPIVA(azienda.getPIVA());
        aziendaResponseDTO.setNettoAnnuale(azienda.getNettoAnnuale());
        return aziendaResponseDTO;
    }

    public void updateEntityFromPatchDTO(AziendaRequestPatchDTO patchDTO, Azienda entity) {
        if (patchDTO.getNomeAzienda() != null) {
            entity.setNomeAzienda(patchDTO.getNomeAzienda());
        }
        if (patchDTO.getNumeroPersonale() != 0) {
            entity.setNumeroPersonale(patchDTO.getNumeroPersonale());
        }
        if (patchDTO.getNettoAnnuale() != 0) {
            entity.setNettoAnnuale(patchDTO.getNettoAnnuale());
        }
    }

    public List<AziendaResponseDTO> toList(List<Azienda> azienda) {
        List<AziendaResponseDTO> aziendaResponseDTOList = new ArrayList<>();
        for (Azienda aziendaEntity : azienda) {
            aziendaResponseDTOList.add(entityToResponseDTO(aziendaEntity)); // Usa il metodo toDTO che hai già creato
        }
        return aziendaResponseDTOList;
    }

}
