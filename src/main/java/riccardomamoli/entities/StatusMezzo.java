package riccardomamoli.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class StatusMezzo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_Status;

    @Enumerated(EnumType.STRING)
    private StatoMezzo stato;

    private LocalDate dataIniizio;
    private LocalDate dataFine;

    @ManyToOne
    @JoinColumn(name = "id_mezzo")
    private Mezzo mezzo;

    public StatusMezzo() {
    }

    public StatusMezzo(StatoMezzo stato, LocalDate dataIniizio, LocalDate dataFine, Mezzo mezzo) {
        this.stato = stato;
        this.dataIniizio = dataIniizio;
        this.dataFine = dataFine;
        this.mezzo = mezzo;
    }

    public Mezzo getMezzo() {
        return mezzo;
    }

    public void setMezzo(Mezzo mezzo) {
        this.mezzo = mezzo;
    }

    public LocalDate getDataFine() {
        return dataFine;
    }

    public void setDataFine(LocalDate dataFine) {
        this.dataFine = dataFine;
    }

    public LocalDate getDataIniizio() {
        return dataIniizio;
    }

    public void setDataIniizio(LocalDate dataIniizio) {
        this.dataIniizio = dataIniizio;
    }

    public StatoMezzo getStato() {
        return stato;
    }

    public void setStato(StatoMezzo stato) {
        this.stato = stato;
    }

    public Long getId_Status() {
        return id_Status;
    }

    public void setId_Status(Long id_Status) {
        this.id_Status = id_Status;
    }

    @Override
    public String toString() {
        return "StatusMezzo{" +
                "id_Status=" + id_Status +
                ", stato=" + stato +
                ", dataIniizio=" + dataIniizio +
                ", dataFine=" + dataFine +
                ", mezzo=" + mezzo +
                '}';
    }
}
