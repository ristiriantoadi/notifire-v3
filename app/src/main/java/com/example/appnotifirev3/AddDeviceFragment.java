package com.example.appnotifirev3;

import android.Manifest;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;


//
///**
// * A simple {@link Fragment} subclass.
// * Activities that contain this fragment must implement the
// * {@link AddDeviceFragment.OnFragmentInteractionListener} interface
// * to handle interaction events.
// * Use the {@link AddDeviceFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
public class AddDeviceFragment extends Fragment {

    Button save, scanQRCode, updateLokasi;
    static TextView idNotifire;
    TextView latitudeLangitude;
    FragmentListener fragmentListener;
    EditText namaNotifire;
    //FusedLocationProviderClient client;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_add_device,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        save = view.findViewById(R.id.buttonSave);
        scanQRCode = view.findViewById(R.id.buttonScan);
        idNotifire = view.findViewById(R.id.idNotifire);
        updateLokasi = view.findViewById(R.id.updateLokasi);
        latitudeLangitude = view.findViewById(R.id.lokasiLatitudeLangitude);
        namaNotifire = view.findViewById(R.id.namaNotifire);

        updateLokasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentListener.updateLokasi(latitudeLangitude);
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idNotifireString,namaNotifireString;
                idNotifireString = idNotifire.getText().toString();
                namaNotifireString = namaNotifire.getText().toString();
                //fragmentListener.saveButtonClicked(idNotifireString);
                fragmentListener.saveButtonClicked(idNotifireString,namaNotifireString);
            }
        });
        scanQRCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentListener.scanButtonClicked();
            }
        });


        //client = LocationServices.getFusedLocationProviderClient(this);

        //ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);

    }


    public void setFragmentListener(FragmentListener fragmentListener){
        this.fragmentListener = fragmentListener;
    }

    public interface FragmentListener{
        public void saveButtonClicked(String idNotifire, String namaNotifire);
        public void scanButtonClicked();
        public void updateLokasi(TextView textView);
    }
}
