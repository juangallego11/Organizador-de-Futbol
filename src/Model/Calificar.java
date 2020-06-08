package Model;

import static Model.Conexion.getConection;
import View.Calificacion;
import static View.Calificacion.CalificarJugador;
import View.CrearPartidos;
import View.Sanciones;
import java.awt.Dialog;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author David
 */
public class Calificar {

    public void Mover() {
        PreparedStatement ps;
        String SQL = "INSERT INTO calificar (nombre,apellido,calificacion)  SELECT nombre,apellidos,calificacion FROM jugadores";

        Statement st;
        Connection con = null;
        try {
            con = getConection();
            ps = con.prepareStatement(SQL);

            int res = ps.executeUpdate();

        } catch (Exception e) {
            System.out.println(e);

        }

    }

    public void mostrarTabla() {
        DefaultTableModel modelo = new DefaultTableModel();

        modelo.addColumn("Nombre");
        modelo.addColumn("Apellidos");
        modelo.addColumn("Calificación");

        Calificacion.CalificarJugador.setModel(modelo);

        String SQL = "SELECT * FROM calificar";
        String datos[] = new String[3];
        Statement st;
        Connection con = null;
        try {
            con = getConection();
            st = con.createStatement();
            ResultSet rs = st.executeQuery(SQL);
            while (rs.next()) {
                datos[0] = rs.getString(2);
                datos[1] = rs.getString(3);
                datos[2] = rs.getString(4);
                
                modelo.addRow(datos);
            }
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public void actualizar() {
        PreparedStatement ps;
        Statement st;
        Connection con = null;

        int fila = Calificacion.CalificarJugador.getSelectedRow();
        String valor = String.valueOf(Calificacion.CalificarJugador.getValueAt(fila, 1));
        try {
            String nombre = Calificacion.txtNombre.getText();
            String calificacion = Calificacion.txtCalifica.getText();
            String SQL = "UPDATE calificar SET calificacion='" + calificacion + "'WHERE nombre='" + nombre + "'";
            con = getConection();
            ps = con.prepareStatement(SQL);
            ps.execute();
            mostrarTabla();
        } catch (SQLException ex) {

        }
    }

}
