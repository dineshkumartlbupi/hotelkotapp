package com.saptrishi.hotelkotapp.view.activities;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;
import com.saptrishi.hotelkotapp.R;
import com.saptrishi.hotelkotapp.domain.helper.Connectivity;
import com.saptrishi.hotelkotapp.domain.mining.AprioriFrequentItemsetGenerator;
import com.saptrishi.hotelkotapp.domain.mining.FrequentItemsetData;
import com.saptrishi.hotelkotapp.domain.mock.FakeWebServer;
import com.saptrishi.hotelkotapp.domain.mock.TableSenderToService;
import com.saptrishi.hotelkotapp.model.CenterRepository;
import com.saptrishi.hotelkotapp.model.entities.Money;
import com.saptrishi.hotelkotapp.model.entities.Product;
import com.saptrishi.hotelkotapp.util.IpServiceChecker;
import com.saptrishi.hotelkotapp.util.PreferenceHelper;
import com.saptrishi.hotelkotapp.util.TinyDB;
import com.saptrishi.hotelkotapp.util.Utils;
//import com.saptrishi.hotelkotapp.view.adapter.ProductListAdapter;
import com.saptrishi.hotelkotapp.view.adapter.ProductListAdapter;
import com.saptrishi.hotelkotapp.view.fragment.HomeFragment;
import com.saptrishi.hotelkotapp.view.fragment.KOTListFragment;
import com.saptrishi.hotelkotapp.view.fragment.MyDialogFragment;
import com.saptrishi.hotelkotapp.view.fragment.OrderedKOTFragment;
//import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Activity_Pos extends AppCompatActivity implements View.OnClickListener {

    public static final double MINIMUM_SUPPORT = 0.1;
    DialogFragment dialogFragment;

    FragmentTransaction ft;
    AlertDialog.Builder builder;
    //private static final String TAG = ECartHomeActivity.class.getSimpleName();
    AprioriFrequentItemsetGenerator<String> generator =
            new AprioriFrequentItemsetGenerator<>();
    FrameLayout viewKot;
    SearchView searchView;
    int chekkot;
    //    String [] items={"Drinks","Food","Beverages","Tobacco","Liquir","Miscelenious","Confectionary"};
//   int [] foodIcon={R.drawable.food1,R.drawable.beverages1,R.drawable.hooka1,R.drawable.liquir1,R.drawable.miscelenious1,R.drawable.confectionary1};
    int co = 0;
    Context context;
    LinearLayout linearlayout;
    LinearLayout linearLayout_sabcate;
    EditText et_itemDesc;
    Button bback ,tablebooks;
    EditText et_search;
    private int itemCount = 0;
    private BigDecimal checkoutAmount = new BigDecimal( BigInteger.ZERO );
    private DrawerLayout mDrawerLayout;
    //    static int subcat_chk=0;
    private EditText editText;
    private TextView checkOutAmount, itemCountTextView, total_amount;
    private TextView offerBanner;
//    private AVLoadingIndicatorView progressBar;
    private ViewPager viewPager;
    private NavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_pos );
        editText = (EditText) findViewById( R.id.et_no_of_pax );
        context = this;
        viewPager = (ViewPager) findViewById( R.id.htab_viewpager );
        linearlayout = (LinearLayout) findViewById( R.id.cate_liner );
        linearLayout_sabcate = (LinearLayout) findViewById( R.id.SabCate_liner );
        et_search = (EditText) findViewById( R.id.autoCompleteTextView );
        tablebooks = (Button)findViewById(R.id.tablebook);
        tablebooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),itemlistviewtable.class);
                startActivity(i);
            }
        });

//        searchView = (SearchView) findViewById(R.id.student_queryside_serchview);
        bback = (Button) findViewById( R.id.backbutton );
        bback.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        } );
        et_itemDesc = (EditText) findViewById( R.id.item_short_des );
        builder = new AlertDialog.Builder( this );
