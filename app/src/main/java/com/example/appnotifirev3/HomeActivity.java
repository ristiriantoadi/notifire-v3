package com.example.appnotifirev3;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements HomeFragmentNoDevice.FragmentListener,
        DevicesFragmentNoDevice.FragmentListener,AddDeviceFragment.FragmentListener,HomeFragment.FragmentListener{

    BottomNavigationView bottomNavigationView;
    String title="Home",latitude="",longitude="";
    ArrayList<NotifireDevice> notifireDevices = new ArrayList<>();

    private ProgressBar spinner;
    ///FusedLocationProviderClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);

        spinner = (ProgressBar) findViewById(R.id.progressBar1);


        bottomNavigationView = findViewById(R.id.bottom_navigation);
        //bottomNavigationView.setSelectedItemId(R.id.nav_home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                //return false;
                Fragment selected=null;

                switch (menuItem.getItemId()){
                    case R.id.nav_devices:
                        selected = new DevicesFragmentNoDevice();
                        title="Devices";
                        break;
                    case R.id.nav_history:
                        selected = new HistoryFragment();
                        title="History";
                        break;
                    case R.id.nav_home:
                        if(notifireDevices.isEmpty()){
                            selected = new HomeFragmentNoDevice();
                        }
                        else {
                            selected = new HomeFragment();
                        }
                        title="Home";
                        break;

                }

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selected).commit();
                getSupportActionBar().setTitle(title);

                return true;
            }
        });

        spinner.setVisibility(View.VISIBLE);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("user/adi123");
        reference.child("device").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue() == null){
                    bottomNavigationView.setSelectedItemId(R.id.nav_home);
                    spinner.setVisibility(View.INVISIBLE);
                }
                else{
                    for(DataSnapshot dataSnapshotIterator: dataSnapshot.getChildren()){
                        final String key = dataSnapshotIterator.getKey();
                        final String namaDevice = dataSnapshotIterator.child("nama_device").getValue().toString();
                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("alat");
                        databaseReference.child(key).child("situasi").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                String status = dataSnapshot.getValue().toString();
                                NotifireDevice notifireDevice = new NotifireDevice(namaDevice,status,key);
                                notifireDevices.add(notifireDevice);
                                spinner.setVisibility(View.INVISIBLE);
                                bottomNavigationView.setSelectedItemId(R.id.nav_home);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }


                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        reference.child("device").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                final String key = dataSnapshot.getKey();
                final String namaDevice = dataSnapshot.child("nama_device").getValue().toString();
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("alat");
                databaseReference.child(key).child("situasi").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String status = dataSnapshot.getValue().toString();
                        NotifireDevice notifireDevice = new NotifireDevice(namaDevice,status,key);
                        for(NotifireDevice notifireDeviceIterator: notifireDevices){
                            if(notifireDeviceIterator.getKeyDevice().
                                    equals(notifireDeviceIterator.getKeyDevice())){
                                notifireDeviceIterator.setStatusDevice(status);
                                return;
                            }
                        }
                        notifireDevices.add(notifireDevice);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                //String key = dataSnapshot.getKey();
                //DatabaseReference databaseReference = FirebaseDatabase.

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("alat");
        databaseReference.orderByChild("user").equalTo("adi123").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String key = dataSnapshot.getKey();
                String status = dataSnapshot.child("situasi").getValue().toString();
                for(NotifireDevice notifireDevice:notifireDevices){
                    if(notifireDevice.getKeyDevice().equals(key)){
                        notifireDevice.setStatusDevice(status);
                        Log.d("status",status);
                        HomeFragment.notifyDataSetChanged(notifireDevice.getKeyDevice());
                        return;
                    }
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

//        reference.child("device").addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//                    //namaDevice.add(dataSnapshot.child("nama_devi ce").getValue().toString());
//                    final String namaDevice = dataSnapshot.child("nama_device").getValue().toString();
//                    String key = dataSnapshot.getKey();
//                    //final String statusDevice="";
//                    DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("alat");
//                    reference1.child(key).child("situasi").addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                            String statusDevice = dataSnapshot.getValue().toString();
//                            NotifireDevice notifireDevice = new NotifireDevice(namaDevice,statusDevice);
//                            for(NotifireDevice notifireDeviceIterator:notifireDevices){
//                                if(notifireDeviceIterator.getNamaDevice().equals(notifireDevice.getNamaDevice())){
//                                    notifireDeviceIterator.setStatusDevice(statusDevice);
//                                    return;
//                                }
//                            }
//                            notifireDevices.add(notifireDevice);
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                        }
//                    });
//                    //notifireDevices.add()
//
//            }
//
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });


    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        //super.onAttachFragment(fragment);
        if(fragment instanceof HomeFragmentNoDevice){
            HomeFragmentNoDevice homeFragmentNoDevice = (HomeFragmentNoDevice) fragment;
            homeFragmentNoDevice.setFragmentListener(this);
        }
        else if(fragment instanceof HomeFragment){
            HomeFragment homeFragment = (HomeFragment) fragment;
            homeFragment.setData(notifireDevices);
            homeFragment.setFragmentListener(this);
        }
        else if(fragment instanceof DevicesFragmentNoDevice){
            DevicesFragmentNoDevice devicesFragmentNoDevice = (DevicesFragmentNoDevice) fragment;
            devicesFragmentNoDevice.setFragmentListener(this);
        }
        else if(fragment instanceof AddDeviceFragment){
            AddDeviceFragment deviceFragment = (AddDeviceFragment) fragment;
            deviceFragment.setFragmentListener(this);
        }
        //else if(fragment instanceof )
    }

    @Override
    public void addDeviceClicked() {
//        title = "Tambah Device";
//        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new AddDeviceFragment()).commit();
//        getSupportActionBar().setTitle(title);
        bottomNavigationView.setSelectedItemId(R.id.nav_devices);
        Log.d("title",title);
        Log.d("hahahah","true");

    }

    @Override
    public void floatingButtonClicked() {
        title="Tambah Device";
//        //Toast.makeText(getApplicationContext(),"Clicked",Toast.LENGTH_SHORT).show();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new AddDeviceFragment()).commit();
        getSupportActionBar().setTitle(title);
//        Intent intent=new Intent(getApplicationContext(),ScanCodeActivity.class);
//        startActivity(intent);

    }

    @Override
    public void saveButtonClicked(String idNotifire,String namaNotifire) {
        //Toast.makeText(getApplicationContext(),"Save clicked",Toast.LENGTH_SHORT).show();
        if(idNotifire.equals("-") || namaNotifire.isEmpty() || latitude.isEmpty() || longitude.isEmpty()){
            Toast.makeText(getApplicationContext(),"Isi semua form dengan benar",Toast.LENGTH_SHORT).show();
            return;
        }


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference = reference.child("alat").child(idNotifire);
//        reference.child("power").setValue("1");
//        reference.child("situasi").setValue("1");
//        reference.child("status").setValue("0");
        reference.child("lokasi").child("lat").setValue(latitude);
        reference.child("lokasi").child("long").setValue(longitude);
        reference.child("user").setValue("adi123");
//        reference.child("nama_device").setValue(namaNotifire);
        reference = FirebaseDatabase.getInstance().getReference("user");
        reference = reference.child("adi123");
        reference = reference.child("device").child(idNotifire);
        reference.child("nama_device").setValue(namaNotifire);



        Toast.makeText(getApplicationContext(),"Device berhasil ditambahkan",Toast.LENGTH_SHORT).show();
        NotifireDevice notifireDevice = new NotifireDevice(namaNotifire,"1",idNotifire);
        notifireDevices.add(notifireDevice);
        bottomNavigationView.setSelectedItemId(R.id.nav_home);
    }

    @Override
    public void scanButtonClicked() {
        Intent intent = new Intent(getApplicationContext(),ScanCodeActivity.class);
        startActivity(intent);
    }

    @Override
    public void updateLokasi(final TextView textView) {
        FusedLocationProviderClient client = LocationServices.getFusedLocationProviderClient(getApplicationContext());
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            Log.d("denied","true");


            return;
        }
        else{
            //Log.d("denied","false");
            client.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if(location != null){
                                //TextView textView = findViewById(R.id.text);
                                //textView.setText(location.toString());
                                String lokasi="";
                                lokasi="Latitude: "+location.getLatitude()+", ";
                                lokasi+="Longitude: "+location.getLongitude();

                                latitude = ""+location.getLatitude();
                                longitude = ""+location.getLongitude();

                                //Log.d("lokasi",location.toString());
                                textView.setText(lokasi);
                            }
                            else{
                                //TextView textView = findViewById(R.id.text);
                                textView.setText("Null");
                                Log.d("lokasi","null");

                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("error","error");
                            e.printStackTrace();
                        }
                    });
        }

    }

    @Override
    public void hubungiPemadam() {
        String nomor="12345";
        if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED){
            String uri = "tel:"+nomor;
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(uri));// Initiates the Intent
            startActivity(intent);
        }
        else{
            String uri = "tel:"+nomor;
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(uri));// Initiates the Intent
            startActivity(intent);
        }
    }
}
