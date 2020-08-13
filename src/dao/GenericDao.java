//CRUD - create/ read/ update/ delete rows
package dao;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public abstract class GenericDao<T>{
    private final Class<T> modelClass;

    public GenericDao(Class<T> mClass){
        this.modelClass = mClass;
    }

    public abstract EntityManager getEntityManager();

    public void create(T model){
        EntityManager em = getEntityManager();
        try{
            em.getTransaction().begin();
            em.persist(model);
            em.getTransaction().commit();
        }catch (RuntimeException e){
            em.getTransaction().rollback();
        }finally {
            em.close();
        }
    }

    public void update(T model){
        EntityManager em = getEntityManager();
        try{
            em.getTransaction().begin();
            em.merge(model);
            em.getTransaction().commit();
        }catch (RuntimeException e){
            em.getTransaction().rollback();
        }finally {
            em.close();
        }
    }

    public void delete(int modelId){
        EntityManager em = getEntityManager();
        try{
            em.getTransaction().begin();
            em.remove(em.find(this.modelClass, modelId));
            em.getTransaction().commit();
        }catch (RuntimeException e){
            em.getTransaction().rollback();
        }finally {
            em.close();
        }
    }

    public List<T> findAll(){
        EntityManager em = getEntityManager();
        try{
            CriteriaQuery<Object> cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(modelClass));
            return (List<T>) em.createQuery(cq).getResultList();
        }catch (RuntimeException e){
            em.getTransaction().rollback();
        }finally {
            em.close();
        }
        return null;
    }
}