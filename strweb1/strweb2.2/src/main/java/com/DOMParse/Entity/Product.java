package com.DOMParse.Entity;

public class Product {
    private int id;
    private String name;
    private String brand;
    private String type;
    private double price;

    public Product(){}

    public Product(String name, String brand, String type, double price){
        this.name = name;
        this.brand = brand;
        this.type = type;
        this.price = price;
    }

    @Override
    public String toString(){
        return "Product[id = " + getId() + "]" +
                "\nname: " + getName() +
                "\nbrand: " + getBrand() +
                "\ntype: " + getType() +
                "\nprice: " + getPrice() +" byn\n\n";
    }

    public String getName(){
        return name;
    }

    public String getBrand(){
        return brand;
    }

    public String getType(){
        return type;
    }

    public double getPrice() {
        return price;
    }

    public int getId() {
        return id;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setType(String type) {
        this.type = type;
    }
}
