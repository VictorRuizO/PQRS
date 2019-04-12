/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.Dependencia;
import modelo.EncargDep;
import modelo.EncargadoDependencia;
import persistencia.EncargadoDependenciaJpaController;
import persistencia.EncargDepJpaController;
import persistencia.DependenciaJpaController;

/**
 *
 * @author Alex
 */
public class RegistroEncargadoLogica {
    
    private EncargadoDependenciaJpaController encCont=new EncargadoDependenciaJpaController();
    private EncargDepJpaController endeCont = new EncargDepJpaController();
    private DependenciaJpaController depCont = new DependenciaJpaController();
    
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

    public List<String> obtenerDependencias() {
        List<Dependencia> depends = depCont.findDependenciaEntities();
        List<String> todas=new ArrayList<String>();

        for(Dependencia d:depends){
            todas.add(d.getCodigo());                 
        }
        
        return todas;
    }
    
}
