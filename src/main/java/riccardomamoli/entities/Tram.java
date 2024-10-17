package riccardomamoli.entities;

import jakarta.persistence.Entity;

import java.util.List;

@Entity

public class Tram extends Mezzo {
    public Tram(){};

    public Tram(int capienza, StatoMezzo statoAttuale ) {
        super(capienza, statoAttuale);
    }

    public Tram(int capienza, StatoMezzo statoAttuale) {
        super(capienza, statoAttuale);
    }


    @Override
    public String toString() {
        return "Tram{" + super.toString() + "}";

    }
}