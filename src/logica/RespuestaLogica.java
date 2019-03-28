/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.util.List;
import modelo.Pqrs;
import modelo.Respuesta;
import persistencia.PqrsJpaController;
import persistencia.RespuestaJpaController;

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
    
}