//        total_amount= (TextView) findViewById(R.id.itemamount);
        SharedPreferences sf = getSharedPreferences( "Restaurant", Context.MODE_PRIVATE );
        final String DepartCode = sf.getString( "DepartCode", "" );
        SharedPreferences.Editor ed = sf.edit();
        ed.putString( "ActivityName", "Activity_pos" );
        ed.apply();
        RequestQueue queue = Volley.newRequestQueue( this.getApplicationContext() );

        String url = "http://" + IpServiceChecker.getIpaddress() + "/" + IpServiceChecker.getAppName() + "/Kanpur_HotelKotApp_Service.svc/ResturantCateApp/RestCode/" + DepartCode + "";

        Log.e( "urlllll", "" + url );

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest( url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    Log.e( "res_len", "" + response.length() );
                    for (int k = 0; k < response.length(); k++) {
                        JSONObject jsonObject = (JSONObject) response.get( k );
                        Log.e( "res_CateName", "" + jsonObject.getString( "ItemCatType" ) );
                        Log.e( "res_CateCode", "" + jsonObject.getString( "ItemCatMastCode" ) );
                        final Button myButton = new Button( getApplicationContext() );
                        myButton.setText( jsonObject.getString( "ItemCatType" ) );
                        myButton.setTag( jsonObject.getString( "ItemCatType" ) );

                        //myButton.setTag(jsonObject.getString("ItemCatMastCode"));
                        //myButton.setOnClickListener(View);
                        //myButton.setText(items[k]);
                        myButton.setGravity( Gravity.CENTER );
                        myButton.setTextSize( 20 );
                        myButton.setBackgroundResource( R.drawable.capsuleshape );
                        //Drawable icon = getApplicationContext().getResources().getDrawable(foodIcon[k]);
                        //myButton.setCompoundDrawablesWithIntrinsicBounds(icon, null, null, null);
                        myButton.setPadding( 20, 20, 20, 20 );
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT );
                        params.setMargins( 3, 0, 3, 0 );
                        myButton.setLayoutParams( params );
                        final int id_ = myButton.getId();
                        Log.e( "butid_cate", "" + id_ );
                        //myButton = ((Button) (id_));
                        myButton.setOnClickListener( new View.OnClickListener() {
                            public void onClick(View view) {
                                //   myButton.setBackgroundResource(R.drawable.capsuleshape);

                                SharedPreferences sf = getSharedPreferences( "Restaurant", Context.MODE_PRIVATE );
                                final String tbc = sf.getString( "tableName", "" );
                                Log.e( "Strinf", tbc + " hello" );
                                if (tbc.equals( "" )) {

//                                    Toast.makeText(Activity_Pos.this, "No table selected...", Toast.LENGTH_SHORT).show();

//                                    builder.setMessage(R.string.dialog_message) .setTitle(R.string.dialog_title);

                                    //Setting message manually and performing action on button click
                                    builder.setMessage( "No table selected kindly select a table" )
                                            .setCancelable( false )
                                            .setPositiveButton( "OK", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    startActivity( new Intent( Activity_Pos.this, Activity_Pos.class ) );
                                                    finish();
//                                                    Toast.makeText(getApplicationContext(), "you choose yes action for alertbox",
//                                                            Toast.LENGTH_SHORT).show();
                                                }
                                            } );

                                    //Creating dialog box
                                    AlertDialog alert = builder.create();
                                    //Setting the title manually
                                    alert.setTitle( "No Table Selected" );

                                    alert.show();


                                } else {
                                    setAllCategoryButtonColor();
                                    view.setBackgroundResource( R.drawable.occupied_button_shape );///////////////////////////////////////////////////////////////////////////////
                                    view.setPadding( 20, 20, 20, 20 );


                                    SubCate( DepartCode, view.getTag().toString().trim() );
                                }

                                //Toast.makeText(view.getContext(),"Button clicked index = " + view.getTag(), Toast.LENGTH_SHORT).show();
//                                 view.setBackgroundResource(R.drawable.occupied_button_shape);
//                                 int id = view.getId();
//                                 itembut(id);


                            }
                        } );

                        //myButton.setId(k);

                        //myButton.setTextSize(20);
                        // myButton.setHeight(150);
                        // myButton.setWidth(150);
                        //myButton.setPadding(30,20,10,0);
                        //     final int id_ = myButton.getId();
                        linearlayout.addView( myButton );
                    }

                } catch (JSONException e) {
                    e.printStackTrace();

                }

            }
        }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText( getApplicationContext(), volleyError + "", Toast.LENGTH_SHORT ).show();


            }
        } );

        queue.add( jsonArrayRequest );

        viewKot = (FrameLayout) findViewById( R.id.viewKot );
        viewKot.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(context, "showing.. Kot list ", Toast.LENGTH_SHORT).show();
                SharedPreferences sf = getSharedPreferences( "Restaurant", Context.MODE_PRIVATE );
                final String tbc = sf.getString( "tableName", "" );
                if (tbc.equals( "" )) {
//                                    Toast.makeText(Activity_Pos.this, "No table selected...", Toast.LENGTH_SHORT).show();

//                                    builder.setMessage(R.string.dialog_message) .setTitle(R.string.dialog_title);

                    //Setting message manually and performing action on button click
                    builder.setMessage( "No table selected kindly select a table" )
                            .setCancelable( false )
                            .setPositiveButton( "OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    startActivity( new Intent( Activity_Pos.this, Activity_Pos.class ) );
                                    finish();
//                                                    Toast.makeText(getApplicationContext(), "you choose yes action for alertbox",
//                                                            Toast.LENGTH_SHORT).show();
                                }
                            } );

                    //Creating dialog box
                    AlertDialog alert = builder.create();
                    //Setting the title manually
                    alert.setTitle( "No Table Selected" );
                    alert.show();


                } else {
//                    Utils.switchFragmentWithAnimation(R.id.frag_container, new KOTListFragment(), Activity_Pos.this, Utils.HOME_FRAGMENT, Utils.AnimationType.SLIDE_UP);

                    Utils.switchFragmentWithAnimation( R.id.frag_container, new OrderedKOTFragment(), Activity_Pos.this, Utils.HOME_FRAGMENT, Utils.AnimationType.SLIDE_UP );
                }
