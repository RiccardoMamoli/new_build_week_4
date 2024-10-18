package riccardomamoli.dao;


import jakarta.persistence.EntityManager;
import riccardomamoli.dao.MezzoDao;
import riccardomamoli.entities.*;

import java.util.InputMismatchException;
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
                    while (true) {
                        try {
                            Mezzo nuovoMezzo;
                            // Creazione di un nuovo mezzo
                            System.out.println("Scegli il tipo di Mezzo da creare: (1: Tram, 2: Autobus, 0: Torna al menu principale)");
                            int tipo = scanner.nextInt();
                            scanner.nextLine();

                            if (tipo == 0) {
                                System.out.println("Tornando al menu principale...");
                                break;
                            }

                            if (tipo == 1) {
                                // Richiesta dei parametri per Tram
                                System.out.print("Inserisci la capacità del Tram (o 0 per tornare al menu): ");
                                int capacitaTram = scanner.nextInt();
                                if (capacitaTram == 0) {
                                    System.out.println("Tornando al menu principale...");
                                    break;
                                }
                                scanner.nextLine();

                                System.out.print("Inserisci lo stato del Tram (1: IN_SERVIZIO, 2: MANUTENZIONE, 0: Torna al menu): ");
                                int statoInput = scanner.nextInt();
                                if (statoInput == 0) {
                                    System.out.println("Tornando al menu principale...");
                                    break;
                                }
                                scanner.nextLine();

                                StatoMezzo statoTram;
                                if (statoInput == 1) {
                                    statoTram = StatoMezzo.IN_SERVIZIO;
                                } else if (statoInput == 2) {
                                    statoTram = StatoMezzo.MANUTENZIONE;
                                } else {
                                    System.out.println("Stato non valido. Riprova.");
                                    continue;
                                }

                                nuovoMezzo = new Tram(capacitaTram, statoTram);

                            } else if (tipo == 2) {
                                System.out.print("Inserisci la capacità dell'Autobus (o 0 per tornare al menu): ");
                                int capacitaAutobus = scanner.nextInt();
                                if (capacitaAutobus == 0) {
                                    System.out.println("Tornando al menu principale...");
                                    break;
                                }
                                scanner.nextLine();

                                System.out.print("Inserisci lo stato dell'Autobus (1: IN_SERVIZIO, 2: MANUTENZIONE, 0: Torna al menu): ");
                                int statoInput = scanner.nextInt();
                                if (statoInput == 0) {
                                    System.out.println("Tornando al menu principale...");
                                    break;
                                }
                                scanner.nextLine();

                                StatoMezzo statoAutobus;
                                if (statoInput == 1) {
                                    statoAutobus = StatoMezzo.IN_SERVIZIO;
                                } else if (statoInput == 2) {
                                    statoAutobus = StatoMezzo.MANUTENZIONE;
                                } else {
                                    System.out.println("Stato non valido. Riprova.");
                                    continue;
                                }

                                nuovoMezzo = new Autobus(capacitaAutobus, statoAutobus);

                            } else {
                                System.out.println("Tipo non valido. Riprova.");
                                continue;
                            }

                            mdao.creazioneMezzo(nuovoMezzo);
                            System.out.println("Mezzo creato.");
                            break;

                        } catch (InputMismatchException e) {
                            System.out.println("Input non valido. Per favore, inserisci un numero intero.");
                            scanner.nextLine();
                        } catch (Exception e) {
                            System.out.println("Errore durante la creazione del mezzo: " + e.getMessage());
                        }
                    }
                    break;
                case 2:
                    while (true) {
                        try {
                            System.out.println("Inserisci l'ID del mezzo da eliminare (o 0 per tornare al menu principale):");
                            long idMezzo = scanner.nextLong();
                            scanner.nextLine();

                            if (idMezzo == 0) {
                                System.out.println("Tornando al menu principale...");
                                break;
                            }

                            mdao.rimuovoMezzo(idMezzo);
                            System.out.println("Mezzo eliminato.");
                            break;

                        } catch (InputMismatchException e) {
                            System.out.println("Input non valido. Per favore, inserisci un numero intero.");
                            scanner.nextLine();
                        } catch (Exception e) {
                            System.out.println("Errore durante l'eliminazione del mezzo: " + e.getMessage());
                        }
                    }
                    break;

                case 3:
                    while (true) {
                        try {
                            System.out.println("Inserisci l'ID del mezzo (o 0 per tornare al menu principale):");
                            long idConsulta = scanner.nextLong();
                            scanner.nextLine();

                            if (idConsulta == 0) {
                                System.out.println("Tornando al menu principale...");
                                break;
                            }

                            Mezzo mezzo = mdao.ricercoMezzo(idConsulta);
                            System.out.println(mezzo);
                            break; // Esci dal ciclo se tutto va bene

                        } catch (InputMismatchException e) {
                            System.out.println("Input non valido. Per favore, inserisci un numero intero.");
                            scanner.nextLine();
                        } catch (Exception e) {
                            System.out.println("Errore durante la ricerca del mezzo: " + e.getMessage());
                        }
                    }
                    break;

                case 4:
                    while (true) {
                        try {
                            System.out.println("Inserisci l'ID del mezzo per verificare lo stato (o 0 per tornare al menu principale):");
                            long idStato = scanner.nextLong();
                            scanner.nextLine();

                            if (idStato == 0) {
                                System.out.println("Tornando al menu principale...");
                                break;
                            }

                            mdao.statoMezzo(idStato);
                            break;

                        } catch (InputMismatchException e) {
                            System.out.println("Input non valido. Per favore, inserisci un numero intero.");
                            scanner.nextLine();
                        } catch (Exception e) {
                            System.out.println("Errore durante la verifica dello stato del mezzo: " + e.getMessage());
                        }
                    }
                    break;
                case 5:
                    while (true) {
                        try {
                            System.out.println("Inserisci l'ID del mezzo per cambiare lo stato (o 0 per tornare al menu principale):");
                            long idCambiaStato = scanner.nextLong();
                            scanner.nextLine();

                            if (idCambiaStato == 0) {
                                System.out.println("Tornando al menu principale...");
                                break;
                            }

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
                            break;

                        } catch (InputMismatchException e) {
                            System.out.println("Input non valido. Per favore, inserisci un numero intero.");
                            scanner.nextLine();
                        } catch (Exception e) {
                            System.out.println("Errore: " + e.getMessage());
                        }
                    }
                    break;

                case 6:
                    while (true) {
                        try {
                            System.out.println("Inserisci l'ID del mezzo per trovare le tratte percorse (o 0 per tornare al menu principale):");
                            long idMezz = scanner.nextLong();
                            scanner.nextLine();

                            if (idMezz == 0) {
                                System.out.println("Tornando al menu principale...");
                                break;
                            }

                            List<TrattaPercorsa> trattePercorse = mdao.trovaTrattePercorse(idMezz);
                            if (trattePercorse != null && !trattePercorse.isEmpty()) {
                                System.out.println("Tratte percorse dal mezzo con ID " + idMezz + ":");
                                for (TrattaPercorsa tratta : trattePercorse) {
                                    System.out.println(tratta);
                                }
                            } else {
                                System.out.println("Nessuna tratta percorsa trovata per il mezzo con ID: " + idMezz);
                            }
                            break;

                        } catch (InputMismatchException e) {
                            System.out.println("Input non valido. Per favore, inserisci un numero intero.");
                            scanner.nextLine();
                        } catch (Exception e) {
                            System.out.println("Errore: " + e.getMessage());
                        }
                    }
                    break;

                case 7:
                    while (true) {
                        try {
                            System.out.println("Inserisci l'ID del mezzo per contare le tratte percorse (o 0 per tornare al menu principale):");
                            long idMezzoo = scanner.nextLong();
                            scanner.nextLine();

                            if (idMezzoo == 0) {
                                System.out.println("Tornando al menu principale...");
                                break;
                            }

                            long numeroTratte = mdao.countTratteInPeriod(idMezzoo);
                            System.out.println("Il numero di tratte percorse dal mezzo con ID " + idMezzoo + " è: " + numeroTratte);
                            break;

                        } catch (InputMismatchException e) {
                            System.out.println("Input non valido. Per favore, inserisci un numero intero.");
                            scanner.nextLine();
                        } catch (Exception e) {
                            System.out.println("Errore: " + e.getMessage());
                        }
                    }
                    break;

                case 8:
                    while (true) {
                        try {
                            System.out.println("Inserisci l'ID del mezzo (o 0 per tornare al menu principale):");
                            long idMez = scanner.nextLong();
                            scanner.nextLine();

                            if (idMez == 0) {
                                System.out.println("Tornando al menu principale...");
                                break;
                            }

                            System.out.println("Inserisci l'ID della tratta:");
                            long idTratta = scanner.nextLong();
                            scanner.nextLine();

                            List<Integer> tempiEffettivi = mdao.getTempiEffettivi(idMez, idTratta);

                            if (tempiEffettivi != null && !tempiEffettivi.isEmpty()) {
                                System.out.println("Tempi effettivi per la tratta con ID " + idTratta + " e mezzo con ID " + idMez + ":");
                                for (Integer tempo : tempiEffettivi) {
                                    System.out.println(tempo + " minuti");
                                }
                            } else {
                                System.out.println("Nessun tempo effettivo trovato per la tratta con ID " + idTratta + " e mezzo con ID " + idMez);
                            }
                            break;

                        } catch (InputMismatchException e) {
                            System.out.println("Input non valido. Per favore, inserisci un numero intero.");
                            scanner.nextLine();
                        } catch (Exception e) {
                            System.out.println("Errore: " + e.getMessage());
                        }
                    }
                    break;

                case 9:
                    while (true) {
                        try {
                            System.out.println("Inserisci l'ID del mezzo (o 0 per tornare al menu principale):");
                            long idMezzooo = scanner.nextLong();
                            scanner.nextLine();

                            if (idMezzooo == 0) {
                                System.out.println("Tornando al menu principale...");
                                break;
                            }

                            System.out.println("Inserisci l'ID della tratta:");
                            long idTrattaa = scanner.nextLong();
                            scanner.nextLine();

                            double tempoMedio = mdao.calcolaTempoMedioEffettivo(idMezzooo, idTrattaa);
                            System.out.println("Il tempo medio effettivo di percorrenza per la tratta con ID " + idTrattaa + " e mezzo con ID " + idMezzooo + " è: " + tempoMedio + " minuti");

                            break;

                        } catch (InputMismatchException e) {
                            System.out.println("Input non valido. Per favore, inserisci un numero intero.");
                            scanner.nextLine();
                        } catch (Exception e) {
                            System.out.println("Errore: " + e.getMessage());
                        }
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