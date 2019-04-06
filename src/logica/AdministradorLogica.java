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
import java.util.ArrayList;
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
            Font font;
            font=FontFactory.getFont("arial",20,Font.BOLD);
            doc.add(new Paragraph("\t \t \t Informe del usuario: "+usuario+"\n\n",font));
            PdfPTable table = new PdfPTable(6);
            font=FontFactory.getFont("arial",10,Font.BOLD);
            table.addCell(new Paragraph("Código",font));
            table.addCell(new Paragraph("Titulo",font));
            table.addCell(new Paragraph("Tipo",font));
            table.addCell(new Paragraph("Fecha",font));
            table.addCell(new Paragraph("Dependencia",font));
            table.addCell(new Paragraph("Descripción",font));
            
            List<Pqrs> pqs = pqCont.findPqrsByUsuario(usuario);
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            font=FontFactory.getFont("arial",9,Font.NORMAL);
            for(Pqrs p:pqs){
                table.addCell(new Paragraph(""+p.getCodigo(),font));
                table.addCell(new Paragraph(p.getTitulo(),font));
                table.addCell(new Paragraph(p.getTipo(),font));
                table.addCell(new Paragraph(dateFormat.format(p.getFecha()),font));
                table.addCell(new Paragraph(p.getCodigoDependencia().getCodigo(),font));
                table.addCell(new Paragraph(p.getDecripcion(),font));
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
    
    public boolean generarReporteSolicitud(String tipo,String ruta){
        try {
            FileOutputStream archivo = new FileOutputStream(ruta);
            Document doc = new Document();
            PdfWriter.getInstance(doc, archivo);
            doc.setMargins(30, 30, 50, 50);
            doc.open();
            Font font;
            font=FontFactory.getFont("arial",20,Font.BOLD);
            doc.add(new Paragraph("\t \t \t Informe de Solicitud: "+tipo+"\n\n",font));
            PdfPTable table = new PdfPTable(7);
            
            font=FontFactory.getFont("arial",10,Font.BOLD);
            table.addCell(new Paragraph("PQRS",font));
            table.addCell(new Paragraph("DNI",font));
            table.addCell(new Paragraph("Nombres",font));
            table.addCell(new Paragraph("Apellidos",font));
            table.addCell(new Paragraph("Fecha Nac",font));
            table.addCell(new Paragraph("EPS",font));
            table.addCell(new Paragraph("Telefono",font));
          
            List<Pqrs> pqs = pqCont.findPqrsByTipo(tipo);
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            font=FontFactory.getFont("arial",9,Font.NORMAL);
            for(Pqrs p:pqs){
                table.addCell(new Paragraph(""+p.getCodigo(),font));
                table.addCell(new Paragraph(p.getDniUsuario().getDni(),font));
                table.addCell(new Paragraph(p.getDniUsuario().getNombres(),font));
                table.addCell(new Paragraph(p.getDniUsuario().getApellidos(),font));
                table.addCell(new Paragraph(dateFormat.format(p.getDniUsuario().getFechaNacimiento()),font));
                table.addCell(new Paragraph(p.getDniUsuario().getEps(),font));
                table.addCell(new Paragraph(p.getDniUsuario().getTelefono(),font));
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
    
    
    public List<String> obtenerUsuarios(){
        List<Usuario> usus = usuCont.findUsuarioEntities();
        List<String> dnis = new ArrayList<>();
        for(Usuario u:usus){
            dnis.add(u.getDni());
        }
        return dnis;
    }
    
}