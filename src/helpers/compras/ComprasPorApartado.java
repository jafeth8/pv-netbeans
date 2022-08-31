package helpers.compras;

import helpers.sql.Productos;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import puntodeventa.bd.ConexionBd;

/**
 *
 * @author jafeth8
 */
public class ComprasPorApartado {

    ConexionBd cc = ConexionBd.obtenerInstancia();
    Connection cn = cc.conexion();

    public boolean apartarProductos(JTable tablaCompras,int idCliente, float total, String fechaApartado, float deudaInicial, String estado) {
        Productos instanciaProductos=new Productos();
        boolean exito=true;
        try {
            cn.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,e.getMessage(),"Error setAutocommit(false): helper.compras.ComprasPorApartado",JOptionPane.WARNING_MESSAGE);
        }
        
        PreparedStatement psInsertApartados=null,psInsertDetalleApartados=null,psActualizarCantidadProductos=null;
        ResultSet resultado=null;
        int idApartado=0;
        try {
            //insertApartados
            psInsertApartados= cn.prepareStatement("INSERT INTO apartados"
            + "(fk_id_cliente,total,fecha_de_apartado,deuda,estado) VALUES (?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
            psInsertApartados.setInt(1, idCliente);
            psInsertApartados.setDouble(2, total);
            psInsertApartados.setString(3, fechaApartado);
            psInsertApartados.setDouble(4, deudaInicial);
            psInsertApartados.setString(5, estado);
            psInsertApartados.executeUpdate();
            //obtenemos el id generado
            resultado=psInsertApartados.getGeneratedKeys();
            if(resultado.next()){
                idApartado=resultado.getInt(1);
            }
            
            for (int i = 0; i < tablaCompras.getRowCount(); i++) {
                String idProducto=tablaCompras.getValueAt(i,0).toString();
                String precioUnitario=tablaCompras.getValueAt(i,3).toString();
                String cantidad=tablaCompras.getValueAt(i, 1).toString();

                int idProductoInteger=Integer.parseInt(idProducto);
                String categoriaProducto=instanciaProductos.obtenerCategoriaTablaProducto(idProductoInteger);//revisar rendimiento con trasacciones
                float costo_unitario=instanciaProductos.obtenerCostoProductoTablaProducto(idProductoInteger);//revisar rendimiento con trasacciones
                float precioUnitarioFloat=Float.parseFloat(precioUnitario);
                //int cantidadInteger=Integer.parseInt(cantidad);
                float cantidadFloat=Float.parseFloat(cantidad);
                double descuento=Double.parseDouble(tablaCompras.getValueAt(i,5).toString());
                
                //insert detalleApartados
                psInsertDetalleApartados = cn.prepareStatement("INSERT INTO detalle_apartados"
    		+ "(fk_id_apartado,fk_id_producto,costo_unitario,precio_unitario,cantidad,fecha_registro,descuento) VALUES (?,?,?,?,?,?,?)");
                psInsertDetalleApartados.setInt(1, idApartado);
                psInsertDetalleApartados.setInt(2, idProductoInteger);
                psInsertDetalleApartados.setFloat(3, costo_unitario);
                psInsertDetalleApartados.setDouble(4, precioUnitarioFloat);
                psInsertDetalleApartados.setDouble(5, cantidadFloat);
                psInsertDetalleApartados.setString(6, fechaApartado);
                psInsertDetalleApartados.setDouble(7, descuento);
                psInsertDetalleApartados.executeUpdate();
                //obtener cantidad tabla productos
                float cantidadTablaProducto=instanciaProductos.obtenerCantidadTablaProducto(idProductoInteger);
                
                //Actualizar cantidadProductos
                double cantidadRestante=cantidadTablaProducto-cantidadFloat;
                psActualizarCantidadProductos = cn.prepareStatement("UPDATE productos SET cantidad='"+cantidadRestante+"' WHERE ID='"+idProducto+"'");
		psActualizarCantidadProductos.executeUpdate();
            }
            
            cn.commit();
            
        } catch (Exception e) {
            exito=false;
            e.printStackTrace();
            try {
                cn.rollback();
            } catch (SQLException ex) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null,e.getMessage(),"helpers.compras.ComprasPorApartado.apartarProductos(): Error Rollback",JOptionPane.ERROR_MESSAGE);
            }
            JOptionPane.showMessageDialog(null,"intente nuevamente: "+e.getMessage(),"Error al Apartar productos:",JOptionPane.ERROR_MESSAGE);
        }finally{
            try {
                cn.setAutoCommit(true);
                if(psInsertApartados!=null)psInsertApartados.close();
                if(psInsertDetalleApartados!=null)psInsertDetalleApartados.close();
                if(psActualizarCantidadProductos!=null)psActualizarCantidadProductos.close();
                if(resultado!=null)resultado.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, ex.getMessage(),"helpers.compras.ComprarPorApartado.apartarProductos(): no se establecio"
                + "setAutoCommit() en true o no se pudieron cerrar conexiones",JOptionPane.INFORMATION_MESSAGE);
            }
        }
        return exito;
    }
}
