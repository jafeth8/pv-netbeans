
package helpers.sql;

import java.sql.Connection;
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
public class TablaDetalleApartados {
    ConexionBd cc= ConexionBd.obtenerInstancia();
    Connection cn= cc.conexion();
    private int cantidadRegistros (int idApartado) {
        //SELECT COUNT(fk_id_apartado) FROM detalle_apartados WHERE fk_id_apartado=24
        String sql="SELECT COUNT(fk_id_apartado) FROM detalle_apartados WHERE fk_id_apartado= '"+idApartado+"'";
        int cantidad=0;    	 
        Statement st = null;
        ResultSet rs = null;
        try {
            st = cn.createStatement();
            rs = st.executeQuery(sql);
            while(rs.next()){
                cantidad=rs.getInt(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null,ex.getMessage(),"Error en helpers.sql.TabblaDetalleApartados.cantidadRegistros()",JOptionPane.ERROR_MESSAGE);
        }finally {
            try {
                if(st!=null)st.close();
                if(rs!=null)rs.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, ex.getMessage(),"No se pudo cerrrar conexion en: helpers.sql.TabblaDetalleApartados.cantidadRegistros() ",JOptionPane.WARNING_MESSAGE);
            }
        }

        return cantidad;
    }
    
    public String[][] obtenerDatosDetalleApartados(int idApartado) throws SQLException {
	
        String sql;
        int cantidadRegistros=cantidadRegistros(idApartado);
        String resultado[][]=new String[cantidadRegistros][5];
        sql="SELECT fk_id_producto,costo_unitario,precio_unitario,cantidad,descuento FROM detalle_apartados WHERE fk_id_apartado="+idApartado+"";

            int contadorRegistro=0;
            Statement st = null;
            ResultSet rs = null;
            try {
                st = cn.createStatement();
                rs = st.executeQuery(sql);
                while(rs.next()){

                    resultado[contadorRegistro][0]=rs.getString(1);
                    resultado[contadorRegistro][1]=rs.getString(2);
                    resultado[contadorRegistro][2]=rs.getString(3);
                    resultado[contadorRegistro][3]=rs.getString(4);
                    resultado[contadorRegistro][4]=rs.getString(5);
                    contadorRegistro++;	            
               }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, e.getMessage(),"Error en: helpers.sql.TablaDetalleApartados.obtenerDatos...",JOptionPane.ERROR_MESSAGE);
                /*se necesita lanzar SQLException para determinar si se debe debe completar la transaccion
                al ingresar abono justamente en la clase VerApartados*/
                throw new SQLException(e.getMessage());
            }finally{
                try {
                    if(st!=null)st.close();
                    if(rs!=null)rs.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, ex.getMessage(),"No se pudo cerrrar conexion en: helpers.sql.TablaDetalleApartados.obtenerDatos... ",JOptionPane.WARNING_MESSAGE);
                }
            }

            return resultado;

		
    } 
}