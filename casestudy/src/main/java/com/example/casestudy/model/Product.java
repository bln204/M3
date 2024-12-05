package com.example.casestudy.model;

public class Product {
    private int id;
    private String name;
    private int inventory;
    private double price;
    private Directory directory;
    private String description;

    public Product(int id, String name, int inventory, Directory directory, double price, String description) {
        this.id = id;
        this.name = name;
        this.inventory = inventory;
        this.directory = directory;
        this.price = price;
        this.description = description;
    }

    public Product(String name, int inventory, Directory directory, double price, String description) {
        this.name = name;
        this.inventory = inventory;
        this.directory = directory;
        this.price = price;
        this.description = description;
    }


    public Directory getDirectory() {
        return directory;
    }

    public void setDirectory(Directory directory) {
        this.directory = directory;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getInventory() {
        return inventory;
    }

    public void setInventory(int inventory) {
        this.inventory = inventory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
