package com.example.coder.fais;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;


@SuppressLint("ValidFragment")
public class TreatmentInfoFragment extends Fragment {
    @Nullable
    int subId;
    DatabaseReference myRef;
    Query query;
    ChildEventListener mChildEventListener;
    TextView symptoms;
    TextView steps;
    TreatmentInfo info;
    ImageView img;
    int res;
    @Override
      public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_treatment_info, container, false);
        img=(ImageView)view.findViewById(R.id.img_subcat);
        symptoms=(TextView) view.findViewById(R.id.symptomsDetails);
        steps=(TextView) view.findViewById(R.id.treatmentDetails);
        LoadData();
        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
    }


    @SuppressLint("ValidFragment")
    public TreatmentInfoFragment(int id) {
        this.subId=id;


    }
    public TreatmentInfoFragment() {
        super();
    }


    private void LoadData()
    {
        myRef = FireBase.getInstance().getFireBaseReference(Constants.FIRBASE_Treatment_DATA);
        query=myRef.orderByChild("SubCategoryId").equalTo(subId);


//        final Toast toast = Toast.makeText(getContext(), "subcategory_"+subId, Toast.LENGTH_LONG);
//        toast.show();

        String imagename = "subcategory_"+subId;
        res = getResources().getIdentifier(imagename, "drawable", this.getActivity().getPackageName());
        img.setImageResource(res);
        //Toast.makeText(getContext(),res,Toast.LENGTH_LONG).show();
        //imageview= (ImageView)findViewById(R.id.imageView);



        mChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                info=new TreatmentInfo();
                for (DataSnapshot messageSnapshot: dataSnapshot.getChildren()) {

                    if(messageSnapshot.getKey().equals("Steps"))
                        info.setSteps((ArrayList<String>)messageSnapshot.getValue());
                    else if (messageSnapshot.getKey().equals("Symptoms"))
                        info.setSymptoms((ArrayList<String>)messageSnapshot.getValue());
                }
                String stepTxt="";
                for (String step:info.getSteps()) {
                    stepTxt+="<font color='#d32f2f'>"+"\u25CF"+"</font>"+step+"</b><br>";
                }
                String symptomTxt="";
                for (String symptom:info.getSymptoms()) {
                    symptomTxt+="<font color='#d32f2f'>"+"\u25CF"+"</font>"+symptom+"<br>";
                }

                symptoms.setText(Html.fromHtml(symptomTxt));
                steps.setText(Html.fromHtml(stepTxt));
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

        query.addChildEventListener(mChildEventListener);

    }
}
