/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.sql.Date;
import javax.swing.JOptionPane;
import modelo.EncargadoDependencia;
import modelo.Usuario;
import persistencia.EncargadoDependenciaJpaController;

/**
 *
 * @author Alex
 */
public class RegistroEncargadoLogica {
    private EncargadoDependenciaJpaController encCont=new EncargadoDependenciaJpaController();
    
    public RegistroEncargadoLogica(){
    }
  
     public boolean registrarEncargado(String di, String contra, String nom, String ape, Date fechaI, String ip){
        EncargadoDependencia enc=new EncargadoDependencia();
        enc.setDocumentoLaboral(di);
        enc.setContrasena(contra);
        enc.setNombres(nom);
        enc.setApellidos(ape);
        enc.setFechaIngreso(fechaI);
        enc.setIpAcceso(ip);
        try {
            encCont.create(enc);
            JOptionPane.showMessageDialog(null, "Encargado registrado con exito");
            return false;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al registrar Encargado");
        }
        
        return true;
        
    }
    
}
