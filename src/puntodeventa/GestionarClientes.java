/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package puntodeventa;

import helpers.sql.TablaClientes;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import puntodeventa.bd.ConexionBd;

/**
 *
 * @author jafeth8
 */
public class GestionarClientes extends javax.swing.JDialog {
    ConexionBd cc= ConexionBd.obtenerInstancia();
    Connection cn= cc.conexion();
    TablaClientes instanciaTablaClientes = new TablaClientes();
    /**
     * Creates new form GestionarClientes
     */
    public GestionarClientes(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        visualizarClientes("");
    }
    
    private void visualizarClientes(String nombreCliente) {
        Statement st=null;
        ResultSet rs=null;
    	DefaultTableModel modelo= new DefaultTableModel();
        modelo.addColumn("Id cliente");
        modelo.addColumn("Nombre");
        
        modelo.addColumn("apellidos");
        modelo.addColumn("Direccion");
        modelo.addColumn("Telefono");
        modelo.addColumn("Tipo Cliente");
       
        String sql="";
        if(nombreCliente.equals("")) {
            sql="SELECT id_cliente,clientes.nombre,apellidos,direccion,numero_telefono, ctipo_clientes.nombre FROM "
            + "clientes JOIN ctipo_clientes ON clientes.fk_id_tipo_cliente=ctipo_clientes.id_tipo_cliente WHERE fk_id_state=1";
        }else {
            sql="SELECT id_cliente,clientes.nombre,apellidos,direccion,numero_telefono, ctipo_clientes.nombre "
            + "FROM clientes JOIN ctipo_clientes ON clientes.fk_id_tipo_cliente=ctipo_clientes.id_tipo_cliente WHERE clientes.nombre LIKE '%"+nombreCliente+"%' AND fk_id_state=1";
        }

        String []datos = new String [6];
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
                
                modelo.addRow(datos);
               
            }
            table.setModel(modelo);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage(),"Error al visualizar clientes: puntodeventa.GestionarClientes",JOptionPane.ERROR_MESSAGE);
        }finally {
        try {
            if(st!=null)st.close();
            if(rs!=null)rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, e.getMessage(),"Error al cerrar conexion: puntodeventa.GestionarClientes",JOptionPane.WARNING_MESSAGE);
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
        java.awt.GridBagConstraints gridBagConstraints;

        jPopupMenu1 = new javax.swing.JPopupMenu();
        menuItemModificar = new javax.swing.JMenuItem();
        menuItemEliminar = new javax.swing.JMenuItem();
        panelFondo = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        textField_id = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        textFieldNombre = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        textFieldApellidos = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        textField_direccion = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        textFieldTelefono = new javax.swing.JTextField();
        btnActualizarDatos = new javax.swing.JButton();
        btnBuscar = new javax.swing.JButton();
        btnMostrar = new javax.swing.JButton();

        menuItemModificar.setText("Modificar");
        menuItemModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemModificarActionPerformed(evt);
            }
        });
        jPopupMenu1.add(menuItemModificar);

        menuItemEliminar.setText("Eliminar");
        menuItemEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemEliminarActionPerformed(evt);
            }
        });
        jPopupMenu1.add(menuItemEliminar);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        panelFondo.setBackground(new java.awt.Color(102, 153, 255));

        jScrollPane1.setComponentPopupMenu(jPopupMenu1);

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
        table.setComponentPopupMenu(jPopupMenu1);
        jScrollPane1.setViewportView(table);

        jPanel1.setLayout(new java.awt.GridBagLayout());

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("ID");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel1.add(jLabel1, gridBagConstraints);

        textField_id.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel1.add(textField_id, gridBagConstraints);

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("NOMBRE");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel1.add(jLabel2, gridBagConstraints);

        textFieldNombre.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel1.add(textFieldNombre, gridBagConstraints);

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("APELLIDOS");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel1.add(jLabel3, gridBagConstraints);

        textFieldApellidos.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel1.add(textFieldApellidos, gridBagConstraints);

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("DIRECCION");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel1.add(jLabel4, gridBagConstraints);

        textField_direccion.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel1.add(textField_direccion, gridBagConstraints);

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("TELEFONO");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel1.add(jLabel5, gridBagConstraints);

        textFieldTelefono.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel1.add(textFieldTelefono, gridBagConstraints);

        btnActualizarDatos.setText("Actualizar Datos");
        btnActualizarDatos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarDatosActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel1.add(btnActualizarDatos, gridBagConstraints);

        btnBuscar.setText("Buscar por Nombre");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel1.add(btnBuscar, gridBagConstraints);

        btnMostrar.setText("Mostrar Todo");
        btnMostrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMostrarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel1.add(btnMostrar, gridBagConstraints);

        javax.swing.GroupLayout panelFondoLayout = new javax.swing.GroupLayout(panelFondo);
        panelFondo.setLayout(panelFondoLayout);
        panelFondoLayout.setHorizontalGroup(
            panelFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFondoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1038, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelFondoLayout.setVerticalGroup(
            panelFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFondoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 584, Short.MAX_VALUE)
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

    private void btnActualizarDatosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarDatosActionPerformed
        
        if(textFieldNombre.getText().equals("")|| textFieldApellidos.getText().equals("")|| textField_direccion.getText().equals("")) {
            JOptionPane.showMessageDialog(null,"asegurese de que algunos campos no esten vacios campos no esten vacios");
        }else {
            instanciaTablaClientes.actualizarDatosClientes(textField_id.getText(), textFieldNombre.getText(), 
            "", textFieldApellidos.getText(), textField_direccion.getText(), textFieldTelefono.getText());
            visualizarClientes("");
        }
        
    }//GEN-LAST:event_btnActualizarDatosActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        
        String nombre=textFieldNombre.getText().toString();
        if(nombre.equals("")) {
            JOptionPane.showMessageDialog(null,"El campo nombre esta vacio","Mensaje",JOptionPane.WARNING_MESSAGE);
        }else {
            visualizarClientes(textFieldNombre.getText().toString());
        }
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnMostrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMostrarActionPerformed
        
        visualizarClientes("");
    }//GEN-LAST:event_btnMostrarActionPerformed

    private void menuItemModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemModificarActionPerformed
        
        int fila=table.getSelectedRow();
				
        if(fila>=0){
            textField_id.setText(table.getValueAt(fila, 0).toString());
            textFieldNombre.setText(table.getValueAt(fila, 1).toString());
            textFieldApellidos.setText(table.getValueAt(fila, 2).toString());
            textField_direccion.setText(table.getValueAt(fila, 3).toString());
            textFieldTelefono.setText(table.getValueAt(fila, 4).toString());
        }
        else{
            JOptionPane.showMessageDialog(null,"no seleciono fila");
        }
    }//GEN-LAST:event_menuItemModificarActionPerformed

    private void menuItemEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemEliminarActionPerformed
        
        int fila=table.getSelectedRow();
        int id_cliente=Integer.parseInt(table.getValueAt(fila,0).toString());

        if(fila>=0){
            instanciaTablaClientes.actualizarStateTablaClientes(id_cliente, 3); // 3 es eliminado en la tabla sstate
            JOptionPane.showMessageDialog(null, "Cliente eliminado satisfactoriamente");
            visualizarClientes("");
        }
        else{
            JOptionPane.showMessageDialog(null,"no seleciono fila");
        }
    }//GEN-LAST:event_menuItemEliminarActionPerformed

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
            java.util.logging.Logger.getLogger(GestionarClientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GestionarClientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GestionarClientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GestionarClientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                GestionarClientes dialog = new GestionarClientes(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnActualizarDatos;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnMostrar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JMenuItem menuItemEliminar;
    private javax.swing.JMenuItem menuItemModificar;
    private javax.swing.JPanel panelFondo;
    private javax.swing.JTable table;
    private javax.swing.JTextField textFieldApellidos;
    private javax.swing.JTextField textFieldNombre;
    private javax.swing.JTextField textFieldTelefono;
    private javax.swing.JTextField textField_direccion;
    private javax.swing.JTextField textField_id;
    // End of variables declaration//GEN-END:variables
}