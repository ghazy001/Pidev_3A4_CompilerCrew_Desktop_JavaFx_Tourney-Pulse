package org.example;

import controller.ServiceAvisJoueur;
import controller.ServiceEquipe;
import entities.AvisJoueur;
import entities.equipe;
import esprit.project.tools.MyDB;


public class Main {
    public static void main(String[] args) {

       // MyDB.getInsatnce().getConnection();

        //------------ Les equipes --------------------

              equipe e = new equipe("real",6);
             equipe e1 = new equipe("barca",8);
              ServiceEquipe serviceEquipe = new ServiceEquipe();
              serviceEquipe.ajouter(e);
              serviceEquipe.modifier(132,e1);
              serviceEquipe.supprimer(132);
              serviceEquipe.afficher();

        // ---------------- Avis Joueur --------------------
/*
        System.out.println("---------- Les Avis sur les joueurs -------------");
        AvisJoueur avisJoueur = new AvisJoueur(2,"goodplayer",7);
        AvisJoueur review = new AvisJoueur(6,"goodplayer",7);
        AvisJoueur avisJoueur1 = new AvisJoueur(8,"goodplayer",7);
        AvisJoueur LMBADEL = new AvisJoueur(2,"WELDEK MUSH TAA KOORA YE MADAME !",2);
        AvisJoueur LMBADEL2 = new AvisJoueur(6,"WELDEK MUSH TAA KOORA YE MADAME !",8);
        AvisJoueur var = new AvisJoueur(2,"goodplayer",10);
        AvisJoueur newavis = new AvisJoueur(6,"wuv_u",3);

        ServiceAvisJoueur serviceAvisJoueur;
        serviceAvisJoueur = new ServiceAvisJoueur();
        serviceAvisJoueur.ajouter(avisJoueur1);
        serviceAvisJoueur.ajouter(review);
        serviceAvisJoueur.afficher();
        serviceAvisJoueur.supprimer(3);
        serviceAvisJoueur.modifier(51,LMBADEL);
        serviceAvisJoueur.supprimer(53);
        serviceAvisJoueur.ajouter(var);
        serviceAvisJoueur.ajouter(newavis);
        serviceAvisJoueur.afficher();


 */








    }
}