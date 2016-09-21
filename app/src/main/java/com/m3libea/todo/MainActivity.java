package com.m3libea.todo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<Item> todoItems;
    ArrayAdapter<Item> aToDoAdapter;
    ListView lvItems;

    EditText etEditText;

    private final int REQUEST_CODE = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        populateArrayItems();
        lvItems = (ListView) findViewById(R.id.lvItems);
        lvItems.setAdapter(aToDoAdapter);
        etEditText = (EditText) findViewById(R.id.etEditText);
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Item item = todoItems.remove(position);
                aToDoAdapter.notifyDataSetChanged();
                item.delete();
                return true;
            }
        });
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(MainActivity.this, EditItemActivity.class);

                i.putExtra("item", todoItems.get(position).text);
                i.putExtra("pos", position);
                startActivityForResult(i, REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK && requestCode == REQUEST_CODE){
            String itemText = data.getExtras().getString("item");
            int pos = data.getExtras().getInt("pos");

            if(itemText.isEmpty()){
                Item item = todoItems.remove(pos);
                item.delete();
            }else {

                Item item = todoItems.get(pos);
                item.text = itemText;
                todoItems.set(pos, item);
                item.save();
            }
            aToDoAdapter.notifyDataSetChanged();
        }
    }

    public void populateArrayItems(){
        todoItems = new ArrayList<Item>();
        readItems();
        aToDoAdapter = new ArrayAdapter<Item>(this, android.R.layout.simple_list_item_1, todoItems);
    }

    private void readItems(){
        todoItems = Item.getAll();
    }

    public void onAddItem(View view) {
        Item item = new Item(etEditText.getText().toString());
        aToDoAdapter.add(item);
        etEditText.setText("");
        item.save();

    }
}
