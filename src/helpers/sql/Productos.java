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
        
        String query="INSERT INTO productos (CODIGO_BARRA,CANTIDAD,DESCRIPCION,PRECIO_UNITARIO,"
                + " COSTO_UNITARIO,CATEGORIA)"
                + " VALUES(?,?,?,?,?,?)";
        PreparedStatement psProductos=null;
        int idProducto=0;
        ResultSet resultadoId=null;//para obtener el nuevo id generado al producto
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
                JOptionPane.showMessageDialog(null, ex.getMessage(),"Error al cerrar conexion al registrar producto"
                    ,JOptionPane.INFORMATION_MESSAGE);
            }
        }
        return idProducto;
    }
    
     public int ActualizarProducto(String codigoBarra,String cantidad, String descripcion,String precioUnitario,String costoUnitario,
        String categoria){
        
        String query="UPDATE productos SET CANTIDAD=?,DESCRIPCION=?,PRECIO_UNITARIO=?,"
            + " COSTO_UNITARIO=?,CATEGORIA=?"+ " WHERE CODIGO_BARRA=?";
        PreparedStatement psProductos=null;
        int idProducto=0;
        ResultSet resultadoId=null;//para obtener el nuevo id generado al producto
        try {
            psProductos=cn.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
            psProductos.setString(1,cantidad);
            psProductos.setString(2,descripcion);
            psProductos.setString(3,precioUnitario);
            psProductos.setString(4,costoUnitario);
            psProductos.setString(5,categoria);
            psProductos.setString(6,codigoBarra);
            
            psProductos.executeUpdate();
            
            resultadoId=psProductos.getGeneratedKeys();
            
            if(resultadoId.next()){
                idProducto=resultadoId.getInt(1);
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex.getMessage(),"error al actualizar producto", JOptionPane.ERROR_MESSAGE);
        }finally{
            try {
                if(psProductos!=null) psProductos.close();
                if(resultadoId!=null) resultadoId.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, ex.getMessage(),"Error al cerrar conexion al actualizar producto"
                    ,JOptionPane.INFORMATION_MESSAGE);
            }
        }
        return idProducto;
    }
    
    public String obtenerCategoriaProducto(int idProducto) {
        // SELECT valor FROM cporcentaje_comision
        String sql = "SELECT CATEGORIA FROM productos where ID="+idProducto+"";
        String categoria ="";
        Statement st = null;
        ResultSet rs = null;
        try {
            st = cn.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                categoria = rs.getString(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex.getMessage(),"Error al obtener cantidad producto de acuerdo"
            + "a su categoria", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if(st!=null)st.close();
                if(rs!=null)rs.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, ex.getMessage(),
                    "No se pudo al cerrar conexion (obteber categoria producto)",JOptionPane.INFORMATION_MESSAGE);
            }
        }
        return categoria;
    }
    
    public String obtenerCantidadProductoDescripcion(String descripcion) {  
   	 
    	String sql="SELECT CANTIDAD,CATEGORIA FROM productos WHERE DESCRIPCION = '"+descripcion+"'";
        
        String cantidad="";    	 
        Statement st = null;
        ResultSet rs = null;        
        try {
            st = cn.createStatement();
            rs = st.executeQuery(sql);
            while(rs.next()){
            	if(rs.getString(2).equals("unidades")) {
                    cantidad=""+rs.getDouble(1);
            	}else {
                    cantidad=""+rs.getInt(1);
            	}
            }
           
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex.getMessage(),"Error al obtener cantidad producto de acuerdo"
                    + "a su categoria", JOptionPane.ERROR_MESSAGE);
        }finally {
            try {
                if(st!=null)st.close();
                if(rs!=null)rs.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, ex.getMessage(),
                    "No se pudo cerrar conexion (obteber cantidad producto de acuerdo a la categoria del producto)",JOptionPane.INFORMATION_MESSAGE);
            }
        }
        return cantidad;
    }
    
    public void actualizarStateTablaProductos(int id_producto,int idState) {
        PreparedStatement pst=null;
        try {
            pst = cn.prepareStatement("UPDATE productos SET fk_id_state='"+idState+"' WHERE id='"+id_producto+"'");
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,e.getMessage(),"Error al actualizar State tablaProductos",JOptionPane.ERROR_MESSAGE);
        }finally{
            try {
                pst.close();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null,"helpers.sql.Productos: "+ex.getMessage(),"No se pudo cerrar la conexion al actualizar State",JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
    
    public float obtenerCostoProductoTablaProducto(int idProducto) throws SQLException {
   	 
    	String sql="SELECT COSTO_UNITARIO FROM productos WHERE ID = '"+idProducto+"'";
        /*en caso de que haya un error en la tabla productos el costo no se incializara provocando asi una excepcion y evitando
        que se complete la transaccion a la que pertenece esta funcion
        Nota: si se va ocupar esta funcion en otra transaccion,  revisar la ubicacion de la funcion, dependiendo del flujo de la funcion en la transaccion
        puede que lo anteriormente mencionado funcione o no.
        */
        Float costo=null;    	 
	Statement st=null;
        ResultSet rs=null;
        String mensajeExcepcion="";
        
        try {
            st = cn.createStatement();
            rs = st.executeQuery(sql);
            while(rs.next()){
                costo=rs.getFloat(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null,ex.getMessage(),"helpers.sql.Productos.obtenerCostoProductoTablaProducto:Error!",JOptionPane.ERROR_MESSAGE);
            mensajeExcepcion=ex.getMessage();
        }finally {
            try {
                if(st!=null)st.close();
                if(rs!=null)rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null,"helpers.sql.Productos.obtenerCostoProductoTablaProducto: "+e.getMessage(),"No se pudo cerrar la conexion al obtener costo producto",JOptionPane.INFORMATION_MESSAGE);
            }
	}
        if(costo==null) throw new SQLException(mensajeExcepcion);
        return costo;
    }
    
    public String obtenerCategoriaTablaProducto(int idProducto) {//tal vez no se ocupe
      	 
    	String sql="SELECT CATEGORIA FROM productos WHERE ID = '"+idProducto+"'";
	String categoria="";    	 
	Statement st=null;
        ResultSet rs=null;	    
        try {
            st = cn.createStatement();
            rs = st.executeQuery(sql);
            while(rs.next()){
                categoria=rs.getString(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null,ex.getMessage(),"helpers.sql.Productos.obtenerCategoriaoTablaProducto:Error!",JOptionPane.ERROR_MESSAGE);
        }finally {
            try {
                if(st!=null)st.close();
                if(rs!=null)rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null,"helpers.sql.Productos.obtenerCategoriaTablaProducto: "+e.getMessage(),"No se pudo cerrar la conexion al obtener categoria del producto",JOptionPane.WARNING_MESSAGE);
            }
	}
        
        return categoria;
    }
    
    public float obtenerCantidadTablaProducto(int idProducto) throws SQLException {  	    	 
        String sql="SELECT CANTIDAD FROM productos WHERE ID = '"+idProducto+"'";
        float cantidad=0;    	 
	Statement st = null;
        ResultSet rs = null;
        String mensajeExcepcion="";
        boolean excepcion=false;
        try {
            st = cn.createStatement();
            rs = st.executeQuery(sql);
            while(rs.next()){
                cantidad=rs.getFloat(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            excepcion=true;
            mensajeExcepcion=ex.getMessage();
        }finally {
            try {
                if(st!=null)st.close();
                if(rs!=null)rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null,"helpers.sql.Productos.obtenerCategoriaTablaProducto: "+e.getMessage(),"No se pudo cerrar la conexion al obtener cantidad del producto",JOptionPane.WARNING_MESSAGE);
            }
	}
        System.out.println("helpers.sql.Productos.obtenerCantidadTablaProducto()!!!!!!!!!");
        //lanzamos excepcion ya que esta funcion es parte de una transaccion y es necesario notificar error para hacer rollback() 
        if(excepcion)throw new SQLException(mensajeExcepcion);
        
        return cantidad;
    }
    
}
