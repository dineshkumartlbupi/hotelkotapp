package com.saptrishi.hotelkotapp.view.activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.saptrishi.hotelkotapp.R;
import com.saptrishi.hotelkotapp.util.IpServiceChecker;
import com.saptrishi.hotelkotapp.view.fragment.MyDialogFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static com.android.volley.toolbox.Volley.newRequestQueue;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentTransaction;

public class RestaurantListActivity extends AppCompatActivity {




    Intent I;
    RequestQueue rq;
    String url;
    String DepartCode;
    DialogFragment dialogFragment;
    FragmentTransaction ft;

    //    String [] from={"name","course","fee"};
//    int [] to={R.id.tlname,R.id.tlcourse,R.id.tlfee};
    int[] to = {R.id.res_name, R.id.departcode};
    String[] from = {"DepartName", "DepartCode"};

    ArrayList<HashMap<String, String>> al = new ArrayList<HashMap<String, String>>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_list);


        final ProgressDialog pDialog = new ProgressDialog( this );

        pDialog.setTitle( "Uploading..." );
        pDialog.setMessage( "Uploading your leave application" );
        pDialog.setCancelable( false );
        pDialog.setCanceledOnTouchOutside( false );
        pDialog.show();

        ListView lv = (ListView) findViewById(R.id.lv);

        final SimpleAdapter sa = new SimpleAdapter(RestaurantListActivity.this, al, R.layout.restuarant_list, from, to);
        lv.setAdapter(sa);

        I = getIntent();
        SharedPreferences sf=getSharedPreferences("Restaurant",Context.MODE_PRIVATE);

        String uname = sf.getString("loginId","");
        String ip=sf.getString("Ip","");
        String appname=sf.getString("Appname","");
        new IpServiceChecker(ip,appname);

        url = "http://"+IpServiceChecker.getIpaddress()+"/"+IpServiceChecker.getAppName()+"/Kanpur_HotelKotApp_Service.svc/ResturantApp/logid/" + uname + "";

        Log.e("crash_check","http://"+IpServiceChecker.getIpaddress()+"/"+IpServiceChecker.getAppName()+"/Kanpur_HotelKotApp_Service.svc/ResturantApp/logid/" + uname + "");
        rq = newRequestQueue(getApplicationContext());

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    pDialog.dismiss();
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObject = (JSONObject) response.get(i);

                         DepartCode = jsonObject.getString("DepartCode");
                         String DepartName = jsonObject.getString("DepartName");
//                        String errormessage=jsonObject.getString("errormessage");
//                        String menuHelpParam_Str=jsonObject.getString("menuHelpParam_Str");
//                        String errormsg=jsonObject.getString("errormessage");
                        HashMap<String, String> hm1 = new HashMap<String, String>();
                        hm1.put("DepartName", DepartName);
                        hm1.put("DepartCode", DepartCode);
                        al.add(hm1);

//                        if(true)
//                        {
//
//                        }
//
//                        else
//                        {
//                            Toast.makeText(RestaurantListActivity.this, errormsg, Toast.LENGTH_SHORT).show();
//
//
//                        }

                    }
                    sa.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("ErrorInLogin:", "" + e.getMessage());
                    // Toast.makeText(StuAssemblyAttandance.this, "response: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

                pDialog.dismiss();
              //  Toast.makeText(RestaurantListActivity.this, volleyError + "", Toast.LENGTH_SHORT).show();


            }
        });

        rq.add(jsonArrayRequest);


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                String psui=al.get(position).get("DepartCode").toString();
                String restaurant_name=al.get(position).get("DepartName");
//                Toast.makeText(RestaurantListActivity.this, psui+"", Toast.LENGTH_SHORT).show();
                Log.e("departcode",psui);
                SharedPreferences sf=getSharedPreferences("Restaurant",Context.MODE_PRIVATE);
                SharedPreferences.Editor ed=sf.edit();
                ed.putString("DepartCode",psui);
                ed.putString("restaurant_name",restaurant_name);
                ed.putString("ActivityName","RestaurantList");
                ed.apply();

                dialogFragment = new MyDialogFragment();

                Bundle bundle = new Bundle();
                bundle.putBoolean("notAlertDialog", true);

                dialogFragment.setArguments(bundle);

                ft = getSupportFragmentManager().beginTransaction();
                ft.addToBackStack(null);
                dialogFragment.show(ft, "dialog");


            }
        });

    }
//    boolean doubleBackToExitPressedOnce = false;
//    int count=0;
//    @Override
//    public void onBackPressed() {
//        if (doubleBackToExitPressedOnce) {
//            if(count>1)
//            {
//                startActivity(new Intent(this,LoginActivity.class));
//                finish();
//            }
//            count++;
//            super.onBackPressed();
//            return;
//        }
//
//        this.doubleBackToExitPressedOnce = true;
//        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
//
//        new Handler().postDelayed(new Runnable() {
//
//            @Override
//            public void run() {
//                doubleBackToExitPressedOnce=false;
//            }
//        }, 2000);
//    }
    @Override
    protected void onRestart() {
//        SharedPreferences sf=getSharedPreferences("Restaurant",Context.MODE_PRIVATE);
//        String loginId=sf.getString("loginId","");
//        if(loginId.equals(""))
            finish();
        super.onRestart();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this,LoginActivity.class));
        finish();
    }

}

