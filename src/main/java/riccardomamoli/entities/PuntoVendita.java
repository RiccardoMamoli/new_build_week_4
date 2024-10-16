package riccardomamoli.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "punto_vendita")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_distributore", discriminatorType = DiscriminatorType.STRING)

public abstract class PuntoVendita {
    @Id
    @GeneratedValue
    @Column(name = "id_punto_vendita")
    private long id;

    @OneToMany(mappedBy = "puntoVendita")
    private List<Abbonamento> abbonamenti;

    @OneToMany(mappedBy = "puntoVendita")
    private List<Biglietto> biglietti;

    public PuntoVendita() {
    }

    public PuntoVendita(List<Abbonamento> abbonamenti, List<Biglietto> biglietti) {
        this.abbonamenti = abbonamenti;
        this.biglietti = biglietti;
    }

    public long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "PuntoVendita{" +
                "id=" + id +
                '}';
    }
}
