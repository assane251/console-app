package sn.groupeisi;

import sn.groupeisi.enums.TypeDemande;
import sn.groupeisi.model.Client;
import sn.groupeisi.model.Demande;
import sn.groupeisi.service.IConsoleApp;
import sn.groupeisi.service.implementation.IConsoleAppImp;
import sn.groupeisi.utils.Input;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;

import static sn.groupeisi.utils.utilsFonction.validerInput;

public class Main {

    private static Client currentClient;

    public static void main(String[] args) throws ParseException {

        Scanner scanner = new Scanner(System.in);

        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();

        int choix;
        boolean isValid = true;

        do {
            // Affichage du menu
            afficherMenu();

            System.out.println("Votre choix: ");

            while (!scanner.hasNextInt()) {
                System.out.println("Veuillez entrer un numéro valide !");
                scanner.next();
            }

            choix = scanner.nextInt();

            Input input = new Input();
            input.setChoix(choix);

            Set<ConstraintViolation<Integer>> violations = validator.validate(input.getChoix());

            if (violations.isEmpty()) {
                traiterChoix(choix);
            } else {
                System.out.println("Erreur: " + violations.stream().findFirst().get().getMessage());
                isValid = false;
            }
        } while ((choix >= 1 && choix <= 4) || !isValid);
    }

    private static void afficherMenu() {
        System.out.println("\n=== MENU PRINCIPAL ===");
        System.out.println("1- Enregistrer une demande.");
        System.out.println("2- Enregistrer un client.");
        System.out.println("3- Rechercher une demande par date.");
        System.out.println("4- Quitter.");
    }

    private static void traiterChoix(int choix) throws ParseException {

        Scanner scanner = new Scanner(System.in);
        IConsoleApp iConsoleApp = new IConsoleAppImp();

        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();

        switch (choix) {
            case 1:
                System.out.println(currentClient);
                if (currentClient == null) {
                    System.out.println("Vous devez d'abord enregistrer un client.");
                    break;
                }

                String description, date, type;

                Demande demande = new Demande();

                System.out.println("=== INFORMATION DEMANDE ===");

                System.out.println("Description: ");
                description = scanner.nextLine();

                System.out.println("Date: ");
                date = scanner.nextLine();
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                java.util.Date parsedDate = formatter.parse(date);
                Date sqlDate = new Date(parsedDate.getTime());

                System.out.println("Type: ");
                Arrays.stream(TypeDemande.values()).map(typeDemande -> typeDemande.name().toUpperCase()).forEach(System.out::println);

                type = scanner.nextLine();

                if (Objects.equals(type, "ADMINISTRATEUR")) type = TypeDemande.ADMINISTRATEUR.name().toUpperCase();
                else type = TypeDemande.COLLABORATEUR.name().toUpperCase();

                demande.setDescription(validerInput(description, validator) ? description : null);

                if (validerInput(date, validator)) demande.setDate(sqlDate);

                demande.setTypeDemande(TypeDemande.valueOf(type));

                demande.setClient(currentClient);

                System.out.println(currentClient);

                iConsoleApp.saveEntity(demande);

                System.out.println("Success!!!.");

                break;
            case 2:
                String nom, prenom, telephone;

                Client client = new Client();

                System.out.println("=== INFORMATION CLIENT ===");

                System.out.println("Nom: ");
                nom = scanner.nextLine();

                System.out.println("Prenom: ");
                prenom = scanner.nextLine();

                System.out.println("Telephone: ");
                telephone = scanner.nextLine();

                client.setNom(validerInput(nom, validator) ? nom : null);

                client.setPrenom(validerInput(prenom, validator) ? prenom : null);

                client.setTelephone(validerInput(telephone, validator) ? telephone : null);

                Client client1 = iConsoleApp.saveEntity(client);

                if (client1 != null) {
                    currentClient = client1;
                }

                System.out.println("Success!!!.");

                break;
            case 3:
                System.out.println("=== RECHERCHE DEMANDE ===");
                System.out.println("Date (format AAAA-MM-JJ): ");
                String dateStr = scanner.nextLine();

                try {
                    Date dateSql = Date.valueOf(dateStr);

                    String demandeByDate = iConsoleApp.findDemandeByDate(dateSql);
                    System.out.printf(demandeByDate);

                } catch (IllegalArgumentException e) {
                    System.out.println("Format de date invalide. Veuillez utiliser le format AAAA-MM-JJ.");
                }
                break;
            case 4:
                System.out.println("Vous avez choisi : Quitter. Au revoir !");
                System.exit(0);
                break;
            default:
                System.out.println("Choix non valide. Veuillez réessayer.");
        }
    }
}