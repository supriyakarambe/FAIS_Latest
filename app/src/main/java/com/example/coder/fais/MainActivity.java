package com.example.coder.fais;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;

import com.example.coder.fais.models.CategoryAdapter;
import com.example.coder.fais.utils.Constants;
import com.example.coder.fais.utils.FireBase;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

   private CategoryAdapter categoryAdapter;
    private ExpandableListView expLV;
    private ChildEventListener mChildEventListener;
    private Map<String, ArrayList<String>> mapChild;
    private DatabaseReference myRef;
    private DatabaseReference subRef;
    private ArrayList<String> listCategories = new ArrayList<String>();
    private ArrayList<Integer> subCategory = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getList();

    }


    private void getList(){

        expLV= (ExpandableListView)findViewById(R.id.expLV);
        listCategories = new ArrayList<>();
        mapChild = new HashMap<>();


        categoryAdapter = new CategoryAdapter(listCategories,mapChild,MainActivity.this);
        expLV.setAdapter(categoryAdapter);


        //Firebase database reference
        myRef = FireBase.getInstance().getFireBaseReference(Constants.FIRBASE_Category_DATA);
        mChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String key = dataSnapshot.getKey();

                //if(messageSnapshot.getKey().equals("CategoryId")) {
                subRef = FireBase.getInstance().getFireBaseReference(Constants.FIRBASE_SubCategory_DATA);
                final String categoryName = dataSnapshot.child("Name").getValue().toString();
                listCategories.add(categoryName);
                Query query=subRef.orderByChild("CategoryId").equalTo(Integer.valueOf(dataSnapshot.child("CategoryId").getValue().toString()));
                query.addChildEventListener(new ChildEventListener(){

                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        if(mapChild.containsKey(categoryName)){
                            mapChild.get(categoryName).add(dataSnapshot.child("Name").getValue().toString());
                        }else{
                            ArrayList<String> subcategoryList = new ArrayList<>();
                            subcategoryList.add(dataSnapshot.child("Name").getValue().toString());
                            subCategory.add(Integer.valueOf( dataSnapshot.child("SubCategoryId").getValue().toString()));
                            mapChild.put(categoryName, subcategoryList);
                        }
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
                });


                categoryAdapter.notifyDataSetInvalidated();

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

      expLV.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int previousItem = -1;

            @Override
            public void onGroupExpand(int groupPosition) {
                if (groupPosition != previousItem)
                    expLV.collapseGroup(previousItem);
                previousItem = groupPosition;
            }
        });


        expLV.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                String selected = (String) categoryAdapter.getChild(
                        groupPosition, childPosition);

                Intent intent = new Intent(MainActivity.this, FaisTabActivity.class);
                intent.putExtra("SubCategoryId",selected);
                startActivity(intent);
                return true;
            }
        });




    }

}







