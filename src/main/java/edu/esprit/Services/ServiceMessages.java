package edu.esprit.Services;

import edu.esprit.Utils.DataSource;
import edu.esprit.entities.Messages;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceMessages implements IService<Messages> {
    PreparedStatement prepare;
    Connection connection;
    Connection cnx = DataSource.getInstance().getCnx();

    @Override
    public void ajouter(Messages message) {
        String sql = "INSERT INTO messages (sender_id, receiver_id, reclamation_id, content, date) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = cnx.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setInt(1, message.getSender_id());
            ps.setInt(2, message.getReceiver_id());
            ps.setInt(3, message.getReclamation_id());
            ps.setString(4, message.getContent());
            ps.setTimestamp(5, new Timestamp(message.getDate().getTime()));
            ps.executeUpdate();

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    message.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating message failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void modifier(int id, Messages message) {


    }
    public void modifierMessage( Messages message) {
        String sql = "UPDATE messages SET content = ?, date = ? WHERE id = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, message.getContent());
            ps.setTimestamp(2, new Timestamp(message.getDate().getTime()));
            ps.setInt(3, message.getId());
            int affectedRows = ps.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Updating message failed, no rows affected.");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void supprimer(int id) {
        String sql = "DELETE FROM messages WHERE id = ?";
        try{
            PreparedStatement ps = cnx.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void afficher() {

    }

    public List<Messages> getMessagesByReclamationId(int reclamation_id) {
        try {
            List<Messages> messages = new ArrayList<>();
            // Adjust the SQL query to join the messages table with the user table
            // to fetch the sender's name and role along with the message details.
            String sql = "SELECT m.*, u.name , u.role FROM messages m " +
                    "JOIN user u ON m.sender_id = u.id WHERE m.reclamation_id = ?";
            PreparedStatement ps = cnx.prepareStatement(sql);
            ps.setInt(1, reclamation_id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Messages message = new Messages();
                Timestamp timestamp = rs.getTimestamp("date");
                Date date = new Date(timestamp.getTime());
                // Set message details
                message.setDate(date);
                message.setId(rs.getInt("id"));
                message.setSender_id(rs.getInt("sender_id"));
                message.setReceiver_id(rs.getInt("receiver_id"));
                message.setReclamation_id(rs.getInt("reclamation_id"));
                message.setContent(rs.getString("content"));
                // Set sender's name and role fetched from the user table
                message.setName(rs.getString("name"));
                message.setRole(rs.getString("role"));
                messages.add(message);
            }
            return messages;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
