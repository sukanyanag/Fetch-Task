package com.example.fetchtask;

public class Item {
    private int id;
    private int listId;
    private String name;

    // Constructor, getters, and setters
    public Item(int id, int listId, String name) {
        this.id = id;
        this.listId = listId;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public int getListId() {
        return listId;
    }

    public String getName() {
        return name;
    }
}
