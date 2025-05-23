/*
 * Copyright (c) 2017. http://hiteshsahu.com- All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * If you use or distribute this project then you MUST ADD A COPY OF LICENCE
 * along with the project.
 *  Written by Hitesh Sahu <hiteshkrsahu@Gmail.com>, 2017.
 */

package com.saptrishi.hotelkotapp.view.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;


import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.saptrishi.hotelkotapp.R;
import com.saptrishi.hotelkotapp.domain.api.ProductCategoryLoaderTask;
import com.saptrishi.hotelkotapp.model.MySqliteDatabase;
import com.saptrishi.hotelkotapp.util.IpServiceChecker;
import com.saptrishi.hotelkotapp.util.Utils;
import com.saptrishi.hotelkotapp.util.Utils.AnimationType;
import com.saptrishi.hotelkotapp.view.activities.APrioriResultActivity;
import com.saptrishi.hotelkotapp.view.activities.Activity_Pos;
import com.saptrishi.hotelkotapp.view.activities.ECartHomeActivity;
import com.saptrishi.hotelkotapp.view.activities.ServiceSettingActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HomeFragment extends Fragment {
//    int mutedColor = R.attr.colorPrimary;
    TextView totalAmount;
    String[] table_code = new String[20];
    String[] table_name = new String[20];
    int ln;
    private CollapsingToolbarLayout collapsingToolbar;
    private RecyclerView recyclerView;
    // Toolbar toolbar;
    /**
     * The double back to exit pressed once.
     */
    private boolean doubleBackToExitPressedOnce;
    private final Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            doubleBackToExitPressedOnce = false;
        }
    };
    private Handler mHandler = new Handler();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        final View view = inflater.inflate( R.layout.frag_product_category, container, false );


