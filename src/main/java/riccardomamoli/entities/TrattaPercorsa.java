package riccardomamoli.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.LocalTime;

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

    private LocalTime orarioPartenza;
    private LocalTime orarioArrivo;
    private int orarioEffettivo;

    public TrattaPercorsa(){};

    public TrattaPercorsa(Tratta tratta, Mezzo mezzo, LocalTime orarioPartenza, LocalTime orarioArrivo, int orarioEffettivo) {
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

    public LocalTime getOrarioPartenza() {
        return orarioPartenza;
    }

    public void setOrarioPartenza(LocalTime orarioPartenza) {
        this.orarioPartenza = orarioPartenza;
    }

    public LocalTime getOrarioArrivo() {
        return orarioArrivo;
    }

    public void setOrarioArrivo(LocalTime orarioArrivo) {
        this.orarioArrivo = orarioArrivo;
    }

    public double getOrarioEffettivo() {
        return orarioEffettivo;
    }

    public void setOrarioEffettivo(int orarioEffettivo) {
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

