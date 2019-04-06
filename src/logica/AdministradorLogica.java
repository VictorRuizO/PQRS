/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Header;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.Administrador;
import modelo.Pqrs;
import modelo.Usuario;
import persistencia.AdministradorJpaController;
import persistencia.PqrsJpaController;
import persistencia.UsuarioJpaController;

/**
 *
 * @author Alex
 */
public class AdministradorLogica {
    private  AdministradorJpaController adminCon = new AdministradorJpaController();
    private PqrsJpaController pqCont=new PqrsJpaController();
    private UsuarioJpaController usuCont=new UsuarioJpaController();
    
    public AdministradorLogica(){
}
        
    
    public Administrador getAdmin(String di, String pass){
        Administrador admin=adminCon.findAdministrador(di);
        if(admin==null)
            JOptionPane.showMessageDialog(null, "Error, Administrador no registrado");
        else if(!admin.getContrasena().equals(pass))
            JOptionPane.showMessageDialog(null, "Error, Contraseña incorrecta");
        
        if(admin!=null && admin.getContrasena().equals(pass))
            return admin;
        return null;
    }
    
    public boolean verificarAdmin(String di){
        Administrador admin = adminCon.findAdministrador(di);
        if(admin==null)
            return false;
        return true;
    }
    
    public boolean generarReporteUsuario(String usuario,String ruta){
        try {
            FileOutputStream archivo = new FileOutputStream(ruta);
            Document doc = new Document();
            PdfWriter.getInstance(doc, archivo);
            doc.setMargins(30, 30, 50, 50);
            doc.open();
            doc.add(new Paragraph("\t \t \t Informe del usuario: "+usuario+"\n\n",
				FontFactory.getFont("arial",   // fuente
				20,                            // tamaño
				Font.BOLD)));
            PdfPTable table = new PdfPTable(6);
            
            table.addCell("Código");
            table.addCell("Titulo");
            table.addCell("Tipo");
            table.addCell("Fecha");
            table.addCell("Dependencia");
            table.addCell("Descripción");
            
            List<Pqrs> pqs = pqCont.findPqrsByUsuario(usuario);
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            for(Pqrs p:pqs){
                table.addCell(""+p.getCodigo());
                table.addCell(p.getTitulo());
                table.addCell(p.getTipo());
                table.addCell(dateFormat.format(p.getFecha()));
                table.addCell(p.getCodigoDependencia().getCodigo());
                table.addCell(p.getDecripcion());
            }
            //PdfPCell celdaFinal = new PdfPCell(new Paragraph("Final de la tabla"));
            //celdaFinal.setColspan(6);
            //table.addCell(celdaFinal);
            doc.add(table);
            doc.close();
            JOptionPane.showMessageDialog(null, "Reporte generado con exito");
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return false;
        }
    }
    
    public boolean existeUsuario(String usu){
        Usuario u=usuCont.findUsuario(usu);
        if(u==null)
            return false;
        return true;
    }
    
}