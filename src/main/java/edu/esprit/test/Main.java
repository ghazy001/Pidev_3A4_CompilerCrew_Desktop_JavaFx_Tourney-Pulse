package edu.esprit.test;

import edu.esprit.entities.matchs;
import edu.esprit.entities.tournois;
import edu.esprit.services.ServiceMatch;
import edu.esprit.services.ServiceTournois;
import edu.esprit.utils.DataSource;


public class Main {
    public static void main(String[] args) {

        DataSource.getInsatnce().getConnection();

        System.out.println("*********** Les Tournois ******************");

        tournois t = new tournois("La Ligua","Tunis Rue Habib Borguiba", 16, "20/05/2023", "05/06/2023");
        ServiceTournois servicee = new ServiceTournois();
        servicee.ajouter(t);
        servicee.supprimer(39);
        tournois t1 = new tournois("Champions","Ariana", 20, "10/05/2024", "05/06/2023");
        servicee.modifier(34,t1);
        servicee.afficher();

        System.out.println("*********** Les Matchs ******************");

        tournois tournoisForMatch = new tournois();
        tournoisForMatch.setId_tournois(32);
        matchs m = new matchs("Demi Final","25/05/2023","90 min",tournoisForMatch);
        ServiceMatch service = new ServiceMatch();
        service.ajouter(m);
        service.supprimer(38);
        tournois tournoisForMatch1 = new tournois();
        tournoisForMatch1.setId_tournois(32);
        matchs m1 = new matchs("Final","11/05/2023","90 min",tournoisForMatch1);
        service.modifier(31,m1);
        service.afficher();

    }
}