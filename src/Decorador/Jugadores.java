
package Decorador;

import static Model.Conexion.getConection;
import View.CrearJugador;
import View.CrearPartidos;
import java.beans.Statement;
import java.sql.*;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author David
 */
public class Jugadores {
    
    String tipo;
    String nombre;
    String apellidos;
    String cedula;
    String equipo;

    public Jugadores(String nombre, String apellidos, String cedula, String equipo) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.cedula = cedula;
        this.equipo = equipo;
    }

    public Jugadores() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getEquipo() {
        return equipo;
    }

    public void setEquipo(String equipo) {
        this.equipo = equipo;
    }

    @Override
    public String toString() {
        return "Jugadores{" + "nombre=" + nombre + ", apellidos=" + apellidos + ", cedula=" + cedula + ", equipo=" + equipo + '}';
    }
    
   
  
   
   public void ingresarJugador(){
      String nombre = CrearJugador.txtNombre.getText();
      String apellido = CrearJugador.txtApellido.getText();
      String cedula = CrearJugador.txtCedula.getText();
      String equipo = CrearJugador.cbxFrecuencia.getSelectedItem().toString();
      
      Connection con = null;
      PreparedStatement ps ;
      ResultSet rs;
      
      if(nombre.equals("") || apellido.equals("") || cedula.equals("")){
          JOptionPane.showMessageDialog(null, "Por favor ingrese todos los campos");
      }else{
          try {
              con = getConection();
              String SQL = "INSERT INTO jugadores(nombre,apellidos,cedula,frecuencia) VALUES (?,?,?,?)";
              ps = con.prepareStatement(SQL);
              ps.setString(1, nombre);
              ps.setString(2, apellido);
              ps.setString(3, cedula);
              ps.setString(4, equipo);
              
              int res = ps.executeUpdate();
              if(res > 0){
                  JOptionPane.showMessageDialog(null, "Jugador agregado correctamente");
                  mostrarTabla();
                  limpiarCampos();
                  
              }
          } catch (Exception e) {
          }
      }
   }
   
   public void mostrarTabla(){
    DefaultTableModel modelo = new DefaultTableModel();
       modelo.addColumn("Id");
       modelo.addColumn("Nombre");
       modelo.addColumn("Apellido");
       modelo.addColumn("Tipo Jugador");
       modelo.addColumn("Cedula");
       
       CrearJugador.tablaJugadores.setModel(modelo);

        String SQL = "SELECT * FROM jugadores";
        String datos[] = new String[5];
        Statement st;
        Connection con = null;
        try {
            con = getConection();
            PreparedStatement ps;
            ps = con.prepareStatement(SQL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                datos[3] = rs.getString(4);
                datos[4] = rs.getString(5);
                
                
                modelo.addRow(datos);
            }
            CrearJugador.tablaJugadores.setModel(modelo);
        } catch (Exception e) {
            System.out.println(e);
        }
   
   }
   
   public void limpiarCampos(){
       CrearJugador.txtApellido.setText("");
       CrearJugador.txtNombre.setText("");
       CrearJugador.txtCedula.setText("");
   }
   
   public void eliminar(){
       Connection con = null;        
        con = getConection();
        int fila = CrearJugador.tablaJugadores.getSelectedRow();
        String valor = String.valueOf(CrearJugador.tablaJugadores.getValueAt(fila, 0));
        
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
    
    
}
