package services;

import entities.Stade;
import entities.ImagesStade;
import interfaces.IService;
import utiles.DbConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Stade_Service implements IService<Stade> {
    private Connection cnx;
    private PreparedStatement ste;

    public Stade_Service() {
        cnx = DbConnector.getInstance().getCon();
    }










    public List<String> GetNomStade() {
        ArrayList<String> stades = new ArrayList();
        String req = "SELECT * FROM stade";

        try {
            Statement st;

            st= DbConnector.getInstance().getCon().prepareStatement(req);
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                Stade e= new Stade(rs.getInt(1),rs.getInt(4),rs.getInt(5), rs.getString(2),
                        rs.getString(3));
                stades.add(e.getNom()
                );

            }


        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return stades;
    }

    public List<Stade> GetStadeFiltred(String filter) {
        ArrayList<Stade> stades = new ArrayList();
        String req = "SELECT * FROM stade where Lieu='"+filter+"'";

        try {
            Statement st;

            st= DbConnector.getInstance().getCon().prepareStatement(req);
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                Stade e= new Stade(rs.getInt(1),rs.getInt(4),rs.getInt(5), rs.getString(2),
                        rs.getString(3));
                String urlImage = "";
                if(getImages(e.getId()).stream().count()>0){
                    urlImage = getImages(e.getId()).get(0);
                    e.setImageIntiale(urlImage);
                }
                stades.add(e
                );

            }


        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return stades;
    }

    public Stade GetStadeById(int id) {
        Stade e = new Stade();
        String req = "SELECT * FROM stade where id="+id+"";

        try {
            Statement st;

            st= DbConnector.getInstance().getCon().prepareStatement(req);
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                 e= new Stade(rs.getInt(1),rs.getInt(4),rs.getInt(5), rs.getString(2),
                        rs.getString(3));


            }


        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return e;
    }

    public Stade GetStadeByName(String name) {
        Stade e = new Stade();
        String req = "SELECT * FROM stade where Nom='"+name+"'";

        try {
            Statement st;

            st= DbConnector.getInstance().getCon().prepareStatement(req);
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                e= new Stade(rs.getInt(1),rs.getInt(4),rs.getInt(5), rs.getString(2),
                        rs.getString(3));


            }


        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return e;
    }


    public void addImage(ImagesStade E) {
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

    public void deleteImage(String urlImage) {
        try {

                String sql = "delete from images_stade WHERE url_image='"+urlImage+"'";
            System.out.println(sql+"++++++++++++++++++++++");
                PreparedStatement st = cnx.prepareStatement(sql);
                st.executeUpdate();


        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public List<String> getImages(int id_stade){
        ArrayList<String> Images = new ArrayList();
        String req = "SELECT * FROM images_stade where id_stade="+id_stade;

        try {
            Statement st;

            st= DbConnector.getInstance().getCon().prepareStatement(req);
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                String url=  rs.getString(3);
                System.out.println("tessssssst"+url);
                Images.add(url);

            }


        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return Images;
    }
    public int getLastStadeId() {
        int lastId = -1;
        String sql = "SELECT MAX(id) AS last_id FROM stade";
        try {
        Statement st;

        st= DbConnector.getInstance().getCon().prepareStatement(sql);
        ResultSet rs = st.executeQuery(sql);

            if (rs.next()) {
                lastId = rs.getInt("last_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lastId;
    }


    @Override
    public void Add(Stade item) {
        try {
            String req = "INSERT INTO stade (`Nom`,`Lieu`,`Capacity`,`Numero`) VALUES (?,?,?,?)";
            PreparedStatement st = cnx.prepareStatement(req);

            st.setString(1, item.getNom());
            st.setString(2, item.getLieu());
            st.setInt(3, item.getCapacity());
            st.setInt(4, item.getNumero());


            st.executeUpdate();
            System.out.println("stade ajoutée avec succes.");

        } catch (SQLException ex) {

            ex.printStackTrace();
        }

    }

    public List<String> getDistinctLocations() {
        List<String> distinctLocations = new ArrayList<>();
        try {
            String req = "SELECT DISTINCT Lieu FROM stade";
            PreparedStatement st = cnx.prepareStatement(req);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                String location = rs.getString("Lieu");
                distinctLocations.add(location);
            }

            rs.close();
            st.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return distinctLocations;
    }

    @Override
    public void Update(Stade item) {
        String sql = "UPDATE stade SET `Nom`=?,`Lieu`=?,`Capacity`=?,`Numero`=? where id=?";
        try {
            PreparedStatement st = cnx.prepareStatement(sql);

            st.setString(1, item.getNom());
            st.setString(2, item.getLieu());
            st.setInt(3,item.getCapacity());
            st.setInt(4,item.getNumero());
            st.setInt(5, item.getId());
            System.out.println(st.toString());
            st.executeUpdate();
            System.out.println("modification avec succees !");
            System.out.println(item);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    @Override
    public void delete(Stade item) {
        try {

            if (item.getId() != 0) {
                String sql = "delete from reservation WHERE id_stade=?";
                String sql0 = "delete from stade WHERE id=?";
                PreparedStatement st = cnx.prepareStatement(sql);
                st.setInt(1, item.getId());
                st.executeUpdate();
                PreparedStatement st1 = cnx.prepareStatement(sql0);
                st1.setInt(1, item.getId());
                st1.executeUpdate();
                System.out.println("deleted !");
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<Stade> Get() {
        ArrayList<Stade> stades = new ArrayList();
        String req = "SELECT * FROM stade";

        try {
            Statement st;

            st= DbConnector.getInstance().getCon().prepareStatement(req);
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                Stade e= new Stade(rs.getInt(1),rs.getInt(4),rs.getInt(5), rs.getString(2),
                        rs.getString(3));
                String urlImage = "";
                if(getImages(e.getId()).stream().count()>0){
                    urlImage = getImages(e.getId()).get(0);
                    e.setImageIntiale(urlImage);
                }

                stades.add(e
                );

            }


        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return stades;
    }
}
