package riccardomamoli.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import riccardomamoli.entities.Biglietto;
import riccardomamoli.entities.Tratta;

public class BigliettoDao {

    private final EntityManager em;

    public BigliettoDao(EntityManager em) {

        this.em = em;
    }

    public void addBiglietto(Biglietto biglietto, Tratta tratta) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(biglietto);
        tx.commit();
        System.out.println(" ");
        System.out.println("Ecco il tuo biglietto:");
        System.out.println(" ");
        System.out.println("Numero " + biglietto.getCodice_univoco_biglietto());
        System.out.println("Tratta " + tratta.getZonaPartenza() + " - " + tratta.getCapolinea());
        System.out.println("Costo: " + tratta.getPrezzo());
        System.out.println("Emesso il: " + biglietto.getData_emissione());
        System.out.println(" ");
    }
}
