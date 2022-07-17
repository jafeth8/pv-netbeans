
package helpers.agregarProductos;
import javax.swing.JOptionPane;
import validaciones.Validaciones;
/**
 *
 * @author jafeth888
 */
public class ValidacionesProductos {
    Validaciones validacion=new Validaciones();
    public boolean verificarPrecioUnitarioProducto(String precioUnitario_formatText, String costoUnitario_formatText) {
        boolean precioUnitarioCorrecto=true;
        if (!validacion.isDouble(precioUnitario_formatText) || !validacion.isDouble(costoUnitario_formatText)) { //se comprueba que los parametros avaluar sean numero y no letras o caracteres
            System.out.println("campos no validos en los campos de precio");
            return precioUnitarioCorrecto=false;
        }

        float precioUnitario = Float.parseFloat(precioUnitario_formatText);
        float costoUnitario = Float.parseFloat(costoUnitario_formatText);

        if (precioUnitario < costoUnitario) {
            JOptionPane.showMessageDialog(null,"El precio unitario debe ser mayor al costo unitario para "+ "obtener ganacias","Actualizacion Abortada", JOptionPane.ERROR_MESSAGE);
            return precioUnitarioCorrecto=false;
        } else if (precioUnitario <= costoUnitario) {
            int opcion = JOptionPane.showConfirmDialog(null,"El precio unitario es igual al costo unitario por lo tanto no "+ "obtendra ninguna ganancia desea continuar de todos modos?","Aviso!", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (opcion != 0) {
                    JOptionPane.showMessageDialog(null, "Operacion Cancelada");
                    return precioUnitarioCorrecto=false;
            }
        }
        return precioUnitarioCorrecto;
	}
}
