package riccardomamoli.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import riccardomamoli.entities.Abbonamento;
import riccardomamoli.entities.Utente;

import java.time.LocalDate;
import java.util.List;

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
    // Lista Abbonamenti per lasso di tempo
    public List<Abbonamento> trovaAbbonamenti(long distributoreId, LocalDate start, LocalDate end) {
        String queryStr = "SELECT a FROM Abbonamento a WHERE a.puntoVendita.id = :distributoreId AND a.data_emmissione BETWEEN :start AND :end";
        TypedQuery<Abbonamento> query = em.createQuery(queryStr, Abbonamento.class);
        query.setParameter("distributoreId", distributoreId);
        query.setParameter("start", start);
        query.setParameter("end", end);
        return query.getResultList();

    }
    public Abbonamento findById(long abbonamentoId) {
        return em.find(Abbonamento.class, abbonamentoId);
    }
    public void rimuoviAbbonamento(long abbonamentoId) {
        Abbonamento abbonamento = em.find(Abbonamento.class, abbonamentoId);
        if (abbonamento != null) {
            em.getTransaction().begin();
            em.remove(abbonamento);
            em.getTransaction().commit();
            System.out.println("L'abbonamento con ID " + abbonamentoId + " è stato rimosso.");
        } else {
            System.out.println("Nessun abbonamento trovato con ID: " + abbonamentoId);
        }
    }
    public void printAbbonamentoDetails(long abbonamentoId) {
        Abbonamento abbonamento = findById(abbonamentoId); // Cerca l'abbonamento per ID

        if (abbonamento != null) {
            // Stampa i dettagli dell'abbonamento
            System.out.println("Dettagli dell'Abbonamento:");
            System.out.println("ID: " + abbonamento.getId_abbonamento());
            System.out.println("Codice Unico: " + abbonamento.getCodice_univoco_abbonamento());
            System.out.println("Data di Emissione: " + abbonamento.getData_emmissione());
            System.out.println("Validità: " + (abbonamento.isValido() ? "Sì" : "No"));

            // Aggiungi ulteriori dettagli se necessario, come mezzo, punto vendita, tratta, ecc.
            System.out.println("Punto Vendita: " + abbonamento.getPuntoVendita());
            System.out.println("Tipologia: " + abbonamento.getTipologia_abbonamento());
        } else {
            System.out.println("Nessun abbonamento trovato con ID: " + abbonamentoId);
        }
    }
}