//




//                Utils.switchContent(R.id.frag_container,
//                        Utils.KOTListFragment,
//                        Activity_Pos.this,
//                        Utils.AnimationType.SLIDE_UP);
////

            }
        } );


//        for (int k= 1; k<= 6; k++) {
//
////            Button btn = new Button(this);
////            Drawable icon = this.getResources().getDrawable( img[i]);
////            btn.setCompoundDrawablesWithIntrinsicBounds(null, icon, null, null);
////            btn.setId (i);
////            btn.setText(name[i]);
////            layout.addView(btn);
//
//            Button myButton = new Button(this);
//
//            myButton.setOnClickListener(this);
//            myButton.setText(items[k]);
//            myButton.setGravity(Gravity.CENTER);
//
//            myButton.setTextSize(20);
//            myButton.setBackgroundResource(R.drawable.capsuleshape);
//            Drawable icon=this.getResources().getDrawable(foodIcon[k]);
//            myButton.setCompoundDrawablesWithIntrinsicBounds(icon,null,null,null);
//          //  myButton.setBackground(foodIcon[k]);
//            myButton.setPadding(20,20,20,20);
//
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
//
//            params.setMargins(3, 0, 3, 0);
////            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) myButton.getLayoutParams();
////            params.leftMargin = 10; params.topMargin = 10;params.rightMargin=10; params.width=180;params.height=100;
////            myButton.setLayoutParams(params);
////            ViewGroup. p=(ViewGro up.MarginLayoutParams)myButton.getLayoutParams();
////            p.leftMargin=10;
//            myButton.setLayoutParams(params);
//
//            myButton.setId(k);
//
//            //myButton.setTextSize(20);
//            // myButton.setHeight(150);
//            // myButton.setWidth(150);
//            //myButton.setPadding(30,20,10,0);
//       //     final int id_ = myButton.getId();
//            linearlayout.addView(myButton);
//        }


        CenterRepository.getCenterRepository().setListOfProductsInShoppingList(
                new TinyDB( getApplicationContext() ).getListObject(
                        PreferenceHelper.MY_CART_LIST_LOCAL, Product.class ) );

        itemCount = CenterRepository.getCenterRepository().getListOfProductsInShoppingList()
                .size();

        //	makeFakeVolleyJsonArrayRequest();

        offerBanner = ((TextView) findViewById( R.id.new_offers_banner ));

        itemCountTextView = (TextView) findViewById( R.id.item_count );
        itemCountTextView.setSelected( true );
        itemCountTextView.setText( String.valueOf( itemCount ) );

        checkOutAmount = (TextView) findViewById( R.id.checkout_amount );
        checkOutAmount.setSelected( true );
        checkOutAmount.setText( Money.rupees( checkoutAmount ).toString() );
        offerBanner.setSelected( true );

        mDrawerLayout = (DrawerLayout) findViewById( R.id.nav_drawer );
        mNavigationView = (NavigationView) findViewById( R.id.nav_view );

        checkOutAmount.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Utils.vibrate( getApplicationContext() );

                Utils.switchContent( R.id.frag_container,
                        Utils.SHOPPING_LIST_TAG, Activity_Pos.this,
                        Utils.AnimationType.SLIDE_UP );

            }
        } );

        if (itemCount != 0) {
            for (Product product : CenterRepository.getCenterRepository()
                    .getListOfProductsInShoppingList()) {

                updateCheckOutAmount(
                        BigDecimal.valueOf( Long.valueOf( product.getSellMRP() ) * Long.valueOf( product.getQuantity() ) ),
                        true );
            }
        }

        findViewById( R.id.item_counter ).setOnClickListener(
                new View.OnClickListener() {


                    @Override
                    public void onClick(View v) {
                        Log.e( "CkickItemlist", "ok" );
                        //Utils.vibrate(getApplicationContext());
                        Utils.switchContent( R.id.frag_container, Utils.SHOPPING_LIST_TAG, Activity_Pos.this, Utils.AnimationType.SLIDE_UP );

                    }
                } );

        findViewById( R.id.checkout ).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (itemCount > 0) {
                            Utils.vibrate( getApplicationContext() );
                            showPurchaseDialog();
                        } else {
                            Toast.makeText( getApplicationContext(), "Cart is blank", Toast.LENGTH_SHORT ).show();
                        }

                    }
                } );

        Utils.switchFragmentWithAnimation( R.id.frag_container, new HomeFragment(), this, Utils.HOME_FRAGMENT, Utils.AnimationType.SLIDE_UP );


        toggleBannerVisibility();


        mNavigationView.setNavigationItemSelectedListener( new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                menuItem.setChecked( true );
                switch (menuItem.getItemId()) {
//                    case R.id.home:
//
//                        mDrawerLayout.closeDrawers();
//
//                        Utils.switchContent( R.id.frag_container,
//                                Utils.HOME_FRAGMENT,
//                                Activity_Pos.this,
//                                Utils.AnimationType.SLIDE_LEFT );
//
//                        return true;
//
//                    case R.id.my_cart:
//
//                        mDrawerLayout.closeDrawers();
//
//                        Utils.switchContent( R.id.frag_container,
//                                Utils.SHOPPING_LIST_TAG,
//                                Activity_Pos.this,
//                                Utils.AnimationType.SLIDE_LEFT );
//                        return true;
//
//                    case R.id.my_waiter:
//
//                        mDrawerLayout.closeDrawers();
//
//                        dialogFragment = new MyDialogFragment();
//
//                        Bundle bundle = new Bundle();
//                        bundle.putBoolean( "notAlertDialog", true );
//
//                        dialogFragment.setArguments( bundle );
//
//                        ft = getSupportFragmentManager().beginTransaction();
////                Fragment prev = getSupportFragmentManager().findFragmentByTag("dialog");
////                if (prev != null) {
////                    ft.remove(prev);
////                }
//                        ft.addToBackStack( null );
//
//
//                        dialogFragment.show( ft, "dialog" );
//
//                        return true;


//                    case R.id.contact_us:
//
//                        mDrawerLayout.closeDrawers();
//
//                        Utils.switchContent(R.id.frag_container,
//                                Utils.CONTACT_US_FRAGMENT,
//                                Activity_Pos.this,
//                                Utils.AnimationType.SLIDE_LEFT);
//                        return true;
//
//                    case R.id.settings:
//
//                        mDrawerLayout.closeDrawers();
//
//                        Utils.switchContent(R.id.frag_container,
//                                Utils.SETTINGS_FRAGMENT_TAG,
//                                Activity_Pos.this,
//                                Utils.AnimationType.SLIDE_LEFT);
//                        return true;
                    default:
                        return true;
                }
            }
        } );


