package com.m3libea.todo.data;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;
import java.util.Date;

@Table(name = "Items")

public class Item extends Model {

    @Column(name = "Item")
    public String text;

    @Column(name = "Priority")
    public String priority;

    @Column(name = "DueDate")
    public Date dueDate;

    public Item(){
        super();
    }

    public Item(String itemText) {
        super();
        this.text = itemText;
        this.dueDate = null;
        this.priority = null;
    }

    public static List<Item> getAll(){
        return new Select().from(Item.class).orderBy("id ASC").execute();
    }

    @Override
    public String toString() {
        return text;
    }
}
