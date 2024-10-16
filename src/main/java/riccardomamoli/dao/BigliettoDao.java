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
        System.out.println("Ecco il tuo biglietto numero: " + biglietto.getCodice_univoco_biglietto());
        System.out.println("Tratta " + tratta.getZonaPartenza() + " - " + tratta.getCapolinea());
        System.out.println("costo: " + tratta.getPrezzo());
        System.out.println("Emesso il: " + biglietto.getData_emissione());
    }

    public void removeBiglietto(Tratta tratta) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.remove(tratta);
        tx.commit();
        System.out.println("La tratta " + tratta.getId_tratta() + "con partenza da" + tratta.getZonaPartenza() + "e capolinea" + tratta.getCapolinea() + "è stata eliminata");
    }

}
