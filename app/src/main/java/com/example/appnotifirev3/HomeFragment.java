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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
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

    static ArrayList<NotifireDevice> notifireDevices;
    static TextView namaNotifire,statusNotifire;
    ImageButton next, prev;
    static ImageView imageView;
    static Button teleponPemadam;

    FragmentListener fragmentListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_home,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imageView = view.findViewById(R.id.statusNotifire);
        teleponPemadam = view.findViewById(R.id.buttonTelepon);
        next = view.findViewById(R.id.buttonNext);
        prev = view.findViewById(R.id.buttonPrev);
        namaNotifire = view.findViewById(R.id.namaNotifire);
        statusNotifire = view.findViewById(R.id.labelStatusNotifire);
        //textView.setText();

        String namaDevice = notifireDevices.get(0).getNamaDevice();
        String statusDevice = notifireDevices.get(0).getStatusDevice();
        namaNotifire.setText(namaDevice);
        if(statusDevice.equals("1")){
            statusNotifire.setText("Status: Aman");
            teleponPemadam.setVisibility(View.INVISIBLE);
            imageView.setImageResource(R.drawable.ic_action_status_aman);
        }
        else if(statusDevice.equals("2")){
            statusNotifire.setText("Status: Kebakaran");
            imageView.setImageResource(R.drawable.ic_action_status_danger);
            teleponPemadam.setVisibility(View.VISIBLE);
        }

        next.setVisibility(View.INVISIBLE);
        prev.setVisibility(View.INVISIBLE);

        teleponPemadam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentListener.hubungiPemadam();
            }
        });

    }

    public void setData(ArrayList<NotifireDevice> notifireDevices){
        this.notifireDevices = notifireDevices;
    }

    public static void notifyDataSetChanged(String notifireId){
        String namaDevice = notifireDevices.get(0).getNamaDevice();
        String statusDevice = notifireDevices.get(0).getStatusDevice();
        if(statusDevice.equals("1")){
            statusNotifire.setText("Status: Aman");
            teleponPemadam.setVisibility(View.INVISIBLE);
            imageView.setImageResource(R.drawable.ic_action_status_aman);
        }
        else if(statusDevice.equals("2")){
            statusNotifire.setText("Status: Kebakaran");
            imageView.setImageResource(R.drawable.ic_action_status_danger);
            teleponPemadam.setVisibility(View.VISIBLE);
        }
    }


    public void setFragmentListener(FragmentListener fragmentListener){
        this.fragmentListener = fragmentListener;
    }

    public interface FragmentListener{
        public void hubungiPemadam();
    }

}
