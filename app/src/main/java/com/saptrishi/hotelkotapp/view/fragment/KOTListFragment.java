package com.saptrishi.hotelkotapp.view.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.saptrishi.hotelkotapp.R;
import com.saptrishi.hotelkotapp.util.Utils;
import com.saptrishi.hotelkotapp.view.activities.Activity_Pos;
import com.saptrishi.hotelkotapp.view.activities.RestaurantListActivity;
import com.saptrishi.hotelkotapp.view.adapter.CustomExpandableListAdapter;
import com.saptrishi.hotelkotapp.view.adapter.ExpandableListDataPump;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link KOTListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link KOTListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class KOTListFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    TextView KotHeading;
    int[] to = {R.id.name, R.id.tv_kotnumber};
    String[] from = {"kotcode", "kotList"};
    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    HashMap<String, List<String>> expandableListDetail;


    ArrayList<HashMap<String, String>> al = new ArrayList<HashMap<String, String>>();


    private OnFragmentInteractionListener mListener;

    public KOTListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment KOTListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static KOTListFragment newInstance(String param1, String param2) {
        KOTListFragment fragment = new KOTListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment



        View view= inflater.inflate(R.layout.fragment_kotlist, container, false);



        expandableListView = (ExpandableListView) view.findViewById(R.id.expandableListView);
        expandableListDetail = ExpandableListDataPump.getData();
        expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
        expandableListAdapter = new CustomExpandableListAdapter(getActivity(), expandableListTitle, expandableListDetail);
        expandableListView.setAdapter(expandableListAdapter);
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getActivity(),
                        expandableListTitle.get(groupPosition) + " List Expanded.",
                        Toast.LENGTH_SHORT).show();
            }
        });

        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getActivity(),
                        expandableListTitle.get(groupPosition) + " List Collapsed.",
                        Toast.LENGTH_SHORT).show();

            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                Toast.makeText(
                        getActivity(),
                        expandableListTitle.get(groupPosition)
                                + " -> "
                                + expandableListDetail.get(
                                expandableListTitle.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT
                ).show();
                return false;
            }
        });





        view.findViewById(R.id.pullDown).setVisibility(View.VISIBLE);

        KotHeading= (TextView) view.findViewById(R.id.kotHeading);
        SharedPreferences sharedPreferences=getActivity().getSharedPreferences("Restaurant",Context.MODE_PRIVATE);
        String tablenumber=sharedPreferences.getString("tableName","");
        KotHeading.setText("Table Number: "+tablenumber);
        view.findViewById(R.id.pullDown).setOnTouchListener(
                new View.OnTouchListener() {

                    @Override
                    public boolean onTouch(View v, MotionEvent event) {



                        Utils.switchFragmentWithAnimation(
                                R.id.frag_container,
                                new HomeFragment(),
                                ((Activity_Pos) (getContext())), Utils.HOME_FRAGMENT,
                                Utils.AnimationType.SLIDE_DOWN);

                        return false;
                    }
                });

//        ListView lv = (ListView) view.findViewById(R.id.kotListView);
//
//
//
//        final SimpleAdapter sa = new SimpleAdapter(getActivity(), al, R.layout.kot_list, from, to);
//        lv.setAdapter(sa);
//
//        for (int i=1;i<=10;i++)
//        {
//            HashMap<String, String> hm1 = new HashMap<String, String>();
//            hm1.put("kotcode", i+"");
//            hm1.put("kotList", "KOT number "+i);
//            al.add(hm1);
//        }
//        sa.notifyDataSetChanged();
//        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(getActivity(), al.get(i).get("kotcode")+" clicked", Toast.LENGTH_SHORT).show();
//            }
//        });


        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
