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
        Abbonamento existingAbbonamento = em.createQuery("SELECT a FROM Abbonamento a WHERE a.codice_univoco_abbonamento = :codice", Abbonamento.class)
                .setParameter("codice", abbonamento.getCodice_univoco_abbonamento())
                .getResultStream()
                .findFirst()
                .orElse(null);

        if (existingAbbonamento != null) {
            System.out.println("Abbonamento with codice_univoco_abbonamento " + abbonamento.getCodice_univoco_abbonamento() + " already exists.");
            return;
        }

        em.getTransaction().begin();
        em.persist(abbonamento);
        em.getTransaction().commit();
    }


    public void updateAbbonamento(Abbonamento abbonamento) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.merge(abbonamento);
        tx.commit();
        System.out.println("l'abbonamento è stato aggiornato");
    }
}