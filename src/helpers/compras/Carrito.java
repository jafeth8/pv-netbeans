
package helpers.compras;

import helpers.MetodosImprimir;
import helpers.TableModel;
import helpers.sql.Productos;
import helpers.sql.TablaCompras;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import puntodeventa.bd.ConexionBd;
import puntodeventa.bd.Ruta;
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
    Productos producto=new Productos();
    ConexionBd cc= ConexionBd.obtenerInstancia();
    Connection cn= cc.conexion();

    public Carrito(JFrame parent) {
        this.parent=parent;
    }

    public Carrito() {
    
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
        /*por dinero
            cantidadProductos=dineroFloat/precioDouble;
        */
        if(categoria.equals("unidades")){
            CantidadProductoUnidades.precioProducto=precio;
            CantidadProductoUnidades.descuento=descuento;
            instanciaCantidadProductoUnidades.setLocationRelativeTo(null);
            instanciaCantidadProductoUnidades.setVisible(true);
            nProductos=CantidadProductoUnidades.valueCantidadProducto;
        }else{
            do{                
                nProductos=JOptionPane.showInputDialog("INGRESA LA CANTIDAD DE PRODUCTOS");
                if(nProductos==null)break;//evita lanzar una excepcion -->> null.equals etc
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
    
    /**
     @param pagar true si el agregado de productos sera a compras, false si el agregado de productos sera a credito 
     **/
    public void pagar(JTable tablaProductos,JTable tablaCompras,JLabel labelTotal,JLabel labelCambio,JLabel usuarioLabel,boolean pagar){
        //TODO: revisar que transacciones funcionen correctamente, depues borrar este comentario y hacer un commit especificando la tarea realizada. 
        float total;
	float pagoIngresado;
        total= instanciaTablaCompras.obtenerSumatoriaSubtotalTablaTcompras(Ruta.nametablaTcompras);
        String inputPagar;
        if (total==0){
            JOptionPane.showMessageDialog(null,"DEBES GENERAR UNA VENTA PARA PAGAR","Mensaje de Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        inputPagar=JOptionPane.showInputDialog("Ingrese con cuanto va a pagar");
        if(inputPagar==null)return;
        pagoIngresado=Float.parseFloat(inputPagar);
        if (total>pagoIngresado){
            JOptionPane.showMessageDialog(null,"PAGO INSUFICIENTE","Mensaje de Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        double cambioDelCliente;
        cambioDelCliente=pagoIngresado-total;
        int idProductoCompras;
        String valorCantidadCompras;
        String valorDescripcionCompras;			
        String descripcionProductos;
        String valorCantidadProductos = null;
        
        try {
            cn.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,e.getMessage(),"Error al desactivar auto-commit:helpers.compras.Carrito.pagar()",JOptionPane.ERROR_MESSAGE);
        }
        PreparedStatement psActualizarCantidad=null,psInsertVentas=null,psInsertDetalleVentas=null,psTruncarTabla=null;
        ResultSet resultado=null;
        int idVenta=0;
        try {
            for (int j = 0; j < tablaCompras.getRowCount(); j++) {			
                idProductoCompras = Integer.parseInt(tablaCompras.getValueAt(j,0).toString());
                valorCantidadCompras=tablaCompras.getValueAt(j, 1).toString();
                valorDescripcionCompras=tablaCompras.getValueAt(j,2).toString();

                tableModel.mostrarDatosProductos("",tablaProductos);//se deben mostrar todos los productos para comparar las descripciones entre las dos tablas (importante no omitir este metodo)
                for (int i = 0; i < tablaProductos.getRowCount(); i++) {
                    descripcionProductos=tablaProductos.getValueAt(i,3).toString();
                    if (valorDescripcionCompras.equals(descripcionProductos)) {
                        valorCantidadProductos=tablaProductos.getValueAt(i,2).toString();
                        float cantidadCompras=Float.parseFloat(valorCantidadCompras);
                        float cantidadProductos=Float.parseFloat(valorCantidadProductos);
                        //actualizamos la cantidad del producto
                        float cantidadRestante=cantidadProductos-cantidadCompras;
                        psActualizarCantidad = cn.prepareStatement("UPDATE productos SET cantidad='"+cantidadRestante+"' WHERE ID='"+idProductoCompras+"'");
			psActualizarCantidad.executeUpdate();
                        //operacion.actualizarCantidadProductos(idProductoCompras, cantidadProductos, cantidadCompras);//se establece la existencia de cantidad en el producto
                    }
                }							
            }
            /*----------------------: REGISTRAR HISTORIAL DE VENTAS-----------------*/
            String fechaVenta=LocalDate.now().toString();
            psInsertVentas = cn.prepareStatement("INSERT INTO ventas"
            + "(total_venta,fecha_venta,pago_del_cliente,cambio_del_cliente,tipo_venta) VALUES (?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
            psInsertVentas.setDouble(1, total);
            psInsertVentas.setString(2, fechaVenta);
            psInsertVentas.setDouble(3, pagoIngresado);
            psInsertVentas.setDouble(4, cambioDelCliente);
            psInsertVentas.setString(5, "al contado");
            psInsertVentas.executeUpdate();
            
            resultado=psInsertVentas.getGeneratedKeys();

            if(resultado.next()){
                idVenta=resultado.getInt(1);
            }
            
            for (int i = 0; i < tablaCompras.getRowCount(); i++) {
						
                String idProducto=tablaCompras.getValueAt(i,0).toString();
                String precioUnitario=tablaCompras.getValueAt(i,3).toString();
                String cantidad=tablaCompras.getValueAt(i, 1).toString();
                String descuentoString=tablaCompras.getValueAt(i, 5).toString();

                int idProductoInteger=Integer.parseInt(idProducto);
                float costo_unitario=producto.obtenerCostoProductoTablaProducto(idProductoInteger);
                float precioUnitarioFloat=Float.parseFloat(precioUnitario);
                float cantidadFloat=Float.parseFloat(cantidad);
                double descuento=Double.parseDouble(descuentoString);

                //insetar detalleVentas
                psInsertDetalleVentas = cn.prepareStatement("INSERT INTO detalle_ventas"
    		+ "(fk_id_venta,fk_id_producto,costo_unitario,precio_unitario,cantidad,descuento) VALUES (?,?,?,?,?,?)");
                psInsertDetalleVentas.setInt(1, idVenta);
                psInsertDetalleVentas.setInt(2, idProductoInteger);
                psInsertDetalleVentas.setFloat(3, costo_unitario);
                psInsertDetalleVentas.setDouble(4, precioUnitarioFloat);
                psInsertDetalleVentas.setDouble(5, cantidadFloat);
                psInsertDetalleVentas.setDouble(6, descuento);
                psInsertDetalleVentas.executeUpdate();
            }
            
            if(pagar) labelCambio.setText("$ "+cambioDelCliente+"");
            total=0;
            tableModel.mostrarDatosProductos("",tablaProductos);
            
            if(pagar){
                int pre=JOptionPane.showConfirmDialog(null, "DESEA IMPRIMIR TICKET ?","",JOptionPane.YES_NO_OPTION);
                if(pre==0){
                    MetodosImprimir instanciaImprimir= new MetodosImprimir();
                    instanciaImprimir.imprimir(inputPagar, tablaCompras, labelTotal,labelCambio, usuarioLabel);
                }else{
                    JOptionPane.showMessageDialog(null, "GRACIAS POR SU COMPRA"); 
                }
            }
            
            //truncar tablaCompras
            psTruncarTabla= cn.prepareStatement("TRUNCATE "+Ruta.nametablaTcompras);
	    psTruncarTabla.executeUpdate();
            
            //mostrar estructura tablaCompras
            tableModel.datosTablaTcompras("", tablaCompras,"tcompras");
            
            labelTotal.setText("0.0");
            labelCambio.setText("0.0");
            cn.commit();
        } catch (SQLException e) {
            try {
                cn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null,e.getMessage(),"helpers.compras.Carrito.pagar(): Error Rollback",JOptionPane.ERROR_MESSAGE);
            }
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"intente nuevamente"+e.getMessage(),"Error al pagar:",JOptionPane.ERROR_MESSAGE);
        }finally{
            try {
                cn.setAutoCommit(true);
                if(psActualizarCantidad!=null)psActualizarCantidad.close();
                if(psInsertVentas!=null)psInsertVentas.close();
                if(psInsertDetalleVentas!=null)psInsertDetalleVentas.close();
                if(psTruncarTabla!=null)psTruncarTabla.close();
                if(resultado!=null)resultado.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, ex.getMessage(),"helpers.compras.Carrito.pagar(): no se establecio"
                    + "setAutoCommit() en true o no se pudieron cerrar conexiones",JOptionPane.INFORMATION_MESSAGE);
            }
        }    
    }
    
}
