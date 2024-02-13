package edu.esprit.test;

import edu.esprit.entities.matchs;
import edu.esprit.entities.tournois;
import edu.esprit.services.ServiceMatch;
import edu.esprit.services.ServiceTournois;
import edu.esprit.utils.DataSource;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;



public class Main {
    public static void main(String[] args) throws ParseException {

        DataSource.getInsatnce().getConnection();

        System.out.println("*********** Les Tournois ******************");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dateD = sdf.parse("2024-05-11");
        java.sql.Date sqlDateD = new java.sql.Date(dateD.getTime());

        Date dateF = sdf.parse("2024-05-20");
        java.sql.Date sqlDateF = new java.sql.Date(dateF.getTime());

        tournois t = new tournois("La Ligua","Tunis Rue Habib Borguiba", 16, sqlDateD, sqlDateF);
        ServiceTournois servicee = new ServiceTournois();

        servicee.ajouter(t);
        servicee.supprimer(49);

        Date dateD1 = sdf.parse("2024-05-30");
        java.sql.Date sqlDateD1 = new java.sql.Date(dateD1.getTime());

        Date dateF1 = sdf.parse("2024-05-20");
        java.sql.Date sqlDateF1 = new java.sql.Date(dateF1.getTime());

        tournois t1 = new tournois("Champions","Ariana", 20, sqlDateD1, sqlDateF1);
        servicee.modifier(48,t1);
        servicee.afficher();

        System.out.println("*********** Les Matchs ******************");

        Date dateM = sdf.parse("2024-10-10");
        java.sql.Date sqlDateM = new java.sql.Date(dateM.getTime());

        tournois tournoisForMatch = new tournois();
        tournoisForMatch.setId_tournois(47);

        matchs m = new matchs("Demi Final",sqlDateM,"90 min",tournoisForMatch);
        ServiceMatch service = new ServiceMatch();

        service.ajouter(m);
        service.supprimer(46);

        Date dateM1 = sdf.parse("2024-08-03");
        java.sql.Date sqlDateM1 = new java.sql.Date(dateM1.getTime());

        tournois tournoisForMatch1 = new tournois();
        tournoisForMatch1.setId_tournois(44);

        matchs m1 = new matchs("Final",sqlDateM1,"90 min",tournoisForMatch1);
        service.modifier(47,m1);
        service.afficher();

    }
}