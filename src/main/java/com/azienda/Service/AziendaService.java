package com.azienda.Service;

import com.azienda.DTO.AziendaRequestDTO;
import com.azienda.DTO.AziendaRequestPatchDTO;
import com.azienda.DTO.AziendaResponseDTO;
import com.azienda.Entity.Azienda;
import com.azienda.Exceptions.IdNotFoundException;
import com.azienda.Mapper.AziendaMapperManuale;
import com.azienda.Repository.AziendaRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AziendaService {
    private final AziendaRepo aziendarepo;
    private final AziendaMapperManuale mapper;

    public AziendaService(AziendaRepo aziendarepo, AziendaMapperManuale mapper) {
        this.aziendarepo = aziendarepo;
        this.mapper = mapper;
    }

    public List<AziendaResponseDTO> getAll() {
        List<Azienda> aziendaEntities = aziendarepo.findAll();

        return mapper.toList(aziendaEntities);
    }//creare un metodo toList e farlo nel mapper

    public AziendaResponseDTO insert(AziendaRequestDTO requestdto) {
        Azienda aziendaEntity = mapper.toEntity(requestdto);
        aziendaEntity = aziendarepo.save(aziendaEntity);
        return mapper.entityToResponseDTO(aziendaEntity);
    }


    public void update(String id, AziendaRequestDTO request) {
        Optional<Azienda> optionalAzienda = aziendarepo.findById(id);
        if (optionalAzienda.isPresent()) {
            Azienda aziendaEsistente = optionalAzienda.get();
            mapper.updateEntityFromDTO(request, aziendaEsistente);
            aziendarepo.save(aziendaEsistente);
        } else {
            throw new IdNotFoundException("Azienda non trovata con il codice: " + id);
        }
    }

    //vedere se mettere void anche a patch
    public AziendaResponseDTO updateParziale(String id, AziendaRequestPatchDTO patchDTO) {
        Azienda aziendaEsistente = aziendarepo.findById(id)
                .orElseThrow(() -> new IdNotFoundException("Azienda non trovata con il codice: " + id));
        mapper.updateEntityFromPatchDTO(patchDTO, aziendaEsistente);
        aziendarepo.save(aziendaEsistente);
        return mapper.entityToResponseDTO(aziendaEsistente);
    }

    public void delete(String id) {
        if (!aziendarepo.existsById(id)) {
            throw new IdNotFoundException("Azienda non trovato con il codice: " + id);
        }
        aziendarepo.deleteById(id);
    }

}


