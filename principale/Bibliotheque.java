package principale;

import documents.Article;
import documents.Document;
import documents.Livre;
import documents.Magazine;
import service.Historique;
import service.PretDocument;
import clients.Clients;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Bibliotheque {
    //    Les Variables
    private ArrayList<Clients> listClients = new ArrayList<>();
    private ArrayList<Document> listDocument = new ArrayList<>();
    private ArrayList<Historique> historique = new ArrayList<>();
    private Scanner sc = new Scanner(System.in);
    private int id_client = 6, id_document = 6, nombreDePretTotale=0;
    public static final String Y = "\u001B[33m";
    public static final String RES = "\u001B[0m";

    public static void main(String[] args){
//        modification d'un client en pret, test si retard ou si il ne peut plus prendre
//        modification d'un document en pret, test si document existe
//        teste saisie sur tout les "int" et les "date"
//        affiche que des document de client souhaiter dans rendre pret ou modifier pret
//        Affichage sous forme de tableau et avec des couleurs en utilisant le codage ANSI
//        id_client et id_document auto_increment
//        avant suppression d'un client il faut qu'il rend tout ces document
//        renouvellement d'un client retard s'effectue que s'il a rendu ces pret
//        confirmation de suppression pour adherent && document et affiche message d'alert si document en cours de pret
//        recharche par nom ou prenom ou adresse et ignore la majuscule et minuscule
//        table historique avec un affichage de la derniaire modification et avec 10 par foie
//        CHERCHER DANS HISTORIQUE PAR ID OU NOM DE CLASSE OU TYPE OU DATE
//        Clients : nombre des emprunts effectués, le nombre des emprunts en cours et le nombre des emprunts dépassés.
//        Document : Nombre effectuer pour chaque document et statestique (%) par rapport a la demande
//        recherche document et afficher ca liste pret
        Bibliotheque b = new Bibliotheque();
        b.enregistrement();
        b.histo();
        b.menu();
    }
    //
    public void histo(){
        Date dateAujourdhui = new Date();
        listDocument.forEach(d -> {
            for (PretDocument pretDocument:d.getListPret()){
                long dureePret = dateAujourdhui.getTime()/(24*60*60*1000)-pretDocument.getDateDuPret().getTime()/(24*60*60*1000)-1;
                if (dureePret>pretDocument.getClient().getDureeMaxPrets()){
                    pretDocument.getClient().setRetard(true);
                    historique.add(new Historique(new Date(), "Clients", "Retard", pretDocument.getClient().getId(), pretDocument.getClient().getPrenom()+" "+pretDocument.getClient().getNom()+" est retardataire : n'a pas rendu le document: "+d.getTitre()));
                }
            }
        });
    }

    public void menu(){
        String choix;
        do {
            System.out.println("\n\t **********\tLE MENU PRINCIPALE DE NOTRE BIBLIOTHEQUE\t**********");
            System.out.println("\t Veillez choisir le numero correspondant à votre choix");
            System.out.println("\t 0 ) Quitter l'application");
            System.out.println("\t 1 ) Gestion des Clients");
            System.out.println("\t 2 ) Gestion des documents");
            System.out.println("\t 3 ) Gestion des prêts");
            System.out.println("\t 4 ) Afficher tout l'historique\n");
            choix=sc.nextLine();
            switch (choix){
                case "0":
                    System.out.println("\t Fermeture en cours ...");
                    System.exit(0);
                    break;
                case "1": gestionClients(); break;
                case "2": gestionDocument(); break;
                case "3": gestionPret(); break;
                case "4": toutHistorique(); break;
                default: System.out.println("\t Choix invalide, veillez réessayer"); break;
            }
        }while (true);
    }

    public void gestionClients(){
        String choixClients;
        do {
            System.out.println("\n\t *****\tGestion Clients\t*****");
            System.out.println("\t Veillez choisir le numero correspondant à votre choix");
            System.out.println("\t 0 ) Retour au menu principale");
            System.out.println("\t 1 ) Afficher les clients disponible");
            System.out.println("\t 2 ) Ajouter un nouveau client");
            System.out.println("\t 3 ) Modifier un client par id");
            System.out.println("\t 4 ) Supprimer un client par id");
            System.out.println("\t 5 ) Rechercher un client et afficher ces informations");
            System.out.println("\t 6 ) Afficher les clients retardataires");
            System.out.println("\t 7 ) Renouveler un clients");
            choixClients=sc.nextLine();
            switch (choixClients){
                case "0":   menu(); break;
                case "1":   afficherClients(); break;
                case "2":   ajouterClients(); break;
                case "3":   modifierClients(); break;
                case "4":   supprimerClients(); break;
                case "5":   chercherToutInformationClients(); break;
                case "6":   afficherClientsRetard(); break;
                case "7":   renouvellerClientsRetard(); break;
                default:    System.out.println("\t Choix invalide! veillez réessayer"); break;
            }
        }while (!choixClients.equals("0"));
    }

    public void afficherClients(){
        System.out.println("\n\t *****\tLes Clients de la bibliotheque\t*****");
        int nb =0;
        if (listClients.size() == 0)
            System.out.println("\t Il n'y a pas encore de client enregistré dans cette bibliothéque");
        else{
            enteteClients();
            for (Clients a:listClients){
                a.afficherCetClient();
                cadreClients();
                nb++;
            }
        }
        System.out.println("\n\t "+nb+" Client trouvé! Appuyez sur entrer pour continuer...");
        sc.nextLine();
    }

    public void ajouterClients(){
        System.out.println("\n\t *****\tAjouter un nouveau client a la bibliotheque\t*****");
        Clients a = new Clients();
        a.entreeTypeClient(sc);
        a.setId(id_client);
        System.out.println("\t Nom du client a ajouter : ");
        a.setNom(sc.nextLine());
        System.out.println("\t Prenom du client : ");
        a.setPrenom(sc.nextLine());
        System.out.println("\t Adresse du client : ");
        a.setAdresse(sc.nextLine());
        this.listClients.add(a);
        this.id_client++;
        historique.add(new Historique(new Date(), "Clients", "Ajout",a.getId(), a.getType()+" sous le nom de "+a.getPrenom()+" "+a.getNom()));
        System.out.println("\t Clients "+a.getPrenom()+" "+a.getNom()+" ajouté avec succées.");

    }

    public void modifierClients(){
        System.out.println("\n\t *****\tModifier un client par id\t*****");
        Clients a;
        a = chercherClientsById();
        a.modifierCetClient(sc, historique);
    }

    public void supprimerClients(){
        System.out.println("\n\t *****\tSuppression de Client\t*****");
        Clients a;
        a = chercherClientsById();
//        VOIR SI LE CLIENT A UN PRET EN COURS
        for (Document d:listDocument)
            for (PretDocument pretDocument:d.getListPret())
                if (pretDocument.getClient() == a){
                    System.out.println("\t Suppression impossible! Cet client a encore des prets en cours...");

                    return;
                }
        System.out.println("\t Vous êtes sur de supprimer du client : "+a.getPrenom()+" "+a.getNom()+" ?");
        System.out.println("\t 1 ) Continuer");
        if (sc.nextLine().equals("1")){
//           SUPPRIMER CLIENT
            historique.add(new Historique(new Date(), "Clients", "Suppression",a.getId(), a.getType()+": "+a.getPrenom()+" "+a.getNom()));
            listClients.remove(a);
            System.out.println("\t Clients supprimer avec succees...");
            return;
        }else
            System.out.println("\t Annulation...");
    }

    public void chercherToutInformationClients(){
        System.out.println("\n\t *****\tChercher toutes les information d'un client");
        System.out.println("\t Chercher le client : ");
        ArrayList<Clients> listClientsTrouver = new ArrayList<>();
        chercherClientsByIdOrNameOrAdresse(listClientsTrouver);
        enteteClients();
        for (Clients a:listClientsTrouver){
            a.afficherCetClient();
            cadreClients();
        }
        if (listClientsTrouver.size() == 1)
            System.out.println("\n\t Les livres en cours de prêt de "+listClientsTrouver.get(0).getPrenom()+" "+listClientsTrouver.get(0).getNom()+" : \n");
        else
            System.out.println("\n\t Les livres en cours de prêt de ces adherents : \n");
        entetePret();
        for (Clients a:listClientsTrouver)
            for (Document d:listDocument)
                for (PretDocument pretDocument:d.getListPret())
                    if (pretDocument.getClient() == a){
                        pretDocument.afficherCePret(d);
                        cadrePret();
                    }
        System.out.println("\t Appuyez sur entrer pour continuer...");
        sc.nextLine();
    }

    public void afficherClientsRetard(){
        System.out.println("\n\t *****\tLes clients retardataires !\t*****\n");
        int nb=0;
        for (Clients a:listClients)
            if (a.isRetard()) {
                if (nb==0){
                    enteteClients();
                }
                a.afficherCetClient();
                cadreClients();
                nb++;
            }
        if (nb==0)
            System.out.println("\t Il n'y a pas encore de clients retardataire");
        System.out.println("\n\t "+nb+" Clients retardataire! Appuyez sur entrer pour continuer...");
        sc.nextLine();
    }

    public void renouvellerClientsRetard(){
        System.out.println("\n\t *****\t Renouveler un client en retard\t*****");
        int id;
        String choix;
        do {
            System.out.println("\t 0 ) Retour");
            System.out.println("\t 1 ) afficher tout les clients en retard");
            System.out.println("\t 2 ) Renouveler client par ID");
            System.out.println("\t Veillez entrer votre choix");
            choix = sc.nextLine();
            switch (choix){
                case "0":   gestionClients(); break;
                case "1":   afficherClientsRetard(); break;
                case "2":
                    System.out.println("\t Entrer ID : ");
                    id = verifierID();
                    sc.nextLine();
                    for (Clients a:listClients){
                        if (a.getId() == id){
//                            CLIENT TROUVER
                            if (a.isRetard()){
                                for (Document d:listDocument){
                                    for (PretDocument pretDocument:d.getListPret()){
                                        if (pretDocument.getClient() == a){
                                            System.out.println("\t Ce client n'a pas encore rendu le document "+d.getTitre());
                                            System.out.println("\t Il faut rendre le document d'abord! Merci");
                                            return;
                                        }
                                    }
                                }
                                a.setRetard(false);
                                historique.add(new Historique(new Date(), "Clients", "Renouvellement",a.getId(), a.getPrenom()+" "+a.getNom()));
                                System.out.println("\t Renouvellement réussie avec succées!");
                                gestionClients();
                                return;
                            }
                            System.out.println("\t Cette client n'est pas retardataire");
                            return;
                        }
                    }
                    System.out.println("\t Clients introuvable! Veillez vérifier l'ID et réessayer...");
                    break;
                default:
                    System.out.println("\t Choix invalide! Veillez réessayer");
                    break;
            }
        }while (!choix.equals("0"));
    }

    public void gestionDocument(){
        String choixdocument;
        do {
            System.out.println("\n\t *****\tGestion Documents\t*****");
            System.out.println("\t Veillez choisir le numero correspondant à votre choix");
            System.out.println("\t 0 ) Retour au menu principale");
            System.out.println("\t 1 ) Afficher les Documents disponible");
            System.out.println("\t 2 ) Ajouter un nouveau document");
            System.out.println("\t 3 ) Modifier un document par id");
            System.out.println("\t 4 ) Supprimer un document par id");
            System.out.println("\t 5 ) Chercher un document");
            choixdocument = sc.nextLine();
            switch (choixdocument){
                case "0": menu(); break;
                case "1": afficherDocument(); break;
                case "2": ajouterDocument(); break;
                case "3": modifierDocument(); break;
                case "4": supprimerDocument(); break;
                case "5": chercherDocument(); break;
                default: System.out.println("\t Choix invalide! veillez réessayer"); break;
            }
        }while (!choixdocument.equals("0"));
    }

    public void afficherDocument(){
        System.out.println("\n\t *****\tLes documents de la bibliotheque\t*****");
        int  nb=0;
        if (listDocument.size() == 0)
            System.out.println("\t Il n'y a pas encore de document enregistrer dans cette bibliotheque");
        else{
            enteteDocument();
            for (Document d:listDocument){
                int disponible = d.getNbExemplaires()-d.getListPret().size();
                d.afficherCeDocument(disponible, nombreDePretTotale);
                cadreDocument();
                nb++;
            }
        }
        System.out.println("\n\t "+nb+" Document disponible! Appuyez sur entrer pour continuer...");
        sc.nextLine();
    }

    public void ajouterDocument(){
        System.out.println("\n\t *****\tAjouter un nouveau document a la bibliotheque\t*****");
        String choixType, str;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        System.out.println("\t Veillez selectionner le type du document a ajouter");
        do {
            System.out.println("\t 0) Annuler");
            System.out.println("\t 1) Article");
            System.out.println("\t 2) Livre");
            System.out.println("\t 3) Magazine");
            choixType = sc.nextLine();
            switch (choixType){
                case "0": gestionDocument(); break;
                case "1":
                    Article a = new Article();
                    remplissageDocument(a);
                    System.out.println("\t Entrer Nom d'auteur");
                    a.setNomAuteur(sc.nextLine());
                    System.out.println("\t Entrer la date de publication de l'article sous forme (DD/MM/YYYY)");
                    str = sc.nextLine();
                    boolean verifier = false;
                    do {
                        try {
                            a.setDatePublication(sdf.parse(str));
                            verifier = true;
                        }catch (ParseException e){
                            System.out.println("\t Forme non respecter!");
                            System.out.println("\t Entrer la date de publication de l'article sous forme (DD/MM/YYYY)");
                            str = sc.nextLine();
                        }
                    }while (!verifier);
                    this.listDocument.add(a);
                    historique.add(new Historique(new Date(), "Document", "Ajout",a.getId(), "Article : "+a.getTitre()+" par "+a.getNomAuteur()));
                    this.id_document++;
                    System.out.println("\n\t Article ajouté avec succées.\n");
                    return;
                case "2":
                    Livre l = new Livre();
                    remplissageDocument(l);
                    System.out.println("\t Entrer Nom d'auteur");
                    l.setNomAuteur(sc.nextLine());
                    System.out.println("\t Entrer Nom éditeur");
                    l.setNomEditeur(sc.nextLine());
                    System.out.println("\t Entrer la date de l'édition du livre sous forme (DD/MM/YYYY)");
                    str = sc.nextLine();
                    verifier = false;
                    do {
                        try {
                            l.setDateEdition(sdf.parse(str));
                            verifier = true;
                        }catch (ParseException e){
                            System.out.println("\t Forme non respecter!");
                            System.out.println("\t Entrer la date de l'édition du livre sous forme (DD/MM/YYYY)");
                            str = sc.nextLine();
                        }
                    }while (!verifier);
                    this.listDocument.add(l);
                    historique.add(new Historique(new Date(), "Document", "Ajout",l.getId(), "Livre : "+l.getTitre()+" par "+l.getNomAuteur()));
                    this.id_document++;
                    System.out.println("\n\t Livre ajouté avec succées.\n");
                    return;
                case "3":
                    Magazine m = new Magazine();
                    verifier = false;
                    remplissageDocument(m);
                    System.out.println("\t Entree la fréquence de parution du magazin");
                    do {
                        try {
                            m.setFrequence(sc.nextInt());
                            verifier = true;
                        }
                        catch (Exception e){
                            System.out.println("\t Erreur! Veillez entrer des chiffres...");
                            System.out.println("\t Entree la fréquence de parution du magazin");
                            sc.nextLine();
                        }
                    }while (!verifier);
                    sc.nextLine();
                    this.listDocument.add(m);
                    historique.add(new Historique(new Date(), "Document", "Ajout",m.getId(), "Magazine : "+m.getTitre()));
                    this.id_document++;
                    System.out.println("\n\t Magazine ajouté avec succées.\n");
                    return;
                default:
                    System.out.println("\t Choix du type Document est non valide veillez réessayer");
                    break;
            }
        }while (!choixType.equals("0"));
    }

    public void modifierDocument(){
        System.out.println("\n\t *****\tModifier un document par id\t*****");
        Document d;
        d = chercherDocumentById();
        d.modifierCeDocument(sc, historique);
    }

    public void supprimerDocument(){
        System.out.println("\n\t *****\tSuppression de document\t*****");
        Document d;
        d = chercherDocumentById();
        System.out.println("\t Vous êtes sur de supprimer le document : "+d.getTitre()+" ?");
        if (d.getListPret().size() !=0)
            System.out.println("\n\t !! Ce document possède des prêts en cours, ca suppression évoquera la suppression de tout ces prets !!");
        System.out.println("\t 1 ) Continuer");
        if (sc.nextLine().equals("1")){
            historique.add(new Historique(new Date(), "Document", "Suppression",d.getId(), d.getTitre()));
            listDocument.remove(d);
            System.out.println("\t Document supprimer avec succees...");
            return;
        }else
            System.out.println("\t Annulation...");
    }

    public void chercherDocument(){
        System.out.println("\n\t *****\tChercher un document et afficher ces prets\t*****");
        ArrayList<Document> listDocumentTrouver = new ArrayList<>();
        chercherDocByIdOrTitre(listDocumentTrouver);
        enteteDocument();
        for (Document d:listDocumentTrouver){
            int disponible = d.getNbExemplaires()-d.getListPret().size();
            d.afficherCeDocument(disponible, nombreDePretTotale);
            cadreDocument();
        }
        if (listDocumentTrouver.size() == 1)
            System.out.println("\n\t Les prêts en cours de "+listDocumentTrouver.get(0).getTitre()+" : \n");
        else
            System.out.println("\n\t Les prêts en cours de ces documents : \n");
        entetePret();
        for (Document d:listDocumentTrouver)
            for (PretDocument pretDocument:d.getListPret()){
                pretDocument.afficherCePret(d);
                cadrePret();
            }
        System.out.println("\t Appuyez sur entrer pour continuer...");
        sc.nextLine();
    }

    public void gestionPret(){
        String choixPret;
        do {
            System.out.println("\n\t *****\tGestion Des Prets\t*****");
            System.out.println("\t Veillez choisir le numero correspondant à votre choix");
            System.out.println("\t 0 ) Retour au menu principale");
            System.out.println("\t 1 ) Afficher tout Prêts disponible");
            System.out.println("\t 2 ) Ajouter un nouveau prêt");
            System.out.println("\t 3 ) Modifier un prêt");
            System.out.println("\t 4 ) Rendre un document");
            choixPret = sc.nextLine();
            switch (choixPret){
                case "0": menu(); break;
                case "1": afficherPret(); break;
                case "2": ajouterPret(); break;
                case "3": modifierPret(); break;
                case "4": rendrePret(); break;
                default: System.out.println("\t Choix invalide! veillez réessayer"); break;
            }
        }while (!choixPret.equals("0"));
    }

    public void afficherPret(){
        int nb=0;
        System.out.println("\n\t ******\tTout les prêts en cours\t*****");
//        VERIFIER SI IL EXISTE UN PRET EN COURS
        for (Document d:listDocument)
            for (PretDocument pretDocument:d.getListPret()) {
                if (nb==0){
                    entetePret();
                }
                pretDocument.afficherCePret(d);
                cadrePret();
                nb++;
            }
        if (nb==0){
            System.out.println("\t Il n'y a aucune pret en cours!");}
        System.out.println("\n\t "+nb+" pret disponible! Appuyez sur entrer pour continuer...");
        sc.nextLine();
    }

    public void ajouterPret(){
        System.out.println("\n\t *****\tAjouter un prêt\t*****");
        System.out.println("\t Selectionnez le client");
        Clients a;
        a = chercherClientsById();
        System.out.println("\t Selectionnez le document");
        Document d;
        d = chercherDocumentById();
//      VOIR SI RETARDATAIRE
        if (!a.isRetard()){
//          ADHERENT N'EST PAS RETARDATAIRE & VOIRE S'IL A ATTEINT CES NOMBRE DE PRET LIMITE
            if (a.getNbMaxPrets()>a.getNbPret()){
//              TOUTES LES CONTRAINTES SUR LE CLIENT SONT VERIFIER & VOIR SI DOCUMENT EXISTE ENCORE DANS LA BIBLIOTHEQUE
                if (d.getListPret().size()<d.getNbExemplaires()){
//                  TOUT EST VERIFIER & MAINTENANT NE RESTE QUE L'AFFECTATION
                    PretDocument pretDocument = new PretDocument();
                    pretDocument.setDateDuPret(new Date());
                    pretDocument.setClient(a);
                    pretDocument.calculerDateRetour();
//                   AJOUTER LE PRET
                    d.getListPret().add(pretDocument);
                    a.setNbPret(a.getNbPret()+1);
                    d.setNbPretTotale(d.getNbPretTotale()+1);
                    a.setNbPretTotale(a.getNbPretTotale()+1);
                    historique.add(new Historique(new Date(),  "Pret", "Ajout", a.getId(), a.getType()+": "+a.getPrenom()+" "+a.getNom()+" a pris le document : "+d.getTitre()));
                    System.out.println("\t Pret du document effectuée avec succées! MERCI.");
                    nombreDePretTotale++;
                    return;
                }else {
                    System.out.println("\t Ce document n'est plus disponible!");
                    return;
                }
            }else {
                System.out.println("\t Cette adherent a atteint ces emprint limite");
                return;
            }
        }else {
            System.out.println("\t Ce client est retardataire et ne peux plus enpreter de documents!");
        }
    }

    public void modifierPret(){
        System.out.println("\n\t *****\tModification d'un prêt\t*****");
        String choix;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        System.out.println("\t Selectionnez le client");
        Clients a;
        a = chercherClientsById();
        System.out.println("\t Selectionnez le document");
        Document d;
        d = chercherDocumentById2(a);
        for (PretDocument pretDocument:d.getListPret()){
            if (pretDocument.getClient() == a){
//                MODIFIER
                do {
                    System.out.println("\t Veillez choisir le numero correspondant à votre choix");
                    System.out.println("\t 0 ) Annuler");
                    System.out.println("\t 1 ) Modifier le document ("+d.getTitre()+")");
                    System.out.println("\t 2 ) Modifier le client ("+a.getPrenom()+" "+a.getNom()+")");
                    System.out.println("\t 3 ) Modifier la date du pret ("+sdf.format(pretDocument.getDateDuPret())+")");
                    choix = sc.nextLine();
                    switch (choix){
                        case "0": gestionPret(); break;
                        case "1":
                            Document newDocument;
                            System.out.println("\t Chercher le nouveau document");
                            newDocument = chercherDocumentById();
//                          VERIFIER SI LE NOUVEAU DOCUMENT EXISTE
                            if (newDocument.getListPret().size()<newDocument.getNbExemplaires()){
//                              TOUT EST VERIFIER & MAINTENANT NE RESTE QUE L'AFFECTATION
                                newDocument.getListPret().add(pretDocument);
                                d.getListPret().remove(pretDocument);
                                newDocument.setNbPretTotale(newDocument.getNbPretTotale()+1);
                                d.setNbPretTotale(d.getNbPretTotale()-1);
                                historique.add(new Historique(new Date(), "Pret", "Modification", 0, "Document: Clients "+a.getPrenom()+" "+a.getNom()+" change le document"+d.getTitre()+" par "+newDocument.getTitre()));
                                d = newDocument;
                                System.out.println("\t Modification Réusite.");
                                break;
                            }
                            System.out.println("\t Ce nouveau document n'est plus disponible!");
                            break;
                        case "2":
                            Clients newClients;
                            System.out.println("\t Chercher le nouveau client");
                            newClients = chercherClientsById();
//                            TESTER SI ADHERENT RETARDATAIRE OU NE PEUX PLUS PRENDRE DE DOCUMENTS
                            if (!newClients.isRetard())
                                if (newClients.getNbMaxPrets()>newClients.getNbPret()){
                                    pretDocument.setClient(newClients);
                                    newClients.setNbPret(newClients.getNbPret()+1);
                                    a.setNbPret(a.getNbPret()-1);
                                    newClients.setNbPretTotale(newClients.getNbPretTotale()+1);
                                    a.setNbPretTotale(a.getNbPretTotale()-1);
                                    historique.add(new Historique(new Date(), "Pret", "Modification", 0, "Clients: "+a.getType()+": "+a.getPrenom()+" "+a.getNom()+" par "+newClients.getType()+": "+newClients.getPrenom()+" "+newClients.getNom()));
                                    a = newClients;
                                    System.out.println("\t Modification réussite.");
                                    break;
                                }
                            System.out.println("\t Cet Client ne peut pas empreinter un document!");
                            break;
                        case "3":
//                            MODIFICATION DE LA DATE DE PRET
                            String str, ancienneDate;
                            ancienneDate = sdf.format(pretDocument.getDateDuPret()) ;
                            System.out.println("\t Entrer la date de publication de l'article sous forme (DD/MM/YYYY)");
                            str = sc.nextLine();
                            try {
                                pretDocument.setDateDuPret(sdf.parse(str));
                                pretDocument.calculerDateRetour();
                                historique.add(new Historique(new Date(), "Pret", "Modification", 0, "Date: de "+ancienneDate+" a "+sdf.format(pretDocument.getDateDuPret())));
                                System.out.println("\t Modification réussite.");
                            }catch (ParseException e){
                                System.out.println("\t Forme non respecter! ");
                            }
                            break;
                        default:
                            System.out.println("\t Choix non valide veillez réessayer");
                            break;
                    }
                }while (!choix.equals("0"));
            }
        }
        System.out.println("\t Ce client na pas empreté ce document");
    }

    public void rendrePret(){
        System.out.println("\n\t *****\tRendre un document\t*****");
        System.out.println("\t Chercher le client qui doit rendre le document");
        Clients a;
        a = chercherClientsById();
        System.out.println("\t Chercher le document a rendre");
        Document d;
        d = chercherDocumentById2(a);
        for (PretDocument pretDocument:d.getListPret()){
            if (pretDocument.getClient() == a){
                historique.add(new Historique(new Date(), "Pret", "Suppression", a.getId(), "le client: "+a.getPrenom()+" "+a.getNom()+" a rendu le document: "+d.getTitre()));
                d.getListPret().remove(pretDocument);
                a.setNbPret(a.getNbPret()-1);
                System.out.println("\t Document rendu avec succées. Merci!");
                return;
            }
        }
        System.out.println("\t Ce client n'a pas empreter ce document");
    }

    public void toutHistorique(){
        System.out.println("\n\t ***\tHISTORIQUE\t*****");
        String choix;
        do {
            System.out.println("\t Veillez choisir le numero correspondant à votre choix");
            System.out.println("\t 0 ) Retour au menu principale");
            System.out.println("\t 1 ) Afficher tout l'historique disponible");
            System.out.println("\t 2 ) Afficher historique des clients");
            System.out.println("\t 3 ) Afficher historique des documents");
            System.out.println("\t 4 ) Afficher historique des prets");
            System.out.println("\t 5 ) Afficher historique des modifications");
            System.out.println("\t 6 ) Afficher historique des ajout");
            System.out.println("\t 7 ) Afficher historique des suppressions");
            System.out.println("\t 8 ) Afficher historique d'une date entrer");
            System.out.println("\t 9 ) Afficher historique par ID");
            choix = sc.nextLine();
            switch (choix){
                case "0": menu(); break;
                case "1": afficherHistorique(); break;
                case "2": afficherHistoriqueByClassName("Clients"); break;
                case "3": afficherHistoriqueByClassName("Document"); break;
                case "4": afficherHistoriqueByClassName("Pret"); break;
                case "5": afficherHistoriqueByType("Modification"); break;
                case "6": afficherHistoriqueByType("Ajout"); break;
                case "7": afficherHistoriqueByType("Suppression"); break;
                case "8": afficherHistoriqueByDate(); break;
                case "9": afficherHistoriqueByID(); break;
                default: System.out.println("\t Choix invalide! veillez réessayer"); break;
            }
        }while (!choix.equals("0"));
    }

    public void afficherHistorique(){
        int count =historique.size()-1;
        int i;
        System.out.println("\n\t *****\tHISTORIQUE DE LA BIBLIOTHEQUE\t*****\n");
        enteteHistorique();
        outerloop:
        while (count>=0) {
            i=0;
            while (i < 10) {
                if (count >= 0) {
                    historique.get(count).afficherCetHistorique();
                    i++;
                    count--;
                } else
                    break outerloop;
            }
            if (count >=0){
                System.out.println("\t entrer pour continuer, 0 pour sortire...");
                if (sc.nextLine().equals("0"))
                    return;
            }
        }
        System.out.println("\n\t Appuyer sur entrer pour retourner...");
        sc.nextLine();
    }

    public void afficherHistoriqueByClassName(String str){
        int count =historique.size()-1;
        int i;
        System.out.println("\n\t *****\tHISTORIQUE DE LA BIBLIOTHEQUE\t*****\n");
        enteteHistorique();
        outerloop:
        while (count>=0) {
            i=0;
            while (i < 10) {
                if (count >= 0) {
                    if (historique.get(count).getClasse().equals(str)){
                        historique.get(count).afficherCetHistorique();
                        i++;
                    }
                    count--;
                } else
                    break outerloop;
            }
            if (count >=0){
                System.out.println("\t entrer pour continuer, 0 pour sortire...");
                if (sc.nextLine().equals("0"))
                    return;
            }
        }
        System.out.println("\n\t Appuyer sur entrer pour retourner...");
        sc.nextLine();
    }

    public void afficherHistoriqueByType(String str){
        int count =historique.size()-1;
        int i=0;
        System.out.println("\n\t *****\tHISTORIQUE DE LA BIBLIOTHEQUE\t*****\n");
        enteteHistorique();
        outerloop:
        while (count>=0) {
            while (i < 10) {
                i=0;
                if (count >= 0) {
                    if (historique.get(count).getType().equals(str)){
                        historique.get(count).afficherCetHistorique();
                        i++;
                    }
                    count--;
                } else
                    break outerloop;
            }
            if (count >=0){
                System.out.println("\t entrer pour continuer, 0 pour sortire...");
                if (sc.nextLine().equals("0"))
                    return;
            }
        }
        System.out.println("\n\t Appuyer sur entrer pour retourner...");
        sc.nextLine();
    }

    public void afficherHistoriqueByDate(){
        SimpleDateFormat sdf= new SimpleDateFormat("dd/MM/yyyy");
        String str;
        int i;
        System.out.println("\t Entrer une date sous forme (dd/MM/yyyy) : ");
        str = sc.nextLine();
        try {
            sdf.parse(str);
        }catch (ParseException e){
            System.out.println("\t Forme non respecter...");
            return;
        }
        int count =historique.size()-1;
        System.out.println("\n\t *****\tHISTORIQUE DE LA BIBLIOTHEQUE\t*****\n");
        enteteHistorique();
        outerloop:
        while (count>=0) {
            i=0;
            while (i < 10) {
                if (count >= 0) {
                    if (sdf.format(historique.get(count).getDateAjout()).equals(str)){
                        historique.get(count).afficherCetHistorique();
                        i++;
                    }
                    count--;
                } else
                    break outerloop;
            }
            if (count >=0){
                System.out.println("\t entrer pour continuer, 0 pour sortire...");
                if (sc.nextLine().equals("0"))
                    return;
            }
        }
        System.out.println("\n\t Appuyer sur entrer pour retourner...");
        sc.nextLine();
    }

    public void afficherHistoriqueByID(){
        Clients a;
        a = chercherClientsById();
        int i;
        int count =historique.size()-1;
        System.out.println("\n\t *****\tHISTORIQUE DE LA BIBLIOTHEQUE\t*****\n");
        enteteHistorique();
        outerloop:
        while (count>=0) {
            i=0;
            while (i < 10) {
                if (count >= 0) {
                    if (historique.get(count).getId() == a.getId() && historique.get(count).getClasse().equals("Clients")){
                        historique.get(count).afficherCetHistorique();
                        i++;
                    }
                    count--;
                } else
                    break outerloop;
            }
            if (count >=0){
                System.out.println("\t entrer pour continuer, 0 pour sortire...");
                if (sc.nextLine().equals("0"))
                    return;
            }
        }
        System.out.println("\n\t Appuyer sur entrer pour retourner...");
        sc.nextLine();
    }

    public Clients chercherClientsById(){
        int id;
        String choix;
        Clients adherent = new Clients();
        do {
            System.out.println("\t 0 ) Annuler");
            System.out.println("\t 1 ) Lister les clients");
            System.out.println("\t 2 ) Entree ID client");
            System.out.println("\t Veillez choisir une option : ");
            choix = sc.nextLine();
            switch (choix){
                case "0": gestionClients(); break;
                case "1": afficherClients(); break;
                case "2":
                    System.out.println("\t Entrer l'ID du client : ");
                    id = verifierID();
                    sc.nextLine();
                    for (Clients a:listClients)
                        if (a.getId() == id)
                            return a;
                    System.out.println("\t Clients introuvable! veillez réssayer");
                    break;
                default:
                    System.out.println("\t Choix invalide! veillez réessayer...");
                    break;
            }
        }while (!choix.equals("0"));
        return adherent;
    }

    public void chercherClientsByIdOrNameOrAdresse(ArrayList<Clients> listClientsTrouve){
        String choix, nom, prenom, adresse;
        int id;
        do {
            System.out.println("\t 0 ) Annuler");
            System.out.println("\t 1 ) Chercher client par ID");
            System.out.println("\t 2 ) Chercher client par NOM");
            System.out.println("\t 3 ) Chercher client par PRENOM");
            System.out.println("\t 4 ) Chercher client par ADRESSE");
            System.out.println("\t Veillez choisir une option : ");
            choix = sc.nextLine();
            switch (choix){
                case "0": gestionClients(); return;
                case "1":
                    System.out.println("\t Entrer l'ID du client : ");
                    id = verifierID();
                    sc.nextLine();
                    for (Clients a:listClients)
                        if (a.getId() == id){
                            listClientsTrouve.add(a);
                            return;
                        }
                    System.out.println("\t Clients introuvable! veillez réssayer");
                    break;
                case "2":
                    System.out.println("\t Entrer NOM client");
                    nom = sc.nextLine();
                    for (Clients a:listClients)
                        if (a.getNom().equalsIgnoreCase(nom))
                            listClientsTrouve.add(a);
                    if (listClientsTrouve.size() == 0){
                        System.out.println("\t Il n'y a aucun client trouver avec ce nom...");
                        break;
                    }
                    return;
                case "3":
                    System.out.println("\t Entrer PRENOM client");
                    prenom = sc.nextLine();
                    for (Clients a:listClients)
                        if (a.getPrenom().equalsIgnoreCase(prenom))
                            listClientsTrouve.add(a);
                    if (listClientsTrouve.size() == 0){
                        System.out.println("\t Il n'y a aucun client trouver avec ce prenom...");
                        break;
                    }
                    return;
                case "4":
                    System.out.println("\t Entrer ADRESSE client");
                    adresse = sc.nextLine();
                    for (Clients a:listClients)
                        if (a.getAdresse().equalsIgnoreCase(adresse))
                            listClientsTrouve.add(a);
                    if (listClientsTrouve.size() == 0){
                        System.out.println("\t Il n'y a aucun client trouver avec cette adresse...");
                        break;
                    }
                    return;
                default:
                    System.out.println("\t Choix invalide! veillez réessayer...");
                    break;
            }
        }while (true);
    }

    public void chercherDocByIdOrTitre(ArrayList<Document> listDocumentTrouve){
        int id;
        String choix, titre;
        do {
            System.out.println("\t 0 ) Annuler");
            System.out.println("\t 1 ) Chercher Document par ID");
            System.out.println("\t 2 ) Chercher Document par Titre");
            System.out.println("\t Veillez choisir une option : ");
            choix = sc.nextLine();
            switch (choix){
                case "0": gestionDocument(); return;
                case "1":
                    System.out.println("\t Entrer ID : ");
                    id = verifierID();
                    sc.nextLine();
                    for (Document d:listDocument)
                        if (d.getId() == id){
                            listDocumentTrouve.add(d);
                            return;
                        }
                    System.out.println("\t Document introuvable! veillez réssayer");
                    break;
                case "2":
                    System.out.println("\t Entrer Titre : ");
                    titre = sc.nextLine();
                    for (Document d:listDocument)
                        if (d.getTitre().equalsIgnoreCase(titre))
                            listDocumentTrouve.add(d);
                    if (listDocumentTrouve.size() == 0){
                        System.out.println("\t Il n'y a aucun Document trouver avec ce titre...");
                        break;
                    }
                    return;
                default:
                    System.out.println("\t Choix invalide! veillez réessayer...");
                    break;
            }
        }while (true);
    }

    public Document chercherDocumentById(){
        int id;
        String choix;
        Document document = new Article();
        do {
            System.out.println("\t 0 ) Annuler");
            System.out.println("\t 1 ) Lister les documents disponible");
            System.out.println("\t 2 ) Entree ID document");
            System.out.println("\t Veillez choisir une option : ");
            choix = sc.nextLine();
            switch (choix){
                case "0": gestionDocument(); break;
                case "1": afficherDocument(); break;
                case "2":
                    System.out.println("\t Entrer l'ID du document : ");
                    id = verifierID();
                    sc.nextLine();
                    for (Document d:listDocument){
                        if (d.getId() == id)
                            return d;
                    }
                    System.out.println("\t Document introuvable! veillez réssayer");
                    break;
                default:
                    System.out.println("\t Choix invalide! veillez réessayer...");
                    break;
            }
        }while (!choix.equals("0"));
        return document;
    }

    public Document chercherDocumentById2(Clients a){
        int id, nb=0;
        String choix;
        Document document = new Article();
        boolean trouver = false;
        do {
            System.out.println("\t 0 ) Annuler");
            System.out.println("\t 1 ) Lister les documents disponible pour "+a.getPrenom()+" "+a.getNom());
            System.out.println("\t 2 ) Entree ID document");
            System.out.println("\t Veillez choisir une option : ");
            choix = sc.nextLine();
            switch (choix){
                case "0": gestionPret(); break;
                case "1":
                    enteteDocument();
                    for (Document d:listDocument)
                        for (PretDocument pretDocument:d.getListPret())
                            if (pretDocument.getClient() == a){
                                int disponible = d.getNbExemplaires()-d.getListPret().size();
                                d.afficherCeDocument(disponible, nombreDePretTotale);
                                cadreDocument();
                                trouver = true;
                                nb++;
                            }
                    if (!trouver)
                        System.out.println("\t Ce client n'a pas de document en cours de pret");
                    else
                        System.out.println("\t "+nb+" Document preté pour "+a.getPrenom()+" "+a.getNom());
                    break;
                case "2":
                    System.out.println("\t Entrer l'ID du document : ");
                    id = verifierID();
                    sc.nextLine();
                    for (Document d:listDocument){
                        if (d.getId() == id)
                            return d;
                    }
                    System.out.println("\t Document introuvable! veillez réssayer");
                    break;
                default:
                    System.out.println("\t Choix invalide! veillez réessayer...");
                    break;
            }
        }while (!choix.equals("0"));
        return document;
    }

    public int verifierID() {
        int id;
        do {
            try{
                id = sc.nextInt();
                return id;
            } catch (Exception e){
                System.out.println("\t Erreur! Veillez entrer des chiffres...");
                System.out.println("\t Entrer l'ID une autre fois : ");
                sc.nextLine();
            }
        }while (true);
    }

    public void remplissageDocument(Document d){
        d.setId(id_document);
        System.out.println("\t Entrer le titre");
        d.setTitre(sc.nextLine());
        System.out.println("\t Entrer localisation (Salle/Rayon)");
        d.setLocalisation(sc.nextLine());
        System.out.println("\t Entrer nombre exemplaires");
        boolean verifier=false;
        do {
            try{
                d.setNbExemplaires(sc.nextInt());
                verifier = true;
            } catch (Exception e){
                System.out.println("\t Erreur! Veillez entrer des chiffres...");
                System.out.println("\t Entrer nombre exemplaires");
                sc.nextLine();
            }
        }while (!verifier);
        sc.nextLine();
    }

    public void cadreClients(){
        System.out.print("+-----------");
        System.out.print("+----");
        for (int i=0; i<2; i++)
            System.out.print("+--------------------");
        System.out.print("+------------------------------");
        for (int i=0; i<2; i++)
            System.out.print("+--------");
        System.out.print("+-------------");
        System.out.println("+");
    }

    public void enteteClients(){
        cadreClients();
        System.out.println("|   "+ Y +"TYPE"+ RES +"    | "+ Y +"ID"+ RES +
                " |        "+ Y +"NOM"+ RES +"         |       "+ Y +"PRENOM"+ RES +
                "       |           "+ Y +"ADRESSE"+ RES +"            |" +
                " "+ Y +"PRETS"+ RES +"  | "+ Y +"RETARD"+ RES +" | "+ Y +"PRET TOTALE"+ RES +" |");
        cadreClients();
    }

    public void cadreDocument(){
        System.out.print("+---------");
        System.out.print("+----");
        System.out.print("+------------------------------------");
        for (int i=0; i<2; i++)
            System.out.print("+--------------------");
        System.out.print("+----------");
        for (int i=0; i<4; i++)
            System.out.print("+------------");
        System.out.print("+-------------");
        System.out.println("+");
    }

    public void enteteDocument(){
        cadreDocument();
        System.out.println("|  "+ Y +"TYPE"+ RES +"   | "+ Y +"ID"+ RES +" |               "+ Y +
                "TITRE"+ RES +"                |     "+ Y +"Nom Auteur"+ RES +"     |    "+ Y +"NOM EDITEUR"+ RES +
                "     |   "+ Y +"DATE"+ RES +"   |  "+ Y +"FREQUENCE"+ RES +" |"+ Y +"LOCALISATION"+ RES +
                "| "+ Y +"EXEMPLAIRES"+ RES +"| "+ Y +"DISPONIBLE"+ RES +" | "+ Y +"PRET TOTALE"+ RES +" |  ");
        cadreDocument();
    }

    public void cadrePret(){
        System.out.print("+---------");
        for (int i=0; i<2; i++)
            System.out.print("+----------------------------------------");
        for (int i=0; i<3; i++)
            System.out.print("+-------------");
        System.out.println("+");
    }

    public void entetePret(){
        cadrePret();
        System.out.println("| "+Y+"TYPE DOC"+RES+"|                 "+Y+"TITRE"+RES+"                  |                "+Y+
                "CLIENT"+RES+"                |   "+Y+"TYPE AD"+RES+"   |    "+Y+"PRIS LE"+RES+
                "  | "+Y+"A RENDRE LE"+RES+" |");
        cadrePret();
    }

    public void enteteHistorique(){
        System.out.println("+-------------------------+--------+------+---------------+--------------------------------------------------------------------------------+");
        System.out.println("|          "+Y+"DATE"+RES+"           |  "+Y+"CLASS"+RES+" |  "+Y+"ID"+RES+"  |      "+Y+
                "TYPE"+RES+"     |                                    "+Y+"INFO"+RES+"                                        |");
        System.out.println("+-------------------------+--------+------+---------------+--------------------------------------------------------------------------------+");
    }

    public void enregistrement(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//      AJOUT AUTOMATIQUE DE 5 CLIENTS
        Clients c1 = new Clients(1, "Monsan", "Junior", "Cocody", "Etudiant", 2, 7);
        Clients c2 = new Clients(2, "Kreme", "Princek", "Beaulieu", "Visiteur", 1, 7);
        Clients c3 = new Clients(3, "Akessi", "Wily", "Rivera", "Enseignant", 4, 21);
        Clients c4 = new Clients(4, "Toure", "Yann", "Yopougon", "Etudiant", 2, 7);
        Clients c5 = new Clients(5, "Kouadio", "Marie", "Port Bouet", "Etudiant", 2, 7);
        listClients.add(c1);
        listClients.add(c2);
        listClients.add(c3);
        listClients.add(c4);
        listClients.add(c5);
//      AJOUT AUTOMATIQUE DE 5 DOCUMENTS
        Document d1 = new Article(1, "Kirikou", "S1/F100", 3, "Karaba", new Date());
        Document d2 = new Article(2, "Mougli", "S2/F321", 1, "Jungle", new Date());
        Document d3 = new Livre(3, "Harry Potter", "S3/L112", 2, "Louis", "Vallesse", new Date());
        Document d4 = new Livre(4, "Aya de Yopougonx", "S1/L538", 4, "Yann", "Vallesse", new Date());
        Document d5 = new Magazine(5, "Fashion Style", "S2/M019", 1, 500);
        listDocument.add(d1);
        listDocument.add(d2);
        listDocument.add(d3);
        listDocument.add(d4);
        listDocument.add(d5);
//        AJOUT DE PRETS EN RETARD D'ARTICLE 1 AU 5EME CLIENT
        PretDocument pretDocument = new PretDocument();
        pretDocument.setClient(c5);
        try{
            pretDocument.setDateDuPret(sdf.parse("10/10/2023"));
        }catch (ParseException e){
            System.out.println("rien");
        }
        pretDocument.calculerDateRetour();
        d1.getListPret().add(pretDocument);
        c5.setNbPret(1);
        c5.setNbPretTotale(1);
        nombreDePretTotale++;
        d1.setNbPretTotale(1);

//        ON AJOUT UN PREDOC A CLIENT 1 ET DOC 3
        PretDocument pretDocument2 = new PretDocument();
        pretDocument2.setClient(c1);
        try{
            pretDocument2.setDateDuPret(sdf.parse("15/10/2023"));
        }catch (ParseException e){
            System.out.println("rien");
        }
        pretDocument2.calculerDateRetour();
        d3.getListPret().add(pretDocument2);
        c1.setNbPret(1);
        c1.setNbPretTotale(1);
        d3.setNbPretTotale(1);
        nombreDePretTotale++;
    }
}
