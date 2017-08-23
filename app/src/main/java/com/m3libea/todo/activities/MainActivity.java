package com.m3libea.todo.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.m3libea.todo.R;
import com.m3libea.todo.adapters.TodoItemAdapter;
import com.m3libea.todo.data.Item;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    List<Item> todoItems;
    TodoItemAdapter aToDoAdapter;
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

        setListeners();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK && requestCode == REQUEST_CODE){
            Long itemID = data.getExtras().getLong("itemID");
            int pos = data.getExtras().getInt("pos");

            todoItems.set(pos,Item.load(Item.class, itemID));
            aToDoAdapter.notifyDataSetChanged();
        }
    }

    public void setListeners(){
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Item item = todoItems.remove(position);
                aToDoAdapter.notifyDataSetChanged();
                item.delete();
                makeToast("Item deleted");
                return true;
            }
        });

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(MainActivity.this, EditItemActivity.class);

                i.putExtra("itemID", todoItems.get(position).getId());
                i.putExtra("pos", position);
                startActivityForResult(i, REQUEST_CODE);
            }
        });
    }

    public void populateArrayItems(){
        todoItems = new ArrayList<Item>();
        readItems();
        aToDoAdapter = new TodoItemAdapter(this, todoItems);
    }

    private void readItems(){
        todoItems = Item.getAll();
    }

    public void onAddItem(View view) {

        String task = etEditText.getText().toString();
        if (task.isEmpty()) {
            makeToast("Item is empty");
        }else{
            Item item = new Item(task);
            aToDoAdapter.add(item);
            etEditText.setText("");
            item.save();
        }

    }

    public void makeToast(String s) {
        Context context = getApplicationContext();;
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, s, duration);
        toast.show();
    }
}
