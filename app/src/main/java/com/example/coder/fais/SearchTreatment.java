package com.example.coder.fais;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coder.fais.models.Categories;
import com.example.coder.fais.models.TreatmentInfo;
import com.example.coder.fais.utils.Constants;
import com.example.coder.fais.utils.FireBase;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SearchTreatment extends AppCompatActivity {
    DatabaseReference myRef;
    Query query;
    TextView nodataTxt;
    ChildEventListener mChildEventListener,mChildEventListener2;
    TreatmentInfo info;
    String searchTxt;
    ArrayList<Integer> treatments= new ArrayList<>();
    int fragmentCounter=0;
    FragmentManager fm;
    FragmentTransaction ft;
    ArrayList<Categories> categoriesList = new ArrayList<Categories>();
    CustomizedListAdapter listAdapter;
    ListView catergoryListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_treatment);
        searchTxt=getIntent().getStringExtra("searchTxt").toString();
        myRef = FireBase.getInstance().getFireBaseReference(Constants.FIRBASE_Treatment_DATA);
        catergoryListView = (ListView) findViewById(R.id.lv_subcategory_list);
        listAdapter = new CustomizedListAdapter(this, categoriesList);
        nodataTxt=(TextView)findViewById(R.id.nodataTxt);
        nodataTxt.setText("No Data Found");
        catergoryListView.setAdapter(listAdapter);
        catergoryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent intent = new Intent(SearchTreatment.this, FaisTabActivity.class);
                int i=categoriesList.get(position).getCategoryid();
                intent.putExtra("SubCategoryId",i);
                startActivity(intent);
            }
        });

        mChildEventListener = new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                info=new TreatmentInfo();
                for (DataSnapshot messageSnapshot: dataSnapshot.getChildren()) {

                    if(messageSnapshot.getKey().equals("Steps"))
                        info.setSteps((ArrayList<String>)messageSnapshot.getValue());
                    else if (messageSnapshot.getKey().equals("Symptoms"))
                        info.setSymptoms((ArrayList<String>)messageSnapshot.getValue());
                    else if(messageSnapshot.getKey().equals("SubCategoryId"))
                        info.setSubCategoryId(Integer.valueOf(messageSnapshot.getValue().toString()));
                }
                boolean found=false;
                for(String step:info.getSteps())
                {
                    if(step.toLowerCase().contains(searchTxt.toLowerCase()))
                    {
                        found=true;
                        break;
                    }
                }
                if(!found)
                {
                    for(String symptom:info.getSymptoms())
                    {
                        if(symptom.toLowerCase().contains(searchTxt.toLowerCase()))
                        {
                            found=true;
                            break;
                        }
                    }
                }
                if(found)
                {
                    nodataTxt.setText("");
                    myRef = FireBase.getInstance().getFireBaseReference(Constants.FIRBASE_SubCategory_DATA);
                    query=myRef.orderByChild("SubCategoryId").equalTo(info.getSubCategoryId());
                    mChildEventListener2 = new ChildEventListener() {
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

                    query.addChildEventListener(mChildEventListener2);
                   //treatments.add(info.getSubCategoryId());
                    /*LinearLayout topLayout = (LinearLayout)findViewById(R.id.container_fragment);
                    FrameLayout frameLayout = new FrameLayout(getApplicationContext());
                    frameLayout.setId(info.getSubCategoryId());
                    frameLayout.setBackgroundColor(Color.WHITE);
                    //LinearLayout.LayoutParams layoutParams= new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 200);
                    //layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                    //frameLayout.setLayoutParams(layoutParams);
                    topLayout.addView(frameLayout);*/
                    /*Fragment frag=new TreatmentInfoFragment(info.getSubCategoryId());
                    fm = getSupportFragmentManager();
                    ft = fm.beginTransaction();
                    ft.add(R.id.container_fragment, frag,"Frag-"+String.valueOf(fragmentCounter)+"-"+(String.valueOf(info.getSubCategoryId())));
                    ft.commit();
                    fragmentCounter++;*/
                }

                // Toast.makeText(getContext(),res,Toast.LENGTH_LONG).show();

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
