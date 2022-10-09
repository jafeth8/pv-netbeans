/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package puntodeventa;

import br.com.adilson.util.Extenso;
import br.com.adilson.util.PrinterMatrix;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import puntodeventa.bd.ConexionBd;

/**
 *
 * @author jafeth8
 */
public class RegistroVenta extends javax.swing.JFrame {
    ConexionBd cc= ConexionBd.obtenerInstancia();
    Connection cn= cc.conexion();
    public static int idVenta=0;
    /**
     * Creates new form RegistroVenta
     */
    public RegistroVenta() {
        initComponents();
        mostrarVentaConRelaciones(idVenta);
        mostrarHistorialVentas(idVenta);
    }
    
    public void mostrarVentaConRelaciones(int ventaId) {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("Id venta");
        modelo.addColumn("Total de la venta");
        modelo.addColumn("Fecha de la venta");
        modelo.addColumn("Pago del cliente");
        modelo.addColumn("Cambio al cliente");
        modelo.addColumn("Nombre cliente");
        modelo.addColumn("Tipo de venta");
        //modelo.addColumn("Tipo de venta");
        tablaVenta.setModel(modelo);
        String sql = "";

        //sql="SELECT * FROM ventas WHERE id_venta="+ventaId+"";
        sql = "SELECT id_venta,total_venta,fecha_venta,pago_del_cliente,cambio_del_cliente,clientes.nombre,tipo_venta FROM ventas "
                + " LEFT JOIN clientes ON ventas.fk_id_cliente=clientes.id_cliente WHERE ventas.id_venta=" + ventaId + "";

        String[] datos = new String[7];
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                datos[0] = rs.getString(1);

                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                datos[3] = rs.getString(4);
                datos[4] = rs.getString(5);
                datos[5] = rs.getString(6);
                if (rs.getString(6) == null) {
                    datos[5] = "No asignado";
                }
                datos[6] = rs.getString(7);
                modelo.addRow(datos);
            }
            tablaVenta.setModel(modelo);
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }

    }
    
    public void mostrarHistorialVentas(int id) {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("Id venta");
        modelo.addColumn("Id Producto");
        modelo.addColumn("Descripcion");
        modelo.addColumn("Precio Unitario");
        modelo.addColumn("Cantidad");
        modelo.addColumn("Descuento");
        tablaHistorialVenta.setModel(modelo);
        String sql = "";

        sql = "SELECT fk_id_venta,fk_id_producto,productos.DESCRIPCION,detalle_ventas.precio_unitario,detalle_ventas.cantidad,detalle_ventas.descuento FROM detalle_ventas "
                + "JOIN productos ON detalle_ventas.fk_id_producto=productos.ID WHERE fk_id_venta=" + id + "";

        String[] datos = new String[6];
        try {
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
            tablaHistorialVenta.setModel(modelo);
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }

    }
    
    public void imprimir()  {
        String total = tablaVenta.getValueAt(0, 1).toString();
        String pagocliente = tablaVenta.getValueAt(0, 3).toString();
        String cambio = tablaVenta.getValueAt(0, 4).toString();
        String fecha = tablaVenta.getValueAt(0, 2).toString();

        //System.out.println("campo tipoVenta : " +tablaVenta.getValueAt(0, 6));
        //System.out.println("campo nombre : " +tablaVenta.getValueAt(0, 5));
        //String tipoVenta=tablaVenta.getValueAt(0, 6).toString();
        //String nombreCliente=tablaVenta.getValueAt(0, 5).toString();
        String nombreCliente = "Sin nombre";
        String tipoVenta = "Al Contado";
        if (tablaVenta.getValueAt(0, 5) != null) {
            tipoVenta = tablaVenta.getValueAt(0, 6).toString();
            nombreCliente = tablaVenta.getValueAt(0, 5).toString();
        }

        //System.out.println("NOOOOMBVRE: "+nombreCliente);
        //JOptionPane.showMessageDialog(null, nombreCliente);
        try {
            PrinterMatrix printer = new PrinterMatrix();
            Extenso e1 = new Extenso();

            e1.setNumber(10);
            //Definir el tamanho del papel para la impresion de dinamico y 32 columnas
            int filas = tablaHistorialVenta.getRowCount();
            int tamanio = filas + 15;
            printer.setOutSize(tamanio, 80);

            //Imprimir = 1ra linea de la columa de 1 a 32
            printer.printTextWrap(0, 1, 5, 80, "=======================================================");
            printer.printTextWrap(1, 1, 40, 80, "La soledad No 6"); //Nombre establecimiento
            printer.printTextWrap(1, 1, 5, 80, "Materiales Fabio"); //Barrio
            printer.printTextWrap(2, 1, 40, 80, "Tel. 423-525-0138"); //Direccion
            printer.printTextWrap(2, 1, 10, 80, "Aranza,Mich."); //Codigo Postal

            printer.printTextWrap(3, 1, 0, 40, "Fecha: " + fecha); //Aqui va la fecha de recibo
            //printer.printTextWrap(3, 1, 40, 80, "Hora"+hora+":"+minuto+":"+segundo); //Aqui va la hora de recibo

            //printer.printTextWrap(9, 1, 3, 80, "Cliente");//Nombre del Cliente
            //printer.printTextWrap(10,1, 5, 80, "������������������������������������������������������������������");
            printer.printTextWrap(4, 1, 0, 80, "Cant.   Producto    P/U   Sub.T");
            //printer.printTextWrap(12,1, 0, 80, "## ");

            for (int i = 0; i < filas; i++) {
                int p = 5 + i; //Fila

                printer.printTextWrap(p, 1, 0, 19, tablaHistorialVenta.getValueAt(i, 4).toString());
                printer.printTextWrap(p, 1, 5, 42, tablaHistorialVenta.getValueAt(i, 2).toString());
                printer.printTextWrap(p, 1, 20, 49, tablaHistorialVenta.getValueAt(i, 3).toString());
                double preciounitario = Double.parseDouble(tablaHistorialVenta.getValueAt(i, 3).toString());
                double cantidad = Double.parseDouble(tablaHistorialVenta.getValueAt(i, 4).toString());
                double sub = cantidad * preciounitario;
                String subtotal = String.valueOf(sub);
                //String pre1= printer.alinharADireita(10, tablaHistorialVenta.getValueAt(i,4).toString());
                printer.printTextWrap(p, 1, 54, 80, subtotal);

                //String inp= printer.alinharADireita(7,punto_Venta.jtbl_venta.getValueAt(i,6).toString());
                //printer.printTextWrap(p , 1, 25, 32, inp);
            }

            DecimalFormat formateador = new DecimalFormat("#.###");

            printer.printTextWrap(filas + 6, 1, 5, 80, "Subtotal: ");
            printer.printTextWrap(filas + 6, 1, 20, 80, "$" + total);

            // String tot= printer.alinharADireita(10, total);
            printer.printTextWrap(filas + 7, 1, 5, 80, "Total a pagar: ");
            printer.printTextWrap(filas + 7, 1, 20, 80, "$" + total);

            //String efe= printer.alinharADireita(10,90);
            printer.printTextWrap(filas + 8, 1, 5, 80, "Efectivo : ");
            printer.printTextWrap(filas + 8, 1, 20, 80, "$" + pagocliente);

            //String cam= printer.alinharADireita(10,9);
            printer.printTextWrap(filas + 9, 1, 5, 80, "Cambio : ");
            printer.printTextWrap(filas + 9, 1, 20, 80, "$" + cambio);

            printer.printTextWrap(filas + 10, 1, 5, 80, "Nombre cliente: " + nombreCliente);
            printer.printTextWrap(filas + 11, 1, 5, 80, "Estado : pagado ");
            printer.printTextWrap(filas + 12, 1, 20, 80, "Tipo de venta: " + tipoVenta);

            //printer.printTextWrap(filas+21, 1, 5, 80, "������������������������������������������������������������������");
            // printer.printTextWrap(filas+1, 1, 5,80, "!Gracias por su Compra!");
            //printer.printTextWrap(filas+11, 1, 5, 80, "Materiales Fabio");
            // printer.printTextWrap(filas+12, 1, 5, 80, "Atendido por : "+UsuarioLabel.getText());
            //printer.printTextWrap(filas+13, 1, 3, 80, "Contacto: workitapp@gmail.com");
            printer.printTextWrap(filas + 13, 1, 3, 80, "===================================================================");

            printer.toFile("impresion.txt");

            FileInputStream inputStream = null;
            
            inputStream = new FileInputStream("impresion.txt");
            
            if (inputStream == null) {
                JOptionPane.showMessageDialog(null,"se detuvo el proceso de imppresion inputStream=null","Imprimir venta",JOptionPane.WARNING_MESSAGE);
                return;
            }

            DocFlavor docFormat = DocFlavor.INPUT_STREAM.AUTOSENSE;
            Doc document = new SimpleDoc(inputStream, docFormat, null);

            PrintRequestAttributeSet attributeSet = new HashPrintRequestAttributeSet();

            PrintService defaultPrintService = PrintServiceLookup.lookupDefaultPrintService();

            if (defaultPrintService != null) {
                DocPrintJob printJob = defaultPrintService.createPrintJob();
                printJob.print(document, attributeSet);

            } else {
                JOptionPane.showMessageDialog(null,"No existen impresoras instaladas");
            }

            //inputStream.close();
            JOptionPane.showMessageDialog(null, "se imprimio correctamente", "mensaje de aviso", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,""+e.getMessage(),"Error al imprimir venta",JOptionPane.WARNING_MESSAGE);

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

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaVenta = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaHistorialVenta = new javax.swing.JTable();
        btnImprimirVenta = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI Black", 1, 12)); // NOI18N
        jLabel1.setText("Venta");

        tablaVenta.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tablaVenta);

        jLabel2.setFont(new java.awt.Font("Segoe UI Black", 1, 12)); // NOI18N
        jLabel2.setText("Registro venta");

        tablaHistorialVenta.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(tablaHistorialVenta);

        btnImprimirVenta.setFont(new java.awt.Font("Segoe UI Black", 1, 12)); // NOI18N
        btnImprimirVenta.setText("Imprimir venta");
        btnImprimirVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImprimirVentaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 915, Short.MAX_VALUE)
                    .addComponent(jScrollPane2)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnImprimirVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnImprimirVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(33, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnImprimirVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimirVentaActionPerformed
        
        int opcion = JOptionPane.showConfirmDialog(null, " imprimir venta?");
        if (opcion == 0) {
            imprimir();
        } else {
            JOptionPane.showMessageDialog(null, "CANCELADO", "Mensaje de aviso", JOptionPane.CANCEL_OPTION);
        }
    }//GEN-LAST:event_btnImprimirVentaActionPerformed

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
            java.util.logging.Logger.getLogger(RegistroVenta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RegistroVenta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RegistroVenta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RegistroVenta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RegistroVenta().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnImprimirVenta;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tablaHistorialVenta;
    private javax.swing.JTable tablaVenta;
    // End of variables declaration//GEN-END:variables
}
