package com.azienda.DTO;

import jakarta.validation.constraints.Min;

public class AziendaRequestPatchDTO {
    private String nomeAzienda;

    @Min(value = 1, message = "Il personale non può essere inferiore ad 1")
    private int numeroPersonale;

    @Min(value = 0, message = "Il netto annuo non può essere inferiore a 0")
    private double nettoAnnuale;

    public AziendaRequestPatchDTO() {
    }

    public AziendaRequestPatchDTO(String nomeAzienda, int numeroPersonale, double nettoAnnuale) {
        this.nomeAzienda = nomeAzienda;
        this.numeroPersonale = numeroPersonale;
        this.nettoAnnuale = nettoAnnuale;
    }

    public double getNettoAnnuale() {
        return nettoAnnuale;
    }

    public void setNettoAnnuale(double nettoAnnuale) {
        this.nettoAnnuale = nettoAnnuale;
    }

    public String getNomeAzienda() {
        return nomeAzienda;
    }

    public void setNomeAzienda(String nomeAzienda) {
        this.nomeAzienda = nomeAzienda;
    }

    public int getNumeroPersonale() {
        return numeroPersonale;
    }

    public void setNumeroPersonale (int numeroPersonale) {
        this.numeroPersonale = numeroPersonale;
    }

}
