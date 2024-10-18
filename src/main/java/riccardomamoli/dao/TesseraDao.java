package riccardomamoli.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import riccardomamoli.entities.Tessera;

public class TesseraDao {
    private final EntityManager em;

    public TesseraDao(EntityManager em) {
        this.em = em;
    }

    public void addTessera(Tessera tessera) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        em.persist(tessera);
        tx.commit();
        System.out.println(" ");
        System.out.println("Congratulazioni! La tua tessera è stata registrata correttamente.");
        System.out.println(" ");
        System.out.println("Utente: " + (tessera.getUtente().getNome()) + (tessera.getUtente().getCognome()));
        System.out.println("Numero Tessera :" + tessera.getNumero_tessera());
        System.out.println("Data Rilascio: " + tessera.getData_rilascio());
        System.out.println("Data Scadenza: " + tessera.getData_scadenza());
        System.out.println("Status: " + ((tessera.isAttiva() ? "attiva" : "scaduta")));
        System.out.println(" ");
    }

    public void removeTessera(Tessera tessera) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.remove(tessera);
        tx.commit();
        System.out.println("La tessera numero: " + tessera.getNumero_tessera() + "è stata eliminata");
    }
}