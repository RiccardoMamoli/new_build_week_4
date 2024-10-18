package riccardomamoli.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.util.List;

@Entity
@DiscriminatorValue("distributore_fisico")


public class DistributoreFisico extends PuntoVendita {

    public DistributoreFisico() {
    }

     /* public DistributoreFisico(List<Abbonamento> abbonamenti, List<Biglietto> biglietti) {
        super(abbonamenti, biglietti);

    }

      */

    @Override
    public String toString() {
        return "DistributoreFisico{} " + super.toString();
    }

    public Boolean isAttivo() {
        return null;
    }
}
