package riccardomamoli.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "biglietto")

public class Biglietto {

    @Id
    @GeneratedValue
    @Column(name = "id_biglietto")
    private Long id_biglietto;

    @ManyToOne
    @JoinColumn(name = "id_mezzo")
    private Mezzo mezzo;

    @ManyToOne
    @JoinColumn(name = "id_distributore")
    private PuntoVendita puntoVendita;

    @ManyToOne
    @JoinColumn(name = "id_tratta")
    private Tratta tratta;

    @Column(name = "codice_univoco_biglietto", unique = true)
    private String codice_univoco_biglietto;

    @Column(name = "data_emissione")
    private LocalDate data_emissione;

    @Column(name = "timbrato")
    private boolean timbrato;


    public Biglietto() {
    }

    public Biglietto(Mezzo mezzo, PuntoVendita puntoVendita, Tratta tratta, String codice_univoco_biglietto, LocalDate data_emissione, boolean timbrato) {
        this.mezzo = mezzo;
        this.puntoVendita = puntoVendita;
        this.tratta = tratta;
        this.codice_univoco_biglietto = generaCodice();
        this.data_emissione = data_emissione;
        this.timbrato = timbrato;
    }

    private String generaCodice() {
        return UUID.randomUUID().toString();
    }

    public Long getId_biglietto() {
        return id_biglietto;
    }

    public Mezzo getMezzo() {
        return mezzo;
    }

    public void setMezzo(Mezzo mezzo) {
        this.mezzo = mezzo;
    }

    public PuntoVendita getPuntoVendita() {
        return puntoVendita;
    }

    public void setPuntoVendita(PuntoVendita puntoVendita) {
        this.puntoVendita = puntoVendita;
    }

    public Tratta getTratta() {
        return tratta;
    }

    public void setTratta(Tratta tratta) {
        this.tratta = tratta;
    }

    public String getCodice_univoco_biglietto() {
        return codice_univoco_biglietto;
    }

    public LocalDate getData_emissione() {
        return data_emissione;
    }

    public void setData_emissione(LocalDate data_emissione) {
        this.data_emissione = data_emissione;
    }

    public boolean isTimbrato() {
        return timbrato;
    }

    public void setTimbrato(boolean timbrato) {
        this.timbrato = timbrato;
    }

    @Override
    public String toString() {
        return "Biglietto{" +
                "id_biglietto=" + id_biglietto +
                ", mezzo=" + mezzo +
                ", puntoVendita=" + puntoVendita +
                ", tratta=" + tratta +
                ", codice_univoco_biglietto=" + codice_univoco_biglietto +
                ", data_emissione=" + data_emissione +
                ", timbrato=" + timbrato +
                '}';
    }
}
