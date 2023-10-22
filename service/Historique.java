package service;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Historique {

    private String info;
    private String classe;
    private String type;
    int id;
    private Date dateAjout;
    // constructeur par defaut
    public Historique() {
    }
    // le contucteur fais pas moi meme pour pouvoir bien manipuler mes variable
    public Historique(Date dateAjout, String classe, String type, int id , String info) {
        this.info = info;
        this.classe = classe;
        this.type = type;
        this.id = id;
        this.dateAjout = dateAjout;
    }
    // creation des getters et setter pour une meilleure accesibiliter de mes variables
    public String getInfo() {
        return info;
    }

    public String getType() {
        return type;
    }

    public String getClasse() {
        return classe;
    }

    public Date getDateAjout() {
        return dateAjout;
    }

    public int getId() { return id; }
    // creation de methode qui me d afficher l historique des action fais pendant le lancement de l application
    public void afficherCetHistorique(){
        SimpleDateFormat sdf= new SimpleDateFormat("dd/MM/yyyy k:m:s");
        System.out.printf("|%25s", sdf.format(getDateAjout()));
        System.out.printf("|%8s", getClasse());
        System.out.printf("|%6d", getId());
        System.out.printf("|%15s", getType());
        System.out.printf("|%80s", getInfo());
        System.out.println("|\n+-------------------------+--------+------+---------------+--------------------------------------------------------------------------------+");
    }
}
