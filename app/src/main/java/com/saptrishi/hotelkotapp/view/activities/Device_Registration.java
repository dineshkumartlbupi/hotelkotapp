package com.saptrishi.hotelkotapp.view.activities;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.provider.Telephony;
//import android.support.v4.app.ActivityCompat;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.TelecomManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.saptrishi.hotelkotapp.R;
import com.saptrishi.hotelkotapp.domain.helper.Connectivity;
import com.saptrishi.hotelkotapp.domain.mock.ServiceAndDBChecker;
import com.saptrishi.hotelkotapp.model.MySqliteDatabase;
import com.saptrishi.hotelkotapp.util.IpServiceChecker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.os.Handler;

import static org.acra.ACRA.log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;


public class Device_Registration extends AppCompatActivity {
    public boolean isRunning = false;
    EditText et_hotelName, et_hotelNumber, et_hotelSitecode;
    TextView tv_CompanyName;
    Button btn;
    String DateOfRegistration;
    boolean doubleBackToExitPressedOnce = false;
    private boolean retsuccess = false;
    private Handler handler;
    private Runnable runnableCode;

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_device__registration );
        handler = new Handler();
        et_hotelName = (EditText) findViewById( R.id.et_hotelName );
        et_hotelNumber = (EditText) findViewById( R.id.et_hotelNumber );
        et_hotelSitecode = (EditText) findViewById( R.id.et_hotelSitecode );
        //tv_CompanyName = (TextView) findViewById(R.id.tv_companyName);
