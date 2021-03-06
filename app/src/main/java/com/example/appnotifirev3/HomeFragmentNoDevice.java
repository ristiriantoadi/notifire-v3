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


///**
// * A simple {@link Fragment} subclass.
// * Activities that contain this fragment must implement the
// * {@link HomeFragmentNoDevice.OnFragmentInteractionListener} interface
// * to handle interaction events.
// * Use the {@link HomeFragmentNoDevice#newInstance} factory method to
// * create an instance of this fragment.
// */
public class HomeFragmentNoDevice extends Fragment {

    Button addDevice;
    FragmentListener fragmentListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_home_fragment_no_device,container,false);
    }


    public void setFragmentListener(FragmentListener fragmentListener){
        this.fragmentListener = fragmentListener;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        addDevice = view.findViewById(R.id.buttonAddDevice);

        addDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentListener.addDeviceClicked();
            }
        });
    }

    public interface FragmentListener{
        public void addDeviceClicked();
    }

}
