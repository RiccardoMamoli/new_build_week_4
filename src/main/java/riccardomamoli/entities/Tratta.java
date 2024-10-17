package riccardomamoli.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Tratta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_tratta;

    private String zonaPartenza;
    private String capolinea;
    private int tempoPrevisto;
    private double prezzo;


    @OneToMany(mappedBy = "tratta")
    private List<TrattaPercorsa> trattePercorse;

    @OneToMany(mappedBy = "tratta")
    private List<Biglietto> biglietti;

    @OneToMany(mappedBy = "tratta")
    private List<TrattaAbbonamento> trattaAbbonamenti;

    public Tratta() {

    }

    public Tratta(String zonaPartenza, String capolinea, int tempoPrevisto, double prezzo) {
        this.zonaPartenza = zonaPartenza;
        this.capolinea = capolinea;
        this.tempoPrevisto = tempoPrevisto;
        this.prezzo = prezzo;
    }

    public Long getId_tratta() {
        return id_tratta;
    }

    public void setId_tratta(Long id_tratta) {
        this.id_tratta = id_tratta;
    }

    public String getZonaPartenza() {
        return zonaPartenza;
    }

    public void setZonaPartenza(String zonaPartenza) {
        this.zonaPartenza = zonaPartenza;
    }

    public String getCapolinea() {
        return capolinea;
    }

    public void setCapolinea(String capolinea) {
        this.capolinea = capolinea;
    }

    public double getTempoPrevisto() {
        return tempoPrevisto;
    }

    public void setTempoPrevisto(int tempoPrevisto) {
        this.tempoPrevisto = tempoPrevisto;
    }

    public double getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(double prezzo) {
        this.prezzo = prezzo;
    }

    @Override
    public String toString() {
        return "Tratta{" +
                "id_tratta=" + id_tratta +
                ", zonaPartenza='" + zonaPartenza + '\'' +
                ", capolinea='" + capolinea + '\'' +
                ", tempoPrevisto=" + tempoPrevisto +
                ", prezzo=" + prezzo +
                '}';
    }
}