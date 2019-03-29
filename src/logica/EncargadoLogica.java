/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.EncargDep;
import modelo.EncargDepPK;
import modelo.EncargadoDependencia;
import persistencia.EncargDepJpaController;
import persistencia.EncargadoDependenciaJpaController;

/**
 *
 * @author Victor
 */
public class EncargadoLogica {
    
    private EncargadoDependenciaJpaController encCont=new EncargadoDependenciaJpaController();
    private EncargDepJpaController endCont =new EncargDepJpaController();
    
    public EncargadoLogica(){
        
    }
    
    public EncargadoDependencia getEncargado(String di, String pass){
        EncargadoDependencia enc=encCont.findEncargadoDependencia(di);
        if(enc==null)
            JOptionPane.showMessageDialog(null, "Error, Encargado no registrado");
        else if(!enc.getContrasena().equals(pass))
            JOptionPane.showMessageDialog(null, "Error, Contraseña incorrecta");
        
        if(enc!=null && enc.getContrasena().equals(pass))
            return enc;
        return null;
    }
    
    public boolean verificarEncargado(String di){
        EncargadoDependencia enc=encCont.findEncargadoDependencia(di);
        if(enc==null)
            return false;
        return true;
    }
    
    public String obtenerCodDepsString(EncargadoDependencia en){
        List<EncargDep> encargs = endCont.findEncargDepEntities();
        String depasig = "";
        
        for(EncargDep e:encargs){
            if (e.getEncargDepPK().getDlEncargado().equals(en.getDocumentoLaboral()))
                if(e.getEstado().equals("activo"))
                    depasig = e.getEncargDepPK().getCodigoDep();
        }
        
        return depasig;
    }
    
}
