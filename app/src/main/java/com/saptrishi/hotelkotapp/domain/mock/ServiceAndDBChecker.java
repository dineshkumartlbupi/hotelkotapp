package com.saptrishi.hotelkotapp.domain.mock;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.saptrishi.hotelkotapp.view.activities.Device_Registration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ServiceAndDBChecker {

    public static int chk;
    Context context;

    public ServiceAndDBChecker(Context context) {
        this.context = context;
    }

    public void serviceConnectivityChecker()
    {
        RequestQueue rq = Volley.newRequestQueue(context);
        SharedPreferences prefs = context.getSharedPreferences("Config", MODE_PRIVATE);
        String ip = prefs.getString("ip", "");
        String port = prefs.getString("port", "");
        String projectPath = prefs.getString("projectPath", "");
        String servicePath = prefs.getString("servicePath", "");
        if (ip.isEmpty() || projectPath.isEmpty() || servicePath.isEmpty()) {
            Toast.makeText(context.getApplicationContext(), "Please configure all settings first", Toast.LENGTH_SHORT).show();
        }
        // Construct base URL
        String DYNAMIC_URL = "http://"+ip+"/"+port+"/"+projectPath + "/checkservice";
        Log.e( "DYNAMIC_URL : ", ""+ DYNAMIC_URL );
        String STATIC_URL ="http://192.168.0.142/Hotel_ServiceApp/Kanpur_HotelKotApp_Service.svc/checkservice";
        Log.e( "STATIC_URL : ", ""+ STATIC_URL );



        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(DYNAMIC_URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {


                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObject = (JSONObject) response.get(i);
                        String str_reb = "";
                        str_reb = jsonObject.optString("errormessage");
                        if (str_reb.equals("Service And DB IS Connected")) {
                            chk=1;

                        }
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("ErrorInLogin:", "" + e.getMessage());
                    Toast.makeText(context, "check your network.....", Toast.LENGTH_SHORT).show();
                    // Toast.makeText(StuAssemblyAttandance.this, "response: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                VolleyLog.d("", "ErrorLoginInfo: " + error.getMessage());
                //Log.d("Volley Error service", error.getMessage());
                //  Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                Toast.makeText(context, "check your network.....", Toast.LENGTH_SHORT).show();
            }
        });
        rq.add(jsonArrayRequest);
    }
}