//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
//        {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                // filter recycler view when query submitted
//               // allEmpAdapter.getFilter().filter(query);
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String query) {
//                // filter recycler view when text is changed
//                ProductListAdapter.getFilter().filter(query);
//
//                return false;
//            }
//        });


    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setAllCategoryButtonColor() {
        if (linearlayout == null) {
            return;
        }

        int childCount = linearlayout.getChildCount();

        for (int i = 0; i < childCount; i++) {
            Button cat = (Button) linearlayout.getChildAt( i );
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                cat.setBackground( getDrawable( R.drawable.capsuleshape ) );
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setAllSub_CategoryButtonColor() {
        if (linearLayout_sabcate == null) {
            return;
        }

        int childCount = linearLayout_sabcate.getChildCount();

        for (int i = 0; i < childCount; i++) {
            Button sub_cat = (Button) linearLayout_sabcate.getChildAt( i );
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                sub_cat.setBackground( getDrawable( R.drawable.capsuleshape ) );
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate( R.menu.menu_main, menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {

            Log.e( "hello", "hello" );
            SharedPreferences sf = getSharedPreferences( "Restaurant", Context.MODE_PRIVATE );
            SharedPreferences.Editor ed = sf.edit();
            ed.putString( "DepartCode", "" );
            ed.putString( "loginId", "" );
            //         ed.putString("loginId","");
            ed.apply();
            Intent i = new Intent( getApplicationContext(), LoginActivity.class );
            startActivity( i );
            finish();
            return true;
        }

        return super.onOptionsItemSelected( item );
    }

    @Override
    public void onClick(View v) {


//        linearLayout_sabcate.removeAllViews();
//        for (int s= 100; s<= 110; s++) {
//            int id = v.getId();
//
//            Log.e("loop","value"+s);
//            v.setBackgroundResource(R.drawable.occupied_button_shape);///////////////////////////////////////////////////////////////////////////////
//            v.setPadding(20,20,20,20);
//            Button myButton_sab = new Button(this);
//            //myButton_sab.setOnClickListener(this);
//            Log.e("catebutid"," "+id);
//            myButton_sab.setText("Cate "+id+" Button "+s);////////////////////////////////////////////////////////////////////////////////////
//            myButton_sab.setTextSize(18);
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
//
//            params.setMargins(3, 0, 3, 0);
//            myButton_sab.setLayoutParams(params);
//
//            Log.e("setsub"," cate ");
//            myButton_sab.setBackgroundResource(R.drawable.capsuleshape);
//            myButton_sab.setId(s);
//            //myButton_sab.setTextSize(20);
//            myButton_sab.setHeight(20);
//            myButton_sab.setWidth(20);
//            //myBvxvxcv`utton_sab.setPadding(30,20,10,0);
//            final int id_ = myButton_sab.getId();
//            Log.e("setsub"," cateid "+id_);
//            linearLayout_sabcate.addView(myButton_sab);
//            myButton_sab = ((Button) findViewById(id_));
//            myButton_sab.setOnClickListener(new View.OnClickListener() {
//                public void onClick(View view) {
//                    //Toast.makeText(view.getContext(),"Button clicked index = " + id_, Toast.LENGTH_SHORT).show();
//
//                    view.setBackgroundResource(R.drawable.occupied_button_shape);
//                    int id = view.getId();
//                    //itembut();
//                }
//            });
//        }

    }

    public void SubCate(String RestuCode, String CateCode) {
        final String resturentcode = RestuCode;
        linearLayout_sabcate = (LinearLayout) findViewById( R.id.SabCate_liner );
        linearLayout_sabcate.removeAllViews();
        String url = "http://" + IpServiceChecker.getIpaddress() + "/" + IpServiceChecker.getAppName() + "/Kanpur_HotelKotApp_Service.svc/ResturantIteamGrpApp/RestCode/" + RestuCode + "/CateCode/" + CateCode + "";

        Log.e( "anwesan", "" + url );

        RequestQueue queue = Volley.newRequestQueue( this.getApplicationContext() );
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest( url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    Log.e( "res_len", "" + response.length() );
                    for (int s = 0; s < response.length(); s++) {
                        JSONObject jsonObject = (JSONObject) response.get( s );
                        Log.e( "res_CateName", "" + jsonObject.getString( "ItemSubgrpname" ) );
                        Log.e( "res_CateCode", "" + jsonObject.getString( "ItemSubgrpnameCode" ) );
                        final Button myButton_sab = new Button( getApplicationContext() );
                        myButton_sab.setText( jsonObject.getString( "ItemSubgrpname" ) );
                        myButton_sab.setTag( jsonObject.getString( "ItemSubgrpnameCode" ) );
                        myButton_sab.setTextSize( 16 );

                        myButton_sab.setBackgroundResource( R.drawable.capsuleshape );
                        myButton_sab.setHeight( 20 );
                        myButton_sab.setWidth( 20 );
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT );

                        params.setMargins( 3, 0, 3, 0 );
                        myButton_sab.setLayoutParams( params );

                        myButton_sab.setOnClickListener( new View.OnClickListener() {
                            public void onClick(View v) {

//                                int id=v.getId();
                                setAllSub_CategoryButtonColor();
                                v.setBackgroundResource( R.drawable.occupied_button_shape );
//                                    subcat_chk++;
//                                    if(subcat_chk>0)
//                                    {
//
//                                    }
//                                    else
//

//                                    myButton_sab.setBackgroundResource(R.drawable.capsuleshape);
                                // myButton_sab.setTextSize(16);

                                ///////////////////////////////////////////////////////////////////////////////
                                //v.setPadding(20, 20, 20, 20);
                                itembut1( resturentcode, v.getTag().toString().trim() );
                                //bind_item(resturentcode,v.getTag().toString().trim());
                                //itembut(resturentcode,v.getTag().toString().trim());
                                //SubCate(DepartCode, view.getTag().toString().trim());
                                //Toast.makeText(view.getContext(), "Button clicked index = " + view.getTag(), Toast.LENGTH_SHORT).show();
//                                 view.setBackgroundResource(R.drawable.occupied_button_shape);
//                                 int id = view.getId();
//                                 itembut(id);
                            }
                        } );


                        linearLayout_sabcate.addView( myButton_sab );
                    }
                } catch (JSONException e) {
                    e.printStackTrace();

                }

            }
        }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText( getApplicationContext(), volleyError + "", Toast.LENGTH_SHORT ).show();


            }
        } );

        queue.add( jsonArrayRequest );

