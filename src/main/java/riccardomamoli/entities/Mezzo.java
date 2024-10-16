package riccardomamoli.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "mezzo")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_mezzo", discriminatorType = DiscriminatorType.STRING)

public abstract class Mezzo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id_Mezzo;
    private int capienza;
    @Enumerated(EnumType.STRING)
    private StatoMezzo statoAttuale;


    @OneToMany(mappedBy = "mezzo")
    private List<TrattaPercorsa> trattePercorse;

    @OneToMany(mappedBy = "mezzo")
    private List<StatusMezzo> stati;

    @OneToMany(mappedBy = "mezzo")
    private List<Biglietto> biglietti;

    public Mezzo() {

    }

    public Mezzo(int capienza, StatoMezzo statoAttuale, List<TrattaPercorsa> trattePercorse, List<StatusMezzo> stati) {
        this.capienza = capienza;
        this.statoAttuale = statoAttuale;
        this.trattePercorse = trattePercorse;
        this.stati = stati;
    }

    public Long getId_Mezzo() {
        return id_Mezzo;
    }


    public int getCapienza() {
        return capienza;
    }

    public void setCapienza(int capienza) {
        this.capienza = capienza;
    }

    public StatoMezzo getStatoAttuale() {
        return statoAttuale;
    }

    public void setStatoAttuale(StatoMezzo statoAttuale) {
        this.statoAttuale = statoAttuale;
    }


    public List<TrattaPercorsa> getTrattePercorse() {
        return trattePercorse;
    }

    public void setTrattePercorse(List<TrattaPercorsa> trattePercorse) {
        this.trattePercorse = trattePercorse;
    }

    public List<StatusMezzo> getStati() {
        return stati;
    }

    public void setStati(List<StatusMezzo> stati) {
        this.stati = stati;
    }

    @Override
    public String toString() {
        return "Mezzo{" +
                "id_Mezzo=" + id_Mezzo +
                ", capienza=" + capienza +
                ", statoAttuale=" + statoAttuale +
                ", trattePercorse=" + trattePercorse +
                ", stati=" + stati +
                '}';
    }
}