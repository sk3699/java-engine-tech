package com.cg.Server.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import java.util.ArrayList;
import java.util.List;

import static com.cg.Server.util.EntityManagerFactorySingleton.getEntityManagerFactory;

public class ReelsDao {

    private final transient EntityManager em = getEntityManagerFactory().createEntityManager();

    public List<List<String>> getAllRecords() {
        Query query = em.createNamedQuery("Reels.getAllRecords");
        List<Object[]> resultList = query.getResultList();

        List<List<String>> reelsData = new ArrayList<>();
        for (Object[] row : resultList) {
            List<String> reel = new ArrayList<>();
            for (Object value : row) {
                reel.add(value.toString());
            }
            reelsData.add(reel);
        }
        return reelsData;
    }
}
