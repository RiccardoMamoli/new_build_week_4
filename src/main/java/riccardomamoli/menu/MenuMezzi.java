package riccardomamoli.menu;


import jakarta.persistence.EntityManager;
import riccardomamoli.dao.MezzoDao;
import riccardomamoli.entities.*;

import java.util.List;
import java.util.Scanner;

public class MenuMezzi {
    public static void gestisciMenuMezzi(Scanner scanner, EntityManager em) {
        MezzoDao mdao = new MezzoDao(em);

        while (true) {
            System.out.println("Menu Mezzi:\n" +
                    "1: Crea\n" +
                    "2: Elimina\n" +
                    "3: Ricerca dati mezzo\n" +
                    "4: Verifica stato mezzo\n" +
                    "5: Cambia stato mezzo\n" +
                    "6: Trova tutte le tratte di un mezzo\n" +
                    "7: Conteggio tratte per mezzo\n" +
                    "8: Calcola il tempo effettivo delle tratte\n" +
                    "9: Calcola il tempo medio effettivo\n" +
                    "0: Torna indietro");

            int scelta = scanner.nextInt();
            scanner.nextLine();

            switch (scelta) {
                case 1:
                    // Creazione di un nuovo mezzo
                    System.out.println("Scegli il tipo di Mezzo da creare: (1: Tram, 2: Autobus)");
                    int tipo = scanner.nextInt();
                    scanner.nextLine();

                    Mezzo nuovoMezzo;

                    if (tipo == 1) {
                        // Richiesta dei parametri per Tram
                        System.out.print("Inserisci la capacità del Tram: ");
                        int capacitaTram = scanner.nextInt();
                        scanner.nextLine();

                        System.out.print("Inserisci lo stato del Tram (1: IN_SERVIZIO, 2: MANUTENZIONE): ");
                        int statoInput = scanner.nextInt();
                        scanner.nextLine();

                        StatoMezzo statoTram;
                        if (statoInput == 1) {
                            statoTram = StatoMezzo.IN_SERVIZIO;
                        } else if (statoInput == 2) {
                            statoTram = StatoMezzo.MANUTENZIONE;
                        } else {
                            System.out.println("Stato non valido. Operazione annullata.");
                            return;
                        }

                        nuovoMezzo = new Tram(capacitaTram, statoTram);

                    } else if (tipo == 2) {
                        // Richiesta dei parametri per Autobus
                        System.out.print("Inserisci la capacità dell'Autobus: ");
                        int capacitaAutobus = scanner.nextInt();
                        scanner.nextLine();

                        System.out.print("Inserisci lo stato dell'Autobus (1: IN_SERVIZIO, 2: MANUTENZIONE): ");
                        int statoInput = scanner.nextInt();
                        scanner.nextLine();

                        StatoMezzo statoAutobus;
                        if (statoInput == 1) {
                            statoAutobus = StatoMezzo.IN_SERVIZIO;
                        } else if (statoInput == 2) {
                            statoAutobus = StatoMezzo.MANUTENZIONE;
                        } else {
                            System.out.println("Stato non valido. Operazione annullata.");
                            return;
                        }

                        // Creazione dell'oggetto Autobus
                        nuovoMezzo = new Autobus(capacitaAutobus, statoAutobus);

                    } else {
                        System.out.println("Tipo non valido. Operazione annullata.");
                        break; // Esci dal case se il tipo non è valido
                    }

                    try {
                        // Salva il nuovo mezzo nel database
                        mdao.creazioneMezzo(nuovoMezzo);
                        System.out.println("Mezzo creato.");

                    } catch (Exception e) {
                        System.out.println("Errore durante la creazione del mezzo: " + e.getMessage());
                    }
                    break;
                case 2:
                    // Eliminazione di un mezzo
                    System.out.println("Inserisci l'ID del mezzo da eliminare:");
                    long idMezzo = scanner.nextLong();
                    scanner.nextLine();
                    try {
                        mdao.rimuovoMezzo(idMezzo);
                        System.out.println("Mezzo eliminato.");
                    } catch (Exception e) {
                        System.out.println("Errore: " + e.getMessage());
                    }
                    break;

                case 3:
                    // dati di un mezzo
                    System.out.println("Inserisci l'ID del mezzo :");
                    long idConsulta = scanner.nextLong();
                    scanner.nextLine();
                    try {
                        Mezzo mezzo = mdao.ricercoMezzo(idConsulta);
                        System.out.println(mezzo);
                    } catch (Exception e) {
                        System.out.println("Errore: " + e.getMessage());
                    }
                    break;
                case 4:
                    // Verifica dello stato di un mezzo
                    System.out.println("Inserisci l'ID del mezzo per verificare lo stato:");
                    long idStato = scanner.nextLong();
                    scanner.nextLine();

                    try {
                        mdao.statoMezzo(idStato);
                    } catch (Exception e) {
                        System.out.println("Errore: " + e.getMessage());
                    }
                    break;

                    case 5:
                    // Cambia lo stato del Mezzo
                    System.out.println("Inserisci l'ID del mezzo per cambiare lo stato:");
                    long idCambiaStato = scanner.nextLong();
                    scanner.nextLine();

                    try {
                        Mezzo mezzo = mdao.ricercoMezzo(idCambiaStato);
                        if (mezzo != null) {
                            String statoAttuale = String.valueOf(mezzo.getStatoAttuale());
                            System.out.println("Lo stato attuale del mezzo è: " + statoAttuale);

                            System.out.print("Vuoi cambiare lo stato? (s/n): ");
                            String risposta = scanner.nextLine();

                            if (risposta.equalsIgnoreCase("s")) {
                                mdao.cambiaStatoMezzo(idCambiaStato);
                                System.out.println("Stato cambiato con successo.");
                            } else {
                                System.out.println("Operazione annullata. Lo stato del mezzo rimane invariato.");
                            }
                        } else {
                            System.out.println("Mezzo non trovato per ID: " + idCambiaStato);
                        }
                    } catch (Exception e) {
                        System.out.println("Errore: " + e.getMessage());
                    }
                    break;
                case 6:
                    // Trova le tratte percorse da un mezzo
                    System.out.println("Inserisci l'ID del mezzo per trovare le tratte percorse:");
                    long idMezz = scanner.nextLong();
                    scanner.nextLine();

                    try {
                        List<TrattaPercorsa> trattePercorse = mdao.trovaTrattePercorse(idMezz);
                        if (trattePercorse != null && !trattePercorse.isEmpty()) {
                            System.out.println("Tratte percorse dal mezzo con ID " + idMezz + ":");
                            for (TrattaPercorsa tratta : trattePercorse) {
                                System.out.println(tratta);
                            }
                        } else {
                            System.out.println("Nessuna tratta percorsa trovata per il mezzo con ID: " + idMezz);
                        }
                    } catch (Exception e) {
                        System.out.println("Errore: " + e.getMessage());
                    }
                    break;
                case 7:
                    // Conta le tratte percorse da un mezzo
                    System.out.println("Inserisci l'ID del mezzo per contare le tratte percorse:");
                    long idMezzoo = scanner.nextLong();
                    scanner.nextLine();

                    try {
                        long numeroTratte = mdao.countTratteInPeriod(idMezzoo);
                        System.out.println("Il numero di tratte percorse dal mezzo con ID " + idMezzoo + " è: " + numeroTratte);
                    } catch (Exception e) {
                        System.out.println("Errore: " + e.getMessage());
                    }
                    break;
                case 8:
                    // Calcola il tempo effettivo delle tratte
                    System.out.println("Inserisci l'ID del mezzo:");
                    long idMez = scanner.nextLong();
                    scanner.nextLine();

                    System.out.println("Inserisci l'ID della tratta:");
                    long idTratta = scanner.nextLong();
                    scanner.nextLine();

                    try {
                        List<Integer> tempiEffettivi = mdao.getTempiEffettivi(idMez, idTratta);

                        if (tempiEffettivi != null && !tempiEffettivi.isEmpty()) {
                            System.out.println("Tempi effettivi per la tratta con ID " + idTratta + " e mezzo con ID " + idMez + ":");
                            for (Integer tempo : tempiEffettivi) {
                                System.out.println(tempo + " minuti"); // Assumendo che il tempo sia in ore
                            }
                        } else {
                            System.out.println("Nessun tempo effettivo trovato per la tratta con ID " + idTratta + " e mezzo con ID " + idMez);
                        }
                    } catch (Exception e) {
                        System.out.println("Errore: " + e.getMessage());
                    }
                    break;
                case 9:
                    // Calcola il tempo medio effettivo di percorrenza
                    System.out.println("Inserisci l'ID del mezzo:");
                    long idMezzooo = scanner.nextLong();
                    scanner.nextLine();

                    System.out.println("Inserisci l'ID della tratta:");
                    long idTrattaa = scanner.nextLong();
                    scanner.nextLine();

                    try {
                        double tempoMedio = mdao.calcolaTempoMedioEffettivo(idMezzooo, idTrattaa);
                        System.out.println("Il tempo medio effettivo di percorrenza per la tratta con ID " + idTrattaa + " e mezzo con ID " + idMezzooo + " è: " + tempoMedio + " minuti");
                    } catch (Exception e) {
                        System.out.println("Errore: " + e.getMessage());
                    }
                    break;



                case 0:
                    return;

                default:
                    System.out.println("Scelta non valida.");
                    break;
            }
        }
    }
}