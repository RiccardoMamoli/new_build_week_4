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


        UtenteDao udao = new UtenteDao(em);
        PuntoVenditaDao pdao = new PuntoVenditaDao(em);
        TesseraDao tdao = new TesseraDao(em);
        AbbonamentoDao adao = new AbbonamentoDao(em);
        TrattaDao trdao = new TrattaDao(em);
        MezzoDao mdao = new MezzoDao(em);
        BigliettoDao bdao = new BigliettoDao(em);

        Utente utente1 = new Utente("franco", "rossi", TipologiaUtente.UNDER25, LocalDate.of(2000, 10, 3));
        Utente utente2 = new Utente("mattia", "gonnola", TipologiaUtente.OVER60, LocalDate.of(1890, 2, 4));
        Utente utente3 = new Utente("giacomo", "guidotti", TipologiaUtente.UNDER18, LocalDate.of(2007, 11, 9));
       // udao.addUtente(utente1);
        //udao.addUtente(utente2);
        //1udao.addUtente(utente3);

        List<Abbonamento> abbonamenti = new ArrayList<Abbonamento>();
        List<Biglietto> biglietti = new ArrayList<Biglietto>();
        List<TrattaPercorsa> trattePercorse = new ArrayList<TrattaPercorsa>();
        List<StatusMezzo> stati = new ArrayList<StatusMezzo>();
        PuntoVendita p1 = new DistributoreAutomatico(abbonamenti, biglietti, true);
        PuntoVendita p2 = new DistributoreFisico(abbonamenti, biglietti);
       // pdao.addPuntoVendita(p1);
        //pdao.addPuntoVendita(p2);

        LocalDate dataRilascio = LocalDate.now();
        LocalDate dataScadenza = dataRilascio.plusYears(1);
        Abbonamento abb1 = new Abbonamento(p1, 243545, dataRilascio, dataScadenza, TipologiaAbbonamento.MENSILE, 30);
        //adao.addAbbonamento(abb1);

        Tessera tessera1 = new Tessera(utente1, abb1, "145sf346", dataRilascio, dataScadenza, true);

        Tratta tratta1 = new Tratta("a", "b", 40, 60, trattePercorse);
        Tratta tratta2 = new Tratta("c", "d", 20, 30, trattePercorse);
        trdao.addTratta(tratta1);
        trdao.addTratta(tratta2);

        Mezzo mezzo1 = new Autobus(70, StatoMezzo.IN_SERVIZIO, trattePercorse, stati);
        Scanner scanner = new Scanner(System.in);


        while (true) {
            try {
                System.out.println("Che tipo di utente sei? (1: Utente, 2: Amministratore)");
                int scelta = scanner.nextInt();
                scanner.nextLine();

                switch (scelta) {
                    case 1:
                        System.out.println("Benvenuto utente, seleziona il punto vendita:");
                        pdao.findAll();


                        int sceltaPuntoVendita = scanner.nextInt();
                        scanner.nextLine();

                        if (sceltaPuntoVendita >= 1 && sceltaPuntoVendita <= pdao.findAll().size()) {
                            PuntoVendita puntoVenditaSelezionato = pdao.findAll().get(sceltaPuntoVendita - 1);
                            System.out.println("Hai selezionato: " + puntoVenditaSelezionato);
                            System.out.println("Cosa vuoi fare?");
                            System.out.println("1: apri menù biglietto");
                            System.out.println("2: apri menù abbonamento");
                            int acquisto = scanner.nextInt();
                            scanner.nextLine();

                            switch (acquisto) {
                                case 1:
                                    System.out.println("Hai scelto il menu biglietto. Quale tratta vorresti percorrere?");
                                   trdao.findAll();
                                   int sceltaTratta = scanner.nextInt();
                                   scanner.nextLine();
                                   if (sceltaTratta >= 1 && sceltaTratta <= trdao.findAll().size()) {
                                       Tratta trattaCercata = trdao.findAll().get(sceltaTratta - 1);
                                       System.out.println("Hai selezionato: " + trattaCercata);
                                       System.out.println("il prezzo del biglietto per questa tratta è" + trattaCercata.getPrezzo());
                                       System.out.println("ecco il tuo biglietto");
                                       PuntoVendita puntoVenditaselezionato = pdao.findById(sceltaPuntoVendita);
                                       Tratta trattaSelezionata = trdao.findById(sceltaTratta);
                                       Biglietto biglietto1 = new Biglietto(puntoVenditaselezionato, trattaSelezionata, LocalDate.now(),false  );
                                       bdao.addBiglietto(biglietto1, trattaSelezionata);
                                   }


                                    break;
                                case 2:
                                    System.out.println("Hai scelto di acquistare un abbonamento.");
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
/*
                    case 2:
                        // L'utente è un amministratore
                        System.out.println("Benvenuto amministratore!");
                        // Logica per l'amministratore
                        break;

                    default:
                        System.out.println("Scelta non valida.");
                        break;
                }

            }
        }
    }

}*/

            } catch (Exception e) {
                System.out.println("Errore: " + e.getMessage());
                scanner.nextLine();
            }
        }
            em.close();
            emf.close();
    }
}
