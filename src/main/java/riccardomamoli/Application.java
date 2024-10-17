package riccardomamoli;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import riccardomamoli.dao.*;
import riccardomamoli.entities.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Application {

    public static void main(String[] args) throws Exception {
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
        StatusMezzoDao sdao = new StatusMezzoDao(em);
        TrattaPercorsaDao tpdao = new TrattaPercorsaDao(em);


        Utente utente1 = new Utente("franco", "rossi", TipologiaUtente.UNDER25, LocalDate.of(2000, 10, 3));
        Utente utente2 = new Utente("mattia", "gonnola", TipologiaUtente.OVER60, LocalDate.of(1890, 2, 4));
        Utente utente3 = new Utente("giacomo", "guidotti", TipologiaUtente.UNDER18, LocalDate.of(2007, 11, 9));
        // udao.addUtente(utente1);
        //udao.addUtente(utente2);
        //udao.addUtente(utente3);




        List<Abbonamento> abbonamenti = new ArrayList<Abbonamento>();
        List<Biglietto> biglietti = new ArrayList<Biglietto>();
        List<TrattaPercorsa> trattePercorse = new ArrayList<TrattaPercorsa>();
        List<StatusMezzo> stati = new ArrayList<StatusMezzo>();
        PuntoVendita p1 = new DistributoreAutomatico(abbonamenti, biglietti, true);
        PuntoVendita p2 = new DistributoreFisico(abbonamenti, biglietti);
        //pdao.addPuntoVendita(p1);
        // pdao.addPuntoVendita(p2);

        LocalDate dataRilascio = LocalDate.now();
        LocalDate dataScadenza = dataRilascio.plusYears(1);
        Abbonamento abb1 = new Abbonamento(p1, 243545, dataRilascio, dataScadenza, TipologiaAbbonamento.MENSILE, 30);
        //adao.addAbbonamento(abb1);

        Tessera tessera1 = new Tessera(utente1, abb1, "145sf346", dataRilascio, dataScadenza, true);





        Mezzo mezzo1 = new Autobus(70, StatoMezzo.IN_SERVIZIO);
        Mezzo mezzo2 = new Tram(80, StatoMezzo.MANUTENZIONE);
        Mezzo mezzo3 = new Autobus(100, StatoMezzo.IN_SERVIZIO);
        // mdao.creazioneMezzo(mezzo1);
        // mdao.creazioneMezzo(mezzo2);
        // mdao.creazioneMezzo(mezzo3);

        Mezzo mezzoid1 = mdao.ricercoMezzo(1);
        Mezzo mezzoid2 = mdao.ricercoMezzo(2);

        StatusMezzo statusMezzo1 = new StatusMezzo(StatoMezzo.IN_SERVIZIO, LocalDate.of(2018,12,10), LocalDate.of(2018, 12, 20), mezzoid1);
        StatusMezzo statusMezzo2 = new StatusMezzo(StatoMezzo.MANUTENZIONE, LocalDate.of(2019,10,20), LocalDate.of(2019, 10, 25), mezzoid2);
        StatusMezzo statusMezzo3 = new StatusMezzo(StatoMezzo.IN_SERVIZIO, LocalDate.of(2020,1,10), LocalDate.now(), mezzoid2);
        StatusMezzo statusMezzo4 = new StatusMezzo(StatoMezzo.IN_SERVIZIO, LocalDate.of(2024,10,18), LocalDate.now(), mezzoid2);

        //sdao.addStatusMezzo(statusMezzo1);
        //sdao.addStatusMezzo(statusMezzo2);
        //sdao.addStatusMezzo(statusMezzo3);
        //sdao.addStatusMezzo(statusMezzo4);



        Tratta tratta1 = trdao.findById(11);
        Tratta tratta2 = trdao.findById(12);

        TrattaPercorsa FasciaOraria1 = new TrattaPercorsa(tratta1, mezzoid1, LocalTime.of(9 , 0), LocalTime.of(10,0), 70);
        TrattaPercorsa FasciaOraria2 = new TrattaPercorsa(tratta1, mezzoid1, LocalTime.of(11 , 0), LocalTime.of(12,0), 60);
        TrattaPercorsa FasciaOraria3 = new TrattaPercorsa(tratta1, mezzoid1, LocalTime.of(13 , 0), LocalTime.of(14,0), 60);

        TrattaPercorsa FasciaOraria4 = new TrattaPercorsa(tratta2, mezzoid2, LocalTime.of(9 , 0), LocalTime.of(10,0), 70);
        TrattaPercorsa FasciaOraria5 = new TrattaPercorsa(tratta2, mezzoid2, LocalTime.of(11 , 0), LocalTime.of(12,0), 60);
        TrattaPercorsa FasciaOraria6 = new TrattaPercorsa(tratta2, mezzoid2, LocalTime.of(13 , 0), LocalTime.of(14,0), 60);



        // tpdao.addTrattaPercorsa(FasciaOraria1);
        // tpdao.addTrattaPercorsa(FasciaOraria2);
        // tpdao.addTrattaPercorsa(FasciaOraria3);
        // tpdao.addTrattaPercorsa(FasciaOraria4);
        // tpdao.addTrattaPercorsa(FasciaOraria5);
        // tpdao.addTrattaPercorsa(FasciaOraria6);






        Scanner scanner = new Scanner(System.in);


        while (true) {
            try {
                System.out.println(" ");
                System.out.println("Che tipo di utente sei?");
                System.out.println(" ");
                System.out.println("1) Utente");
                System.out.println("2) Admin");
                int scelta = scanner.nextInt();
                scanner.nextLine();

                switch (scelta) {
                    case 1:
                        System.out.println(" ");
                        System.out.println("Benvenuto utente, seleziona il punto vendita:");
                        System.out.println(" ");
                        List<PuntoVendita> listaPuntiVendita = pdao.findAll();

                        int sceltaPuntoVendita = scanner.nextInt();

                        scanner.nextLine();

                        if (sceltaPuntoVendita >= 1 && sceltaPuntoVendita <= listaPuntiVendita.size()) {

                            System.out.println("Hai selezionato il punto vendita numero " + sceltaPuntoVendita);
                            System.out.println(" ");
                            System.out.println("Cosa vuoi fare?");
                            System.out.println(" ");
                            System.out.println("1: Apri Menù Biglietto");
                            System.out.println("2: Apri Menù Abbonamento");
                            System.out.println(" ");
                            int acquisto = scanner.nextInt();
                            scanner.nextLine();

                            switch (acquisto) {
                                case 1:

                                    System.out.println("Hai scelto il menu biglietto. Quale tratta vorresti percorrere?");
                                    System.out.println(" ");

                                    List<Tratta> listaTratte = trdao.findAll();

                                    int sceltaTratta = scanner.nextInt();
                                    scanner.nextLine();

                                    // Tratta trattaSelezionata = null;
                                    if (sceltaTratta >= 1 && sceltaTratta <= listaTratte.size()) {
                                        System.out.println("Hai selezionato la tratta numero " + sceltaTratta);
                                        System.out.println(" ");
                                       // trattaSelezionata = listaTratte.get(sceltaTratta);
                                       // System.out.println("Il prezzo del biglietto per questa tratta è " + trattaSelezionata.getPrezzo());
                                        System.out.println(" ");

                                        PuntoVendita puntoVenditaselezionato = listaPuntiVendita.get(sceltaPuntoVendita);
                                       // Biglietto biglietto1 = new Biglietto(puntoVenditaselezionato, trattaSelezionata, LocalDate.now(), false);
                                         // bdao.addBiglietto(biglietto1, trattaSelezionata);

                                    }

                                    System.out.println(" ");
                                    System.out.println("A che ora vorresti partire?");
                                    System.out.println(" ");
                                   //  tpdao.printFascePerTratta(trattaSelezionata);


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
