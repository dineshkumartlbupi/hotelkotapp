package com.saptrishi.hotelkotapp.view.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.saptrishi.hotelkotapp.R;
import com.saptrishi.hotelkotapp.util.IpServiceChecker;
import com.saptrishi.hotelkotapp.view.activities.Activity_Pos;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static com.android.volley.toolbox.Volley.newRequestQueue;
import static org.acra.ACRA.log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class MyDialogFragment extends DialogFragment {


    int[] to = {R.id.res_name, R.id.departcode};
    String[] from = {"WaiterName","WaiterCode"};
    ArrayList<HashMap<String,String>> al=new ArrayList<HashMap<String, String>>();
    String url;
    RequestQueue rq;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
                return super.onCreateDialog(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.activity_waiter_list, container, false);


        ListView lv =(ListView) v.findViewById(R.id.lvwaiterList);


        final SimpleAdapter sa = new SimpleAdapter(getActivity().getApplicationContext(), al, R.layout.restuarant_list, from, to);
        lv.setAdapter(sa);






        SharedPreferences sf=getActivity().getSharedPreferences("Restaurant",Context.MODE_PRIVATE);
        String DepartCode=sf.getString("DepartCode","");
        String loginid=sf.getString("loginId","");
        log.e("test1",loginid);
        log.e("test1",DepartCode);

//        url = "http://"+IpServiceChecker.getIpaddress()+"/"+IpServiceChecker.getAppName()+"/Kanpur_HotelKotApp_Service.svc/WaiterApp/RestCode/"+DepartCode+"/logid/"+loginid+"";

        url = "http://"+IpServiceChecker.getIpaddress()+"/"+IpServiceChecker.getAppName()+"/Kanpur_HotelKotApp_Service.svc/WaiterApp/RestCode/"+DepartCode.trim()+"";

        Log.e("departcode",url);

        rq = newRequestQueue(getActivity().getApplicationContext());

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {


                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObject = (JSONObject) response.get(i);


                        String WaiterName = jsonObject.getString("WaiterName");
                        String WaiterCode = jsonObject.getString("WaiterCode");

                        if(jsonObject.getString("errormessage").equals("Login Detail Did Not Match For Restaurant View"))
                        {
                            log.e("HELLO1",WaiterName);
                            WaiterName="No Waiter";

                        }


                        HashMap<String, String> hm1 = new HashMap<String, String>();

                        hm1.put("WaiterCode", WaiterCode);
                        hm1.put("WaiterName", WaiterName);
                        al.add(hm1);


                    }
                    sa.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();

                }

            }
        }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getContext(), volleyError + "", Toast.LENGTH_SHORT).show();


            }
        });

        rq.add(jsonArrayRequest);


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String chk=al.get(i).get("WaiterName");
                log.e("CHECKAA",chk);
                if(chk.equals("No Waiter"))
                {
                    Toast.makeText(getContext(), "Try Again....", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    String psui=al.get(i).get("WaiterCode");
                    SharedPreferences sf=getActivity().getSharedPreferences("Restaurant",Context.MODE_PRIVATE);
                    SharedPreferences.Editor ed=sf.edit();
                    ed.putString("WaiterCode",psui);

                    ed.apply();
                    Log.e("waiter date",psui+"");

                    if (sf.getString("ActivityName","").equals("RestaurantList"))
                    {
                        Intent intent=new Intent(getActivity(),Activity_Pos.class);
                        startActivity(intent);
                    }
                    dismiss();

                }



            }
        });
        return v;

    }


    @Override
    public void onResume() {
        super.onResume();

        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        //params.width = ConstraintLayout.LayoutParams.WRAP_CONTENT;
        //params.height = ConstraintLayout.LayoutParams.150dp;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);



//        boolean setFullScreen = false;
//
//        if (setFullScreen)
//            setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
    }


}
