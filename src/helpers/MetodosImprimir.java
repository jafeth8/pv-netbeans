package helpers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;

import br.com.adilson.util.Extenso;
import br.com.adilson.util.PrinterMatrix;
import helpers.sql.DatosTicket;
import java.time.LocalDate;
import java.util.HashMap;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;

/**
 *
 * @author jafeth888
 */
public class MetodosImprimir {
    
    DatosTicket instanciaTicket = new DatosTicket();
    
    HashMap<String, String> hashDatosTicket=instanciaTicket.obtenerDatos("1");
    
    String establecimiento=hashDatosTicket.get("establecimiento");
    String direccion=hashDatosTicket.get("direccion");
    String telefono=hashDatosTicket.get("telefono");
    String localidad=hashDatosTicket.get("localidad");
    String estado= hashDatosTicket.get("estado");
    
    
    
    
    
    public void imprimir(String pagar,JTable tablaCompras,JLabel TOTAL,JLabel Cambio,JLabel UsuarioLabel){	
	Calendar fecha = new GregorianCalendar();
        int anio = fecha.get(Calendar.YEAR);
        int mes = fecha.get(Calendar.MONTH)+1;
        int dia = fecha.get(Calendar.DAY_OF_MONTH);
        int hora = fecha.get(Calendar.HOUR_OF_DAY);
        int minuto = fecha.get(Calendar.MINUTE);
        int segundo = fecha.get(Calendar.SECOND);	
		
        try {
            PrinterMatrix printer = new PrinterMatrix();
            Extenso e1 = new Extenso();

            e1.setNumber(10);
            //Definir el tamanho del papel para la impresion de dinamico y 32 columnas
            int filas = tablaCompras.getRowCount();
            int tamanio = filas+15;
            printer.setOutSize(tamanio, 80);

            //Imprimir = 1ra linea de la columa de 1 a 32
            printer.printTextWrap(0, 1, 5, 80, "=======================================================");
            printer.printTextWrap(1, 1, 40, 80, direccion); //Nombre establecimiento
            printer.printTextWrap(1, 1, 5, 80, establecimiento); //Barrio
            printer.printTextWrap(2, 1, 40, 80, "Tel. "+telefono); //Direccion
            printer.printTextWrap(2, 1, 10, 80, localidad+","+estado); //Codigo Postal

            printer.printTextWrap(3, 1, 0, 40, "Fecha: "+dia+"/"+mes+"/"+anio); //Aqui va la fecha de recibo
            printer.printTextWrap(3, 1, 40, 80, "Hora"+hora+":"+minuto+":"+segundo); //Aqui va la hora de recibo


            //printer.printTextWrap(9, 1, 3, 80, "Cliente");//Nombre del Cliente
            //printer.printTextWrap(10,1, 5, 80, "ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½");
            printer.printTextWrap(4,1, 0, 80, "Cant.   Producto    P/U   Sub.T");
            //printer.printTextWrap(12,1, 0, 80, "## ");

            for (int i = 0; i < filas; i++) {
                int p = 5+i; //Fila

                printer.printTextWrap(p , 1, 0, 19 , tablaCompras.getValueAt(i,1).toString());
                printer.printTextWrap(p , 1, 5, 42 , tablaCompras.getValueAt(i,2).toString());
                printer.printTextWrap(p , 1, 20, 49, tablaCompras.getValueAt(i,3).toString());

                String pre1= printer.alinharADireita(10, tablaCompras.getValueAt(i,4).toString());
                printer.printTextWrap(p , 1, 54, 80, pre1);

                //String inp= printer.alinharADireita(7,punto_Venta.jtbl_venta.getValueAt(i,6).toString());
                //printer.printTextWrap(p , 1, 25, 32, inp);
            }

            DecimalFormat formateador = new DecimalFormat("#.###");


            printer.printTextWrap(filas+6, 1, 5, 80, "Subtotal: ");
            printer.printTextWrap(filas+6, 1, 20, 80, "$"+TOTAL.getText());

           // String tot= printer.alinharADireita(10, total);
            printer.printTextWrap(filas+7, 1, 5, 80, "Total a pagar: ");
            printer.printTextWrap(filas+7, 1, 20, 80, "$"+TOTAL.getText());

            //String efe= printer.alinharADireita(10,90);
            printer.printTextWrap(filas+8, 1, 5, 80, "Efectivo : ");
            printer.printTextWrap(filas+8, 1, 20, 80, "$"+pagar);

            //String cam= printer.alinharADireita(10,9);
            printer.printTextWrap(filas+9, 1, 5, 80, "Cambio : ");
            printer.printTextWrap(filas+9, 1, 20, 80, "$"+ Cambio.getText());

            //printer.printTextWrap(filas+21, 1, 5, 80, "ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½");
            printer.printTextWrap(filas+10, 1, 5,80, "!Gracias por su Compra!");
            printer.printTextWrap(filas+11, 1, 5, 80, establecimiento);
            printer.printTextWrap(filas+12, 1, 2, 80, "Atendido por : "+UsuarioLabel.getText());
            //printer.printTextWrap(filas+13, 1, 3, 80, "Contacto: workitapp@gmail.com");
            printer.printTextWrap(filas+13, 1, 3,80, "===================================================================");

            printer.toFile("impresion.txt");

            FileInputStream inputStream = null;
            
            inputStream = new FileInputStream("impresion.txt");
            
            if (inputStream == null) {
               JOptionPane.showMessageDialog(null,"inputStream es nulo","imprimir",JOptionPane.WARNING_MESSAGE);
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
                JOptionPane.showMessageDialog(null,"imprimir: No existen impresoras instaladas");
            }
            //inputStream.close();
        } catch (Exception e2) {
            e2.printStackTrace();
            JOptionPane.showMessageDialog(null,e2.getMessage(),"Error en imprimir",JOptionPane.ERROR_MESSAGE);
        }	
    }

