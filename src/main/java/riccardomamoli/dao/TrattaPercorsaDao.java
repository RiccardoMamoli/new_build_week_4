package riccardomamoli.dao;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import riccardomamoli.entities.StatusMezzo;
import riccardomamoli.entities.TrattaPercorsa;

import java.util.List;

public class TrattaPercorsaDao {
    private final EntityManager em;

    public TrattaPercorsaDao(EntityManager em) {

        this.em = em;
    }

    public List<TrattaPercorsa> findAllStatus() {

        TypedQuery<TrattaPercorsa> query = em.createQuery("SELECT tp FROM TrattaPercorsa tp", TrattaPercorsa.class);
        List<TrattaPercorsa> risultati = query.getResultList();

        if (risultati.isEmpty()) {
            System.out.println("Non ci sono status a database.");
        } else {

            for (int i = 0; i < risultati.size(); i++) {
                TrattaPercorsa tp = risultati.get(i);
                System.out.println(" ");
                System.out.println("Tratta percorsa " + (i + 1) + ":");
                System.out.println("ID: " + tp.getId_TrattaPercorsa());
                System.out.println(" ");
                System.out.println("ID Mezzo Associato: " + tp.getMezzo().getId_Mezzo());
                System.out.println("Orario Partenza: " + tp.getOrarioPartenza());
                System.out.println("Orario Arrivo: " + tp.getOrarioArrivo());
                System.out.println("Tempo previsto di percorrenza: " + tp.getTratta().getTempoPrevisto());
                System.out.println("Tempo effettivo di percorrenza: " + tp.getOrarioEffettivo());
                System.out.println(" ");
            }
        }
        return risultati;
    }

    public void addTrattaPercorsa(TrattaPercorsa trattaPercorsa) {
        EntityTransaction tx = em.getTransaction();

        TypedQuery<TrattaPercorsa> query = em.createQuery("SELECT tp FROM TrattaPercorsa tp WHERE tp.mezzo.id = :mezzoId ORDER BY tp.dataFine DESC", TrattaPercorsa.class);
        query.setParameter("mezzoId", trattaPercorsa.getMezzo().getId_Mezzo());

        List<TrattaPercorsa> risultati = query.setMaxResults(1).getResultList();

        if (!risultati.isEmpty()) {
            TrattaPercorsa ultimaTrattaPerc = risultati.getFirst();
            if (ultimaTrattaPerc.getOrarioPartenza().isBefore(trattaPercorsa.getOrarioArrivo()) ||
                    ultimaTrattaPerc.getOrarioPartenza().isEqual(trattaPercorsa.getOrarioArrivo()) || ultimaTrattaPerc.getOrarioPartenza().isAfter(ultimaTrattaPerc.getOrarioArrivo())) {
                throw new IllegalArgumentException("Errore nell'inserimento degli orari.");
            }
        }

        tx.begin();
        em.persist(trattaPercorsa);
        tx.commit();
    }

    public void updateTrattaPercorsa(TrattaPercorsa trattaPercorsa) {
        EntityTransaction tx = em.getTransaction();

        TypedQuery<TrattaPercorsa> query = em.createQuery("SELECT tp FROM TrattaPercorsa tp WHERE tp.mezzo.id = :mezzoId ORDER BY tp.dataFine DESC", TrattaPercorsa.class);
        query.setParameter("mezzoId", trattaPercorsa.getMezzo().getId_Mezzo());

        List<TrattaPercorsa> risultati = query.setMaxResults(1).getResultList();

        if (!risultati.isEmpty()) {
            TrattaPercorsa ultimaTrattaPerc = risultati.getFirst();
            tx.begin();
            ultimaTrattaPerc.setOrarioPartenza(trattaPercorsa.getOrarioPartenza());
            ultimaTrattaPerc.setOrarioArrivo(trattaPercorsa.getOrarioArrivo());
            em.merge(trattaPercorsa);
            tx.commit();
        } else {
            System.out.println("Nessun status trovato per il mezzo richiesto.");
        }
    }

}



