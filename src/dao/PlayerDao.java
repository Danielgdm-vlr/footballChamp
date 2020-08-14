package dao;

import model.Player;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import java.util.List;

public class PlayerDao extends GenericDao<Player> {
    private final EntityManagerFactory factory;

    public PlayerDao(EntityManagerFactory factory){
        super(Player.class);
        this.factory = factory;
    }

    @Override
    public EntityManager getEntityManager() {
        try{
            return factory.createEntityManager();
        }catch (Exception e){
            System.out.println("The entity could not be created!");
            return null;
        }
    }

    public Player findAfterName(String playerName){
        EntityManager em = getEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Player> cq = cb.createQuery(model.Player.class);
        Root<Player> r = cq.from(model.Player.class);
        ParameterExpression<String> pName = cb.parameter(String.class);
        cq.select(r).where(cb.equal(r.get("playerName"), pName));
        TypedQuery<Player> query = em.createQuery(cq);
        query.setParameter(pName, playerName);
        return query.getResultList().get(0);
    }

    public List<Player> findAllSpecific(int idTeam){
            EntityManager em = getEntityManager();
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Player> cq = cb.createQuery(model.Player.class);
            Root<Player> r = cq.from(model.Player.class);
            ParameterExpression<Integer> id = cb.parameter(int.class);
            cq.select(r).where(cb.equal(r.get("idTeam"), id));
            TypedQuery<Player> query = em.createQuery(cq);
            query.setParameter(id, idTeam);
            return query.getResultList();
        }
    }