    public void imprimirApartado(JTable tablaCompras,JLabel TOTAL,JLabel UsuarioLabel,String NombreClienteApartados){
        Calendar fecha = new GregorianCalendar();
        int anio = fecha.get(Calendar.YEAR);
        int mes = fecha.get(Calendar.MONTH)+1;
        int dia = fecha.get(Calendar.DAY_OF_MONTH);
        int hora = fecha.get(Calendar.HOUR_OF_DAY);
        int minuto = fecha.get(Calendar.MINUTE);
        int segundo = fecha.get(Calendar.SECOND);	

        try {
            PrinterMatrix printer = new PrinterMatrix();
            Extenso e1 = new Extenso();

            e1.setNumber(10);
            //Definir el tamanho del papel para la impresion de dinamico y 32 columnas
            int filas = tablaCompras.getRowCount();
            int tamanio = filas+15;
            printer.setOutSize(tamanio, 80);

            //Imprimir = 1ra linea de la columa de 1 a 32
            printer.printTextWrap(0, 1, 5, 80, "=======================================================");
            printer.printTextWrap(1, 1, 40, 80, direccion); //Nombre establecimiento
            printer.printTextWrap(1, 1, 5, 80, establecimiento); //Barrio
            printer.printTextWrap(2, 1, 40, 80, "Tel. "+telefono); //Direccion
            printer.printTextWrap(2, 1, 10, 80, localidad+","+estado); //Codigo Postal

            printer.printTextWrap(3, 1, 0, 40, "Fecha: "+dia+"/"+mes+"/"+anio); //Aqui va la fecha de recibo
            printer.printTextWrap(3, 1, 40, 80, "Hora"+hora+":"+minuto+":"+segundo); //Aqui va la hora de recibo


            //printer.printTextWrap(9, 1, 3, 80, "Cliente");//Nombre del Cliente
            //printer.printTextWrap(10,1, 5, 80, "������������������������������������������������������������������");
            printer.printTextWrap(4,1, 0, 80, "Cant.   Producto    P/U   Sub.T");
            //printer.printTextWrap(12,1, 0, 80, "## ");

            for (int i = 0; i < filas; i++) {
                int p = 5+i; //Fila

                printer.printTextWrap(p , 1, 0, 19 , tablaCompras.getValueAt(i,1).toString());
                printer.printTextWrap(p , 1, 5, 42 , tablaCompras.getValueAt(i,2).toString());
                printer.printTextWrap(p , 1, 20, 49, tablaCompras.getValueAt(i,3).toString());

                String pre1= printer.alinharADireita(10, tablaCompras.getValueAt(i,4).toString());
                printer.printTextWrap(p , 1, 54, 80, pre1);

                //String inp= printer.alinharADireita(7,punto_Venta.jtbl_venta.getValueAt(i,6).toString());
                //printer.printTextWrap(p , 1, 25, 32, inp);
            }

            DecimalFormat formateador = new DecimalFormat("#.###");



            //String cam= printer.alinharADireita(10,9);
            printer.printTextWrap(filas+6, 1, 5, 80, "Deuda Total  : ");
            printer.printTextWrap(filas+6, 1, 20, 80, "$"+ TOTAL.getText());

            printer.printTextWrap(filas+7, 1, 0, 80, "Nombre cliente: ");
            printer.printTextWrap(filas+7, 1, 20, 80, " "+NombreClienteApartados);
            System.out.println("nombre del cliente--- "+NombreClienteApartados);

            //printer.printTextWrap(filas+21, 1, 5, 80, "������������������������������������������������������������������");
            printer.printTextWrap(filas+8, 1, 5,80, "!Formato de apartado!");
            printer.printTextWrap(filas+9, 1, 5, 80, establecimiento);
            printer.printTextWrap(filas+10, 1, 3, 80, "Cotizado por : "+UsuarioLabel.getText());
            //printer.printTextWrap(filas+13, 1, 3, 80, "Contacto: workitapp@gmail.com");
            printer.printTextWrap(filas+11, 1, 3,80, "===================================================================");

            printer.toFile("impresion.txt");

            FileInputStream inputStream = null;
            
            inputStream = new FileInputStream("impresion.txt");
           
            if (inputStream == null) {
               JOptionPane.showMessageDialog(null,"inputStream es nulo","imprimir apartado",JOptionPane.WARNING_MESSAGE);
               return;
            }

            DocFlavor docFormat = DocFlavor.INPUT_STREAM.AUTOSENSE;
            Doc document = new SimpleDoc(inputStream, docFormat, null);

            PrintRequestAttributeSet attributeSet = new HashPrintRequestAttributeSet();

            PrintService defaultPrintService = PrintServiceLookup.lookupDefaultPrintService();


            if (defaultPrintService != null) {
               DocPrintJob printJob = defaultPrintService.createPrintJob();
               try {
                   printJob.print(document, attributeSet);
               }catch (Exception ex) {
                   ex.printStackTrace();
                   JOptionPane.showMessageDialog(null,ex.getMessage(),"impirmir apartado: printJob.print()", JOptionPane.WARNING_MESSAGE);
               }
            }else {
               System.err.println("No existen impresoras instaladas");
               JOptionPane.showMessageDialog(null, "Imprimir apartado: No existen impresora instaladas");
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,e.getMessage(),"Error al imprimir apartado",JOptionPane.WARNING_MESSAGE);
        }	
    }
    
