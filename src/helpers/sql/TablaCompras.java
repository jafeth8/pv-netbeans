
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
 * @author jafeth888
 */
public class TablaCompras {
    ConexionBd cc= ConexionBd.obtenerInstancia();
    Connection cn= cc.conexion();
    
    public void actualizarRegistroTablaTCompras(float cantidad,float subTotal,String descripcion,String nameTablaTcompras) {
        PreparedStatement preparedStatement=null;
        try {
            preparedStatement=cn.prepareStatement("UPDATE "+nameTablaTcompras+" SET CANTIDAD=? , SUB_TOTAL=? WHERE DESCRIPCION=?");
            preparedStatement.setFloat(1, cantidad);
            preparedStatement.setFloat(2, subTotal);
            preparedStatement.setString(3,descripcion);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage(),"Error al actualizar Registro en TablaCompras", JOptionPane.ERROR_MESSAGE);
        }finally{
            try {
                if(preparedStatement!=null)preparedStatement.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, ex.getMessage(),"No se puedo cerrar la conexion en actualizarRegistroTablaCompras",JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
    
    public void ingresarNuevoRegistroTablaTcompras(int idProducto,String cantidad,String descripcion,String precioUnitario,String subTotal,String descuento,String nameTablaTcompras) {
	PreparedStatement preparedStatement=null;	
        try {
        preparedStatement = cn.prepareStatement("INSERT INTO "+nameTablaTcompras
            + " (ID,CANTIDAD,DESCRIPCION,PRECIO_UNITARIO,SUB_TOTAL,DESCUENTO) VALUES (?,?,?,?,?,?)");
        preparedStatement.setInt(1,idProducto);
        preparedStatement.setString(2,cantidad);
        preparedStatement.setString(3,descripcion);
        preparedStatement.setString(4,precioUnitario);
        preparedStatement.setString(5,subTotal);
        preparedStatement.setString(6,descuento);
        preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage(),"Error al ingresar nuevo registro en TablaTcompras",JOptionPane.ERROR_MESSAGE);
        }finally{
            try {
                if(preparedStatement!=null)preparedStatement.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, ex.getMessage(),"no se pudo cerrar la conexion al ingresar nuevo registro en TablaTcompras",JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
    
    public boolean existenciaEnComprasPorDescripcion(String des,String nameTablaCompras) {
        Statement st = null;
        ResultSet resultadosConsulta=null;
        try {

            st = cn.createStatement();
            resultadosConsulta = st.executeQuery("SELECT * FROM "+nameTablaCompras+" WHERE DESCRIPCION='" + des + "'");
            if (resultadosConsulta.first())
                return true;
            else
                return false;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"helpers.sql.TablaCompras: "+ e.getMessage(),"Error en al comprobar existencia en comprasPorDescripcion", 0);
            return false;
        }finally{
            try {
                if(st!=null)st.close();
                if(resultadosConsulta!=null)resultadosConsulta.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showConfirmDialog(null,"helper.slq.TablaCompras: "+ex.getMessage(),
                "no se puedo cerrar la conexion en exitenciaEncomprasPorDescripcion", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
    
    public float obtenerSumatoriaSubtotalTablaTcompras(String nameTablaCompras) {
      	 
    	String sql="SELECT SUM(SUB_TOTAL) FROM "+nameTablaCompras+"";
        Float sumatoria=null;    	 
        Statement st = null;
        ResultSet rs = null;
        try {
            st = cn.createStatement();
            rs = st.executeQuery(sql);
            while(rs.next()){
                sumatoria=rs.getFloat(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null,"helpes.sql.TablaCompras"+ex.getMessage(),"Error al obtenerSumatoria Subtotal TablaTCompras", JOptionPane.ERROR_MESSAGE);
        }finally {
            try {
                if(st!=null)st.close();
                if(rs!=null)rs.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null,"helpers.sql.TablaCompras: " +ex.getMessage(),"no se pudo cerrar la conexion al obtenerSumatoria Subtotal TablaTCompras", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        return sumatoria;
    }
        
    public void truncarTablaTcompras(String querySql) {
        try {
            PreparedStatement pst = cn.prepareStatement(querySql);
            pst.executeUpdate();
        } catch (Exception e) {
            System.err.println(e);
            e.printStackTrace();
        }
    }
    
    public void eliminarRegistroTablaTcompras(String descripcion,String nameTablaCompras) {
        try {
            PreparedStatement pst = cn.prepareStatement("DELETE FROM "+nameTablaCompras+" WHERE  DESCRIPCION=?");
            pst.setString(1, descripcion);
            pst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage(),"Error al aliminar registro en tabla compras", JOptionPane.ERROR_MESSAGE);
        }
    }
    
        
}
