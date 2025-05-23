/*
 * Copyright (c) 2017. http://hiteshsahu.com- All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * If you use or distribute this project then you MUST ADD A COPY OF LICENCE
 * along with the project.
 *  Written by Hitesh Sahu <hiteshkrsahu@Gmail.com>, 2017.
 */

package com.saptrishi.hotelkotapp.domain.api;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.saptrishi.hotelkotapp.R;
import com.saptrishi.hotelkotapp.domain.mock.FakeWebServer;
import com.saptrishi.hotelkotapp.util.AppConstants;
import com.saptrishi.hotelkotapp.util.Utils;
import com.saptrishi.hotelkotapp.util.Utils.AnimationType;
import com.saptrishi.hotelkotapp.view.activities.ECartHomeActivity;
import com.saptrishi.hotelkotapp.view.adapter.CategoryListAdapter;
import com.saptrishi.hotelkotapp.view.adapter.CategoryListAdapter.OnItemClickListener;
import com.saptrishi.hotelkotapp.view.fragment.ProductOverviewFragment;

/**
 * The Class ImageLoaderTask.
 */
public class ProductCategoryLoaderTask extends AsyncTask<String, Void, Void> {

    private static final int NUMBER_OF_COLUMNS = 2;
    private Context context;
    private RecyclerView recyclerView;

    public ProductCategoryLoaderTask(RecyclerView listView, Context context) {

        this.recyclerView = listView;
        this.context = context;
    }

    @Override
    protected void onPreExecute() {

        super.onPreExecute();

        //if (null != ((ECartHomeActivity) context).getProgressBar())
         //   ((ECartHomeActivity) context).getProgressBar().setVisibility(
          //          View.VISIBLE);

    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);

//        if (null != ((ECartHomeActivity) context).getProgressBar())
//            ((ECartHomeActivity) context).getProgressBar().setVisibility(View.GONE);

        if (recyclerView != null) {
            CategoryListAdapter simpleRecyclerAdapter = new CategoryListAdapter(
                    context);

//            recyclerView.setAdapter(simpleRecyclerAdapter);

            simpleRecyclerAdapter
                    .SetOnItemClickListener(new OnItemClickListener() {

                        @Override
                        public void onItemClick(View view, int position) {

                            AppConstants.CURRENT_CATEGORY = position;

//                            Utils.switchFragmentWithAnimation(
//                                    R.id.frag_container,
//                                    new ProductOverviewFragment(),
//                                    ((ECartHomeActivity) context), null,
//                                    AnimationType.SLIDE_LEFT);

                        }
                    });
        }

    }

    @Override
    protected Void doInBackground(String... params) {

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //FakeWebServer.getFakeWebServer().addCategory();

        return null;
    }

}