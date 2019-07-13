package com.example.appnotifirev3;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HomeActivity extends AppCompatActivity implements HomeFragmentNoDevice.FragmentListener,
        DevicesFragment.FragmentListener,AddDeviceFragment.FragmentListener{

    BottomNavigationView bottomNavigationView;
    String title="Home",latitude="",longitude="";

    //FusedLocationProviderClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("");
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeFragmentNoDevice()).commit();

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                //return false;
                Fragment selected=null;

                switch (menuItem.getItemId()){
                    case R.id.nav_devices:
                        selected = new DevicesFragment();
                        title="Devices";
                        break;
                    case R.id.nav_history:
                        selected = new HistoryFragment();
                        title="History";
                        break;
                    case R.id.nav_home:
                        selected = new HomeFragment();
                        title="Home";
                        break;

                }

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selected).commit();
                getSupportActionBar().setTitle(title);

                return true;
            }
        });
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        //super.onAttachFragment(fragment);
        if(fragment instanceof HomeFragmentNoDevice){
            HomeFragmentNoDevice homeFragmentNoDevice = (HomeFragmentNoDevice) fragment;
            homeFragmentNoDevice.setFragmentListener(this);
        }
        else if(fragment instanceof  DevicesFragment){
            DevicesFragment devicesFragment = (DevicesFragment) fragment;
            devicesFragment.setFragmentListener(this);
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
//        reference.child("nama_device").setValue(namaNotifire);
        reference = FirebaseDatabase.getInstance().getReference("user");
        reference = reference.child("adi123");
        reference = reference.child("device").child(idNotifire);
        reference.child("nama_device").setValue(namaNotifire);

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
}
