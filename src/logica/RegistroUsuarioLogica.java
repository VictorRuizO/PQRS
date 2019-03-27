/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import modelo.Usuario;
import persistencia.*;

/**
 *
 * @author Victor
 */

public class RegistroUsuarioLogica {
    
    private UsuarioJpaController contUsu=new UsuarioJpaController();
    
    public RegistroUsuarioLogica(){
        
    }
    
    public void registrarUsuario(String di, String nom, String ape, Date fechaN, String eps, String dir, String tel, String pass,String tipo){
        Usuario usu=new Usuario();
        usu.setDni(di);
        usu.setNombres(nom);
        usu.setApellidos(ape);
        usu.setFechaNacimiento(fechaN);
        usu.setEps(eps);
        usu.setDireccion(dir);
        usu.setTelefono(tel);
        usu.setContrasena(pass);
        usu.setTipoDocumento(tipo);
        try {
            contUsu.create(usu);
            JOptionPane.showMessageDialog(null, "Usuario registrado con exito");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al registrar usuario");
        }
        
    }
}
