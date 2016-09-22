package com.m3libea.todo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.m3libea.todo.model.Item;

import java.util.Calendar;
import java.util.Date;

public class EditItemActivity extends AppCompatActivity {

    EditText etEditItem;
    DatePicker dpDueDate;
    Spinner spPriority;

    Long itemId;
    Item editItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        itemId = getIntent().getExtras().getLong("itemID");
        editItem = Item.load(Item.class, itemId );

        etEditItem = (EditText) findViewById(R.id.etEditItem);
        etEditItem.setText(editItem.text);
        setSpinner();
        setDatePicker();

    }

    public void onSaveItem(View view) {
        Intent i = new Intent();
        i.putExtra("itemID", itemId);
        i.putExtra("pos", getIntent().getExtras().getInt("pos"));

        editItem.text = etEditItem.getText().toString();
        editItem.save();

        setResult(RESULT_OK, i);
        this.finish();
    }

    public void setSpinner(){
        spPriority = (Spinner) findViewById(R.id.spPriority);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.priority_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPriority.setAdapter(adapter);
        spPriority.setSelection(adapter.getPosition(editItem.priority));
        spPriority.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                editItem.priority = spPriority.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void setDatePicker(){
        dpDueDate = (DatePicker) findViewById(R.id.dpDueDate);
        Calendar c = Calendar.getInstance();
        dpDueDate.init(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                editItem.dueDate = new Date(getMilliseconds());
            }
        });
    }

    public long getMilliseconds(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(dpDueDate.getYear(), dpDueDate.getMonth(), dpDueDate.getDayOfMonth());
        return calendar.getTimeInMillis();
    }
}