//        for (int s= 100; s<= 110; s++) {
//
//
//            Log.e("loop","value"+s);
//
//            Button myButton_sab = new Button(this);
//            //myButton_sab.setOnClickListener(this);
//            myButton_sab.setText("Button "+s);////////////////////////////////////////////////////////////////////////////////////
//            myButton_sab.setTextSize(18);
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
//
//            params.setMargins(3, 0, 3, 0);
//            myButton_sab.setLayoutParams(params);
//
//            Log.e("setsub"," cate ");
//            myButton_sab.setBackgroundResource(R.drawable.capsuleshape);
//            myButton_sab.setId(s);
//            //myButton_sab.setTextSize(20);
//            myButton_sab.setHeight(20);
//            myButton_sab.setWidth(20);
//            //myButton_sab.setPadding(30,20,10,0);
//            final int id_ = myButton_sab.getId();
//            Log.e("setsub"," cateid "+id_);
//            linearLayout_sabcate.addView(myButton_sab);
//            myButton_sab = ((Button) findViewById(id_));
//            myButton_sab.setOnClickListener(new View.OnClickListener() {
//                public void onClick(View view) {
//                    //Toast.makeText(view.getContext(),"Button clicked index = " + id_, Toast.LENGTH_SHORT).show();
//                    view.setBackgroundResource(R.drawable.occupied_button_shape);
//                    int id = view.getId();
//                    itembut(id);
//                }
//            });
//        }
    }

    public void itembut1(String rest_code, String Subcate_ocde) {

        String url = "http://" + IpServiceChecker.getIpaddress() + "/" + IpServiceChecker.getAppName() + "/Kanpur_HotelKotApp_Service.svc/ResturantIteams/RestCode/" + rest_code + "/SubcateCode/" + Subcate_ocde + "";

        Log.e( "rishabh", "" + url );


        RequestQueue queue = Volley.newRequestQueue( this );
        JsonArrayRequest req = new JsonArrayRequest( url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                // Locate the WorldPopulation Class
                // itembut(response);
                try {




                    // Parsing json array response
                    //itembut(response);

                    FakeWebServer.getFakeWebServer().getAllProductsmix_jarry( response );
                    //RecyclerView recyclerView = (RecyclerView) findViewById(R.id.product_list_recycler_view);
                    final RecyclerView recyclerView = (RecyclerView) findViewById( R.id.product_list_recycler_view );

                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager( context.getApplicationContext() );
                    recyclerView.setLayoutManager( linearLayoutManager );
                    recyclerView.setHasFixedSize( true );




                    ProductListAdapter adapter = new ProductListAdapter( "Television", context, false, "" );
                    //CategoryListAdapter adapter=new CategoryListAdapter(this);
                    Log.e( "adpter Count", "" + adapter.getItemCount() );
                    recyclerView.setAdapter( adapter );

//SEARCH

                    //search
                    et_search.addTextChangedListener( new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        }

                        @Override
                        public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                               // Toast.makeText( context,  ""+ s, Toast.LENGTH_SHORT ).show();

                            ProductListAdapter adapter = new ProductListAdapter( "Television", context, false, s );
                            Log.e( "abcdddd", "" + adapter.getItemCount() );
                            recyclerView.setAdapter( adapter );
                        }

                        @Override
                        public void afterTextChanged(Editable editable) {

                        }
                    } );

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d( "Error service", e.getMessage() );
                    //Toast.makeText(AnimalInfoReadTags.this,"Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

                //hidepDialog();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d( "", "Error: " + error.getMessage() );
                //Log.d("Volley Error service",error.getMessage());
                //hidepDialog();
            }
        } );

        // Adding request to request queue

        queue.add( req );

    }

