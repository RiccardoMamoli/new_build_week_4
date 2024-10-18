package riccardomamoli.menu;

import jakarta.persistence.EntityManager;
import riccardomamoli.dao.PuntoVenditaDao;
import riccardomamoli.entities.DistributoreAutomatico;
import riccardomamoli.entities.DistributoreFisico;
import riccardomamoli.entities.PuntoVendita;

import java.util.Scanner;

public class MenuDistributore {
    public static void gestisciMenu(Scanner scanner, EntityManager em) {
        PuntoVenditaDao pdao = new PuntoVenditaDao(em);

        while (true) {
            System.out.println("Menu Distributore:\n" +
                    "1: Crea\n" +
                    "2: Elimina\n" +
                    "3: Consulta dati\n" +
                    "4: Verifica stato\n" +
                    "5: Cambia lo stato\n" +
                    "0: Torna indietro");
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
                    System.out.println("Inserisci l'ID del distributore da creare :");
                    long id = scanner.nextLong();
                    scanner.nextLine();
                    nuovoDistributore.setId(id);

                    try {
                        pdao.addPuntoVendita(nuovoDistributore);
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
                        pdao.deletePuntoVendita(idDistributore);
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
                case 4:
                    // Logica per verificare lo stato di un distributore
                    System.out.println("Inserisci l'ID del distributore da verificare:");
                    long idVerificaStato = scanner.nextLong();
                    scanner.nextLine();
                    try {
                        boolean isActive = pdao.isActive(idVerificaStato);

                        if (isActive) {
                            System.out.println("Il distributore con ID " + idVerificaStato + " è attivo.");
                        } else {
                            System.out.println("Il distributore con ID " + idVerificaStato + " non è attivo.");
                        }
                    } catch (Exception e) {
                        System.out.println("Errore: " + e.getMessage());
                    }
                    break;
                case 5:
                    // Logica per switchare lo stato del distributore
                    System.out.println("Inserisci l'ID del distributore a cui cambiare lo stato:");
                    long idVerifica = scanner.nextLong();
                    scanner.nextLine();

                    try {
                        Boolean isActive = pdao.isActive(idVerifica); // Verifica se il distributore è attivo

                        // Controllo se il valore di isActive è null
                        if (isActive == null) {
                            System.out.println("Il valore dello stato attivo del distributore con ID " + idVerifica + " è null, quindi non può essere cambiato.");
                            return; // Esci dal metodo o dal ciclo se necessario
                        }

                        if (isActive) {
                            System.out.println("Il distributore con ID " + idVerifica + " è attivo.");
                            System.out.println("Vuoi disattivarlo? (s/n)");
                            String risposta = scanner.nextLine();
                            if (risposta.equalsIgnoreCase("s")) {
                                pdao.updateActive(idVerifica, false); // Disattiva il distributore
                                System.out.println("Il distributore con ID " + idVerifica + " è stato disattivato.");
                            } else {
                                System.out.println("Operazione annullata. Il distributore rimane attivo.");
                            }
                        } else {
                            System.out.println("Il distributore con ID " + idVerifica + " non è attivo.");
                            // Chiedi all'utente se vuole attivarlo
                            System.out.println("Vuoi attivarlo? (s/n)");
                            String risposta = scanner.nextLine();
                            if (risposta.equalsIgnoreCase("s")) {
                                pdao.updateActive(idVerifica, true); // Attiva il distributore
                                System.out.println("Il distributore con ID " + idVerifica + " è stato attivato.");
                            } else {
                                System.out.println("Operazione annullata. Il distributore rimane inattivo.");
                            }
                        }
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