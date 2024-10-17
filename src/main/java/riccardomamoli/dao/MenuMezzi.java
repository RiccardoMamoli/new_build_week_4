package riccardomamoli.dao;


import jakarta.persistence.EntityManager;
import riccardomamoli.dao.MezzoDao;
import riccardomamoli.entities.*;

import java.util.Scanner;

public class MenuMezzi {
    public static void gestisciMenuMezzi(Scanner scanner, EntityManager em) {
        MezzoDao mdao = new MezzoDao(em);

        while (true) {
            System.out.println("Menu Mezzi: (1: Crea, 2: Elimina, 3: Consulta, 4: Ricerca mezzo ,5: Verifica stato mezzo, 6: Cambia stato mezzo, 7: Trova tutte le tratte di un mezzo, 8: Conteggio tratte per mezzo  0: Torna indietro)");
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
                        scanner.nextLine(); // Pulisce il buffer

                        System.out.print("Inserisci lo stato dell'Autobus (1: IN_SERVIZIO, 2: MANUTENZIONE): ");
                        int statoInput = scanner.nextInt();
                        scanner.nextLine(); // Pulisce il buffer

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

//                case 3:
//                    // Consultazione di un mezzo
//                    System.out.println("Inserisci l'ID del mezzo da consultare:");
//                    long idConsulta = scanner.nextLong();
//                    scanner.nextLine();
//                    try {
//                        Mezzo mezzo = mdao.findByID(idConsulta);
//                        System.out.println(mezzo);
//                    } catch (Exception e) {
//                        System.out.println("Errore: " + e.getMessage());
//                    }
//                    break;
//
//                case 4:
//                    // Modifica dello stato del mezzo
//                    System.out.println("Inserisci l'ID del mezzo da modificare:");
//                    long idModifica = scanner.nextLong();
//                    scanner.nextLine();
//                    System.out.println("Inserisci il nuovo stato (in servizio o in manutenzione):");
//                    String nuovoStato = scanner.nextLine();
//                    mdao.modifyStatusByID(idModifica, nuovoStato);
//                    System.out.println("Stato modificato.");
//                    break;

                case 0:
                    return;

                default:
                    System.out.println("Scelta non valida.");
                    break;
            }
        }
    }
}