package utiles;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnector {
    final String url="jdbc:mysql://localhost:3306/tournois";
    final String user="root";
    final String pwd="";
    Connection con;

    public static DbConnector db;
    private DbConnector() {

        try {
            System.out.println("Connexion en cours");
            con= (Connection) DriverManager.getConnection(url, user, pwd);
            System.out.println("Connexion etablie");
        } catch (SQLException ex) {
            System.out.println("Probléme de connexion : "+ex.getMessage());

        }
    }
    public static DbConnector getInstance(){
        if(db==null)
            db=new DbConnector();
        return db;

    }
    public Connection getCon() {
        return con;
    }
}