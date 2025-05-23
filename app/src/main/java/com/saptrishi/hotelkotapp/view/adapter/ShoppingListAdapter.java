/*
 * Copyright (c) 2017. http://hiteshsahu.com- All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * If you use or distribute this project then you MUST ADD A COPY OF LICENCE
 * along with the project.
 *  Written by Hitesh Sahu <hiteshkrsahu@Gmail.com>, 2017.
 */

package com.saptrishi.hotelkotapp.view.adapter;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.text.Editable;
import android.text.Spannable;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.StrikethroughSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TextView.BufferType;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.saptrishi.hotelkotapp.R;
import com.saptrishi.hotelkotapp.model.CenterRepository;
import com.saptrishi.hotelkotapp.model.MySqliteDatabase;
import com.saptrishi.hotelkotapp.model.entities.Money;
import com.saptrishi.hotelkotapp.model.entities.Product;
import com.saptrishi.hotelkotapp.util.ColorGenerator;
import com.saptrishi.hotelkotapp.util.Utils;
import com.saptrishi.hotelkotapp.view.activities.Activity_Pos;
import com.saptrishi.hotelkotapp.view.activities.ECartHomeActivity;
import com.saptrishi.hotelkotapp.view.customview.ItemTouchHelperAdapter;
import com.saptrishi.hotelkotapp.view.customview.ItemTouchHelperViewHolder;
import com.saptrishi.hotelkotapp.view.customview.OnStartDragListener;
import com.saptrishi.hotelkotapp.view.customview.TextDrawable;
import com.saptrishi.hotelkotapp.view.customview.TextDrawable.IBuilder;
import com.saptrishi.hotelkotapp.view.fragment.MyCartFragment;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Simple RecyclerView.Adapter that implements {@link ItemTouchHelperAdapter} to
 * respond to move and dismiss events from a
 * {@link android.support.v7.widget.helper.ItemTouchHelper}.
 *
 * @author Hitesh Sahu (hiteshsahu.com)
 */
