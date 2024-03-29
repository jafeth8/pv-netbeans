/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package puntodeventa;

import helpers.sql.TablaVentas;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import puntodeventa.bd.ConexionBd;

/**
 *
 * @author jafeth8
 */
public class VerVentas extends javax.swing.JFrame {

    TablaVentas instanciaTablaVentas=new TablaVentas();
    private RegistroVenta instanciaRegistroVenta;
    
    /**
     * Creates new form VerVentas
     */
    public VerVentas() {
        initComponents();
        mostrardatos("");
    }
    
    public void mostrardatos(String valor) {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("Id venta");
        modelo.addColumn("Total de la venta");
        modelo.addColumn("Fecha de la venta");
        modelo.addColumn("Pago del cliente");
        modelo.addColumn("Cambio Entregado al cliente");
        modelo.addColumn("Tipo de venta");
        String sql = "";
        //table.setModel(modelo);
        tablaVentas.setModel(modelo);
        if (valor.equals("")) {
            sql = "SELECT * FROM ventas ORDER BY id_venta DESC LIMIT 100";
        } else {
            sql = "SELECT * FROM ventas WHERE fecha_venta='" + valor + "'";
        }

        String[] datos = new String[6];
        try {
            ConexionBd cc= ConexionBd.obtenerInstancia();
            Connection cn= cc.conexion();
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                datos[0] = rs.getString(1);

                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                datos[3] = rs.getString(4);
                datos[4] = rs.getString(5);
                datos[5] = rs.getString(6);
                modelo.addRow(datos);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
            JOptionPane.showMessageDialog(null, ex.getMessage(),"No se pudieron mostrar los datos de ventas",JOptionPane.ERROR_MESSAGE);
            VentanaPrincipal.instanciaVerVentas.dispose();
            VentanaPrincipal.instanciaVerVentas=null;
        }

    }
    
