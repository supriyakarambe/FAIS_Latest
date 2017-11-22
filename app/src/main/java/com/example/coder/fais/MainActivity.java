package com.example.coder.fais;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.coder.fais.models.Categories;
import com.example.coder.fais.utils.Constants;
import com.example.coder.fais.utils.FireBase;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    ListView catergoryListView;
    CustomizedListAdapter listAdapter;
    ArrayList<Categories> categoriesList = new ArrayList<Categories>();
    ChildEventListener mChildEventListener;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        catergoryListView = (ListView) findViewById(R.id.lv_category_list);
        listAdapter = new CustomizedListAdapter(this, categoriesList);
        catergoryListView.setAdapter(listAdapter);
        catergoryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent intent = new Intent(MainActivity.this, SubCategoryActivity.class);
                int i=categoriesList.get(position).getCategoryid();
                intent.putExtra("CategoryId",i);
                startActivity(intent);
            }
        });
        myRef = FireBase.getInstance().getFireBaseReference(Constants.FIRBASE_Category_DATA);
        mChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Categories cat = new Categories();
                for (DataSnapshot messageSnapshot: dataSnapshot.getChildren()) {

                    if(messageSnapshot.getKey().equals("CategoryId"))
                        cat.setCategoryid(Integer.valueOf(messageSnapshot.getValue().toString()));
                    else
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

        myRef.addChildEventListener(mChildEventListener);

    }

    }