public class ShoppingListAdapter extends
        RecyclerView.Adapter<ShoppingListAdapter.ItemViewHolder> implements
        ItemTouchHelperAdapter {

    //private static OnItemClickListener clickListener;
    private final OnStartDragListener mDragStartListener;
    private ColorGenerator mColorGenerator = ColorGenerator.MATERIAL;
    private IBuilder mDrawableBuilder;
    private TextDrawable drawable;
    private String ImageUrl;
    public List<Product> productList = new ArrayList<Product>();
    private Context context;
    View view;
    ItemViewHolder holder1;
    MySqliteDatabase db=new MySqliteDatabase(context);
    public ShoppingListAdapter(Context context,
                               OnStartDragListener dragStartListener) {
        mDragStartListener = dragStartListener;

        this.context = context;

        productList = CenterRepository.getCenterRepository().getListOfProductsInShoppingList();
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_product_list, parent, false);
        ItemViewHolder itemViewHolder = new ItemViewHolder(view);
        return itemViewHolder;
    }

    //EditText et;
    @Override
    public void onBindViewHolder(final ItemViewHolder holder, final int position) {
        holder1 = holder;

        holder.itemName.setText(productList.get(position).getItemName());

//        holder.itemDesc.setText(productList.get(position).getItemShortDesc());

        String sellCostString = Money.rupees(
                BigDecimal.valueOf(Double.parseDouble(productList.get(position)
                        .getSellMRP()))).toString()
                + "  ";

        String buyMRP = Money.rupees(
                BigDecimal.valueOf(Double.parseDouble(productList.get(position)
                        .getMRP()))).toString();

      String cost=  (Double.parseDouble(productList.get(position)
                .getSellMRP()))+"";
        Log.e("sellCostString3",cost);
        String costString = sellCostString + buyMRP;

        Log.e("sellCostString1",sellCostString);
        Log.e("sellCostString2",buyMRP);

//        CenterRepository.getCenterRepository().getListOfProductsInShoppingList().get(position).getItemShortDesc();

        holder.edit_itemremark.setText( productList.get(position).getItemShortDesc());

        holder.edit_itemremark.setText(holder.edit_itemremark.getText().toString().trim());

        holder.quanitity.setText(CenterRepository.getCenterRepository()
                .getListOfProductsInShoppingList().get(position).getQuantity());

        holder.itemCost.setText(costString, BufferType.SPANNABLE);

        Spannable spannable = (Spannable) holder.itemCost.getText();

        spannable.setSpan(new StrikethroughSpan(), sellCostString.length(),
                costString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        mDrawableBuilder = TextDrawable.builder().beginConfig().withBorder(4)
                .endConfig().roundRect(10);

        drawable = mDrawableBuilder.build(String.valueOf(productList
                .get(position).getItemName().charAt(0)), mColorGenerator
                .getColor(productList.get(position).getItemName()));

        //ImageUrl = productList.get(position).getImageURL();




        //et= (EditText) view.findViewById(R.id.item_short_desc);

//holder.edit_itemremark.addTextChangedListener(new TextWatcher() {
//    @Override
//    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//    }
//
//    @Override
//    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
////        getItemCount().get(getAdapterPosition()).setEditTextValue(et.getText().toString());
//
////        productList.get(position).setItemShortDesc(charSequence.toString().trim());
//
//
//        Log.e("itemDescsla", charSequence.toString().trim());
//        try {
////            CenterRepository.getCenterRepository().getListOfProductsInShoppingList().get(position).setItemShortDesc(charSequence.toString().trim());
////            holder.edit_itemremark.setText(holder.edit_itemremark.getText().toString().trim());
//            MySqliteDatabase db = new MySqliteDatabase(context);
//            db.updateRestaurantTable(CenterRepository
//                    .getCenterRepository().getListOfProductsInShoppingList()
//                    .get(position).getProductId(), holder.edit_itemremark.getText().toString().trim());
//        } catch (Exception ex) {
//            Log.e("Exception", "Array index out of Bound");
//        }
//
//    }
//
//    @Override
//    public void afterTextChanged(Editable editable) {
////        Log.e("itemeditable",editable.toString().trim());
////        try {
////            CenterRepository.getCenterRepository().getListOfProductsInShoppingList().get(position).setItemShortDesc(holder.edit_itemremark.getText().toString().trim());
////
//////            CenterRepository
//////                    .getCenterRepository()
//////                    .getListOfProductsInShoppingList()
//////                    .get(position)
//////                    .setItemShortDesc(holder.edit_itemremark.getText().toString().trim());
////
//////            productList.get(position).setItemShortDesc(editable.toString().trim());
////
////            Log.e("itemCenterRepository",  CenterRepository
////                            .getCenterRepository()
////                            .getListOfProductsInShoppingList()
////                            .get(position)
////                            .getItemShortDesc());
////
////            Log.e("itemDesproductList",  productList.get(position).getItemShortDesc());
////
//////            holder.edit_itemremark.setText(holder.edit_itemremark.getText().toString().trim());
//////            holder.edit_itemremark.setText(CenterRepository.getCenterRepository()
//////                         .getListOfProductsInShoppingList().get(position).getItemShortDesc());
////            MySqliteDatabase db = new MySqliteDatabase(context);
////            db.updateRestaurantTable(CenterRepository
////                    .getCenterRepository().getListOfProductsInShoppingList()
////                    .get(position).getProductId(), holder.edit_itemremark.getText().toString().trim());
////        } catch (Exception ex) {
////            Log.e("Exception", "Array index out of Bound");
////        }
//
//    }
//});
        holder.edit_itemremark.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
//                 holder.edit_itemremark.setText(CenterRepository.getCenterRepository()
//                         .getListOfProductsInShoppingList().get(position).getItemShortDesc());

                Log.e("itemDesc", holder.edit_itemremark.getText().toString().trim());
                try {
                    //CenterRepository.getCenterRepository().getListOfProductsInShoppingList().get(position).setItemShortDesc(holder.edit_itemremark.getText().toString().trim());
                    //holder.itemDesc.setText(holder.edit_itemremark.getText().toString().trim());
                    MySqliteDatabase db = new MySqliteDatabase(context);
                    db.updateRestaurantTable(CenterRepository
                            .getCenterRepository().getListOfProductsInShoppingList()
                            .get(position).getProductId(), holder.edit_itemremark.getText().toString().trim());
                } catch (Exception ex) {
                    Log.e("Exception", "Array index out of Bound");
                }


//                 et.getText().toString();
//                 CenterRepository
//                         .getCenterRepository()
//                         .getListOfProductsInShoppingList()
//                         .get(position).setItemShortDesc(edit_itemremark.getText().toString().trim());
            }
        });


        // holder.itemDesc.setText(et.getText().toString());

