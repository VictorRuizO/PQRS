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
import modelo.Respuesta;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import modelo.EncargDep;
import modelo.EncargadoDependencia;
import persistencia.exceptions.IllegalOrphanException;
import persistencia.exceptions.NonexistentEntityException;
import persistencia.exceptions.PreexistingEntityException;

/**
 *
 * @author Victor
 */
public class EncargadoDependenciaJpaController implements Serializable {

    public EncargadoDependenciaJpaController() {
        this.emf = Persistence.createEntityManagerFactory("PQRSPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(EncargadoDependencia encargadoDependencia) throws PreexistingEntityException, Exception {
        if (encargadoDependencia.getRespuestaList() == null) {
            encargadoDependencia.setRespuestaList(new ArrayList<Respuesta>());
        }
        if (encargadoDependencia.getEncargDepList() == null) {
            encargadoDependencia.setEncargDepList(new ArrayList<EncargDep>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Respuesta> attachedRespuestaList = new ArrayList<Respuesta>();
            for (Respuesta respuestaListRespuestaToAttach : encargadoDependencia.getRespuestaList()) {
                respuestaListRespuestaToAttach = em.getReference(respuestaListRespuestaToAttach.getClass(), respuestaListRespuestaToAttach.getCodigo());
                attachedRespuestaList.add(respuestaListRespuestaToAttach);
            }
            encargadoDependencia.setRespuestaList(attachedRespuestaList);
            List<EncargDep> attachedEncargDepList = new ArrayList<EncargDep>();
            for (EncargDep encargDepListEncargDepToAttach : encargadoDependencia.getEncargDepList()) {
                encargDepListEncargDepToAttach = em.getReference(encargDepListEncargDepToAttach.getClass(), encargDepListEncargDepToAttach.getEncargDepPK());
                attachedEncargDepList.add(encargDepListEncargDepToAttach);
            }
            encargadoDependencia.setEncargDepList(attachedEncargDepList);
            em.persist(encargadoDependencia);
            for (Respuesta respuestaListRespuesta : encargadoDependencia.getRespuestaList()) {
                EncargadoDependencia oldEncargadoOfRespuestaListRespuesta = respuestaListRespuesta.getEncargado();
                respuestaListRespuesta.setEncargado(encargadoDependencia);
                respuestaListRespuesta = em.merge(respuestaListRespuesta);
                if (oldEncargadoOfRespuestaListRespuesta != null) {
                    oldEncargadoOfRespuestaListRespuesta.getRespuestaList().remove(respuestaListRespuesta);
                    oldEncargadoOfRespuestaListRespuesta = em.merge(oldEncargadoOfRespuestaListRespuesta);
                }
            }
            for (EncargDep encargDepListEncargDep : encargadoDependencia.getEncargDepList()) {
                EncargadoDependencia oldEncargadoDependenciaOfEncargDepListEncargDep = encargDepListEncargDep.getEncargadoDependencia();
                encargDepListEncargDep.setEncargadoDependencia(encargadoDependencia);
                encargDepListEncargDep = em.merge(encargDepListEncargDep);
                if (oldEncargadoDependenciaOfEncargDepListEncargDep != null) {
                    oldEncargadoDependenciaOfEncargDepListEncargDep.getEncargDepList().remove(encargDepListEncargDep);
                    oldEncargadoDependenciaOfEncargDepListEncargDep = em.merge(oldEncargadoDependenciaOfEncargDepListEncargDep);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEncargadoDependencia(encargadoDependencia.getDocumentoLaboral()) != null) {
                throw new PreexistingEntityException("EncargadoDependencia " + encargadoDependencia + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(EncargadoDependencia encargadoDependencia) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EncargadoDependencia persistentEncargadoDependencia = em.find(EncargadoDependencia.class, encargadoDependencia.getDocumentoLaboral());
            List<Respuesta> respuestaListOld = persistentEncargadoDependencia.getRespuestaList();
            List<Respuesta> respuestaListNew = encargadoDependencia.getRespuestaList();
            List<EncargDep> encargDepListOld = persistentEncargadoDependencia.getEncargDepList();
            List<EncargDep> encargDepListNew = encargadoDependencia.getEncargDepList();
            List<String> illegalOrphanMessages = null;
            for (EncargDep encargDepListOldEncargDep : encargDepListOld) {
                if (!encargDepListNew.contains(encargDepListOldEncargDep)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain EncargDep " + encargDepListOldEncargDep + " since its encargadoDependencia field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Respuesta> attachedRespuestaListNew = new ArrayList<Respuesta>();
            for (Respuesta respuestaListNewRespuestaToAttach : respuestaListNew) {
                respuestaListNewRespuestaToAttach = em.getReference(respuestaListNewRespuestaToAttach.getClass(), respuestaListNewRespuestaToAttach.getCodigo());
                attachedRespuestaListNew.add(respuestaListNewRespuestaToAttach);
            }
            respuestaListNew = attachedRespuestaListNew;
            encargadoDependencia.setRespuestaList(respuestaListNew);
            List<EncargDep> attachedEncargDepListNew = new ArrayList<EncargDep>();
            for (EncargDep encargDepListNewEncargDepToAttach : encargDepListNew) {
                encargDepListNewEncargDepToAttach = em.getReference(encargDepListNewEncargDepToAttach.getClass(), encargDepListNewEncargDepToAttach.getEncargDepPK());
                attachedEncargDepListNew.add(encargDepListNewEncargDepToAttach);
            }
            encargDepListNew = attachedEncargDepListNew;
            encargadoDependencia.setEncargDepList(encargDepListNew);
            encargadoDependencia = em.merge(encargadoDependencia);
            for (Respuesta respuestaListOldRespuesta : respuestaListOld) {
                if (!respuestaListNew.contains(respuestaListOldRespuesta)) {
                    respuestaListOldRespuesta.setEncargado(null);
                    respuestaListOldRespuesta = em.merge(respuestaListOldRespuesta);
                }
            }
            for (Respuesta respuestaListNewRespuesta : respuestaListNew) {
                if (!respuestaListOld.contains(respuestaListNewRespuesta)) {
                    EncargadoDependencia oldEncargadoOfRespuestaListNewRespuesta = respuestaListNewRespuesta.getEncargado();
                    respuestaListNewRespuesta.setEncargado(encargadoDependencia);
                    respuestaListNewRespuesta = em.merge(respuestaListNewRespuesta);
                    if (oldEncargadoOfRespuestaListNewRespuesta != null && !oldEncargadoOfRespuestaListNewRespuesta.equals(encargadoDependencia)) {
                        oldEncargadoOfRespuestaListNewRespuesta.getRespuestaList().remove(respuestaListNewRespuesta);
                        oldEncargadoOfRespuestaListNewRespuesta = em.merge(oldEncargadoOfRespuestaListNewRespuesta);
                    }
                }
            }
            for (EncargDep encargDepListNewEncargDep : encargDepListNew) {
                if (!encargDepListOld.contains(encargDepListNewEncargDep)) {
                    EncargadoDependencia oldEncargadoDependenciaOfEncargDepListNewEncargDep = encargDepListNewEncargDep.getEncargadoDependencia();
                    encargDepListNewEncargDep.setEncargadoDependencia(encargadoDependencia);
                    encargDepListNewEncargDep = em.merge(encargDepListNewEncargDep);
                    if (oldEncargadoDependenciaOfEncargDepListNewEncargDep != null && !oldEncargadoDependenciaOfEncargDepListNewEncargDep.equals(encargadoDependencia)) {
                        oldEncargadoDependenciaOfEncargDepListNewEncargDep.getEncargDepList().remove(encargDepListNewEncargDep);
                        oldEncargadoDependenciaOfEncargDepListNewEncargDep = em.merge(oldEncargadoDependenciaOfEncargDepListNewEncargDep);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = encargadoDependencia.getDocumentoLaboral();
                if (findEncargadoDependencia(id) == null) {
                    throw new NonexistentEntityException("The encargadoDependencia with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EncargadoDependencia encargadoDependencia;
            try {
                encargadoDependencia = em.getReference(EncargadoDependencia.class, id);
                encargadoDependencia.getDocumentoLaboral();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The encargadoDependencia with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<EncargDep> encargDepListOrphanCheck = encargadoDependencia.getEncargDepList();
            for (EncargDep encargDepListOrphanCheckEncargDep : encargDepListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This EncargadoDependencia (" + encargadoDependencia + ") cannot be destroyed since the EncargDep " + encargDepListOrphanCheckEncargDep + " in its encargDepList field has a non-nullable encargadoDependencia field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Respuesta> respuestaList = encargadoDependencia.getRespuestaList();
            for (Respuesta respuestaListRespuesta : respuestaList) {
                respuestaListRespuesta.setEncargado(null);
                respuestaListRespuesta = em.merge(respuestaListRespuesta);
            }
            em.remove(encargadoDependencia);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<EncargadoDependencia> findEncargadoDependenciaEntities() {
        return findEncargadoDependenciaEntities(true, -1, -1);
    }

    public List<EncargadoDependencia> findEncargadoDependenciaEntities(int maxResults, int firstResult) {
        return findEncargadoDependenciaEntities(false, maxResults, firstResult);
    }

    private List<EncargadoDependencia> findEncargadoDependenciaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(EncargadoDependencia.class));
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

    public EncargadoDependencia findEncargadoDependencia(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(EncargadoDependencia.class, id);
        } finally {
            em.close();
        }
    }

    public int getEncargadoDependenciaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<EncargadoDependencia> rt = cq.from(EncargadoDependencia.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
