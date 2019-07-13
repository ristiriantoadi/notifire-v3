package com.example.appnotifirev3;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

//
///**
// * A simple {@link Fragment} subclass.
// * Activities that contain this fragment must implement the
// * {@link HomeFragment.OnFragmentInteractionListener} interface
// * to handle interaction events.
// * Use the {@link HomeFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
public class HomeFragment extends Fragment {

    ArrayList<NotifireDevice> notifireDevices;
    TextView namaNotifire,statusNotifire;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_home,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        namaNotifire = view.findViewById(R.id.namaNotifire);
        statusNotifire = view.findViewById(R.id.labelStatusNotifire);
        //textView.setText();
        String namaDevice = notifireDevices.get(0).getNamaDevice();
        String statusDevice = notifireDevices.get(0).getStatusDevice();
        namaNotifire.setText(namaDevice);
        statusNotifire.setText(statusDevice);
    }

    public void setData(ArrayList<NotifireDevice> notifireDevices){
        this.notifireDevices = notifireDevices;
    }

}
