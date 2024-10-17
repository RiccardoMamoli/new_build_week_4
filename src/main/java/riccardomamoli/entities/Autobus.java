package riccardomamoli.entities;

import jakarta.persistence.Entity;

import java.util.List;

@Entity

public class Autobus extends Mezzo {

    public Autobus() {
    }

    public Autobus(int capienza, StatoMezzo statoAttuale) {
        super(capienza, statoAttuale);
    }

    @Override
    public String toString() {
        return "Autobus{" + super.toString() + "}";

    }
}