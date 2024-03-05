/*package Services;

import Utils.DataSource;
import entities.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserService implements Iservice<User> {
    private Connection conn;
    private Statement ste;
    private PreparedStatement pst;


    public UserService() {
        conn= DataSource.getInstance().getCnx();
    }

    @Override*/
    /*public void add(User u) {
        String requete="insert into user (NomU,PrenomU,EmailU,mdpU,RoleU) value(?,?,?,?,?)";
        try {
            pst=conn.prepareStatement(requete);
            pst.setString(1,u.getNomU());
            pst.setString(2,u.getPrenomU());
            pst.setString(3,u.getEmailU());
            pst.setString(4,u.getMdpU());
            pst.setString(5,u.getRoleU());
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void delete(User u){
        try {
            String requet="delete from user where idU=?";
            pst=conn.prepareStatement(requet);

            pst.setInt(1,u.getIdU());
            pst.executeUpdate();
            System.out.println("user supprimé");

        }
        catch (SQLException ex){
            System.err.println(ex.getMessage());
            System.out.println("user non supprimé");



        }


    }
    public void deleteById(int idU ) {
        try {
            String requete = "DELETE  FROM user WHERE idU=?";
            PreparedStatement pst = conn.prepareStatement(requete);
            pst.setInt(1, idU);

            pst.executeUpdate();
            System.out.println("Activité supprimée!");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            System.out.println("Activité non supprimée!");
        }
    }

    @Override
    public void update(User u) {
        String req="update user SET NomU=?,PrenomU=?,EmailU=?,mdpU=?,RoleU=? where idU=?";
        try {
            pst=conn.prepareStatement(req);
            pst.setString(1,u.getNomU());
            pst.setString(2,u.getPrenomU());
            pst.setString(3,u.getEmailU());
            pst.setString(4,u.getMdpU());
            pst.setString(5,u.getRoleU());
            pst.setInt(6,u.getIdU()); // Setting the value for idU
            pst.executeUpdate();
        } catch (SQLException e) {
System.out.println(e.getMessage());
        }

    }

    @Override
    public List<User> readall() {
        String requete="select * from user";
        List<User>list=new ArrayList<>();

        try {
            ste=conn.createStatement();
            ResultSet rs=ste.executeQuery(requete);
            while(rs.next()){
                list.add(new User(rs.getInt("idU"),rs.getString("NomU"),rs.getString("PrenomU"),rs.getString("EmailU"),rs.getString("mdpU"),rs.getString("RoleU")));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return list;
    }

    @Override




    public User readById(int idU) {
        User user = new User();
        String req = "SELECT * FROM user WHERE idU = ?";
        try {
            PreparedStatement pst= conn.prepareStatement(req);
            pst.setInt(1, idU);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                user.setIdU(rs.getInt("idU"));
                user.setNomU(rs.getString("NomU"));
                user.setPrenomU(rs.getString("PrenomU"));
                user.setEmailU(rs.getString("EmailU"));
                user.setMdpU(rs.getString("mdpU"));
                user.setRoleU(rs.getString("RoleU"));


            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return user;
    }
}*/
package tn.Esprit.services;

