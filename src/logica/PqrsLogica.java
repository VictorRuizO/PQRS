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
import modelo.Dependencia;
import modelo.Pqrs;
import modelo.Usuario;
import persistencia.*;

/**
 *
 * @author Usuario
 */
public class PqrsLogica {
    
    private DependenciaJpaController depen = new DependenciaJpaController();
    private PqrsJpaController pqr = new PqrsJpaController();
    
    public PqrsLogica(){
        
    }
    
    public void registroPqrs(String titulo, String decripcion, String tipo,Usuario usu,Dependencia dep) {
        Pqrs pq = new Pqrs();
        pq.setTitulo(titulo);
        pq.setDecripcion(decripcion);
        pq.setTipo(tipo);
        pq.setFecha(new Date());
        pq.setDniUsuario(usu);
        pq.setCodigoDependencia(dep);
        
        pqr.create(pq);
        
        JOptionPane.showMessageDialog(null, "Su "+tipo+" fue registrada exitosamente");
    }
    
}
