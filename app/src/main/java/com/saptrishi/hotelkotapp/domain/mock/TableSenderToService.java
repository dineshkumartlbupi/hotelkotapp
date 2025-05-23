package com.saptrishi.hotelkotapp.domain.mock;

import android.content.Context;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.util.Log;
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
import com.saptrishi.hotelkotapp.model.MySqliteDatabase;
import com.saptrishi.hotelkotapp.util.IpServiceChecker;
import com.saptrishi.hotelkotapp.view.activities.Activity_Pos;
import com.saptrishi.hotelkotapp.view.activities.LoginActivity;
import com.saptrishi.hotelkotapp.view.activities.RestaurantListActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class TableSenderToService {

    Context context;

    int i = 1;

    public TableSenderToService(Context context) {
        this.context = context;
    }

    String editpox = "0";

    public void tableSender(String Pox) {

        if (!Pox.trim().isEmpty()) {
            editpox = Pox;
            Log.e("testPox", editpox);
        }


        SharedPreferences sf = context.getSharedPreferences("Restaurant", Context.MODE_PRIVATE);

        String departCode = sf.getString("DepartCode", "");

        final String roomNo=sf.getString("tableName","");
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        String URL = "http://" + IpServiceChecker.getIpaddress() + "/" + IpServiceChecker.getAppName() + "/Kanpur_HotelKotApp_Service.svc/ResturantIteams_KOT/RestCode/" + departCode;
        Log.e("url_fetch", URL);
        //RequestQueue requestQueue = Volley.newRequestQueue(context);
        //String URL = "http://192.168.0.117/Hotel_ServiceApp/Kanpur_HotelKotApp_Service.svc/ResturantIteams_KOT/RestCode/LLP004";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {


                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObject = (JSONObject) response.get(i);
                        String str_reb = "";
                        str_reb = jsonObject.optString("errormessage");

                        if (str_reb.equals("Correct")) {
                            String Vdate_from = jsonObject.optString("Date_From");
                            String Docid = jsonObject.optString("Docid");
                            String Sno = jsonObject.optString("Number_Method");
                            String Vprefix = jsonObject.optString("Prefix");
                            String SiteCode = jsonObject.optString("SiteCODE");
                            String Vno = jsonObject.optString("Start_Srl_No");
                            String V_type = jsonObject.optString("V_Type");

                            String VToDate = jsonObject.optString("Date_To");
                            String envirodate = jsonObject.optString("Envirodate");

                            Log.e("docid", Docid);

                            //datasend(Vdate,Docid, Sno,Vprefix, SiteCode, Vno, V_type, VToDate);
                            datasend(Vdate_from, Docid, Sno, Vprefix, SiteCode, Vno, V_type, VToDate, envirodate, editpox,roomNo);

                        } else
                            Toast.makeText(context, str_reb, Toast.LENGTH_SHORT).show();


                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("ErrorInLogin:", "" + e.getMessage());
                    // Toast.makeText(StuAssemblyAttandance.this, "response: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                VolleyLog.d("", "ErrorLoginInfo: " + error.getMessage());
                //Log.d("Volley Error service", error.getMessage());
                //  Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonArrayRequest);
    }


    public void datasend(String Vdate_from, String Docid, String Sno, String Vprefix, String SiteCode, String Vno, String V_type, String VToDate, String envirodate, String poxtable,String roomNo) {
        final int j = 1;
        final MySqliteDatabase db = new MySqliteDatabase(context);
        final Cursor cursor = db.fetchRestaurantTable();

        if (cursor.moveToFirst()) {
            do {

                Log.e("courser count", "" + cursor.getCount());
                final String url = "http://" + IpServiceChecker.getIpaddress() + "/" + IpServiceChecker.getAppName() + "/Kanpur_HotelKotApp_Service.svc/KOT_Details_Post";
                try {
                    RequestQueue queue = Volley.newRequestQueue(context);

                    int sno = cursor.getPosition() + 1;
                    Log.e("Docid", Docid);
                    Log.e("Sno", "" + sno);
                    Log.e("Vprefix", Vprefix);
                    Log.e("SiteCode", SiteCode);
                    Log.e("Vno", Vno);
                    Log.e("V_type", V_type);
                    Log.e("Vdate", envirodate);
                    Log.e("VToDate", VToDate);
                    Log.e("Vdate_from", Vdate_from);
                    Log.e("poxtable", poxtable);


                    Log.e("Snopk", cursor.getString(0));
                    Log.e("RestCode", cursor.getString(1));
                    Log.e("U_Name", cursor.getString(2));
                    Log.e("Waiter", cursor.getString(3));
                    Log.e("Item", cursor.getString(4));
                    Log.e("Qty", cursor.getString(5));
                    Log.e("Rate", cursor.getString(6));
                    Log.e("Amount", cursor.getString(7));
                    Log.e("tableNumber", cursor.getString(8));
                    Log.e("item_desc", cursor.getString(9));


                    JSONObject jsonBody = new JSONObject();
                    jsonBody.put("DocId", Docid);
                    jsonBody.put("Sno", sno);
                    jsonBody.put("Vtype", V_type);
                    jsonBody.put("VNo", Vno);
                    jsonBody.put("Site_Code", SiteCode);
                    jsonBody.put("Vprefix", Vprefix);
                    jsonBody.put("Vdate", envirodate);
                    jsonBody.put("Date_To", VToDate);

                    jsonBody.put("Date_From", Vdate_from);
                    jsonBody.put("NoPer", poxtable);


                    jsonBody.put("RestCode", cursor.getString(1));
//                    jsonBody.put("RoomNo", cursor.getString(8));


                    jsonBody.put("RoomNo",roomNo);
                    jsonBody.put("Item", cursor.getString(4));
                    jsonBody.put("Qty", cursor.getString(5));
                    jsonBody.put("Rate", cursor.getString(6));
                    jsonBody.put("Amount", cursor.getString(7));
                    jsonBody.put("Waiter", cursor.getString(3));
                    jsonBody.put("U_Name", cursor.getString(2));
                    //jsonBody.put("U_EntDt", "2019-02-11");
                    jsonBody.put("U_AE", "A");
                    jsonBody.put("U_Name1", "");
                    //Log.e("docid3",Docid);
                    //jsonBody.put("U_EntDt1", "2017-02-11");
                    jsonBody.put("U_AE1", "");
                    jsonBody.put("LogSite_Code", "2");
                    jsonBody.put("ItemRestCode", cursor.getString(1));
                    jsonBody.put("itemremark",cursor.getString(9));

                    final String requestBody = jsonBody.toString();
                    // Request a string response from the provided URL.
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.e("url_post", url);
                            //Log.e("ResponseCheck:-", "" + response);
                            if (response.equals("200")) {


                                Log.e("respose", response);


                                if (cursor.getCount() == i) {

                                    Log.e("Delete code exc", "" + i);
                                    int retvalue = db.deleteRestaurantTable();
                                    i = 1;
                                    if (retvalue > 0) {
                                        SharedPreferences sf = context.getSharedPreferences("Restaurant", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor ed = sf.edit();
                                        ed.putString("tableName", "");
                                        ed.putString("tableCode", "");
                                        ed.apply();

                                        context.startActivity(new Intent(context, Activity_Pos.class));
                                    }
                                    // Toast.makeText(context, "Table deleted", Toast.LENGTH_SHORT).show();
                                    // Toast.makeText(context, "Data Send to main", Toast.LENGTH_SHORT).show();
                                }
                                i++;
                                Log.e("i value", "" + i);

                            }

                        }

                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            VolleyLog.d("", "Error...." + error);
                        }
                    }) {
                        @Override
                        public String getBodyContentType() {
                            return "application/json; charset=utf-8";
                        }

                        @Override
                        public byte[] getBody() throws AuthFailureError {
                            try {
                                return requestBody == null ? null : requestBody.getBytes("utf-8");
                            } catch (UnsupportedEncodingException uee) {
                                VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                                return null;
                            }
                        }

                        @Override
                        protected Response<String> parseNetworkResponse(NetworkResponse response) {
                            String responseString = "";
                            if (response != null) {
                                responseString = String.valueOf(response.statusCode);
                            }
                            return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                        }
                    };
                    // Add the request to the RequestQueue.
                    queue.add(stringRequest);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } while (cursor.moveToNext());


        }

    }


}