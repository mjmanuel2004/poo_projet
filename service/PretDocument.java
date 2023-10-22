package service;

import documents.Document;
import clients.Clients;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class PretDocument {
    //    Les Variables
    // le client la personne qui va emprunter un livre
    private Clients client;
    // la date a laquelle le client a emprunter un livre
    private Date dateDuPret;
    // la date a laquelle le client doit venir deposer le livre
    private Date dateDeRetour;
    //
    public static final String R = "\u001B[31m";
    //
    public static final String RES = "\u001B[0m";
    //
    public static final String G = "\u001B[32m";
    // le constructeur par defaut
    public PretDocument(){

    }
    // le contucteur fais pas moi meme pour pouvoir bien manipuler mes variable
    public PretDocument(Clients client, Date dateDuPret, Date dateDeRetour) {
        this.client = client;
        this.dateDuPret = dateDuPret;
        this.dateDeRetour = dateDeRetour;
    }
    // creation des getters et setter pour une meilleure accesibiliter de mes variables
    public Clients getClient() {
        return client;
    }

    public void setClient(Clients client) {
        this.client = client;
    }

    public Date getDateDuPret() {
        return dateDuPret;
    }

    public void setDateDuPret(Date dateDuPret) {
        this.dateDuPret = dateDuPret;
    }

    public Date getDateDeRetour() {
        return dateDeRetour;
    }

    public void setDateDeRetour(Date dateDeRetour) {
        this.dateDeRetour = dateDeRetour;
    }
    // creation d une methode qui verifie la date et affiche si le client a rendue le livre
    // il met en rouge si la personne a depasser la date de retour et en vert si la personne est toujour dans les temps
    public void afficherCePret(Document d){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        String nomClient = getClient().getPrenom()+" "+getClient().getNom();
        System.out.printf("|%9s", d.getClass().getSimpleName());
        System.out.printf("|%40s", d.getTitre());
        System.out.printf("|%40s", nomClient);
        System.out.printf("|%13s", getClient().getType());
        System.out.printf("|%13s", sdf.format(getDateDuPret()));
        if (getDateDeRetour().before(date)){
//            EN ROUGE
            System.out.print("|"+R);
            System.out.printf("%13s", sdf.format(getDateDeRetour()));
            System.out.print(RES);
        }else {
//            VERT
            System.out.print("|"+G);
            System.out.printf("%13s", sdf.format(getDateDeRetour()));
            System.out.print(RES);
        }

        System.out.println("|");
    }
    // methode qui calcule la date a laquelle le livre dois etre retourner
    public void calculerDateRetour(){
        Calendar c = Calendar.getInstance();
        c.setTime(getDateDuPret());
        c.add(Calendar.DATE, getClient().getDureeMaxPrets());
        setDateDeRetour(c.getTime());
    }
}
