/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import javax.swing.JOptionPane;
import modelo.Usuario;
import persistencia.UsuarioJpaController;

/**
 *
 * @author Victor
 */
public class UsuarioLogica {
    
    private UsuarioJpaController usuCon=new UsuarioJpaController();
    
    public UsuarioLogica(){
        
    }
    
    public Usuario getUsuario(String di, String pass){
        Usuario usu=usuCon.findUsuario(di);
        if(usu==null)
            JOptionPane.showMessageDialog(null, "Error, Usuario no registrado");
        else if(!usu.getContrasena().equals(pass))
            JOptionPane.showMessageDialog(null, "Error, Contraseña incorrecta");
        
        if(usu!=null && usu.getContrasena().equals(pass))
            return usu;
        return null;
    }
    
}
