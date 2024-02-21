package edu.esprit.example;

import edu.esprit.Services.ServiceMessagerie;
import edu.esprit.Services.ServiceReclamation;
import edu.esprit.Utils.DataSource;
import edu.esprit.entities.Messagerie;
import edu.esprit.entities.Reclamation;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        DataSource.getInstance().getCnx();


        System.out.println("---------- reclamation -------------");


        ServiceReclamation service = new ServiceReclamation();
/*
        Reclamation r = new Reclamation("iheb", "probleme", "jj", new Date(System.currentTimeMillis()) ,100);
        Reclamation r1 = new Reclamation("saami", "probleme2", "jjll", new Date(System.currentTimeMillis()), 101);
        Reclamation r2 = new Reclamation("hama", "prob", "reclamation2", new Date(System.currentTimeMillis()), 40);

          service.ajouter(r);
          //service.ajouter(r1);
//service.supprimer(4);
//service.afficher();
          //service.modifier(2,r2);

 */

/*
    ServiceMessagerie service1 = new ServiceMessagerie();

            Messagerie m = new Messagerie("hello",Date.valueOf(LocalDate.now()));
        service1.ajouter(m);


*//*
        System.out.println("---------- mesagerie -------------");

   Messagerie m = new Messagerie(3,"helloooooo", new Date(System.currentTimeMillis()));
        Messagerie m2 = new Messagerie(3,"good ", new Date(System.currentTimeMillis()));

    ServiceMessagerie service2 = new ServiceMessagerie();
   service2.ajouter(m2);
       // service2.modifier(3,m);
//service2.afficher();
      //  service2.supprimer(4);

    */


        //-------------------------------------------
        // Assuming you have imported the necessary packages


            List<Reclamation> reclamationList = service.getAll();



                System.out.println("List of Reclamations:");
                for (Reclamation reclamation : reclamationList) {
                    System.out.println("ID: " + reclamation.getId());
                    System.out.println("User ID: " + reclamation.getId());
                    System.out.println("Email: " + reclamation.getEmail());
                    System.out.println("Object: " + reclamation.getObject());
                    System.out.println("Reclamation: " + reclamation.getRec());
                    System.out.println("Date: " + reclamation.getDate_rec());
                    System.out.println("----------------------------------------");
                }



    }
}



