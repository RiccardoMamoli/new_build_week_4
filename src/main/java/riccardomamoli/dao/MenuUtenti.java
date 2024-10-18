package riccardomamoli.dao;

import jakarta.persistence.EntityManager;
import riccardomamoli.dao.UtenteDao;
import riccardomamoli.entities.Utente;

import java.util.InputMismatchException;
import java.util.Scanner;

public class MenuUtenti {
    public static void gestisciMenuUtenti(Scanner scanner, EntityManager em) {
        UtenteDao udao = new UtenteDao(em);

        while (true) {
            System.out.println("Menu Utenti:\n1: Visualizza tutti gli utenti\n2: Elimina\n3: Ricerca Utente\n0: Torna indietro");
            int scelta = scanner.nextInt();
            scanner.nextLine();

            switch (scelta) {
                case 1:
                    try {
                        udao.printAllUtenti();
                    } catch (Exception e) {
                        System.out.println("Errore durante la visualizzazione degli utenti: " + e.getMessage());
                    }
                    break;

                case 2:
                    while (true) {
                        try {
                            System.out.println("Inserisci l'ID dell'utente da eliminare (o 0 per tornare indietro):");
                            long idUtente = scanner.nextLong();
                            scanner.nextLine();

                            if (idUtente == 0) {
                                System.out.println("Tornando al menu principale...");
                                break;
                            }

                            Utente utenteDaEliminare = udao.findById(idUtente);

                            if (utenteDaEliminare != null) {
                                udao.removeUtente(utenteDaEliminare);
                                System.out.println("Utente eliminato con successo.");
                            } else {
                                System.out.println("Utente non trovato con ID: " + idUtente);
                            }
                            break;

                        } catch (InputMismatchException e) {
                            System.out.println("Input non valido. Per favore, inserisci un numero intero.");
                            scanner.nextLine();
                        } catch (Exception e) {
                            System.out.println("Errore durante l'eliminazione dell'utente: " + e.getMessage());
                        }
                    }
                    break;

                case 3:
                    while (true) {
                        try {
                            System.out.println("Inserisci l'ID dell'utente da cercare (o 0 per tornare indietro):");
                            long idUtentee = scanner.nextLong();
                            scanner.nextLine();

                            if (idUtentee == 0) {
                                System.out.println("Tornando al menu principale...");
                                break;
                            }

                            Utente utenteTrovato = udao.findById(idUtentee);

                            if (utenteTrovato != null) {
                                System.out.println("Utente trovato:");
                                System.out.println("Nome: " + utenteTrovato.getNome());
                                System.out.println("Cognome: " + utenteTrovato.getCognome());

                                // Display additional information
                                if (utenteTrovato.getTessere() != null) {
                                    System.out.println("Tessera: " + utenteTrovato.getTessere());
                                } else {
                                    System.out.println("Nessuna tessera associata.");
                                }


                            } else {
                                System.out.println("Nessun utente trovato con ID: " + idUtentee);
                            }
                            break;

                        } catch (InputMismatchException e) {
                            System.out.println("Input non valido. Per favore, inserisci un numero intero.");
                            scanner.nextLine();
                        } catch (Exception e) {
                            System.out.println("Errore durante la ricerca dell'utente: " + e.getMessage());
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