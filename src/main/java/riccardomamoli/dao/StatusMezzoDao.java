package riccardomamoli.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import riccardomamoli.entities.*;

import java.util.List;

public class StatusMezzoDao {
    private final EntityManager em;

    public StatusMezzoDao(EntityManager em) {

        this.em = em;
    }

    public List<StatusMezzo> findAllStatus() {

        TypedQuery<StatusMezzo> query = em.createQuery("SELECT sm FROM StatusMezzo sm", StatusMezzo.class);
        List<StatusMezzo> risultati = query.getResultList();

        if (risultati.isEmpty()) {
            System.out.println("Non ci sono status a database.");
        } else {

            for (int i = 0; i < risultati.size(); i++) {
                StatusMezzo sm = risultati.get(i);
                System.out.println(" ");
                System.out.println("Status numero " + (i + 1) + ":");
                System.out.println("ID: " + sm.getId_Status());
                System.out.println(" ");
                System.out.println("ID Mezzo Associato: " + sm.getMezzo());
                System.out.println("Data inizio: " + sm.getDataIniizio());
                System.out.println("Data fine: " + sm.getDataFine());
                System.out.println(" ");
            }
        }
        return risultati;
    }

    public void addStatusMezzo(StatusMezzo statusMezzo) {
        EntityTransaction tx = em.getTransaction();

        TypedQuery<StatusMezzo> query = em.createQuery("SELECT sm FROM StatusMezzo sm WHERE sm.mezzo.id = :mezzoId ORDER BY sm.dataFine DESC", StatusMezzo.class);
        query.setParameter("mezzoId", statusMezzo.getMezzo().getId_Mezzo());

        List<StatusMezzo> risultati = query.setMaxResults(1).getResultList();

        if (!risultati.isEmpty()) {
            StatusMezzo ultimoStatus = risultati.getFirst();
            if (statusMezzo.getDataIniizio().isBefore(ultimoStatus.getDataFine()) ||
                    statusMezzo.getDataIniizio().isEqual(ultimoStatus.getDataFine()) || statusMezzo.getDataIniizio().isAfter(statusMezzo.getDataFine())) {
                throw new IllegalArgumentException("Errore nell'inserimento delle date.");
            }
        }

        tx.begin();
        em.persist(statusMezzo);
        tx.commit();
    }

    public void updateStatusMezzo(StatusMezzo statusMezzo) {
        EntityTransaction tx = em.getTransaction();

        TypedQuery<StatusMezzo> query = em.createQuery("SELECT sm FROM StatusMezzo sm WHERE sm.mezzo.id = :mezzoId ORDER BY sm.dataFine DESC", StatusMezzo.class);
        query.setParameter("mezzoId", statusMezzo.getMezzo().getId_Mezzo());

        List<StatusMezzo> risultati = query.setMaxResults(1).getResultList();

        if (!risultati.isEmpty()) {
            StatusMezzo ultimoStatus = risultati.getFirst();
            tx.begin();
            ultimoStatus.setDataFine(statusMezzo.getDataFine());
            em.merge(ultimoStatus);
            tx.commit();
        } else {
            System.out.println("Nessun status trovato per il mezzo richiesto.");
        }
    }



    public void deleteStatusMezzo(long id_Status) throws Exception {
        em.getTransaction().begin();
        StatusMezzo statusMezzo = em.find(StatusMezzo.class, id_Status);
        if (statusMezzo == null) {
            em.getTransaction().rollback();
            throw new Exception("Status con id " + id_Status + " non trovato.");
        }
        em.remove(statusMezzo);
        em.getTransaction().commit();
    }

}
