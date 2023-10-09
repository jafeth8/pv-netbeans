/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package puntodeventa;

import br.com.adilson.util.Extenso;
import br.com.adilson.util.PrinterMatrix;
import helpers.sql.DatosTicket;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class VerRegistrosApartados extends javax.swing.JDialog {
    
    public static int idApartado=0;
    DatosTicket instanciaTicket=new DatosTicket();
    HashMap<String, String> hashDatosTicket=instanciaTicket.obtenerDatos("1");
    String establecimiento=hashDatosTicket.get("establecimiento");
    String direccion=hashDatosTicket.get("direccion");
    String telefono=hashDatosTicket.get("telefono");
    String localidad=hashDatosTicket.get("localidad");
    String estado= hashDatosTicket.get("estado");
    /**
     * Creates new form VerRegistrosApartados
     */
    public VerRegistrosApartados(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        mostrarApartado(VerRegistrosApartados.idApartado);
        mostrarDetalleApartado(VerRegistrosApartados.idApartado);
        mostrarRegistroApartado(VerRegistrosApartados.idApartado);
    }
    
    private void mostrarApartado(int id_apartado){
        DefaultTableModel modelo= new DefaultTableModel();
        modelo.addColumn("Id Apartado");
        modelo.addColumn("Id Cliente");
        modelo.addColumn("Nombre");
        modelo.addColumn("Total Compra");
        modelo.addColumn("Fecha Apartado");
        modelo.addColumn("Total abono");
        modelo.addColumn("Deuda");
        //modelo.addColumn("Tipo de venta");
        tablaMostrarApartado.setModel(modelo);
        String sql="";

        sql="SELECT id_apartado,fk_id_cliente,clientes.nombre,total,fecha_de_apartado,total_abono,deuda "
        + "FROM apartados JOIN clientes ON apartados.fk_id_cliente=clientes.id_cliente WHERE id_apartado ="+id_apartado+"";
	 
        String []datos = new String [7];
        Statement st = null;
        ResultSet rs = null;
        ConexionBd cc= ConexionBd.obtenerInstancia();
        Connection cn= cc.conexion();
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
            tablaMostrarApartado.setModel(modelo);
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex.getMessage(),"Error al mostrar encabezado de apartado", HEIGHT);
        }finally{
            try {
                if(rs!=null)rs.close();
                if(st!=null)st.close();
                if(cn!=null)cn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, ex.getMessage(),"No se puedo cerrar conexion al mostrar encabezado de apartado", HEIGHT);
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
        tablaDetalleApartado.setModel(modelo);
        String sql="";

        sql="SELECT fk_id_apartado,fk_id_producto,productos.DESCRIPCION,detalle_apartados.precio_unitario,detalle_apartados.cantidad FROM "
                    + "detalle_apartados JOIN productos ON detalle_apartados.fk_id_producto=productos.ID WHERE fk_id_apartado="+id+"";

        String []datos = new String [6];
        Statement st = null;
        ResultSet rs = null;
        ConexionBd cc= ConexionBd.obtenerInstancia();
        Connection cn= cc.conexion();
        try {
            st = cn.createStatement();
            rs = st.executeQuery(sql);
            while(rs.next()){
                datos[0]=rs.getString(1);

                datos[1]=rs.getString(2);
                datos[2]=rs.getString(3);
                datos[3]=rs.getString(4);
                datos[4]=rs.getString(5);

                modelo.addRow(datos);
            }
            tablaDetalleApartado.setModel(modelo);
        }catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex.getMessage(),"Error al mostrar detalles de apartado", HEIGHT);
        }finally{
            try {
                if(rs!=null)rs.close();
                if(st!=null)st.close();
                if(cn!=null)cn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, ex.getMessage(),"No se puedo cerrar conexion al mostrar detalles de apartado", HEIGHT);
            }
        }    
    }
    
    private void mostrarRegistroApartado(int idApartado) {
        Statement st=null;
        ResultSet rs=null;
        ConexionBd cc= ConexionBd.obtenerInstancia();
        Connection cn= cc.conexion();
    	DefaultTableModel modelo= new DefaultTableModel();
        modelo.addColumn("Id apartado");
        modelo.addColumn("Dinero recibido");
        modelo.addColumn("Abono");
        modelo.addColumn("Cambio Entregado");
        modelo.addColumn("Fecha del Abono");
       
        String sql;
        sql="SELECT fk_id_apartado,dinero_recibido,abono,cambio_entregado,fecha_abono FROM registro_apartados WHERE fk_id_apartado="+idApartado+"";
        String []datos = new String [5];
        try {
            st = cn.createStatement();
            rs = st.executeQuery(sql);
            
            while(rs.next()){
            	
                datos[0]=rs.getString(1);
                datos[1]=rs.getString(2);
                datos[2]=rs.getString(3);
                datos[3]=rs.getString(4);
                datos[4]=rs.getString(5);
                modelo.addRow(datos);
               
            }
            table.setModel(modelo);
        }catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex.getMessage(),"Error al mostrar registros de abono de apartado", HEIGHT);
        }finally{
            try {
                if(rs!=null)rs.close();
                if(st!=null)st.close();
                if(cn!=null)cn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, ex.getMessage(),"No se puedo cerrar conexion al mostrar registros de abono de apartado", HEIGHT);
            }
        }    
    }
    
    public void imprimircomprobante(){
        
        int fila=table.getSelectedRow();
        
        String dinerorecibido = table.getValueAt(fila, 1).toString();
        String abono = table.getValueAt(fila, 2).toString();
        String cambio  =  table.getValueAt(fila, 3).toString();
        String fecha = table.getValueAt(fila, 4).toString();
	
        String deuda = tablaMostrarApartado.getValueAt(0, 6).toString();
			
        try {
            PrinterMatrix printer = new PrinterMatrix();
            Extenso e1 = new Extenso();

            e1.setNumber(10);
            //Definir el tamanho del papel para la impresion de dinamico y 32 columnas
            // int filas = tablaHistorialVenta.getRowCount();
            //int tamanio = filas+15;
            printer.setOutSize(13, 80);

            //Imprimir = 1ra linea de la columa de 1 a 32


            printer.printTextWrap(1, 1, 40, 80, direccion); //Nombre establecimiento
            printer.printTextWrap(1, 1, 5, 80, establecimiento); //Barrio
            printer.printTextWrap(2, 1, 40, 80, "Tel. "+telefono); //Direccion
            printer.printTextWrap(2, 1, 5, 80, localidad+","+estado); //Codigo Postal

            printer.printTextWrap(3, 1, 0, 80, "=======================================================");
            printer.printTextWrap(4, 1, 0, 40, "Fecha de apartado: "+tablaMostrarApartado.getValueAt(0,4).toString());
            printer.printTextWrap(5, 1, 0, 40, "Cliente: "+tablaMostrarApartado.getValueAt(0,2).toString());

            printer.printTextWrap(6, 1, 0, 40, "Dinero Recibido: "+dinerorecibido);
            printer.printTextWrap(7, 1, 0, 40, "Abono: "+abono);
            printer.printTextWrap(8, 1, 0, 80, "Fecha de Abono: "+fecha);
            printer.printTextWrap(9, 1, 0, 40, "Cambio : "+cambio);
            //printer.printTextWrap(5, 1, 0, 40, "Dinero Recibido");
            printer.printTextWrap(10, 1, 0, 40, "Deuda : "+ deuda);

            DecimalFormat formateador = new DecimalFormat("#.###");
            //5
            printer.printTextWrap(11, 1, 40,80,"!Comprobante de pago!");


            printer.printTextWrap(12, 1, 0,80, "===================================================================");

            printer.toFile("impresion.txt");

            FileInputStream inputStream = null;
            
            inputStream = new FileInputStream("impresion.txt");
            
            if (inputStream == null) {
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
               System.err.println("No existen impresoras instaladas");
               JOptionPane.showMessageDialog(null,"No existen impresoras instaladas");
           }

        } catch (Exception e2) {
           e2.printStackTrace();
           JOptionPane.showMessageDialog(null, e2.getMessage(),"Error al imprimir comprobante apartados",JOptionPane.ERROR_MESSAGE);
        }	
    }
    
    public void imprimirCompra(){
		
        String dinerorecibido = tablaMostrarApartado.getValueAt(0, 1).toString();
        String nombre = tablaMostrarApartado.getValueAt(0, 2).toString();
        String totalabono = tablaMostrarApartado.getValueAt(0, 5).toString();
        String totalcompra  =  tablaMostrarApartado.getValueAt(0, 3).toString();
        String fechaparatado = tablaMostrarApartado.getValueAt(0, 4).toString();
        String DeudaTotal = tablaMostrarApartado.getValueAt(0, 6).toString();

        Calendar fecha = new GregorianCalendar();
        int anio = fecha.get(Calendar.YEAR);
        int mes = fecha.get(Calendar.MONTH)+1;
        int dia = fecha.get(Calendar.DAY_OF_MONTH);
        int hora = fecha.get(Calendar.HOUR_OF_DAY);
        int minuto = fecha.get(Calendar.MINUTE);
        int segundo = fecha.get(Calendar.SECOND);	
		
        try {
            PrinterMatrix printernew = new PrinterMatrix();
            Extenso e2 = new Extenso();


            e2.setNumber(10);
            //Definir el tamanho del papel para la impresion de dinamico y 32 columnas
            int filas = tablaDetalleApartado.getRowCount();
            int tamanio = filas+17;
            printernew.setOutSize(tamanio, 80);

            //Imprimir = 1ra linea de la columa de 1 a 32
			    
            printernew.printTextWrap(1, 1, 40, 80, direccion); //Nombre establecimiento
            printernew.printTextWrap(1, 1, 5, 80, establecimiento); //Barrio
            printernew.printTextWrap(2, 1, 40, 80, "Tel. "+telefono); //Direccion
            printernew.printTextWrap(2, 1, 5, 80, localidad+","+estado); //Codigo Postal

            printernew.printTextWrap(3, 1, 0, 80, "=======================================================");
            printernew.printTextWrap(4, 1, 0, 40, "Fecha de apartado: "+tablaMostrarApartado.getValueAt(0,4).toString());
            printernew.printTextWrap(5, 1, 0, 80, "Cliente: "+tablaMostrarApartado.getValueAt(0,2).toString());
            //Aqui va la fecha de recibo
			    
			    
			   
            //printer.printTextWrap(9, 1, 3, 80, "Cliente");//Nombre del Cliente
            //printer.printTextWrap(10,1, 5, 80, "������������������������������������������������������������������");
            printernew.printTextWrap(6,1, 0, 80, "Producto           Sub.T");
            //printer.printTextWrap(12,1, 0, 80, "## ");

            for (int i = 0; i < filas; i++) {
                int p = 7+i; //Fila
                printernew.printTextWrap(p , 1, 20, 49, tablaDetalleApartado.getValueAt(i,4).toString());
                printernew.printTextWrap(p , 1, 0, 19 , tablaDetalleApartado.getValueAt(i,2).toString());
                // printernew.printTextWrap(p , 1, 5, 42 , tablaDetalleApartado.getValueAt(i,3).toString());

                //String pre1= printernew.alinharADireita(10, table.getValueAt(i,5).toString());
                //printernew.printTextWrap(p , 1, 54, 80, pre1);

                //String inp= printer.alinharADireita(7,punto_Venta.jtbl_venta.getValueAt(i,6).toString());
                //printer.printTextWrap(p , 1, 25, 32, inp);
            }

            DecimalFormat formateador = new DecimalFormat("#.###");


            printernew.printTextWrap(filas+8, 1, 0, 80, "Subtotal: ");
            printernew.printTextWrap(filas+8, 1, 20, 80, "$"+totalcompra);

            // String tot= printer.alinharADireita(10, total);
            printernew.printTextWrap(filas+9, 1, 0, 80, "Total a pagar: ");
            printernew.printTextWrap(filas+9, 1, 20, 80, "$"+totalcompra);

            //String efe= printer.alinharADireita(10,90);
            printernew.printTextWrap(filas+10, 1, 0, 80, "Total Abono : ");
            printernew.printTextWrap(filas+10, 1, 20, 80, "$"+totalabono);

            //String cam= printer.alinharADireita(10,9);
            printernew.printTextWrap(filas+11, 1, 0, 80, "Deuda : ");
            printernew.printTextWrap(filas+11, 1, 20, 80, "$"+DeudaTotal);

            //printer.printTextWrap(filas+21, 1, 5, 80, "������������������������������������������������������������������");
            printernew.printTextWrap(filas+13, 1, 0,80, "!Gracias por su Compra!");
            printernew.printTextWrap(filas+14, 1, 0, 80, establecimiento);
            //printer.printTextWrap(filas+12, 1, 2, 80, "Atendido por : "+UsuarioLabel.getText());
            //printer.printTextWrap(filas+13, 1, 3, 80, "Contacto: workitapp@gmail.com");
            printernew.printTextWrap(filas+15, 1, 0, 80, "impresion de ticket: "+LocalDateTime.now().toString());
            printernew.printTextWrap(filas+16, 1, 0,80, "===================================================================");

            printernew.toFile("impresion.txt");

            FileInputStream inputStream = null;
           
            inputStream = new FileInputStream("impresion.txt");
            
            if (inputStream == null) {
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
                System.err.println("No existen impresoras instaladas");
                JOptionPane.showMessageDialog(null,"No existen impresoras instaladas");
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            JOptionPane.showMessageDialog(null, e2.getMessage(), "Error al imprimir compras apartados", JOptionPane.ERROR_MESSAGE);
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

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaMostrarApartado = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaDetalleApartado = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        btnImprimirComprobante = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(153, 102, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("APARTADO:");

        tablaMostrarApartado.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tablaMostrarApartado);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setText("DETALLE APARTADO:");

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
        jScrollPane2.setViewportView(tablaDetalleApartado);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setText("REGISTROS DEL APARTADO");

        table.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane3.setViewportView(table);

        btnImprimirComprobante.setText("Imprimir comprobante");
        btnImprimirComprobante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImprimirComprobanteActionPerformed(evt);
            }
        });

        jButton2.setText("Imprimir compras");
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
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnImprimirComprobante)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2))
                    .addComponent(jLabel1)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 960, Short.MAX_VALUE)
                    .addComponent(jLabel2)
                    .addComponent(jScrollPane2)
                    .addComponent(jLabel3)
                    .addComponent(jScrollPane3))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnImprimirComprobante)
                    .addComponent(jButton2))
                .addContainerGap(7, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnImprimirComprobanteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimirComprobanteActionPerformed
        
        int fila=table.getSelectedRow();
        if(fila>=0) {
            imprimircomprobante();
            JOptionPane.showMessageDialog(null, "se imprimio correctamente","mensaje de aviso",JOptionPane.INFORMATION_MESSAGE);     
        }else {
            JOptionPane.showMessageDialog(null,"no seleccionaste un registro del apartado");
        }
        
    }//GEN-LAST:event_btnImprimirComprobanteActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        
        imprimirCompra();
    }//GEN-LAST:event_jButton2ActionPerformed

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
            java.util.logging.Logger.getLogger(VerRegistrosApartados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VerRegistrosApartados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VerRegistrosApartados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VerRegistrosApartados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                VerRegistrosApartados dialog = new VerRegistrosApartados(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnImprimirComprobante;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable tablaDetalleApartado;
    private javax.swing.JTable tablaMostrarApartado;
    private javax.swing.JTable table;
    // End of variables declaration//GEN-END:variables
}
