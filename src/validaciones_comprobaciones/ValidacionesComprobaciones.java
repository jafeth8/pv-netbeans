
package validaciones_comprobaciones;

import helpers.sql.TablaApartados;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import puntodeventa.bd.ConexionBd;

/**
 *
 * @author jafeth888
 */
public class ValidacionesComprobaciones {
    ConexionBd cc= ConexionBd.obtenerInstancia();
    Connection cn= cc.conexion();
    TablaApartados instanciaTablaApartados=new TablaApartados();
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
    
    public boolean validarEntradaApartados(String entrada){
        boolean resultado=true;
        if (entrada.equals("")) {
            JOptionPane.showMessageDialog(null, "El campo esta vacio","error",JOptionPane.ERROR_MESSAGE);
            resultado= false;
        }
        else if(isDouble(entrada)==false) {
            resultado=false;
            JOptionPane.showMessageDialog(null, "Ups,al parecer no ingresaste un numero o escribiste una , entre los numeros","error",JOptionPane.ERROR_MESSAGE);
        }else if(isDouble(entrada)) {
            if(Double.parseDouble(entrada)<=0) {
                JOptionPane.showMessageDialog(null, "El numero es negativo o igual a cero","error",JOptionPane.ERROR_MESSAGE);
                resultado=false;
            }
        }
        return resultado;
    }
    
    public boolean entradasValidas(String entrada1,String entrada2) {
        boolean resultado=true;
        double entradaDineroRecibido=Double.parseDouble(entrada1);
        double entradaCantidadAbono=Double.parseDouble(entrada2);
        if(entradaDineroRecibido<entradaCantidadAbono) {
           JOptionPane.showMessageDialog(null,"El dinero recibido del cliente es menor a la cantidad que va a abonar","Atencion",JOptionPane.WARNING_MESSAGE);
           resultado=false;
        }
        return resultado;
    }
    
    public boolean validarCantidadAbono(String entrada,int id_apartado) {

        boolean resultado=true;

        double precioTotal=instanciaTablaApartados.obtenerPrecioTotalTablaApartados(id_apartado);
        double abono=Double.parseDouble(entrada);
        double abonoTotal=instanciaTablaApartados.obtenerTotalAbonoTablaApartados(id_apartado);
        double abonoAcumulado;
        double deuda=instanciaTablaApartados.obtenerDeudaTablaApartados(id_apartado);

        abonoAcumulado=abonoTotal+abono;
        if(abonoAcumulado>precioTotal) {
            JOptionPane.showMessageDialog(null, "Tan solo la deuda es de: "+deuda+" ingresa la cantidad que corresponde al abono","error",JOptionPane.ERROR_MESSAGE);
            resultado=false;
        }

        return resultado;
   }
}
