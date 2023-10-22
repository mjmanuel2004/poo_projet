package documents;

import service.Historique;
import service.PretDocument;

import java.util.ArrayList;
import java.util.Scanner;

public abstract class Document {
    //    les variables
    protected String titre, localisation;
    //nbExemplaires est le nombre d'exemplaires totale inclue les documents qui sont en cours de pret
    protected int id, nbExemplaires, nbPretTotale=0;
    protected ArrayList<PretDocument> listPret = new ArrayList<PretDocument>();
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";
    // constructeur par defaut
    public Document(){

    }
    // le contucteur fais pas moi meme pour pouvoir bien manipuler mes variable
    public Document(int id, String titre, String localisation, int nbExemplaires) {
        this.id = id;
        this.titre = titre;
        this.localisation = localisation;
        this.nbExemplaires = nbExemplaires;
    }
    // creation des getters et setter pour une meilleure accesibiliter de mes variables
    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getLocalisation() {
        return localisation;
    }

    public void setLocalisation(String localisation) {
        this.localisation = localisation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNbExemplaires() {
        return nbExemplaires;
    }

    public void setNbExemplaires(int nbExemplaires) {
        this.nbExemplaires = nbExemplaires;
    }

    public int getNbPretTotale() {
        return nbPretTotale;
    }

    public void setNbPretTotale(int nbPretTotale) {
        this.nbPretTotale = nbPretTotale;
    }
    //
    public ArrayList<PretDocument> getListPret() {
        return listPret;
    }
    //
    public String toString() {
        String ch=id+", T: "+titre+", N.E: "+nbExemplaires+", L: "+localisation;
        return ch;
    }
    // creation des methode abstract
    public abstract void afficherCeDocument(int disponible, int nb);

    public abstract void modifierCeDocument(Scanner sc, ArrayList<Historique> historique);
}
