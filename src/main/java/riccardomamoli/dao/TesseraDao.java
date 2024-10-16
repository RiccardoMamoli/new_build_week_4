package riccardomamoli.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import riccardomamoli.entities.Tessera;

public class TesseraDao {
    private final EntityManager em;

    public TesseraDao(EntityManager em) {
        this.em = em;
    }

    public void addTessera(Tessera tessera) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(tessera);
        tx.commit();
        System.out.println("La tessera numero: " + tessera.getNumero_tessera() + "è stata creata");
    }

    public void removeTessera(Tessera tessera) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.remove(tessera);
        tx.commit();
        System.out.println("La tessera numero: " + tessera.getNumero_tessera() + "è stata eliminata");
    }
}