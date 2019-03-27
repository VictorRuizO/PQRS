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
import modelo.Pqrs;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import modelo.Dependencia;
import modelo.EncargDep;
import persistencia.exceptions.IllegalOrphanException;
import persistencia.exceptions.NonexistentEntityException;
import persistencia.exceptions.PreexistingEntityException;

/**
 *
 * @author Victor
 */
public class DependenciaJpaController implements Serializable {

    public DependenciaJpaController() {
        this.emf = Persistence.createEntityManagerFactory("PQRSPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Dependencia dependencia) throws PreexistingEntityException, Exception {
        if (dependencia.getPqrsList() == null) {
            dependencia.setPqrsList(new ArrayList<Pqrs>());
        }
        if (dependencia.getEncargDepList() == null) {
            dependencia.setEncargDepList(new ArrayList<EncargDep>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Pqrs> attachedPqrsList = new ArrayList<Pqrs>();
            for (Pqrs pqrsListPqrsToAttach : dependencia.getPqrsList()) {
                pqrsListPqrsToAttach = em.getReference(pqrsListPqrsToAttach.getClass(), pqrsListPqrsToAttach.getCodigo());
                attachedPqrsList.add(pqrsListPqrsToAttach);
            }
            dependencia.setPqrsList(attachedPqrsList);
            List<EncargDep> attachedEncargDepList = new ArrayList<EncargDep>();
            for (EncargDep encargDepListEncargDepToAttach : dependencia.getEncargDepList()) {
                encargDepListEncargDepToAttach = em.getReference(encargDepListEncargDepToAttach.getClass(), encargDepListEncargDepToAttach.getEncargDepPK());
                attachedEncargDepList.add(encargDepListEncargDepToAttach);
            }
            dependencia.setEncargDepList(attachedEncargDepList);
            em.persist(dependencia);
            for (Pqrs pqrsListPqrs : dependencia.getPqrsList()) {
                Dependencia oldCodigoDependenciaOfPqrsListPqrs = pqrsListPqrs.getCodigoDependencia();
                pqrsListPqrs.setCodigoDependencia(dependencia);
                pqrsListPqrs = em.merge(pqrsListPqrs);
                if (oldCodigoDependenciaOfPqrsListPqrs != null) {
                    oldCodigoDependenciaOfPqrsListPqrs.getPqrsList().remove(pqrsListPqrs);
                    oldCodigoDependenciaOfPqrsListPqrs = em.merge(oldCodigoDependenciaOfPqrsListPqrs);
                }
            }
            for (EncargDep encargDepListEncargDep : dependencia.getEncargDepList()) {
                Dependencia oldDependenciaOfEncargDepListEncargDep = encargDepListEncargDep.getDependencia();
                encargDepListEncargDep.setDependencia(dependencia);
                encargDepListEncargDep = em.merge(encargDepListEncargDep);
                if (oldDependenciaOfEncargDepListEncargDep != null) {
                    oldDependenciaOfEncargDepListEncargDep.getEncargDepList().remove(encargDepListEncargDep);
                    oldDependenciaOfEncargDepListEncargDep = em.merge(oldDependenciaOfEncargDepListEncargDep);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findDependencia(dependencia.getCodigo()) != null) {
                throw new PreexistingEntityException("Dependencia " + dependencia + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Dependencia dependencia) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Dependencia persistentDependencia = em.find(Dependencia.class, dependencia.getCodigo());
            List<Pqrs> pqrsListOld = persistentDependencia.getPqrsList();
            List<Pqrs> pqrsListNew = dependencia.getPqrsList();
            List<EncargDep> encargDepListOld = persistentDependencia.getEncargDepList();
            List<EncargDep> encargDepListNew = dependencia.getEncargDepList();
            List<String> illegalOrphanMessages = null;
            for (Pqrs pqrsListOldPqrs : pqrsListOld) {
                if (!pqrsListNew.contains(pqrsListOldPqrs)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Pqrs " + pqrsListOldPqrs + " since its codigoDependencia field is not nullable.");
                }
            }
            for (EncargDep encargDepListOldEncargDep : encargDepListOld) {
                if (!encargDepListNew.contains(encargDepListOldEncargDep)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain EncargDep " + encargDepListOldEncargDep + " since its dependencia field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Pqrs> attachedPqrsListNew = new ArrayList<Pqrs>();
            for (Pqrs pqrsListNewPqrsToAttach : pqrsListNew) {
                pqrsListNewPqrsToAttach = em.getReference(pqrsListNewPqrsToAttach.getClass(), pqrsListNewPqrsToAttach.getCodigo());
                attachedPqrsListNew.add(pqrsListNewPqrsToAttach);
            }
            pqrsListNew = attachedPqrsListNew;
            dependencia.setPqrsList(pqrsListNew);
            List<EncargDep> attachedEncargDepListNew = new ArrayList<EncargDep>();
            for (EncargDep encargDepListNewEncargDepToAttach : encargDepListNew) {
                encargDepListNewEncargDepToAttach = em.getReference(encargDepListNewEncargDepToAttach.getClass(), encargDepListNewEncargDepToAttach.getEncargDepPK());
                attachedEncargDepListNew.add(encargDepListNewEncargDepToAttach);
            }
            encargDepListNew = attachedEncargDepListNew;
            dependencia.setEncargDepList(encargDepListNew);
            dependencia = em.merge(dependencia);
            for (Pqrs pqrsListNewPqrs : pqrsListNew) {
                if (!pqrsListOld.contains(pqrsListNewPqrs)) {
                    Dependencia oldCodigoDependenciaOfPqrsListNewPqrs = pqrsListNewPqrs.getCodigoDependencia();
                    pqrsListNewPqrs.setCodigoDependencia(dependencia);
                    pqrsListNewPqrs = em.merge(pqrsListNewPqrs);
                    if (oldCodigoDependenciaOfPqrsListNewPqrs != null && !oldCodigoDependenciaOfPqrsListNewPqrs.equals(dependencia)) {
                        oldCodigoDependenciaOfPqrsListNewPqrs.getPqrsList().remove(pqrsListNewPqrs);
                        oldCodigoDependenciaOfPqrsListNewPqrs = em.merge(oldCodigoDependenciaOfPqrsListNewPqrs);
                    }
                }
            }
            for (EncargDep encargDepListNewEncargDep : encargDepListNew) {
                if (!encargDepListOld.contains(encargDepListNewEncargDep)) {
                    Dependencia oldDependenciaOfEncargDepListNewEncargDep = encargDepListNewEncargDep.getDependencia();
                    encargDepListNewEncargDep.setDependencia(dependencia);
                    encargDepListNewEncargDep = em.merge(encargDepListNewEncargDep);
                    if (oldDependenciaOfEncargDepListNewEncargDep != null && !oldDependenciaOfEncargDepListNewEncargDep.equals(dependencia)) {
                        oldDependenciaOfEncargDepListNewEncargDep.getEncargDepList().remove(encargDepListNewEncargDep);
                        oldDependenciaOfEncargDepListNewEncargDep = em.merge(oldDependenciaOfEncargDepListNewEncargDep);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = dependencia.getCodigo();
                if (findDependencia(id) == null) {
                    throw new NonexistentEntityException("The dependencia with id " + id + " no longer exists.");
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
            Dependencia dependencia;
            try {
                dependencia = em.getReference(Dependencia.class, id);
                dependencia.getCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The dependencia with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Pqrs> pqrsListOrphanCheck = dependencia.getPqrsList();
            for (Pqrs pqrsListOrphanCheckPqrs : pqrsListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Dependencia (" + dependencia + ") cannot be destroyed since the Pqrs " + pqrsListOrphanCheckPqrs + " in its pqrsList field has a non-nullable codigoDependencia field.");
            }
            List<EncargDep> encargDepListOrphanCheck = dependencia.getEncargDepList();
            for (EncargDep encargDepListOrphanCheckEncargDep : encargDepListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Dependencia (" + dependencia + ") cannot be destroyed since the EncargDep " + encargDepListOrphanCheckEncargDep + " in its encargDepList field has a non-nullable dependencia field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(dependencia);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Dependencia> findDependenciaEntities() {
        return findDependenciaEntities(true, -1, -1);
    }

    public List<Dependencia> findDependenciaEntities(int maxResults, int firstResult) {
        return findDependenciaEntities(false, maxResults, firstResult);
    }

    private List<Dependencia> findDependenciaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Dependencia.class));
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

    public Dependencia findDependencia(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Dependencia.class, id);
        } finally {
            em.close();
        }
    }

    public int getDependenciaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Dependencia> rt = cq.from(Dependencia.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
