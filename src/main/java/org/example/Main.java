package org.example;

import controller.ServiceAvisJoueur;
import controller.ServiceEquipe;
import entities.AvisJoueur;
import entities.Equipe;
import entities.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class Main {
    public static void main(String[] args) throws SQLException {
        ServiceAvisJoueur serviceAvis= ServiceAvisJoueur.getInstance();
        ServiceEquipe serviceEquipe= ServiceEquipe.getInstance();




            AvisJoueur nouvelAvis = new AvisJoueur();
             Equipe  equipe = new Equipe();
              User user = new User();



/*
//-------------------------------------------Test Ajouter Avis----------------------------------------------------------------
           nouvelAvis.setCommentaire("Madrid Rojla " );
        nouvelAvis.setNote(3.9f);
           user.setId(1);
           try {

               serviceAvis.ajouter(nouvelAvis,user);

                System.out.println("Avis ajouté avec succès ! L'ID généré est : " + nouvelAvis.getIdAvis());

          System.out.println("/////////////////////////////////////////////////////////////////////////////////////////\n");

          System.out.println("Liste des avis pour le restaurant " + user.getId() + ":");
           } catch (SQLException e) {
               System.err.println("Erreur lors de l'ajout de l'avis : " + e.getMessage());
          }


      }*/
        ////////////////////////////////////////


//-------------------------------------------Test supprimer Avis----------------------------------------------------------------


        //serviceAvis.supprimer(1);
        //
        //
        //


        // ////////////

        //-------------------------------------------Test Modifier Avis----------------------------------------------------------------

       /* AvisJoueur avisModifie = new AvisJoueur();
        avisModifie.setCommentaire("Modified Comment");
        avisModifie.setNote(4.5f);
        serviceAvis.modifier(80, avisModifie, 5);*/
        //-------------------------------------------Test recuperer Avis----------------------------------------------------------------
   /*     try {



            List<AvisJoueur> avisList = serviceAvis.recuperer();
            System.out.println("Tous Les Avis:");
            displayAvisJoueurList(avisList);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void displayAvisJoueurList(List<AvisJoueur> avisList) {
        for (AvisJoueur avis : avisList) {
            System.out.println("Avis ID: " + avis.getIdAvis());
            System.out.println("Commentaire: " + avis.getCommentaire());
            System.out.println("Note: " + avis.getNote());
            System.out.println("User ID: " + avis.getUser().getId());
            System.out.println("User Name: " + avis.getUser().getName());
            System.out.println("----------§§§§§§§§§§§§§§§§§");
        }*/




        //-------------------------------------------Test Ajouter Equipe----------------------------------------------------------------

        equipe.setNom("EspritUnited" );
        equipe.setImage("PNG");
        user.setId(1);
        try {

            serviceEquipe.ajouter(equipe,user);

            System.out.println("Equipe ajouté avec succès ! L'ID généré est : " + equipe.getId());

            System.out.println("/////////////////////////////////////////////////////////////////////////////////////////\n");

            System.out.println(" equipes pour le user " + user.getId() + ":");
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout d'un equipe : " + e.getMessage());
        }


        //-------------------------------------------Test Modifier Equipe----------------------------------------------------------------

        /*equipe.setNom("Hala Comment");
        equipe.setImage("Image");
        serviceEquipe.modifier(144,equipe, 1);

*/
        //-------------------------------------------Test supprimer Avis----------------------------------------------------------------


        //serviceEquipe.supprimer(143);
        //-------------------------------------------Test recuperer Avis----------------------------------------------------------------

     /*
      try {



            List<Equipe> avisList = serviceEquipe.recuperer();
            System.out.println("Tous Les Equipe:");
            displayAvisJoueurList(avisList);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void displayAvisJoueurList(List<Equipe> avisList) {
        for (Equipe avis : avisList) {
            System.out.println("Equipe ID: " + avis.getId());
            System.out.println("Image Logo: " + avis.getImage());
            System.out.println("User ID: " + avis.getUser().getId());
            System.out.println("User Name: " + avis.getUser().getName());
            System.out.println("----------§§§§§§§§§§§§§§§§§");
        }*/


    }



    }