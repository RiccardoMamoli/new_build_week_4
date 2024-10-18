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

        // Creazione degli oggetti DAO e degli utenti
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
        PuntoVendita p3 = new DistributoreAutomatico(abbonamenti, biglietti, false);
        // pdao.addPuntoVendita(p3);
        // pdao.addPuntoVendita(p2);

        LocalDate dataRilascio = LocalDate.now();
        LocalDate dataScadenza = dataRilascio.plusYears(1);
        //adao.addAbbonamento(abb1);


        Mezzo mezzo1 = new Autobus(70, StatoMezzo.IN_SERVIZIO);
        Mezzo mezzo2 = new Tram(80, StatoMezzo.MANUTENZIONE);
        Mezzo mezzo3 = new Autobus(100, StatoMezzo.IN_SERVIZIO);
        // mdao.creazioneMezzo(mezzo1);
        // mdao.creazioneMezzo(mezzo2);
        // mdao.creazioneMezzo(mezzo3);

        Mezzo mezzoid1 = mdao.ricercoMezzo(1);
        Mezzo mezzoid2 = mdao.ricercoMezzo(2);

        StatusMezzo statusMezzo1 = new StatusMezzo(StatoMezzo.IN_SERVIZIO, LocalDate.of(2018, 12, 10), LocalDate.of(2018, 12, 20), mezzoid1);
        StatusMezzo statusMezzo2 = new StatusMezzo(StatoMezzo.MANUTENZIONE, LocalDate.of(2019, 10, 20), LocalDate.of(2019, 10, 25), mezzoid2);
        StatusMezzo statusMezzo3 = new StatusMezzo(StatoMezzo.IN_SERVIZIO, LocalDate.of(2020, 1, 10), LocalDate.now(), mezzoid2);
        StatusMezzo statusMezzo4 = new StatusMezzo(StatoMezzo.IN_SERVIZIO, LocalDate.of(2024, 10, 18), LocalDate.now(), mezzoid2);

        //sdao.addStatusMezzo(statusMezzo1);
        //sdao.addStatusMezzo(statusMezzo2);
        //sdao.addStatusMezzo(statusMezzo3);
        //sdao.addStatusMezzo(statusMezzo4);

        Tratta tratta1 = trdao.findById(11);
        Tratta tratta2 = trdao.findById(12);

        TrattaPercorsa FasciaOraria1 = new TrattaPercorsa(tratta1, mezzoid1, LocalTime.of(9, 0), LocalTime.of(10, 0), 70);
        TrattaPercorsa FasciaOraria2 = new TrattaPercorsa(tratta1, mezzoid1, LocalTime.of(11, 0), LocalTime.of(12, 0), 60);
        TrattaPercorsa FasciaOraria3 = new TrattaPercorsa(tratta1, mezzoid1, LocalTime.of(13, 0), LocalTime.of(14, 0), 60);

        TrattaPercorsa FasciaOraria4 = new TrattaPercorsa(tratta2, mezzoid2, LocalTime.of(9, 0), LocalTime.of(10, 0), 70);
        TrattaPercorsa FasciaOraria5 = new TrattaPercorsa(tratta2, mezzoid2, LocalTime.of(11, 0), LocalTime.of(12, 0), 60);
        TrattaPercorsa FasciaOraria6 = new TrattaPercorsa(tratta2, mezzoid2, LocalTime.of(13, 0), LocalTime.of(14, 0), 60);

        // tpdao.addTrattaPercorsa(FasciaOraria1);
        // tpdao.addTrattaPercorsa(FasciaOraria2);
        // tpdao.addTrattaPercorsa(FasciaOraria3);
        // tpdao.addTrattaPercorsa(FasciaOraria4);
        // tpdao.addTrattaPercorsa(FasciaOraria5);
        // tpdao.addTrattaPercorsa(FasciaOraria6);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            try {
                while (true) {
                    System.out.println(" ");
                    System.out.println("Che tipo di utente sei?");
                    System.out.println(" ");
                    System.out.println("1) Utente");
                    System.out.println("2) Admin");
                    int scelta = scanner.nextInt();
                    scanner.nextLine();

                    if (scelta == 0) {
                        break;
                    }

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
                                System.out.println("3: Torna al Menù principale");
                                System.out.println(" ");
                                int acquisto = scanner.nextInt();
                                scanner.nextLine();


                                boolean statoSelezioneMenu = false;
                                PuntoVendita puntoVenditaselezionato = null;
                                while (!statoSelezioneMenu) {
                                    switch (acquisto) {
                                        case 1:

                                            System.out.println("Hai scelto il menu biglietto. Quale tratta vorresti percorrere?");
                                            System.out.println(" ");

                                            List<Tratta> listaTratte = trdao.findAll();

                                            int sceltaTratta = scanner.nextInt();
                                            scanner.nextLine();

                                            Tratta trattaSelezionata = null;
                                            if (sceltaTratta >= 1 && sceltaTratta <= listaTratte.size()) {
                                                System.out.println("Hai selezionato la tratta numero " + sceltaTratta);
                                                System.out.println(" ");
                                                trattaSelezionata = listaTratte.get(sceltaTratta - 1);
                                                System.out.println("Il prezzo del biglietto per questa tratta è " + trattaSelezionata.getPrezzo());
                                                System.out.println(" ");

                                                puntoVenditaselezionato = listaPuntiVendita.get(sceltaPuntoVendita - 1);
                                                Biglietto biglietto1 = new Biglietto(puntoVenditaselezionato, trattaSelezionata, LocalDate.now(), false);
                                                bdao.addBiglietto(biglietto1, trattaSelezionata);
                                            }

                                            System.out.println(" ");
                                            System.out.println("A che ora vorresti partire?");
                                            System.out.println(" ");
                                            tpdao.printFascePerTratta(trattaSelezionata);

                                            break;


                                        case 2:
                                            System.out.println(" ");
                                            System.out.println("Hai scelto il menu abbonamento.");
                                            System.out.println(" ");
                                            System.out.println("Possiedi gia un tessera cliente? (y/n)");
                                            System.out.println(" ");
                                            String sceltaPossedimento = scanner.nextLine();
                                            if (sceltaPossedimento.equals("y")) {
                                                System.out.println("Inserisci numero di tessera");
                                                int numeroTessera = scanner.nextInt();

                                            } else if (sceltaPossedimento.equals("n")) {
                                                System.out.println("Vuoi crearne una ora? (y/n)");
                                                String sceltaCreaTessera = scanner.nextLine();
                                                if (sceltaCreaTessera == "y") {
                                                    System.out.println("Sei gia un utente registrato?");
                                                    String sceltaUtenteReg = scanner.nextLine();
                                                    if (sceltaUtenteReg.equals("y")) {
                                                        System.out.println("Inserisci il tuo ID utente. ");
                                                        int idUtente = scanner.nextInt();
                                                        Utente utenteCercato = udao.findById(idUtente);
                                                        System.out.println("Benvenuto " + utenteCercato.getNome() + " " + utenteCercato.getCognome() + " .");


                                                    } else if (sceltaUtenteReg.equals("n")) {
                                                        System.out.println(" ");
                                                        System.out.println("Registrati ora!");
                                                        System.out.println("Inserisci il tuo nome: ");
                                                        String nomeUtente = scanner.nextLine();
                                                        System.out.println(" ");
                                                        System.out.println("Inserisci il tuo cognome: ");
                                                        System.out.println(" ");
                                                        String cognomeUtente = scanner.nextLine();
                                                        System.out.println(" ");
                                                        System.out.println("Inserisci la tua data di nascita: (formato: dd/MM/yyyy)");
                                                        System.out.println(" ");
                                                        String stringaNascita = scanner.nextLine();
                                                        LocalDate dataNascita = LocalDate.parse(stringaNascita);
                                                        int eta = (LocalDate.now().getYear()) - (dataNascita.getYear());

                                                        TipologiaUtente tipologiaUtente = TipologiaUtente.DEFAULT;

                                                        if (eta < 25 && eta > 18) {
                                                            tipologiaUtente = TipologiaUtente.UNDER25;
                                                        } else if (eta < 18) {
                                                            tipologiaUtente = TipologiaUtente.UNDER18;
                                                        } else if (eta > 25 && eta < 35) {
                                                            tipologiaUtente = TipologiaUtente.YOUNG;
                                                        } else if (eta > 35 && eta < 60) {
                                                            tipologiaUtente = TipologiaUtente.SENIOR;
                                                        } else if (eta > 60) {
                                                            tipologiaUtente = TipologiaUtente.OVER60;
                                                        }

                                                        Utente utente = new Utente(nomeUtente, cognomeUtente, tipologiaUtente, dataNascita);
                                                        udao.addUtente(utente);
                                                        System.out.println(" ");
                                                        System.out.println("Creiamo ora una tessera per te!");
                                                        LocalDate dataRilascioTessera = LocalDate.now();
                                                        LocalDate dataScadenzaTessera = LocalDate.now().plusYears(1);
                                                        Tessera nuovaTessera = new Tessera(utente, dataRilascioTessera, dataScadenzaTessera, true);
                                                        tdao.addTessera(nuovaTessera);
                                                        System.out.println(" ");
                                                        System.out.println("Che cosa vuoi fare?");
                                                        System.out.println(" ");
                                                        System.out.println("1) Acquista abbonamento.");
                                                        System.out.println("2) Controlla profilo utente.");
                                                        System.out.println("3) Torna al menu principale.");
                                                        int scelta3 = scanner.nextInt();
                                                        switch (scelta3){
                                                            case 1:
                                                                System.out.println(" ");
                                                                System.out.println("Che tipo di abbonamento vuoi acquistare?");
                                                                System.out.println(" ");
                                                                System.out.println("1) Settimanale");
                                                                System.out.println("2) Mensile");
                                                                int sceltaDurataAbb = scanner.nextInt();
                                                                if(sceltaDurataAbb == 1) {
                                                                    System.out.println(" ");
                                                                    System.out.println("Hai scelto l'abbonamento settimanale.");
                                                                    System.out.println(" ");
                                                                    System.out.println("Per quale tratta vuoi acquistarlo?");

                                                                    List<Tratta> listaTratteAbbonamento = trdao.findAll();

                                                                    int sceltaTrattaAbbonamento = scanner.nextInt();
                                                                    scanner.nextLine();

                                                                    Tratta trattaSelezionataAbbonamento = null;
                                                                    if (sceltaTrattaAbbonamento >= 1 && sceltaTrattaAbbonamento <= listaTratteAbbonamento.size()) {
                                                                        System.out.println("Hai selezionato la tratta numero " + sceltaTrattaAbbonamento);
                                                                        System.out.println(" ");
                                                                        trattaSelezionataAbbonamento = listaTratteAbbonamento.get(sceltaTrattaAbbonamento - 1);
                                                                        System.out.println("Il prezzo dell'abbonamento per questa tratta è " + trattaSelezionataAbbonamento.getPrezzo());
                                                                        System.out.println(" ");
                                                                        System.out.println("Vuoi acquistarlo? (y/n)");
                                                                        System.out.println(" ");
                                                                        String sceltaAcquisto = scanner.nextLine();
                                                                        if (sceltaAcquisto.equals("y")) {
                                                                            LocalDate dataEmissioneAbbonamento = LocalDate.now();
                                                                            LocalDate dateScadenzaAbbonamento = dataEmissioneAbbonamento.plusDays(7);
                                                                            TipologiaAbbonamento tipoAbb = TipologiaAbbonamento.SETTIMANALE;
                                                                            Abbonamento abbonamento = new Abbonamento(puntoVenditaselezionato, nuovaTessera, dataEmissioneAbbonamento, dateScadenzaAbbonamento, tipoAbb);
                                                                            adao.addAbbonamento(abbonamento);
                                                                        }
                                                                    }
                                                                } else {
                                                                    System.out.println(" ");
                                                                    System.out.println("Hai scelto l'abbonamento mensile.");
                                                                    System.out.println(" ");
                                                                    System.out.println("Per quale tratta vuoi acquistarlo?");

                                                                    List<Tratta> listaTratteAbbonamento = trdao.findAll();

                                                                    int sceltaTrattaAbbonamento = scanner.nextInt();
                                                                    scanner.nextLine();

                                                                    Tratta trattaSelezionataAbbonamento = null;
                                                                    if (sceltaTrattaAbbonamento >= 1 && sceltaTrattaAbbonamento <= listaTratteAbbonamento.size()) {
                                                                        System.out.println("Hai selezionato la tratta numero " + sceltaTrattaAbbonamento);
                                                                        System.out.println(" ");
                                                                        trattaSelezionataAbbonamento = listaTratteAbbonamento.get(sceltaTrattaAbbonamento - 1);
                                                                        System.out.println("Il prezzo dell'abbonamento per questa tratta è " + trattaSelezionataAbbonamento.getPrezzo());
                                                                        System.out.println(" ");
                                                                        System.out.println("Vuoi acquistarlo? (y/n)");
                                                                        System.out.println(" ");
                                                                        String sceltaAcquisto = scanner.nextLine();
                                                                        if (sceltaAcquisto.equals("y")) {
                                                                            LocalDate dataEmissioneAbbonamento = LocalDate.now();
                                                                            LocalDate dateScadenzaAbbonamento = dataEmissioneAbbonamento.plusDays(30);
                                                                            TipologiaAbbonamento tipoAbb = TipologiaAbbonamento.MENSILE;
                                                                            Abbonamento abbonamento = new Abbonamento(puntoVenditaselezionato, nuovaTessera, dataEmissioneAbbonamento, dateScadenzaAbbonamento, tipoAbb);
                                                                            adao.addAbbonamento(abbonamento);
                                                                        }
                                                                    }
                                                                }
                                                                break;
                                                        }
                                                    } else {
                                                        System.out.println("Inserisci una scelta valida.");
                                                    }
                                                } else if (sceltaCreaTessera.equals("n")){
                                                   statoSelezioneMenu = true;
                                                } else {
                                                    System.out.println("Inserisci una scelta valida.");
                                                }
                                            } else {
                                                System.out.println("Inserisci una scelta valida.");
                                            }
                                            break;

                                        case 3:
                                            statoSelezioneMenu = true;
                                            break;
                                    }
                                }
                            } else {
                                System.out.println("Scelta non valida.");
                            }


                    break;

                    case 2:
                        menuAmministratore(scanner, em);
                        break;

                    default:
                        System.out.println("Operazione non valida.");
                }

                }

                break;

            } catch (Exception e) {
                System.out.println("Errore: " + e.getMessage());
                scanner.nextLine();
            } finally {
                if (em != null && em.isOpen()) {
                    em.close();
                }
                if (emf != null && emf.isOpen()) {
                    emf.close();
                }
                scanner.close();
                System.out.println("Risorse chiuse correttamente.");
            }
        }
    }

    private static void menuAmministratore(Scanner scanner, EntityManager em) {
        while (true) {
            System.out.println("Seleziona il menu: (1: Distributore, 2: Mezzi, 3: Utenti, 4: Biglietti, 5: Abbonamenti, 6: Tratte  0: Torna indietro)");
            int scelta = scanner.nextInt();
            scanner.nextLine();

            switch (scelta) {
                case 1:
                    MenuDistributore.gestisciMenu(scanner, em);
                    break;
                case 2:
                    MenuMezzi.gestisciMenuMezzi(scanner, em);
                    break;
                case 3:
                    MenuUtenti.gestisciMenuUtenti(scanner, em);
                    break;
                case 4:
                    MenuBiglietti.gestisciMenuBuglietti(scanner, em);
                    break;
                case 5:
                    MenuAbbonamento.gestisciMenuAbbonamenti(scanner, em);
                    break;
                case 0:
                    return;
                default:
                    break;
            }
        }
    }
}
