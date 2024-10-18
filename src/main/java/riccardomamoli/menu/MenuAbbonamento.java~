package riccardomamoli.dao;

import jakarta.persistence.EntityManager;
import riccardomamoli.entities.Abbonamento;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class MenuAbbonamento {
    public static void gestisciMenuAbbonamenti(Scanner scanner, EntityManager em) {
        AbbonamentoDao adao = new AbbonamentoDao(em);

        while (true) {
            System.out.println("Menu Abbonamenti:\n" +
                    "1: Elimina Abbonamento\n" +
                    "2: Visualizzare tutti gli abbonamenti venduti da un distributore\n" +
                    "3: Visualizza dettagli di uno specifico abbonamento\n" +
                    "0: Torna indietro");
            int scelta = scanner.nextInt();
            scanner.nextLine();

            switch (scelta) {
                case 1:
                    while (true) {
                        try {
                            System.out.println("Inserisci l'ID dell'abbonamento da rimuovere (o 0 per tornare indietro):");
                            long abbonamentoId = scanner.nextLong();
                            scanner.nextLine();

                            if (abbonamentoId == 0) {
                                System.out.println("Tornando al menu principale...");
                                break;
                            }

                            Abbonamento abbonamento = adao.findById(abbonamentoId);

                            if (abbonamento != null) {
                                adao.rimuoviAbbonamento(abbonamentoId);
                                System.out.println("Abbonamento rimosso con successo.");
                            } else {
                                System.out.println("Nessun abbonamento trovato con ID: " + abbonamentoId);
                            }
                            break;

                        } catch (InputMismatchException e) {
                            System.out.println("Input non valido. Per favore, inserisci un numero intero.");
                            scanner.nextLine();
                        } catch (Exception e) {
                            System.out.println("Errore durante la rimozione dell'abbonamento: " + e.getMessage());
                        }
                    }
                    break;

                case 2:
                    while (true) {
                        try {
                            System.out.println("Inserisci l'ID del distributore (o 0 per tornare indietro):");
                            long distributoreId = scanner.nextLong();
                            scanner.nextLine();

                            if (distributoreId == 0) {
                                System.out.println("Tornando al menu principale...");
                                break;
                            }

                            System.out.println("Inserisci la data di inizio (formato: yyyy-MM-dd):");
                            String startDateInput = scanner.nextLine();
                            LocalDate startDate = LocalDate.parse(startDateInput);

                            System.out.println("Inserisci la data di fine (formato: yyyy-MM-dd):");
                            String endDateInput = scanner.nextLine();
                            LocalDate endDate = LocalDate.parse(endDateInput);

                            List<Abbonamento> abbonamenti = adao.trovaAbbonamenti(distributoreId, startDate, endDate);

                            if (abbonamenti.isEmpty()) {
                                System.out.println("Nessun abbonamento trovato per il distributore con ID " + distributoreId + " nel periodo specificato.");
                            } else {
                                System.out.println("Abbonamenti trovati:");
                                for (Abbonamento abbonamento : abbonamenti) {
                                    System.out.println("ID Abbonamento: " + abbonamento.getId_abbonamento() +
                                            ", Data emissione: " + abbonamento.getData_emmissione() +
                                            ", Codice: " + abbonamento.getCodice_univoco_abbonamento() +
                                            ", Prezzo: " + abbonamento.getPrezzo());
                                }
                            }
                            break;

                        } catch (InputMismatchException e) {
                            System.out.println("Input non valido. Per favore, inserisci un numero intero.");
                            scanner.nextLine();
                        } catch (DateTimeParseException e) {
                            System.out.println("Formato data non valido. Assicurati di usare il formato yyyy-MM-dd.");
                        } catch (Exception e) {
                            System.out.println("Errore durante la ricerca degli abbonamenti: " + e.getMessage());
                        }
                    }
                    break;

                case 3:
                    while (true) {
                        try {
                            System.out.println("Inserisci l'ID dell'abbonamento di cui desideri visualizzare i dettagli (o 0 per tornare indietro):");
                            long abbonamentooId = scanner.nextLong();
                            scanner.nextLine();

                            if (abbonamentooId == 0) {
                                System.out.println("Tornando al menu principale...");
                                break;
                            }

                            adao.printAbbonamentoDetails(abbonamentooId);
                            break;

                        } catch (InputMismatchException e) {
                            System.out.println("Input non valido. Per favore, inserisci un numero intero.");
                            scanner.nextLine();
                        } catch (Exception e) {
                            System.out.println("Errore durante la visualizzazione dei dettagli dell'abbonamento: " + e.getMessage());
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