//        tv_VendorName = (TextView) findViewById(R.id.tv_VendorName);
//
//        Button button;
//        button=(Button)findViewById(R.id.saveData);
//        button.setBackgroundResource(R.drawable.capsuleshape);

        //requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE}, 1);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions( new String[]{Manifest.permission.READ_PHONE_STATE}, 1 );
        }

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat( "yyyy / MM / dd " );
        DateOfRegistration = mdformat.format( calendar.getTime() );



        btn = (Button) findViewById( R.id.btn_save );


        btn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn.setClickable(true);
                if (et_hotelSitecode.getText().toString().trim().isEmpty()) {
                    et_hotelSitecode.setError( "please enter site code" );
                    et_hotelSitecode.requestFocus();
                } else {
                    if (et_hotelName.getText().toString().trim().isEmpty()) {
                        et_hotelName.setError( "please enter hotel name" );
                        et_hotelName.requestFocus();
                    } else {
                        if (et_hotelNumber.getText().toString().trim().isEmpty()) {
                            et_hotelNumber.setError( "please enter hotel's number" );
                            et_hotelNumber.requestFocus();
                        } else {
                            final String hotelName = et_hotelName.getText().toString().trim().replaceAll( "\\s", "_" );
                            final String hotelNumber = et_hotelNumber.getText().toString().trim();
                            final String sitecode = et_hotelSitecode.getText().toString().trim();
                            TelephonyManager tm = (TelephonyManager) getSystemService( Context.TELEPHONY_SERVICE );
                            try {

                                    final String dev_IEMI = "36099155321055665475";

                                    // Define the code block to be executed

                                    runnableCode = new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                sendDeviceInfo( hotelName, hotelNumber, sitecode, dev_IEMI ); // Volley Request

                                                // Repeat this the same runnable code block again another 2 seconds
                                                handler.postDelayed( runnableCode, 2000 );
                                            } catch (Exception exc) {
                                                exc.printStackTrace();
                                                // isRunning = false;
                                            }
                                        }
                                    };
                                    // Start the initial runnable task by posting through the handler
                                    handler.post( runnableCode );

                            } catch (Exception ex) {
                                Toast.makeText( Device_Registration.this, "Kindly give Phone permission", Toast.LENGTH_SHORT ).show();
                            }
                        }
                    }
                }
            }
        } );


    }

    public boolean isRunning() {
        return isRunning;
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText( this, "Please click BACK again to exit", Toast.LENGTH_SHORT ).show();

        new Handler().postDelayed( new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000 );
    }


    public boolean sendDeviceInfo(final String Hname, final String Hnumber, final String sitecode, final String dev_IEMI) {

        SharedPreferences prefs = getSharedPreferences("Config", MODE_PRIVATE);
        String ip = prefs.getString("ip", "");
        String port = prefs.getString("port", "");
        String projectPath = prefs.getString("projectPath", "");
        String servicePath = prefs.getString("servicePath", "");
        if (ip.isEmpty() || projectPath.isEmpty() || servicePath.isEmpty()) {
            Toast.makeText(this, "Please configure all settings first", Toast.LENGTH_SHORT).show();
            return false;
        }
        // Construct base URL
        String DYNAMIC_URL = "http://"+ip+"/"+port+"/"+projectPath + "/" + servicePath+"/"+"HotelName/"+ Hname + "/HotelMobile/" + Hnumber + "/SiteCode/" + sitecode + "/Device_IEMINo/" + dev_IEMI;
        Log.e( "DYNAMIC_URL : ", ""+ DYNAMIC_URL );
        String STATIC_URL ="http://192.168.0.142/Hotel_ServiceApp/Kanpur_HotelKotApp_Service.svc/Hotel_DeviceReg_Post/HotelName/" + Hname + "/HotelMobile/" + Hnumber + "/SiteCode/" + sitecode + "/Device_IEMINo/" + dev_IEMI;
        Log.e( "STATIC_URL : ", ""+ STATIC_URL );
        RequestQueue requestQueue = Volley.newRequestQueue( this );
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest( DYNAMIC_URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response)
            {
                try {

                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObject = (JSONObject) response.get( i );
                        String str_reb = "";
                        str_reb = jsonObject.optString( "errormessage" );
                        String noofDays = jsonObject.optString( "NoOfDays" );
//
                        int expiryDays = Integer.parseInt( noofDays );
                        Log.e( "response", "" + response + "" );

                        String expiryDate = DateOfRegistration;  // Start date
                        SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd" );
                        Calendar c = Calendar.getInstance();
                        try {
                            c.setTime( sdf.parse( expiryDate ) );
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        c.add( Calendar.DATE, Integer.parseInt( noofDays ) );
                        sdf = new SimpleDateFormat( "dd/MM/yyyy" );
                        Date resultdate = new Date( c.getTimeInMillis() );
                        expiryDate = sdf.format( resultdate );

                        SharedPreferences sfa = getSharedPreferences( "Restaurant", Context.MODE_PRIVATE );
                        SharedPreferences.Editor eda = sfa.edit();

                        eda.putString( "ExpiryDate", expiryDate );
                        eda.apply();


                        Log.e( "expiry", expiryDate );
                        if (str_reb.equals( "" )) {
                            Toast.makeText( Device_Registration.this, "Kindly wait", Toast.LENGTH_SHORT ).show();
                        } else {


                            if ((str_reb.equals( "Approve Your Device" ) || str_reb.equals( "Device Already exits" )) && expiryDays > 0) {
                                retsuccess = true;
                                Log.e( "msgindeviceRegistration", str_reb );
                                SharedPreferences sf = getSharedPreferences( "Restaurant", Context.MODE_PRIVATE );
                                SharedPreferences.Editor ed = sf.edit();
                                ed.putString( "DeviceRegistrationValue", "true" );
                                ed.putString( "ExpiryDate", expiryDate );
                                ed.apply();
                                handler.removeCallbacks( runnableCode );
                                Log.e( "newExpiryDAte", expiryDate );
                                Intent intent = new Intent( Device_Registration.this, ServiceSettingActivity.class );
                                startActivity( intent );
                                finish();
                            } else {
//                                if (str_reb.equals("Device Already exits") && expiryDays >0)
//                                {
//                                    retsuccess = true;
//                                    SharedPreferences sf = getSharedPreferences("Restaurant", Context.MODE_PRIVATE);
//                                    SharedPreferences.Editor ed = sf.edit();
//                                    ed.putString("DeviceRegistrationValue", "true");
//                                    ed.apply();
//
////                                    MySqliteDatabase db = new MySqliteDatabase(Device_Registration.this);
////                                    db.insertDeviceRegistrationInfoTable(sitecode, Hname, dev_IEMI, Hnumber, expiryDate);
//
//
//                                    Intent intent = new Intent(Device_Registration.this, ServiceSettingActivity.class);
//                                    startActivity(intent);
//                                    finish();
//
//                                }
//                                else
//                                {
                                if (expiryDays < 1) {
                                    Toast.makeText( Device_Registration.this, str_reb, Toast.LENGTH_SHORT ).show();
                                    handler.removeCallbacks( runnableCode );
                                } else {
                                    Toast.makeText( Device_Registration.this, "Something went Wrong......", Toast.LENGTH_SHORT ).show();
                                    handler.removeCallbacks( runnableCode );
                                }

//                                }

                            }
                        }


                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e( "ErrorInLogin:", "" + e.getMessage() );
                    // Toast.makeText(StuAssemblyAttandance.this, "response: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                btn.setClickable( true );
                VolleyLog.d( "", "ErrorLoginInfo: " + error.getMessage() );
                Toast.makeText( Device_Registration.this, "Server Error" , Toast.LENGTH_SHORT ).show();
            }
        } );
        requestQueue.add( jsonArrayRequest );


        return retsuccess;
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!Connectivity.isConnected( getApplicationContext() )) {
            final Dialog dialog = new Dialog( Device_Registration.this );
            dialog.requestWindowFeature( Window.FEATURE_NO_TITLE );
            dialog.setContentView( R.layout.connection_dialog );
            Button dialogButton = (Button) dialog
                    .findViewById( R.id.dialogButtonOK );

            // if button is clicked, close the custom dialog
            dialogButton.setOnClickListener( new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    dialog.dismiss();

                }
            } );

            dialog.show();
        }

        // Show Whats New Features If Requires
        //new WhatsNewDialog(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }

}
