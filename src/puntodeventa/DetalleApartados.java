/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package puntodeventa;

import helpers.TableModel;
import helpers.sql.Productos;
import helpers.sql.TablaApartados;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import puntodeventa.bd.ConexionBd;

/**
 *
 * @author jafeth8
 */
public class DetalleApartados extends javax.swing.JDialog {
    public static int idApartado=0;
    ConexionBd cc= ConexionBd.obtenerInstancia();
    Connection cn= cc.conexion();
    TableModel instanciaTableModel=new TableModel();
    Productos instanciaTablaProductos=new Productos();
    /**
     * Creates new form DetalleApartados
     */
    public DetalleApartados(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        mostrarApartado(idApartado);
        mostrarDetalleApartado(idApartado);
    }
    
    public void mostrarApartado(int id_apartado){
        DefaultTableModel modelo= new DefaultTableModel();
        modelo.addColumn("Id Apartado");
        modelo.addColumn("Id Cliente");
        modelo.addColumn("Nombre");
        modelo.addColumn("Total Compra");
        modelo.addColumn("Fecha Apartado");
        modelo.addColumn("Total abono");
        modelo.addColumn("Deuda");
        //modelo.addColumn("Tipo de venta");
        tablaRegistroApartado.setModel(modelo);
        String sql="";

        sql="SELECT id_apartado,fk_id_cliente,clientes.nombre,total,fecha_de_apartado,total_abono,deuda "
        + "FROM apartados JOIN clientes ON apartados.fk_id_cliente=clientes.id_cliente WHERE id_apartado ="+id_apartado+"";

        String []datos = new String [7];
        Statement st = null;
        ResultSet rs = null;
        try {
            st = cn.createStatement();
            rs = st.executeQuery(sql);
            while(rs.next()){
                datos[0]=rs.getString(1);

                datos[1]=rs.getString(2);
                datos[2]=rs.getString(3);
                datos[3]=rs.getString(4);
                datos[4]=rs.getString(5);
                datos[5]=rs.getString(6);
                datos[6]=rs.getString(7);
                //datos[5]=rs.getString(6);
                modelo.addRow(datos);
            }
            tablaRegistroApartado.setModel(modelo);
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al mostrar registro apartado: "+ex.getMessage(),"Error en: puntodeventa.DetalleApartados.mostrarApartado()", JOptionPane.ERROR_MESSAGE);
        }finally{
            try {
                if(st!=null)st.close();
                if(rs!=null)rs.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error al cerrar conexion: "+ex.getMessage(),"Error de conexion: puntodeventa.DetalleApartados.mostrarApartado()", JOptionPane.WARNING_MESSAGE);

            }
        }
    }
    
    public void mostrarDetalleApartado(int id){
        DefaultTableModel modelo= new DefaultTableModel();
        modelo.addColumn("Id Apartado");
        modelo.addColumn("Id Producto");
        modelo.addColumn("Descripcion");
        modelo.addColumn("Precio unitario");
        modelo.addColumn("Cantidad");
        modelo.addColumn("id_detalle_apartados");
        modelo.addColumn("Fecha registro");
        modelo.addColumn("Descuento");
        tablaDetalleApartado.setModel(modelo);

        String sql="";

        sql="SELECT fk_id_apartado,fk_id_producto,productos.DESCRIPCION,detalle_apartados.precio_unitario,detalle_apartados.cantidad,id_detalle_apartados,fecha_registro,detalle_apartados.descuento FROM "
        + "detalle_apartados JOIN productos ON detalle_apartados.fk_id_producto=productos.ID WHERE fk_id_apartado="+id+"";

        String []datos = new String [8];
        Statement st = null;
        ResultSet rs = null;
        try {
            st = cn.createStatement();
            rs = st.executeQuery(sql);
            while(rs.next()){
                datos[0]=rs.getString(1);

                datos[1]=rs.getString(2);
                datos[2]=rs.getString(3);
                datos[3]=rs.getString(4);
                datos[4]=rs.getString(5);
                datos[5]=""+rs.getInt(6);
                datos[6]=rs.getString(7);
                datos[7]=""+rs.getDouble(8);
                modelo.addRow(datos);
            }
            tablaDetalleApartado.setModel(modelo);
            /*ocultamos la columna id detalle apartados*/
            tablaDetalleApartado.getColumnModel().getColumn(5).setMaxWidth(0);
            tablaDetalleApartado.getColumnModel().getColumn(5).setMinWidth(0);
            tablaDetalleApartado.getColumnModel().getColumn(5).setPreferredWidth(0);
            /*FIN DE ocultar la columna id detalle apartados*/
        }catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al mostrar detalle apartado: "+ex.getMessage(),"Error en: puntodeventa.DetalleApartados.mostrarDetalleApartado()", JOptionPane.ERROR_MESSAGE);
        }finally{
            try {
                if(st!=null)st.close();
                if(rs!=null)rs.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error al cerrar conexion: "+ex.getMessage(),"Error de conexion: puntodeventa.DetalleApartados.mostrarDetalleApartado()", JOptionPane.WARNING_MESSAGE);
            }
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu = new javax.swing.JPopupMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaRegistroApartado = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaDetalleApartado = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        btnCancelarCredito = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        jMenuItem1.setText("Quitar del apartado");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jPopupMenu.add(jMenuItem1);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("APARTADO:");

        tablaRegistroApartado.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tablaRegistroApartado);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setText("DETALLES DEL APARTADO:");

