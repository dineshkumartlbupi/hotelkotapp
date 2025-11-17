package com.saptrishi.hotelkotapp.view.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
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

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class OrderedKOTFragment extends Fragment {

    private static final String TAG = "OrderedKOTFragment";

    int[] to = {R.id.tv_kotnumber, R.id.name, R.id.quantity, R.id.rate, R.id.amount};
    String[] from = {"Sr_no", "name", "quantity", "rate", "amount"};

    TextView totalCost;
    ArrayList<HashMap<String, String>> kotList = new ArrayList<>();

    public OrderedKOTFragment() {
        // Required empty constructor
    }

    public static OrderedKOTFragment newInstance(String param1, String param2) {
        OrderedKOTFragment fragment = new OrderedKOTFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_ordered_kot, container, false);

        Log.d(TAG, "Fragment Loaded");

        totalCost = view.findViewById(R.id.kotTotalAmount);

        // Table Number
        TextView kotHeading = view.findViewById(R.id.kotHeading);
        SharedPreferences sp = requireActivity().getSharedPreferences("Restaurant", Context.MODE_PRIVATE);
        String tableNumber = sp.getString("tableName", "");
        kotHeading.setText("Table Number: " + tableNumber);

        // Pull Down
        view.findViewById(R.id.pullDown).setVisibility(View.VISIBLE);
        view.findViewById(R.id.pullDown).setOnTouchListener((v, event) -> {
            Utils.switchFragmentWithAnimation(
                    R.id.frag_container,
                    new HomeFragment(),
                    (Activity_Pos) getContext(),
                    Utils.HOME_FRAGMENT,
                    Utils.AnimationType.SLIDE_DOWN);
            return false;
        });

        ListView listView = view.findViewById(R.id.kotListView);

        loadKOTData();

        SimpleAdapter adapter = new SimpleAdapter(
                getActivity(),
                kotList,
                R.layout.kot_list,
                from,
                to
        );

        listView.setAdapter(adapter);

        return view;
    }

    private void loadKOTData() {
        MySqliteDatabase db = new MySqliteDatabase(getActivity());
        Cursor cursor = db.fetchkotListTable();

        double totalAmount = 0.0;
        System.out.println("cursor : "+cursor);
        if (cursor != null && cursor.moveToFirst()) {

            // Header row
            HashMap<String, String> header = new HashMap<>();
            header.put("Sr_no", "Sr.no");
            header.put("name", "Name");
            header.put("quantity", "Qty");
            header.put("rate", "Rate(" + getString(R.string.Rs) + ")");
            header.put("amount", "Amount(" + getString(R.string.Rs) + ")");
            kotList.add(header);

            int i = 1;

            do {
                HashMap<String, String> row = new HashMap<>();
                row.put("Sr_no", String.valueOf(i));
                row.put("name", cursor.getString(1));
                row.put("rate", "Rate (" + getString(R.string.Rs) + ") " + cursor.getString(4));
                row.put("quantity", "Qty " + cursor.getString(3));
                row.put("amount", "Amt (" + getString(R.string.Rs) + ") " + cursor.getString(2));

                totalAmount += Double.parseDouble(cursor.getString(2));

                kotList.add(row);
                i++;

            } while (cursor.moveToNext());

        }

        DecimalFormat df = new DecimalFormat("0.00");
        totalCost.setText("Total: " + getString(R.string.Rs) + " " + df.format(totalAmount));

        Log.d(TAG, "Total Amount: " + df.format(totalAmount));
    }
}
