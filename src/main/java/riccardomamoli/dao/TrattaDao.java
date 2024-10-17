package riccardomamoli.dao;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import riccardomamoli.entities.*;

import java.util.List;

public class TrattaDao {
    private final EntityManager em;

    public TrattaDao(EntityManager em) {

        this.em = em;
    }

    public void addTratta(Tratta tratta) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(tratta);
        tx.commit();
        System.out.println("La tratta " + tratta.getId_tratta() + "con partenza da" + tratta.getZonaPartenza() + "e capolinea" + tratta.getCapolinea() + "è stata creata");
    }

    public void removeTratta(Tratta tratta) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.remove(tratta);
        tx.commit();
        System.out.println("La tratta " + tratta.getId_tratta() + " con partenza da " + tratta.getZonaPartenza() + " e capolinea " + tratta.getCapolinea() + " è stata eliminata");
    }


    public Tratta findById(long id) {
        Tratta trattaTrovata = em.find(Tratta.class, id);
        return trattaTrovata;
    }


    public List<Tratta> findAll() {

        TypedQuery<Tratta> query = em.createQuery("SELECT tr FROM Tratta tr", Tratta.class);
        List<Tratta> risultati = query.getResultList();

        if (risultati.isEmpty()) {
            System.out.println("Non ci sono tratte disponibili.");
        } else {

            for (int i = 0; i < risultati.size(); i++) {
                Tratta tr= risultati.get(i);
                System.out.println(" ");
                System.out.println("Tratta numero " + (i + 1) + ":");
                System.out.println("ID: " + tr.getId_tratta());
                System.out.println(" ");
                System.out.println("Stazione di partenza: " + tr.getZonaPartenza());
                System.out.println("Stazione di arrivo: " + tr.getCapolinea());
                System.out.println("Tempo di percorrenza previsto: " + tr.getTempoPrevisto() + " minuti");
                System.out.println(" ");
            }
        }
        return risultati;
    }
}
