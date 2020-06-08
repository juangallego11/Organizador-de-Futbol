/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Strategy;

import static Model.Conexion.getConection;
import View.CrearPartidos;
import View.Sanciones;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author JuanPablo
 */
public class Cancelacion implements Sancion{

    @Override
    public void sancionar() {
        PreparedStatement ps;
        PreparedStatement ps2;
        
        int fila = CrearPartidos.equipoDos.getSelectedRow();
        String valor = String.valueOf(CrearPartidos.equipoDos.getValueAt(fila, 0)); 
        
          String SQL = "INSERT INTO sanciones (nombre,apellido,cedula,frecuencia,tiempoSancion)  SELECT nombre,apellidos,cedula,frecuencia,tiempoSancion FROM jugadores WHERE idjugadores=" + valor + "";  
        String SQL2 = "UPDATE jugadores SET tiempoSancion= '2 partidos' WHERE idjugadores=" + valor + "";    
    
        Statement st;
        Connection con = null;
        try {
            
         
            con = getConection();
            ps2 = con.prepareStatement(SQL2);
            ps = con.prepareStatement(SQL);
                       
            int res2 = ps2.executeUpdate();
            int res = ps.executeUpdate();
           
            
            JOptionPane.showMessageDialog(null, "Sancionado por cancelar sobre la hora");
        } catch (Exception e) {
            System.out.println(e);
        
        }
    }
    
     public void mostrarTabla(){
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("Id");
        modelo.addColumn("Nombre");
        modelo.addColumn("Apellidos");
        modelo.addColumn("Cedula");
        modelo.addColumn("Tipo de frecuencia");
        modelo.addColumn("Partidos de suspención");
      
        Sanciones.tablaSancion.setModel(modelo);

        String SQL = "SELECT * FROM sanciones";
        String datos[] = new String[6];
        Statement st;
        Connection con = null;
        try {
            con = getConection();
            st = con.createStatement();
            ResultSet rs = st.executeQuery(SQL);
            while (rs.next()) {
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                datos[3] = rs.getString(4);
                datos[4] = rs.getString(5);
                datos[5] = rs.getString(6);
                modelo.addRow(datos);
            }
            Sanciones.tablaSancion.setModel(modelo);
        } catch (Exception e) {
            System.out.println("Error");
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
                mostrarTabla();
              
            } else {
                JOptionPane.showMessageDialog(null, "debe seleccionar una fila");
            }

        } catch (Exception e) {

        }
    }
        
        public void quitarSancion(){
        PreparedStatement ps;
        
        int fila = Sanciones.tablaSancion.getSelectedRow();
        String valor = String.valueOf(Sanciones.tablaSancion.getValueAt(fila, 0)); 
        String SQL = "INSERT INTO jugadores (nombre,apellidos,cedula,frecuencia) SELECT nombre,apellido,cedula,frecuencia FROM sanciones WHERE idsanciones=" + valor + "";  
        Statement st;
        Connection con = null;
        try {
            con = getConection();
            ps = con.prepareStatement(SQL);
            
            int res = ps.executeUpdate();
        
            JOptionPane.showMessageDialog(null, "Sancion Levantada");
        } catch (Exception e) {
            System.out.println(e);
        
        }
        
     }   
        
        public void eliminarSancion() {
        Connection con = null;        
        con = getConection();
        int fila = Sanciones.tablaSancion.getSelectedRow();
        String valor = String.valueOf(Sanciones.tablaSancion.getValueAt(fila, 0));
        
            try {
            if (fila >= 0) {
                PreparedStatement ps;
                ResultSet rs;
                String SQL = "DELETE FROM sanciones WHERE idsanciones=" + valor + "";
                ps = con.prepareStatement(SQL);
                ps.executeUpdate();
                mostrarTabla();
              
            } else {
                JOptionPane.showMessageDialog(null, "debe seleccionar una fila");
            }

        } catch (Exception e) {

        }
    }
    
}