//        view.findViewById(R.id.search_item).setOnClickListener(
//                new OnClickListener() {
//
//                    @Override
//                    public void onClick(View v) {
//
//                        Utils.switchFragmentWithAnimation(R.id.frag_container,
//                                new SearchProductFragment(),
//                                ((Activity_Pos) getActivity()), null,
//                                AnimationType.SLIDE_UP);
//
//                    }
//                });

        collapsingToolbar = (CollapsingToolbarLayout) view.findViewById( R.id.collapsing_toolbar );
        // toolbar=(Toolbar)view.findViewById(R.id.toolbar) ; ///////////////////////////////////////////////////////////
        Log.e( "CollAppbrly", "" + collapsingToolbar );

        final AppBarLayout appbrly = (AppBarLayout) view.findViewById( R.id.appbar );
        Log.e( "Appbrly", "" + appbrly );


        final SharedPreferences sf = getActivity().getSharedPreferences( "Restaurant", Context.MODE_PRIVATE );

        final String DepartCode = sf.getString( "DepartCode", "" );
        final String restaurant_name = sf.getString( "restaurant_name", "" );

        collapsingToolbar.setTitle( restaurant_name );


        final Toolbar toolbar = (Toolbar) view.findViewById( R.id.anim_toolbar );
        ((Activity_Pos) getActivity()).setSupportActionBar(toolbar);
        ((Activity_Pos) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled( true );

        toolbar.setNavigationIcon( R.drawable.ic_drawer );

        toolbar.setNavigationOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity_Pos) getActivity()).getmDrawerLayout().openDrawer( GravityCompat.START );
                //((Activity_Pos) getActivity()).getmDrawerLayout();

            }
        } );
        //final AppBarLayout.LayoutParams params =(AppBarLayout.LayoutParams) toolbar.getLayoutParams();
        //Log.e("AppBarLayout",""+params);
        final LinearLayout linearLayout = (LinearLayout) view.findViewById( R.id.Tbl_liner );


        RequestQueue queue = Volley.newRequestQueue( getActivity().getApplicationContext() );
        //SharedPreferences sf=getActivity().getSharedPreferences("Restaurant",Context.MODE_PRIVATE);
        //String DepartCode=sf.getString("DepartCode","");
        //final String restaurant_name=sf.getString("restaurant_name","");
        String url = "http://" + IpServiceChecker.getIpaddress() + "/" + IpServiceChecker.getAppName() + "/Kanpur_HotelKotApp_Service.svc/ResturantTableApp/RestCode/" + DepartCode + "";
        Log.e( "urlTable", url );
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest( url, new Response.Listener<JSONArray>() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onResponse(final JSONArray response) {
                try {

                    int j = 0;
                    for (int i = 0; i <= response.length(); i++) {
                        Log.e( "ln value", response.length() + "" );
//                        table_code [i] = jsonObject.getString("Table_Code");
//                        table_name [i]= jsonObject.getString("Table_Name");
                        //Log.e("ln value",jsonObject.length()+"");

                        TableRow row = new TableRow( getActivity() );
                        for (int k = 0; k < 8; k++) {
                            Log.e( "j value", j + "" );
                            final JSONObject jsonObject = (JSONObject) response.get( j );
                            final Button myButton = new Button( getActivity() );
                            TableRow.LayoutParams tr = new TableRow.LayoutParams( LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT );
                            tr.weight = 0;
                            myButton.setLayoutParams( tr );
                            //myButton.setText();
                            myButton.setText( jsonObject.getString( "Table_Name" ) );
                            myButton.setTag( jsonObject.getString( "Table_Code" ) );
                            Log.e( "Table Code", jsonObject.getString( "Table_Code" ) );
                            Log.e( "WaiterName", jsonObject.getString( "WAITERNAME" ) );

                            String name = sf.getString( "tableName", "" );
                            Log.e( "tbname", name );
//                            if(jsonObject.getString("WAITERNAME").equals("NA"))
                            if (jsonObject.getString( "CONTRADOCID" ).equals( "" )) {
                                myButton.setBackgroundResource( R.drawable.occupied_button_shape );

                            } else {
                                myButton.setBackgroundResource( R.drawable.capsuleshape );
                            }
                            if (name.equals( jsonObject.getString( "Table_Name" ) )) {
                                myButton.setBackgroundResource( R.drawable.currentselected );
                                collapsingToolbar.setTitle( restaurant_name + " Table NO " + "" + name );
                            }


                            myButton.setTextSize( 15 );
                            myButton.setGravity( Gravity.CENTER );
                            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) myButton.getLayoutParams();
                            params.leftMargin = 10;
                            params.topMargin = 10;
                            params.rightMargin = 10;
                            params.width = 130;
                            params.height = 70;
                            myButton.setLayoutParams( params );
                            myButton.setOnClickListener( new OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    appbrly.setExpanded( false );
                                    myButton.setBackgroundResource( R.drawable.currentselected );

                                    final MySqliteDatabase mySqliteDatabase = new MySqliteDatabase( getActivity() );
                                    if (mySqliteDatabase.fetchkotListTable().moveToFirst())
                                        mySqliteDatabase.deletekotListTable();
                                    final SharedPreferences sf = getActivity().getSharedPreferences( "Restaurant", Context.MODE_PRIVATE );
                                    final SharedPreferences.Editor ed = sf.edit();
                                    Log.e( "table date", myButton.getTag() + "" );
                                    ed.putString( "tableCode", myButton.getTag() + "" );
                                    ed.putString( "tableName", ((Button) view).getText() + "" );
                                    ed.apply();

                                    Log.e( "butname", "" + ((Button) view).getText() );
                                    collapsingToolbar.setTitle( restaurant_name + " Table NO " + "" + ((Button) view).getText() );//Button Clicked here Red converted
                                    //view.setBackgroundResource(R.drawable.occupied_button_shape);
                                    String kotURL = "http://" + IpServiceChecker.getIpaddress() + "/" + IpServiceChecker.getAppName() + "/Kanpur_HotelKotApp_Service.svc/KotShow/RestCode/" + DepartCode + "/tblno/" + myButton.getText() + "";
                                    Log.e( "urlKotList", kotURL );
                                       String tablename;
                                       tablename =  myButton.getText().toString();
                                    Log.e("onClick: ", tablename);
                                    RequestQueue requestQueue = Volley.newRequestQueue( getActivity() );
                                    JsonArrayRequest jsonArrayRequest1 = new JsonArrayRequest( kotURL, new Response.Listener<JSONArray>() {
                                        @Override
                                        public void onResponse(JSONArray jsonArray) {
                                            try {
                                                int amount = 0;
                                                Log.e( "response", "" + jsonArray );
                                                for (int i = 0; i < jsonArray.length(); i++) {
                                                    JSONObject jsonObject = (JSONObject) jsonArray.get( i );
                                                    if (jsonObject.get( "errormessage" ).equals( "Correct" )) {
                                                        String Amount = (String) jsonObject.get( "Amount" );
                                                        String ITEMNAME = (String) jsonObject.get( "ITEMNAME" );
                                                        String Qty = (String) jsonObject.get( "Qty" );
                                                        String Rate = (String) jsonObject.get( "Rate" );
                                                        String Unit = (String) jsonObject.get( "Unit" );
                                                        mySqliteDatabase.insertkotListTable( ITEMNAME, Amount, Qty, Rate, Unit );
                                                        amount = amount + Integer.parseInt( (String) jsonObject.get( "Amount" ) );
                                                        ed.putString( "amount", amount + "" );
                                                        ed.apply();
                                                    }
//                                             else
//                                             {
//                                                 Toast.makeText(getActivity(), "Table is not occupied", Toast.LENGTH_SHORT).show();
//                                             }


                                                }


                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError volleyError) {

                                        }
                                    } );
                                    requestQueue.add( jsonArrayRequest1 );

                                }
                            } );
                            row.addView( myButton );
                            j++;
                            if (response.length() == j) {
                                break;
                            }
                        }

                        linearLayout.addView( row );
//                        Log.e("tblcode_test1",jsonObject.getString("Table_Code"));
//                        Log.e("tblname_test1",jsonObject.getString("Table_Name"));

                    }


                } catch (JSONException e) {
                    e.printStackTrace();

                }

            }
        }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText( getContext(), volleyError + "", Toast.LENGTH_SHORT ).show();

            }
        } );

        queue.add( jsonArrayRequest );

