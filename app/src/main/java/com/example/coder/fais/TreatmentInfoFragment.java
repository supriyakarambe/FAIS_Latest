package com.example.coder.fais;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coder.fais.models.TreatmentInfo;
import com.example.coder.fais.utils.Constants;
import com.example.coder.fais.utils.FireBase;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;


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

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        Toast.makeText(getActivity().getApplicationContext(), "SubCategoryId :"+subId, Toast.LENGTH_SHORT).show();

        myRef = FireBase.getInstance().getFireBaseReference(Constants.FIRBASE_SubCategory_DATA);
        query=myRef.orderByChild("CategoryId").equalTo(subId);
//        mChildEventListener = new ChildEventListener() {
//
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                 info=new TreatmentInfo();
//                for (DataSnapshot messageSnapshot: dataSnapshot.getChildren()) {
//
//                    if(messageSnapshot.getKey().equals("Symptoms"))
//                        info.setSymptoms(messageSnapshot.getValue().toString());
//                    else if(messageSnapshot.getKey().equals("Steps"))
//                        info.setSteps(messageSnapshot.getValue().toString());
//
//                }
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        };
//
//
//        symptoms.setText(info.getSymptoms());
//        steps.setText(info.getSteps());
        return inflater.inflate(R.layout.fragment_treatment_info, container, false);

    }

    @SuppressLint("ValidFragment")
    public TreatmentInfoFragment(int id) {
        this.subId=id;
    }


    public TreatmentInfoFragment() {
    }
}
