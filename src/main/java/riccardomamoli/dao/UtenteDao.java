package riccardomamoli.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import riccardomamoli.entities.Utente;

import java.util.List;

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
        System.out.println("L'utente " + utente.getNome() + " " + utente.getCognome() + " è stato aggiunto.");
    }
    public Utente findById(long id) {
        return em.find(Utente.class, id);
    }
    public void removeUtente(Utente utente) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.remove(utente);
        tx.commit();
        System.out.println("L'utente " + utente.getNome() + "è stato eliminato");
    }
    public void printAllUtenti() {
        TypedQuery<Utente> query = em.createQuery("SELECT u FROM Utente u", Utente.class);
        List<Utente> utenti = query.getResultList();

        if (utenti.isEmpty()) {
            System.out.println("Non ci sono utenti disponibili.");
        } else {
            System.out.println("Lista degli utenti:");
            for (Utente utente : utenti) {
                System.out.println("Nome: " + utente.getNome() + ", Cognome: " + utente.getCognome());
            }
        }
    }
}