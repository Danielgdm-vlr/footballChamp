package dao;

import model.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;

public class TeamDao extends GenericDao<Team>{
    private final EntityManagerFactory factory;

    public TeamDao(EntityManagerFactory factory){
        super(Team.class);
        this.factory = factory;
    }
    @Override
    public EntityManager getEntityManager() {
        try{
            return factory.createEntityManager();
        }catch (Exception e){
            System.out.println("The entity cannot be created!");
            return null;
        }
    }

    public Team findSpecificTeam(String teamName){
        EntityManager em = getEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Team> cq = cb.createQuery(model.Team.class);
        Root<Team> r = cq.from(model.Team.class);
        ParameterExpression<String> parameterTeamName = cb.parameter(String.class);
        cq.select(r).where(cb.equal(r.get("teamName"), parameterTeamName));
        TypedQuery<Team> query = em.createQuery(cq);
        query.setParameter(parameterTeamName, teamName);
        return query.getResultList().get(0);
    }
}
