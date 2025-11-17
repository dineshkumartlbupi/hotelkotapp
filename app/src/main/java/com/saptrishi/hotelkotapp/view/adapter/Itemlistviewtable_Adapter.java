package com.saptrishi.hotelkotapp.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.saptrishi.hotelkotapp.R;
import com.saptrishi.hotelkotapp.model.entities.productlistview;

import java.util.ArrayList;

public class Itemlistviewtable_Adapter extends RecyclerView.Adapter<Itemlistviewtable_Adapter.ViewHolder> {

    Context context;
    private ArrayList<productlistview> courseModalArrayList;

    public Itemlistviewtable_Adapter(Context context, ArrayList<productlistview> courseModalArrayList) {
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
    public void onBindViewHolder(@NonNull Itemlistviewtable_Adapter.ViewHolder holder, int position) {
        try {
            productlistview modal = courseModalArrayList.get(position);

            holder.Itemnames.setText(modal.getITEMNAME());
            String desc = modal.getItemDescription();
            holder.ItemDescription.setText(
                    desc != null && !desc.isEmpty()
                            ? "Description: " + desc
                            : "Description not found"
            );

            holder.Qty.setText("Qty: " + modal.getQty());
            holder.Rate.setText("Rate: Rs " + modal.getRate());
            holder.Amountname.setText("Amount: Rs " + modal.getAmount());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return courseModalArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView Amountname, Itemnames, Qty, Rate, ItemDescription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            Itemnames = itemView.findViewById(R.id.item_names);
            ItemDescription = itemView.findViewById(R.id.item_short_desc1);
            Qty = itemView.findViewById(R.id.iteam_amounts);
            Rate = itemView.findViewById(R.id.item_rate);
            Amountname = itemView.findViewById(R.id.item_prices);
        }
    }
}
