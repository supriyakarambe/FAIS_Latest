package com.example.coder.fais;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.coder.fais.models.Categories;

import java.util.ArrayList;

/**
 * Created by Agni on 4/16/2017.
 */

public class CustomizedListAdapter extends BaseAdapter {
    private ArrayList<Categories> data;
    private static LayoutInflater inflater = null;
    //public ImageLoader imageLoader;

    public CustomizedListAdapter(Activity a, ArrayList<Categories> d) {
        data = d;
        inflater = (LayoutInflater) a.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //imageLoader=new ImageLoader(activity.getApplicationContext());
    }

    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (convertView == null)
            view = inflater.inflate(R.layout.list_row, null);

        TextView categoryName = (TextView) view.findViewById(R.id.txt); // title
        Categories category = data.get(position);
        categoryName.setText(category.getCategoryName());
        return view;
    }
}
