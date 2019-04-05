/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.Dependencia;
import persistencia.DependenciaJpaController;

/**
 *
 * @author Victor
 */
public class DependenciaLogica {
    
    private DependenciaJpaController depCon=new DependenciaJpaController();
    
    public DependenciaLogica(){
        
    }
    
    public List<String> obtenerDependenciasString(){
        List<Dependencia> deps = depCon.findDependenciaEntities();
        List<String> cadenas = new ArrayList<>();
        for(Dependencia d:deps){
            cadenas.add(d.getNombre());
        }
        return cadenas;
    }
    
    public List<Dependencia> obtenerDependencias(){
        return depCon.findDependenciaEntities(); 
    }
    
    public boolean registrarDependecia(String cod, String nom){
        Dependencia dep=new Dependencia();
        
        dep.setCodigo(cod);
        dep.setNombre(nom);

        try {
            depCon.create(dep);
            JOptionPane.showMessageDialog(null, "Dependencia registrada con exito");
            return false;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al registrar dependencia");
        }
        
        return true;
        
    }
    
}
