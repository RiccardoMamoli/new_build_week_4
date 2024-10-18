package riccardomamoli.menu;

import jakarta.persistence.EntityManager;
import riccardomamoli.dao.PuntoVenditaDao;
import riccardomamoli.entities.DistributoreAutomatico;
import riccardomamoli.entities.DistributoreFisico;
import riccardomamoli.entities.PuntoVendita;

import java.util.InputMismatchException;
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
                    while (true) {
                        try {
                            System.out.println("Scegli il tipo di distributore da creare: (1: Automatico, 2: Fisico, 0: Torna al menu principale)");
                            int tipo = scanner.nextInt();
                            scanner.nextLine();

                            if (tipo == 0) {
                                System.out.println("Tornando al menu principale...");
                                break;
                            }

                            PuntoVendita nuovoDistributore;

                            if (tipo == 1) {
                                nuovoDistributore = new DistributoreAutomatico();
                            } else if (tipo == 2) {
                                nuovoDistributore = new DistributoreFisico();
                            } else {
                                System.out.println("Tipo non valido. Operazione annullata.");
                                continue;
                            }

                            System.out.println("Inserisci l'ID del distributore da creare :");
                            long id = scanner.nextLong();
                            scanner.nextLine();
                            nuovoDistributore.setId(id);

                            try {
                                pdao.addPuntoVendita(nuovoDistributore);
                                System.out.println("Distributore creato.");
                                break;

                            } catch (Exception e) {
                                System.out.println("Errore durante la creazione del distributore: " + e.getMessage());
                            }
                        } catch (InputMismatchException e) {
                            System.out.println("Input non valido. Per favore, inserisci un numero intero.");
                            scanner.nextLine();
                        }
                    }
                    break;

                case 2:
                    while (true) {
                        try {
                            System.out.println("Inserisci l'ID del distributore da eliminare (o 0 per tornare al menu principale):");
                            long idDistributore = scanner.nextLong();
                            scanner.nextLine();

                            if (idDistributore == 0) {
                                System.out.println("Tornando al menu principale...");
                                break;
                            }

                            try {
                                pdao.deletePuntoVendita(idDistributore);
                                System.out.println("Distributore eliminato.");
                                break;

                            } catch (Exception e) {
                                System.out.println("Errore: " + e.getMessage());
                            }
                        } catch (InputMismatchException e) {
                            System.out.println("Input non valido. Per favore, inserisci un numero intero.");
                            scanner.nextLine();
                        }
                    }
                    break;

                case 3:
                    while (true) {
                        try {
                            System.out.println("Inserisci l'ID del distributore da consultare (o 0 per tornare al menu principale):");
                            long idConsulta = scanner.nextLong();
                            scanner.nextLine();

                            if (idConsulta == 0) {
                                System.out.println("Tornando al menu principale...");
                                break;
                            }

                            try {
                                PuntoVendita pv = pdao.ricercaPuntovendita(idConsulta);
                                if (pv != null) {
                                    System.out.println(pv);
                                } else {
                                    System.out.println("Distributore non trovato per ID: " + idConsulta);
                                }
                                break;

                            } catch (Exception e) {
                                System.out.println("Errore: " + e.getMessage());
                            }
                        } catch (InputMismatchException e) {
                            System.out.println("Input non valido. Per favore, inserisci un numero intero.");
                            scanner.nextLine();
                        }
                    }
                    break;

                case 4:
                    while (true) {
                        try {
                            System.out.println("Inserisci l'ID del distributore da verificare (o 0 per tornare al menu principale):");
                            long idVerificaStato = scanner.nextLong();
                            scanner.nextLine();

                            if (idVerificaStato == 0) {
                                System.out.println("Tornando al menu principale...");
                                break;
                            }

                            try {
                                boolean isActive = pdao.isActive(idVerificaStato);

                                if (isActive) {
                                    System.out.println("Il distributore con ID " + idVerificaStato + " è attivo.");
                                } else {
                                    System.out.println("Il distributore con ID " + idVerificaStato + " non è attivo.");
                                }
                                break;

                            } catch (Exception e) {
                                System.out.println("Errore: " + e.getMessage());
                            }
                        } catch (InputMismatchException e) {
                            System.out.println("Input non valido. Per favore, inserisci un numero intero.");
                            scanner.nextLine();
                        }
                    }
                    break;

                case 5:
                    while (true) {
                        try {
                            System.out.println("Inserisci l'ID del distributore a cui cambiare lo stato (o 0 per tornare al menu principale):");
                            long idVerifica = scanner.nextLong();
                            scanner.nextLine();

                            if (idVerifica == 0) {
                                System.out.println("Tornando al menu principale...");
                                break;
                            }

                            Boolean isActive = pdao.isActive(idVerifica);

                            if (isActive == null) {
                                System.out.println("Il valore dello stato attivo del distributore con ID " + idVerifica + " è null, quindi non può essere cambiato.");
                                continue;
                            }

                            if (isActive) {
                                System.out.println("Il distributore con ID " + idVerifica + " è attivo.");
                                System.out.print("Vuoi disattivarlo? (s/n): ");
                                String risposta = scanner.nextLine();
                                if (risposta.equalsIgnoreCase("s")) {
                                    pdao.updateActive(idVerifica, false);
                                    System.out.println("Il distributore con ID " + idVerifica + " è stato disattivato.");
                                } else {
                                    System.out.println("Operazione annullata. Il distributore rimane attivo.");
                                }
                            } else {
                                System.out.println("Il distributore con ID " + idVerifica + " non è attivo.");
                                System.out.print("Vuoi attivarlo? (s/n): ");
                                String risposta = scanner.nextLine();
                                if (risposta.equalsIgnoreCase("s")) {
                                    pdao.updateActive(idVerifica, true);
                                    System.out.println("Il distributore con ID " + idVerifica + " è stato attivato.");
                                } else {
                                    System.out.println("Operazione annullata. Il distributore rimane inattivo.");
                                }
                            }
                            break;

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