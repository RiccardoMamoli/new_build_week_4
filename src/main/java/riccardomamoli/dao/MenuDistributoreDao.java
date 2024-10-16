package riccardomamoli.dao;

import jakarta.persistence.EntityManager;
import riccardomamoli.entities.DistributoreAutomatico;
import riccardomamoli.entities.DistributoreFisico;
import riccardomamoli.entities.PuntoVendita;

import java.util.Scanner;

public class MenuDistributoreDao {
    public static void gestisciMenu(Scanner scanner, EntityManager em) {
        PuntoVenditaDao pdao = new PuntoVenditaDao(em);

        while (true) {
            System.out.println("Menu Distributore: (1: Crea, 2: Elimina, 3: Consulta, 0: Torna indietro)");
            int scelta = scanner.nextInt();
            scanner.nextLine();

            switch (scelta) {
                case 1:
                    // Logica per creare un distributore
                    System.out.println("Scegli il tipo di distributore da creare: (1: Automatico, 2: Fisico)");
                    int tipo = scanner.nextInt();
                    scanner.nextLine();

                    PuntoVendita nuovoDistributore;

                    if (tipo == 1) {
                        nuovoDistributore = new DistributoreAutomatico();
                    } else if (tipo == 2) {
                        nuovoDistributore = new DistributoreFisico();
                    } else {
                        System.out.println("Tipo non valido. Operazione annullata.");
                        break;
                    }

                    // Inserisci ulteriori dettagli del distributore se necessario
                    System.out.println("Inserisci l'ID del distributore:");
                    long id = scanner.nextLong();
                    scanner.nextLine();
                    nuovoDistributore.setId(id); // Assicurati che il distributore abbia un metodo setId

                    // Persisti il nuovo distributore
                    try {
                        pdao.creazioneDistributore(nuovoDistributore);
                        System.out.println("Distributore creato.");
                    } catch (Exception e) {
                        System.out.println("Errore durante la creazione del distributore: " + e.getMessage());
                    }
                    break;

                case 2:
                    // Logica per eliminare un distributore
                    System.out.println("Inserisci l'ID del distributore da eliminare:");
                    long idDistributore = scanner.nextLong();
                    scanner.nextLine();
                    try {
                        pdao.rimuoviPuntovendita(idDistributore);
                        System.out.println("Distributore eliminato.");
                    } catch (Exception e) {
                        System.out.println("Errore: " + e.getMessage());
                    }
                    break;

                case 3:
                    // Logica per consultare un distributore
                    System.out.println("Inserisci l'ID del distributore da consultare:");
                    long idConsulta = scanner.nextLong();
                    scanner.nextLine();
                    try {
                        PuntoVendita pv = pdao.ricercaPuntovendita(idConsulta);
                        System.out.println(pv);
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