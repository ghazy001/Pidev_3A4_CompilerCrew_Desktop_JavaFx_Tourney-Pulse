package edu.esprit.Services;

import edu.esprit.Utils.DataSource;
import edu.esprit.entities.Messages;
import edu.esprit.entities.Reclamation;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ServiceReclamation implements IService<Reclamation> {

    PreparedStatement prepare;
    Connection connection;
    Connection cnx = DataSource.getInstance().getCnx();



    @Override
    public void ajouter(Reclamation e) {

        String req = "INSERT INTO reclamation (`email`,`object`,`reclamation`,`date_rec`,`id`) VALUES (?,?,?,?,?)";

        try {            PreparedStatement st = cnx.prepareStatement(req);


            st.setString(1, e.getEmail());
            st.setString(2, e.getObject());
            st.setString(3, e.getRec());
            st.setString(4, String.valueOf(e.getDate_rec()));
            st.setInt(5, e.getId());


            st.executeUpdate();
            System.out.println("ajoutée avec succes.");

        } catch (SQLException ex) {
            ex.printStackTrace();




    }
}

    @Override
    public void modifier(int id_rec, Reclamation t) {
        connection= DataSource.getInstance().getCnx();

        String sql = "UPDATE `reclamation` SET `email` = ?, `object` = ?, `reclamation` = ?,`date_rec` = ?,`id` = ?  WHERE `id_rec` = ?";

        try {
            prepare = connection.prepareStatement(sql);


            prepare.setString(1, t.getEmail());
            prepare.setString(2, t.getObject());
            prepare.setString(3, t.getRec());
            prepare.setString(4, String.valueOf(t.getDate_rec()));
            prepare.setInt(5, t.getId());
            prepare.setInt(6, id_rec);

            prepare.executeUpdate();


            int rowsAffected = prepare.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Match with ID " + id_rec + " modified successfully.");
            } else {
                System.out.println("Match with ID " + id_rec + " not found or failed to modify.");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }




    }

    @Override
    public void supprimer(int id_rec) {
        try {
            if (id_rec != 0) {
                String sql = "DELETE FROM reclamation WHERE id_rec=?";
                PreparedStatement st = cnx.prepareStatement(sql);
                st.setInt(1, id_rec);
                int rowsAffected = st.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Réclamation supprimée avec succès !");
                } else {
                    System.out.println("Aucune réclamation correspondant à l'id_rec spécifié n'a été trouvée.");
                }
            } else {
                System.out.println("L'ID de la réclamation à supprimer ne peut pas être 0.");
            }
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la suppression de la réclamation : " + ex.getMessage());
        }
    }


    public void update(Reclamation user) {

        String sql = "UPDATE `reclamation` SET `email` = ?, `object` = ?, `reclamation` = ?,`date_rec` = SYSDATE(),`id` = ?  WHERE `id_rec` = ?";
        try {
            PreparedStatement pst = cnx.prepareStatement(sql);
            pst.setString(1, user.getEmail());
            pst.setString(2, user.getObject());
            pst.setString(3,user.getRec());
            pst.setInt(4,user.getId());
            pst.setInt(5,user.getId_rec());
            int rowsUpdated = pst.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("User updated!!");
            } else {
                System.out.println("User with id " + user.getId() + " does not exist.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public Reclamation getReclamationById(int id_rec) {
        Reclamation reclamation = null;
        String sql = "SELECT r.*, u.name FROM reclamation r " +
                "JOIN user u ON r.id = u.id WHERE r.id_rec = ?";
        try {
            PreparedStatement pst = cnx.prepareStatement(sql);
            pst.setInt(1, id_rec);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                reclamation = new Reclamation();
                reclamation.setId_rec(rs.getInt("id_rec"));
                reclamation.setEmail(rs.getString("email"));
                reclamation.setObject(rs.getString("object"));
                reclamation.setReclamation(rs.getString("reclamation"));
                reclamation.setDate_rec(rs.getDate("date_rec")); // Assuming date_rec is stored as a Date
                reclamation.setId(rs.getInt("id"));
                reclamation.setName(rs.getString("name"));
                System.out.println("Reclamation fetched successfully.");
            } else {
                System.out.println("No Reclamation found with ID: " + id_rec);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching Reclamation by ID: " + e.getMessage());
            e.printStackTrace();
        }
        return reclamation;
    }

/*
    @Override
    public void afficher() {
        connection= DataSource.getInstance().getCnx();

        String sql = "SELECT * FROM reclamation;";
        try {
            prepare = connection.prepareStatement(sql);
            ResultSet resultSet = prepare.executeQuery();

            while (resultSet.next()) {

                System.out.println("Id_rec: " + resultSet.getInt("id_rec"));
                System.out.println("email: " + resultSet.getString("email"));
                System.out.println("object: " + resultSet.getString("object"));
                System.out.println("reclamation: " + resultSet.getString("reclamation"));
                System.out.println("date: " + resultSet.getString("date_rec"));

                System.out.println("ID user: " + resultSet.getInt("id"));
                System.out.println("-------------------------");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();

        }


    }

 */

    @Override
    public void afficher() {

    }

 /*   public List<Reclamation> getAll() {
        List<Reclamation> Rec = new ArrayList<>();

        String req = "Select * from reclamation";
        try {
            Statement st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);
            while (res.next()){
                ServiceMessages ms=new ServiceMessages();
                int id = res.getInt("id_rec");
                Date date = res.getDate("date_rec");
                String object = res.getString("object");
                String rec = res.getString("reclamation");
                String email = res.getString("email");
                int id_user = res.getInt("id");

                Reclamation p = new Reclamation(id,id_user,object,email,rec,date);
                Rec.add(p);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return Rec;
    }*/
 public List<Reclamation> getAll() {
     List<Reclamation> Rec = new ArrayList<>();

     String req = "Select * from reclamation";
     try {
         Statement st = cnx.createStatement();
         ResultSet res = st.executeQuery(req);
         while (res.next()){
             ServiceMessages ms=new ServiceMessages();
             Reclamation reclamation=new Reclamation();
             reclamation.setId_rec(res.getInt("id_rec"));
             reclamation.setDate_rec(res.getDate("date_rec"));
             reclamation.setObject(res.getString("object"));
             reclamation.setEmail( res.getString("email"));
             reclamation.setReclamation(res.getString("reclamation"));
             reclamation.setId(res.getInt("id"));
             reclamation.setReclamationsMs(ms.getMessagesByReclamationId(reclamation.getId_rec()));
             Rec.add(reclamation);
         }
     } catch (SQLException e) {
         System.out.println(e.getMessage());
     }

     return Rec;
 }

}
