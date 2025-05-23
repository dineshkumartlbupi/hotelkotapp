package com.saptrishi.hotelkotapp.view.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
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
import com.saptrishi.hotelkotapp.model.MySqliteDatabase;
import com.saptrishi.hotelkotapp.model.MySqliteIPDataBase;
import com.saptrishi.hotelkotapp.util.IpServiceChecker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import static org.acra.ACRA.log;

import androidx.appcompat.app.AppCompatActivity;

public class ServiceSettingActivity extends AppCompatActivity {
    EditText et_appName, et_ipaddress;
    RequestQueue rq;
    TextView tv_error;
    SharedPreferences sf;
    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_setting);
        tv_error=(TextView)findViewById(R.id.tv_error);
        et_appName = (EditText) findViewById(R.id.et_appName);
        et_ipaddress = (EditText) findViewById(R.id.et_ipaddress);
        sf = getSharedPreferences("Restaurant", Context.MODE_PRIVATE);
        et_ipaddress.setText(sf.getString("Ip", ""));
        et_appName.setText(sf.getString("Appname", ""));

        et_ipaddress.setText("");
        et_appName.setText("");

    }
    public void submit(View v) {
        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        final String appname = et_appName.getText().toString().trim();
        final String ipaddress = et_ipaddress.getText().toString().trim();
        if (appname.trim().isEmpty()) {
            et_appName.setError("Kindly enter App Name");
            et_appName.requestFocus();
        } else {
            if (ipaddress.trim().isEmpty()) {
                et_ipaddress.setError("Kindly Enter ip Address");
                et_ipaddress.requestFocus();
            } else {
//                Toast.makeText(this, ipaddress, Toast.LENGTH_SHORT).show();
//                Toast.makeText(this, appname, Toast.LENGTH_SHORT).show();

                sf = getSharedPreferences("Restaurant", Context.MODE_PRIVATE);
                SharedPreferences.Editor ed = sf.edit();
                ed.putString("Ip", ipaddress);
                ed.putString("Appname", appname);
                ed.apply();
                new IpServiceChecker(ipaddress, appname);

                //   String url = "http://192.168.202.65/Hotel_SeviceApp/Kanpur_HotelKotApp_Service.svc/ResturantIteamGrpApp/RestCode/UUN001/CateCode/Food";
               // http://183.83.180.173/kot/Kanpur_HotelKotApp_Service.svc/ResturantIteamGrpApp/RestCode/UUN001/CateCode/Food
              //  http://183.83.180.173/kot/Kanpur_HotelKotApp_Service.svc/ResturantIteamGrpApp/RestCode/UUN001/CateCode/Beverage



                String url = "http://" + IpServiceChecker.getIpaddress() + "/" + IpServiceChecker.getAppName() + "/Kanpur_HotelKotApp_Service.svc/CheckService";
                Log.e("anwes", url);
//
//
//                String url = "http://192.168.0.117/Hotel_ServiceApp/Kanpur_HotelKotApp_Service.svc/Hotel_DeviceIP_Post";

                MySqliteDatabase ip = new MySqliteDatabase(this);
                ip.insertIPInfoTable(ipaddress, appname);

                RequestQueue requestQueue = Volley.newRequestQueue(this);

                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {


                        JSONObject jsonObject = null;
                        try {
                            jsonObject = (JSONObject) jsonArray.get(0);
                            String errormsg = jsonObject.getString("errormessage");
                            Log.e("msg", "my"+errormsg);
                            if (errormsg.equals("Service And DB IS Connected")) {
                                progressDialog.dismiss();
                                Intent i = new Intent(ServiceSettingActivity.this, LoginActivity.class);
                                startActivity(i);
                                finish();

                            }
                            else{
                                progressDialog.dismiss();
                                tv_error.setVisibility(View.VISIBLE);
                                tv_error.setText("Please connect same IP Address");
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.e("volleyerror", volleyError + "");
                        progressDialog.dismiss();
                        tv_error.setVisibility(View.VISIBLE);
                        tv_error.setText("Please connect with same IP Address");
                    }
                });


                requestQueue.add(jsonArrayRequest);


//                try {
//                    RequestQueue queue = Volley.newRequestQueue(this);
//
//
//                    JSONObject jsonBody = new JSONObject();
//                    jsonBody.put("AppName", ipaddress);
//                    try {
//                        jsonBody.put("AppIP", appname);
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//
//
//                    final String requestBody = jsonBody.toString();
//                    // Request a string response from the provided URL.
//                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//                        @Override
//                        public void onResponse(String response) {
//                            //Log.e("ResponseCheck:-", "" + response);
//                                if(response.equals("200")) {
////                                    Toast.makeText(context,"पशु पंजीकरण अद्यतन/updated डेटा सफलतापूर्वक भेज दिया गया है !",Toast.LENGTH_SHORT).show();
////                                    animalUpdateDetails.clear();
////                                    deleteUpdateTbl();
////                                    Toast.makeText(ServiceSettingActivity.this, response, Toast.LENGTH_SHORT).show();
//                                    Intent i=new Intent(ServiceSettingActivity.this,LoginActivity.class);
//                                    startActivity(i);
//                                    finish();
//
//
//
//                                }
//
//                        }
//
//                    }, new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//                            VolleyLog.d("", "Error...." + error);
//                        }
//                    }) {
//                        @Override
//                        public String getBodyContentType() {
//                            return "application/json; charset=utf-8";
//                        }
//
//                        @Override
//                        public byte[] getBody() throws AuthFailureError {
//                            try {
//                                return requestBody == null ? null : requestBody.getBytes("utf-8");
//                            } catch (UnsupportedEncodingException uee) {
//                                VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
//                                return null;
//                            }
//                        }
//
//                        @Override
//                        protected Response<String> parseNetworkResponse(NetworkResponse response) {
//                            String responseString = "";
//                            if (response != null) {
//                                responseString = String.valueOf(response.statusCode);
//                            }
//                            return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
//                        }
//                    };
//                    // Add the request to the RequestQueue.
//                    queue.add(stringRequest);
//
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }


//                StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String s) {
//                        Log.e("response",s);
//                        Toast.makeText(ServiceSettingActivity.this, "on REsponse "+s, Toast.LENGTH_SHORT).show();
//                        Intent i=new Intent(ServiceSettingActivity.this,LoginActivity.class);
//                        startActivity(i);
//                        finish();
//
//                    }
//                }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError volleyError) {
//                        Toast.makeText(ServiceSettingActivity.this, volleyError+"", Toast.LENGTH_SHORT).show();
//                        volleyError.printStackTrace();
//
//                    }
//                })
//                {
//                    protected Map<String,String> getParams(){
//
//                        HashMap<String,String> hm= new HashMap<>();
//                        hm.put("AppName",appname);
//                        hm.put("AppIP",ipaddress);
//
//
//                        return hm;
//
//
//                    }
//                };rq.add(stringRequest);


            }
        }


    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
}
