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

    private void getData(String DepartCode,String Table) {
        // creating a new variable for our request queue
        RequestQueue queue = Volley.newRequestQueue(itemlistviewtable.this);
        // in this case the data we are getting is in the form
        // of array so we are making a json array request.
        // below is the line where we are making an json array
        // request and then extracting data from each json object.

       String url = "http://"+IpServiceChecker.getIpaddress()+"/"+IpServiceChecker.getAppName()+"/Kanpur_HotelKotApp_Service.svc/KotitemShow/RestCode/" +DepartCode+"/tblno/"+Table;
        Log.e( "aaaaaaaaaaaaaa: ",url );

       // String url = "http://192.168.0.123/Hotel_ServiceApp/Kanpur_HotelKotApp_Service.svc/KotitemShow/RestCode/" +DepartCode+"/tblno/"+Table;
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(JSONArray response) {
                courseRV.setVisibility(View.VISIBLE);

                for (int i = 0; i < response.length(); i++) {
                    // creating a new json object and
                    // getting each object from our json array.
                    try {
                        // we are getting each json object.
                        JSONObject responseObj = response.getJSONObject(i);
                        if( responseObj.getString("Amount")== "null"){
                            Toast.makeText(itemlistviewtable.this, "No Item On This Table!! ", Toast.LENGTH_SHORT).show();
                            break;
                        }
                        // now we get our response from API in json object format.
                        // in below line we are extracting a string with
                        // its key value from our json object.
                        // similarly we are extracting all the strings from our json object.
                        String amount = responseObj.getString("Amount");

                        String  ITEMNAME = responseObj.getString("ITEMNAME");
                        String qty = responseObj.getString("Qty");
                        String rate = responseObj.getString("Rate");
                        String ItemDescription = responseObj.getString( "Description" );
                        courseModalArrayList.add(new productlistview(amount, ITEMNAME, qty, rate,ItemDescription));
                        buildRecyclerView();


                        String str = qty; // Replace this with your input string
                        int intqtyy = Integer.parseInt(str);

                        String st = amount;
                        int intamount = Integer.parseInt( st );
                      //  double dv = Double.parseDouble(st);



                        Quantity = Quantity+intqtyy;
                        Amount = Amount+intamount;
                       // Amount = Amount+intamount*intqtyy;

                     //   Toast.makeText( itemlistviewtable.this, ""+Quantity, Toast.LENGTH_SHORT ).show();

                        tv_amount.setText("Total Amount: " + Amount +" Rs ");
                        tv_quantity.setText("Total Quantity: " + Quantity);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(itemlistviewtable.this, "No Item On This Table!! ", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(jsonArrayRequest);
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