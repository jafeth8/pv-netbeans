
package helpers.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import puntodeventa.bd.ConexionBd;

/**
 *
 * @author jafeth8
 */
public class TablaInsumos {

    public void insertInsumos(String descripcion,String costo, String fecha){
        ConexionBd cc= ConexionBd.obtenerInstancia();
        Connection cn= cc.conexion();
        PreparedStatement pst=null;
        
        try {
            pst = cn.prepareStatement("INSERT INTO insumos"
            + "(descripcion,costo,fecha) VALUES (?,?,?)");
            pst.setString(1,descripcion);
            pst.setString(2,costo);
            pst.setString(3,fecha);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null,"operacion realizada correctamente");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"no se pudo registrar insumo: "+e.getMessage(),"Error en helpers.sql.TablaInsumos.insertInsumos()",JOptionPane.ERROR_MESSAGE);
        }finally {
            try {
                if(pst!=null)pst.close();
                if(cn!=null)cn.close();
            }catch (SQLException ex) {
                JOptionPane.showMessageDialog(null,"no se cerrar conexion: "+ex.getMessage(),"Error en helpers.sql.TablaInsumos.insertInsumos()",JOptionPane.WARNING_MESSAGE);
            }
        }
    }
    
    public void eliminarRegistroInsumos(int idInsumo) {
        
        try {
            ConexionBd cc= ConexionBd.obtenerInstancia();
            Connection cn= cc.conexion();
            PreparedStatement pst = cn.prepareStatement("DELETE FROM insumos WHERE  id_insumo='" + idInsumo + "'");
            pst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"no se pudo eliminar insumo: "+e.getMessage(),"Error en helpers.sql.TablaInsumos.eliminarRegistroInsumos()",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void actualizarInsumos(int idInsumo, String descripcion, double costo, String fecha) {

        ConexionBd cc= ConexionBd.obtenerInstancia();
        Connection cn= cc.conexion();
        PreparedStatement pst=null;
        try {
            pst = cn.prepareStatement("UPDATE insumos SET descripcion='" + descripcion + "', costo='" + costo + "', fecha='" + fecha + "' WHERE id_insumo='" + idInsumo + "'");
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"no se pudo actualizar insumo: "+e.getMessage(),"Error en helpers.sql.TablaInsumos.actualizarInsumos()",JOptionPane.ERROR_MESSAGE);
        }finally{
             try {
                if(pst!=null)pst.close();
                if(cn!=null)cn.close();
            }catch (SQLException ex) {
                JOptionPane.showMessageDialog(null,"no se cerrar conexion: "+ex.getMessage(),"Error en helpers.sql.TablaInsumos.actualizarInsumos()",JOptionPane.WARNING_MESSAGE);
            }
        }
    }
}
