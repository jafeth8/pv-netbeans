
package helpers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import puntodeventa.VentanaPrincipal;
import puntodeventa.bd.ConexionBd;

/**
 *
 * @author jafeth888
 */
public class TableModel {

    public TableModel() {
        
    }
    
    
    //este metodo no cierra la conexion de tipo Connection debido a que esta dentro de una transaccion u otro metodo que esta 
    //utilizando dicha conexion, esta debe de cerrarse al final del proceso de la transaccion u metodo que este utilizando
    //este metodo mostrarDatosProductos y no dentro de este ya que estaria cerrando la conexion premamaturamente lo cual causaria errores.
    public void mostrarDatosProductos(String valor,JTable tablaProductos){
        
        ConexionBd cc= ConexionBd.obtenerInstancia();
        Connection cn= cc.conexion();
		  
        DefaultTableModel modelo= new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                // TODO Auto-generated method stub
                return false;
            }
        };
        modelo.addColumn("ID");
        modelo.addColumn("CODIGO_BARRA");
        modelo.addColumn("CANTIDAD");
        modelo.addColumn("DESCRIPCION");
        modelo.addColumn("PRECIO UNITARIO");
        modelo.addColumn("COSTO PRODUCTO");
        modelo.addColumn("CATEGORIA");

        tablaProductos.setModel(modelo);
        /*-----------------------------tamaño de columnas*------------------------------*/
        establecerTamanioTablaProductos(tablaProductos);
        /*----------------------------fin de tamaño para columnas----------------------*/
        String sql="";
        if(valor.equals(""))
        {
            sql="SELECT ID,CODIGO_BARRA,CANTIDAD,DESCRIPCION,PRECIO_UNITARIO,COSTO_UNITARIO,CATEGORIA FROM productos WHERE fk_id_state=1";
        }
        else{
            sql="SELECT ID,CODIGO_BARRA,CANTIDAD,DESCRIPCION,PRECIO_UNITARIO,COSTO_UNITARIO,CATEGORIA FROM productos WHERE descripcion like '%"+valor+"%' AND fk_id_state=1 ";
        }

        Object []datos = new Object [7];
        Statement st = null;
        ResultSet rs = null;
        try {
            st = cn.createStatement();
            rs = st.executeQuery(sql);

            while(rs.next()){

                datos[0]=rs.getInt(1);
                datos[1]=rs.getString(2);
                datos[3]=rs.getString(4);
                datos[4]=rs.getDouble(5);
                datos[5]=rs.getDouble(6);
                datos[6]=rs.getString(7);
                if(datos[6].equals("unidades")) {
                    datos[2]=rs.getDouble(3);
                }else {
                    datos[2]=rs.getInt(3);
                }
                modelo.addRow(datos);
            }
            if(rs.first()!=true) {
                //JOptionPane.showMessageDialog(null,"No se encontraron resultados");
                DefaultTableModel modeloVacio= new DefaultTableModel();
                modeloVacio.addColumn("NO SE ENCONTRARON RESULTADOS");
                String mensaje[]=new String[1];
                mensaje[0]="sin resultados en la busqueda";
                modeloVacio.addRow(mensaje);
                tablaProductos.setModel(modeloVacio);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }finally{
            try {
                if(st!=null)st.close();
                if(rs!=null)rs.close();      
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, ex.getMessage(),"Error Close conexion TableModel.mostrarDatosProductos",JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
    
    public void mostrardatosCategorias(String valor,JTable tablaProductos){
        DefaultTableModel modelo= new DefaultTableModel() {
          @Override
          public boolean isCellEditable(int row, int column) {
              // TODO Auto-generated method stub
              return false;
          }
        };
        modelo.addColumn("ID");
        modelo.addColumn("CODIGO_BARRA");
        modelo.addColumn("CANTIDAD");
        modelo.addColumn("DESCRIPCION");
        modelo.addColumn("PRECIO UNITARIO");
        modelo.addColumn("COSTO PRODUCTO");
        modelo.addColumn("CATEGORIA");
        tablaProductos.setModel(modelo);
        /*-----------------------------tamaño de columnas*------------------------------*/
        establecerTamanioTablaProductos(tablaProductos);
        /*----------------------------fin de tamaño para columnas----------------------*/
        ConexionBd cc= ConexionBd.obtenerInstancia();
        Connection cn= cc.conexion();
        String sql="";
        if(valor.equals("")){
          sql="SELECT ID,CODIGO_BARRA,CANTIDAD,DESCRIPCION,PRECIO_UNITARIO,COSTO_UNITARIO,CATEGORIA FROM productos WHERE fk_id_state=1";
        }
        else{
          sql="SELECT ID,CODIGO_BARRA,CANTIDAD,DESCRIPCION,PRECIO_UNITARIO,COSTO_UNITARIO,CATEGORIA FROM productos WHERE categoria like '%"+valor+"%' AND fk_id_state=1 ";
        }

        Object []datos = new Object [7];
        Statement st = null;
        ResultSet rs = null;
        try {
            st = cn.createStatement();
            rs = st.executeQuery(sql);
            while(rs.next()){
                datos[0]=rs.getInt(1);
                datos[1]=rs.getString(2);

                datos[3]=rs.getString(4);
                datos[4]=rs.getDouble(5);
                datos[5]=rs.getDouble(6);
                datos[6]=rs.getString(7);
                if(datos[6].equals("unidades")) {
                    datos[2]=rs.getDouble(3);
                }else {
                    datos[2]=rs.getInt(3);
                }
                modelo.addRow(datos);
            }
            if(rs.first()!=true) {
                JOptionPane.showMessageDialog(null,"No se encontraron resultados");
            }
        }catch (SQLException e) {
            JOptionPane.showMessageDialog(null,e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }finally {
            try {
                if(st!=null)st.close();
                if(rs!=null)rs.close();
                if(cn!=null)cn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, ex.getMessage(),"Error Close conexion TableModel.mostrarDatosCategorias",JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
    
    public void mostrarProductoCodigoDeBarra(String valorBuscador,JTable tablaProductos) {
        DefaultTableModel modelo= new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                // TODO Auto-generated method stub
                return false;
            }
        };
        modelo.addColumn("ID");
        modelo.addColumn("CODIGO_BARRA");
        modelo.addColumn("CANTIDAD");
        modelo.addColumn("DESCRIPCION");
        modelo.addColumn("PRECIO UNITARIO");
        modelo.addColumn("COSTO PRODUCTO");
        modelo.addColumn("CATEGORIA");
        tablaProductos.setModel(modelo);
        /*-----------------------------tamaño de columnas*------------------------------*/
        establecerTamanioTablaProductos(tablaProductos);
        /*----------------------------fin de tamaño para columnas----------------------*/
        ConexionBd cc= ConexionBd.obtenerInstancia();
        Connection cn= cc.conexion();
        String sql="";
        if(valorBuscador.equals("")){
            //sql="SELECT CODIGO_BARRA,CANTIDAD,DESCRIPCION,PRECIO_UNITARIO FROM productos";
        }
        else{
            //sql="SELECT ID,CODIGO_BARRA,CANTIDAD,DESCRIPCION,PRECIO_UNITARIO,COSTO_UNITARIO,CATEGORIA FROM productos WHERE categoria like '%"+valor+"%' AND fk_id_state=1 ";
            //sql="SELECT CODIGO_BARRA,CANTIDAD,DESCRIPCION,PRECIO_UNITARIO FROM productos WHERE descripcion like '%"+valor+"%'";
            sql="SELECT ID,CODIGO_BARRA,CANTIDAD,DESCRIPCION,PRECIO_UNITARIO,COSTO_UNITARIO,CATEGORIA from productos where CODIGO_BARRA='"+valorBuscador+"' AND fk_id_state=1";
        }

        Object []datos = new Object [7];
        Statement st =null;
        ResultSet rs =null;
        try {
            st = cn.createStatement();
            rs = st.executeQuery(sql);
            while(rs.next()){

                datos[0]=rs.getInt(1);
                datos[1]=rs.getDouble(2);

                datos[3]=rs.getString(4);
                datos[4]=rs.getDouble(5);
                datos[5]=rs.getDouble(6);
                datos[6]=rs.getString(7);
                if(datos[6].equals("unidades")) {
                    datos[2]=rs.getDouble(3);
                }else {
                    datos[2]=rs.getInt(3);
                }
                modelo.addRow(datos);
            }
            tablaProductos.setModel(modelo);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally {
            try {
                if(st!=null)st.close();
                if(rs!=null)rs.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, ex.getMessage(),"Error Close conexion TableModel.mostrarProductoCodigoBarra",JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
    
    public void datosTablaTcompras(String valor,JTable table,String nameTablaTcompras){
        DefaultTableModel modelo= new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                // TODO Auto-generated method stub
                return false;
            }
        };
        modelo.addColumn("ID");
        modelo.addColumn("CANTIDAD");
        modelo.addColumn("DESCRIPCION");
        modelo.addColumn("PRECIO UNITARIO");
        modelo.addColumn("SUB_TOTAL");
        modelo.addColumn("DESCUENTO");

        table.setModel(modelo);
        /*-----------------------------tamaño de columnas*------------------------------*/
        TableColumn columnaId=table.getColumn("ID");
        columnaId.setMinWidth(30);
        columnaId.setPreferredWidth(30);
        

        TableColumn columnaCantidad=table.getColumn("CANTIDAD");
        columnaCantidad.setMinWidth(60);
        columnaCantidad.setPreferredWidth(60);
        
        TableColumn columnaDescripcion=table.getColumn("DESCRIPCION");
        columnaDescripcion.setMinWidth(240);
        columnaDescripcion.setPreferredWidth(240);

        TableColumn columnaPrecioUnitario=table.getColumn("PRECIO UNITARIO");
        columnaPrecioUnitario.setMinWidth(60);
        columnaPrecioUnitario.setPreferredWidth(60);
        

        TableColumn columnaSubTotal=table.getColumn("SUB_TOTAL");
        columnaSubTotal.setMinWidth(60);
        columnaSubTotal.setPreferredWidth(60);
        

        TableColumn columnaDescuento=table.getColumn("DESCUENTO");
        columnaDescuento.setMinWidth(60);
        columnaDescuento.setPreferredWidth(60);
        


        /*----------------------------fin de tamaño para columnas----------------------*/
        
        ConexionBd cc= ConexionBd.obtenerInstancia();
        Connection cn= cc.conexion();
        
        String sql="";
        if(valor.equals(""))
        {
            sql="select * from "+nameTablaTcompras+"";
        }
        else{
            //TODO: query adicional
        }

        Object []datos = new Object [6];
        Statement st = null;
        ResultSet rs = null;
        try {
            st = cn.createStatement();
            rs = st.executeQuery(sql);
            while(rs.next()){

                datos[0]=rs.getInt(1);
                datos[1]=rs.getDouble(2);
                datos[2]=rs.getString(3);
                datos[3]=rs.getDouble(4);
                datos[4]=rs.getDouble(5);
                datos[5]=rs.getDouble(6);

                modelo.addRow(datos);
            }
            table.setModel(modelo);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally {
            try {
                if(st!=null)st.close();
                if(rs!=null)rs.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, ex.getMessage(),"Error Close conexion TableModel.datosTablaTcompras",JOptionPane.INFORMATION_MESSAGE);
            }
        } 
    }
    
    public void establecerTamanioTablaProductos(JTable tablaProductos) {
        TableColumn columnaId=tablaProductos.getColumn("ID");
        columnaId.setMinWidth(60);
        columnaId.setPreferredWidth(60);
        //columnaId.setMaxWidth(65);

        TableColumn columnaCodigoBarra=tablaProductos.getColumn("CODIGO_BARRA");
        columnaCodigoBarra.setMinWidth(150);
        columnaCodigoBarra.setPreferredWidth(150);
        //columnaCodigoBarra.setMaxWidth(150);

        TableColumn columnaCantidad=tablaProductos.getColumn("CANTIDAD");
        columnaCantidad.setMinWidth(60);
        columnaCantidad.setPreferredWidth(60);
        //columnaCantidad.setMaxWidth(65);

        TableColumn columnaDescripcion=tablaProductos.getColumn("DESCRIPCION");
        columnaDescripcion.setMinWidth(600);
        columnaDescripcion.setPreferredWidth(600);
        //columnaDescripcion.setMaxWidth(605);

        TableColumn columnaPrecioUnitario=tablaProductos.getColumn("PRECIO UNITARIO");
        columnaPrecioUnitario.setMinWidth(120);
        columnaPrecioUnitario.setPreferredWidth(120);
        //columnaPrecioUnitario.setMaxWidth(125);

        TableColumn columnaCostoProducto=tablaProductos.getColumn("COSTO PRODUCTO");
        columnaCostoProducto.setMinWidth(120);
        columnaCostoProducto.setPreferredWidth(120);
        //columnaCostoProducto.setMaxWidth(125);

        TableColumn columnaCategoria=tablaProductos.getColumn("CATEGORIA");
        columnaCategoria.setMinWidth(140);
        columnaCategoria.setPreferredWidth(140);
        //columnaCategoria.setMaxWidth(145);	
    }
}
