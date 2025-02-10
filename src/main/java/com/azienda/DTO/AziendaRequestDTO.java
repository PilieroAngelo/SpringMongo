package com.azienda.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "aziende")
public class AziendaRequestDTO {

    @NotBlank(message = "Inserire il nome dell'azienda")
    private String nomeAzienda;
    @NotNull(message = "Inserire il numero del personale dell'azienda")
    @Min(value = 1, message = "Il personale non può essere inferiore ad 1")
    private int numeroPersonale;
    @Size(min = 12, max = 12, message = "La partita IVA deve essere composta da " +
            "12 caratteri alfanumerici")
    private String PIVA;
    @NotNull(message = "Inserire il netto annuale dell'azienda")
    @Min(value = 0, message = "Il netto annuo non può essere inferiore a 0")
    private double nettoAnnuale;

    public AziendaRequestDTO() {
    }

    public AziendaRequestDTO(double nettoAnnuale, String PIVA, int numeroPersonale, String nomeAzienda) {
        this.PIVA = PIVA;
        this.numeroPersonale = numeroPersonale;
        this.nomeAzienda = nomeAzienda;
        this.nettoAnnuale = nettoAnnuale;
    }

    public double getNettoAnnuale() {
        return nettoAnnuale;
    }

    public void setNettoAnnuale(double nettoAnnuale) {
        this.nettoAnnuale = nettoAnnuale;
    }

    public String getPIVA() {
        return PIVA;
    }

    public void setPIVA(String PIVA) {
        this.PIVA = PIVA;
    }

    public int getNumeroPersonale() {
        return numeroPersonale;
    }

    public void setNumeroPersonale(int numeroPersonale) {
        this.numeroPersonale = numeroPersonale;
    }

    public String getNomeAzienda() {
        return nomeAzienda;
    }

    public void setNomeAzienda(String nomeAzienda) {
        this.nomeAzienda = nomeAzienda;
    }
}