import tn.Esprit.iservices.IService;
import tn.Esprit.models.User;
import tn.Esprit.models.role;
import tn.Esprit.utils.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

    public class UserService implements IService<User> {
        private Connection conn;
        private Statement ste;
        private PreparedStatement pst;
        private List<User> userList;


        public UserService() {
            conn= Database.getInstance().getConnection();
        }
        public void initUserList() {
            userList = getAll();
        }



        @Override
        public void add(User u) {
            u.setRoleU(role.utulisateur); // Définition du rôle par défaut à "utulisateur"
            String requete = "insert into user (NomU,PrenomU,EmailU,mdpU,RoleU) value(?,?,?,?,?)";
            try {
                pst = conn.prepareStatement(requete);
                pst.setString(1, u.getNomU());
                pst.setString(2, u.getPrenomU());
                pst.setString(3, u.getEmailU());
                pst.setString(4, u.getMdpU());
                pst.setString(5, u.getRoleU().toString()); // Utilisation du rôle de l'utilisateur fourni
                pst.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }




        @Override
        public boolean delete(User u){
            try {
                String requet = "delete from user where idU=?";
                pst = conn.prepareStatement(requet);
                pst.setInt(1, u.getIdU());
                pst.executeUpdate();
                System.out.println("Utilisateur supprimé de la base de données avec succès.");
            } catch (SQLException ex) {
                System.err.println("Erreur lors de la suppression de l'utilisateur de la base de données : " + ex.getMessage());
                ex.printStackTrace(); // Affichez la pile d'erreurs complète pour obtenir plus d'informations sur l'erreur.
            }


            return false;
        }
        public boolean isEmailAlreadyUsed(String email) {
            userList=getAll();
            // Filtrer la liste des utilisateurs pour vérifier si l'email est déjà utilisé
            return userList.stream().anyMatch(user -> user.getEmailU().equals(email));
        }

        // Méthode pour vérifier si le mot de passe est déjà utilisé par un autre utilisateur
        public boolean isPasswordAlreadyUsed(String password) {
            userList=getAll();
            // Filtrer la liste des utilisateurs pour vérifier si le mot de passe est déjà utilisé
            return userList.stream().anyMatch(user -> user.getMdpU().equals(password));
        }
        public void deleteById(int idU ) {
            try {
                String requete = "DELETE  FROM user WHERE idU=?";
                PreparedStatement pst = conn.prepareStatement(requete);
                pst.setInt(1, idU);

                pst.executeUpdate();
                System.out.println("Activité supprimée!");

            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
                System.out.println("Activité non supprimée!");
            }
        }

        @Override
        public void update(User u) {
            String req="update user SET NomU=?,PrenomU=?,EmailU=?,mdpU=?,RoleU=? where idU=?";
            try {
                pst=conn.prepareStatement(req);
                pst.setString(1,u.getNomU());
                pst.setString(2,u.getPrenomU());
                pst.setString(3,u.getEmailU());
                pst.setString(4,u.getMdpU());
                pst.setString(5,u.getRoleU().name());
                pst.setInt(6,u.getIdU()); // Setting the value for idU
                pst.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

        }

        @Override
        public ArrayList<User> getAll() {
            String requete="select * from user";
            List<User>list=new ArrayList<>();

            try {
                ste=conn.createStatement();
                ResultSet rs=ste.executeQuery(requete);
                while(rs.next()){
                    list.add(new User(rs.getInt("idU"), rs.getString("NomU"), rs.getString("PrenomU"), rs.getString("EmailU"), rs.getString("mdpU"), role.valueOf(rs.getString("RoleU"))));

                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            return (ArrayList<User>) list;
        }

        @Override




        public User readById(int idU) {
            User user = new User();
            String req = "SELECT * FROM user WHERE idU = ?";
            try {
                PreparedStatement pst= conn.prepareStatement(req);
                pst.setInt(1, idU);
                ResultSet rs = pst.executeQuery();
                while (rs.next()) {
                    user.setIdU(rs.getInt("idU"));
                    user.setNomU(rs.getString("NomU"));
                    user.setPrenomU(rs.getString("PrenomU"));
                    user.setEmailU(rs.getString("EmailU"));
                    user.setMdpU(rs.getString("mdpU"));
                    user.setRoleU(role.valueOf(rs.getString("RoleU")));



                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            return user;
        }
        public User login(String email, String password) {
            String query = "SELECT * FROM user WHERE EmailU = ? AND mdpU = ?";
            try {
                PreparedStatement pst = conn.prepareStatement(query);
                pst.setString(1, email);
                pst.setString(2, password);
                ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    return new User(rs.getInt("idU"), rs.getString("NomU"), rs.getString("PrenomU"), rs.getString("EmailU"), rs.getString("mdpU"), role.valueOf(rs.getString("RoleU")));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null; // Si l'utilisateur n'est pas trouvé ou si les informations de connexion sont incorrectes
        }

        // Méthode pour obtenir le rôle de l'utilisateur
        public role getRole(int userId) {
            String query = "SELECT RoleU FROM user WHERE idU = ?";
            try {
                PreparedStatement pst = conn.prepareStatement(query);
                pst.setInt(1, userId);
                ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    return role.valueOf(rs.getString("RoleU"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null; // Si l'utilisateur n'est pas trouvé ou s'il n'a pas de rôle défini
        }
    }

