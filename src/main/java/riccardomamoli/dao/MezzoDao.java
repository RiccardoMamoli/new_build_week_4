package riccardomamoli.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import riccardomamoli.entities.*;

import java.time.LocalDate;
import java.util.List;

public class MezzoDao {
    private final EntityManager em;

    public MezzoDao(EntityManager em) {
        this.em = em;
    }

    // Aggiungi mezzo
    public void creazioneMezzo(Mezzo mezzo) {
        em.getTransaction().begin();
        em.persist(mezzo);
        em.getTransaction().commit();
    }

    // Rimuovi un mezzo
    public void rimuovoMezzo(long id_mezzo) throws Exception {
        em.getTransaction().begin();
        Mezzo mezzo = em.find(Mezzo.class, id_mezzo);
        if (mezzo == null) {
            em.getTransaction().rollback();
            throw new Exception("Mezzo non trovato tramite id: " + id_mezzo);
        }
        em.remove(mezzo);
        em.getTransaction().commit();
    }

    // Cerca mezzo tramite id
    public Mezzo ricercoMezzo(long id_Mezzo) throws Exception {
        Mezzo trovato = em.find(Mezzo.class, id_Mezzo);
        if (trovato == null) {
            throw new Exception("Mezzo non trovato per ID: " + id_Mezzo);
        }
        return trovato;
    }

    // Ricerca stato Mezzo
    public void statoMezzo(long id) {
        Mezzo mezzo = em.find(Mezzo.class, id);
        if (mezzo != null) {
            String stato = String.valueOf(mezzo.getStatoAttuale());
            System.out.println(stato);
        }
    }

    // Cambia lo stato del Mezzo
    public void cambiaStatoMezzo(long id) {
        Mezzo mezzo = em.find(Mezzo.class, id);
        if (mezzo != null) {
            String statoAttuale = String.valueOf(mezzo.getStatoAttuale());
            String nuovoStato;

            switch (statoAttuale) {
                case "MANUTENZIONE":
                    nuovoStato = "IN_SERVIZIO";
                    break;
                case "IN_SERVIZIO":
                    nuovoStato = "MANUTENZIONE";
                    break;
                default:
                    System.out.println(statoAttuale);
                    return;
            }

            mezzo.setStatoAttuale(StatoMezzo.valueOf(nuovoStato));
            System.out.println("Stato cambiato in: " + nuovoStato);
        }
    }

    // Lista tratte per mezzo
    public List<TrattaPercorsa> trovaTrattePercorse(long id_mezzo) {
        String queryStr = "SELECT tp FROM TrattaPercorsa tp WHERE tp.mezzo.id = :mezzoId";
        TypedQuery<TrattaPercorsa> query = em.createQuery(queryStr, TrattaPercorsa.class);
        query.setParameter("mezzoId", id_mezzo);
        return query.getResultList();
    }

    //metodo per conteggio tratte di un mezzo
    public long countTratteInPeriod(Long id_Mezzo) {
        String jpql = "SELECT COUNT(t) FROM TrattaPercorsa t WHERE t.mezzo.id = :id_Mezzo ";
        TypedQuery<Long> query = em.createQuery(jpql, Long.class);
        query.setParameter("id_Mezzo", id_Mezzo);
        return query.getSingleResult();
    }

    // Calcola il tempo effettivo delle tratte
    public List<Double> getTempiEffettivi(Long idMezzo, Long idTratta) {
        String jpql = "SELECT t.orarioEffettivo FROM TrattaPercorsa t WHERE t.mezzo.id = :idMezzo AND t.tratta.id = :idTratta";
        TypedQuery<Double> query = em.createQuery(jpql, Double.class);
        query.setParameter("idMezzo", idMezzo);
        query.setParameter("idTratta", idTratta);
        return query.getResultList();
    }

    // Calcola il tempo medio effettivo di percorrenza
    public double calcolaTempoMedioEffettivo(Long idMezzo, Long idTratta) {
        String jpql = "SELECT AVG(t.orarioEffettivo) FROM TrattaPercorsa t WHERE t.mezzo.id = :idMezzo AND t.tratta.id = :idTratta";
        TypedQuery<Double> query = em.createQuery(jpql, Double.class);
        query.setParameter("idMezzo", idMezzo);
        query.setParameter("idTratta", idTratta);
        Double tempoMedio = query.getSingleResult();
        return tempoMedio != null ? tempoMedio : 0.0;
    }



}