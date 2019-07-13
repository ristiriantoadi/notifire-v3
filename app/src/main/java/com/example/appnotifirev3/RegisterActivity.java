package com.example.appnotifirev3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    TextView goToLoginActivityButton;
    EditText namaLengkap,username,password,konfirmasiPassword;
    Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        goToLoginActivityButton = findViewById(R.id.goToLoginActivityButton);
        registerButton = findViewById(R.id.buttonRegister);
        namaLengkap = findViewById(R.id.namaLengkapEditText);
        username = findViewById(R.id.usernameRegisterEditText);
        password = findViewById(R.id.passwordRegisterEditText);
        konfirmasiPassword = findViewById(R.id.konfirmasiPasswordEditText);


        goToLoginActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(namaLengkap.getText().toString().isEmpty() || username.getText().toString().isEmpty()
                || password.getText().toString().isEmpty() || konfirmasiPassword.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Lengkapi form",Toast.LENGTH_SHORT).show();
                }
                else if(!password.getText().toString().equals(konfirmasiPassword.getText().toString())){
                    Toast.makeText(getApplicationContext(),"Konfirmasi password keliru",Toast.LENGTH_SHORT).show();
                }
                else{
                    //Toast.makeText(getApplicationContext(),"Input benar",Toast.LENGTH_SHORT).show();
                    doRegister(namaLengkap.getText().toString(),username.getText().toString(),password.getText().toString());
                }

            }
        });

    }

    protected  void doRegister(String namaLengkap,String username,String password){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference = reference.child("user").child(username);
        reference.child("level").setValue("2");
        reference.child("nama").setValue(namaLengkap);
        reference.child("password").setValue(password);
        Toast.makeText(getApplicationContext(),"Pendaftaran berhasil",Toast.LENGTH_SHORT);
        Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
        startActivity(intent);
    }
}
