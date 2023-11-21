import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entities.PersonEntity;
import org.hibernate.Session;
import xmlmarshalling.PersonXML;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class JAXBAPI {
    static PersonEntity executeXML(String filename, Session session) throws IOException, JAXBException {
        PersonXML personXML = getQuery(filename);

        switch (personXML.getQtype()) {
            case CREATE:
                create(personXML.getEntity(), session);
                break;
            case UPDATE:
                update(personXML.getEntity(), session);
                break;
            case READ:
                return read(personXML.getEntity(), session);
            case DELETE:
                delete(personXML.getEntity(), session);
                break;
        }
        return null;
    }

    static PersonXML getQuery(String filename) throws IOException, JAXBException {
        StringBuilder strbldr = new StringBuilder();
        ArrayList<String> lines = (ArrayList<String>) Files.readAllLines(Paths.get(filename), StandardCharsets.UTF_8);
        for (String line : lines) {
            strbldr.append(line);
        }
        JAXBContext jaxbContext = JAXBContext.newInstance(PersonXML.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        return (PersonXML) jaxbUnmarshaller.unmarshal(new StringReader(strbldr.toString()));
    }

    private static void create(PersonEntity entity, Session session) {
        session.beginTransaction();
        session.save(entity);
        session.getTransaction().commit();
    }

    private static void update(PersonEntity entity, Session session) {
        session.beginTransaction();
        PersonEntity mod = session.get(PersonEntity.class, entity.getId());
        if (entity.getSurname() != null) {
            mod.setSurname(entity.getSurname());
        }
        if (entity.getAddress() != null) {
            mod.setAddress(entity.getAddress());
        }
        mod.setDutyBound(entity.getDutyBound());
        session.update(mod);
        session.getTransaction().commit();
    }

    private static PersonEntity read(PersonEntity entity, Session session) {
        return session.get(PersonEntity.class, entity.getId());
    }

    private static void delete(PersonEntity entity, Session session) {
        session.beginTransaction();
        PersonEntity myObject = session.load(PersonEntity.class, entity.getId());
        session.delete(myObject);
        session.getTransaction().commit();
    }

    static void addFromJson(@SuppressWarnings("SameParameterValue") String filename, Session session) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (Reader reader = new FileReader(filename)) {
            PersonEntity personEntity = gson.fromJson(reader, PersonEntity.class);
            create(personEntity, session);
        }
    }

    static void dumpToJson(@SuppressWarnings("SameParameterValue") String filename, Session session, @SuppressWarnings("SameParameterValue") String ID) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        PersonEntity personEntity = session.find(PersonEntity.class, ID);
        try (FileWriter writer = new FileWriter(filename)) {
            gson.toJson(personEntity, writer);
        }
    }


}
