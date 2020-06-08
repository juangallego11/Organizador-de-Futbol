
package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author David
 */
public class Conexion {
       public static final String URL = "jdbc:mysql://localhost:3306/db_futbol";
    public static final String USERNAME = "root";
    public static final String PASSWORD = "6357014cg";

    PreparedStatement ps;
    ResultSet rs;

    public static Connection getConection() {

        Connection con = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = (Connection) DriverManager.getConnection(URL, USERNAME, PASSWORD);
       //     JOptionPane.showMessageDialog(null, "Conexion Exitosa");

        } catch (Exception e) {
            System.out.println(e);
        }
        return con;
    }
    
}
