package com.example.appnotifirev3;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


///**
// * A simple {@link Fragment} subclass.
// * Activities that contain this fragment must implement the
// * {@link DevicesFragment.OnFragmentInteractionListener} interface
// * to handle interaction events.
// * Use the {@link DevicesFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
public class DevicesFragment extends Fragment {

    FloatingActionButton button;
    FragmentListener fragmentListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_devices,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        button  = view.findViewById(R.id.floatingButtonaddDevice);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getContext(),"Clicked",Toast.LENGTH_SHORT).show();
                fragmentListener.floatingButtonClicked();
            }
        });
    }
    public void setFragmentListener(FragmentListener fragmentListener){
        this.fragmentListener = fragmentListener;
    }


    public interface FragmentListener{
        public void floatingButtonClicked();
    }
}
