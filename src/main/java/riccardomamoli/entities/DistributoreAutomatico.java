package riccardomamoli.entities;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.util.List;

@Entity
@DiscriminatorValue("distributore_automatico")


public class DistributoreAutomatico extends PuntoVendita {

    @Column(name = "attivo")
    private boolean attivo;

    public DistributoreAutomatico() {
    }

    public DistributoreAutomatico(List<Abbonamento> abbonamenti, List<Biglietto> biglietti, boolean attivo) {
        super(abbonamenti, biglietti);
        this.attivo = attivo;
    }

    public boolean isAttivo() {
        return attivo;
    }

    public void setAttivo(boolean attivo) {
        this.attivo = attivo;
    }

    @Override
    public String toString() {
        return "DistributoreAutomatico{" +
                "attivo=" + attivo +
                "} " + super.toString();
    }
}
