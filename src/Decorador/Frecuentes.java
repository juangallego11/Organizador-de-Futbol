/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Decorador;

import static Model.Conexion.getConection;
import View.CrearPartidos;
import java.beans.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author David
 */
public class Frecuentes extends Jugadores{
    
     public void tablaFrecuentes(){
       DefaultTableModel modelo = new DefaultTableModel();
       modelo.addColumn("Id");
       modelo.addColumn("Nombre");
       modelo.addColumn("Apellido");
       CrearPartidos.equipoUno.setModel(modelo);

        String SQL = "SELECT * FROM jugadores where frecuencia='frecuente' ORDER BY rand() LIMIT 5";
        String datos[] = new String[3];
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
                
                modelo.addRow(datos);
            }
            CrearPartidos.equipoUno.setModel(modelo);
        } catch (Exception e) {
            System.out.println(e);
        }

   }
    
}
