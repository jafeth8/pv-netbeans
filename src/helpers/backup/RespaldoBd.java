
package helpers.backup;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import puntodeventa.bd.ConexionBd;
import puntodeventa.bd.Ruta;

/**
 *
 * @author jafeth8
 */
public class RespaldoBd {
    ConexionBd cc= ConexionBd.obtenerInstancia();
    Connection cn= cc.conexion();
    public void crearRespaldoBd() throws IOException {

        Process p = Runtime.getRuntime().exec("mysqldump -u root " + Ruta.DATABASE + "");

        new HiloLector(p.getErrorStream()).start();

        InputStream is = p.getInputStream();// Pedimos la entrada
        /*------jafeth8-----*/
        JFileChooser file = new JFileChooser();
        file.showSaveDialog(null);
        File guarda = file.getSelectedFile();
        /*------------------*/
        if (guarda != null) {
            FileOutputStream fos = new FileOutputStream(guarda + ".sql"); // creamos el archivo para le respaldo
            byte[] buffer = new byte[1000]; // Creamos una variable de tipo byte para el buffer

            int leido = is.read(buffer); // Devuelve el n�mero de bytes le�dos o -1 si se alcanz� el final del stream.
            while (leido > 0) {
                fos.write(buffer, 0, leido);// Buffer de caracteres, Desplazamiento de partida para empezar a escribir
                // caracteres, N�mero de caracteres para escribir
                leido = is.read(buffer);
            }

            fos.close();//Cierra respaldo
        } else {
            JOptionPane.showMessageDialog(null, "La operacion fue cancelada!", "AVISO", JOptionPane.WARNING_MESSAGE);
        }

    }
    
    public boolean registro_ruta_respaldo() {
        try {
            String sql = "SELECT * FROM ruta_respaldo";
            Statement st = cn.createStatement();
            ResultSet resultadosConsulta = st.executeQuery(sql);
            if (resultadosConsulta.next()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public String obtenerRutaRespaldo() {

        // SELECT valor FROM cporcentaje_comision
        String sql = "SELECT ruta FROM ruta_respaldo where id='1'";

        String categoria = "";

        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {

                categoria = rs.getString(1);

            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null,"Error"+ex.getMessage(),"No se pudo obtener la ruta de respaldo", 0);
        } finally {
            
        }
        return categoria;
    }

    public void registrarRutaRespaldo(String ruta) {
        try {
            PreparedStatement pst = cn.prepareStatement("INSERT INTO ruta_respaldo" + "(ruta) VALUES (?)");
            pst.setString(1, ruta);
            pst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: "+e.getMessage(),"No se pudo registrar ruta respaldo", JOptionPane.ERROR_MESSAGE); 
        }
    }
    
    public void actualizar_RutaRespaldo(String ruta) {
        PreparedStatement pst;
        try {
            pst = cn.prepareStatement("UPDATE ruta_respaldo SET ruta=? WHERE id='1'");
            pst.setString(1, ruta);
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: "+e.getMessage(),"No se pudo registrar ruta respaldo", JOptionPane.ERROR_MESSAGE); 
        }
    }
    
}
