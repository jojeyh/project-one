package com.revature.dao;

public abstract class DaoFactory {

    public enum FactoryType {
        LOCALHOST,
        GCP
    }

    public abstract UserDAO getUserDAO();
    public abstract ReimbDAO getReimbDAO();

    public static DAOFactory getDAOFactory(FactoryType fType) {
        switch (fType) {
            case LOCALHOST:
                return new LocalhostDAOFactory();
            case GCP:
                return new GCPDAOFactory();
            default:
                return null;
        }
    }
}