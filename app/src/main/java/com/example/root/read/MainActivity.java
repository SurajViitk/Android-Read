package com.example.root.read;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    static WifiManager wifi;
    static String SSID1;
    static String SSID2;
    static String SSID3;
    static String SSID4;
    static String SSID5;
    double signal1[]=new double[10];
    double signal2[]=new double[10];
    double signal3[]=new double[10];
    double signal4[]=new double[10];
    double signal5[]=new double[10];
    TextView tV1 ;
    EditText e1;
    EditText e2;
    Button bRead;
    Button bShow;
    Toast toast;
    FileOutputStream fout;
    OutputStreamWriter out;
    FileOutputStream fouts;
    OutputStreamWriter outs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkAndRequestPermissions();
        tV1=(TextView) findViewById(R.id.textView);
        tV1.setMovementMethod(new ScrollingMovementMethod());
        bRead=(Button) findViewById(R.id.button);
        bShow=(Button) findViewById(R.id.button2);
        e1=(EditText) findViewById(R.id.editText);
        e2=(EditText) findViewById(R.id.editText2);
        wifi=(WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        wifi.setWifiEnabled(true);

    }

    public void Readclick(View v){
        try {
            fout = new FileOutputStream(new File(Environment.getExternalStorageDirectory().getPath() + "/file.txt"), true);
            out =new OutputStreamWriter(fout);
            fouts = new FileOutputStream(new File(Environment.getExternalStorageDirectory().getPath() + "/file_s.txt"), true);
            outs =new OutputStreamWriter(fouts);
            out.append(e1.getText()+" "+e2.getText()+" ");
            outs.append(e1.getText()+" "+e2.getText()+" \n");
            WifiS wfs = new WifiS();
            wfs.execute();

        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public void showb(View v){
        tV1.setText("");
        try{
            File file = new File(Environment.getExternalStorageDirectory().getPath()+"/file.txt");
            Scanner sc = new Scanner(file);
            while(sc.hasNext()){
                tV1.append(sc.next()+" ");
            }

        }
        catch(Exception e){
            e.printStackTrace();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.item1:
                Intent i=new Intent(MainActivity.this,com.example.root.read.GetSSID.class);
                startActivity(i);
                return true;
            case R.id.item2:
                Log.d("something","done");
                AlertDialog.Builder helpBuilder = new AlertDialog.Builder(this);
                helpBuilder.setTitle("About");
                helpBuilder.setMessage("Created by: Suraj Verma \n email: surajv@iitk.ac.in");
                helpBuilder.setNeutralButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing
                    }
                });

                Log.d("something","done2");
                AlertDialog helpDialog = helpBuilder.create();
                helpDialog.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class WifiS extends AsyncTask<Void,String,Void> {

        int l=0;

        protected Void doInBackground(Void... Params) {
            double mean1=0,mean2=0,mean3=0,mean4=0,mean5=0;
            double e1=0,e2=0,e3=0,e4=0,e5=0;
            while(l<10){

                wifi.startScan();
                List<ScanResult> res = wifi.getScanResults();
                if(l==0)
                publishProgress("2");
                publishProgress("1");
                for (ScanResult ress : res) {
                    if(ress.SSID.toString().equals(SSID1))
                        signal1[l]=ress.level;
                    else if(ress.SSID.toString().equals(SSID2))
                        signal2[l]=ress.level;
                    else if(ress.SSID.toString().equals(SSID3))
                        signal3[l]=ress.level;
                    else if(ress.SSID.toString().equals(SSID4))
                        signal4[l]=ress.level;
                    else if(ress.SSID.toString().equals(SSID5))
                        signal5[l]=ress.level;
                }
                try{
                    outs.append(l+" "+signal1[l]+" "+signal2[l]+" "+signal3[l]+" "+signal4[l]+" "+signal5[l]+"\n");
                }
                catch (Exception e){
                    e.printStackTrace();

                }
                mean1=mean1+signal1[l];
                mean2=mean2+signal2[l];
                mean3=mean3+signal3[l];
                mean4=mean4+signal4[l];
                mean5=mean5+signal5[l];



                try {
                    Thread.sleep(2000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if(1==2)
                    break;
                l++;
            }
            mean1=mean1/10;
            mean2=mean2/10;
            mean3=mean3/10;
            mean4=mean4/10;
            mean5=mean5/10;

            for(int i=0;i<10;i++){
                e1+=Math.pow(signal1[i]-mean1,2);
                e2+=Math.pow(signal2[i]-mean2,2);
                e3+=Math.pow(signal3[i]-mean3,2);
                e4+=Math.pow(signal4[i]-mean4,2);
                e5+=Math.pow(signal5[i]-mean5,2);
            }
            e1/=9;
            e2/=9;
            e3/=9;
            e4/=9;
            e5/=9;
            e1=Math.sqrt(e1);
            e2=Math.sqrt(e2);
            e3=Math.sqrt(e3);
            e4=Math.sqrt(e4);
            e5=Math.sqrt(e5);
            try {
               out.append(mean1+" "+mean2+" "+mean3+" "+mean4+" "+mean5+" "+e1+" "+e2+" "+e3+" "+e4+" "+ e5+"\n");
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(String... Values){
            if(Values[0].equals("2")){
                if(toast!=null)
                    toast.cancel();
                toast= Toast.makeText(getBaseContext(),"started",Toast.LENGTH_SHORT);
                toast.show();
            }
            if(Values[0].equals("1")){
                tV1.setText(""+l+"\n");
            }
            else {
                tV1.append(Values[0]+"\n\n");
            }


        }
        @Override
        protected void onPostExecute(Void v){
            try{
                out.close();
                outs.close();
            }
            catch(Exception e){
                e.printStackTrace();
            }

        }


    }

    private boolean checkAndRequestPermissions() {
        int WIFISTATE = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_WIFI_STATE);
        int CHANGESTATE=ContextCompat.checkSelfPermission(this, Manifest.permission.CHANGE_WIFI_STATE);
        int ACCESS = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        int ACCESSFINE= ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        int READE= ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int WRITEE= ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (WIFISTATE != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_WIFI_STATE);
        }
        if (CHANGESTATE != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CHANGE_WIFI_STATE);
        }
        if (ACCESS != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        if (ACCESSFINE != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (READE != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (WRITEE != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),1);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        Log.d("Permission", "Permission callback called-------");
        switch (requestCode) {
            case 1: {

                Map<String, Integer> perms = new HashMap<>();
                // Initialize the map with both permissions
                perms.put(Manifest.permission.ACCESS_WIFI_STATE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.CHANGE_WIFI_STATE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.ACCESS_COARSE_LOCATION, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.ACCESS_FINE_LOCATION, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.READ_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                // Fill with actual results from user
                if (grantResults.length > 0) {
                    for (int i = 0; i < permissions.length; i++)
                        perms.put(permissions[i], grantResults[i]);
                    // Check for both permissions
                    if (perms.get(Manifest.permission.ACCESS_WIFI_STATE) == PackageManager.PERMISSION_GRANTED
                            && perms.get(Manifest.permission.CHANGE_WIFI_STATE) == PackageManager.PERMISSION_GRANTED
                            && perms.get(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                            && perms.get(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                            && perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED
                            && perms.get(Manifest.permission.READ_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED
                            ) {
                        Log.d("Permission", "sms & location services permission granted");
                        // process the normal flow
                        //else any one or both the permissions are not granted
                    } else {
                        Log.d("Permission", "Some permissions are not granted ask again ");
                        //permission is denied (this is the first time, when "never ask again" is not checked) so ask again explaining the usage of permission
//                        // shouldShowRequestPermissionRationale will return true
                        //show the dialog or snackbar saying its necessary and try again otherwise proceed with setup.
                        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_WIFI_STATE)
                                || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CHANGE_WIFI_STATE)
                                || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                                || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)
                                || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                                )









                        {
                            showDialogOK("SMS and Location Services Permission required for this app",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            switch (which) {
                                                case DialogInterface.BUTTON_POSITIVE:
                                                    checkAndRequestPermissions();
                                                    break;
                                                case DialogInterface.BUTTON_NEGATIVE:
                                                    // proceed with logic by disabling the related features or quit the app.
                                                    break;
                                            }
                                        }
                                    });
                        }
                        //permission is denied (and never ask again is  checked)
                        //shouldShowRequestPermissionRationale will return false
                        else {

                            //                            //proceed with logic by disabling the related features or quit the app.
                        }
                    }
                }
            }
        }

    }

    private void showDialogOK(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", okListener)
                .create()
                .show();
    }

}
