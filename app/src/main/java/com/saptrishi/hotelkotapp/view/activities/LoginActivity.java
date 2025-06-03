package com.saptrishi.hotelkotapp.view.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.saptrishi.hotelkotapp.*;
import com.saptrishi.hotelkotapp.util.IpServiceChecker;
import com.saptrishi.hotelkotapp.view.IP_Registration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class LoginActivity extends AppCompatActivity {

        Button button;
//    TextView tv_error;
    String url;
    EditText etName,etPassword;
    boolean checkser=false;
    static int chk=0;
    //RequestQueue requestQueue;

//    ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.user_login );

//        constraintLayout=(ConstraintLayout)findViewById(R.id.conslayout);

        //   Drawable icon=this.getResources().getDrawable(R.drawable.loginimage);
        //     myButton.setCompoundDrawablesWithIntrinsicBounds(icon,null,null,null);
//            constraintLayout.setBackgroundResource(R.drawable.loginimage);

        button = (Button) findViewById( R.id.button );
//        tv_error = (TextView) findViewById( R.id.tv_error );
        etName = (EditText) findViewById( R.id.et_userid );
        etPassword = (EditText) findViewById( R.id.et_password );
        etName.setText( "" );
        etPassword.setText( "" );
        //pDialog =  new SpotsDialog(this,R.style.Custom);

//        Context context=this;
//        Log.e("context",context+"");
//        Button button;
//        button=(Button)findViewById(R.id.btn_login);
//        button.setBackgroundResource(R.drawable.capsuleshape);

//        Button button;
//        button=(Button)findViewById(R.id.btn_login);
//        button.setBackgroundResource(R.drawable.capsuleshape);


    }
    public void submitit(View view) throws ParseException {
        Toast.makeText( this, "abced", Toast.LENGTH_SHORT ).show();
        SharedPreferences sharedPreferences=getSharedPreferences("Restaurant",Context.MODE_PRIVATE);
        String expiryDate=sharedPreferences.getString("ExpiryDate","");

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar c = Calendar.getInstance();
        String currentDate = sdf.format(c.getTime());

        Log.e("date1",expiryDate);
        Log.e("date2",currentDate);
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");


//        try {
//            Date currDate=new SimpleDateFormat("dd/MM/yyyy").parse(currentDate);
//            Date expDate=new SimpleDateFormat("dd/MM/yyyy").parse(expiryDate);
//
//            log.e("c1",currDate+"");
//            log.e("c1",expDate+"");
//            if(currDate.before(expDate))
//            {
//
//
//            }
//            else
//            {
//                Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
//            }
//        } catch (ParseException e) {
//
//            Log.e("hello","hello11111111");
//            e.printStackTrace();
//        }

        Date current=new SimpleDateFormat("dd/MM/yyyy").parse(currentDate);
        Date exp=new SimpleDateFormat("dd/MM/yyyy").parse(expiryDate);
        Log.e("date21",current+"");
        Log.e("date21",exp+"");

//        Log.e("chk",date1+"");
//
//        try {
//             currDate = formatter.parse(currentDate);
//        } catch (ParseException e) {
//            e.printStackTrace();
//            currDate=null;
//        }
//
//        try {
//             expDate=formatter.parse(expiryDate);
//        } catch (ParseException e) {
//            e.printStackTrace();
//            expDate=null;
//        }

//        Log.e("date2",currDate+"");
//        Log.e("date3",expDate+"");
//
//            if(currDate.before(expDate)) {
                 Log.e("current date",current+"");
                 Log.e("expiryDate",exp+"");
               if(current.before(exp))
               {
                   if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
                       if (etName.getText().toString().trim().isEmpty()) {
                           etName.setError("enter user name");
                           etName.requestFocus();
                       }
                       else {
                           if (etPassword.getText().toString().trim().isEmpty()) {
                               etPassword.setError("enter password");
                               etPassword.requestFocus();
                           } else {

                               final String userid = etName.getText().toString().trim();
                               final String password = etPassword.getText().toString().trim();

       //                        SharedPreferences s=getSharedPreferences("Restaurant",Context.MODE_PRIVATE);
       //                        SharedPreferences.Editor ed=s.edit();


                              getLoginInfo(userid, password);

                               Log.e("chk1",chk+"");

                           }
                       }
                   }
               }
            else
            {
                Toast.makeText(this, "Your License Expired..........", Toast.LENGTH_SHORT).show();
                Intent i=new Intent(this, IP_Registration.class);
                startActivity(i);
                finish();
            }
        }



    public void forgotpassword(View v){
        Toast.makeText(this, "Opps you forgot your Password", Toast.LENGTH_SHORT).show();
    }


    private void getLoginInfo(final String loginId , final String loginPass) {
        SharedPreferences sf=getSharedPreferences("Restaurant",Context.MODE_PRIVATE);

        String Appname=sf.getString("Appname","");
        String AppIp=sf.getString("Ip","");

        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Validate your credentials");
        progressDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        url="http://"+AppIp+"/"+Appname+"/Kanpur_HotelKotApp_Service.svc/LoginApp_Post/logid/"+loginId+"/passid/"+loginPass+"";
        Log.e("loginurl11",url);
        Toast.makeText( this, "abcdef", Toast.LENGTH_SHORT ).show();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {

                    Log.e("check",""+response);

                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObject = (JSONObject) response.get(i);
                        String uname = jsonObject.getString("uname");
                        String ushname = jsonObject.getString("UshortName");
                        String errormsg=jsonObject.getString("errormessage");


                        if(errormsg.equals("Correct"))
                        {

//                            CenterRepository.getCenterRepository().setListOfProductsInShoppingList(
//                                    new TinyDB(getApplicationContext()).getListObject(
//                                            PreferenceHelper.MY_CART_LIST_LOCAL, Product.class));
//
//                            if(!CenterRepository.getCenterRepository().getListOfProductsInShoppingList().isEmpty())
//                            {
//                                Log.e("listnotempty",CenterRepository.getCenterRepository().getListOfProductsInShoppingList().size()+"");
//                                //CenterRepository.getCenterRepository().getListOfProductsInShoppingList().clear();
//                                        new TinyDB(getApplicationContext()).clear();
//
//
//                            }
//                            else{
//                                Log.e("listempty",CenterRepository.getCenterRepository().getListOfProductsInShoppingList().size()+"");
//
//                            }
//

                            SharedPreferences sf=getSharedPreferences("Restaurant",Context.MODE_PRIVATE);
                            SharedPreferences.Editor ed=sf.edit();

//
                            chk++;

                            Log.e("chk",chk+"");
//                            ed.putString("amount","00");
                            ed.putString("tableName", "");
                            ed.putString("tableCode", "");
                            ed.putString("loginId",uname);
                            ed.putString("UshortName",ushname);
//                            ed.putString("tableName","");
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
                                ed.apply();
                            }
                            progressDialog.dismiss();
                            Intent intent = new Intent(LoginActivity.this, RestaurantListActivity.class);

                            startActivity(intent);

                            checkser=true;
//                            Intent intent=new Intent(LoginActivity.this,RestaurantListActivity.class);
//                            intent.putExtra("uname",uname);
//                            intent.putExtra("ushname",ushname);
//                            startActivity(intent);
//                            finish();
                        }
                        else{
                            progressDialog.dismiss();
//                            tv_error.setVisibility(View.VISIBLE);
//                            tv_error.setText("Please check your credentials!");
                            etName.requestFocus();
                        }

                        Log.e("username:",uname);
                        Log.e("userShortname:",ushname);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
//                    tv_error.setVisibility(View.VISIBLE);
//                    tv_error.setText(""+e.getMessage());
                    Log.e("ErrorInLogin:",""+e.getMessage());
                    // Toast.makeText(StuAssemblyAttandance.this, "response: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
//                tv_error.setVisibility(View.VISIBLE);
//                tv_error.setText("ErrorLoginInfo : "+error.getMessage());
                VolleyLog.d("", "ErrorLoginInfo: " + error.getMessage());
                //Log.d("Volley Error service", error.getMessage());
                //  Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonArrayRequest);

    }


    boolean doubleBackToExitPressedOnce = false;

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
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }
}
//http://122.180.145.57/hotel_serviceapp/Kanpur_HotelKotApp_Service.svc/checkservice