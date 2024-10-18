package riccardomamoli.menu;


import jakarta.persistence.EntityManager;
import riccardomamoli.dao.UtenteDao;
import riccardomamoli.entities.Utente;

import java.util.Scanner;

public class MenuUtenti {
    public static void gestisciMenuUtenti(Scanner scanner, EntityManager em) {
        UtenteDao udao = new UtenteDao(em);

        while (true) {
            System.out.println("Menu Utenti: (1: Visualizza tutti gli utenti, 2: Elimina, 3: Ricerca Utente 0: Torna indietro)");
            int scelta = scanner.nextInt();
            scanner.nextLine();

            switch (scelta) {
                case 1:
                    // Visualizza tutti gli utenti
                    try {
                        udao.printAllUtenti();
                    } catch (Exception e) {
                        System.out.println("Errore durante la visualizzazione degli utenti: " + e.getMessage());
                    }
                    break;
                case 2:
                    System.out.println("Inserisci l'ID dell'utente da eliminare:");
                    long idUtente = scanner.nextLong();
                    scanner.nextLine();

                    try {
                        // Recupera l'utente dall'ID
                        Utente utenteDaEliminare = udao.findById(idUtente);

                        if (utenteDaEliminare != null) {
                            udao.removeUtente(utenteDaEliminare);
                            System.out.println("Utente eliminato con successo.");
                        } else {
                            System.out.println("Utente non trovato con ID: " + idUtente);
                        }
                    } catch (Exception e) {
                        System.out.println("Errore durante l'eliminazione dell'utente: " + e.getMessage());
                    }
                    break;
                case 3:
                    System.out.println("Inserisci l'ID dell'utente da cercare:");
                    long idUtentee = scanner.nextLong();
                    scanner.nextLine(); // Pulisce il buffer

                    try {
                        Utente utenteTrovato = udao.findById(idUtentee);

                        if (utenteTrovato != null) {
                            System.out.println("Utente trovato:");
                            System.out.println("Nome: " + utenteTrovato.getNome());
                            System.out.println("Cognome: " + utenteTrovato.getCognome());
                            // Aggiungi ulteriori dettagli se necessario
                        } else {
                            System.out.println("Nessun utente trovato con ID: " + idUtentee);
                        }
                    } catch (Exception e) {
                        System.out.println("Errore durante la ricerca dell'utente: " + e.getMessage());
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