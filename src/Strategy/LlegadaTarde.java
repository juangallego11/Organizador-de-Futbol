/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Strategy;

import static Model.Conexion.getConection;
import View.CrearPartidos;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class LlegadaTarde implements Sancion {

    Cancelacion c = new Cancelacion();

    @Override
    public void sancionar() {
        PreparedStatement ps;
        PreparedStatement ps2;

        //int fila1 = CrearPartidos.tablaBarcelona.getAutoResizeMode();
        int fila = CrearPartidos.equipoDos.getSelectedRow();
        String valor = String.valueOf(CrearPartidos.equipoDos.getValueAt(fila, 0));

        String SQL = "INSERT INTO sanciones (nombre,apellido,cedula,frecuencia,tiempoSancion)  SELECT nombre,apellidos,cedula,frecuencia,tiempoSancion FROM jugadores WHERE idjugadores=" + valor + "";
        String SQL2 = "UPDATE jugadores SET tiempoSancion= '1 partido' WHERE idjugadores=" + valor + "";

        Statement st;
        Connection con = null;
        try {

            con = getConection();
            ps2 = con.prepareStatement(SQL2);
            ps = con.prepareStatement(SQL);

            int res2 = ps2.executeUpdate();
            int res = ps.executeUpdate();

            JOptionPane.showMessageDialog(null, "Sancionado por llegar tarde al partido");
        } catch (Exception e) {
            System.out.println(e);

        }
    }

    public void eliminarJugador() {
        Connection con = null;
        con = getConection();
        int fila = CrearPartidos.equipoDos.getSelectedRow();
        String valor = String.valueOf(CrearPartidos.equipoDos.getValueAt(fila, 0));

        try {
            if (fila >= 0) {
                PreparedStatement ps;
                ResultSet rs;
                String SQL = "DELETE FROM jugadores WHERE idjugadores=" + valor + "";
                ps = con.prepareStatement(SQL);
                ps.executeUpdate();
                c.mostrarTabla();

            } else {
                JOptionPane.showMessageDialog(null, "debe seleccionar una fila");
            }

        } catch (Exception e) {

        }
    }
}
