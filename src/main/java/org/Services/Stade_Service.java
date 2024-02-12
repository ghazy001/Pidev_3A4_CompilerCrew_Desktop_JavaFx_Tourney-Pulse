package org.Services;

import org.Entities.Stade;
import org.Entities.images_stade;
import org.Utils.DbConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Stade_Service {
    private Connection cnx;
    private PreparedStatement ste;

    public Stade_Service() {
        cnx = DbConnector.getInstance().getCon();
    }
    public void createStade(Stade E) {
        try {
            String req = "INSERT INTO stade (`Nom`,`Lieu`,`Capacity`,`Numero`) VALUES (?,?,?,?)";
            PreparedStatement st = cnx.prepareStatement(req);

            st.setString(1, E.getNom());
            st.setString(2, E.getLieu());
            st.setInt(3, E.getCapacity());
            st.setInt(4, E.getNumero());


            st.executeUpdate();
            System.out.println("stade ajoutée avec succes.");

        } catch (SQLException ex) {

            ex.printStackTrace();
        }

    }


    public void modifyStade(Stade E) {
        String sql = "UPDATE stade SET `Nom`=?,`Lieu`=?,`Capacity`=?,`Numero`=? where id=?";
        try {
            PreparedStatement st = cnx.prepareStatement(sql);

            st.setString(1, E.getNom());
            st.setString(2, E.getLieu());
            st.setInt(3,E.getCapacity());
            st.setInt(4,E.getNumero());
            st.setInt(5, E.getId());
            System.out.println(st.toString());
            st.executeUpdate();
            System.out.println("modification avec succees !");
            System.out.println(E);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void deleteStade(Stade e) {
        try {

            if (e.getId() != 0) {
                String sql = "delete from reservation WHERE id_stade=?";
                String sql0 = "delete from stade WHERE id=?";
                PreparedStatement st = cnx.prepareStatement(sql);
                st.setInt(1, e.getId());
                st.executeUpdate();
                PreparedStatement st1 = cnx.prepareStatement(sql0);
                st1.setInt(1, e.getId());
                st1.executeUpdate();
                System.out.println("deleted !");
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }



    public List<Stade> GetStade() {
        ArrayList<Stade> stades = new ArrayList();
        String req = "SELECT * FROM stade";

        try {
            Statement st;

            st= DbConnector.getInstance().getCon().prepareStatement(req);
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                Stade e= new Stade(rs.getInt(1),rs.getInt(4),rs.getInt(5), rs.getString(2),
                        rs.getString(3));
                stades.add(e
                );

            }


        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return stades;
    }


    public void addImage(images_stade E) {
        try {
            String req = "INSERT INTO images_stade (`id_stade`,`url_image`) VALUES (?,?)";
            PreparedStatement st = cnx.prepareStatement(req);

            st.setInt(1, E.getId_stade());
            st.setString(2, E.getUrl_image());


            st.executeUpdate();

        } catch (SQLException ex) {

            ex.printStackTrace();
        }

    }

    public void deleteImage(images_stade e) {
        try {

                String sql = "delete from images_stade WHERE id=?";
                PreparedStatement st = cnx.prepareStatement(sql);
                st.setInt(1, e.getId());
                st.executeUpdate();


        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }


}
