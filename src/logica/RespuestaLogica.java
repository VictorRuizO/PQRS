/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.Pqrs;
import modelo.Respuesta;
import modelo.EncargadoDependencia;
import persistencia.PqrsJpaController;
import persistencia.RespuestaJpaController;
import persistencia.EncargadoDependenciaJpaController;

/**
 *
 * @author Victor
 */
public class RespuestaLogica {
    
    private RespuestaJpaController resCont= new RespuestaJpaController();
    
    public RespuestaLogica(){
        
    }
    
    public List<Respuesta> obtenerRespuestas(Pqrs pq){
        return resCont.findRespuestaEntities(pq);
    }
    
    public void registroRespuesta( Date hora, Date fecha ,String descripcion, Pqrs codpqrs, EncargadoDependencia encargado) {
        Respuesta res = new Respuesta();
        res.setDescripcion(descripcion);
        res.setFecha(fecha);
        res.setHora(hora);
        res.setCodPqrs(codpqrs);
        res.setEncargado(encargado);
        
        resCont.create(res);
        
        JOptionPane.showMessageDialog(null, "Su respuesta fue registrada exitosamente");
        
    }
    
}
