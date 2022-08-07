
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
    ConexionBd cc= ConexionBd.obtenerInstancia();
    Connection cn= cc.conexion();
    public TableModel() {
        
    }
    
    public void mostrarDatosProductos(String valor,JTable tablaProductos){
		  
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
        establecerTamanioTablaProductos();
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
        establecerTamanioTablaProductos();
        /*----------------------------fin de tamaño para columnas----------------------*/
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
        establecerTamanioTablaProductos();
        /*----------------------------fin de tamaño para columnas----------------------*/
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
            /*tamanio de las celdas*/
            establecerTamanioTablaProductos();
            /*fin del tamanio de las celdas*/
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
        columnaId.setMinWidth(60);
        columnaId.setPreferredWidth(60);
        columnaId.setMaxWidth(65);

        TableColumn columnaCantidad=table.getColumn("CANTIDAD");
        columnaCantidad.setMinWidth(100);
        columnaCantidad.setPreferredWidth(100);
        columnaCantidad.setMaxWidth(100);

        TableColumn columnaPrecioUnitario=table.getColumn("PRECIO UNITARIO");
        columnaPrecioUnitario.setMinWidth(200);
        columnaPrecioUnitario.setPreferredWidth(200);
        columnaPrecioUnitario.setMaxWidth(200);

        TableColumn columnaSubTotal=table.getColumn("SUB_TOTAL");
        columnaSubTotal.setMinWidth(200);
        columnaSubTotal.setPreferredWidth(200);
        columnaSubTotal.setMaxWidth(200);

        TableColumn columnaDescuento=table.getColumn("DESCUENTO");
        columnaDescuento.setMinWidth(80);
        columnaDescuento.setPreferredWidth(80);
        columnaDescuento.setMaxWidth(85);


        /*----------------------------fin de tamaño para columnas----------------------*/
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
    
    public void establecerTamanioTablaProductos() {
        TableColumn columnaId=VentanaPrincipal.jTablaProductos.getColumn("ID");
        columnaId.setMinWidth(60);
        columnaId.setPreferredWidth(60);
        columnaId.setMaxWidth(65);

        TableColumn columnaCodigoBarra=VentanaPrincipal.jTablaProductos.getColumn("CODIGO_BARRA");
        columnaCodigoBarra.setMinWidth(150);
        columnaCodigoBarra.setPreferredWidth(150);
        columnaCodigoBarra.setMaxWidth(150);

        TableColumn columnaCantidad=VentanaPrincipal.jTablaProductos.getColumn("CANTIDAD");
        columnaCantidad.setMinWidth(60);
        columnaCantidad.setPreferredWidth(60);
        columnaCantidad.setMaxWidth(65);

        TableColumn columnaDescripcion=VentanaPrincipal.jTablaProductos.getColumn("DESCRIPCION");
        columnaDescripcion.setMinWidth(600);
        columnaDescripcion.setPreferredWidth(600);
        columnaDescripcion.setMaxWidth(605);

        TableColumn columnaPrecioUnitario=VentanaPrincipal.jTablaProductos.getColumn("PRECIO UNITARIO");
        columnaPrecioUnitario.setMinWidth(120);
        columnaPrecioUnitario.setPreferredWidth(120);
        columnaPrecioUnitario.setMaxWidth(125);

        TableColumn columnaCostoProducto=VentanaPrincipal.jTablaProductos.getColumn("COSTO PRODUCTO");
        columnaCostoProducto.setMinWidth(120);
        columnaCostoProducto.setPreferredWidth(120);
        columnaCostoProducto.setMaxWidth(125);

        TableColumn columnaCategoria=VentanaPrincipal.jTablaProductos.getColumn("CATEGORIA");
        columnaCategoria.setMinWidth(140);
        columnaCategoria.setPreferredWidth(140);
        columnaCategoria.setMaxWidth(145);	
    }
}
