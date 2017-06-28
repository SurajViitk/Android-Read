package com.example.root.read;

import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

public class GetSSID extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner spin1;
    Spinner spin2;
    Spinner spin3;
    Spinner spin4;
    Spinner spin5;

    Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_ssid);
        spin1=(Spinner) findViewById(R.id.spinner);
        spin1.setOnItemSelectedListener(this);
        spin2=(Spinner) findViewById(R.id.spinner2);
        spin2.setOnItemSelectedListener(this);
        spin3=(Spinner) findViewById(R.id.spinner3);
        spin3.setOnItemSelectedListener(this);
        spin4=(Spinner) findViewById(R.id.spinner4);
        spin4.setOnItemSelectedListener(this);
        spin5=(Spinner) findViewById(R.id.spinner5);
        spin5.setOnItemSelectedListener(this);
        WifiManager wf=MainActivity.wifi;
        wf.startScan();
        List<ScanResult> res=wf.getScanResults();
        List<String> ssid=new ArrayList<>();
        if(toast!=null)
            toast.cancel();
        toast= Toast.makeText(this,""+res.size(),Toast.LENGTH_SHORT);
        toast.show();
        for(ScanResult ress:res){
            ssid.add(ress.SSID.toString());
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, ssid);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin2.setAdapter(dataAdapter);
        spin1.setAdapter(dataAdapter);
        spin3.setAdapter(dataAdapter);
        spin4.setAdapter(dataAdapter);
        spin5.setAdapter(dataAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()){
            case R.id.spinner:
                MainActivity.SSID1=parent.getItemAtPosition(position).toString();
            case R.id.spinner2:
                MainActivity.SSID2=parent.getItemAtPosition(position).toString();
            case R.id.spinner3:
                MainActivity.SSID3=parent.getItemAtPosition(position).toString();
            case R.id.spinner4:
                MainActivity.SSID4=parent.getItemAtPosition(position).toString();
            case R.id.spinner5:
                MainActivity.SSID5=parent.getItemAtPosition(position).toString();
    }
    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }



}
