/*
 * Copyright (c) 2017. http://hiteshsahu.com- All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * If you use or distribute this project then you MUST ADD A COPY OF LICENCE
 * along with the project.
 *  Written by Hitesh Sahu <hiteshkrsahu@Gmail.com>, 2017.
 */

package com.saptrishi.hotelkotapp.view.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
//import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TextView.BufferType;
import android.widget.Toast;

import com.android.volley.Cache;
import com.saptrishi.hotelkotapp.R;
import com.saptrishi.hotelkotapp.model.CenterRepository;
import com.saptrishi.hotelkotapp.model.MySqliteDatabase;
import com.saptrishi.hotelkotapp.model.entities.Money;
import com.saptrishi.hotelkotapp.model.entities.Product;
import com.saptrishi.hotelkotapp.util.ColorGenerator;
import com.saptrishi.hotelkotapp.util.Utils;
import com.saptrishi.hotelkotapp.view.activities.Activity_Pos;
import com.saptrishi.hotelkotapp.view.customview.TextDrawable;
import com.saptrishi.hotelkotapp.view.customview.TextDrawable.IBuilder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.media.CamcorderProfile.get;

import androidx.recyclerview.widget.RecyclerView;

/**
 * @author Hitesh Sahu (hiteshsahu.com)
 */
