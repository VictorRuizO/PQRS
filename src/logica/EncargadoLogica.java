/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import javax.swing.JOptionPane;
import modelo.EncargadoDependencia;
import persistencia.EncargadoDependenciaJpaController;

/**
 *
 * @author Victor
 */
public class EncargadoLogica {
    
    private EncargadoDependenciaJpaController encCont=new EncargadoDependenciaJpaController();
    
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
    
}
