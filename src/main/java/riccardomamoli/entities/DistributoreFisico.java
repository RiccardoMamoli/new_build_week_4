package riccardomamoli.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("distributore_fisico")


public class DistributoreFisico extends PuntoVendita {

    public DistributoreFisico() {
    }

    @Override
    public String toString() {
        return "DistributoreFisico{} " + super.toString();
    }
}
