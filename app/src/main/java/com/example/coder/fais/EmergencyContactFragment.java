package com.example.coder.fais;

import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class EmergencyContactFragment extends Fragment {
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_emergency_contact, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        Resources res = getResources();
        String[] items = res.getStringArray(R.array.emergency);


        ListView listView = (ListView) getActivity().findViewById(R.id.list_emergency);
        listView.setAdapter(new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, items));
    }
}
