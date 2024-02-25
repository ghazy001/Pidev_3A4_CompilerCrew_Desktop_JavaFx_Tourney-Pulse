package org.example;

import service.ServiceAvisJoueur;
import service.ServiceEquipe;
import entities.AvisJoueur;
import entities.Equipe;
import entities.User;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class Main {
    public static void main(String[] args) throws SQLException, ParseException {
        ServiceAvisJoueur serviceAvis = ServiceAvisJoueur.getInstance();
        ServiceEquipe serviceEquipe = ServiceEquipe.getInstance();
//-------------------------------------------Test Ajouter Avis----------------------------------------------------------------



            AvisJoueur nouvelAvis = new AvisJoueur();
        Equipe  equipe = new Equipe();
            User user = new User();
/*
          nouvelAvis.setDateAvis(LocalDate.now());

           nouvelAvis.setCommentaire("Madrid Rojla " );
        nouvelAvis.setNote(3.4f);
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
     /*  try {



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
            System.out.println("date: " + avis.getDateAvis());
            System.out.println("User ID: " + avis.getUser().getId());
            System.out.println("User Name: " + avis.getUser().getName());

            System.out.println("----------§§§§§§§§§§§§§§§§§");
        }

*/
        //-------------------------------------------Test Ajouter Equipe----------------------------------------------------------------
/*
        equipe.setNom("Madrid Rojla " );
        equipe.setImage("fffff");
        user.setId(1);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date testDate = dateFormat.parse("2003/12/03");
        equipe.setDateCreation(testDate);
        try {

            serviceEquipe.ajouter(equipe);

            System.out.println("Equipe ajouté avec succès ! L'ID généré est : " + equipe.getId());

            System.out.println("/////////////////////////////////////////////////////////////////////////////////////////\n");

            System.out.println(" equipes pour le user " + user.getId() + ":");
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout d'un equipe : " + e.getMessage());
        }

*/
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



            List<Equipe> equipeList = serviceEquipe.recuperer();
            System.out.println("Tous Les Equipe:");
            for(Equipe e:equipeList)
            {
                System.out.println("******************************");

                System.out.println("Equipe ID: " + e.getId());
                System.out.println("Image Logo: " + e.getImage());
                System.out.println("date avis: " + e.getDateCreation());
                if(e.getJouers().size()>0) {
                    System.out.println("*****************Liste Des Joueurs *************");
                    for (User jouer : e.getJouers()) {

                        System.out.println("joueur ID: " + jouer.getName());

                    }
                }
                System.out.println("******************************");



            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void displayAvisJoueurList(List<Equipe> avisList) {
        for (Equipe avis : avisList) {
            System.out.println("Equipe ID: " + avis.getId());
            System.out.println("Image Logo: " + avis.getImage());
            System.out.println("date avis: " + avis.getDateCreation());



            System.out.println("----------§§§§§§§§§§§§§§§§§");
        }


 */

//--------------

        //equipe.setNom("Madrid Rojla " );
        //equipe.setImage("fffff");
        equipe.setId(165);
        user.setId(7);
       // SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
       // Date testDate = dateFormat.parse("2003/12/03");
        //equipe.setDateCreation(testDate);
        serviceEquipe.affecter(equipe,user);




    }
}
