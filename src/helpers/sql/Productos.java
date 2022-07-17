/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
public class Productos {
    ConexionBd cc= ConexionBd.obtenerInstancia();
    Connection cn= cc.conexion();
    public int registrarProducto(String codigoBarra,String cantidad, String descripcion,String precioUnitario,String costoUnitario,
        String categoria){
        //calculamos de una vez la tarifa mensual
        String query="INSERT INTO productos (CODIGO_BARRA,CANTIDAD,DESCRIPCION,PRECIO_UNITARIO,"
                + " COSTO_UNITARIO,CATEGORIA)"
                + " VALUES(?,?,?,?,?,?)";
        PreparedStatement psProductos=null;
        int idProducto=0;
        ResultSet resultadoId=null;//para obtener el nuevo id generado al usuario
        try {
            psProductos=cn.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
            psProductos.setString(1,codigoBarra);
            psProductos.setString(2,cantidad);
            psProductos.setString(3,descripcion);
            psProductos.setString(4,precioUnitario);
            psProductos.setString(5,costoUnitario);
            psProductos.setString(6,categoria);
            
            psProductos.executeUpdate();
            
            resultadoId=psProductos.getGeneratedKeys();
            
            if(resultadoId.next()){
                idProducto=resultadoId.getInt(1);
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex.getMessage(),"error al registrar producto", JOptionPane.ERROR_MESSAGE);
        }finally{
            try {
                if(psProductos!=null) psProductos.close();
                if(resultadoId!=null) resultadoId.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return idProducto;
    }
}
