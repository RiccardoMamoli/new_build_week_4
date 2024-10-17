package riccardomamoli;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import riccardomamoli.dao.*;
import riccardomamoli.entities.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Application {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("build_week_4");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        em.getTransaction().commit();

        // Creazione degli oggetti DAO e degli utenti
        UtenteDao udao = new UtenteDao(em);
        Utente utente1 = new Utente("franco", "rossi", TipologiaUtente.UNDER25, LocalDate.of(2000, 10, 3));
        Utente utente2 = new Utente("mattia", "gonnola", TipologiaUtente.OVER60, LocalDate.of(1890, 2, 4));
        Utente utente3 = new Utente("giacomo", "guidotti", TipologiaUtente.UNDER18, LocalDate.of(2007, 11, 9));

        PuntoVenditaDao pdao = new PuntoVenditaDao(em);
        PuntoVendita p1 = new DistributoreAutomatico();
        List<Abbonamento> abbStat= new ArrayList<>();
        List<Biglietto> bigStat= new ArrayList<>();

        LocalDate dataRilascio = LocalDate.now();
        LocalDate dataScadenza = dataRilascio.plusYears(1);
        BigliettoDao adao = new BigliettoDao(em);
        Abbonamento abb1 = new Abbonamento(p1, 243545, dataRilascio, dataScadenza, TipologiaAbbonamento.MENSILE, 30);

        TesseraDao tdao = new TesseraDao(em);
        Tessera tessera1 = new Tessera(utente1, abb1, "145sf346", dataRilascio, dataScadenza, true);


        Scanner scanner = new Scanner(System.in);


        try {
            while (true) {
                System.out.println("Che tipo di utente sei? (1: Utente, 2: Amministratore, 0: Esci)");
                int scelta = scanner.nextInt();
                scanner.nextLine();

                if (scelta == 0) {
                    break; // Esci dal ciclo
                }

                switch (scelta) {
                    case 1:
                        System.out.println("Benvenuto utente, seleziona il punto vendita:");
                        List<PuntoVendita> puntiVendita = pdao.findAll();

                        if (puntiVendita.isEmpty()) {
                            System.out.println("Non ci sono punti vendita disponibili.");
                        } else {
                            for (int i = 0; i < puntiVendita.size(); i++) {
                                System.out.println((i + 1) + ": " + puntiVendita.get(i).toString());
                            }
                            int sceltaPuntoVendita = scanner.nextInt();
                            scanner.nextLine();

                            if (sceltaPuntoVendita >= 1 && sceltaPuntoVendita <= puntiVendita.size()) {
                                PuntoVendita puntoVenditaSelezionato = puntiVendita.get(sceltaPuntoVendita - 1);
                                System.out.println("Hai selezionato: " + puntoVenditaSelezionato);
                                System.out.println("Cosa vuoi fare? (1: Acquistare un abbonamento, 2: Acquistare un biglietto)");
                                int acquisto = scanner.nextInt();
                                scanner.nextLine();

                                switch (acquisto) {
                                    case 1:
                                        System.out.println("Hai scelto di acquistare un abbonamento.");
                                        // Logica per acquistare un abbonamento
                                        break;
                                    case 2:
                                        System.out.println("Hai scelto di acquistare un biglietto.");
                                        // Logica per mostrare biglietti
                                        break;
                                    default:
                                        System.out.println("Operazione non valida.");
                                }
                            } else {
                                System.out.println("Scelta non valida.");
                            }
                        }
                        break;

                    case 2:
                        menuAmministratore(scanner, em);
                        break;

                    default:
                        System.out.println("Operazione non valida.");
                }

            }
        } catch (Exception e) {
            System.out.println("Errore: " + e.getMessage());
            scanner.nextLine(); // Pulisce il buffer dello scanner
        } finally {
            // Chiudi le risorse qui
            if (em != null && em.isOpen()) {
                em.close();
            }
            if (emf != null && emf.isOpen()) {
                emf.close();
            }
            scanner.close(); // Chiudi lo scanner
            System.out.println("Risorse chiuse correttamente.");
        }
    }

    private static void menuAmministratore(Scanner scanner, EntityManager em) {
        while (true) {
            System.out.println("Seleziona il menu: (1: Distributore, 2: Mezzi, 3: Utenti, 4: Biglietti, 5: Abbonamenti, 6:Tratte  0: Torna indietro)");
            int scelta = scanner.nextInt();
            scanner.nextLine();

            switch (scelta) {
                case 1:
                    MenuDistributore.gestisciMenu(scanner, em);
                    break;
                case 2 :
                    MenuMezzi.gestisciMenuMezzi(scanner,em);
                case 3:
                    MenuUtenti.gestisciMenuUtenti(scanner,em);
                case 4:
                    MenuBiglietti.gestisciMenuBuglietti(scanner,em);
                case 5:
                    MenuAbbonamento.gestisciMenuAbbonamenti(scanner, em);
                case 0:
                    return;

                default:

                  break;
            }
        }
    }
}