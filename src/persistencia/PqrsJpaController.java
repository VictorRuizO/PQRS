/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.Usuario;
import modelo.Dependencia;
import modelo.Respuesta;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import modelo.Pqrs;
import persistencia.exceptions.NonexistentEntityException;

/**
 *
 * @author Victor
 */
public class PqrsJpaController implements Serializable {

    public PqrsJpaController() {
        this.emf = Persistence.createEntityManagerFactory("PQRSPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    public int create2(Pqrs pqrs) throws NonexistentEntityException, Exception{
        EntityManager em = null;
        em = getEntityManager();
        em.getTransaction().begin();
        em.persist(pqrs);
        em.flush();
        em.getTransaction().commit();
        em.close();
        return pqrs.getCodigo();
    }

    public void create(Pqrs pqrs) {
        if (pqrs.getRespuestaList() == null) {
            pqrs.setRespuestaList(new ArrayList<Respuesta>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario dniUsuario = pqrs.getDniUsuario();
            if (dniUsuario != null) {
                dniUsuario = em.getReference(dniUsuario.getClass(), dniUsuario.getDni());
                pqrs.setDniUsuario(dniUsuario);
            }
            Dependencia codigoDependencia = pqrs.getCodigoDependencia();
            if (codigoDependencia != null) {
                codigoDependencia = em.getReference(codigoDependencia.getClass(), codigoDependencia.getCodigo());
                pqrs.setCodigoDependencia(codigoDependencia);
            }
            List<Respuesta> attachedRespuestaList = new ArrayList<Respuesta>();
            for (Respuesta respuestaListRespuestaToAttach : pqrs.getRespuestaList()) {
                respuestaListRespuestaToAttach = em.getReference(respuestaListRespuestaToAttach.getClass(), respuestaListRespuestaToAttach.getCodigo());
                attachedRespuestaList.add(respuestaListRespuestaToAttach);
            }
            pqrs.setRespuestaList(attachedRespuestaList);
            em.persist(pqrs);
            if (dniUsuario != null) {
                dniUsuario.getPqrsList().add(pqrs);
                dniUsuario = em.merge(dniUsuario);
            }
            if (codigoDependencia != null) {
                codigoDependencia.getPqrsList().add(pqrs);
                codigoDependencia = em.merge(codigoDependencia);
            }
            for (Respuesta respuestaListRespuesta : pqrs.getRespuestaList()) {
                Pqrs oldCodPqrsOfRespuestaListRespuesta = respuestaListRespuesta.getCodPqrs();
                respuestaListRespuesta.setCodPqrs(pqrs);
                respuestaListRespuesta = em.merge(respuestaListRespuesta);
                if (oldCodPqrsOfRespuestaListRespuesta != null) {
                    oldCodPqrsOfRespuestaListRespuesta.getRespuestaList().remove(respuestaListRespuesta);
                    oldCodPqrsOfRespuestaListRespuesta = em.merge(oldCodPqrsOfRespuestaListRespuesta);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Pqrs pqrs) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pqrs persistentPqrs = em.find(Pqrs.class, pqrs.getCodigo());
            Usuario dniUsuarioOld = persistentPqrs.getDniUsuario();
            Usuario dniUsuarioNew = pqrs.getDniUsuario();
            Dependencia codigoDependenciaOld = persistentPqrs.getCodigoDependencia();
            Dependencia codigoDependenciaNew = pqrs.getCodigoDependencia();
            List<Respuesta> respuestaListOld = persistentPqrs.getRespuestaList();
            List<Respuesta> respuestaListNew = pqrs.getRespuestaList();
            if (dniUsuarioNew != null) {
                dniUsuarioNew = em.getReference(dniUsuarioNew.getClass(), dniUsuarioNew.getDni());
                pqrs.setDniUsuario(dniUsuarioNew);
            }
            if (codigoDependenciaNew != null) {
                codigoDependenciaNew = em.getReference(codigoDependenciaNew.getClass(), codigoDependenciaNew.getCodigo());
                pqrs.setCodigoDependencia(codigoDependenciaNew);
            }
            List<Respuesta> attachedRespuestaListNew = new ArrayList<Respuesta>();
            for (Respuesta respuestaListNewRespuestaToAttach : respuestaListNew) {
                respuestaListNewRespuestaToAttach = em.getReference(respuestaListNewRespuestaToAttach.getClass(), respuestaListNewRespuestaToAttach.getCodigo());
                attachedRespuestaListNew.add(respuestaListNewRespuestaToAttach);
            }
            respuestaListNew = attachedRespuestaListNew;
            pqrs.setRespuestaList(respuestaListNew);
            pqrs = em.merge(pqrs);
            if (dniUsuarioOld != null && !dniUsuarioOld.equals(dniUsuarioNew)) {
                dniUsuarioOld.getPqrsList().remove(pqrs);
                dniUsuarioOld = em.merge(dniUsuarioOld);
            }
            if (dniUsuarioNew != null && !dniUsuarioNew.equals(dniUsuarioOld)) {
                dniUsuarioNew.getPqrsList().add(pqrs);
                dniUsuarioNew = em.merge(dniUsuarioNew);
            }
            if (codigoDependenciaOld != null && !codigoDependenciaOld.equals(codigoDependenciaNew)) {
                codigoDependenciaOld.getPqrsList().remove(pqrs);
                codigoDependenciaOld = em.merge(codigoDependenciaOld);
            }
            if (codigoDependenciaNew != null && !codigoDependenciaNew.equals(codigoDependenciaOld)) {
                codigoDependenciaNew.getPqrsList().add(pqrs);
                codigoDependenciaNew = em.merge(codigoDependenciaNew);
            }
            for (Respuesta respuestaListOldRespuesta : respuestaListOld) {
                if (!respuestaListNew.contains(respuestaListOldRespuesta)) {
                    respuestaListOldRespuesta.setCodPqrs(null);
                    respuestaListOldRespuesta = em.merge(respuestaListOldRespuesta);
                }
            }
            for (Respuesta respuestaListNewRespuesta : respuestaListNew) {
                if (!respuestaListOld.contains(respuestaListNewRespuesta)) {
                    Pqrs oldCodPqrsOfRespuestaListNewRespuesta = respuestaListNewRespuesta.getCodPqrs();
                    respuestaListNewRespuesta.setCodPqrs(pqrs);
                    respuestaListNewRespuesta = em.merge(respuestaListNewRespuesta);
                    if (oldCodPqrsOfRespuestaListNewRespuesta != null && !oldCodPqrsOfRespuestaListNewRespuesta.equals(pqrs)) {
                        oldCodPqrsOfRespuestaListNewRespuesta.getRespuestaList().remove(respuestaListNewRespuesta);
                        oldCodPqrsOfRespuestaListNewRespuesta = em.merge(oldCodPqrsOfRespuestaListNewRespuesta);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = pqrs.getCodigo();
                if (findPqrs(id) == null) {
                    throw new NonexistentEntityException("The pqrs with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pqrs pqrs;
            try {
                pqrs = em.getReference(Pqrs.class, id);
                pqrs.getCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pqrs with id " + id + " no longer exists.", enfe);
            }
            Usuario dniUsuario = pqrs.getDniUsuario();
            if (dniUsuario != null) {
                dniUsuario.getPqrsList().remove(pqrs);
                dniUsuario = em.merge(dniUsuario);
            }
            Dependencia codigoDependencia = pqrs.getCodigoDependencia();
            if (codigoDependencia != null) {
                codigoDependencia.getPqrsList().remove(pqrs);
                codigoDependencia = em.merge(codigoDependencia);
            }
            List<Respuesta> respuestaList = pqrs.getRespuestaList();
            for (Respuesta respuestaListRespuesta : respuestaList) {
                respuestaListRespuesta.setCodPqrs(null);
                respuestaListRespuesta = em.merge(respuestaListRespuesta);
            }
            em.remove(pqrs);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Pqrs> findPqrsEntities() {
        return findPqrsEntities(true, -1, -1);
    }

    public List<Pqrs> findPqrsEntities(int maxResults, int firstResult) {
        return findPqrsEntities(false, maxResults, firstResult);
    }

    private List<Pqrs> findPqrsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Pqrs.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Pqrs findPqrs(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Pqrs.class, id);
        } finally {
            em.close();
        }
    }

    public int getPqrsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Pqrs> rt = cq.from(Pqrs.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public List<Pqrs> findPqrsByUsuario(String usuario){
        String consultar = "select p from Pqrs p where p.dniUsuario.dni='"+usuario+"'";
        try{
            EntityManager em = getEntityManager();
            Query query = em.createQuery(consultar);
        return (List<Pqrs>) query.getResultList();
        }catch (NoResultException e) {
            return null;
        }
    }
    
    public List<Pqrs> findPqrsByTipo(String tipo){
        String consultar = "select p from Pqrs p where p.tipo='"+tipo+"'";
        try{
            EntityManager em = getEntityManager();
            Query query = em.createQuery(consultar);
        return (List<Pqrs>) query.getResultList();
        }catch (NoResultException e) {
            return null;
        }
    }
}