    public void mostrarMasDetalles(String valor) {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("Id venta");
        modelo.addColumn("Total de la venta");
        modelo.addColumn("Fecha de la venta");
        modelo.addColumn("Descripcion producto");
        modelo.addColumn("Precio unitario");
        modelo.addColumn("Costo unitario");
        modelo.addColumn("Descuento");
        modelo.addColumn("Categoria");
        modelo.addColumn("Cantidad");
        String sql = "";
        //table.setModel(modelo);
        tablaMasDetalles.setModel(modelo);
        if (valor.equals("")) {
            sql = "SELECT * FROM ventas";
        } else {
            //sql="SELECT * FROM ventas WHERE fecha_venta='"+valor+"'";
            sql = "SELECT ventas.id_venta,ventas.total_venta,ventas.fecha_venta,productos.DESCRIPCION,detalle_ventas.precio_unitario,detalle_ventas.costo_unitario,detalle_ventas.descuento, productos.CATEGORIA, detalle_ventas.cantidad "
            + "FROM ventas JOIN detalle_ventas ON id_venta=detalle_ventas.fk_id_venta "
            + "JOIN productos ON detalle_ventas.fk_id_producto=productos.ID WHERE ventas.fecha_venta='" + valor + "'";
        }

        String[] datos = new String[9];
        try {
            ConexionBd cc= ConexionBd.obtenerInstancia();
            Connection cn= cc.conexion();
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                datos[3] = rs.getString(4);
                datos[4] = rs.getString(5);
                datos[5] = rs.getString(6);
                datos[6] = rs.getString(7);
                datos[7] = rs.getString(8);
                datos[8] = rs.getString(9);
                modelo.addRow(datos);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
            JOptionPane.showMessageDialog(null, ex.getMessage(),"No se pudieron mostrar detalles de las ventas",JOptionPane.ERROR_MESSAGE);
            if(VentanaPrincipal.instanciaVerVentas!=null) VentanaPrincipal.instanciaVerVentas.dispose();
            VentanaPrincipal.instanciaVerVentas=null;
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

        jPopupMenuVentas = new javax.swing.JPopupMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        panelFondo = new javax.swing.JPanel();
        panel_principal = new javax.swing.JPanel();
        jTabbedPane = new javax.swing.JTabbedPane();
        panelVentas = new javax.swing.JPanel();
        jScrollPaneTablaVentas = new javax.swing.JScrollPane();
        tablaVentas = new javax.swing.JTable();
        panelDetalleVentas = new javax.swing.JPanel();
        jScrollPaneTablaDetalleVentas = new javax.swing.JScrollPane();
        tablaMasDetalles = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        labelGanacias = new javax.swing.JLabel();
        btnFiltraPorFecha = new javax.swing.JButton();
        dateChooser = new com.toedter.calendar.JDateChooser();

        jMenuItem1.setText("Ver registros de esta venta");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jPopupMenuVentas.add(jMenuItem1);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setModalExclusionType(java.awt.Dialog.ModalExclusionType.APPLICATION_EXCLUDE);

        panel_principal.setBackground(new java.awt.Color(102, 204, 255));

        tablaVentas.setModel(new javax.swing.table.DefaultTableModel(
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
        tablaVentas.setComponentPopupMenu(jPopupMenuVentas);
        jScrollPaneTablaVentas.setViewportView(tablaVentas);

        javax.swing.GroupLayout panelVentasLayout = new javax.swing.GroupLayout(panelVentas);
        panelVentas.setLayout(panelVentasLayout);
        panelVentasLayout.setHorizontalGroup(
            panelVentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelVentasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPaneTablaVentas, javax.swing.GroupLayout.DEFAULT_SIZE, 1088, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelVentasLayout.setVerticalGroup(
            panelVentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelVentasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPaneTablaVentas, javax.swing.GroupLayout.DEFAULT_SIZE, 473, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane.addTab("Ventas", panelVentas);

        tablaMasDetalles.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPaneTablaDetalleVentas.setViewportView(tablaMasDetalles);

        javax.swing.GroupLayout panelDetalleVentasLayout = new javax.swing.GroupLayout(panelDetalleVentas);
        panelDetalleVentas.setLayout(panelDetalleVentasLayout);
        panelDetalleVentasLayout.setHorizontalGroup(
            panelDetalleVentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDetalleVentasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPaneTablaDetalleVentas, javax.swing.GroupLayout.DEFAULT_SIZE, 1088, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelDetalleVentasLayout.setVerticalGroup(
            panelDetalleVentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDetalleVentasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPaneTablaDetalleVentas, javax.swing.GroupLayout.DEFAULT_SIZE, 473, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane.addTab("Mas detalle de ganacias por dia", panelDetalleVentas);

        jLabel1.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        jLabel1.setText("Ganancias por dia:");

        labelGanacias.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        labelGanacias.setForeground(new java.awt.Color(116, 32, 68));
        labelGanacias.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelGanacias.setText("NaN");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelGanacias, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(labelGanacias)
                .addContainerGap())
        );

        javax.swing.GroupLayout panel_principalLayout = new javax.swing.GroupLayout(panel_principal);
        panel_principal.setLayout(panel_principalLayout);
        panel_principalLayout.setHorizontalGroup(
            panel_principalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_principalLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panel_principalLayout.setVerticalGroup(
            panel_principalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_principalLayout.createSequentialGroup()
                .addComponent(jTabbedPane, javax.swing.GroupLayout.PREFERRED_SIZE, 518, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_principalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 31, Short.MAX_VALUE))
        );

        btnFiltraPorFecha.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnFiltraPorFecha.setText("Filtrar por fecha");
        btnFiltraPorFecha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFiltraPorFechaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelFondoLayout = new javax.swing.GroupLayout(panelFondo);
        panelFondo.setLayout(panelFondoLayout);
        panelFondoLayout.setHorizontalGroup(
            panelFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_principal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(panelFondoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnFiltraPorFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(dateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelFondoLayout.setVerticalGroup(
            panelFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFondoLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(panelFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnFiltraPorFecha)
                    .addComponent(dateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addComponent(panel_principal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelFondo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelFondo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnFiltraPorFechaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFiltraPorFechaActionPerformed
        
        String fecha = "";
        try {
            SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
            fecha = formatoFecha.format(dateChooser.getDate());
            mostrardatos(fecha);
            double ganancias = instanciaTablaVentas.obtenerGanaciasPorDia(fecha);
            labelGanacias.setText("$ " + ganancias);

            mostrarMasDetalles(fecha);

        } catch (NullPointerException vacio) {
            JOptionPane.showMessageDialog(null, "No selecciono una fecha, se mostraran todos los registros");
            mostrardatos("");

        } catch (Exception e2) {
            // TODO: handle exception
            e2.printStackTrace();
        }
    }//GEN-LAST:event_btnFiltraPorFechaActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        int fila=tablaVentas.getSelectedRow();
        if(fila>=0){
            int id_venta=Integer.parseInt(tablaVentas.getValueAt(fila,0).toString());
            RegistroVenta.idVenta=id_venta;
            if(instanciaRegistroVenta!=null) instanciaRegistroVenta.dispose();
            instanciaRegistroVenta =new RegistroVenta();
            instanciaRegistroVenta.setVisible(true);
        }else JOptionPane.showMessageDialog(null,"no selecciono ninguna fila");
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
            java.util.logging.Logger.getLogger(VerVentas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VerVentas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VerVentas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VerVentas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VerVentas().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnFiltraPorFecha;
    private com.toedter.calendar.JDateChooser dateChooser;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPopupMenu jPopupMenuVentas;
    private javax.swing.JScrollPane jScrollPaneTablaDetalleVentas;
    private javax.swing.JScrollPane jScrollPaneTablaVentas;
    private javax.swing.JTabbedPane jTabbedPane;
    private javax.swing.JLabel labelGanacias;
    private javax.swing.JPanel panelDetalleVentas;
    private javax.swing.JPanel panelFondo;
    private javax.swing.JPanel panelVentas;
    private javax.swing.JPanel panel_principal;
    private javax.swing.JTable tablaMasDetalles;
    private javax.swing.JTable tablaVentas;
    // End of variables declaration//GEN-END:variables
}
