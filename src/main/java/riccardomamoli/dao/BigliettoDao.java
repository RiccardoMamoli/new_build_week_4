package riccardomamoli.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import riccardomamoli.entities.Biglietto;
import riccardomamoli.entities.Tratta;

import java.time.LocalDate;
import java.util.List;

public class BigliettoDao {

    private final EntityManager em;

    public BigliettoDao(EntityManager em) {

        this.em = em;
    }

    public void addBiglietto(Biglietto biglietto, Tratta tratta) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(biglietto);
        tx.commit();
        System.out.println(" ");
        System.out.println("Ecco il tuo biglietto:");
        System.out.println(" ");
        System.out.println("Numero " + biglietto.getCodice_univoco_biglietto());
        System.out.println("Tratta " + tratta.getZonaPartenza() + " - " + tratta.getCapolinea());
        System.out.println("Costo: " + tratta.getPrezzo());
        System.out.println("Emesso il: " + biglietto.getData_emissione());
        System.out.println(" ");
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

    public void rimuoviBiglietto(long bigliettoId) {
        Biglietto biglietto = em.find(Biglietto.class, bigliettoId);
        if (biglietto != null) {
            em.getTransaction().begin();
            em.remove(biglietto);
            em.getTransaction().commit();
            System.out.println("Il biglietto con ID " + bigliettoId + " è stato rimosso.");
        } else {
            System.out.println("Nessun biglietto trovato con ID: " + bigliettoId);
        }
    }

    public Biglietto findById(long bigliettoId) {
        return em.find(Biglietto.class, bigliettoId);
    }

    
    public void printBigliettoDetails(long bigliettoId) {
        Biglietto biglietto = findById(bigliettoId); // Cerca il biglietto per ID

        if (biglietto != null) {
            // Stampa i dettagli del biglietto
            System.out.println("Dettagli del Biglietto:");
            System.out.println("ID: " + biglietto.getId_biglietto());
            System.out.println("Codice Unico: " + biglietto.getCodice_univoco_biglietto());
            System.out.println("Data di Emissione: " + biglietto.getData_emissione());
            System.out.println("Timbrato: " + (biglietto.isTimbrato() ? "Sì" : "No"));
            // Aggiungi ulteriori dettagli se necessario, come mezzo, punto vendita, tratta, ecc.


            System.out.println("Punto Vendita: " + biglietto.getPuntoVendita());
            System.out.println("Tratta: " + biglietto.getTratta().getZonaPartenza() + " - " + biglietto.getTratta().getCapolinea());
        } else {
            System.out.println("Nessun biglietto trovato con ID: " + bigliettoId);
        }
    }

}
