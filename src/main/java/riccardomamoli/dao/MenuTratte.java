package riccardomamoli.dao;

import jakarta.persistence.EntityManager;
import riccardomamoli.entities.Tratta;

import java.util.Scanner;

public class MenuTratte {
    public static void gestisciMenuTratte(Scanner scanner, EntityManager em) {
        TrattaDao tdao = new TrattaDao(em);

        while (true) {
            System.out.println("Menu Tratte:\n" +
                    "1: Crea Tratta\n" +
                    "2: Elimina Tratta\n" +
                    "3: Modifica Tratta\n" +
                    "4: Consulta Tratta\n" +
                    "0: Torna indietro");

            int scelta = scanner.nextInt();
            scanner.nextLine();

            switch (scelta) {
                case 1:
                    // Creazione di una nuova tratta
                    System.out.print("Inserisci la zona di partenza: ");
                    String zonaPartenza = scanner.nextLine();

                    System.out.print("Inserisci il capolinea: ");
                    String capolinea = scanner.nextLine();

                    // Richiesta del tempo previsto con validazione
                    int tempoPrevisto = -1;
                    while (tempoPrevisto < 0) {
                        System.out.print("Inserisci il tempo previsto (in minuti): ");
                        try {
                            tempoPrevisto = Integer.parseInt(scanner.nextLine());
                            if (tempoPrevisto < 0) {
                                System.out.println("Il tempo previsto deve essere un numero positivo.");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Input non valido. Inserisci un numero.");
                        }
                    }

                    // Richiesta del prezzo con validazione
                    double prezzo = -1;
                    while (prezzo < 0) {
                        System.out.print("Inserisci il prezzo: ");
                        try {
                            String inputPrezzo = scanner.nextLine().replace(',', '.');
                            prezzo = Double.parseDouble(inputPrezzo);
                            if (prezzo < 0) {
                                System.out.println("Il prezzo deve essere un numero positivo.");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Input non valido. Inserisci un numero valido per il prezzo.");
                        }
                    }

                    Tratta nuovaTratta = new Tratta(zonaPartenza, capolinea, tempoPrevisto, prezzo);
                    try {
                        tdao.addTratta(nuovaTratta);
                        System.out.printf("La tratta %d con partenza da %s e capolinea %s è stata creata.%n",
                                nuovaTratta.getId_tratta(), zonaPartenza, capolinea);
                        System.out.println("Tratta creata con successo.");
                    } catch (Exception e) {
                        System.out.println("Errore durante la creazione della tratta: " + e.getMessage());
                    }
                    break;

                case 2:
                    // Eliminazione di una tratta
                    System.out.print("Inserisci l'ID della tratta da eliminare: ");
                    long idDaEliminare = scanner.nextLong();
                    scanner.nextLine();
                    try {
                        Tratta trattaDaEliminare = tdao.findById(idDaEliminare);
                        if (trattaDaEliminare != null) {
                            tdao.removeTratta(trattaDaEliminare);
                            System.out.println("Tratta eliminata con successo.");
                        } else {
                            System.out.println("Tratta non trovata.");
                        }
                    } catch (Exception e) {
                        System.out.println("Errore durante l'eliminazione della tratta: " + e.getMessage());
                    }
                    break;

                case 3:
                    // Modifica di una tratta
                    System.out.print("Inserisci l'ID della tratta da modificare: ");
                    long idDaModificare = scanner.nextLong();
                    scanner.nextLine();
                    Tratta trattaDaModificare = tdao.findById(idDaModificare);
                    if (trattaDaModificare != null) {
                        System.out.print("Inserisci la nuova zona di partenza (o premi invio per mantenere il valore attuale): ");
                        String nuovaZonaPartenza = scanner.nextLine();
                        if (!nuovaZonaPartenza.isEmpty()) {
                            trattaDaModificare.setZonaPartenza(nuovaZonaPartenza);
                        }

                        System.out.print("Inserisci il nuovo capolinea (o premi invio per mantenere il valore attuale): ");
                        String nuovoCapolinea = scanner.nextLine();
                        if (!nuovoCapolinea.isEmpty()) {
                            trattaDaModificare.setCapolinea(nuovoCapolinea);
                        }

                        // Modifica del tempo previsto con validazione
                        System.out.print("Inserisci il nuovo tempo previsto (o premi invio per mantenere il valore attuale): ");
                        String inputTempo = scanner.nextLine();
                        if (!inputTempo.isEmpty()) {
                            try {
                                int nuovoTempo = Integer.parseInt(inputTempo);
                                if (nuovoTempo >= 0) {
                                    trattaDaModificare.setTempoPrevisto(nuovoTempo);
                                } else {
                                    System.out.println("Il tempo previsto deve essere un numero positivo.");
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Input non valido. Inserisci un numero.");
                            }
                        }

                        // Modifica del prezzo con validazione
                        System.out.print("Inserisci il nuovo prezzo (o premi invio per mantenere il valore attuale): ");
                        String inputPrezzo = scanner.nextLine();
                        if (!inputPrezzo.isEmpty()) {
                            try {
                                String nuovoPrezzoInput = inputPrezzo.replace(',', '.');
                                double nuovoPrezzo = Double.parseDouble(nuovoPrezzoInput);
                                if (nuovoPrezzo >= 0) {
                                    trattaDaModificare.setPrezzo(nuovoPrezzo);
                                } else {
                                    System.out.println("Il prezzo deve essere un numero positivo.");
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Input non valido. Inserisci un numero valido per il prezzo.");
                            }
                        }

                        try {
                            tdao.addTratta(trattaDaModificare);
                            System.out.println("Tratta aggiornata.");
                        } catch (Exception e) {
                            System.out.println("Errore durante l'aggiornamento della tratta: " + e.getMessage());
                        }
                    } else {
                        System.out.println("Tratta non trovata.");
                    }
                    break;

                case 4:
                    // Consultazione di una tratta
                    System.out.print("Inserisci l'ID della tratta da consultare: ");
                    long idDaConsultare = scanner.nextLong();
                    scanner.nextLine();
                    try {
                        Tratta trattaTrovata = tdao.findById(idDaConsultare);
                        if (trattaTrovata != null) {
                            System.out.println("Dettagli della Tratta:");
                            System.out.println("ID Tratta        : " + trattaTrovata.getId_tratta());
                            System.out.println("Zona di Partenza : " + trattaTrovata.getZonaPartenza());
                            System.out.println("Capolinea        : " + trattaTrovata.getCapolinea());
                            System.out.println("Tempo Previsto   : " + trattaTrovata.getTempoPrevisto() + " minuti");
                            System.out.println("Prezzo           : €" + trattaTrovata.getPrezzo());
                        } else {
                            System.out.println("Tratta non trovata.");
                        }
                    } catch (Exception e) {
                        System.out.println("Errore durante la consultazione della tratta: " + e.getMessage());
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
