package riccardomamoli.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "tratta_abbonamento")

public class TrattaAbbonamento {

    @Id
    @GeneratedValue
    @Column(name = "id_tratta_abbonamento")
    private Long id_tratta_abbonamento;

    @ManyToOne
    @JoinColumn(name = "id_tratta")
    private Tratta tratta;

    @ManyToOne
    @JoinColumn(name = "id_abbonamento")
    private Abbonamento abbonamento;

    public TrattaAbbonamento() {
    }

    public TrattaAbbonamento(Tratta tratta, Abbonamento abbonamento) {
        this.tratta = tratta;
        this.abbonamento = abbonamento;
    }

    public Tratta getTratta() {
        return tratta;
    }

    public void setTratta(Tratta tratta) {
        this.tratta = tratta;
    }

    public Abbonamento getAbbonamento() {
        return abbonamento;
    }

    public void setAbbonamento(Abbonamento abbonamento) {
        this.abbonamento = abbonamento;
    }

    @Override
    public String toString() {
        return "TrattaAbbonamento{" +
                "tratta=" + tratta +
                ", abbonamento=" + abbonamento +
                '}';
    }
}
