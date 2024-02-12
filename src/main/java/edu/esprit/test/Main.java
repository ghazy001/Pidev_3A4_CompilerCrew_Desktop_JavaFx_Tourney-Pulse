package edu.esprit.test;

import edu.esprit.entities.matchs;
import edu.esprit.entities.tournois;
import edu.esprit.services.ServiceMatch;
import edu.esprit.services.ServiceTournois;
import edu.esprit.utils.DataSource;


public class Main {
    public static void main(String[] args) {

        DataSource.getInsatnce().getConnection();

        tournois t = new tournois("La Ligua","Tunis Rue Habib Borguiba", 16, "20/05/2023", "05/06/2023");
        ServiceTournois servicee = new ServiceTournois();
        servicee.ajouter(t);
        servicee.supprimer(13);
        tournois t1 = new tournois("Champions","Ariana", 20, "10/05/2024", "05/06/2023");
        servicee.modifier(12,t1);
        servicee.afficher();

        matchs m = new matchs("25/05/2023","90 min");
        ServiceMatch service = new ServiceMatch();
        service.ajouter(m);
        service.supprimer(12);
        matchs m1 = new matchs("11/05/2023","90 min");
        service.modifier(13,m1);
        service.afficher();

    }
}