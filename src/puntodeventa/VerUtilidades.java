/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package puntodeventa;

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
public class VerUtilidades extends javax.swing.JDialog {
    
    ConexionBd cc= ConexionBd.obtenerInstancia();
    Connection cn= cc.conexion();

    /**
     * Creates new form VerUtilidades
     */
    public VerUtilidades(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }
    
    public void mostrarUtilidades(String valor) {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("Sumatoria por precio Unitario");
        modelo.addColumn("Sumatoria por costo Unitario");
        modelo.addColumn("Ganancias");
        modelo.addColumn("Del mes");
        modelo.addColumn("Del año");

        tableUtilidades.setModel(modelo);
        String sql = "";
        if (valor.equals("")) {
            sql = "SELECT SUM((precio_unitario)*cantidad) AS precio_unitario,SUM((costo_unitario)*cantidad) AS costo_unitario, SUM((precio_unitario)*cantidad)-SUM((costo_unitario)*cantidad) AS ganancias, MONTH(ventas.fecha_venta),YEAR(ventas.fecha_venta) FROM "
                    + "detalle_ventas JOIN ventas ON detalle_ventas.fk_id_venta=ventas.id_venta GROUP BY MONTH(ventas.fecha_venta),YEAR(ventas.fecha_venta)";
        } else {
            //WHERE YEAR(ventas.fecha_venta)=YEAR('2021-08-30') AND MONTH(ventas.fecha_venta)=MONTH('2021-08-30')
            sql = "SELECT SUM((precio_unitario)*cantidad) AS precio_unitario,SUM((costo_unitario)*cantidad) AS costo_unitario, SUM((precio_unitario)*cantidad)-SUM((costo_unitario)*cantidad) AS ganancias, MONTH(ventas.fecha_venta),YEAR(ventas.fecha_venta) FROM "
                    + "detalle_ventas JOIN ventas ON detalle_ventas.fk_id_venta=ventas.id_venta WHERE YEAR(ventas.fecha_venta)=YEAR('" + valor + "') AND MONTH(ventas.fecha_venta)=MONTH('" + valor + "')"
                    + " GROUP BY MONTH(ventas.fecha_venta),YEAR(ventas.fecha_venta)";
        }

        String[] datos = new String[5];
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                datos[0] = "" + rs.getDouble(1);

                datos[1] = "" + rs.getDouble(2);
                datos[2] = "" + rs.getDouble(3);
                datos[3] = rs.getString(4);
                datos[4] = rs.getString(5);

                modelo.addRow(datos);
            }
            tableUtilidades.setModel(modelo);
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "No se pudieron mostrar datos: "+ex.getMessage(),"Error en la clase VerUtilidades",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void mostrarInsumos(String valor) {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("GASTOS POR INSUMO");
        modelo.addColumn("DEL MES");
        modelo.addColumn("DEL AÑO");

        tableInsumos.setModel(modelo);
        String sql = "";
        if (valor.equals("")) {
            sql = "SELECT SUM(costo),MONTH(fecha), YEAR(fecha) FROM insumos GROUP BY MONTH(fecha),YEAR(fecha)";
        } else {
            sql = "SELECT SUM(costo),MONTH(fecha), YEAR(fecha) FROM insumos "
            + "WHERE MONTH(fecha)=MONTH('" + valor + "') and YEAR(fecha)=YEAR('" + valor + "') GROUP BY MONTH(fecha),YEAR(fecha)";
        }

        String[] datos = new String[6];
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                datos[0] = "" + rs.getDouble(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                modelo.addRow(datos);
            }
            tableInsumos.setModel(modelo);
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }

    }

    public void mostrarInsumosDetalle(String valor) {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("DESCRIPCION");
        modelo.addColumn("GASTO");
        modelo.addColumn("FECHA");

        tableInsumosDetalles.setModel(modelo);
        String sql = "";
        if (valor.equals("")) {
            sql = "SELECT descripcion,costo,fecha FROM insumos";
        } else {
            sql = "SELECT descripcion,costo,fecha FROM insumos WHERE MONTH(fecha)=MONTH('" + valor + "') and YEAR(fecha)=YEAR('" + valor + "')";
        }

        String[] datos = new String[3];
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                datos[0] = "" + rs.getString(1);

                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);

                modelo.addRow(datos);
            }
            tableInsumosDetalles.setModel(modelo);
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
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

        dateChooser = new com.toedter.calendar.JDateChooser();
        btnFiltrar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableUtilidades = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableInsumos = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableInsumosDetalles = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Utilidades");
        setModal(true);

        btnFiltrar.setFont(new java.awt.Font("Segoe UI Black", 1, 12)); // NOI18N
        btnFiltrar.setText("Filtrar resultados por fecha");
        btnFiltrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFiltrarActionPerformed(evt);
            }
        });

        tableUtilidades.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "HAZ CLICK EN EL BOTON DE FILTRADO PARA MOSTRAR RESULTADOS"
            }
        ));
        jScrollPane1.setViewportView(tableUtilidades);

        jLabel1.setFont(new java.awt.Font("Segoe UI Black", 1, 12)); // NOI18N
        jLabel1.setText("GASTOS POR INSUMO");

        jPanel1.setLayout(new java.awt.GridLayout(1, 2, 10, 0));

        tableInsumos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null}
            },
            new String [] {
                "GASTOS POR INSUMO", "POR MES", "POR AÑO"
            }
        ));
        jScrollPane2.setViewportView(tableInsumos);

        jPanel1.add(jScrollPane2);

        tableInsumosDetalles.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null}
            },
            new String [] {
                "DESCRIPCION", "GASTO", "FECHA"
            }
        ));
        jScrollPane3.setViewportView(tableInsumosDetalles);

        jPanel1.add(jScrollPane3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(dateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnFiltrar)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnFiltrar)
                    .addComponent(dateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnFiltrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFiltrarActionPerformed
        // TODO add your handling code here:
        
        String fecha = "";

        try {
            SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
            fecha = formatoFecha.format(dateChooser.getDate());
            mostrarUtilidades(fecha);
            mostrarInsumos(fecha);
            mostrarInsumosDetalle(fecha);
        } catch (NullPointerException vacio) {
            // TODO: handle exception
            //JOptionPane.showMessageDialog(null,"no seleccionaste una fecha: se seleccionara la fecha de hoy");
            //fecha=LocalDate.now().toString();
            mostrarUtilidades("");
            mostrarInsumos("");
            mostrarInsumosDetalle("");
        }
        
    }//GEN-LAST:event_btnFiltrarActionPerformed

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
            java.util.logging.Logger.getLogger(VerUtilidades.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VerUtilidades.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VerUtilidades.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VerUtilidades.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                VerUtilidades dialog = new VerUtilidades(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnFiltrar;
    private com.toedter.calendar.JDateChooser dateChooser;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable tableInsumos;
    private javax.swing.JTable tableInsumosDetalles;
    private javax.swing.JTable tableUtilidades;
    // End of variables declaration//GEN-END:variables
}
