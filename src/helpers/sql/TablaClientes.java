
package helpers.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import puntodeventa.bd.ConexionBd;

/**
 *
 * @author jafeth8
 */
public class TablaClientes {
    ConexionBd cc= ConexionBd.obtenerInstancia();
    Connection cn= cc.conexion();
    public int registrarClientes(String nombre,String segundoNombre,String apellidos,String direccion, String numero,String tipocliente){
        
        String query="INSERT INTO clientes (nombre,segundo_nombre,apellidos,"
                + "direccion,numero_telefono,fk_id_tipo_cliente)"
                + " VALUES(?,?,?,?,?,?)";
        PreparedStatement psClientes=null;
        int idCliente=0;
        ResultSet resultadoId=null;
        try {
            psClientes=cn.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
            psClientes.setString(1,nombre);
            psClientes.setString(2,segundoNombre);
            psClientes.setString(3,apellidos);
            psClientes.setString(4,direccion);
            psClientes.setString(5,numero);
            psClientes.setString(6,tipocliente);
            
            psClientes.executeUpdate();
            
            resultadoId=psClientes.getGeneratedKeys();
            
            if(resultadoId.next()){
                idCliente=resultadoId.getInt(1);
            }
            JOptionPane.showMessageDialog(null, "SE REGISTRO CORRECTAMENTE AL CLIENTE","mensaje de aviso",JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex.getMessage(),"error al registrar cliente", JOptionPane.ERROR_MESSAGE);
        }finally{
            try {
                if(psClientes!=null) psClientes.close();
                if(resultadoId!=null) resultadoId.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, ex.getMessage(),"Error al cerrar conexion al registrar cliente"
                ,JOptionPane.INFORMATION_MESSAGE);
            }
        }
        return idCliente;
    }
    
}
