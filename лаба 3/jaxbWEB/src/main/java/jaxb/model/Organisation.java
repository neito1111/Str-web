package jaxb.model;

import jakarta.xml.bind.annotation.*;

import java.util.List;

@XmlRootElement(name = "organisation")
@XmlAccessorType(XmlAccessType.FIELD)
public class Organisation {
    private String orgName;

    @XmlElementWrapper(name = "department")
    @XmlElement(name = "department")
    private List<Department> departments;

    public Organisation(){}

    public Organisation(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public List<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }
}