//        Glide.with(context).load(ImageUrl).placeholder(drawable)
//                .error(drawable).animate(R.anim.base_slide_right_in)
//                .centerCrop().into(holder.imagView);

        // Start a drag whenever the handle view it touched
//        holder.imagView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
//                    mDragStartListener.onStartDrag(holder);
//                }
//                return false;
//            }
//        });

        holder.addItem.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                CenterRepository
                        .getCenterRepository()
                        .getListOfProductsInShoppingList()
                        .get(position)
                        .setQuantity(
                                String.valueOf(
                                        Integer.parseInt(CenterRepository
                                                .getCenterRepository().getListOfProductsInShoppingList()
                                                .get(position).getQuantity()) + 1));


                holder.quanitity.setText(CenterRepository.getCenterRepository()
                        .getListOfProductsInShoppingList().get(position).getQuantity());

                Utils.vibrate(context);

                ((Activity_Pos) context).updateCheckOutAmount(
                        BigDecimal.valueOf(Double.parseDouble(CenterRepository
                                .getCenterRepository().getListOfProductsInShoppingList()
                                .get(position).getSellMRP())), true);
                Log.e("msg11111111", CenterRepository
                        .getCenterRepository().getListOfProductsInShoppingList()
                        .get(position).getProductId());
                long amount = (long) (Long.parseLong(CenterRepository
                                        .getCenterRepository().getListOfProductsInShoppingList()
                                        .get(position).getQuantity()) * Double.parseDouble(CenterRepository
                                        .getCenterRepository().getListOfProductsInShoppingList()
                                        .get(position).getSellMRP()));

                String amt = amount + "";

                MySqliteDatabase db = new MySqliteDatabase(context);
                db.updateRestaurantTable(CenterRepository
                        .getCenterRepository().getListOfProductsInShoppingList()
                        .get(position).getProductId(), CenterRepository
                        .getCenterRepository().getListOfProductsInShoppingList()
                        .get(position).getQuantity(), amt);

            }
        });

        holder.removeItem.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {


                if (Integer.parseInt(CenterRepository.getCenterRepository()
                        .getListOfProductsInShoppingList().get(position).getQuantity()) == 1) {
                    Toast.makeText(context, "Kindly swipe to remove this item", Toast.LENGTH_SHORT).show();
                } else {
                    if (Integer.parseInt(CenterRepository.getCenterRepository()
                            .getListOfProductsInShoppingList().get(position).getQuantity()) > 1) {

                        CenterRepository
                                .getCenterRepository()
                                .getListOfProductsInShoppingList()
                                .get(position)
                                .setQuantity(
                                        String.valueOf(

                                                Integer.parseInt(CenterRepository
                                                        .getCenterRepository()
                                                        .getListOfProductsInShoppingList().get(position)
                                                        .getQuantity()) - 1));

                        holder.quanitity.setText(CenterRepository
                                .getCenterRepository().getListOfProductsInShoppingList()
                                .get(position).getQuantity());

                        ((Activity_Pos) context).updateCheckOutAmount(
                                BigDecimal.valueOf(Double.parseDouble(CenterRepository
                                        .getCenterRepository().getListOfProductsInShoppingList()
                                        .get(position).getSellMRP())), false);

                        Utils.vibrate(context);
                    } else if (Integer.parseInt(CenterRepository.getCenterRepository()
                            .getListOfProductsInShoppingList().get(position).getQuantity()) == 1) {
                        ((Activity_Pos) context).updateItemCount(false);

                        ((Activity_Pos) context).updateCheckOutAmount(
                                BigDecimal.valueOf(Double.parseDouble(CenterRepository
                                        .getCenterRepository().getListOfProductsInShoppingList()
                                        .get(position).getSellMRP())), false);

                        CenterRepository.getCenterRepository().getListOfProductsInShoppingList()
                                .remove(position);

                        if (((Activity_Pos) context)
                                .getItemCount() == 0) {

                            MyCartFragment.updateMyCartFragment(false);

                        }

                        Utils.vibrate(context);

                    }

                    Log.e("msg11111111", CenterRepository
                            .getCenterRepository().getListOfProductsInShoppingList()
                            .get(position).getProductId());
                    long amount = (long) (Long.parseLong(CenterRepository
                                                .getCenterRepository().getListOfProductsInShoppingList()
                                                .get(position).getQuantity()) * Double.parseDouble(CenterRepository
                                                .getCenterRepository().getListOfProductsInShoppingList()
                                                .get(position).getSellMRP()));

                    String amt = amount + "";

                    MySqliteDatabase db = new MySqliteDatabase(context);
                    db.updateRestaurantTable(CenterRepository
                            .getCenterRepository().getListOfProductsInShoppingList()
                            .get(position).getProductId(), CenterRepository
                            .getCenterRepository().getListOfProductsInShoppingList()
                            .get(position).getQuantity(), amt);

                }
            }
        });
    }

    @Override
    public void onItemDismiss(int position) {

        ((Activity_Pos) context).updateItemCount(false);

        ((Activity_Pos) context).updateCheckOutAmount(
                BigDecimal.valueOf(Double.parseDouble(CenterRepository
                        .getCenterRepository().getListOfProductsInShoppingList().get(position)
                        .getSellMRP()) * Long.valueOf(CenterRepository.getCenterRepository().getListOfProductsInShoppingList().get(position).getQuantity())), false);



        //CenterRepository.getCenterRepository().getListOfProductsInShoppingList().get(position+1).setItemShortDesc("");

        //((Activity_Pos) context).updateItemdesc(CenterRepository.getCenterRepository().getListOfProductsInShoppingList().get(position+1).getItemShortDesc(),position);

        //Log.e("remove_itemdesc",""+CenterRepository.getCenterRepository().getListOfProductsInShoppingList().get(position).getItemShortDesc());
//        notifyItemChanged(position,CenterRepository.getCenterRepository().getListOfProductsInShoppingList().get(position).getItemShortDesc());
//        holder1.edit_itemremark.setVisibility(View.GONE);

        //holder1.itemDesc.setText(CenterRepository.getCenterRepository().getListOfProductsInShoppingList().remove(position).getItemShortDesc());


//        ((Activity_Pos) context).updateItemdesc(
//                CenterRepository.getCenterRepository().getListOfProductsInShoppingList().get(position).getItemShortDesc());

//        holder1.edit_itemremark;
//        CenterRepository.getCenterRepository().getListOfProductsInShoppingList().get(position).setItemShortDesc("");
//        int amt = Integer.parseInt(CenterRepository.getCenterRepository().getListOfProductsInShoppingList().get(position).getSellMRP()) * Integer.parseInt(CenterRepository.getCenterRepository().getListOfProductsInShoppingList().get(position).getQuantity());
//        Log.e("hello11111111111111v1", CenterRepository.getCenterRepository().getListOfProductsInShoppingList().get(position).getSellMRP());
//        Log.e("hello11111111111111v2", amt + ""); holder.quanitity.setText(CenterRepository.getCenterRepository()
//                        .getListOfProductsInShoppingList().get(position).getQuantity());

        MySqliteDatabase db = new MySqliteDatabase(context);

        db.deleteRestaurantTableOnID(CenterRepository
                .getCenterRepository().getListOfProductsInShoppingList().get(position).getProductId());

//        MySqliteDatabase mySqliteDatabase=new MySqliteDatabase(context);
//        Cursor cursor=mySqliteDatabase.fetchrestTableonItemId(CenterRepository
//                .getCenterRepository().getListOfProductsInShoppingList().get(position).getProductId());

//        db.updateRestaurantTable(CenterRepository
//                .getCenterRepository().getListOfProductsInShoppingList()
//                .get(position).getProductId(), CenterRepository
//                .getCenterRepository().getListOfProductsInShoppingList()
//                .get(position).getItemShortDesc());

        CenterRepository.getCenterRepository().getListOfProductsInShoppingList().remove(position);
        //CenterRepository.getCenterRepository().getListOfProductsInShoppingList().get(position).getItemShortDesc();
        if (((Activity_Pos) context).getItemCount() == 0) {

//            CenterRepository
//                    .getCenterRepository()
//                    .getListOfProductsInShoppingList()
//                    .get(position)
//                    .setQuantity("0");


            ((Activity_Pos) context).updateCheckOutAmount(
                    BigDecimal.valueOf(0), false);


            db.deleteRestaurantTable();
//            CenterRepository.getCenterRepository().getListOfProductsInShoppingList().clear();
            MyCartFragment.updateMyCartFragment(false);

        }
//        notifyItemRemoved(position);
        Utils.vibrate(context);

        notifyDataSetChanged();

//        productList.remove(position);

//         productList.remove(position);


//        db.deleteRestaurantTableOnID(CenterRepository
//                .getCenterRepository().getListOfProductsInShoppingList().get(position).getProductId());
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {

//        Collections.swap(productList, fromPosition, toPosition);
//        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public int getItemCount() {
        return productList.size();

    }

//    public void SetOnItemClickListener(
//            final OnItemClickListener itemClickListener) {
//        this.clickListener = itemClickListener;
//    }

//    public interface OnItemClickListener {
//        public void onItemClick(View view, int position);
//    }

    /**
     * Simple example of a view holder that implements
     * {@link ItemTouchHelperViewHolder} and has a "handle" view that initiates
     * a drag event when touched.
     */
    public static class ItemViewHolder extends RecyclerView.ViewHolder
            implements ItemTouchHelperViewHolder {

        // public final ImageView handleView;

        TextView itemName, itemDesc, itemCost, availability, quanitity,
                addItem, removeItem;
        EditText edit_itemremark;
        ImageView imagView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            // handleView = (ImageView) itemView.findViewById(R.id.handle);

            itemName = (TextView) itemView.findViewById(R.id.item_name);

            //itemDesc = (TextView) itemView.findViewById(R.id.item_short_desc);

            itemCost = (TextView) itemView.findViewById(R.id.item_price);

            availability = (TextView) itemView
                    .findViewById(R.id.iteam_avilable);

            quanitity = (TextView) itemView.findViewById(R.id.iteam_amount);

            itemName.setSelected(true);
//            edit_itemremark = (EditText) itemView.findViewById(R.id.item_short_des);
            //imagView = ((ImageView) itemView.findViewById(R.id.product_thumb));

            addItem = ((TextView) itemView.findViewById(R.id.add_item));

            removeItem = ((TextView) itemView.findViewById(R.id.remove_item));
             edit_itemremark = (EditText) itemView.findViewById(R.id.item_short_des);
            edit_itemremark.setVisibility(View.VISIBLE);
            String item = edit_itemremark.getText().toString();
            edit_itemremark.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


//                    editModelArrayList.get(getAdapterPosition()).setEditTextValue(editText.getText().toString());

                    Log.e("itemDescsla", charSequence.toString().trim());
                    try {

                        CenterRepository.getCenterRepository().getListOfProductsInShoppingList().get(getAdapterPosition()).setItemShortDesc(charSequence.toString().trim());
//            holder.edit_itemremark.setText(holder.edit_itemremark.getText().toString().trim());
                        String name=charSequence.toString().trim();
//                        datasendtodb(name);

                    } catch (Exception ex) {
                        Log.e("Exception", "Array index out of Bound");
                    }
                }



                @Override
                public void afterTextChanged(Editable editable) {

                }
            });




            //itemView.setOnClickListener(this);
        }

        @Override
        public void onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY);
        }

        @Override
        public void onItemClear() {
            itemView.setBackgroundColor(0);
        }

//        @Override
//        public void onClick(View v) {
//
//            clickListener.onItemClick(v, getPosition());
//        }


    }
//    private void datasendtodb(String name) {
//
//        MySqliteDatabase db = new MySqliteDatabase(context);
//        db.updateRestaurantTable(CenterRepository
//                .getCenterRepository().getListOfProductsInShoppingList()
//                .get(getAdapterPosition()).getProductId(), charSequence.toString().trim());
//    }
}
