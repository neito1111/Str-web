package org.example.core;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.example.domain.Staff;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.List;

public class GsonExample2 {

    public static void main(String[] args) {

        /*Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                //.excludeFieldsWithModifiers(Modifier.TRANSIENT, Modifier.TRANSIENT)
                .excludeFieldsWithoutExposeAnnotation() //for Annotations
                .create();*/
        Gson gson = new GsonBuilder()
                .setExclusionStrategies(new CustomExclusionStrategy(List.class))
                .create();

        try (Reader reader = new FileReader("src/main/resources/student.json")) {

            // To Java Object
//            Staff[] staff = gson.fromJson(reader, Staff[].class);
//            for(Staff staffOne : staff){
//                System.out.println(staffOne.toString());
//            }


            //For all
            Type collectionType = new TypeToken<List<Staff>>() {
            }.getType();
            List<Staff> lcs = new Gson()
                    .fromJson(reader, collectionType);

            // print staff
            System.out.println(lcs);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