//        log.e("checking1",table_code[0]+" tablecode");
//        log.e("checking1",table_name[0]+"table name");

//        for(int j=1;j<=ln;j++) {
//            Log.e("ButID","check");
//            TableRow row = new TableRow(getActivity());
//            for (int k = 1; k <= ln; k++) {
//
//                Button myButton = new Button(getActivity());
//                TableRow.LayoutParams tr = new TableRow.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
//                tr.weight = 0;
//                myButton.setLayoutParams(tr);
//                //myButton.setBackground(R.drawable.m1);
//                //myButton.setOnClickListener(this);
//                myButton.setText(table_name[k-1]); //yaha table name aayega and table id s bhi khelna h
////                myButton.setId(Integer.parseInt(table_code[k-1]));
//
//                log.e("checking",table_code[k-1]+" tablecode");
//                log.e("checking",table_name[k-1]+"table name");
//
//                myButton.setBackgroundResource(R.drawable.capsuleshape);
//                myButton.setTextSize(18);
//                myButton.setGravity(Gravity.CENTER);
//                 //myButton.setHeight(5);
//                // myButton.setWidth(5);
//
//                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) myButton.getLayoutParams();
//                params.leftMargin = 10; params.topMargin = 10;params.rightMargin=10; params.width=180;params.height=100;
//                myButton.setLayoutParams(params);
//                //myButton.setPadding(30,20,10,0);
//                //myButton.setPadding(30,20,30,20);
//                //linearLayout.addView(myButton);
//                row.addView(myButton);
//                //myButton.setTextSize(20);
//                // myButton.setHeight(150);
//                // myButton.setWidth(150);
//                //myButton.setPadding(30,20,10,0);
//
//
//
//                final int id_ = myButton.getId();
//                Log.e("ButID",""+id_);
//                //myButton = (Button) view.findViewById(id_);
//                //Log.e("mmyButID",""+myButton);
//
//                myButton.setOnClickListener(new View.OnClickListener() {
//
//                    public void onClick(View view) {
//
//
//                        appbrly.setExpanded(false);
//                        //toolbar.setTitle("Hotel KOT Shop Table NO "+""+((Button) view).getText());
//                        //toolbar.setTitle("Hotel Shop ");
//                        collapsingToolbar.setTitle("Hotel KOT Shop Table NO "+""+((Button) view).getText());//Button Clicked here Red converted
//                        view.setBackgroundResource(R.drawable.occupied_button_shape);
//
//
//                        //Log.e("butid",""+view.getId());
//                        Log.e("butname",""+((Button) view).getText());
//                        //final android.support.design.widget.CollapsingToolbarLayout.LayoutParams params =(android.support.design.widget.CollapsingToolbarLayout.LayoutParams) toolbar.getLayoutParams();
//                        //Log.e("AppBarLayout",""+params);
//                        //AppBarLayout.LayoutParams p = (AppBarLayout.LayoutParams) collapsingToolbar.getLayoutParams();
//                        //android.support.design.widget.CollapsingToolbarLayout.LayoutParams params =(android.support.design.widget.CollapsingToolbarLayout.LayoutParams) toolbar.getLayoutParams();
//                       //p.height=500;
//
//                        //p.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL | AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED);
//                        //collapsingToolbar.setLayoutParams(params);
//                        //toolbar.animate().translationY(0).setInterpolator(new LinearInterpolator()).setDuration(500);
//                        //appbrly.animate().translationY(-50).setInterpolator(new LinearInterpolator()).setDuration(500);
////                        CollapsingToolbarLayout.LayoutParams appBarLayoutParams = (CollapsingToolbarLayout.LayoutParams) appbrly.getLayoutParams();
//                       // params.setScrollFlags(0);
////                        appBarLayoutParams.setBehavior(null);
////                        appbrly.setLayoutParams(appBarLayoutParams);
//                        //Collappbrly.generateLayoutParams().setScrollFlags(0);
//                        //params.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL | AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS);
//
//                        //Collappbrly.setTitle("Title");
//                        //appbrly.animate().translationY(0).setInterpolator(new LinearInterpolator()).setDuration(500);
//                       // Collappbrly.animate().translationY(0).setInterpolator(new LinearInterpolator()).setDuration(500);
//                       // appbrly.onViewRemoved(AnimationType.SLIDE_UP);
//
////                        AppBarLayout appbrly=(AppBarLayout)view.findViewById(R.id.appbar);layout_height
//
//                       //appbrly.setMinimumHeight(40);
//                        //Toast.makeText(view.getContext(),"Table Button clicked index = " + id_, Toast.LENGTH_SHORT).show();
//
//                        // int id = view.getId();
//                        //Utils.switchFragmentWithAnimation(R.id.frag_container, new AllFragment(), ((Activity_Pos) getActivity()), null, AnimationType.SLIDE_UP);
//                        // Utils.switchContent(R.id.frag_container, null, getActivity(), AnimationType.SLIDE_UP);
//
//                        //toolbar.
//
//
//                    }
//                });
//
//            }
//            linearLayout.addView(row);
//        }


        //collapsingToolbar = (CollapsingToolbarLayout) view.findViewById(R.id.collapsing_toolbar);

        //collapsingToolbar.setTitle("Categories");

        //ImageView header = (ImageView) view.findViewById(R.id.header);

