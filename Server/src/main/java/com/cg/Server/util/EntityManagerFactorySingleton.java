package com.cg.Server.util;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class EntityManagerFactorySingleton {

    private static EntityManagerFactory entityManagerFactory;

    private EntityManagerFactorySingleton(){};

    public static EntityManagerFactory getEntityManagerFactory() {
        if(entityManagerFactory == null) {
            entityManagerFactory = Persistence.createEntityManagerFactory("valueChances");
        }
        return entityManagerFactory;
    }

}
