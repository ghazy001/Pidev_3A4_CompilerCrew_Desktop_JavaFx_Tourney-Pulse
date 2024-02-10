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
        equipe e3 = new equipe("real","narime");
        ServiceEquipe services = new ServiceEquipe();
        services.ajouter(e3);
        services.afficher();

      //  services.supprimer(5);
       // equipe e2 = new equipe("barcalona","lionel");
        //services.modifier(9,e2);
       //services.afficher();



        // ---------------- Avis Joueur --------------------

        System.out.println("---------- Les Avis sur les joueurs -------------");
        AvisJoueur avisJoueur = new AvisJoueur("good player",8,"lionel");
        ServiceAvisJoueur serviceAvisJoueur;
        serviceAvisJoueur = new ServiceAvisJoueur();
        serviceAvisJoueur.ajouter(avisJoueur);
        serviceAvisJoueur.afficher();
        serviceAvisJoueur.supprimer(3);

        // Cristiano
        AvisJoueur avisJoueur1 =new AvisJoueur("GOAT",9,"Cristiano");
        serviceAvisJoueur.modifier(4,avisJoueur1);
        //
        serviceAvisJoueur.afficher();








        }
    }
