
package validaciones;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import puntodeventa.bd.ConexionBd;

/**
 *
 * @author jafeth888
 */
public class Validaciones {
    ConexionBd cc= ConexionBd.obtenerInstancia();
    Connection cn= cc.conexion();
    public boolean validarProductos(String cod,String des){
    	boolean existe=false;
        try{
    	    Statement st=null;
    	    st=cn.createStatement();
            ResultSet resultadosConsulta = st.executeQuery ("SELECT * FROM productos WHERE CODIGO_BARRA='"+cod+"'AND DESCRIPCION='"+des+"'");
            if( resultadosConsulta.first())existe=true;                          
        } catch (Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,e.getMessage(),"Error al verificar existencia de producto",JOptionPane.ERROR_MESSAGE);
        }
        return existe;
    }
    
    public boolean codigoDeBarrasYaExistente(String des){
        boolean existe=false;
        try{
    	    Statement st=null;
    	    st=cn.createStatement();
            ResultSet resultadosConsulta = st.executeQuery ("SELECT * FROM productos WHERE CODIGO_BARRA='"+des+"'");
            if( resultadosConsulta.first() )existe=true;                         
	} catch (Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,e.getMessage(),"Error al verificar existencia de codigo de barras",JOptionPane.ERROR_MESSAGE);
        }
        return existe;
    }
    
    public boolean descripcionProductoYaExistente(String des){
        boolean existe=false;
    	try{
    	    Statement st=null;
    	    st=cn.createStatement();
            ResultSet resultadosConsulta = st.executeQuery ("SELECT * FROM productos WHERE DESCRIPCION='"+des+"'");
            if( resultadosConsulta.first() )existe=true;           
	} catch (Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,e.getMessage(),"Error al verificar existencia de descripcion producto",JOptionPane.ERROR_MESSAGE);
	}
        return existe;
    }
    
    public boolean isNumeric(String cadena){
        try {
            Integer.parseInt(cadena);
            return true;
        } catch (NumberFormatException nfe){
            return false;
        }
    }
    
    public boolean isDouble(String cadena){
        try {
            Double.parseDouble((cadena));
            return true;
        } catch (NumberFormatException nfe){
            return false;
        }
    }

    
}
