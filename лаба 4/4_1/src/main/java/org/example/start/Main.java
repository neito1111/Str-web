package org.example.start;

import org.example.model.Author;
import org.example.model.Publisher;
import org.example.model.Title;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Main {

    private static final String MENU =  "1)\tСделайте выборку по авторам, отсортировав по их Имени и Фамилии\n" +
            "2)\tДобавьте нового Издателя\n" +
            "3)\tСделайте выборку Издателей и измените имя определенного Издателя.\n" +
            "4)\tПредоставьте отсортированный список книг определенного издателя (при этом id требуемого издателя можно менять в sql запросе)\n" +
            "5)\tВыполните добавление Нового автора в БД\n" +
            "6)\tОбновите Имя автора по определенному id\n" +
            "7)\tДобавить нового Publisher\n" +
            "Добавить новую Titles (При передачи VALUES publisherID – нужно сделать подзапросом select*from publisher where publisherName =””)\n" +
            "Добавить authorISBN (при передачи VALUES необходимо параметр autorID так же сделать подзапросом с указанием имени и фамилии)\n" +
            "8)\tВыйти\n";

    public static void main(String[] args) {
        try {
            DBWorker.connect();
            BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
            boolean stopFlag = false;

            try (Statement statement = DBWorker.getConnection().createStatement()) {
                while (!stopFlag) {
                    System.out.println(MENU);

                    int choice = Integer.parseInt(stdin.readLine());

                    switch (choice) {
                        case 1: {
                            System.out.println("Enter letter to sort authors by last name and last name " +
                                    "\n\t('ASC' - in alphabetical order )" +
                                    "\n\t('DESC' - in reverse order )"
                            );
                            String order = stdin.readLine();
                            simpleSelectAuthors(statement, order);
                            break;
                        }
                        case 2: {

                            System.out.println("Enter publisher name");
                            String name = stdin.readLine();
                            addPublisher(statement, name);
                            break;
                        }
                        case 3: {
                            selectPublishers(statement);
                            System.out.println("Enter publisher id");
                            int id = Integer.parseInt(stdin.readLine());
                            System.out.println("Enter publisher name");
                            String name = stdin.readLine();
                            updatePublisher(statement, id, name);

                            break;
                        }
                        case 4: {
                            selectPublishers(statement);
                            System.out.println("Enter publisher id");
                            int id = Integer.parseInt(stdin.readLine());
                            selectTitles(statement, id);
                            break;
                        }
                        case 5: {
                            System.out.println("Enter author first name");
                            String firstName = stdin.readLine();
                            System.out.println("Enter author second name");
                            String secondName = stdin.readLine();
                            addAuthor(statement, firstName, secondName);
                            break;
                        }
                        case 6: {
                            System.out.println("Enter author id");
                            int id = Integer.parseInt(stdin.readLine());
                            System.out.println("Enter author name");
                            String name = stdin.readLine();
                            updateAuthor(statement, id, name);
                            break;
                        }
                        case 7: {
                            addPublisher(statement, "");
                            insertIntoTitles(statement, "isbn1", "wowo", 2, "2005", 23.89);
                            insertIntoAuthorISBN(statement, "isbn1", "Leo", "Tolstoy");
                            break;
                        }
                        case 8: {
                            stopFlag = true;
                            break;
                        }
                        default: {
                            System.out.println("Такого пункта нет");
                            break;
                        }
                    }
                }
            }


            DBWorker.closeConnection();


        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public static void simpleSelectAuthors(Statement statement, String order) {
        String query = "SELECT * FROM authors ORDER BY \"secondName\", \"firstName\"";
        if (order != null) query += order;
        try {
            ResultSet resultSet = statement.executeQuery(query);
            if (!resultSet.next()) System.out.println("ResultSet in empty in Java");
            else {
                Author author = new Author();
                do {
                    author.setAuthorID(resultSet.getInt("authorID"));
                    author.setFirstName(resultSet.getString("firstName").trim());
                    author.setLastName(resultSet.getString("secondName").trim());

                    System.out.println(author);

                } while (resultSet.next());
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static void selectPublishers(Statement statement){
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * from publishers");
            if(!resultSet.next()) System.out.println("No data");
            else{
                Publisher publisher = new Publisher();
                do{
                    publisher.setPublisherID(resultSet.getInt("publisherID"));
                    publisher.setPublisherName(resultSet.getString("publisherName"));
                    System.out.println(publisher);
                }while (resultSet.next());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void addPublisher(Statement statement, String name) {
        try  {
            String insertQuery = "INSERT INTO publishers (\"publisherName\") " +
                    "VAlUES('" + name + "')";
            int res = statement.executeUpdate(insertQuery);
            System.out.println("Succesfully added" + res + " publisher");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updatePublisher(Statement statement, int id, String name){
        try  {
            String insertQuery = "UPDATE publishers set \"publisherName\" = '" +
                    name + "' where \"publisherID\" =  " + id;
            int res = statement.executeUpdate(insertQuery);
            System.out.println("Succesfully added" + res + " publisher");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addAuthor(Statement statement, String firstName, String secondName) {
        try  {
            String insertQuery = "INSERT INTO authors (\"firstName\", \"secondName\") VALUES ('" +
                    firstName + "', '" + secondName + "')";
            int res = statement.executeUpdate(insertQuery);
            System.out.println("Succesfully added " + res + "author ");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateAuthor(Statement statement, int id, String name){
        try  {
            String insertQuery = "UPDATE authors set \"firstName\" = '" +
                    name + "' where \"authorID\" =  " + id;
            int res = statement.executeUpdate(insertQuery);
            System.out.println("Succesfully updated " + res + " authors");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void selectTitles(Statement statement, int id){
        String query = "SELECT titles.\"isbn\" , titles.\"title\" , publishers.\"publisherName\"" +
                "FROM titles " +
                "INNER JOIN publishers  ON titles.\"publisherID\" = publishers.\"publisherID\"" +
                "where titles.\"publisherID\" = " + id;

        try {
            ResultSet resultSet = statement.executeQuery(query);
            if(!resultSet.next()) System.out.println("No data");
            else{
                do{
                    System.out.print(resultSet.getString("isbn").trim());
                    System.out.print(resultSet.getString("title").trim());
                    System.out.println(resultSet.getString("publisherName"));
                }while (resultSet.next());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static void insertIntoTitles (Statement statement, String isbn,String title, int editionNumber,  String year, double price){
        try  {
            String insertQuery = "INSERT INTO titles values ('" + isbn +"', '" + title + "'," +
                    editionNumber + ", '" + year + "', " + price +
                    ", (select \"publisherID\" from publishers" +
                    " where \"publisherName\" = ''" +
                    " order by \"publisherID\" limit  1))";

            int res = statement.executeUpdate(insertQuery);
            System.out.println("Successfully added" + res + " title");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static  void insertIntoAuthorISBN(Statement statement, String isbn, String firstname, String secondName){
        try  {
            String insertQuery = "INSERT INTO authorisbn VALUES (" +
                    "(select \"authorID\" from authors " +
                    "where authors.\"firstName\" = '" + firstname + "' and authors.\"secondName\"= '" + secondName + "' " +
                    "order by \"authorID\" limit  1), '" + isbn + "')";

            int res = statement.executeUpdate(insertQuery);
            System.out.println("Successfully added" + res + " title");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}







