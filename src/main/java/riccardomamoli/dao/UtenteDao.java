package riccardomamoli.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import riccardomamoli.entities.Abbonamento;
import riccardomamoli.entities.Tessera;
import riccardomamoli.entities.Utente;

import java.util.Comparator;
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
        System.out.println(" ");
        System.out.println("Congratulazioni! Ti sei registrato.");
        System.out.println(" ");
        System.out.println("Ecco qui la tua scheda utente: ");
        System.out.println(" ");
        System.out.println("Nome: " + utente.getNome());
        System.out.println("Cognome: "+ utente.getCognome());
        System.out.println("Data: " + utente.getData_di_nascita());
        System.out.println("Tipologia utente: " + utente.getTipologiaUtente());
        System.out.println(" ");
    }


    public Utente findById(long id) {
        return em.find(Utente.class, id);
    }

    public void printByUser(Utente utente , Tessera tessera) {

        System.out.println("Ecco qui la tua scheda utente: ");
        System.out.println(" ");
        System.out.println("Nome: " + utente.getNome());
        System.out.println("Cognome: "+ utente.getCognome());
        System.out.println("Data: " + utente.getData_di_nascita());
        System.out.println("Tipologia utente: " + utente.getTipologiaUtente());
        System.out.println(" ");
        System.out.println("Numero Tessera: " + tessera.getNumero_tessera());
        System.out.println("Data Rilascio: " + tessera.getData_rilascio());
        System.out.println("Data Scadenza: " + tessera.getData_scadenza());
        System.out.println("Status: " + (tessera.isAttiva() ? "Attiva" : "Scaduta"));
        System.out.println("");

        List<Abbonamento> abbonamenti = tessera.getStoricoAbbonamenti();
        if (abbonamenti != null && !abbonamenti.isEmpty()) {
            Abbonamento ultimoAbbonamentoValido = abbonamenti.stream()
                    .filter(Abbonamento::isValido)
                    .max(Comparator.comparing(Abbonamento::getData_scadenza))
                    .orElse(null);

            if (ultimoAbbonamentoValido != null) {
                System.out.println("Abbonamento attivo:");
                System.out.println("Tipologia: " + ultimoAbbonamentoValido.getTipologia_abbonamento());
                System.out.println("Data di Emissione: " + ultimoAbbonamentoValido.getData_emmissione());
                System.out.println("Data di Scadenza: " + ultimoAbbonamentoValido.getData_scadenza());
                System.out.println("Prezzo: " + ultimoAbbonamentoValido.getPrezzo());
            } else {
                System.out.println("Non ci sono abbonamenti attivi.");
            }
        } else {
            System.out.println("Non ci sono abbonamenti associati a questa tessera.");
        }


    }

    public boolean findTessera(int id_utente) {
        TypedQuery<Tessera> query = em.createQuery("SELECT t FROM Tessera t WHERE t.utente.id = :idUtente", Tessera.class);
        query.setParameter("idUtente", id_utente);

        List<Tessera> tessere = query.getResultList();

        return !tessere.isEmpty();
    }
    public void printById(int id_user) {

        Utente utente = findById(id_user);

        System.out.println("Ecco qui la tua scheda utente: ");
        System.out.println(" ");
        System.out.println("Nome: " + utente.getNome());
        System.out.println("Cognome: "+ utente.getCognome());
        System.out.println("Data: " + utente.getData_di_nascita());
        System.out.println("Tipologia utente: " + utente.getTipologiaUtente());
        System.out.println(" ");
    }

    public void trovaStoricoAbbonamenti(Tessera tessera) {
        TypedQuery<Abbonamento> query = em.createQuery("SELECT a FROM Abbonamento a WHERE a.tessera = :tessera ORDER BY a.data_emmissione ASC", Abbonamento.class);
        query.setParameter("tessera", tessera);
        List<Abbonamento> storicoAbbonamenti = query.getResultList();

        for (Abbonamento abbonamento : storicoAbbonamenti) {
            System.out.println("Abbonamento: ");
            System.out.println("Codice Univoco: " + abbonamento.getCodice_univoco_abbonamento());
            System.out.println("Data di emissione: " + abbonamento.getData_emmissione());
            System.out.println("Data di scadenza: " + abbonamento.getData_scadenza());
            System.out.println("Prezzo: " + abbonamento.getPrezzo());
            System.out.println("Tipologia: " + abbonamento.getTipologia_abbonamento());
        }

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
                System.out.println(" ");
                System.out.println("Nome: " + utente.getNome() + ", Cognome: " + utente.getCognome());
                System.out.println(" ");
            }
        }
    }
}