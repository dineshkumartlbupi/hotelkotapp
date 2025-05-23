package com.saptrishi.hotelkotapp.view.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.saptrishi.hotelkotapp.R;
import com.saptrishi.hotelkotapp.model.entities.productlistview;

import java.util.ArrayList;
import java.util.List;

public class Itemlistviewtable_Adapter extends RecyclerView.Adapter<Itemlistviewtable_Adapter.ViewHolder> {
//    int Quantity = 0;
//    int Amount = 0;
    Context context;
    private ArrayList<productlistview> courseModalArrayList;
    ProgressDialog progressDialog;

    public Itemlistviewtable_Adapter(Context context,ArrayList<productlistview>courseModalArrayList ) {
        this.courseModalArrayList = courseModalArrayList;
        this.context = context;
}



    @NonNull
    @Override
    public Itemlistviewtable_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemproduct_view, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull Itemlistviewtable_Adapter.ViewHolder holder,  int position) {
        try {
            productlistview modal = courseModalArrayList.get(position);
            holder.Amountname.setText("Rs " +modal.getAmount());
            holder.Itemnames.setText(modal.getITEMNAME());
            holder.Qty.setText(""+modal.getQty());
            holder.ItemDescription.setText( modal.getItemDescription() );
//            holder.Rates.setText(courseModalArrayList.get(position).getRate());
            // Toast.makeText( context, ""+sdf, Toast.LENGTH_SHORT).show();
            // Toast.makeText( context, "sdf",sdf, Toast.LENGTH_SHORT ).show();
//            String a += modal.getQty();

//            String str = modal.getQty(); // Replace this with your input string
//            int intqtyy = Integer.parseInt(str);
//
//            String st = modal.getAmount();
//            int intamount = Integer.parseInt( st );
//
//            Quantity = Quantity+intqtyy;
//            Amount = Amount+intamount;





        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return courseModalArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView Amountname,Itemnames,Qty,Rates;
        TextView ItemDescription;
        TextView total_amount,total_qty;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            Amountname = (TextView) itemView.findViewById(R.id.item_prices);
            Itemnames = (TextView) itemView.findViewById(R.id.item_names);
            Qty =  (TextView) itemView.findViewById(R.id.iteam_amounts);
            ItemDescription =  (TextView) itemView.findViewById(R.id.item_short_desc1);
            total_amount = (TextView)itemView.findViewById( R.id.total_amount );
            total_qty = (TextView)itemView.findViewById( R.id.total_qty );


        }
    }


}

//}


