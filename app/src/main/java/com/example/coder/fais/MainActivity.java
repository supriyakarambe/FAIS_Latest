package com.example.coder.fais;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.coder.fais.models.CategoryAdapter;
import com.example.coder.fais.models.SubCategories;
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
    private Map<String, ArrayList<SubCategories>> mapChild;
    private DatabaseReference myRef;
    private DatabaseReference subRef;
    private ArrayList<String> listCategories = new ArrayList<String>();
    private ArrayList<SubCategories> subCategory = new ArrayList<SubCategories>();
    private Button searchBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getList();

    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        return true;
    }*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.option_menu, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                //TODO write your code what you want to perform on search
                Intent intent = new Intent (MainActivity.this, SearchTreatment.class);
                intent.putExtra("searchTxt",query);
                startActivity(intent);
                return true;
            }
            @Override
            public boolean onQueryTextChange(String query) {
                //TODO write your code what you want to perform on search text change
               // Toast.makeText(getApplicationContext(),"Please enter search text2",Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        return true;
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
                        SubCategories sub=new SubCategories(Integer.valueOf( dataSnapshot.child("SubCategoryId").getValue().toString()),dataSnapshot.child("Name").getValue().toString());
                        if(mapChild.containsKey(categoryName)){
                            mapChild.get(categoryName).add(sub);
                        }else{
                            ArrayList<SubCategories> subcategoryList = new ArrayList<>();
                            subcategoryList.add(sub);
                            subCategory.add(new SubCategories(Integer.valueOf( dataSnapshot.child("SubCategoryId").getValue().toString()),dataSnapshot.child("Name").getValue().toString()));
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
                SubCategories selected = categoryAdapter.getChild(
                        groupPosition, childPosition);

                //Toast.makeText(getApplicationContext(),groupPosition +":"+ childPosition,Toast.LENGTH_LONG);
                Intent intent = new Intent(MainActivity.this, FaisTabActivity.class);
                intent.putExtra("SubCategoryId",selected.getSubCategoryId());
                startActivity(intent);
                return true;
            }
        });




    }

}







