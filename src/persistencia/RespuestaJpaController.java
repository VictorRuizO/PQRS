/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.Pqrs;
import modelo.EncargadoDependencia;
import modelo.Respuesta;
import persistencia.exceptions.NonexistentEntityException;

/**
 *
 * @author Victor
 */
public class RespuestaJpaController implements Serializable {

    public RespuestaJpaController() {
        this.emf = Persistence.createEntityManagerFactory("PQRSPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Respuesta respuesta) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pqrs codPqrs = respuesta.getCodPqrs();
            if (codPqrs != null) {
                codPqrs = em.getReference(codPqrs.getClass(), codPqrs.getCodigo());
                respuesta.setCodPqrs(codPqrs);
            }
            EncargadoDependencia encargado = respuesta.getEncargado();
            if (encargado != null) {
                encargado = em.getReference(encargado.getClass(), encargado.getDocumentoLaboral());
                respuesta.setEncargado(encargado);
            }
            em.persist(respuesta);
            if (codPqrs != null) {
                codPqrs.getRespuestaList().add(respuesta);
                codPqrs = em.merge(codPqrs);
            }
            if (encargado != null) {
                encargado.getRespuestaList().add(respuesta);
                encargado = em.merge(encargado);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Respuesta respuesta) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Respuesta persistentRespuesta = em.find(Respuesta.class, respuesta.getCodigo());
            Pqrs codPqrsOld = persistentRespuesta.getCodPqrs();
            Pqrs codPqrsNew = respuesta.getCodPqrs();
            EncargadoDependencia encargadoOld = persistentRespuesta.getEncargado();
            EncargadoDependencia encargadoNew = respuesta.getEncargado();
            if (codPqrsNew != null) {
                codPqrsNew = em.getReference(codPqrsNew.getClass(), codPqrsNew.getCodigo());
                respuesta.setCodPqrs(codPqrsNew);
            }
            if (encargadoNew != null) {
                encargadoNew = em.getReference(encargadoNew.getClass(), encargadoNew.getDocumentoLaboral());
                respuesta.setEncargado(encargadoNew);
            }
            respuesta = em.merge(respuesta);
            if (codPqrsOld != null && !codPqrsOld.equals(codPqrsNew)) {
                codPqrsOld.getRespuestaList().remove(respuesta);
                codPqrsOld = em.merge(codPqrsOld);
            }
            if (codPqrsNew != null && !codPqrsNew.equals(codPqrsOld)) {
                codPqrsNew.getRespuestaList().add(respuesta);
                codPqrsNew = em.merge(codPqrsNew);
            }
            if (encargadoOld != null && !encargadoOld.equals(encargadoNew)) {
                encargadoOld.getRespuestaList().remove(respuesta);
                encargadoOld = em.merge(encargadoOld);
            }
            if (encargadoNew != null && !encargadoNew.equals(encargadoOld)) {
                encargadoNew.getRespuestaList().add(respuesta);
                encargadoNew = em.merge(encargadoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = respuesta.getCodigo();
                if (findRespuesta(id) == null) {
                    throw new NonexistentEntityException("The respuesta with id " + id + " no longer exists.");
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
            Respuesta respuesta;
            try {
                respuesta = em.getReference(Respuesta.class, id);
                respuesta.getCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The respuesta with id " + id + " no longer exists.", enfe);
            }
            Pqrs codPqrs = respuesta.getCodPqrs();
            if (codPqrs != null) {
                codPqrs.getRespuestaList().remove(respuesta);
                codPqrs = em.merge(codPqrs);
            }
            EncargadoDependencia encargado = respuesta.getEncargado();
            if (encargado != null) {
                encargado.getRespuestaList().remove(respuesta);
                encargado = em.merge(encargado);
            }
            em.remove(respuesta);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Respuesta> findRespuestaEntities() {
        return findRespuestaEntities(true, -1, -1);
    }

    public List<Respuesta> findRespuestaEntities(int maxResults, int firstResult) {
        return findRespuestaEntities(false, maxResults, firstResult);
    }

    private List<Respuesta> findRespuestaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Respuesta.class));
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

    public Respuesta findRespuesta(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Respuesta.class, id);
        } finally {
            em.close();
        }
    }

    public int getRespuestaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Respuesta> rt = cq.from(Respuesta.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
