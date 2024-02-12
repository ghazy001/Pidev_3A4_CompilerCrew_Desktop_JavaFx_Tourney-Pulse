package org.example;

import org.Entities.Reservation;
import org.Entities.Stade;
import org.Entities.images_stade;
import org.Services.Reservation_Service;
import org.Services.Stade_Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {

        images_stade img = new images_stade(1,"url_image");
        images_stade img2 = new images_stade(1,"url_image_test");

        Stade_Service ss = new Stade_Service();
        Stade s = new Stade(24,23702602,"Manouba","Tunis");
      //  ss.createStade(s);
      //  ss.addImage(img);
     //   ss.addImage(img2);

      //  s.setNom("Aouina");
      //  s.setId(1);
      //  ss.modifyStade(s);
s.setId(5);
//ss.deleteStade(s);

        Stade s1= new Stade(15,27802602,"Benzaret","Kef");
       // ss.createStade(s1);
        List<Stade> lst = ss.GetStade();
        for(int i=0; i<lst.size();i++){
            System.out.println(lst.get(i).toString());
        }

        Reservation_Service raar = new Reservation_Service();

        Reservation r = new Reservation(1,22,23,55, Date.valueOf(LocalDate.now()));

        Reservation r1 = new Reservation(2,23,55,66, Date.valueOf(LocalDate.now()));

       // raar.createReservation(r);
        //r.setId_PremiereEquipe(12);
        r.setId(12);
        raar.delete_reservation(r);
        //raar.createReservation(r1);
        List<Reservation> lste = raar.GetResevation();
        for(int i=0; i<lste.size();i++){
            System.out.println(lste.get(i).toString());
        }


    }


}