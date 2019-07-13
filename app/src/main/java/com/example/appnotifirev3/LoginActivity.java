package com.example.appnotifirev3;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    TextView goToRegisterButton;
    EditText username,password;
    Button loginButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        goToRegisterButton = findViewById(R.id.goToRegisterActivityButton);
        loginButton = findViewById(R.id.buttonLogin);
        username = findViewById(R.id.usernameEditText);
        password = findViewById(R.id.passwordEditText);

        goToRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),RegisterActivity.class);
                startActivity(intent);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(username.getText().toString().isEmpty() || password.getText().toString().isEmpty()){
                    showWrongPassorUsernameMessage();
                }
                else {
                    doLogin(username.getText().toString(), password.getText().toString());
                }
            }
        });

    }

    protected void doLogin(final String username, final String password){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("user");
        reference = reference.child(username);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("username",username);
                Log.d("password",password);
                if(dataSnapshot.getValue() == null){
                    showWrongPassorUsernameMessage();
                    return;
                }
                String passwordTemp = dataSnapshot.child("password").getValue().toString();
                if(password.equals(passwordTemp)){
//                    Toast.makeText(LoginActivity.this, "Benar", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
                    startActivity(intent);
                }
                else{
                    showWrongPassorUsernameMessage();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    protected  void  showWrongPassorUsernameMessage(){
        String wrongPassOrUsername = "Password/username salah";
        Toast.makeText(getApplicationContext(),wrongPassOrUsername,Toast.LENGTH_SHORT).show();
    }
}
