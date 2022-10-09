
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
public class TablaVentas {
    ConexionBd cc= ConexionBd.obtenerInstancia();
    Connection cn= cc.conexion();
    public float obtenerGanaciasPorDia(String fecha) {
        //String sql="SELECT SUM(total_venta) FROM `ventas`WHERE fecha_venta='2021-01-22'"
        //String sql="SELECT SUM(total_venta) FROM `ventas` WHERE fecha_venta='"+fecha+"'";
        String sql = "SELECT SUM(detalle_ventas.precio_unitario*detalle_ventas.cantidad)-SUM(detalle_ventas.costo_unitario*detalle_ventas.cantidad) FROM "
        + "ventas JOIN detalle_ventas ON id_venta=detalle_ventas.fk_id_venta WHERE ventas.fecha_venta='" + fecha + "'";
        float ganancia = 0;
        Statement st = null;
        ResultSet rs = null;
        try {
            st = cn.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                ganancia = rs.getFloat(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null,"Error al obtnener ganacias: "+ex.getMessage(),"Error en helpers.sql.TablaVentas.obtenerGanaciasPorDia()", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if(st!=null)st.close();
                if(rs!=null)rs.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null,"No se pudo cerrar conexion: "+ex.getMessage(),"Error en helpers.sql.TablaVentas.obtenerGanaciasPorDia()", JOptionPane.WARNING_MESSAGE);
            }
        }

        return ganancia;
    }
}
