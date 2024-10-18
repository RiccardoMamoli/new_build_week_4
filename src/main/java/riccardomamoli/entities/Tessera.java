package riccardomamoli.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tessera")

public class Tessera {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "id_utente")
    private Utente utente;

    @OneToMany
    @JoinColumn(name = "id_abbonamento")
    private List<Abbonamento> storicoAbbonamenti ;

    @Column(name = "numero_tessera", unique = true)
    private String numero_tessera;

    @Column(name = "data_rilascio")
    private LocalDate data_rilascio;

    @Column(name = "data_scadenza")
    private LocalDate data_scadenza;

    @Column(name = "tessera_attiva")
    private boolean attiva;

    public Tessera() {
    }

    public Tessera(Utente utente, LocalDate data_rilascio, LocalDate data_scadenza, boolean attiva) {
        this.utente = utente;
        this.numero_tessera = generaCodice();
        this.data_rilascio = data_rilascio;
        this.data_scadenza = data_scadenza;
        this.attiva = attiva;
    }

    private String generaCodice() {
        return UUID.randomUUID().toString();
    }

    public long getId() {
        return id;
    }

    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    public String getNumero_tessera() {
        return numero_tessera;
    }

    public void setNumero_tessera(String numero_tessera) {
        this.numero_tessera = numero_tessera;
    }

    public LocalDate getData_rilascio() {
        return data_rilascio;
    }

    public void setData_rilascio(LocalDate data_rilascio) {
        this.data_rilascio = data_rilascio;
    }

    public LocalDate getData_scadenza() {
        return data_scadenza;
    }

    public void setData_scadenza(LocalDate data_scadenza) {
        this.data_scadenza = data_scadenza;
    }

    public boolean isAttiva() {
        return attiva;
    }

    public void setAttiva(boolean attiva) {
        this.attiva = attiva;
    }

    public List<Abbonamento> getStoricoAbbonamenti() {
        return storicoAbbonamenti;
    }

    public void setStoricoAbbonamenti(List<Abbonamento> storicoAbbonamenti) {
        this.storicoAbbonamenti = storicoAbbonamenti;
    }

    @Override
    public String toString() {
        return "Tessera{" +
                "id=" + id +
                ", utente=" + utente +
                ", numero_tessera='" + numero_tessera + '\'' +
                ", data_rilascio=" + data_rilascio +
                ", data_scadenza=" + data_scadenza +
                ", attiva=" + attiva +
                '}';
    }
}
