import entities.PersonEntity;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.*;
import xmlmarshalling.PersonXML;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestingClasses {

    private static SessionFactory sessionFactory = null;

    @BeforeAll
    static void configure() {
        try {
            Configuration configuration = new Configuration();
            configuration.configure();
            sessionFactory = configuration.buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    private static Session getSession() throws HibernateException {
        return sessionFactory.openSession();
    }

    @Test
    @Order(1)
    void creation() {
        try {
            Session session = getSession();
            JAXBAPI.executeXML("files/1.xml", session);
            PersonEntity personEntity = JAXBAPI.executeXML("files/2.xml", session);
            PersonXML personXML = JAXBAPI.getQuery("files/1.xml");
            assertEquals(personEntity, personXML.getEntity());
        } catch (IOException | JAXBException e) {
            fail();
        }
    }

    @Test
    @Order(2)
    void update() {
        try {
            Session session = getSession();
            int prev = Objects.requireNonNull(JAXBAPI.executeXML("files/2.xml", session)).getDutyBound();
            JAXBAPI.executeXML("files/3.xml", session);
            int post = Objects.requireNonNull(JAXBAPI.executeXML("files/2.xml", session)).getDutyBound();
            assertNotEquals(prev, post);
        } catch (IOException | JAXBException | NullPointerException e) {
            fail();
        }
    }

    @Test
    @Order(3)
    void toxml() {
        PersonEntity personEntity = getSession().find(PersonEntity.class, "1");
        PersonXML personXML = new PersonXML(personEntity);
        try {
            JAXBContext context = JAXBContext.newInstance(PersonXML.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            BufferedWriter writer = new BufferedWriter(new FileWriter("files/out.xml"));
            marshaller.marshal(personXML, writer);
            writer.close();
        } catch (JAXBException | IOException e) {
            fail();
        }
    }

    @Test
    @Order(4)
    void delete() {
        try {
            Session session = getSession();
            JAXBAPI.executeXML("files/4.xml", session);
            PersonEntity p = JAXBAPI.executeXML("files/2.xml", session);
            assertNull(p);
        } catch (IOException | JAXBException e) {
            fail();
        }
    }




    @Test
    @Order(5)
    void jsontest(){
        try {
            JAXBAPI.addFromJson("files/in.json", getSession());
            JAXBAPI.dumpToJson("files/out.json", getSession(), "2");
        } catch (IOException e) {
            fail();
        }
    }

}
