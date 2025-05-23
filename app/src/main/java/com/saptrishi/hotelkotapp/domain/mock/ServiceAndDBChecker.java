package com.saptrishi.hotelkotapp.domain.mock;

import android.content.Context;
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
        //http://192.168.123.2/RAJRASOI/Kanpur_HotelKotApp_Service.svc/checkservice
        String url = "http://192.168.1.135/Hotel_ServiceApp/Kanpur_HotelKotApp_Service.svc/checkservice";//"http://192.168.9.41/Hotel_ServiceApp/Kanpur_HotelKotApp_Service.svc/checkservice";
        //String url ="http://192.168.123.2/RAJRASOI/Kanpur_HotelKotApp_Service.svc/checkservice";//"http://192.168.0.117/Hotel_ServiceApp/Kanpur_HotelKotApp_Service.svc/CheckService";
//http://192.168.0.13/  //http://192.168.123.6//http://192.168.0.171/Nirmal/Kanpur_HotelKotApp_Service.svc/checkservice
//http://192.168.0.7/Hotel_SeviceApp/Kanpur_HotelKotApp_Service.svc/checkservice
//http://192.168.1.73/Hotel_ServiceApp/Kanpur_HotelKotApp_Service.svc/checkservice// kanpur(ashish)
        //http://192.168.0.68/hotel_seviceapp/Kanpur_HotelKotApp_Service.svc/checkservice
        //http://192.168.29.23/Hotel_SeviceApp/Kanpur_HotelKotApp_Service.svc/Hotel_DeviceReg_Post/HotelName/
//        http://192.168.9.41/Hotel_ServiceApp/Kanpur_HotelKotApp_Service.svc/checkservice
   // String url = "http://192.168.30.51/Hotel_ServiceApp/Kanpur_HotelKotApp_Service.svc/checkservice";//"http://192.168.9.41/Hotel_ServiceApp/Kanpur_HotelKotApp_Service.svc/checkservice";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
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
