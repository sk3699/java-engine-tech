package com.cg.Server.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import static com.cg.Server.util.EntityManagerFactorySingleton.getEntityManagerFactory;

public class WinPatternDao {

    private final transient EntityManager em = getEntityManagerFactory().createEntityManager();

    public double getValueByPattern(String pattern, int occurances) {
        Query query = em.createNamedQuery("WinPattern.getValueByPattern");
        query.setParameter("patern", pattern);
        query.setParameter("occurrences", occurances);
        return (double) query.getSingleResult();
    }
}