//       /*Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.header);
//
//        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
//            @SuppressWarnings("ResourceType")
//            @Override
//            public void onGenerated(Palette palette) {
//
//                mutedColor = palette.getMutedColor(R.color.primary_500);
//                collapsingToolbar.setContentScrimColor(mutedColor);
//                collapsingToolbar.setStatusBarScrimColor(R.color.black_trans80);
//            }
//        });*/
//
//        /*recyclerView = (RecyclerView) view.findViewById(R.id.scrollableview);
//
//        recyclerView.setHasFixedSize(true);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(
//                getActivity());
//        recyclerView.setLayoutManager(linearLayoutManager);*/
//
//        // new ProductCategoryLoaderTask(recyclerView, getActivity()).execute();
//
////
////		if (simpleRecyclerAdapter == null) {
////			simpleRecyclerAdapter = new CategoryListAdapter(getActivity());
////			recyclerView.setAdapter(simpleRecyclerAdapter);
////
////			simpleRecyclerAdapter
////					.SetOnItemClickListener(new OnItemClickListener() {
////
////						@Override
////						public void onItemClick(View view, int position) {
////
////							if (position == 0) {
////								CenterRepository.getCenterRepository()
////										.getAllElectronics();
////							} else if (position == 1) {
////								CenterRepository.getCenterRepository()
////										.getAllFurnitures();
////							}
////							Utils.switchFragmentWithAnimation(
////									R.id.frag_container,
////									new ProductOverviewFragment(),
////									((ECartHomeActivity) getActivity()), null,
////									AnimationType.SLIDE_LEFT);
////
////						}
////					});
////		}

        view.setFocusableInTouchMode( true );
        view.requestFocus();
        view.setOnKeyListener( new View.OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP
                        && keyCode == KeyEvent.KEYCODE_BACK) {

                    if (doubleBackToExitPressedOnce) {
                        // super.onBackPressed();

                        if (mHandler != null) {
                            mHandler.removeCallbacks( mRunnable );
                        }

                        getActivity().finish();

                        return true;
                    }

                    doubleBackToExitPressedOnce = true;
                    Toast.makeText( getActivity(),
                            "Please click BACK again to exit",
                            Toast.LENGTH_SHORT ).show();

                    mHandler.postDelayed( mRunnable, 1000 );

                }
                return true;
            }
        } );

        return view;

    }

//    @Override
//    public void onClick(View v) {
//
//        //appbrly.animate().translationY(-appbrly.getHeight()).setInterpolator(new LinearInterpolator()).setDuration(500);
//        Toast.makeText(v.getContext(),"Table Button clicked index = " + v.getId(), Toast.LENGTH_SHORT).show();
//    }


}
