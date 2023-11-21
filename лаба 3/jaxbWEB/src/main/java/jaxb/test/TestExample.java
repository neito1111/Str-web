package jaxb.test;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import jaxb.model.Department;
import jaxb.model.Employee;
import jaxb.model.Organisation;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestExample {

    private static final String XML_FILE = "dept-info.xml";

    public static void main(String[] args) {

        Employee emp1 = new Employee("E01", "Tom", null);
        Employee emp2 = new Employee("E02", "Mary", "E01");
        Employee emp3 = new Employee("E03", "John", null);
        Employee emp4 = new Employee("E04", "Viktor", null);
        Employee emp5 = new Employee("E05", "Nikita", "E04");
        Employee emp6 = new Employee("E06", "Evgeniy", null);

        List<Employee> listEmp1 = new ArrayList<>(Arrays.asList(emp1, emp2, emp3));
        List<Employee> listEmp2 = new ArrayList<>(Arrays.asList(emp4, emp5, emp6));

        Department dept1 = new Department("D01", "ACCOUNTING", "NEW YORK");
        Department dept2 = new Department("D02", "MANAGEMENT", "LOS ANGELES");
        List<Department> listDeps = new ArrayList<>(Arrays.asList(dept1, dept2));

        dept1.setEmployees(listEmp1);
        dept2.setEmployees(listEmp2);

        Organisation organisation = new Organisation("OOO MUSOR");
        organisation.setDepartments(listDeps);
        

        try {
            // create JAXB context and instantiate marshaller
            JAXBContext context = JAXBContext.newInstance(Organisation.class);

            // (1) Marshaller : Java Object to XML content.
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            m.marshal(organisation, System.out);

            // Write to File
            File outFile = new File(XML_FILE);
            m.marshal(organisation, outFile);

            System.err.println("Write to file: " + outFile.getAbsolutePath());


            // (2) Unmarshaller : Read XML content to Java Object.
            Unmarshaller um = context.createUnmarshaller();

            // XML file create before.

            Organisation organisationFromFile = (Organisation) um.unmarshal(new FileReader(XML_FILE));
            List<Department> deps = organisationFromFile.getDepartments();
            System.out.printf("Organisation name: %s\n\n", organisationFromFile.getOrgName());
            for(Department department : deps){
                System.out.printf("""
                        Department : %s
                        Number: %s
                        Location: %s
                        """, department.getDeptName(), department.getDeptNo(), department.getLocation());
                for(Employee employee : department.getEmployees()){
                    System.out.printf("""
                            Employee: %s
                            Number: %s
                            """, employee.getEmpName(), employee.getEmpNo());
                    System.out.println("Manager number: " + (employee.getManagerNo() == null
                            ? "None"
                            : employee.getManagerNo()));
                }
                System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

