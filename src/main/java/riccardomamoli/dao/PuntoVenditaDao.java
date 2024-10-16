package riccardomamoli.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import riccardomamoli.entities.Abbonamento;
import riccardomamoli.entities.Biglietto;
import riccardomamoli.entities.DistributoreAutomatico;
import riccardomamoli.entities.PuntoVendita;

import java.time.LocalDate;
import java.util.List;

public class PuntoVenditaDao {
    private final EntityManager em;

    public PuntoVenditaDao(EntityManager em) {
        this.em = em;
    }

    // Aggiungi distributore
    public void creazioneDistributore(PuntoVendita puntoVendita) {
        System.out.println("Inserisci l'id per  il nuovo distributore:");
        System.out.print("Id: ");
        em.getTransaction().begin();
        em.persist(puntoVendita);
        em.getTransaction().commit();
    }

    // Aggiungi biglietto
    public void creazioneBiglietto(Biglietto biglietto) {
        em.getTransaction().begin();
        em.persist(biglietto);
        em.getTransaction().commit();
    }

    // Rimuovi un distributore
    public void rimuoviPuntovendita(long id_distributore) throws Exception {
        em.getTransaction().begin();
        PuntoVendita distributore = em.find(PuntoVendita.class, id_distributore);
        if (distributore == null) {
            em.getTransaction().rollback();
            throw new Exception("Distributore non trovato tramite id: " + id_distributore);
        }
        em.remove(distributore);
        em.getTransaction().commit();
    }

    // Cerca distributore tramite id
    public PuntoVendita ricercaPuntovendita(long id_Distributore) throws Exception {
        PuntoVendita trovato = em.find(PuntoVendita.class, id_Distributore);
        if (trovato == null) {
            throw new Exception("Distributore non trovato per ID: " + id_Distributore);
        }
        return trovato;
    }

    // Lista biglietti per lasso di tempo
    public List<Biglietto> trovaBiglietti(long distributoreId, LocalDate start, LocalDate end) {
        String queryStr = "SELECT b FROM Biglietto b WHERE b.puntoVendita.id = :distributoreId AND b.data_emissione BETWEEN :start AND :end";
        TypedQuery<Biglietto> query = em.createQuery(queryStr, Biglietto.class);
        query.setParameter("distributoreId", distributoreId);
        query.setParameter("start", start);
        query.setParameter("end", end);
        return query.getResultList();
    }
    // Lista Abbonamenti per lasso di tempo
    public List<Abbonamento> trovaAbbonamenti(long distributoreId, LocalDate start, LocalDate end) {
        String queryStr = "SELECT a FROM Abbonamento a WHERE a.puntoVendita.id = :distributoreId AND a.data_emmissione BETWEEN :start AND :end";
        TypedQuery<Abbonamento> query = em.createQuery(queryStr, Abbonamento.class);
        query.setParameter("distributoreId", distributoreId);
        query.setParameter("start", start);
        query.setParameter("end", end);
        return query.getResultList();
    }

    // Ricerca attivo/nonAttivo Distributore
    public boolean isActive(long id) {
        PuntoVendita distributore = em.find(PuntoVendita.class, id);
        return distributore instanceof DistributoreAutomatico && ((DistributoreAutomatico) distributore).isAttivo();
    }


    // switch attivo/ non distributore
    public void updateActive(long id, boolean active) {
        em.getTransaction().begin();

        PuntoVendita distributore = em.find(PuntoVendita.class, id);
        if (distributore instanceof DistributoreAutomatico) {
            ((DistributoreAutomatico) distributore).setAttivo(active);
        }

        em.merge(distributore);
        em.getTransaction().commit();
    }

    public List<PuntoVendita> findAll() {
        return em.createQuery("SELECT pv FROM PuntoVendita pv", PuntoVendita.class).getResultList();
    }


}