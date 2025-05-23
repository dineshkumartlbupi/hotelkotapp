package com.saptrishi.hotelkotapp.model.entities;

//import com.github.mikephil.charting.components.Description;

public class productlistview {

    // "Amount": "190",
    //        "ITEMNAME": "DAL FRY",
    //        "Qty": "1",
    //        "Rate": "190",
    //        "Unit": null,
    //        "errormessage": "Correct"
    private String Amount;
    private String ITEMNAME;
    private String Qty;
    private String Rate;
    private String ItemDescription;

    public productlistview(String amount, String ITEMNAME, String qty, String rate, String itemDescription) {
        Amount = amount;
        this.ITEMNAME = ITEMNAME;
        Qty = qty;
        Rate = rate;
        ItemDescription = itemDescription;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public String getITEMNAME() {
        return ITEMNAME;
    }

    public void setITEMNAME(String ITEMNAME) {
        this.ITEMNAME = ITEMNAME;
    }

    public String getQty() {
        return Qty;
    }

    public void setQty(String qty) {
        Qty = qty;
    }

    public String getRate() {
        return Rate;
    }

    public void setRate(String rate) {
        Rate = rate;
    }

    public String getItemDescription() {
        return ItemDescription;
    }

    public void setItemDescription(String itemDescription) {
        ItemDescription = itemDescription;
    }

    //
//    public productlistview(String amount, String ITEMNAME, String qty, String rate) {
//        Amount = amount;
//        this.ITEMNAME = ITEMNAME;
//        Qty = qty;
//        Rate = rate;
//    }
//
//
//
//    public String getAmount() {
//        return Amount;
//    }
//
//    public void setAmount(String amount) {
//        Amount = amount;
//    }
//
//    public String getITEMNAME() {
//        return ITEMNAME;
//    }
//
//    public void setITEMNAME(String ITEMNAME) {
//        this.ITEMNAME = ITEMNAME;
//    }
//
//    public String getQty() {
//        return Qty;
//    }
//
//    public void setQty(String qty) {
//        Qty = qty;
//    }
//
//    public String getRate() {
//        return Rate;
//    }
//
//    public void setRate(String rate) {
//        Rate = rate;
//    }
}