package com.example.casestudy.model;

public class Directory {
    private int id;
    private String name;
    public Directory(int id, String name) {
        this.id = id;
        this.name = name;
    }
    public Directory(int id) {
        this.id = id;
    }

    public Directory(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
