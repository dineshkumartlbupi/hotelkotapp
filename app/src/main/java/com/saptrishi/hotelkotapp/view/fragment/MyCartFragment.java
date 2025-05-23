/*
 * Copyright (c) 2017. http://hiteshsahu.com- All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * If you use or distribute this project then you MUST ADD A COPY OF LICENCE
 * along with the project.
 *  Written by Hitesh Sahu <hiteshkrsahu@Gmail.com>, 2017.
 */

package com.saptrishi.hotelkotapp.view.fragment;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.SearchView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.saptrishi.hotelkotapp.R;
import com.saptrishi.hotelkotapp.model.CenterRepository;
import com.saptrishi.hotelkotapp.model.entities.Money;
import com.saptrishi.hotelkotapp.util.Utils;
import com.saptrishi.hotelkotapp.util.Utils.AnimationType;
import com.saptrishi.hotelkotapp.view.activities.Activity_Pos;
import com.saptrishi.hotelkotapp.view.activities.ECartHomeActivity;
import com.saptrishi.hotelkotapp.view.adapter.ShoppingListAdapter;
//import com.saptrishi.hotelkotapp.view.adapter.ShoppingListAdapter.OnItemClickListener;
import com.saptrishi.hotelkotapp.view.customview.OnStartDragListener;
import com.saptrishi.hotelkotapp.view.customview.SimpleItemTouchHelperCallback;

import java.math.BigDecimal;
import java.math.BigInteger;

public class MyCartFragment extends Fragment implements OnStartDragListener {

    private static FrameLayout noItemDefault;
    private static RecyclerView recyclerView;
    private ItemTouchHelper mItemTouchHelper;
    SearchView searchView;
    public MyCartFragment() {
    }

//    /**
//     * @param rootView
//     * @param myCartListView
//     */
    public static void updateMyCartFragment(boolean showList) {

        Log.e("checklist",""+showList);
        if (showList) {

            if (null != recyclerView && null != noItemDefault) {
                recyclerView.setVisibility(View.VISIBLE);
                Log.e("checklist1","11"+showList);
                noItemDefault.setVisibility(View.GONE);
            }
        } else {
            if (null != recyclerView && null != noItemDefault) {
                recyclerView.setVisibility(View.GONE);
                Log.e("checklist2","22"+showList);
                noItemDefault.setVisibility(View.VISIBLE);

            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        Log.e("checklist","Test125");
        View view = inflater.inflate(R.layout.frag_product_list_fragment, container,
                false);
       // searchView = (SearchView)view.findViewById(R.id.student_queryside_serchview);
        view.findViewById(R.id.slide_down).setVisibility(View.VISIBLE);
        view.findViewById(R.id.slide_down).setOnTouchListener(
                new OnTouchListener() {

                    @Override
                    public boolean onTouch(View v, MotionEvent event) {

                        Utils.switchFragmentWithAnimation(R.id.frag_container,new HomeFragment(),((Activity_Pos) (getContext())),Utils.HOME_FRAGMENT, AnimationType.SLIDE_DOWN);
                        //Utils.switchFragmentWithAnimation(R.id.frag_container,new MyCartFragment(),null,Utils.HOME_FRAGMENT, AnimationType.SLIDE_DOWN);



                        return false;
                    }
                });

        // Fill Recycler View

        noItemDefault = (FrameLayout) view.findViewById(R.id.default_nodata);

        recyclerView = (RecyclerView) view
                .findViewById(R.id.product_list_recycler_view);

        if (CenterRepository.getCenterRepository().getListOfProductsInShoppingList().size() != 0) {

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(
                    getActivity().getBaseContext());

            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setHasFixedSize(true);

            final ShoppingListAdapter shoppinListAdapter = new ShoppingListAdapter(
                    getActivity(), this);

            recyclerView.setAdapter(shoppinListAdapter);
//            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//                @Override
//                public boolean onQueryTextSubmit(String query) {
//                    // filter recycler view when query submitted
//                    shoppinListAdapter.().filter(query);
//                    return false;
//                }
//
//                @Override
//                public boolean onQueryTextChange(String query) {
//                    // filter recycler view when text is changed
//                    shoppinListAdapter.getFilter().filter(query);
//                    return false;
//                }
//            });




//            shoppinListAdapter
//                    .SetOnItemClickListener(new OnItemClickListener() {
//
//                        @Override
//                        public void onItemClick(View view, int position) {
//
//                            Utils.switchFragmentWithAnimation(
//                                    R.id.frag_container,
//                                    new ProductDetailsFragment("", position,
//                                            true),
//                                    ((Activity_Pos) (getContext())), null,
//                                    AnimationType.SLIDE_LEFT);
//
//                        }
//                    });

            ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(
                    shoppinListAdapter);
            mItemTouchHelper = new ItemTouchHelper(callback);
            mItemTouchHelper.attachToRecyclerView(recyclerView);


        } else {

            updateMyCartFragment(false);

        }

        view.findViewById(R.id.start_shopping).setOnClickListener(
                new OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        CenterRepository.getCenterRepository().getListOfProductsInShoppingList().clear();

                        Utils.switchContent(R.id.frag_container,
                                Utils.HOME_FRAGMENT,
                                ((Activity_Pos) (getContext())),
                                AnimationType.SLIDE_UP);

                    }
                });

        // Handle Back press
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP
                        && keyCode == KeyEvent.KEYCODE_BACK) {

                    Utils.switchContent(R.id.frag_container,
                            Utils.HOME_FRAGMENT,
                            ((Activity_Pos) (getContext())),
                            AnimationType.SLIDE_UP);

                }
                return true;
            }
        });

        return view;
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }

}