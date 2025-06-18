package com.saptrishi.hotelkotapp.view.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.saptrishi.hotelkotapp.R;
import com.saptrishi.hotelkotapp.model.entities.productlistview;
import com.saptrishi.hotelkotapp.util.IpServiceChecker;
import com.saptrishi.hotelkotapp.view.adapter.Itemlistviewtable_Adapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class itemlistviewtable  extends AppCompatActivity {
    int Quantity = 0;
    Double Amount = (double) 0;
   TextView tv_amount;
    TextView tv_quantity;
    private RecyclerView courseRV;
    TextView tablenumbers;
    // variable for our adapter
    // Fclass and array list
    private Itemlistviewtable_Adapter adapter;
    private ArrayList<productlistview> courseModalArrayList;




    // below line is the variable for url from
    // where we will be querying our data.
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_productviewtable);

        tv_amount= (TextView) findViewById( R.id.total_amount);
       tv_quantity= (TextView) findViewById( R.id.total_qty);

        final SharedPreferences sf = getSharedPreferences( "Restaurant", Context.MODE_PRIVATE );
        final String DepartCode = sf.getString( "DepartCode", "" );
        final String restaurant_name = sf.getString( "restaurant_name", "" );
        final String Table = sf.getString( "tableCode", "" );
        Log.e( "onCreate: ",DepartCode );
        Log.e( "onCreate: ",restaurant_name );
        Log.e( "onCreate: ",Table );
        // initializing our variables.

        courseRV = (RecyclerView) findViewById(R.id.productlist);
      //  progressBar = findViewById(R.id.idPB);
        tablenumbers = (TextView) findViewById(R.id.tablenumber);
        tablenumbers.setText(Table);


        // below line we are creating a new array list
        courseModalArrayList = new ArrayList<>();
        getData(DepartCode,Table);

        // calling method to
        // build recycler view.
      //  buildRecyclerView();
    }

    private void getData(String DepartCode, String Table) {
        RequestQueue queue = Volley.newRequestQueue(itemlistviewtable.this);

        String url = "http://" + IpServiceChecker.getIpaddress() + "/" + IpServiceChecker.getAppName()
                + "/Kanpur_HotelKotApp_Service.svc/ActiveKotList/RoomNo/" + Table + "/restcode/" + DepartCode;
        Log.e("API_URL", url);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray kotList = response.getJSONArray("KotList");

                            if (kotList.length() == 0) {
                                Toast.makeText(itemlistviewtable.this, "No Items On This Table!", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            for (int i = 0; i < kotList.length(); i++) {
                                JSONObject obj = kotList.getJSONObject(i);

                                if (obj.isNull("Amount")) continue;

                                String amountStr = obj.getString("Amount");
                                String qtyStr = obj.getString("Qty");
                                String rateStr = obj.getString("Rate");
                                String itemName = obj.getString("Item");
                                String itemDesc = obj.optString("Description", "");

                                int qty = Integer.parseInt(qtyStr);
                                int amount = Integer.parseInt(amountStr);

                                Quantity += qty;
                                Amount += amount;

                                courseModalArrayList.add(new productlistview(amountStr, itemName, qtyStr, rateStr, itemDesc));
                            }

                            tv_amount.setText("Total Amount: " + Amount + " Rs");
                            tv_quantity.setText("Total Quantity: " + Quantity);

                            buildRecyclerView();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(itemlistviewtable.this, "Parsing error!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(itemlistviewtable.this, "API Error!", Toast.LENGTH_SHORT).show();
                Log.e("VolleyError", error.toString());
            }
        });

        queue.add(jsonObjectRequest);
    }
 private void buildRecyclerView() {

        // initializing our adapter class.
        adapter = new Itemlistviewtable_Adapter(itemlistviewtable.this, courseModalArrayList);
        // adding layout manager
        // to our recycler view.
        LinearLayoutManager manager = new LinearLayoutManager(this);
        courseRV.setHasFixedSize(true);
        // setting layout manager
        // to our recycler view.
        courseRV.setLayoutManager(manager);
        // setting adapter to
        // our recycler view.
        courseRV.setAdapter(adapter);
    }
}