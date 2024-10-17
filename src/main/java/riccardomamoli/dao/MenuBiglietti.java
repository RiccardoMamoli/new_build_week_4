package riccardomamoli.dao;


import jakarta.persistence.EntityManager;
import riccardomamoli.dao.BigliettoDao;
import riccardomamoli.entities.Biglietto;


import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class MenuBiglietti {
    public static void gestisciMenuBuglietti(Scanner scanner, EntityManager em) {
        BigliettoDao bdao = new BigliettoDao(em);

        while (true) {
            System.out.println("Menu Biglietti:\n" +
                    "1: Elimina\n" +
                    "2: Visualizzare tutti i biglietti venduti da un distributore\n" +
                    "3: Visualizza dettagli di uno specifico biglietto"+
                    "0: Torna indietro");
            int scelta = scanner.nextInt();
            scanner.nextLine();

            switch (scelta) {
                case 1:
                    System.out.println("Inserisci l'ID del biglietto da rimuovere:");
                    long bigliettoId = scanner.nextLong();
                    scanner.nextLine();

                    try {
                        Biglietto biglietto = bdao.findById(bigliettoId);

                        if (biglietto != null) {
                            bdao.rimuoviBiglietto(bigliettoId);
                            System.out.println("Biglietto rimosso con successo.");
                        } else {
                            System.out.println("Nessun biglietto trovato con ID: " + bigliettoId);
                        }
                    } catch (Exception e) {
                        System.out.println("Errore durante la rimozione del biglietto: " + e.getMessage());
                    }
                    break;
                case 2:
                    System.out.println("Inserisci l'ID del distributore:");
                    long distributoreId = scanner.nextLong();
                    scanner.nextLine();

                    System.out.println("Inserisci la data di inizio (formato: yyyy-MM-dd):");
                    String startDateInput = scanner.nextLine();
                    LocalDate startDate = LocalDate.parse(startDateInput); // Parsing della data di inizio

                    System.out.println("Inserisci la data di fine (formato: yyyy-MM-dd):");
                    String endDateInput = scanner.nextLine();
                    LocalDate endDate = LocalDate.parse(endDateInput); // Parsing della data di fine

                    try {
                        // Chiama il metodo per trovare i biglietti
                        List<Biglietto> biglietti = bdao.trovaBiglietti(distributoreId, startDate, endDate);

                        if (biglietti.isEmpty()) {
                            System.out.println("Nessun biglietto trovato per il distributore con ID " + distributoreId + " nel periodo specificato.");
                        } else {
                            System.out.println("Biglietti trovati:");
                            for (Biglietto biglietto : biglietti) {
                                System.out.println("ID Biglietto: " + biglietto.getId_biglietto() +
                                        ", Data emissione: " + biglietto.getData_emissione() +
                                        ", Codice: " + biglietto.getCodice_univoco_biglietto()+
                                        ",Tratta: "+ biglietto.getTratta());
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Errore durante la ricerca dei biglietti: " + e.getMessage());
                    }
                    break;
                case 3:
                    System.out.println("Inserisci l'ID del biglietto di cui desideri visualizzare i dettagli:");
                    long bigliettooId = scanner.nextLong();
                    scanner.nextLine(); // Pulisce il buffer

                    try {
                        bdao.printBigliettoDetails(bigliettooId);
                    } catch (Exception e) {
                        System.out.println("Errore durante la visualizzazione dei dettagli del biglietto: " + e.getMessage());
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