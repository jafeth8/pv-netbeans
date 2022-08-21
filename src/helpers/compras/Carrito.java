
package helpers.compras;

import helpers.TableModel;
import helpers.sql.TablaCompras;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import puntodeventa.VentanaPrincipal;
import validaciones_comprobaciones.ValidacionesComprobaciones;

/**
 *
 * @author jafeth888
 */
public class Carrito {
    ValidacionesComprobaciones validacion =new ValidacionesComprobaciones();
    TablaCompras instanciaTablaCompras=new TablaCompras();
    TableModel tableModel = new TableModel();
    JFrame parent;

    public Carrito(JFrame parent) {
        this.parent=parent;
    }
   
    CantidadProductoUnidades instanciaCantidadProductoUnidades=new CantidadProductoUnidades(parent, true);
    
    private boolean validacionCantidad(String categoria,String cantidad){
        boolean cantidadValida=true;
        
        if(categoria.equals("unidades")){
            cantidadValida= validacion.isDouble(cantidad);
        }else{
            cantidadValida=validacion.isNumeric(cantidad);
        }
        
        if(cantidadValida==false) JOptionPane.showMessageDialog(null,"Ingresa un numero!");
        return cantidadValida;
        
    }
    
    private boolean validacionDescuento(String descuento,JTextField textFieldDescuento,float precioTablaProductos,float costo,String descripcion){
        boolean descuentoValido=true;
        descuentoValido=validacion.isDouble(descuento);
        if(descuentoValido==false){
            JOptionPane.showMessageDialog(null, "El valor del campo descuento debe ser un numero","Aviso!",JOptionPane.WARNING_MESSAGE);
            textFieldDescuento.setText("0.0");
            return descuentoValido;
        }
        if(Float.parseFloat(descuento)<0) {
            JOptionPane.showMessageDialog(null, "El descuento no debe ser un numero negativo","Aviso!",JOptionPane.WARNING_MESSAGE);
            textFieldDescuento.setText("0.0");
            return false;
        }
        if(Float.parseFloat(descuento)>precioTablaProductos) {
            JOptionPane.showMessageDialog(null, "El descuento no debe ser mayor al precio del producto","Aviso!",JOptionPane.WARNING_MESSAGE);
            textFieldDescuento.setText("0.0");
            return false;
            
        }
        float diferenciaPrecioDescuento=precioTablaProductos-Float.parseFloat(descuento);
        float descuentoPermitido=precioTablaProductos-costo;
        if(diferenciaPrecioDescuento<costo) {
            JOptionPane.showMessageDialog(null, "El producto: "+descripcion+" tiene un costo de : "+costo+
            " y el descuento resultante al precio del producto es de : "+diferenciaPrecioDescuento+" \nA este producto solo pude darle un descuento maximo de: "+descuentoPermitido+
            "\n'...'","Aviso!",JOptionPane.WARNING_MESSAGE );
            textFieldDescuento.setText("0.0");
            return false;
        }
        return descuentoValido;
    }
    
    private boolean validacionStock(float cantidadProductosIngresada,float cantidadEnTabla){
        boolean stockValido=true;
        if (cantidadProductosIngresada<=0) {
            JOptionPane.showMessageDialog(null,"EL DATO ES INCORRECTO","Mensaje de Error", JOptionPane.ERROR_MESSAGE);
            stockValido=false;
        }
	if (cantidadProductosIngresada>cantidadEnTabla) {
            JOptionPane.showMessageDialog(null,"PRODUCTO INSUFICIENTE","Mensaje de Error", JOptionPane.ERROR_MESSAGE);
            stockValido=false;
        }
        return stockValido;
    }
    
    private String ingresarCantidadProductos(String categoria,float precio,float descuento){
        String nProductos=null;
        
        if(categoria.equals("unidades")){
            CantidadProductoUnidades.precioProducto=precio;
            CantidadProductoUnidades.descuento=descuento;
            instanciaCantidadProductoUnidades.setLocationRelativeTo(null);
            instanciaCantidadProductoUnidades.setVisible(true);
            nProductos=CantidadProductoUnidades.valueCantidadProducto;
        }else{
            do{                
                nProductos=JOptionPane.showInputDialog("INGRESA LA CANTIDAD DE PRODUCTOS");
            }while (nProductos.equals("") || validacionCantidad(categoria,nProductos)==false);
        }
        
        return nProductos;
    }
    
