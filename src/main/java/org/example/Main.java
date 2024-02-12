package org.example;

import controller.ServiceAvisJoueur;
import controller.ServiceEquipe;
import entities.AvisJoueur;
import entities.equipe;
import esprit.project.tools.MyDB;


public class Main {
    public static void main(String[] args) {

        MyDB.getInsatnce().getConnection();

        //------------ Les equipes --------------------

        System.out.println("---------- Les equipes -------------");
        equipe e = new equipe("real","lionel");
        equipe e3 = new equipe("real","narimen");
        ServiceEquipe services = new ServiceEquipe();
        services.ajouter(e3);
        services.ajouter(e);
        services.afficher();

        //  services.supprimer(5);
        // equipe e2 = new equipe("barcalona","lionel");
        //services.modifier(9,e2);
        //services.afficher();



        // ---------------- Avis Joueur --------------------

        System.out.println("---------- Les Avis sur les joueurs -------------");
        AvisJoueur avisJoueur = new AvisJoueur(2,"goodplayer",7);
        AvisJoueur avisJoueur1 = new AvisJoueur(8,"goodplayer",7);
        AvisJoueur LMBADEL = new AvisJoueur(2,"WELDEK MUSH TAA KOORA YE MADAME !",7);

        ServiceAvisJoueur serviceAvisJoueur;
        serviceAvisJoueur = new ServiceAvisJoueur();
        serviceAvisJoueur.ajouter(avisJoueur1);
        serviceAvisJoueur.afficher();
        serviceAvisJoueur.supprimer(3);

        serviceAvisJoueur.modifier(38,LMBADEL);

        serviceAvisJoueur.afficher();








    }
}