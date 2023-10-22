package documents;

import service.Historique;
import principale.Bibliotheque;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Magazine extends Document {
    //    Variables
    // frequence qui contient la frequence du magazine
    int frequence;
    // constructeur par defaut
    public Magazine(){

    }
    // le contucteur pour pouvoir bien manipuler mes variable
    public Magazine(int id, String titre, String localisation, int nbExemplaires, int frequence) {
        super(id, titre, localisation, nbExemplaires);
        this.frequence = frequence;
    }
    // creation des getters et setter pour une meilleure accesibiliter de mes variables
    public int getFrequence() {
        return frequence;
    }

    public void setFrequence(int frequence) {
        this.frequence = frequence;
    }
    //
    public String toString() {
        return "Magazine [ "+super.toString()+", F: "+frequence+" ]";
    }
    // creation d une methode qui me permet d afficher une liste des magazine et voir en meme temps lequel est disponible
    public void afficherCeDocument(int disponible, int nb){
        double k;
        if (nb==0)
            k=0;
        else
            k= Math.ceil(((double) getNbPretTotale()/nb)*100);
        System.out.printf("|%9s", getClass().getSimpleName());
        System.out.printf("|%4d", getId());
        System.out.printf("|%36s", getTitre());
        System.out.print("|    ------------    ");
        System.out.print("|    ------------    ");
        System.out.print("|  ------  ");
        System.out.printf("|%12d", getFrequence());
        System.out.printf("|%12s", getLocalisation());
        System.out.printf("|%12d", getNbExemplaires());
        if (disponible==0){
            System.out.print("|"+ANSI_RED);
            System.out.printf("%12d", disponible);
            System.out.print(ANSI_RESET);
        }else
            System.out.printf("|%12d", disponible);
        System.out.printf("|%13d", getNbPretTotale());
        System.out.println("|");
    }
    // methode qui me permet de modifier les magazine enregistrer
    public void modifierCeDocument(Scanner sc, ArrayList<Historique> historique){
        String choix;
        Bibliotheque b = new Bibliotheque();
        System.out.println("\t Que voulez vous modifier de ce Magazin : ");
        do {
            System.out.println("\t 0) Sortir");
            System.out.println("\t 1) Titre ("+getTitre()+")");
            System.out.println("\t 2) Localisation ("+getLocalisation()+")");
            System.out.println("\t 3) Nombre Exemplaires ("+getNbExemplaires()+")");
            System.out.println("\t 4) Fréquence de parution ("+getFrequence()+")");
            choix = sc.nextLine();
            switch (choix){
                case "0": return;
                case "1":
                    String ancienTitre = getTitre();
                    System.out.println("\t Entrer nouveau titre");
                    setTitre(sc.nextLine());
                    historique.add(new Historique(new Date(), "Document", "Modification",getId(), "Titre de "+ancienTitre+" par "+getTitre()));
                    System.out.println("\t Modification avec succees.");
                    break;
                case "2":
                    String loc = getLocalisation();
                    System.out.println("\t Entrer nouvelle localisation (Salle/Rayon)");
                    setLocalisation(sc.nextLine());
                    System.out.println("\t Modification avec succees.");
                    historique.add(new Historique(new Date(), "Document", "Modification", getId(), "Localisation de "+loc+" par "+getLocalisation()));
                    break;
                case "3":
                    System.out.println("\t Entrer nouveau nombre d'exemplaires");
                    boolean verifier=false;
                    int nb = getNbExemplaires();
                    do {
                        try{
                            setNbExemplaires(sc.nextInt());
                            verifier = true;
                        } catch (Exception e){
                            System.out.println("\t Erreur! Veillez entrer des chiffres...");
                            System.out.println("\t Entrer nouveau nombre d'exemplaires");
                            sc.nextLine();
                        }
                    }while (!verifier);
                    sc.nextLine();
                    historique.add(new Historique(new Date(), "Document", "Modification", getId(), "Nb exemplaires de "+nb+" a "+getNbExemplaires()+" exemplaires en totale"));
                    System.out.println("\t Modification avec succees.");
                    break;
                case "4":
                    System.out.println("\t Entrer nouvelle fréquence ");
                    verifier=false;
                    int freq = getFrequence();
                    do {
                        try{
                            setFrequence(sc.nextInt());
                            verifier = true;
                        } catch (Exception e){
                            System.out.println("\t Erreur! Veillez entrer des chiffres...");
                            System.out.println("\t Entrer nouvelle fréquence ");
                            sc.nextLine();
                        }
                    }while (!verifier);
                    sc.nextLine();
                    historique.add(new Historique(new Date(), "Document", "Modification",getId(), "frequence de "+freq+" par "+getFrequence()));
                    System.out.println("\t Modification avec succees.");
                    break;
                default:
                    System.out.println("\t Choix invalide! veillez réessayer");
                    break;
            }
        }while (true);
    }
}
