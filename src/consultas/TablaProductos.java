
package consultas;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import puntodeventa.VentanaPrincipal;
import puntodeventa.bd.ConexionBd;


/**
 *
 * @author jafeth888
 */
public class TablaProductos {
    ConexionBd cc= ConexionBd.obtenerInstancia();
    Connection cn= cc.conexion();
    public void mostrardatosProductos(String valor,JTable tablaProductos){
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
        establecerTamanioJTable();
        /*----------------------------fin de tamaño para columnas----------------------*/
        String sql="";
        if(valor.equals("")){
            //sql="SELECT CODIGO_BARRA,CANTIDAD,DESCRIPCION,PRECIO_UNITARIO FROM productos";
            sql="SELECT ID,CODIGO_BARRA,CANTIDAD,DESCRIPCION,PRECIO_UNITARIO,COSTO_UNITARIO,CATEGORIA FROM productos WHERE fk_id_state=1";
        }
        else{
            sql="SELECT ID,CODIGO_BARRA,CANTIDAD,DESCRIPCION,PRECIO_UNITARIO,COSTO_UNITARIO,CATEGORIA FROM productos WHERE descripcion like '%"+valor+"%' AND fk_id_state=1 ";
            //sql="SELECT CODIGO_BARRA,CANTIDAD,DESCRIPCION,PRECIO_UNITARIO FROM productos WHERE descripcion like '%"+valor+"%'";
        }

        Object []datos = new Object [7];
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
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
            tablaProductos.setModel(modelo);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally {

        }

    }
    
    public void establecerTamanioJTable() {
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
