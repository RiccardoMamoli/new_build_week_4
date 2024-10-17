package riccardomamoli.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import riccardomamoli.entities.*;

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
        tx.begin();

        TypedQuery<TrattaPercorsa> query = em.createQuery("SELECT tp FROM TrattaPercorsa tp WHERE tp.mezzo.id = :mezzoId AND tp.tratta.id = :trattaId ORDER BY tp.orarioArrivo DESC", TrattaPercorsa.class);
        query.setParameter("mezzoId", trattaPercorsa.getMezzo().getId_Mezzo());
        query.setParameter("trattaId", trattaPercorsa.getTratta().getId_tratta());

        List<TrattaPercorsa> risultati = query.setMaxResults(1).getResultList();

        if (!risultati.isEmpty()) {
            TrattaPercorsa ultimaTrattaPerc = risultati.getFirst();

            if (trattaPercorsa.getOrarioPartenza().isBefore(ultimaTrattaPerc.getOrarioArrivo())){
                throw new IllegalArgumentException("Errore: l'orario di partenza della nuova tratta non può essere prima dell'orario di arrivo dell'ultima tratta.");
            }

            if (trattaPercorsa.getOrarioArrivo().isBefore(trattaPercorsa.getOrarioPartenza())) {
                throw new IllegalArgumentException("Errore: l'orario di arrivo non può essere prima dell'orario di partenza della nuova tratta.");
            }
        }

        em.persist(trattaPercorsa);
        tx.commit();
    }






    public void updateTrattaPercorsa(TrattaPercorsa trattaPercorsa) {
        EntityTransaction tx = em.getTransaction();

        TypedQuery<TrattaPercorsa> query = em.createQuery("SELECT tp FROM TrattaPercorsa tp WHERE tp.mezzo.id = :mezzoId ORDER BY tp.orarioArrivo DESC", TrattaPercorsa.class);
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

    public void printFascePerTratta(Tratta trattaSelezionata) {
        long trattaId = trattaSelezionata.getId_tratta();
        TypedQuery<TrattaPercorsa> query = em.createQuery("SELECT tp FROM TrattaPercorsa tp WHERE tp.tratta.id = :trattaId", TrattaPercorsa.class);
        query.setParameter("trattaId", trattaId);

        List<TrattaPercorsa> fasceOrarie = query.getResultList();

        if (!fasceOrarie.isEmpty()) {
            for (TrattaPercorsa trattaPercorsa : fasceOrarie) {
                System.out.println(" ");
                System.out.println((trattaPercorsa.getMezzo() instanceof Autobus ? "Autobus" : "Tram") + " numero " + trattaPercorsa.getMezzo().getId_Mezzo());
                System.out.println("Orario di partenza: " + trattaPercorsa.getOrarioPartenza());
                System.out.println("Orario di arrivo: " + trattaPercorsa.getOrarioArrivo());
                System.out.println("Capacità: " + trattaPercorsa.getMezzo().getCapienza());
                System.out.println(" ");
            }
        } else {
            System.out.println("Nessuna fascia oraria associata alla tratta con ID " + trattaId);
        }
    }



}
