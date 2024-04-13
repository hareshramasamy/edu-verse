package com.csye6220.eduverse.dao;


import java.util.logging.Level;
import java.util.logging.Logger;

import com.csye6220.eduverse.entity.*;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public abstract class DAO {

    private static SessionFactory sessionFactory;

    static {
        StandardServiceRegistryBuilder registryBuilder = new StandardServiceRegistryBuilder();

        registryBuilder.applySetting("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver")
                .applySetting("hibernate.connection.url", "jdbc:mysql://localhost:3306/eduversedb")
                .applySetting("hibernate.connection.username", "root")
                .applySetting("hibernate.connection.password", "password")
                .applySetting("hibernate.hbm2ddl.auto", "update");

        StandardServiceRegistry standardRegistry = registryBuilder.build();

        MetadataSources sources = new MetadataSources(standardRegistry);

        sources.addAnnotatedClasses(Announcement.class, Assignment.class, Course.class, CourseOffering.class, Department.class, Enrollment.class, Grade.class, Instructor.class, Student.class, StudentAssignment.class, TAAssignment.class, User.class, FileData.class);

        sessionFactory = sources.buildMetadata().buildSessionFactory();
    }
    private static final ThreadLocal sessionThread = new ThreadLocal();

    private static final Logger log = Logger.getAnonymousLogger();

    protected DAO() {
    }

    public static Session getSession() {
        Session session = (Session) DAO.sessionThread.get();

        if (session == null) {
            session = sessionFactory.openSession();
            DAO.sessionThread.set(session);
        }
        return session;
    }

    protected void begin() {
        getSession().beginTransaction();
    }

    protected void commit() {
        getSession().getTransaction().commit();
    }

    protected void rollback() {
        try {
            getSession().getTransaction().rollback();
        } catch (HibernateException e) {
            log.log(Level.WARNING, "Cannot rollback", e);
        }
        try {
            getSession().close();
        } catch (HibernateException e) {
            log.log(Level.WARNING, "Cannot close", e);
        }
        DAO.sessionThread.set(null);
    }

    public static void close() {
        getSession().close();
        DAO.sessionThread.set(null);
    }
}