public class ProductListAdapter extends
        RecyclerView.Adapter<ProductListAdapter.VersionViewHolder> implements
        ItemTouchHelperAdapter {

    private ColorGenerator mColorGenerator = ColorGenerator.MATERIAL;

    private IBuilder mDrawableBuilder;

    private TextDrawable drawable;

    private String ImageUrl;
    int itemnumber = 1;

    private List<Product> productList = new ArrayList<Product>();
    private OnItemClickListener clickListener;
    private Context context;


    public ProductListAdapter(String subcategoryKey, Context context, boolean isCartlist, CharSequence s) {
        Log.e( "SubCate_old", subcategoryKey );
        Log.e( "Context", "" + context );
        Log.e( "isCartlist", "" + isCartlist );

        if (isCartlist) {

            productList = CenterRepository.getCenterRepository().getListOfProductsInShoppingList();

        } else {
            if (s.length() > 0) {
                productList.clear();
               // ArrayList<Product> getShoppingList = new ArrayList<>();
                List<Product> productListNew = new ArrayList<Product>();
                productListNew = CenterRepository.getCenterRepository().getlistOfProductsAll();// productList = CenterRepository.getCenterRepository().getlistOfProductsAll();
                for (Product product : productListNew) {
                    if (product.getItemName().toLowerCase().contains( s.toString().toLowerCase() )) {
                        productList.add( product );
                       // getShoppingList.add( product );
                    }
                }
               // CenterRepository.getCenterRepository().setListOfProductsInShoppingList( getShoppingList );

            } else {
                productList = CenterRepository.getCenterRepository().getlistOfProductsAll();
            }


//            if(CenterRepository.getCenterRepository()
//                    .getListOfProductsInShoppingList().isEmpty()) {
//                //productList = CenterRepository.getCenterRepository().getMapOfProductsInCategory().get(subcategoryKey);
//                productList = CenterRepository.getCenterRepository().getlistOfProductsAll();
//            }
//            else
//            {
//                productList = CenterRepository.getCenterRepository()
//                        .getListOfProductsInShoppingList();
//            }
            Log.e( "SubCate_old_else", subcategoryKey );
            //Log.e("SubCate_old_else",""+productList.size());
        }

        this.context = context;
    }

    @Override
    public VersionViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from( viewGroup.getContext() ).inflate(
                R.layout.item_product_list, viewGroup, false );
        VersionViewHolder viewHolder = new VersionViewHolder( view );
        return viewHolder;


    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final VersionViewHolder holder,
                                 @SuppressLint("RecyclerView") final int position) {

        holder.itemnumber.setText( "" +  (position +1 ) + ".");


        holder.itemName.setText( productList.get( position )
                .getItemName() );

        holder.itemcode.setText( productList.get( position )
                .getProductId() );

        holder.quanitity.setText( productList.get( position )
                .getQuantity() );

      //  holder.textone.setText (productList.get( position ).getProductId() + productList.get( position ).getProductId());
     //   holder.textone.setText( "" +  (position +1 ) + ".");


//        holder.itemDesc.setText(productList.get(position)
//                .getItemShortDesc());

        //        String sellCostString = productList.get(position).getSellMRP();
        //        double sellMRP = Double.parseDouble(sellCostString);
        //
        //        double sellCostDouble = Double.parseDouble( sellMRP
        String sellCostString = String.format( "%s  ", Money.rupees(
                BigDecimal.valueOf(Double.parseDouble( productList.get( position )
                        .getSellMRP() ) ) ) ) ;

//        String buyMRP = Money.rupees(
//                BigDecimal.valueOf(Long.valueOf(productList.get(position)
//                        .getMRP()))).toString();

      //  + "  " );

        // String costString = sellCostString + buyMRP;
        String costString = sellCostString;

        holder.itemCost.setText( costString, BufferType.SPANNABLE );

        Spannable spannable = (Spannable) holder.itemCost.getText();

        spannable.setSpan( new StrikethroughSpan(), sellCostString.length(),
                costString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE );

        mDrawableBuilder = TextDrawable.builder().beginConfig().withBorder( 4 )
                .endConfig().roundRect( 10 );

        drawable = mDrawableBuilder.build( String.valueOf( productList
                .get( position ).getItemName().charAt( 0 ) ), mColorGenerator
                .getColor( productList.get( position ).getItemName() ) );

        //ImageUrl = productList.get(position).getImageURL();


//        Glide.with(context).load(ImageUrl).placeholder(drawable)
//                .error(drawable).animate(R.anim.base_slide_right_in)
//                .centerCrop().into(holder.imagView);
        final List<Product> s = CenterRepository.getCenterRepository()
                .getListOfProductsInShoppingList();

        holder.addItem.findViewById( R.id.add_item ).setOnClickListener(
                new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        //current object
                        Product tempObj = productList.get( position );

                        Log.e( "position", "in add123" + position );
                        Log.e( "s list", " " + s );
//                       Log.e("total values1",tempObj.getQuantity()+"");
                        //Log.e("checking values2",tempObj.getProductId()+"");
//                        Log.e("total values3",tempObj.getSellMRP()+"");


//                        Log.e("total values4",tempObj.getMRP()+"");
                        //if current object is lready in shopping list
                        if (s.contains( tempObj )) {

                            Log.e( "hello123", "in add123" );
                            //get position of current item in shopping list

                            int indexOfTempInShopingList = CenterRepository
                                    .getCenterRepository().getListOfProductsInShoppingList()
                                    .indexOf( tempObj );

                            // increase quantity of current item in shopping list

                            if (Integer.parseInt( tempObj.getQuantity() ) == 0) {

                                ((Activity_Pos) getContext())
                                        .updateItemCount( true );

                            }


                            // update quanity in shopping list


                            CenterRepository
                                    .getCenterRepository()
                                    .getListOfProductsInShoppingList()
                                    .get( indexOfTempInShopingList )
                                    .setQuantity(
                                            String.valueOf( Integer
                                                    .parseInt( tempObj
                                                            .getQuantity() ) + 1 ) );


                            //update checkout amount
                            ((Activity_Pos) getContext()).updateCheckOutAmount(
                                    BigDecimal
                                            .valueOf( Double.parseDouble( productList
                                                            .get( position )
                                                            .getSellMRP() ) ),
                                    true );

                            // update current item quanitity
                            holder.quanitity.setText( tempObj.getQuantity() );

                        } else {

//                            if(CenterRepository.getCenterRepository()
//                                    .getListOfProductsInShoppingList().isEmpty()){
//                                Log.e("checking cart id", tempObj.getProductId());
//                            }else{
//                                Log.e("checking tempObj", ""+tempObj);
//                                Log.e("checking SList Size", ""+ CenterRepository
//                                        .getCenterRepository().getListOfProductsInShoppingList().size());
//`
//
//                            }

                            if (shopinglistadd( tempObj, holder )) {
                                Log.e( "checking function", "true" );
                            } else {
                                //productList = CenterRepository.getCenterRepository().getlistOfProductsAll(); // shai hai
                                tempObj = productList.get( position );// error shantanu
                                MySqliteDatabase db = new MySqliteDatabase( getContext() );

                                Log.e( "checking id", tempObj.getProductId() );

                                ((Activity_Pos) getContext()).updateItemCount( true );

                                tempObj.setQuantity( String.valueOf( 1 ) );

                                Log.e( "checking Values", tempObj.getQuantity() + " tempObj " + tempObj );

                                holder.quanitity.setText( tempObj.getQuantity() );

                                CenterRepository.getCenterRepository()
                                        .getListOfProductsInShoppingList().add( tempObj );

                                ((Activity_Pos) getContext()).updateCheckOutAmount(
                                        BigDecimal
                                                .valueOf( Double.parseDouble( productList
                                                                .get( position )
                                                                .getSellMRP() ) ),
                                        true );
//                            Log.e("checking values", String.valueOf(BigDecimal
//                                    .valueOf(Long
//                                            .valueOf(productList
//                                                    .get(position)
//                                                    .getSellMRP()))));

                            }
                        }
                        Utils.vibrate( getContext() );

                        sqlTableInsertion( tempObj );
                    }
                } );

        holder.removeItem.setOnClickListener( new OnClickListener() {

            @Override
            public void onClick(View v) {

                Product tempObj = (productList).get( position );

                if (CenterRepository.getCenterRepository().getListOfProductsInShoppingList()
                        .contains( tempObj )) {

                    int indexOfTempInShopingList = CenterRepository
                            .getCenterRepository().getListOfProductsInShoppingList()
                            .indexOf( tempObj );

                    if (Integer.valueOf( tempObj.getQuantity() ) != 0) {

                        CenterRepository
                                .getCenterRepository()
                                .getListOfProductsInShoppingList()
                                .get( indexOfTempInShopingList )
                                .setQuantity(
                                        String.valueOf( Integer.valueOf( tempObj
                                                .getQuantity() ) - 1 ) );

                        ((Activity_Pos) getContext()).updateCheckOutAmount(
                                BigDecimal.valueOf( Double.parseDouble( productList
                                        .get( position ).getSellMRP() ) ),
                                false );

                        holder.quanitity.setText( CenterRepository
                                .getCenterRepository().getListOfProductsInShoppingList()
                                .get( indexOfTempInShopingList ).getQuantity() );

                        Utils.vibrate( getContext() );

                        if (Integer.valueOf( tempObj.getQuantity() ) == 0) {
//                            Toast.makeText(context, "remove item inside pla 271", Toast.LENGTH_SHORT).show();
                            ((Activity_Pos) getContext())
                                    .updateItemCount( false );
                            CenterRepository.getCenterRepository().getListOfProductsInShoppingList()
                                    .remove( indexOfTempInShopingList );

//
//
//                            CenterRepository.getCenterRepository()
//                                    .getListOfProductsInShoppingList()
//                                    .remove(indexOfTempInShopingList);
//
//                            notifyDataSetChanged();
//
//                            ((Activity_Pos) getContext())
//                                    .updateItemCount(false);
//
                        }

                    } else if (Integer.valueOf( tempObj.getQuantity() ) == 0) {
                        Toast.makeText( context, "remove item", Toast.LENGTH_SHORT ).show();
                    }

                }
                sqlTableInsertion( tempObj );

            }

        } );

    }

    public boolean shopinglistadd(Product tempObj, VersionViewHolder holder) {
        boolean listadd = false;
        try {
            //productList = CenterRepository.getCenterRepository().getListOfProductsInShoppingList();

            for (int i = 0; i < productList.size(); i++) {

                Log.e( "Checking Item", "" + CenterRepository.getCenterRepository()
                        .getListOfProductsInShoppingList().get( i ).getProductId() );
                if (CenterRepository.getCenterRepository()
                        .getListOfProductsInShoppingList().get( i ).getProductId().equals( tempObj.getProductId() )) {
                    Log.e( "checking pro id", tempObj.getProductId() );
                    tempObj = productList.get( i );
                    int indexOfTempInShopingList = CenterRepository
                            .getCenterRepository().getListOfProductsInShoppingList()
                            .indexOf( tempObj );
                    Log.e( "check indexShopingList", "" + indexOfTempInShopingList );
                    // increase quantity of current item in shopping list

                    if (Integer.parseInt( tempObj.getQuantity() ) == 0) {

                        ((Activity_Pos) getContext())
                                .updateItemCount( true );

                    }


                    // update quanity in shopping list


                    CenterRepository
                            .getCenterRepository()
                            .getListOfProductsInShoppingList()
                            .get( i )
                            .setQuantity(
                                    String.valueOf( Integer
                                            .valueOf( tempObj
                                                    .getQuantity() ) + 1 ) );

                    Log.e( "check getQuantity", "" + CenterRepository
                            .getCenterRepository()
                            .getListOfProductsInShoppingList()
                            .get( i ).getQuantity() );
                    //update checkout amount
                    ((Activity_Pos) getContext()).updateCheckOutAmount(
                            BigDecimal
                                    .valueOf( Double.parseDouble( productList
                                                    .get( i )
                                                    .getSellMRP() ) ),
                            true );

                    // update current item quanitity
                    holder.quanitity.setText( tempObj.getQuantity() );
                    Log.e( "check getQuantity", "" + tempObj.getQuantity() );
                    //productList = CenterRepository.getCenterRepository().getlistOfProductsAll();
                    //tempObj = productList.get(i);
                    listadd = true;

                }
//                                    int indexOfTempInShopingList = CenterRepository
//                                            .getCenterRepository().getListOfProductsInShoppingList()
//                                            .indexOf(tempObj);
//                                    Log.e("checking pro id", "" + indexOfTempInShopingList);
//                                    if (indexOfTempInShopingList > -1) {
//                                        Log.e("checking pro id", "" + indexOfTempInShopingList);
//                                        if (CenterRepository.getCenterRepository()
//                                                .getListOfProductsInShoppingList().get(indexOfTempInShopingList).getProductId().contains(tempObj.getProductId())) {
//                                            Log.e("checking pro id", tempObj.getProductId());
//                                        }
//                                    }
            }
        } catch (Exception exc) {
        }
        return listadd;
    }


    public void sqlTableInsertion(Product tempObj) {

        SharedPreferences sf = getContext().getSharedPreferences( "Restaurant", Context.MODE_PRIVATE );
        String RestCode = sf.getString( "DepartCode", "" );
        String U_name = sf.getString( "loginId", "" );
        String WaiterCode = sf.getString( "WaiterCode", "" );
        String tableName = sf.getString( "tableCode", "" );
        Log.e( "temp", tempObj.getQuantity() );
        long amount = (long) (Double.parseDouble( tempObj.getQuantity() ) * Double.parseDouble( tempObj.getSellMRP() ));

        String amt = amount + "";

        MySqliteDatabase db = new MySqliteDatabase( getContext() );
        Cursor c = db.fetchrestTableonItemId( tempObj.getProductId() );

        Log.e( "C", "" + c.getCount() );
        if (c.moveToFirst()) {
            Log.e( "C12", "" + c.getCount() );
            Log.e( "quantity", tempObj.getQuantity() + "" );
            if (tempObj.getQuantity().equals( "0" )) {
                db.deleteRestaurantTableOnID( tempObj.getProductId() );
            } else
                db.updateRestaurantTable( tempObj.getProductId(), tempObj.getQuantity(), amt );
        } else
            db.insertRestaurantTable( RestCode, tempObj.getProductId(), tempObj.getQuantity(), tempObj.getSellMRP(), amt, U_name, WaiterCode, tableName, tempObj.getItemShortDesc() );

        //c=db.fetchRestaurantTable();
//        if(c.moveToFirst())
//        {
//            do {
//                Log.e("checking table", String.valueOf(c.getString(4)));
//
//            }while(c.moveToNext());
//        }


    }


    private Activity_Pos getContext() {
        // TODO Auto-generated method stub
        return (Activity_Pos) context;
    }

    @Override
    public int getItemCount() {
        return productList == null ? 0 : productList.size();
    }

    public void SetOnItemClickListener(
            final OnItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }


    @Override
    public void onItemDismiss(int position) {
        productList.remove( position );
        notifyItemRemoved( position );
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap( productList, i, i + 1 );
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap( productList, i, i - 1 );
            }
        }
        notifyItemMoved( fromPosition, toPosition );
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    class VersionViewHolder extends RecyclerView.ViewHolder {
        TextView itemnumber, itemName, itemDesc, itemCost, availability, quanitity, itemcode,
                addItem, removeItem;
        ImageView imagView;




        public VersionViewHolder(View itemView) {
            super( itemView );

            itemnumber = (TextView) itemView.findViewById( R.id.item_number );
           // textone = String.valueOf( (TextView) itemView.findViewById( R.id.textone ) );
            itemName = (TextView) itemView.findViewById( R.id.item_name );


//            itemDesc = (TextView) itemView.findViewById(R.id.item_short_desc);

            itemCost = (TextView) itemView.findViewById( R.id.item_price );

            availability = (TextView) itemView.findViewById( R.id.iteam_avilable );
            itemcode = (TextView) itemView.findViewById( R.id.iteam_codeee );
            quanitity = (TextView) itemView.findViewById( R.id.iteam_amount );

            itemName.setSelected( true );

            //imagView = ((ImageView) itemView.findViewById(R.id.product_thumb));

            addItem = ((TextView) itemView.findViewById( R.id.add_item ));

            removeItem = ((TextView) itemView.findViewById( R.id.remove_item ));

//            itemDesc.setVisibility(VISIBLE);

//            itemView.setOnClickListener(this);


        }



//        @Override
//        public void onClick(View v) {
//            clickListener.onItemClick(v, getPosition());
//        }
    }

}
