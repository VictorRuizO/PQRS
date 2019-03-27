/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.util.ArrayList;
import java.util.List;
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
    
}
