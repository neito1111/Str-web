package org.example.domain;

import com.google.gson.annotations.Expose;

import java.util.List;


public class Software {

    List<Staff> staff;
    @Override
    public String toString() {
        return "Software [employees=" + staff + "]";
    }
}

