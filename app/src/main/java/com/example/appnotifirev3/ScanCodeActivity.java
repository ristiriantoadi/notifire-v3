package com.example.appnotifirev3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanCodeActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    ZXingScannerView scannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_code);
        scannerView = findViewById(R.id.scannerView);

        Toolbar toolbar = findViewById(R.id.toolbarScan);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Scan QR Code pada Device Notifire");


    }

    @Override
    public void handleResult(Result result) {
        //MainActivity.textView.setText(result.getText());
        AddDeviceFragment.idNotifire.setText(result.getText());
        Log.d("result", result.getText());
        onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        scannerView.stopCamera();
    }

    @Override
    protected void onResume() {
        super.onResume();
        scannerView.setResultHandler(this);
        scannerView.startCamera();
    }
}