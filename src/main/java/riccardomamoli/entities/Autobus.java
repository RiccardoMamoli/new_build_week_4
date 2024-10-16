package riccardomamoli.entities;

import jakarta.persistence.Entity;

import java.util.List;

@Entity

public class Autobus extends Mezzo {

    public Autobus(int capienza, StatoMezzo statoAttuale, List<TrattaPercorsa> trattePercorse, List<StatusMezzo> stati) {
        super(capienza, statoAttuale, trattePercorse, stati);
    }

    @Override
    public String toString() {
        return "Autobus{}";
    }
}