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
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.EncargadoDependencia;
import modelo.Dependencia;
import modelo.EncargDep;
import modelo.EncargDepPK;
import persistencia.exceptions.NonexistentEntityException;
import persistencia.exceptions.PreexistingEntityException;

/**
 *
 * @author Victor
 */
public class EncargDepJpaController implements Serializable {

    public EncargDepJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(EncargDep encargDep) throws PreexistingEntityException, Exception {
        if (encargDep.getEncargDepPK() == null) {
            encargDep.setEncargDepPK(new EncargDepPK());
        }
        encargDep.getEncargDepPK().setDlEncargado(encargDep.getEncargadoDependencia().getDocumentoLaboral());
        encargDep.getEncargDepPK().setCodigoDep(encargDep.getDependencia().getCodigo());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EncargadoDependencia encargadoDependencia = encargDep.getEncargadoDependencia();
            if (encargadoDependencia != null) {
                encargadoDependencia = em.getReference(encargadoDependencia.getClass(), encargadoDependencia.getDocumentoLaboral());
                encargDep.setEncargadoDependencia(encargadoDependencia);
            }
            Dependencia dependencia = encargDep.getDependencia();
            if (dependencia != null) {
                dependencia = em.getReference(dependencia.getClass(), dependencia.getCodigo());
                encargDep.setDependencia(dependencia);
            }
            em.persist(encargDep);
            if (encargadoDependencia != null) {
                encargadoDependencia.getEncargDepList().add(encargDep);
                encargadoDependencia = em.merge(encargadoDependencia);
            }
            if (dependencia != null) {
                dependencia.getEncargDepList().add(encargDep);
                dependencia = em.merge(dependencia);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEncargDep(encargDep.getEncargDepPK()) != null) {
                throw new PreexistingEntityException("EncargDep " + encargDep + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(EncargDep encargDep) throws NonexistentEntityException, Exception {
        encargDep.getEncargDepPK().setDlEncargado(encargDep.getEncargadoDependencia().getDocumentoLaboral());
        encargDep.getEncargDepPK().setCodigoDep(encargDep.getDependencia().getCodigo());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EncargDep persistentEncargDep = em.find(EncargDep.class, encargDep.getEncargDepPK());
            EncargadoDependencia encargadoDependenciaOld = persistentEncargDep.getEncargadoDependencia();
            EncargadoDependencia encargadoDependenciaNew = encargDep.getEncargadoDependencia();
            Dependencia dependenciaOld = persistentEncargDep.getDependencia();
            Dependencia dependenciaNew = encargDep.getDependencia();
            if (encargadoDependenciaNew != null) {
                encargadoDependenciaNew = em.getReference(encargadoDependenciaNew.getClass(), encargadoDependenciaNew.getDocumentoLaboral());
                encargDep.setEncargadoDependencia(encargadoDependenciaNew);
            }
            if (dependenciaNew != null) {
                dependenciaNew = em.getReference(dependenciaNew.getClass(), dependenciaNew.getCodigo());
                encargDep.setDependencia(dependenciaNew);
            }
            encargDep = em.merge(encargDep);
            if (encargadoDependenciaOld != null && !encargadoDependenciaOld.equals(encargadoDependenciaNew)) {
                encargadoDependenciaOld.getEncargDepList().remove(encargDep);
                encargadoDependenciaOld = em.merge(encargadoDependenciaOld);
            }
            if (encargadoDependenciaNew != null && !encargadoDependenciaNew.equals(encargadoDependenciaOld)) {
                encargadoDependenciaNew.getEncargDepList().add(encargDep);
                encargadoDependenciaNew = em.merge(encargadoDependenciaNew);
            }
            if (dependenciaOld != null && !dependenciaOld.equals(dependenciaNew)) {
                dependenciaOld.getEncargDepList().remove(encargDep);
                dependenciaOld = em.merge(dependenciaOld);
            }
            if (dependenciaNew != null && !dependenciaNew.equals(dependenciaOld)) {
                dependenciaNew.getEncargDepList().add(encargDep);
                dependenciaNew = em.merge(dependenciaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                EncargDepPK id = encargDep.getEncargDepPK();
                if (findEncargDep(id) == null) {
                    throw new NonexistentEntityException("The encargDep with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(EncargDepPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EncargDep encargDep;
            try {
                encargDep = em.getReference(EncargDep.class, id);
                encargDep.getEncargDepPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The encargDep with id " + id + " no longer exists.", enfe);
            }
            EncargadoDependencia encargadoDependencia = encargDep.getEncargadoDependencia();
            if (encargadoDependencia != null) {
                encargadoDependencia.getEncargDepList().remove(encargDep);
                encargadoDependencia = em.merge(encargadoDependencia);
            }
            Dependencia dependencia = encargDep.getDependencia();
            if (dependencia != null) {
                dependencia.getEncargDepList().remove(encargDep);
                dependencia = em.merge(dependencia);
            }
            em.remove(encargDep);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<EncargDep> findEncargDepEntities() {
        return findEncargDepEntities(true, -1, -1);
    }

    public List<EncargDep> findEncargDepEntities(int maxResults, int firstResult) {
        return findEncargDepEntities(false, maxResults, firstResult);
    }

    private List<EncargDep> findEncargDepEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(EncargDep.class));
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

    public EncargDep findEncargDep(EncargDepPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(EncargDep.class, id);
        } finally {
            em.close();
        }
    }

    public int getEncargDepCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<EncargDep> rt = cq.from(EncargDep.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
