package services;

import entities.Reservation;
import entities.Stade;
import utiles.DbConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Reservation_Service {
    private Connection cnx;
    private PreparedStatement ste;

    public Reservation_Service() {
        cnx = DbConnector.getInstance().getCon();
    }
    public void createReservation(Reservation R) {
        try {
            String req = "INSERT INTO reservation (`id_stade`,`id_PremiereEquipe`,`id_DeuxiemeEquipe`,`date`,`id_organisateur`) VALUES (?,?,?,?,?)";
            PreparedStatement st = cnx.prepareStatement(req);

            st.setInt(1, R.getId_stade());
            st.setInt(2, R.getId_PremiereEquipe());
            st.setInt(3, R.getId_DeuxiemeEquipe());
            st.setDate(4, R.getDate());
            st.setInt(5, R.getId_organisateur());


            st.executeUpdate();
            System.out.println("votre reservation est ajoutée avec succes.");

        } catch (SQLException ex) {

            ex.printStackTrace();
        }

    }


    public void modify_reservation(Reservation R) {
        String sql = "UPDATE reservation SET `id_stade`=?,`id_PremiereEquipe`=?,`id_DeuxiemeEquipe`=?,`date`=? ,`id_organisateur`=? where id=?";
        try {
            PreparedStatement st = cnx.prepareStatement(sql);

            st.setInt(1, R.getId_stade());
            st.setInt(2, R.getId_PremiereEquipe());
            st.setInt(3,R.getId_DeuxiemeEquipe());
            st.setDate(4,R.getDate());
            st.setInt(5, R.getId_organisateur());
            st.setInt(6, R.getId());
            System.out.println(st.toString());
            st.executeUpdate();
            System.out.println("modification avec succees !");
            System.out.println(R);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void delete_reservation(Reservation r) {
        try {

            if (r.getId() != 0) {
                String sql = "delete from reservation WHERE id=?";

                PreparedStatement st = cnx.prepareStatement(sql);
                st.setInt(1, r.getId());
                st.executeUpdate();

                System.out.println("deleted !");
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }



    public List<Reservation> GetResevation() {
        ArrayList<Reservation> reservations = new ArrayList();
        Stade_Service ss = new Stade_Service();
        String req = "SELECT * FROM reservation";

        try {
            Statement st;

            st= DbConnector.getInstance().getCon().prepareStatement(req);
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                Reservation e= new Reservation(rs.getInt(1),rs.getInt(2),rs.getInt(3), rs.getInt(4),rs.getInt(6),
                        rs.getDate(5));
                e.setNomStade(ss.GetStadeById(e.getId_stade()).getNom());
                reservations.add(e
                );

            }


        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return reservations;
    }



}
