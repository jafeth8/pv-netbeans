/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package helpers.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import javax.swing.JOptionPane;
import puntodeventa.bd.ConexionBd;

/**
 *
 * @author jaf63
 */
public class DatosTicket {
    
    ConexionBd cc= ConexionBd.obtenerInstancia();
    Connection cn= cc.conexion();
    
    public void registrarDatos(String id,String establecimiento,String direccion, String telefono,String localidad,String estado) {
        PreparedStatement pst=null;
        try {
            pst = cn.prepareStatement("INSERT INTO datos_ticket" + "(id,establecimiento,direccion,"
            +"telefono,localidad,estado) VALUES (?,?,?,?,?,?)");
            pst.setString(1, id);
            pst.setString(2, establecimiento);
            pst.setString(3, direccion);
            pst.setString(4, telefono);
            pst.setString(5, localidad);
            pst.setString(6, estado);
            pst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: "+e.getMessage(),"No se pudo registrar datos ticket", JOptionPane.ERROR_MESSAGE); 
        }finally{
            try {
                if(pst!=null) pst.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, ex.getMessage(),"Error al cerrar conexion al registrar datos tickets"
                ,JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
    
        public void actualizarDatos(String establecimiento,String direccion, String telefono,String localidad,String estado) {

        PreparedStatement pst=null;
        try {
            pst = cn.prepareStatement("UPDATE datos_ticket SET establecimiento='" + establecimiento
            + "', direccion='" + direccion + "' , telefono='"+telefono+"' , localidad='"+localidad+"'"
            + " , estado='"+estado+"' WHERE id='" +1+ "'");
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "datos actualizados :)");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showInternalMessageDialog(null, e.getMessage(),"Error en:helpers.sql.DatosTicket.actializarDatos()",JOptionPane.ERROR_MESSAGE);
        }finally{
            try {
                if(pst!=null)pst.close();
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showInternalMessageDialog(null, e.getMessage(),"Error al cerrar conexion:helpers.sql.DatosTicket.actializarDatos()",JOptionPane.WARNING_MESSAGE);

            }
        }

    }
    
    public boolean datos_ticket_registrado() {
        try {
            String sql = "SELECT * FROM datos_ticket";
            Statement st = cn.createStatement();
            ResultSet resultadosConsulta = st.executeQuery(sql);
            if (resultadosConsulta.next()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage(),"Error al comprobar registro de datos ticket", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
     public HashMap<String, String> obtenerDatos(String id) {
        HashMap<String, String> datos = new HashMap<String, String>();
        String sql = "SELECT establecimiento,direccion,telefono,localidad,estado FROM datos_ticket where id='" + id+ "'";
       
        Statement st = null;
        ResultSet rs = null;
        try {
            st = cn.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {

                datos.put("establecimiento", rs.getString(1));

                datos.put("direccion", rs.getString(2));

                datos.put("telefono", rs.getString(3));

                datos.put("localidad", rs.getString(4));

                datos.put("estado", rs.getString(5));
                
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex.getMessage(), "error en obtener datos en helpers.sql.DatosTicket.obtenerDatos()", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (st != null) {
                    st.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, ex.getMessage(), "error en al cerrar conexion en helpers.sql.DatosTicket.obtenerDatos()", JOptionPane.INFORMATION_MESSAGE);
            }
        }

        return datos;
    }

}
