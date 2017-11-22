package com.example.coder.fais;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.coder.fais.models.Categories;
import com.example.coder.fais.models.SubCategories;
import com.example.coder.fais.utils.Constants;
import com.example.coder.fais.utils.FireBase;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import java.util.ArrayList;

public class SubCategoryActivity extends AppCompatActivity {
    ListView catergoryListView;
    CustomizedListAdapter listAdapter;
    ArrayList<Categories> categoriesList = new ArrayList<Categories>();
    ChildEventListener mChildEventListener;
    DatabaseReference myRef;
    Query query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_category);
        int id=getIntent().getIntExtra("CategoryId",0);
        catergoryListView = (ListView) findViewById(R.id.lv_subcategory_list);
        listAdapter = new CustomizedListAdapter(this, categoriesList);
        catergoryListView.setAdapter(listAdapter);
        catergoryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent intent = new Intent(SubCategoryActivity.this, FaisTabActivity.class);
                int i=categoriesList.get(position).getCategoryid();
                intent.putExtra("SubCategoryId",i);
                startActivity(intent);
            }
        });
        myRef = FireBase.getInstance().getFireBaseReference(Constants.FIRBASE_SubCategory_DATA);
        query=myRef.orderByChild("CategoryId").equalTo(id);
        mChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Categories cat = new Categories();

                for (DataSnapshot messageSnapshot: dataSnapshot.getChildren()) {

                    if(messageSnapshot.getKey().equals("SubCategoryId"))
                        cat.setCategoryid(Integer.valueOf(messageSnapshot.getValue().toString()));
                    else if(messageSnapshot.getKey().equals("Name"))
                        cat.setCategoryName(messageSnapshot.getValue().toString());

                }
                categoriesList.add(cat);
                listAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }


        };

        query.addChildEventListener(mChildEventListener);

    }
}
