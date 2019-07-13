package com.example.appnotifirev3;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity implements HomeFragmentNoDevice.FragmentListener,
        DevicesFragment.FragmentListener,AddDeviceFragment.FragmentListener{

    BottomNavigationView bottomNavigationView;
    String title="Home";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(title);
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
        //Toast.makeText(getApplicationContext(),"Clicked",Toast.LENGTH_SHORT).show();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new AddDeviceFragment()).commit();
        getSupportActionBar().setTitle(title);
    }

    @Override
    public void saveButtonClicked() {
        Toast.makeText(getApplicationContext(),"Save clicked",Toast.LENGTH_SHORT).show();
    }
}
