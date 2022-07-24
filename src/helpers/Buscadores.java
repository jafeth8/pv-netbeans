
package helpers;

import helpers.sql.TablaCompras;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import puntodeventa.VentanaPrincipal;
import puntodeventa.bd.ConexionBd;


import validaciones_comprobaciones.ValidacionesComprobaciones;

/**
 *
 * @author jafeth888
 */
public class Buscadores {
    ConexionBd cc= ConexionBd.obtenerInstancia();
    Connection cn= cc.conexion();
    
    public void searchByBarCode(JTable tablaProductos,JTable tablaCompras,JTextField jtextFieldDescuento,JTextField buscador,JComboBox comboBoxTipoBusqueda,JTextField jtextFieldCantidadProductos,JLabel labelTotal,String nameTablaCompras){
        TableModel conexionTableModel=new TableModel();
        ValidacionesComprobaciones validacion =new ValidacionesComprobaciones();
        TablaCompras helperTablaCompras=new TablaCompras();
        
        String MetodoBusqueda=(String)comboBoxTipoBusqueda.getSelectedItem();
        int cantidadJtextfield;
        float total=0;
		
        if(jtextFieldCantidadProductos.getText().equals("")) {
            JOptionPane.showMessageDialog(null,"DEBES DE PONER UNA CANTIDAD","Mensaje de Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if(jtextFieldDescuento.getText().equals("") || validacion.isDouble(jtextFieldDescuento.getText())==false) {
            JOptionPane.showMessageDialog(null, "El valor del campo descuento no fue valido: debe ingresar un numero.","Aviso!",JOptionPane.WARNING_MESSAGE);
            jtextFieldDescuento.setText("0.0");
            buscador.setText("");
            return;
        }


        /*------------BUSQUEDA POR CODIGO DE BARRAS-------------------------------------------*/
        if(busquedaPorCodigoBarra(buscador.getText())) {

            conexionTableModel.mostrarProductoCodigoDeBarra(buscador.getText(), tablaProductos);
            final int fila=0;
            cantidadJtextfield=Integer.parseInt(jtextFieldCantidadProductos.getText());
            int idProducto=Integer.parseInt(tablaProductos.getValueAt(fila, 0).toString());
            String cantidad=tablaProductos.getValueAt(fila, 2).toString();
            String descripcion = tablaProductos.getValueAt(fila, 3).toString();
            String precio=tablaProductos.getValueAt(fila, 4).toString();
            String categoria=tablaProductos.getValueAt(fila, 6).toString();
            float descuento=Float.parseFloat((jtextFieldDescuento.getText().toString()));

            if(categoria.equals("unidades")) {
                JOptionPane.showMessageDialog(null,"Los productos de categoria 'unidades' no debe ser buscado por metodo de codigo de barras,debido a cuestiones tecnicas, por favor busque este producto por descripcion o categoria","ERRORR",JOptionPane.ERROR_MESSAGE);
                return;
            }

            int cantidadEntablaProductos = Integer.parseInt(cantidad);
            if (cantidadJtextfield<=0) {
                JOptionPane.showMessageDialog(null,"EL DATO ES INCORRECTO","Mensaje de Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (cantidadJtextfield>cantidadEntablaProductos) {
                JOptionPane.showMessageDialog(null,"PRODUCTO INSUFICIENTE","Mensaje de Error", JOptionPane.ERROR_MESSAGE);
                VentanaPrincipal.codigoBarra.setText("");//limpia el campo de busqueda ya que seria molesto borrar todo el codigo de barras manualmente ejemplo: 7503005496890
                return;
            }

            if(helperTablaCompras.existenciaEnComprasPorDescripcion(descripcion,nameTablaCompras) ) {

                for (int i = 0; i < tablaCompras.getRowCount(); i++) {
                    String cantidadTablaProductosAgregados=tablaCompras.getValueAt(i, 1).toString();
                    String descripcioncompras=tablaCompras.getValueAt(i, 2).toString();
                    float descuentoTablaCompras=Float.parseFloat(tablaCompras.getValueAt(i, 5).toString());
                    float precioTablaProductos =Float.parseFloat(precio.toString());
                    float resultadoDescuento = precioTablaProductos-descuento;

                    if (descripcioncompras.equals(descripcion)!=true) continue;
                    /*---------------------VERIFICACION PARA ASIGNAR UN DESCUENTO CORRECTO AL PRODUCTO-----*/
                    if(descuento!=descuentoTablaCompras) {
                        JOptionPane.showMessageDialog(null, "El producto: "+descripcioncompras+" ya tiene un descuento de: "+descuentoTablaCompras+
                        " y usted esta reasignando un descuento de: "+descuento+" \n**el descuento debe ser el mismo para este producto**"+
                        "\n'si quiere reasignar un descuento a este producto quitelo del carrito de compras y vuelva a gregarlo con el descuento deseado'","Aviso!",JOptionPane.WARNING_MESSAGE );
                        jtextFieldDescuento.setText("0.0");
                        buscador.setText("");
                        return;
                    }
                    /*---------------------FIN VERIFICACION PARA ASIGNAR UN DESCUENTO CORRECTO AL PRODUCTO-----*/
                    float cantidadFinal=(Float.parseFloat(cantidadTablaProductosAgregados)+cantidadJtextfield);
                    float subTotal=((Float.parseFloat(cantidadTablaProductosAgregados)+cantidadJtextfield)*resultadoDescuento);

                    if(cantidadEntablaProductos<cantidadFinal){
                        JOptionPane.showMessageDialog(null,"PRODUCTO INSUFICIENTE","Mensaje de Error", JOptionPane.ERROR_MESSAGE);
                        VentanaPrincipal.codigoBarra.setText("");//mismo caso, limpia el campo de busqueda ya que seria molesto borrar todo el codigo de barras manualmente ejemplo: 7503005496890
                        return;
                    }

                    helperTablaCompras.actualizarRegistroTablaTCompras(cantidadFinal, subTotal, descripcion, nameTablaCompras);
                    conexionTableModel.datosTablaTcompras("",tablaCompras,nameTablaCompras);
                    total=helperTablaCompras.obtenerSumatoriaSubtotalTablaTcompras(nameTablaCompras);
                    buscador.setText("");
                }
                jtextFieldDescuento.setText("0.0");
                jtextFieldCantidadProductos.setText("1");
                labelTotal.setText("$ "+total);
                return;
            }

            float precioTablaProductos = Float.parseFloat(precio);
            float resultadoDescuento = precioTablaProductos-descuento;
            /*--------------------VALIDACIONES-----------------*/
            if(descuento<0) {
                JOptionPane.showMessageDialog(null, "El descuento no debe ser un numero negativo","Aviso!",JOptionPane.WARNING_MESSAGE);
                jtextFieldDescuento.setText("0.0");
                buscador.setText("");
                return;
            }
            if(descuento>precioTablaProductos) {
                JOptionPane.showMessageDialog(null, "El descuento no debe ser mayor al precio del producto","Aviso!",JOptionPane.WARNING_MESSAGE);
                jtextFieldDescuento.setText("0.0");
                buscador.setText("");
                return;
            }
            /*--------------------FIN DE VALIDACIONES-----------------*/
            String Sub_Total= String.valueOf(cantidadJtextfield*resultadoDescuento);
            helperTablaCompras.ingresarNuevoRegistroTablaTcompras(idProducto,String.valueOf(cantidadJtextfield), descripcion, resultadoDescuento, Sub_Total, jtextFieldDescuento.getText(),nameTablaCompras);
            conexionTableModel.datosTablaTcompras("", tablaCompras,nameTablaCompras);
            total=helperTablaCompras.obtenerSumatoriaSubtotalTablaTcompras(nameTablaCompras);
            buscador.setText("");				    					    
            jtextFieldDescuento.setText("0.0");
            jtextFieldCantidadProductos.setText("1");
            labelTotal.setText("$ "+total);
        }
        /*------------FIN DE BUSQUEDA DE CODIGO DE BARRAS--------------------------------------*/
        else if (MetodoBusqueda.equals("DESCRIPCION")) {
                conexionTableModel.mostrardatosProductos(buscador.getText(), tablaProductos);
        }else if(MetodoBusqueda.equals("CATEGORIA")) {
                conexionTableModel.mostrardatosCategorias(buscador.getText(), tablaProductos);
        }

    }
    
    
    
    
    
    
    
    public boolean busquedaPorCodigoBarra(String valor) {
        String sql = "SELECT ID,CODIGO_BARRA,CANTIDAD,DESCRIPCION,PRECIO_UNITARIO,COSTO_UNITARIO from productos where CODIGO_BARRA='"+valor+"' AND fk_id_state=1";
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if( rs.first() )        
                return true;        
            else
                return false; 

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;		
    }
    
    
}
