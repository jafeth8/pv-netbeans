
package helpers.sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import puntodeventa.bd.ConexionBd;

/**
 *
 * @author jafeth8
 */
public class TablaApartados {
    ConexionBd cc= ConexionBd.obtenerInstancia();
    Connection cn= cc.conexion();
    public float obtenerPrecioTotalTablaApartados(int id_apartado) {
        String sql="SELECT total FROM apartados WHERE id_apartado = '"+id_apartado+"'";
        float total = 0;
        Statement st =null;
        ResultSet rs =null;
        try {
            st = cn.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                total = rs.getFloat(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null,ex.getMessage(),"Error en helper.sql.TablaApartados.obtenerPrecioTotal...",JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if(st!=null)st.close();
                if(rs!=null)rs.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, ex.getMessage(),"no se pudo cerrar la conexion: helper.sql.TablaApartados.obtenerPrecioTotal...",JOptionPane.WARNING_MESSAGE);
            }
        }
        return total;
    }
    
    public float obtenerTotalAbonoTablaApartados(int id_apartado) {
        String sql="SELECT total_abono FROM apartados WHERE id_apartado = '"+id_apartado+"'";
        float total = 0;
        Statement st = null;
        ResultSet rs = null;
        try {
            st = cn.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                total = rs.getFloat(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex.getMessage(),"Error en helpers.sql.TablaApartados.obtenerTotalAbono...",JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if(st!=null)st.close();
                if(rs!=null)rs.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, ex.getMessage(),"no se pudo cerrar la conexion: helpers.sql.TablaApartados.obtenerTotalAbono...",JOptionPane.WARNING_MESSAGE);
            }
        }
        return total;
    }
    
    public double obtenerDeudaTablaApartados(int id_apartado) {
        String sql="SELECT deuda FROM apartados WHERE id_apartado = '"+id_apartado+"'";
        double total = 0;
        Statement st = null;
        ResultSet rs = null;
        try {
            st = cn.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                total = rs.getDouble(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex.getMessage(),"Error en helpers.TablaApartados.obtenerDeuda...",JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if(st!=null)st.close();
                if(rs!=null)rs.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, ex.getMessage(),"No se pudo cerrar la conexion en: helpers.TablaApartados.obtenerDeuda...",JOptionPane.WARNING_MESSAGE);
            }
        }
        return total;
    }
    
    
}
