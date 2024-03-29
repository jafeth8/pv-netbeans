/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package puntodeventa;

import helpers.TableModel;
import helpers.agregarProductos.ValidacionesProductos;
import helpers.sql.Productos;
import java.awt.Graphics;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import validaciones_comprobaciones.ValidacionesComprobaciones;

/**
 *
 * @author jafeth888
 */
public class ActualizarProducto extends javax.swing.JDialog {
    TableModel tableModel=new TableModel();
    /**
     * Creates new form ActualizarProducto
     */
    public ActualizarProducto(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        //this.setContentPane(new ActualizarProducto.ImagenFondo());
        initComponents();
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
        codigoBarraTextField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        descripcionTextField = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        cantidadActualTextField = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        sumarTextfield = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        restarTextField = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        costoUnitarioTextField = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        precioUnitarioTextField = new javax.swing.JTextField();
        categoriaTextField = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("ACTUALIZAR DATOS");
        setResizable(false);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setText("CODIGO BARRA");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("PRODUCTO");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("CANTIDAD ACTUAL:");

        cantidadActualTextField.setEditable(false);

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("SUMAR:");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setText("RESTAR:");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setText("COSTO UNITARIO");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setText("PRECIO UNITARIO");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel8.setText("CATEGORIA");

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/actualizar_producto.png"))); // NOI18N
        jButton1.setText("ACTUALIZAR DATOS!");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(precioUnitarioTextField)
                            .addComponent(costoUnitarioTextField)
                            .addComponent(codigoBarraTextField)
                            .addComponent(descripcionTextField)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 114, Short.MAX_VALUE)
                                    .addComponent(cantidadActualTextField))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(sumarTextfield, javax.swing.GroupLayout.DEFAULT_SIZE, 114, Short.MAX_VALUE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(restarTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 114, Short.MAX_VALUE)))
                            .addComponent(categoriaTextField)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(68, 68, 68))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addGroup(layout.createSequentialGroup()
                .addGap(111, 111, 111)
                .addComponent(jButton1)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(codigoBarraTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(descripcionTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cantidadActualTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sumarTextfield, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(restarTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(costoUnitarioTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(precioUnitarioTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(categoriaTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1)
                .addContainerGap(11, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        
        ValidacionesComprobaciones validacion=new ValidacionesComprobaciones();
        Productos instanciaProducto=new Productos();
        String codigoBarra,descripcion,cantidadProducto,costoUnitario,precioUnitario,categoria;
        codigoBarra=codigoBarraTextField.getText(); descripcion=descripcionTextField.getText();
        costoUnitario=costoUnitarioTextField.getText(); precioUnitario=precioUnitarioTextField.getText();
        categoria=categoriaTextField.getText();
        if(descripcion.equals("") || costoUnitario.equals("") || precioUnitario.equals("") || categoria.equals("")){
            JOptionPane.showMessageDialog(null,"Parece que algunos campos estan vacios");
            return;
        }
        double cantidadDouble,cantidadRestaDouble;
        double cantidadDoubleActual;
        double sumaCantidades=0;
        double restaCantidades;
        boolean campoSumar;//determina si la catidad debe ser evaluada como un numero o no en caso de que el campo Cantidad este vacio
        boolean campoRestar=false;
        if(sumarTextfield.getText().equals("")) {
            cantidadProducto=cantidadActualTextField.getText();
            campoSumar=false;
        }else {
            cantidadProducto=sumarTextfield.getText();
            campoSumar =true;

            cantidadDouble=Double.parseDouble(sumarTextfield.getText());
            cantidadDoubleActual=Double.parseDouble(cantidadActualTextField.getText());
            sumaCantidades=cantidadDoubleActual+cantidadDouble;

            cantidadProducto=""+sumaCantidades;
        }

        /////CAMPO textFieldRestar Depende del campo jtextFieldSumar
        if(restarTextField.getText().equals("")&& campoSumar==false) {
            cantidadProducto=cantidadActualTextField.getText();
        }else if(restarTextField.getText().equals("")&& campoSumar) {
            cantidadProducto=""+sumaCantidades;
            campoRestar=false;
        }else if(!restarTextField.getText().equals("") && campoSumar){
            campoRestar=true;
            cantidadRestaDouble=Double.parseDouble(restarTextField.getText());
            restaCantidades=sumaCantidades-cantidadRestaDouble;
            if(restaCantidades<0) {
                JOptionPane.showMessageDialog(null,"el resultado no puede ser menor a cero verifique el campo resta");
                return;
            }
            cantidadProducto=""+restaCantidades;

        }else if(!restarTextField.getText().equals("") && campoSumar==false) {
            campoRestar=true;
            cantidadRestaDouble=Double.parseDouble(restarTextField.getText());
            cantidadDoubleActual=Double.parseDouble(cantidadActualTextField.getText());
            restaCantidades=cantidadDoubleActual-cantidadRestaDouble;
            if(restaCantidades<0) {
                JOptionPane.showMessageDialog(null,"el resultado no puede ser menor a cero verifique campo resta");
                return;
            }
            cantidadProducto=""+restaCantidades;
        }

        if(categoria.equals("unidades")==false && campoSumar) {
            if(validacion.isNumeric(sumarTextfield.getText())==false) {
                JOptionPane.showMessageDialog(null,"solos los productos con categoria ' UNIDADES ' pueden tener numero decimal en el apartado de la cantidad para sumar");
                return;
            }
        }

        if(categoria.equals("unidades")==false && campoRestar) {
            if(validacion.isNumeric(restarTextField.getText())==false) {
                JOptionPane.showMessageDialog(null,"solos los productos con categoria ' UNIDADES ' pueden tener numero decimal en el apartado de la cantidad para restar");
                return;
            }
        }
        
        ValidacionesProductos validacionProducto=new ValidacionesProductos();
        /*-----VERIFICAR QUE EL PRECIO UNITARIO SEA MAYOR O IGUAL AL COSTO VENTA PARA OBTENER GANANCIAS-----*/
        if(validacionProducto.verificarPrecioUnitarioProducto(precioUnitario, costoUnitario)!=true) return;
        
        instanciaProducto.ActualizarProducto(codigoBarra,cantidadProducto,descripcion,precioUnitario,costoUnitario,categoria);
        
        if(VentanaPrincipal.codigoBarra.getText().equals("")) {
            tableModel.mostrarDatosProductos("",VentanaPrincipal.jTablaProductos);
        }else {
            tableModel.mostrarDatosProductos(VentanaPrincipal.codigoBarra.getText(),VentanaPrincipal.jTablaProductos);
        }
        
        this.setVisible(false);
        
        JOptionPane.showMessageDialog(null, "SE A ACTUALIZADO CORRECTAMENTE EL PRODUCTO");
        
        ProductosAgotados.dialog.mostrarProductosAgotados("");//se actualiza el registro de productos agotados
        
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(ActualizarProducto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ActualizarProducto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ActualizarProducto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ActualizarProducto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ActualizarProducto dialog = new ActualizarProducto(new javax.swing.JFrame(), true);
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
    private javax.swing.JTextField cantidadActualTextField;
    private javax.swing.JTextField categoriaTextField;
    private javax.swing.JTextField codigoBarraTextField;
    private javax.swing.JTextField costoUnitarioTextField;
    private javax.swing.JTextField descripcionTextField;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JTextField precioUnitarioTextField;
    private javax.swing.JTextField restarTextField;
    private javax.swing.JTextField sumarTextfield;
    // End of variables declaration//GEN-END:variables
    public class ImagenFondo extends JPanel{

        @Override
        public void paint(Graphics g) {
            ImageIcon imagen = new ImageIcon(getClass().getResource("../img/fondoSecundario.png"));
            g.drawImage(imagen.getImage(),0,0,getWidth(),getHeight(),this);
            setOpaque(false);
            super.paint(g); 
        }
    }
    public void establecerTextoCodigoBarra(String codigoBarra){
        codigoBarraTextField.setText(codigoBarra);
    }
    public void establecerTextoDescripcion(String descripcion){
        descripcionTextField.setText(descripcion);
    }
    public void establecerTextoCantidadActual(String cantidad){
        cantidadActualTextField.setText(cantidad);
    }
    public void establecerTextoCostoUnitario(String costoUnitario){
        costoUnitarioTextField.setText(costoUnitario);
    }
    public void establecerTextoPrecioUnitario(String precioUnitario){
        precioUnitarioTextField.setText(precioUnitario);
    }
    public void establecerTextoCategoria(String categoria){
        categoriaTextField.setText(categoria);
    }
}
