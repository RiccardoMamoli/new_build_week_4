package riccardomamoli.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class TrattaPercorsa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_TrattaPercorsa;

    @ManyToOne
    @JoinColumn(name = "id_tratta")
    private Tratta tratta;

    @ManyToOne
    @JoinColumn(name = "id_mezzo")
    private Mezzo mezzo;

    private LocalDateTime orarioPartenza;
    private LocalDateTime orarioArrivo;
    private double orarioEffettivo;

    public TrattaPercorsa(Tratta tratta, Mezzo mezzo, LocalDateTime orarioPartenza, LocalDateTime orarioArrivo, double orarioEffettivo) {
        this.tratta = tratta;
        this.mezzo = mezzo;
        this.orarioPartenza = orarioPartenza;
        this.orarioArrivo = orarioArrivo;
        this.orarioEffettivo = orarioEffettivo;
    }

    public Long getId_TrattaPercorsa() {
        return id_TrattaPercorsa;
    }

    public void setId_TrattaPercorsa(Long id_TrattaPercorsa) {
        this.id_TrattaPercorsa = id_TrattaPercorsa;
    }

    public Tratta getTratta() {
        return tratta;
    }

    public void setTratta(Tratta tratta) {
        this.tratta = tratta;
    }

    public Mezzo getMezzo() {
        return mezzo;
    }

    public void setMezzo(Mezzo mezzo) {
        this.mezzo = mezzo;
    }

    public LocalDateTime getOrarioPartenza() {
        return orarioPartenza;
    }

    public void setOrarioPartenza(LocalDateTime orarioPartenza) {
        this.orarioPartenza = orarioPartenza;
    }

    public LocalDateTime getOrarioArrivo() {
        return orarioArrivo;
    }

    public void setOrarioArrivo(LocalDateTime orarioArrivo) {
        this.orarioArrivo = orarioArrivo;
    }

    public double getOrarioEffettivo() {
        return orarioEffettivo;
    }

    public void setOrarioEffettivo(double orarioEffettivo) {
        this.orarioEffettivo = orarioEffettivo;
    }

    @Override
    public String toString() {
        return "TrattaPercorsa{" +
                "id_TrattaPercorsa=" + id_TrattaPercorsa +
                ", tratta=" + tratta +
                ", mezzo=" + mezzo +
                ", orarioPartenza=" + orarioPartenza +
                ", orarioArrivo=" + orarioArrivo +
                ", orarioEffettivo=" + orarioEffettivo +
                '}';
    }
}

