package com.m3libea.todo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {

    EditText etEditItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        etEditItem = (EditText) findViewById(R.id.etEditItem);
        etEditItem.setText(getIntent().getStringExtra("item"));
    }

    public void onSaveItem(View view) {
        Intent i = new Intent();
        i.putExtra("item", etEditItem.getText().toString());
        i.putExtra("pos", getIntent().getExtras().getInt("pos"));
        setResult(RESULT_OK, i);
        this.finish();
    }
}
