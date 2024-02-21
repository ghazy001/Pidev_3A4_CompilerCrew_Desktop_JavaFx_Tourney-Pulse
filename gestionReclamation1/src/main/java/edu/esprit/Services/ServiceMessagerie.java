package edu.esprit.Services;

import edu.esprit.Utils.DataSource;
import edu.esprit.entities.Messagerie;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ServiceMessagerie implements IService<Messagerie> {

    PreparedStatement prepare;
    Connection connection;
    Connection cnx = DataSource.getInstance().getCnx();


    @Override
    public void ajouter(Messagerie t) {
        String req = "INSERT INTO messagerie (`id_rec`,`contenue`,`temps`) VALUES (?,?,?)";

        try {
            PreparedStatement st = cnx.prepareStatement(req);

            st.setInt(1, t.getId_rec());
            st.setString(2, t.getContenue());
            st.setDate(3, t.getTemps());


            st.executeUpdate();
            System.out.println("ajoutée avec succes.");

        } catch (SQLException ex) {
            ex.printStackTrace();


        }
    }

    @Override
    public void modifier(int id_mess, Messagerie t) {
        connection = DataSource.getInstance().getCnx();

        String sql = "UPDATE `messagerie` SET  `contenue` = ?, `temps` = ?, `id_rec` = ?  WHERE `id_mess` = ?";

        try {
            prepare = connection.prepareStatement(sql);

            prepare.setString(1, t.getContenue());
            prepare.setDate(2, t.getTemps());
            prepare.setInt(3, t.getId_rec());
            prepare.setInt(4, id_mess);

            int rowsAffected = prepare.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Message avec l'ID " + id_mess + " modifié avec succès.");
            } else {
                System.out.println("Message avec l'ID " + id_mess + " non trouvé ou la modification a échoué.");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


    @Override
    public void supprimer(int id_mess) {
        try {
            if (id_mess != 0) {
                String sql = "DELETE FROM messagerie WHERE id_mess=?";
                PreparedStatement st = cnx.prepareStatement(sql);
                st.setInt(1, id_mess);
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

    @Override
    public void afficher() {
        connection = DataSource.getInstance().getCnx();

        String sql = "SELECT m.*, r.email " +
                "FROM messagerie m " +
                "JOIN reclamation r ON m.id_rec = r.id_rec;";
        try {
            prepare = connection.prepareStatement(sql);
            ResultSet resultSet = prepare.executeQuery();

            while (resultSet.next()) {

                System.out.println("Id_mess: " + resultSet.getInt("id_mess"));
                System.out.println("contenue; " + resultSet.getString("contenue"));
                System.out.println("temps: " + resultSet.getDate("temps"));
                System.out.println("id_Reclamaion: " + resultSet.getString("id_rec"));
                System.out.println("Email de la réclamation: " + resultSet.getString("email"));

                System.out.println("-------------------------");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();

        }
    }

    public List<Messagerie> getMessagesForReclamationId(int id_rec) {
        List<Messagerie> messages = new ArrayList<>();
        connection = DataSource.getInstance().getCnx();


              String sql = "SELECT * FROM messagerie WHERE id_rec = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id_rec);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int messageId = resultSet.getInt("id_mess");
                String contenue = resultSet.getString("contenue");
                Date temps = resultSet.getDate("temps");

                // Créer un objet Message
                Messagerie message = new Messagerie(messageId, id_rec, contenue, temps);
                messages.add(message);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer les erreurs de la base de données
        }

        return messages;
    }

    }

