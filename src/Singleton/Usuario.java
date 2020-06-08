package Singleton;

import View.Login;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import static Model.Conexion.*;
import View.MenuPrincipal;
import View.Usuarios;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class Usuario {

    public void limpiarUsuarios() {
        Usuarios.txtNuevoUser.setText("");
        Usuarios.txtNuevaClave.setText("");
        Usuarios.txtRepetirClave.setText("");

    }

    public void ingresar() {
        String usuario = Login.txtUsuario.getText();
        String clave = String.valueOf(Login.txtClave.getPassword());
        String tipo = String.valueOf(Login.cbxTipo.getSelectedItem());
        

        PreparedStatement ps;
        ResultSet rs;
        try {
            Connection con = null;
            con = getConection();
            String SQL = "SELECT * FROM usuarios WHERE usuario=? AND clave = ? AND tipoUsuario=? ";
            ps = con.prepareStatement(SQL);
            ps.setString(1, usuario);
            ps.setString(2, Login.txtClave.getText());
            ps.setString(3, tipo);

            rs = ps.executeQuery();

            if (rs.next()) {
                if (tipo.equals("ADMINISTRADOR")) {
                    MenuPrincipal menu = new MenuPrincipal();
                    menu.setVisible(true);
                    con.close();
                    
                } else {
                    if (tipo.equals("JUGADOR")) {
                        MenuPrincipal menu = new MenuPrincipal();
                        menu.setVisible(true);
                        MenuPrincipal.MenuPartido.setEnabled(false);
                        MenuPrincipal.itemInscribir.setEnabled(false);
                        MenuPrincipal.menuSancion.setEnabled(false);
                        MenuPrincipal.menuUsuarios.setEnabled(false);
                    } 
                }
            } else {
                JOptionPane.showMessageDialog(null, "Usuario/clave o Perfil incorrectos");
                Login.txtUsuario.setText("");
                Login.txtClave.setText("");
            }

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public void agregarUsuario() {

        String user = Usuarios.txtNuevoUser.getText();
        String nClave = Usuarios.txtNuevaClave.getText();
        String repClave = Usuarios.txtRepetirClave.getText();
        String TipoUsuario = Usuarios.cbxTIpoUsuario.getSelectedItem().toString();

        Connection con = null;
        PreparedStatement ps;
        ResultSet rs;

        if (user.equals("") || nClave.equals("") || repClave.equals("")) {
            JOptionPane.showMessageDialog(null, "Por favor llene todos los campos");
        } else {

            if (nClave.equals(repClave)) {
                try {
                    con = getConection();
                    String SQL = "INSERT INTO usuarios(usuario,clave,tipoUsuario) VALUES(?,?,?)";
                    ps = con.prepareStatement(SQL);
                    ps.setString(1, user);
                    ps.setString(2, nClave);
                    ps.setString(3, TipoUsuario);

                    int res = ps.executeUpdate();
                    if (res > 0) {
                        JOptionPane.showMessageDialog(null, "Persona guardada con perfil " + TipoUsuario);
                        //this.tipo = TipoUsuario.toString();
                        mostrarTabla();
                        limpiarUsuarios();
                    } else {
                        JOptionPane.showMessageDialog(null, "Error al guardar persona");
                    }
                    con.close();

                } catch (Exception e) {
                    System.err.println(e);
                }

            } else {
                JOptionPane.showMessageDialog(null, "Las claves no coinciden");
            }
        }
    }

    public void mostrarTabla() {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("Id");
        modelo.addColumn("Usuarios");
        modelo.addColumn("Tipo de usuario");
        Usuarios.tablaUsuarios.setModel(modelo);

        String SQL = "SELECT * FROM usuarios";
        String datos[] = new String[3];
        Statement st;
        Connection con = null;
        try {
            con = getConection();
            st = con.createStatement();
            ResultSet rs = st.executeQuery(SQL);
            while (rs.next()) {
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(4);
                modelo.addRow(datos);
            }
            Usuarios.tablaUsuarios.setModel(modelo);
        } catch (Exception e) {
            System.out.println("Error");
        }

    }

    public void eliminarUsuario() {
        Connection con = null;
        con = getConection();
        int fila = Usuarios.tablaUsuarios.getSelectedRow();
        String valor = String.valueOf(Usuarios.tablaUsuarios.getValueAt(fila, 0));

        try {
            if (fila >= 0) {
                PreparedStatement ps;
                ResultSet rs;
                String SQL = "DELETE FROM usuarios WHERE idusuarios=" + valor + "";
                ps = con.prepareStatement(SQL);
                ps.executeUpdate();
                JOptionPane.showMessageDialog(null, "Usuario eliminado correctamente");
                mostrarTabla();
            } else {
                JOptionPane.showMessageDialog(null, "debe seleccionar una fila");
            }

        } catch (Exception e) {

        }
    }

}