        tablaDetalleApartado.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tablaDetalleApartado.setComponentPopupMenu(jPopupMenu);
        jScrollPane2.setViewportView(tablaDetalleApartado);

        btnCancelarCredito.setText("Cancelar todo el credito");
        btnCancelarCredito.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarCreditoActionPerformed(evt);
            }
        });

        jButton2.setText("Añadir productos");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnCancelarCredito)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton2)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelarCredito)
                    .addComponent(jButton2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 855, Short.MAX_VALUE)
                    .addComponent(jScrollPane2)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 386, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelarCreditoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarCreditoActionPerformed
        
        int confirmar;
        int idProducto;
        float cantidadProductosApartados;
        int idApartado=Integer.parseInt(tablaRegistroApartado.getValueAt(0, 0).toString());
	System.out.println("numero de filas " +tablaDetalleApartado.getRowCount());
        confirmar=JOptionPane.showConfirmDialog(this,"Esta seguro de cancelar este credito y todos sus registros?","Confirmar",JOptionPane.YES_NO_OPTION);
        if(confirmar==0){
            PreparedStatement psActualizar=null,psEliminar=null;
            try {
                
                cn.setAutoCommit(false);
                
                for (int i = 0; i <tablaDetalleApartado.getRowCount(); i++) {
                    idProducto=Integer.parseInt(tablaDetalleApartado.getValueAt(i, 1).toString());
                    cantidadProductosApartados=Float.parseFloat(tablaDetalleApartado.getValueAt(i, 4).toString());
                    float cantidadTablaProductos=instanciaTablaProductos.obtenerCantidadTablaProducto(idProducto);
                    //actualizarCantidadProductosApartados
                    float cantidadActualizada=cantidadTablaProductos+cantidadProductosApartados;
                    psActualizar = cn.prepareStatement("UPDATE productos SET cantidad='"+cantidadActualizada+"' WHERE ID='"+idProducto+"'");
                    psActualizar.executeUpdate();

                }
                //eliminarRegistroApartado
                psEliminar = cn.prepareStatement("DELETE FROM apartados WHERE  id_apartado='"+idApartado+"'");
                psEliminar.executeUpdate();
                
                
                cn.commit();
                JOptionPane.showMessageDialog(null,"El apartado ha sido eliminado correctamente");
                VerApartados.dialog.mostrarApartados("");
                dispose();
                instanciaTableModel.mostrarDatosProductos("",VentanaPrincipal.jTablaProductos);
            }catch (SQLException e) {
                try {
                    cn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null,"Error de rollback() en evento cancelar credito: "+e.getMessage(),"Error rollback() en evento cancelar credito: puntodeventa.DetalleApartados",JOptionPane.ERROR_MESSAGE);

                }
                e.printStackTrace();
                JOptionPane.showMessageDialog(null,"Error en evento cancelar credito: "+e.getMessage(),"Error en evento cancelar credito: puntodeventa.DetalleApartados",JOptionPane.ERROR_MESSAGE);
                dispose();
            }finally{
                try {
                    cn.setAutoCommit(true);
                    if(psActualizar!=null)psActualizar.close();
                    if(psEliminar!=null)psEliminar.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null,"No se pudo cerrar conexion en evento cancelar credito: "+ex.getMessage(),"Error al cerrar conexion en  evento cancelar credito: puntodeventa.DetalleApartados",JOptionPane.WARNING_MESSAGE);
                }
            }
            
        }
    }//GEN-LAST:event_btnCancelarCreditoActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        
        int id_Apartado=Integer.parseInt(tablaRegistroApartado.getValueAt(0, 0).toString());
        ProductosParaAniadir.idApartado=id_Apartado;
        ProductosParaAniadir instancia=new ProductosParaAniadir(null,true);
        instancia.setVisible(true);
        mostrarDetalleApartado(idApartado);
        mostrarApartado(idApartado);
        VerApartados.dialog.mostrarApartados("");
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        
        int fila=tablaDetalleApartado.getSelectedRow();
        if(fila<0){
            JOptionPane.showMessageDialog(null,"no selecciono un registro","Atencion!",JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        PreparedStatement psTotalAndDeudaApartados=null,psActualizarProductosApartados=null,psEliminarRegistroDetalleApartados=null;

        int opcion=JOptionPane.showConfirmDialog(null,"esta seguro de quitar este producto del apartado?","Aviso!!",JOptionPane.YES_NO_OPTION);
        if(opcion==0){
            try {
                        
                int idApartado=Integer.parseInt(tablaRegistroApartado.getValueAt(0, 0).toString());//perfectamente se podria utiliza la variable estatica de la clase 'idApartado'
                TablaApartados instanciaTablaApartados=new TablaApartados();
                Productos instanciaProductos = new Productos();
                float deudaOriginal=instanciaTablaApartados.obtenerDeudaTablaApartados(idApartado);
                int idProducto=Integer.parseInt(tablaDetalleApartado.getValueAt(fila,1).toString());
                float precioUnitario=Float.parseFloat(tablaDetalleApartado.getValueAt(fila, 3).toString());
                float cantidadDeProductosApartados=Float.parseFloat(tablaDetalleApartado.getValueAt(fila, 4).toString());
                if(precioUnitario<deudaOriginal) {
                    cn.setAutoCommit(false);
                    //actualizamos el total y deuda del apartado
                    float totalOriginal=instanciaTablaApartados.obtenerTotalTablaApartados(idApartado);
                    float totalPrecioUnitario=precioUnitario*cantidadDeProductosApartados;
                    float resultadoTotal=totalOriginal-totalPrecioUnitario;
                    float resultadoDeuda=deudaOriginal-totalPrecioUnitario;
                    psTotalAndDeudaApartados = cn.prepareStatement("UPDATE apartados SET total='"+resultadoTotal+"',deuda='"+resultadoDeuda+"' WHERE id_apartado='"+idApartado+"'");
                    psTotalAndDeudaApartados.executeUpdate();
                    //regresamos la cantidad de productos en la tabla PRODUCTOS
                    double cantidadTablaProductos = instanciaProductos.obtenerCantidadTablaProducto(idProducto);
                    double cantidadActualizada=cantidadTablaProductos+cantidadDeProductosApartados;
                    psActualizarProductosApartados = cn.prepareStatement("UPDATE productos SET cantidad='"+cantidadActualizada+"' WHERE ID='"+idProducto+"'");
                    psActualizarProductosApartados.executeUpdate();
                    /* Eliminamos el registro de la tabla 'detallle_apartados' */
                    //la columna para obtner el 'ID_DETALLE_APARTADO' se establecio OCULTA desde el metodo mostrarDetalleApartado()
                    int id_detalle_apartado=Integer.parseInt(tablaDetalleApartado.getValueAt(fila,5).toString());
                    psEliminarRegistroDetalleApartados = cn.prepareStatement("DELETE FROM detalle_apartados WHERE  id_detalle_apartados='"+id_detalle_apartado+"'");
                    psEliminarRegistroDetalleApartados.executeUpdate();
                    
                    cn.commit();
                    //actuliazamos las tablas
                    JOptionPane.showMessageDialog(null,"La deuda y total de este apartado fueron actualizados");
                    mostrarApartado(DetalleApartados.idApartado);
                    mostrarDetalleApartado(DetalleApartados.idApartado);
                    instanciaTableModel.mostrarDatosProductos("",VentanaPrincipal.jTablaProductos);
                    VerApartados.dialog.mostrarApartados("");
                    
                }else JOptionPane.showMessageDialog(null,"no es posible quitar este elemento debido a que causaria inconsitencias en el apartado");
						
            } catch (Exception e) {
                
                e.printStackTrace();
                try {
                cn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null,e.getMessage(),"puntodeventa.DetalleApartados.Evento quitar producto de apartado: Error Rollback",JOptionPane.ERROR_MESSAGE);
                }
                JOptionPane.showMessageDialog(null,"intente nuevamente: "+e.getMessage(),"Error al quitar producto del apartado:",JOptionPane.ERROR_MESSAGE);
            }finally{
                try {
                    //psTotalAndDeudaApartados,psActualizarProductosApartados,psEliminarRegistroDetalleApartados;
                    cn.setAutoCommit(true);
                    if(psTotalAndDeudaApartados!=null)psTotalAndDeudaApartados.close();
                    if(psActualizarProductosApartados!=null)psActualizarProductosApartados.close();
                    if(psEliminarRegistroDetalleApartados!=null)psEliminarRegistroDetalleApartados.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, ex.getMessage(),"puntodeventa.DetalleApartados(evento quitar producto de apartado): no se establecio"
                    + "setAutoCommit() en true o no se pudieron cerrar conexiones",JOptionPane.INFORMATION_MESSAGE);
                }
            }

        }else{
            JOptionPane.showMessageDialog(null,"Operacion canceladada");
        }
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(DetalleApartados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DetalleApartados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DetalleApartados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DetalleApartados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                DetalleApartados dialog = new DetalleApartados(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelarCredito;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPopupMenu jPopupMenu;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tablaDetalleApartado;
    private javax.swing.JTable tablaRegistroApartado;
    // End of variables declaration//GEN-END:variables
}
