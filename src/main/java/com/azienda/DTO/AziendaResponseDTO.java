package com.azienda.DTO;

//server al client
public class AziendaResponseDTO {
    private String id;
    private String nomeAzienda;
    private int numeroPersonale;
    private double nettoAnnuale;
    private String PIVA;

    public AziendaResponseDTO() {
    }

    public AziendaResponseDTO(String id, String nomeAzienda, String PIVA, int numeroPersonale) {
        this.id = id;
        this.nomeAzienda = nomeAzienda;
        this.PIVA = PIVA;
        this.numeroPersonale = numeroPersonale;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public void setNumeroPersonale(int numeroPersonale) {
        this.numeroPersonale = numeroPersonale;
    }

    public String getPIVA() {
        return PIVA;
    }

    public void setPIVA(String PIVA) {
        this.PIVA = PIVA;
    }
}
