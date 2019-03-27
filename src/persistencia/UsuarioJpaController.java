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
import modelo.Usuario;
import persistencia.exceptions.IllegalOrphanException;
import persistencia.exceptions.NonexistentEntityException;
import persistencia.exceptions.PreexistingEntityException;

/**
 *
 * @author Victor
 */
public class UsuarioJpaController implements Serializable {

    public UsuarioJpaController() {
        this.emf = Persistence.createEntityManagerFactory("PQRSPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Usuario usuario) throws PreexistingEntityException, Exception {
        if (usuario.getPqrsList() == null) {
            usuario.setPqrsList(new ArrayList<Pqrs>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Pqrs> attachedPqrsList = new ArrayList<Pqrs>();
            for (Pqrs pqrsListPqrsToAttach : usuario.getPqrsList()) {
                pqrsListPqrsToAttach = em.getReference(pqrsListPqrsToAttach.getClass(), pqrsListPqrsToAttach.getCodigo());
                attachedPqrsList.add(pqrsListPqrsToAttach);
            }
            usuario.setPqrsList(attachedPqrsList);
            em.persist(usuario);
            for (Pqrs pqrsListPqrs : usuario.getPqrsList()) {
                Usuario oldDniUsuarioOfPqrsListPqrs = pqrsListPqrs.getDniUsuario();
                pqrsListPqrs.setDniUsuario(usuario);
                pqrsListPqrs = em.merge(pqrsListPqrs);
                if (oldDniUsuarioOfPqrsListPqrs != null) {
                    oldDniUsuarioOfPqrsListPqrs.getPqrsList().remove(pqrsListPqrs);
                    oldDniUsuarioOfPqrsListPqrs = em.merge(oldDniUsuarioOfPqrsListPqrs);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findUsuario(usuario.getDni()) != null) {
                throw new PreexistingEntityException("Usuario " + usuario + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usuario usuario) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario persistentUsuario = em.find(Usuario.class, usuario.getDni());
            List<Pqrs> pqrsListOld = persistentUsuario.getPqrsList();
            List<Pqrs> pqrsListNew = usuario.getPqrsList();
            List<String> illegalOrphanMessages = null;
            for (Pqrs pqrsListOldPqrs : pqrsListOld) {
                if (!pqrsListNew.contains(pqrsListOldPqrs)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Pqrs " + pqrsListOldPqrs + " since its dniUsuario field is not nullable.");
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
            usuario.setPqrsList(pqrsListNew);
            usuario = em.merge(usuario);
            for (Pqrs pqrsListNewPqrs : pqrsListNew) {
                if (!pqrsListOld.contains(pqrsListNewPqrs)) {
                    Usuario oldDniUsuarioOfPqrsListNewPqrs = pqrsListNewPqrs.getDniUsuario();
                    pqrsListNewPqrs.setDniUsuario(usuario);
                    pqrsListNewPqrs = em.merge(pqrsListNewPqrs);
                    if (oldDniUsuarioOfPqrsListNewPqrs != null && !oldDniUsuarioOfPqrsListNewPqrs.equals(usuario)) {
                        oldDniUsuarioOfPqrsListNewPqrs.getPqrsList().remove(pqrsListNewPqrs);
                        oldDniUsuarioOfPqrsListNewPqrs = em.merge(oldDniUsuarioOfPqrsListNewPqrs);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = usuario.getDni();
                if (findUsuario(id) == null) {
                    throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.");
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
            Usuario usuario;
            try {
                usuario = em.getReference(Usuario.class, id);
                usuario.getDni();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Pqrs> pqrsListOrphanCheck = usuario.getPqrsList();
            for (Pqrs pqrsListOrphanCheckPqrs : pqrsListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Pqrs " + pqrsListOrphanCheckPqrs + " in its pqrsList field has a non-nullable dniUsuario field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(usuario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Usuario> findUsuarioEntities() {
        return findUsuarioEntities(true, -1, -1);
    }

    public List<Usuario> findUsuarioEntities(int maxResults, int firstResult) {
        return findUsuarioEntities(false, maxResults, firstResult);
    }

    private List<Usuario> findUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuario.class));
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

    public Usuario findUsuario(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuario> rt = cq.from(Usuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
