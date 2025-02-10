package com.azienda.Controller;

import com.azienda.DTO.AziendaRequestDTO;
import com.azienda.DTO.AziendaRequestPatchDTO;
import com.azienda.DTO.AziendaResponseDTO;
import com.azienda.Entity.Azienda;
import com.azienda.Exceptions.ErrorDetails;
import com.azienda.Exceptions.GlobalExceptionHandler;
import com.azienda.Service.AziendaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/aziende")
public class AziendaController {

    private final AziendaService service;

    public AziendaController(AziendaService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    @Operation(description = "Create a new Azienda")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Entity created",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AziendaRequestDTO.class))),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad request",
                    content = @Content(
                            schema = @Schema(implementation = GlobalExceptionHandler.class))),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorDetails.class)))
    })
    public ResponseEntity<?> insert(@Valid @RequestBody AziendaRequestDTO aziendaRequestDTO, BindingResult bindResult) {
        if (bindResult.hasErrors()) {
            StringBuilder errore = new StringBuilder("Errore di validazione: ");
            bindResult.getAllErrors().forEach(error -> errore.append(error.getDefaultMessage()).append("; \n"));
            return new ResponseEntity<>(errore.toString(), HttpStatus.BAD_REQUEST);
        }
       AziendaResponseDTO response = service.insert(aziendaRequestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(description = "Get all Azienda entities")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "OK",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Azienda.class)))
    })
    public List<AziendaResponseDTO> getAll() {
        return service.getAll();
    }

    @PutMapping("/{id}")
    @Operation(description = "Update an Azienda by ID")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Azienda updated",
                    content = @Content(
                            schema = @Schema(implementation = String.class))),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad request",
                    content = @Content(
                            schema = @Schema(implementation = ErrorDetails.class))),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorDetails.class)))
    })
    public ResponseEntity<String> update(@PathVariable String id, @Valid @RequestBody AziendaRequestDTO request, BindingResult bindResult) {
        if (bindResult.hasErrors()) {
            StringBuilder errore = new StringBuilder("Errore di validazione: ");
            bindResult.getAllErrors().forEach(error -> errore.append(error.getDefaultMessage()).append("; \n"));
            return new ResponseEntity<>(errore.toString(), HttpStatus.BAD_REQUEST);
        }
        service.update(id, request);
        return new ResponseEntity<>("Modifica Effettuata", HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    @Operation(description = "Partially update an Azienda by ID")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Azienda partially updated",
                    content = @Content(
                            schema = @Schema(implementation = String.class))),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad request",
                    content = @Content(
                            schema = @Schema(implementation = ErrorDetails.class))),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorDetails.class)))
    })
    public ResponseEntity<?> updateParziale(@PathVariable String id, @RequestBody AziendaRequestPatchDTO patch, BindingResult bindResult) {
        if (bindResult.hasErrors()) {
            StringBuilder errore = new StringBuilder("Errore di validazione: ");
            bindResult.getAllErrors().forEach(error -> errore.append(error.getDefaultMessage()).append("; \n"));
            return new ResponseEntity<>(errore.toString(), HttpStatus.BAD_REQUEST);
        }

        service.updateParziale(id, patch);
        //AziendaResponseDTO risposta = service.updateParziale(id, patch);
        return new ResponseEntity<>("Modifica effettuata", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(description = "Delete an Azienda by ID")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Azienda deleted",
                    content = @Content(
                            schema = @Schema(implementation = String.class))),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorDetails.class)))
    })
    public ResponseEntity<String> delete(@PathVariable String id) {
        service.delete(id);
        return new ResponseEntity<>("Eliminazione Effettuata", HttpStatus.OK);
    }
}
