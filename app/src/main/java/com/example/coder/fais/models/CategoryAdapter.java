package com.example.coder.fais.models;

import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.coder.fais.FaisTabActivity;
import com.example.coder.fais.R;
import com.example.coder.fais.SubCategoryActivity;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by jaineeta on 11/21/17.
 */

public class CategoryAdapter extends BaseExpandableListAdapter
        {

    private ArrayList<String> listCategories;
    private Map<String, ArrayList<SubCategories>> mapChild;
    private Context context;
    Integer img=R.drawable.cpr;

    public CategoryAdapter(ArrayList<String> listCategories, Map<String, ArrayList<SubCategories>> mapChild, Context context) {
        this.listCategories = listCategories;
        this.mapChild = mapChild;
        this.context = context;
        this.img=img;
    }



    @Override
    public int getGroupCount() {
        return listCategories.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mapChild.get(listCategories.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return listCategories.get(groupPosition);
    }

    @Override
    public SubCategories getChild(int groupPosition, int childPosition) {
        return mapChild.get(listCategories.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        String totalCategories=(String)getGroup(groupPosition);
        convertView = LayoutInflater.from(context).inflate(R.layout.category_group,null);
        ImageView image= (ImageView) convertView.findViewById(R.id.catimage);
        image.setImageResource(R.drawable.cpr);
        TextView tvGroup = (TextView) convertView.findViewById(R.id.catgroup);

        tvGroup.setText(totalCategories);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        String item = (String)(getChild(groupPosition,childPosition).getSubCategoryName());
        convertView = LayoutInflater.from(context).inflate(R.layout.subcategory_group,null);
        TextView tvGroup = (TextView) convertView.findViewById(R.id.subcat);
        tvGroup.setText(item);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {


        /*Intent intent = new Intent(this.context,FaisTabActivity.class);


        int i=mapChild.get(listCategories.get(groupPosition)).get(childPosition);
        intent.putExtra("SubCategoryId",i);
        startActivity(intent);*/
        return true;
    }
}
