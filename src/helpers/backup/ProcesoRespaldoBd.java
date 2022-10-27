
package helpers.backup;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.swing.SwingWorker;
import puntodeventa.bd.Ruta;

/**
 *
 * @author jafeth8
 */
public class ProcesoRespaldoBd extends SwingWorker<Void, Void>{
    private String ruta;

    public ProcesoRespaldoBd(String ruta) {
        // TODO Auto-generated constructor stub
        this.setProgress(0);
        this.ruta = ruta;
    }
    
    public void crearRespaldoBd(String ruta) throws IOException {

        Process p = Runtime.getRuntime().exec("mysqldump -u root " + Ruta.DATABASE + "");

        //new HiloLector(p.getErrorStream()).start();
        InputStream is = p.getInputStream();// Pedimos la entrada

        FileOutputStream fos = new FileOutputStream(ruta + ".sql"); // creamos el archivo para le respaldo
        byte[] buffer = new byte[1000]; // Creamos una variable de tipo byte para el buffer

        int leido = is.read(buffer); // Devuelve el n�mero de bytes le�dos o -1 si se alcanz� el final del stream.

        while (leido > 0) {
            fos.write(buffer, 0, leido);// Buffer de caracteres, Desplazamiento de partida para empezar a escribir
            // caracteres, N�mero de caracteres para escribir
            leido = is.read(buffer);

        }

        fos.close();// Cierra respaldo
    }
    
    public void procesarRespaldpBd() throws InterruptedException {
        this.setProgress(0);
        for (int i = 0; i <= 100; i++) {
            if (i == 70) {
                try {
                    this.crearRespaldoBd(ruta);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            this.setProgress(i);
            Thread.sleep(10);
        }
        Thread.sleep(1000);
        System.exit(1);

    }

    @Override
    protected Void doInBackground() throws Exception {
        this.setProgress(0);
        this.procesarRespaldpBd();
        return null;
    }

}