    public void addCarrito(JTable tablaProductos,JTable tablaCompras,JTextField jtextFieldDescuento,JTextField buscador,JLabel labelTotal,String nameTablaCompras){
        int fila = tablaProductos.getSelectedRow();
        if(fila<0){
            JOptionPane.showMessageDialog(null,"Seleccione un registro");
            return;
        }
        String Nproductos,cantidad,descripcion,precio;
        float cantidadProductosIngresada;
        int idProducto=Integer.parseInt(tablaProductos.getValueAt(fila, 0).toString());
        float total=0,subTotal;
        
        String categoria=tablaProductos.getValueAt(fila, 6).toString();
        
        float descuento=Float.parseFloat((jtextFieldDescuento.getText().toString()));
        cantidad=tablaProductos.getValueAt(fila, 2).toString();
        descripcion = tablaProductos.getValueAt(fila, 3).toString();
        precio=tablaProductos.getValueAt(fila, 4).toString();
        float costo=Float.parseFloat(tablaProductos.getValueAt(fila, 5).toString());
        float cantidadEnTabla = Float.parseFloat(cantidad);
        
        Nproductos=ingresarCantidadProductos(categoria,Float.parseFloat(precio),descuento);
        if(Nproductos==null) return;//la variable sera nula si se cierran las ventanas jDialog en el metodo 'ingresarCantidadProductos'
        cantidadProductosIngresada=Float.parseFloat(Nproductos);
        
        if(validacionDescuento(jtextFieldDescuento.getText(),jtextFieldDescuento,Float.parseFloat(precio),costo,descripcion)==false) return;

        if(validacionStock(cantidadProductosIngresada, cantidadEnTabla)==false)return;
        //------------------------------------------------------------------------------------------------------
        if(instanciaTablaCompras.existenciaEnComprasPorDescripcion(descripcion, nameTablaCompras)){
            for (int j = 0; j < tablaCompras.getRowCount(); j++) {
                String cantidadTablaCompras=tablaCompras.getValueAt(j, 1).toString();
                String descripcioncompras=tablaCompras.getValueAt(j, 2).toString();
                float descuentoTablaCompras=Float.parseFloat(tablaCompras.getValueAt(j, 5).toString());
                float precioTablaProductos = Float.parseFloat(precio.toString());//------------
                float resultadoDescuento = precioTablaProductos-descuento;//----------
                float cantidadFinal;
                if (descripcioncompras.equals(descripcion)) {
                    /*---------------------VERIFICACION PARA ASIGNAR UN DESCUENTO CORRECTO AL PRODUCTO-----*/
                    if(descuento!=descuentoTablaCompras) {
                        JOptionPane.showMessageDialog(null, "El producto: "+descripcioncompras+" ya tiene un descuento de: "+descuentoTablaCompras+
                        " y usted esta reasignando un descuento de: "+descuento+" \n**el descuento debe ser el mismo para este producto**"+
                        "\n'si quiere reasignar un descuento a este producto quitelo del carrito de compras y vuelva a gregarlo con el descuento deseado'","Aviso!",JOptionPane.WARNING_MESSAGE );
                        jtextFieldDescuento.setText("0.0");
                        return;
                    }
                    /*---------------------FIN VERIFICACION PARA ASIGNAR UN DESCUENTO CORRECTO AL PRODUCTO-----*/
                    // x= cantidad a=sub_total
                    cantidadFinal=(Float.parseFloat(cantidadTablaCompras)+cantidadProductosIngresada);
                    subTotal=(cantidadFinal*resultadoDescuento);
                    if (cantidadEnTabla<cantidadFinal) {
                        JOptionPane.showMessageDialog(null,"PRODUCTO INSUFICIENTE","Mensaje de Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }else{
                        instanciaTablaCompras.actualizarRegistroTablaTCompras(cantidadFinal, subTotal, descripcion, nameTablaCompras);
                        tableModel.datosTablaTcompras("", tablaCompras,nameTablaCompras);
                        //calcula = (resultadoDescuento*cantidadProductosIngresada);
                        total=instanciaTablaCompras.obtenerSumatoriaSubtotalTablaTcompras(nameTablaCompras);
                        buscador.setText("");
                    }
                }
            }
            jtextFieldDescuento.setText("0.0");
        }else{
            float precioTablaProductos =Float.parseFloat(precio.toString());//------------
            float resultadoDescuento = precioTablaProductos-descuento;//----------
            subTotal=cantidadProductosIngresada*resultadoDescuento;
            String stringPrecioUnitario = String.valueOf(resultadoDescuento);
            instanciaTablaCompras.ingresarNuevoRegistroTablaTcompras(idProducto,String.valueOf(cantidadProductosIngresada), 
                descripcion, stringPrecioUnitario,String.valueOf(subTotal),jtextFieldDescuento.getText(), nameTablaCompras);
            tableModel.datosTablaTcompras("", tablaCompras,nameTablaCompras);
            total=instanciaTablaCompras.obtenerSumatoriaSubtotalTablaTcompras(nameTablaCompras);
            buscador.setText("");
            jtextFieldDescuento.setText("0.0");

        }
        //-------------------------------------------------------------------------------------------------------
        labelTotal.setText(""+total);
    }
    /*por dinero
    cantidadProductos=dineroFloat/precioDouble;
    */
}
