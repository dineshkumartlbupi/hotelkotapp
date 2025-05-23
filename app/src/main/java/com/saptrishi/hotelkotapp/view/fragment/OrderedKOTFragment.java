package com.saptrishi.hotelkotapp.view.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
//import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.saptrishi.hotelkotapp.R;
import com.saptrishi.hotelkotapp.model.MySqliteDatabase;
import com.saptrishi.hotelkotapp.util.Utils;
import com.saptrishi.hotelkotapp.view.activities.Activity_Pos;
import com.saptrishi.hotelkotapp.view.activities.RestaurantListActivity;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

///**
// * A simple {@link Fragment} subclass.
// * Activities that contain this fragment must implement the
// * {@link OrderedKOTFragment.OnFragmentInteractionListener} interface
// * to handle interaction events.
// * Use the {@link OrderedKOTFragment#newInstance} factory method to
// * create an instance of this fragment.
// */

public class OrderedKOTFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    int[] to = {R.id.tv_kotnumber, R.id.name, R.id.quantity, R.id.rate, R.id.amount};
    String[] from = {"Sr_no", "name", "quantity", "rate", "amount"};
    TextView totalCost;
    ArrayList<HashMap<String, String>> al = new ArrayList<HashMap<String, String>>();
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private KOTListFragment.OnFragmentInteractionListener mListener;

    public OrderedKOTFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OrderedKOTFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OrderedKOTFragment newInstance(String param1, String param2) {
        OrderedKOTFragment fragment = new OrderedKOTFragment();
        Bundle args = new Bundle();
        args.putString( ARG_PARAM1, param1 );
        args.putString( ARG_PARAM2, param2 );
        fragment.setArguments( args );
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        if (getArguments() != null) {
            mParam1 = getArguments().getString( ARG_PARAM1 );
            mParam2 = getArguments().getString( ARG_PARAM2 );
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view = inflater.inflate( R.layout.fragment_ordered_kot, container, false );

        totalCost = (TextView) view.findViewById( R.id.kotTotalAmount );

        view.findViewById( R.id.pullDown ).setVisibility( View.VISIBLE );

        TextView KotHeading = (TextView) view.findViewById( R.id.kotHeading );
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences( "Restaurant", Context.MODE_PRIVATE );
        String tablenumber = sharedPreferences.getString( "tableName", "" );
        KotHeading.setText( "Table Number: " + tablenumber );
        view.findViewById( R.id.pullDown ).setOnTouchListener(
                new View.OnTouchListener() {

                    @Override
                    public boolean onTouch(View v, MotionEvent event) {


                        Utils.switchFragmentWithAnimation(
                                R.id.frag_container,
                                new HomeFragment(),
                                ((Activity_Pos) (getContext())), Utils.HOME_FRAGMENT,
                                Utils.AnimationType.SLIDE_DOWN );

                        return false;
                    }
                } );

        ListView lv = (ListView) view.findViewById( R.id.kotListView );

        final SimpleAdapter sa = new SimpleAdapter( getActivity(), al, R.layout.kot_list, from, to );
        lv.setAdapter( sa );
//        for (int i=1;i<10;i++)
//        {
//            HashMap<String, String> hm1 = new HashMap<String, String>();
//            hm1.put("Sr_no", "1");
//            hm1.put("name", "matar paneer");
//            hm1.put("quantity","250");
//            al.add(hm1);
//       }
        MySqliteDatabase mySqliteDatabase = new MySqliteDatabase( getActivity() );

        Cursor cursor = mySqliteDatabase.fetchkotListTable();
        double amount = 0x0.0p0;
        if (cursor.moveToFirst()) {
//            HashMap<String, String> hm = new HashMap<String, String>();
//            hm.put("Sr_no", "Sr.no");
//            hm.put("name", "Name");
//            hm.put("quantity","Qty");
//            hm.put("rate","Rate("+getString(R.string.Rs)+")");
//            hm.put("amount","Amount("+getString(R.string.Rs)+")");
//            al.add(hm);

            int i = 1;
            do {
                HashMap<String, String> hm1 = new HashMap<String, String>();
                hm1.put( "Sr_no", i + "" );
                hm1.put( "name", cursor.getString( 1 ) );
                hm1.put( "rate", "Rate (" + getString( R.string.Rs ) + ") " + cursor.getString( 4 ) );
                hm1.put( "quantity", "Qty " + cursor.getString( 3 ) );
                hm1.put( "amount", "Amt (" + getString( R.string.Rs ) + ") " + cursor.getString( 2 ) );
                Log.e( "Sr_no", i + "" );
                Log.e( "name", cursor.getString( 1 ) );
                Log.e( "rate", cursor.getString( 2 ) );
                Log.e( "quantity", cursor.getString( 3 ) );
                Log.e( "amount", cursor.getString( 4 ) );
                amount = amount + Double.parseDouble( cursor.getString( 2 ) );
                i++;
                al.add( hm1 );
            } while (cursor.moveToNext());
            sa.notifyDataSetChanged();
        }
        DecimalFormat format = new DecimalFormat( "0.00" );
        String formattedamount = format.format( amount );

        totalCost.setText( totalCost.getText().toString() + getString( R.string.Rs ) + " " + formattedamount );


        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction( uri );
        }
    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

}
