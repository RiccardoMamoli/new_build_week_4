package riccardomamoli;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import riccardomamoli.dao.AbbonamentoDao;
import riccardomamoli.dao.PuntoVenditaDao;
import riccardomamoli.dao.TesseraDao;
import riccardomamoli.dao.UtenteDao;
import riccardomamoli.entities.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Application {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("build_week_4");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        em.getTransaction().commit();


        UtenteDao udao = new UtenteDao(em);
        Utente utente1 = new Utente("franco", "rossi", TipologiaUtente.UNDER25, LocalDate.of(2000, 10, 3));
        Utente utente2 = new Utente("mattia", "gonnola", TipologiaUtente.OVER60, LocalDate.of(1890, 2, 4));
        Utente utente3 = new Utente("giacomo", "guidotti", TipologiaUtente.UNDER18, LocalDate.of(2007, 11, 9));

        PuntoVenditaDao pdao = new PuntoVenditaDao(em);
        PuntoVendita p1 = new DistributoreAutomatico();

        LocalDate dataRilascio = LocalDate.now();
        LocalDate dataScadenza = dataRilascio.plusYears(1);
        AbbonamentoDao adao = new AbbonamentoDao(em);
        Abbonamento abb1 = new Abbonamento(p1, 243545,dataRilascio , dataScadenza, TipologiaAbbonamento.MENSILE, 30);

        TesseraDao tdao = new TesseraDao(em);
        Tessera tessera1 = new Tessera(utente1, abb1, "145sf346", dataRilascio, dataScadenza, true);




        Scanner scanner = new Scanner(System.in);

        while (true) {
            try {
                System.out.println("Che tipo di utente sei? (1: Utente, 2: Amministratore)");
                int scelta = scanner.nextInt();
                scanner.nextLine();

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
                        // L'utente è un amministratore
                        System.out.println("Benvenuto amministratore!");
                        // Logica per l'amministratore
                        break;

                    default:
                        System.out.println("Scelta non valida.");
                        break;
                }

            } catch (Exception e) {
                System.out.println("Errore: " + e.getMessage());
                scanner.nextLine();
            }
            em.close();
            emf.close();
        }
    }

}