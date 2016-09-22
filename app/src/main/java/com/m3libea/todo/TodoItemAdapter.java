package com.m3libea.todo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.m3libea.todo.model.Item;

import java.text.Format;
import java.util.List;
import java.text.SimpleDateFormat;

/**
 * Created by m3libea on 9/21/16.
 */

public class TodoItemAdapter extends ArrayAdapter<Item> {

    public TodoItemAdapter(Context context, List<Item> items){
        super(context, R.layout.item_list, items);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView tvItem;
        TextView tvDueDate;
        TextView tvPriority;

        Item item = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_list, parent, false);
        }

        tvItem = (TextView) convertView.findViewById(R.id.tvItem);
        tvDueDate = (TextView) convertView.findViewById(R.id.tvDueDate);
        tvPriority = (TextView) convertView.findViewById(R.id.tvPriority);

        tvItem.setText(item.text);


        if (item.dueDate != null) {
            Format formatter = new SimpleDateFormat("MM/dd/yyyy");
            String dateFormatted = formatter.format(item.dueDate);
            tvDueDate.setText("Due to " + dateFormatted);
            tvDueDate.setVisibility(View.VISIBLE);
        } else {
            tvDueDate.setVisibility(View.INVISIBLE);
        }

        if (item.priority != null) {
            tvPriority.setText(item.priority);
            tvPriority.setVisibility(View.VISIBLE);
        } else {
            tvPriority.setVisibility(View.INVISIBLE);
        }

        return convertView;
    }
}
