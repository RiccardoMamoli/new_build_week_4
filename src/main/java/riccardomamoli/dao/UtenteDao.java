package riccardomamoli.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import riccardomamoli.entities.Utente;

public class UtenteDao{
    private final EntityManager em;

    public UtenteDao(EntityManager em) {
        this.em = em;
    }

    public void addUtente(Utente utente) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(utente);
        tx.commit();
        System.out.println("l'utente " + utente.getNome() + utente.getCognome() + "è stato aggiunto");
    }
    public void removeUtente(Utente utente) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.remove(utente);
        tx.commit();
        System.out.println("l'utente " + utente.getNome() + "è stato eliminato");
    }
}