package org.example.domain;

import com.google.gson.annotations.Expose;
import org.example.core.ExcludeField;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


public class Staff {

    @Expose(serialize = true, deserialize = true)
    private String name;
    @Expose(serialize = false, deserialize = false)
    private int age;
    @Expose(serialize = true, deserialize = true)
    private String[] position;
    @Expose(serialize = true, deserialize = true)
    private List<String> skills;
    @ExcludeField
    private Map<String, BigDecimal> salary;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String[] getPosition() {
        return position;
    }

    public void setPosition(String[] position) {
        this.position = position;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public Map<String, BigDecimal> getSalary() {
        return salary;
    }

    @Override
    public String toString() {
        return "Staff{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", position=" + Arrays.toString(position) +
                ", skills=" + skills +
                ", salary=" + salary +
                '}';
    }

    public void setSalary(Map<String, BigDecimal> salary) {
        this.salary = salary;
    }


}
