package com.example.fetchtask;

/**
 * Represents an item entity in the application.
 * This class is used to model the data structure for items fetched.
 */
public class Item {
    private int id;
    private int listId;
    private String name;

    /**
     * Constructor for Item.
     *
     * @param id     The unique identifier for the item.
     * @param listId The identifier for the list to which this item belongs.
     * @param name   The name of the item.
     */
    public Item(int id, int listId, String name) {
        this.id = id;
        this.listId = listId;
        this.name = name;
    }

    //Getter methods

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
