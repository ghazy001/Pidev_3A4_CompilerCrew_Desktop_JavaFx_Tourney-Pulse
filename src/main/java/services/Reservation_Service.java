package services;

import entities.Reservation;
import interfaces.IService;
import utiles.DbConnector;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Reservation_Service implements IService<Reservation> {
    private Connection cnx;
    private PreparedStatement ste;

    public Reservation_Service() {
        cnx = DbConnector.getInstance().getCon();
    }












    public List<LocalDate> getReservedDaysByStade(int idStade){
        ArrayList<LocalDate> reservedDate = new ArrayList();
        Stade_Service ss = new Stade_Service();
        String req = "SELECT * FROM reservation where id_stade="+idStade+"";

        try {
            Statement st;

            st= DbConnector.getInstance().getCon().prepareStatement(req);
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                Reservation e= new Reservation(rs.getInt(1),rs.getInt(2),rs.getInt(3), rs.getInt(4),rs.getInt(6),
                        rs.getDate(5));
                reservedDate.add(e.getDate().toLocalDate());


            }


        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return reservedDate;
    }


    @Override
    public void Add(Reservation item) {
        try {
            String req = "INSERT INTO reservation (`id_stade`,`id_PremiereEquipe`,`id_DeuxiemeEquipe`,`date`,`id_organisateur`) VALUES (?,?,?,?,?)";
            PreparedStatement st = cnx.prepareStatement(req);

            st.setInt(1, item.getId_stade());
            st.setInt(2, item.getId_PremiereEquipe());
            st.setInt(3, item.getId_DeuxiemeEquipe());
            st.setDate(4, item.getDate());
            st.setInt(5, item.getId_organisateur());


            st.executeUpdate();
            System.out.println("votre reservation est ajoutée avec succes.");

        } catch (SQLException ex) {

            ex.printStackTrace();
        }

    }

    @Override
    public void Update(Reservation item) {
        String sql = "UPDATE reservation SET `id_stade`=?,`id_PremiereEquipe`=?,`id_DeuxiemeEquipe`=?,`date`=? ,`id_organisateur`=? where id=?";
        try {
            PreparedStatement st = cnx.prepareStatement(sql);

            st.setInt(1, item.getId_stade());
            st.setInt(2, item.getId_PremiereEquipe());
            st.setInt(3,item.getId_DeuxiemeEquipe());
            st.setDate(4,item.getDate());
            st.setInt(5, item.getId_organisateur());
            st.setInt(6, item.getId());
            System.out.println(st.toString());
            st.executeUpdate();
            System.out.println("modification avec succees !");
            System.out.println(item);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void delete(Reservation item) {
        try {

            if (item.getId() != 0) {
                String sql = "delete from reservation WHERE id=?";

                PreparedStatement st = cnx.prepareStatement(sql);
                st.setInt(1, item.getId());
                st.executeUpdate();

                System.out.println("deleted !");
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<Reservation> Get() {
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
