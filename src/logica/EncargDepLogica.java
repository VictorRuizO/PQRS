/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import modelo.Dependencia;
import modelo.EncargDep;
import modelo.EncargDepPK;
import modelo.EncargadoDependencia;
import persistencia.EncargDepJpaController;
import persistencia.DependenciaJpaController;
import persistencia.exceptions.NonexistentEntityException;
/**
 *
 * @author Alex
 */
public class EncargDepLogica {

    private EncargDepJpaController endCont =new EncargDepJpaController();
    private DependenciaJpaController depCont = new DependenciaJpaController();
    private EncargadoLogica enLog = new EncargadoLogica();
    public EncargDepLogica(){
        
    }

    public String obtenerDepAsig(String id){
        List<EncargDep> encargs = endCont.findEncargDepEntities();
        String sal="";
        for(EncargDep e:encargs){
            if (e.getEncargDepPK().getDlEncargado().equals(id))
                sal= e.getEncargDepPK().getCodigoDep();
        }
        return sal;
    }
    public List<String> obtenerDependencias(String id) {
        List<EncargDep> encargs = endCont.findEncargDepEntities();
        List<Dependencia> depends = depCont.findDependenciaEntities();
        List<String> todas=new ArrayList<String>();

        for(Dependencia d:depends){
            todas.add(d.getCodigo());                 
        }
        
        for(EncargDep e:encargs){
            if (e.getEncargDepPK().getDlEncargado().equals(id))
                if(e.getEstado().equals("activo")){
                    todas.remove(e.getEncargDepPK().getCodigoDep());
                }                    
        }
        
        return todas;
    }
    

    public List<String> obtenerDocumentos() {
        List<EncargDep> encargs = endCont.findEncargDepEntities();
        List<String> docs=new ArrayList<String>();
        
        for(EncargDep e:encargs){
            if(!docs.contains(e.getEncargDepPK().getDlEncargado()))
                    docs.add(e.getEncargadoDependencia().getDocumentoLaboral());
        }
        
        return docs;
    }

    public EncargDep buscarEncargado(String depen, String doc) {
        List<EncargDep> encargs = endCont.findEncargDepEntities();
        for(EncargDep e:encargs){
            if(e.getEncargDepPK().getCodigoDep().equals(depen) && 
                    e.getEncargDepPK().getDlEncargado().equals(doc))
                return e;
        }
        JOptionPane.showMessageDialog(null, "no hay encarg");
        return null;        
    }

    public boolean reasignarEncargado(EncargDep emcc, String depev, String depen, EncargadoDependencia encargadoDependencia) {
        EncargDep encN = new EncargDep();
        EncargDep encV = emcc;
        EncargDepPK pkN = emcc.getEncargDepPK();
        List<Dependencia> depends = depCont.findDependenciaEntities();
        Dependencia dep = new Dependencia();
        for(Dependencia d:depends){
            if(d.getCodigo().equals(depen))
                    dep=d;                 
        }
        pkN.setCodigoDep(dep.getCodigo());
        pkN.setCodigoDep(depen);
        encN.setEncargDepPK(pkN);
        encN.setEstado("activo");
        encN.setDependencia(dep);
        encN.setEncargadoDependencia(encargadoDependencia);
        encV.setEstado("inactivo");
        
        
        try {
            endCont.create(encN);
            endCont.edit(encV);
            return false;
        
        } catch (Exception ex) {
            Logger.getLogger(EncargDepLogica.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        return true;
    }
    
    public boolean asignarEncargado(String codDep, String idEmp) {
        EncargDep encN = new EncargDep();
        EncargDepPK pkN = new EncargDepPK();
        EncargadoDependencia endep = enLog.getEncargado2(idEmp);
        List<Dependencia> depends = depCont.findDependenciaEntities();
        Dependencia dep = new Dependencia();
        for(Dependencia d:depends){
            if(d.getCodigo().equals(codDep))
                    dep=d;                 
        }
        pkN.setCodigoDep(dep.getCodigo());
        pkN.setDlEncargado(idEmp);
        encN.setEncargDepPK(pkN);
        encN.setEstado("activo");
        encN.setDependencia(dep);
        encN.setEncargadoDependencia(endep);
        
        
        try {
            endCont.create(encN);
            return false;
        
        } catch (Exception ex) {
            Logger.getLogger(EncargDepLogica.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        return true;
    }
}