//    public void itembut(String rest_code, String Subcate_ocde) {
//
//
//        FakeWebServer objfk=new FakeWebServer(this);
//        objfk.getAllProductsmix(rest_code,Subcate_ocde);
//        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.product_list_recycler_view);
//
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getBaseContext());
//        recyclerView.setLayoutManager(linearLayoutManager);
//
////        recyclerView.setClickable(false);
//
//        recyclerView.setHasFixedSize(true);
//        ProductListAdapter adapter = new ProductListAdapter("Television", this, false);
//        //CategoryListAdapter adapter=new CategoryListAdapter(this);
//        Log.e("adpter Count", "" + adapter.getItemCount());
//        recyclerView.setAdapter(adapter);
//
//
////        Utils.switchFragmentWithAnimation(
////                R.id.frag_container,
////                new ProductOverviewFragment(),
////                this, null,
////                Utils.AnimationType.SLIDE_LEFT);
//
//        //Log.e("fun test","ok");
////        LinearLayout linearLayoutItem_sabcate= (LinearLayout) findViewById(R.id.SabCateItem_liner);
////        linearLayoutItem_sabcate.removeAllViews();
//        //FakeWebServer.getFakeWebServer().getAllProductsmix();
////
////        ProductsInCategoryPagerAdapter adapter = new ProductsInCategoryPagerAdapter(this.getSupportFragmentManager());
////        adapter.addFrag(new ProductListFragment("Microwave oven"), "Microwave oven");
////        viewPager.setAdapter(adapter);
////
////        Log.e("adpter Count",""+adapter.getCount());
////        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.product_list_recycler_view);
////
////        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getBaseContext());
////        recyclerView.setLayoutManager(linearLayoutManager);
////        recyclerView.setHasFixedSize(true);
////       ProductListAdapter adapter = new ProductListAdapter("Television",this, false);
////        //CategoryListAdapter adapter=new CategoryListAdapter(this);
////        Log.e("adpter Count",""+adapter.getItemCount());
////       recyclerView.setAdapter(adapter);
//
//        // Log.e("fun test1255","ok");
//        //linearLayoutItem_sabcate.addView(adapter);
//
//        //FragmentManager fm = getFragmentManager();
//
//// add
//        //FragmentTransaction ft = fm.beginTransaction();
//        //ft.add(R.id.frag_container,new ProductListFragment());
//// alternatively add it with a tag
//// trx.add(R.id.your_placehodler, new YourFragment(), "detail");
//        //ft.commit();
//        //linearLayoutItem_sabcate.addView(ft);
////        for (int z= 20; z<= 30; z++) {
////
////            Button myButton_sabItem = new Button(this);
////            myButton_sabItem.setText("SubCateItem "+id+" Button "+z);
////            myButton_sabItem.setId(z);
////            linearLayoutItem_sabcate.addView(myButton_sabItem);
////        }
//    }

    public void updateItemCount(boolean ifIncrement) {
        try {
            if (ifIncrement) {
                itemCount++;
                itemCountTextView.setText( String.valueOf( itemCount ) );
                Log.e( "if_incre", "" + itemCount );

            } else {
                itemCountTextView.setText( String.valueOf( itemCount <= 0 ? 0
                        : --itemCount ) );
                Log.e( "else_incre", "" + itemCount );
            }
            Log.e( "chk", ifIncrement + "" );

            toggleBannerVisibility();

        } catch (Exception exc) {

        }
    }

    public void updateCheckOutAmount(BigDecimal amount, boolean increment) {

        if (increment) {
            checkoutAmount = checkoutAmount.add( amount );
        } else {
            if (checkoutAmount.signum() == 1)
                checkoutAmount = checkoutAmount.subtract( amount );
        }
        if (checkoutAmount.compareTo( BigDecimal.ZERO ) < 0)
            checkoutAmount = BigDecimal.valueOf( 0 );

        checkOutAmount.setText( Money.rupees( checkoutAmount ).toString() );
    }

    public void updateItemdesc(String itemdesc, int position) {

        if (!itemdesc.isEmpty()) {

            Log.e( "if_itemdesc", itemdesc );
            et_itemDesc.setText( CenterRepository.getCenterRepository().getListOfProductsInShoppingList().get( position ).getItemShortDesc() );
        } else {
            Log.e( "else_itemdesc", itemdesc );
        }
        Log.e( "nhjiitemdesc", itemdesc );


    }


    public void toggleBannerVisibility() {
        if (itemCount == 0) {
            Log.e( "if_togBannerVisibility", "" + itemCount );
            findViewById( R.id.checkout_item_root ).setVisibility( View.VISIBLE );
            findViewById( R.id.new_offers_banner ).setVisibility( View.GONE );

        } else {
            Log.e( "else_toBannerVisibility", "" + itemCount );
            findViewById( R.id.checkout_item_root ).setVisibility( View.VISIBLE );
            findViewById( R.id.new_offers_banner ).setVisibility( View.GONE );
        }
    }

    public int getItemCount() {
        return itemCount;
    }

    public DrawerLayout getmDrawerLayout() {
        return mDrawerLayout;
    }

    public void showPurchaseDialog() {

        Log.e( "hello1", "hello" );

        AlertDialog.Builder exitScreenDialog = new AlertDialog.Builder( Activity_Pos.this, R.style.PauseDialog );

//        exitScreenDialog.setTitle("Order Confirmation")
//                .setMessage("Would you like to place this order ?");
        exitScreenDialog.setTitle( "Order Confirmation" )
                .setMessage( "Would you like to place this order ? " );
        exitScreenDialog.setCancelable( true );

        exitScreenDialog.setPositiveButton(
                "Place Order",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //finish();
                        dialog.cancel();

                        ArrayList<String> productId = new ArrayList<String>();

                        for (Product productFromShoppingList : CenterRepository.getCenterRepository().getListOfProductsInShoppingList()) {
                            //add product ids to array
                            productId.add( productFromShoppingList.getProductId() );
                        }

                        //pass product id array to Apriori ALGO
                        CenterRepository.getCenterRepository()
                                .addToItemSetList( new HashSet<>( productId ) );

                        //Do Minning
                        FrequentItemsetData<String> data = generator.generate(
                                CenterRepository.getCenterRepository().getItemSetList()
                                , MINIMUM_SUPPORT );

                        for (Set<String> itemset : data.getFrequentItemsetList()) {
                            Log.e( "APriori", "Item Set : " +
                                    itemset + "Support : " +
                                    data.getSupport( itemset ) );
                        }

                        //clear all list item


                        toggleBannerVisibility();

                        itemCount = 0;
                        itemCountTextView.setText( String.valueOf( 0 ) );
                        checkoutAmount = new BigDecimal( BigInteger.ZERO );
                        checkOutAmount.setText( Money.rupees( checkoutAmount ).toString() );
                        CenterRepository.getCenterRepository().getListOfProductsInShoppingList().clear();
                        TableSenderToService tableSenderToService = new TableSenderToService( Activity_Pos.this );
                        tableSenderToService.tableSender( editText.getText().toString() );

                        SharedPreferences sf = getSharedPreferences( "Restaurant", Context.MODE_PRIVATE );
                        SharedPreferences.Editor ed = sf.edit();
                        ed.putString( "tableName", "" );
                        ed.apply();
                        //tableSenderToService.tableSender();

                    }
                } ).setNegativeButton(
                "Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                } );

        //        exitScreenDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
//            @Override
//            public void onDismiss(DialogInterface dialog) {
//                Snackbar.make(Activity_Pos.this.getWindow().getDecorView().findViewById(android.R.id.content)
//                        , "Order Placed Successfully, Happy Shopping !!", Snackbar.LENGTH_LONG)
//                        .setAction("View Apriori Output", new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                startActivity(new Intent(Activity_Pos.this, APrioriResultActivity.class));
//                            }
//                        }).show();
//            }
//        });

        AlertDialog alert11 = exitScreenDialog.create();
        alert11.show();

        alert11.getButton( alert11.BUTTON_NEGATIVE ).setTextColor( Color.WHITE );
        alert11.getButton( alert11.BUTTON_POSITIVE ).setTextColor( Color.WHITE );

    }

    @Override
    protected void onPause() {
        super.onPause();

        // Store Shopping Cart in DB
        new TinyDB( getApplicationContext() ).putListObject(
                PreferenceHelper.MY_CART_LIST_LOCAL, CenterRepository
                        .getCenterRepository().getListOfProductsInShoppingList() );
    }


    @Override
    protected void onResume() {
        super.onResume();

        // Show Offline Error Message
        if (!Connectivity.isConnected( getApplicationContext() )) {
            final Dialog dialog = new Dialog( Activity_Pos.this );
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
    public void onBackPressed() {
        super.onBackPressed();
        startActivity( new Intent( this, RestaurantListActivity.class ) );
        finish();
    }


}
