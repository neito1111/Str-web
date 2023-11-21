package net.iba.hibernate.example;

import net.iba.hibernate.example.model.Developer;
//import org.hibernate.Criteria;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import java.util.List;
import java.util.Map;

public class DeveloperRunner {
    private static SessionFactory sessionFactory;


    public static void main(String[] args) {
        sessionFactory = new Configuration().configure().buildSessionFactory();
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(net.iba.hibernate.example.model.Developer.class);

        DeveloperRunner developerRunner = new DeveloperRunner();

        System.out.println("Adding developer's records to the DB");
        developerRunner.addDeveloper("Ihor", "Developer", "Java Developer", 2, 1818);
        developerRunner.addDeveloper("Some", "Developer", "C++ Developer", 2, 2929);
        developerRunner.addDeveloper("Peter", "UI", "UI Developer", 4, 0);

        System.out.println("List of developers");
        List<Developer> developers = developerRunner.listDevelopers();
        for (Developer developer : developers) {
            System.out.println(developer);
        }
        System.out.println("===================================");
        System.out.println("Removing Some Developer and updating Ihor");
        developerRunner.updateDeveloper(1, 11000);
        developerRunner.removeDeveloper(8);

        System.out.println("Final list of developers");
        developers = developerRunner.listDevelopers();
        for (Developer developer : developers) {
            System.out.println(developer);
        }
        System.out.println("===================================");

        System.out.println("List of Developers with experience more than 3 years:");
        developerRunner.listDevelopersOverThreeYears();

        System.out.println("Total Salary of all Developers:");
        developerRunner.totalSalary();

        System.out.println("List of Developers using Scalar Query:");
        developerRunner.listDevelopersScalar();


        sessionFactory.close();


    }


    public void addDeveloper(String firstName, String lastName, String specialty, int experience, int salary) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;

        transaction = session.beginTransaction();
        Developer developer = new Developer(firstName, lastName, specialty, experience, salary);
        session.save(developer);
        transaction.commit();
        session.close();
    }


    public List<Developer> listDevelopers() {
        Session session = this.sessionFactory.openSession();
        Transaction transaction = null;

        transaction = session.beginTransaction();
        List<Developer> developers = session.createQuery("FROM Developer").list();

        transaction.commit();
        session.close();
        return developers;
    }

    public void updateDeveloper(int developerId, int experience) {
        Session session = this.sessionFactory.openSession();
        Transaction transaction = null;

        transaction = session.beginTransaction();
        Developer developer = (Developer) session.get(Developer.class, developerId);
        developer.setExperience(experience);
        session.update(developer);
        transaction.commit();
        session.close();
    }


    public void removeDeveloper(int developerId) {
        Session session = this.sessionFactory.openSession();
        Transaction transaction = null;

        transaction = session.beginTransaction();
        Developer developer = (Developer) session.get(Developer.class, developerId);
        session.delete(developer);
        transaction.commit();
        session.close();
    }


    public void listDevelopersOverThreeYears() {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;

        transaction = session.beginTransaction();
        Criteria criteria = session.createCriteria(Developer.class); //выборка с условиями
        criteria.add(Restrictions.gt("experience", 3));
        List<Developer> developers = criteria.list();

        for (Developer developer : developers) {
            System.out.println("=======================");
            System.out.println(developer);
            System.out.println("=======================");
        }
        transaction.commit();
        session.close();
    }

    public void totalSalary() {
        Session session  = sessionFactory.openSession();
        Transaction transaction = null;

        transaction = session.beginTransaction();
       // CriteriaBuilder builder = session.getCriteriaBuilder();
       // CriteriaQuery<Developer> critQuery = builder.createQuery(Developer.class);

        Criteria criteria = session.createCriteria(Developer.class);
        criteria.setProjection(Projections.sum("salary"));

        List totalSalary = criteria.list();
        System.out.println("Total salary of all developers: " + totalSalary.get(0));
        transaction.commit();
        session.close();
    }

    public void listDevelopersScalar() {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;

        transaction = session.beginTransaction();
        SQLQuery sqlQuery = session.createSQLQuery("SELECT * FROM HIBERNATE_DEVELOPERS");
        sqlQuery.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        List<Developer> developers = sqlQuery.list();
        for (Object developer : developers) {
            Map row = (Map) developer;
            System.out.println("=======================");
            System.out.println("id: " + row.get("id"));
            System.out.println("First Name: " + row.get("first_name"));
            System.out.println("Last Name: " + row.get("last_name"));
            System.out.println("Specialty: " + row.get("speciality"));
            System.out.println("Experience: " + row.get("experience"));
            System.out.println("Salary: " + row.get("salary"));
            System.out.println("=======================");
        }
        transaction.commit();
        session.close();
    }


}
