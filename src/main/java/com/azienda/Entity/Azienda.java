package com.azienda.Entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "aziende")
public class Azienda {
    @Id
    private String id;
    private String nomeAzienda;
    private int numeroPersonale;
    private String PIVA;
    private double nettoAnnuale;

    public Azienda() {

    }

    public Azienda(String id, double nettoAnnuale, String PIVA, int numeroPersonale, String nomeAzienda) {
        this.id = id;
        this.nettoAnnuale = nettoAnnuale;
        this.PIVA = PIVA;
        this.numeroPersonale = numeroPersonale;
        this.nomeAzienda = nomeAzienda;
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
