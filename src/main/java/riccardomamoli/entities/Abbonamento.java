package riccardomamoli.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Entity
@Table(name = "abbonamento")

public class Abbonamento {
    @Id
    @GeneratedValue
    @Column(name = "id_abbonamento", nullable = false)
    private long id_abbonamento;

    @ManyToOne
    @JoinColumn(name = "id_punto_vendita", nullable = false)
    private PuntoVendita puntoVendita;

    @ManyToOne
    @JoinColumn(name = "id_tessera", nullable = false)
    private Tessera tessera;

    @OneToMany(mappedBy = "abbonamento")
    private List<TrattaAbbonamento> trattaAbbonamenti;

    @Column(name = "codice_univoco_abbonamento", unique = true, nullable = false)
    private long codice_univoco_abbonamento;

    @Column(name = "data_emmissione", nullable = false)
    private LocalDate data_emmissione;

    @Column(name = "data_scadenza", nullable = false)
    private LocalDate data_scadenza;

    @Column(name = "tipologia_abbonamento", nullable = false)
    private TipologiaAbbonamento tipologia_abbonamento;

    @Column(name = "prezzo", nullable = false)
    private double prezzo;

    public Abbonamento() {
    }

    public Abbonamento(PuntoVendita puntoVendita, Tessera tessera, LocalDate data_emmissione, LocalDate data_scadenza, TipologiaAbbonamento tipologia_abbonamento) {
        this.puntoVendita = puntoVendita;
        this.tessera = tessera;
        this.codice_univoco_abbonamento = generaCodice();
        this.data_emmissione = data_emmissione;
        this.data_scadenza = data_scadenza;
        this.tipologia_abbonamento = tipologia_abbonamento;
        this.prezzo = definisciPrezzi();
    }

    private long generaCodice() {
        Random random = new Random();
        return 100000 + random.nextInt(900000);
    }

    public double definisciPrezzi() {
        switch (this.tipologia_abbonamento) {
            case MENSILE:
                return 30.0;
            case SETTIMANALE:
                return 10.0;
            default:
                return 0.0;
        }
    }

    public Tessera getTessera() {
        return tessera;
    }

    public void setTessera(Tessera tessera) {
        this.tessera = tessera;
    }

    public long getId_abbonamento() {
        return id_abbonamento;
    }

    public PuntoVendita getPuntoVendita() {
        return puntoVendita;
    }

    public void setPuntoVendita(PuntoVendita puntoVendita) {
        this.puntoVendita = puntoVendita;
    }

    public long getCodice_univoco_abbonamento() {
        return codice_univoco_abbonamento;
    }

    public void setCodice_univoco_abbonamento(long codice_univoco_abbonamento) {
        this.codice_univoco_abbonamento = codice_univoco_abbonamento;
    }

    public LocalDate getData_emmissione() {
        return data_emmissione;
    }

    public void setData_emmissione(LocalDate data_emmissione) {
        this.data_emmissione = data_emmissione;
    }

    public LocalDate getData_scadenza() {
        return data_scadenza;
    }

    public void setData_scadenza(LocalDate data_scadenza) {
        this.data_scadenza = data_scadenza;
    }

    public Enum getTipologia_abbonamento() {
        return tipologia_abbonamento;
    }

    public void setTipologia_abbonamento(TipologiaAbbonamento tipologia_abbonamento) {
        this.tipologia_abbonamento = tipologia_abbonamento;
    }
    public boolean isValido() {
        // Controlla se la data di scadenza è dopo la data attuale
        return LocalDate.now().isBefore(data_scadenza) || LocalDate.now().isEqual(data_scadenza);
    }

    public double getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(double prezzo) {
        this.prezzo = prezzo;
    }

    @Override
    public String toString() {
        return "Abbonamento{" +
                "id_abbonamento=" + id_abbonamento +
                ", puntoVendita=" + puntoVendita +
                ", tessera=" + tessera +
                ", trattaAbbonamenti=" + trattaAbbonamenti +
                ", codice_univoco_abbonamento=" + codice_univoco_abbonamento +
                ", data_emmissione=" + data_emmissione +
                ", data_scadenza=" + data_scadenza +
                ", tipologia_abbonamento=" + tipologia_abbonamento +
                ", prezzo=" + prezzo +
                '}';
    }
}
