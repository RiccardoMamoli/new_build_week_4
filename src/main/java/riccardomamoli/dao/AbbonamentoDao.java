package riccardomamoli.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import riccardomamoli.entities.Abbonamento;
import riccardomamoli.entities.Utente;

public class AbbonamentoDao {
    private final EntityManager em;

    public AbbonamentoDao(EntityManager em) {
        this.em = em;
    }

    public void addAbbonamento(Abbonamento abbonamento) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(abbonamento);
        tx.commit();
        System.out.println("l'abbonamento numero" + abbonamento.getId_abbonamento() + "è stato sottoscritto");
    }
    public void updateAbbonamento(Abbonamento abbonamento) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.merge(abbonamento);
        tx.commit();
        System.out.println("l'abbonamento è stato aggiornato");
    }
}