    public void imprimirCotizacion(JTable tablaCompras,JLabel labelTotal,JLabel usuarioLabel){
        Calendar fecha = new GregorianCalendar();
        int anio = fecha.get(Calendar.YEAR);
        int mes = fecha.get(Calendar.MONTH)+1;
        int dia = fecha.get(Calendar.DAY_OF_MONTH);
        int hora = fecha.get(Calendar.HOUR_OF_DAY);
        int minuto = fecha.get(Calendar.MINUTE);
        int segundo = fecha.get(Calendar.SECOND);	
		
        try {
            PrinterMatrix printer = new PrinterMatrix();
            Extenso e1 = new Extenso();
            
            e1.setNumber(10);
            //Definir el tamanho del papel para la impresion de dinamico y 32 columnas
            int filas = tablaCompras.getRowCount();
            int tamanio = filas+15;
            printer.setOutSize(tamanio, 80);

            //Imprimir = 1ra linea de la columa de 1 a 32
            printer.printTextWrap(0, 1, 5, 80, "=======================================================");
            printer.printTextWrap(1, 1, 40, 80, direccion); //Nombre establecimiento
            printer.printTextWrap(1, 1, 5, 80, establecimiento); //Barrio
            printer.printTextWrap(2, 1, 40, 80, "Tel. "+telefono); //Direccion
            printer.printTextWrap(2, 1, 10, 80, localidad+","+estado); //Codigo Postal

            printer.printTextWrap(3, 1, 0, 40, "Fecha: "+dia+"/"+mes+"/"+anio); //Aqui va la fecha de recibo
            printer.printTextWrap(3, 1, 40, 80, "Hora"+hora+":"+minuto+":"+segundo); //Aqui va la hora de recibo
			    
			   
            //printer.printTextWrap(9, 1, 3, 80, "Cliente");//Nombre del Cliente
            //printer.printTextWrap(10,1, 5, 80, "������������������������������������������������������������������");
            printer.printTextWrap(4,1, 0, 80, "Cant.   Producto    P/U   Sub.T");
            //printer.printTextWrap(12,1, 0, 80, "## ");

            for (int i = 0; i < filas; i++) {
                int p = 5+i; //Fila

                printer.printTextWrap(p , 1, 0, 19 , tablaCompras.getValueAt(i,1).toString());
                printer.printTextWrap(p , 1, 5, 42 , tablaCompras.getValueAt(i,2).toString());
                printer.printTextWrap(p , 1, 20, 49, tablaCompras.getValueAt(i,3).toString());

                String pre1= printer.alinharADireita(10, tablaCompras.getValueAt(i,4).toString());
                printer.printTextWrap(p , 1, 54, 80, pre1);

                //String inp= printer.alinharADireita(7,punto_Venta.jtbl_venta.getValueAt(i,6).toString());
                //printer.printTextWrap(p , 1, 25, 32, inp);
            }
			    
            DecimalFormat formateador = new DecimalFormat("#.###");

            //String cam= printer.alinharADireita(10,9);
            printer.printTextWrap(filas+6, 1, 5, 80, "Cotizacion  : ");
            printer.printTextWrap(filas+6, 1, 20, 80, "$"+ labelTotal.getText());

            //printer.printTextWrap(filas+21, 1, 5, 80, "������������������������������������������������������������������");
            printer.printTextWrap(filas+7, 1, 5,80, "!Formato de cotizacion!");
            printer.printTextWrap(filas+8, 1, 5, 80, establecimiento);
            printer.printTextWrap(filas+9, 1, 3, 80, "Cotizado por : "+usuarioLabel.getText());
            //printer.printTextWrap(filas+13, 1, 3, 80, "Contacto: workitapp@gmail.com");
            printer.printTextWrap(filas+10, 1, 3,80, "===================================================================");

            printer.toFile("impresion.txt");
		       
            FileInputStream inputStream = null;
           
            inputStream = new FileInputStream("impresion.txt");
          
            if (inputStream == null) {
                JOptionPane.showMessageDialog(null,"inputStream es nulo","imprimir cotizacion",JOptionPane.WARNING_MESSAGE);
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
                JOptionPane.showMessageDialog(null,"imprimir cotizacion: No existen impresoras instaladas");
            }	
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,e.getMessage(),"Error al imprimir cotizacion",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
    public void imprimirComprobanteApartados(JTable tablaApartados, String dineroRecibido,String abono,String cambio){
        int fila=tablaApartados.getSelectedRow();
		
        //String dinerorecibido = table.getValueAt(fila, 1).toString();
        //String abono = table.getValueAt(fila, 2).toString();
        //String cambio  =  table.getValueAt(fila, 3).toString();
        Calendar fecha = new GregorianCalendar();
        int anio = fecha.get(Calendar.YEAR);
        int mes = fecha.get(Calendar.MONTH)+1;
        int dia = fecha.get(Calendar.DAY_OF_MONTH);
        //String fecha = table.getValueAt(fila, 4).toString();
        String fechaVenta=LocalDate.now().toString();
        int n=0;
        String deuda = tablaApartados.getValueAt(fila, 5).toString();
		
        //bloque comentando para fines de testing
        /*
        if(n==0) {//esta condicional se uso para poder asignar un return; que detuviera el flujo de la funcion
        Double DeudaFinal2 = Double.parseDouble(deuda) - Double.parseDouble(abono);
        System.out.println("Deuda: "+deuda);
        System.out.println("Abono: "+abono);
        System.err.println(String.valueOf(DeudaFinal2));
        return;
        }
        */
        //fin de bloque comentado para fines de testing
        
        try {
            PrinterMatrix printer = new PrinterMatrix();
            Extenso e1 = new Extenso();

            e1.setNumber(10);
            //Definir el tamanho del papel para la impresion de dinamico y 32 columnas
            // int filas = tablaHistorialVenta.getRowCount();
            //int tamanio = filas+15;
            printer.setOutSize(10, 80);

            //Imprimir = 1ra linea de la columa de 1 a 32

            printer.printTextWrap(0, 1, 5, 80, "=======================================================");
            printer.printTextWrap(1, 1, 40, 80, direccion); //Nombre establecimiento
            printer.printTextWrap(1, 1, 5, 80, establecimiento); //Barrio
            printer.printTextWrap(2, 1, 40, 80, "Tel. "+telefono); //Direccion
            printer.printTextWrap(2, 1, 10, 80, localidad+","+estado); //Codigo Postal
            printer.printTextWrap(3, 1, 0, 40, "Fecha abono: "+fechaVenta);

            printer.printTextWrap(4, 1, 0, 40, "Dinero Recibido: "+dineroRecibido);
            printer.printTextWrap(5, 1, 0, 40, "Abono: "+abono);
            printer.printTextWrap(6, 1, 0, 40, "Cambio : "+cambio);
            Double DeudaFinal = Double.parseDouble(deuda) - Double.parseDouble(abono);
            printer.printTextWrap(7, 1, 0, 40, "Deuda : "+ String.valueOf(DeudaFinal));

            DecimalFormat formateador = new DecimalFormat("#.###");

            printer.printTextWrap(8, 1, 5,80,"!Comprobante de pago!");
			   
            printer.printTextWrap(9, 1, 3,80, "===================================================================");

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
                JOptionPane.showMessageDialog(null,"No existen impresoras instaladas metodo: helpers.MetodosImprimir.imprimirComprobanteApartado()");
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            JOptionPane.showMessageDialog(null,e2.getMessage(),"Error al imprimir comprobante apartado",JOptionPane.ERROR_MESSAGE);
        }
		
    }
}
