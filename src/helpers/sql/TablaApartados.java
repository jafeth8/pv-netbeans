
package helpers.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    
    public float obtenerDeudaTablaApartados(int id_apartado) throws SQLException {
        String sql="SELECT deuda FROM apartados WHERE id_apartado = '"+id_apartado+"'";
        Float total = null;
        Statement st = null;
        ResultSet rs = null;
        boolean excepcion=false;
        String mensajeExcepcion="";
        try {
            st = cn.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                total = rs.getFloat(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex.getMessage(),"Error en helpers.TablaApartados.obtenerDeuda...",JOptionPane.ERROR_MESSAGE);
            excepcion=true;
            mensajeExcepcion=ex.getMessage();
        } finally {
            try {
                if(st!=null)st.close();
                if(rs!=null)rs.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, ex.getMessage(),"No se pudo cerrar la conexion en: helpers.TablaApartados.obtenerDeuda...",JOptionPane.WARNING_MESSAGE);
            }
        }
        //si existe error lanzamos excepcion ya que esta funcion forma parte de una transaccion
        if(excepcion) {
            throw new SQLException(mensajeExcepcion);
        }
        return total;
    }
    
    public float obtenerTotalTablaApartados(int idApartado) throws SQLException {
        String sql="SELECT total FROM apartados WHERE id_apartado = '"+idApartado+"'";
        Float costo=null;    	 
        Statement st = null;
        ResultSet rs = null;
        boolean excepcion=false;
        String mensajeExcepcion="";
        try {
            st = cn.createStatement();
            rs = st.executeQuery(sql);
            while(rs.next()){
                costo=rs.getFloat(1);
            }
        }catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al obtener el total: "+ex.getMessage(),"Error en helpers.sql.TablaApartados.obtenerTotal...",JOptionPane.ERROR_MESSAGE);
            excepcion=true;
            mensajeExcepcion=ex.getMessage();
        } finally {
            try {
                if(st!=null)st.close();
                if(rs!=null)rs.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null,"Error al cerrar conexion al obtner total"+ ex.getMessage(),"No se pudo cerrar la conexion en: helpers.sql.TablaApartados.obtenerTotal...",JOptionPane.WARNING_MESSAGE);
            }
        }
        //si existe error lanzamos excepcion ya que esta funcion forma parte de una transaccion
        if(excepcion) {
            throw new SQLException(mensajeExcepcion);
        }
        return costo;
    }
    
    public void aumentarTotal_y_DeudaTablaApartados(int idApartado,float totalOriginal,float deudaOriginal,float totalAgregado) throws SQLException {
    	float resultadoTotal=totalOriginal+totalAgregado;
    	float resultadoDeuda=deudaOriginal+totalAgregado;
    	PreparedStatement pst=null;
        boolean excepcion=false;
        String mensajeExcepcion="";
        try {
            pst = cn.prepareStatement("UPDATE apartados SET total='"+resultadoTotal+"',deuda='"+resultadoDeuda+"' WHERE id_apartado='"+idApartado+"'");
            pst.executeUpdate();
        }catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al aumentar el total y deuda tabla apartados: "+ex.getMessage(),"Error en helpers.sql.TablaApartados.aumentarTotal_y_DeudaTablaApartados()",JOptionPane.ERROR_MESSAGE);
            excepcion=true;
            mensajeExcepcion=ex.getMessage();
        }finally {
            try {
                if(pst!=null)pst.close();
            }catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null,"Error al cerrar conexion al aumentar total y deuda"+ ex.getMessage(),"No se pudo cerrar la conexion en: helpers.sql.TablaApartados.aumentarTotal_y_DeudaTablaApartados()",JOptionPane.WARNING_MESSAGE);
            }
        }
        //si existe error lanzamos excepcion ya que esta funcion forma parte de una transaccion
        if (excepcion) {
            throw new SQLException(mensajeExcepcion);
        }
    }
}
