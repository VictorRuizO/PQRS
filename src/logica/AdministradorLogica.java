/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import javax.swing.JOptionPane;
import modelo.Administrador;
import modelo.Usuario;
import persistencia.AdministradorJpaController;

/**
 *
 * @author Alex
 */
public class AdministradorLogica {
    private  AdministradorJpaController adminCon = new AdministradorJpaController();
    
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
    
}