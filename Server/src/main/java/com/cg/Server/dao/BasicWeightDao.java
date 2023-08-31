package com.cg.Server.dao;

import com.cg.Server.wrapper.BasicWeightWrapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import java.util.List;

import static com.cg.Server.util.EntityManagerFactorySingleton.getEntityManagerFactory;

public class BasicWeightDao {

    private final transient EntityManager em = getEntityManagerFactory().createEntityManager();

    public List<BasicWeightWrapper> getAllRecords() {
        Query query = em.createNamedQuery("BasicWeight.getAllRecords");
        return query.getResultList();
    }
}
