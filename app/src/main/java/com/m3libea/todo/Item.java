package com.m3libea.todo;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

@Table(name = "Items")

public class Item extends Model {

    @Column(name = "Item")
    public String text;

    public Item(){
        super();
    }

    public Item(String itemText) {
        super();
        this.text = itemText;
    }

    public static List<Item> getAll(){
        return new Select().from(Item.class).orderBy("id ASC").execute();
    }

    @Override
    public String toString() {
        return text;
    }
}
