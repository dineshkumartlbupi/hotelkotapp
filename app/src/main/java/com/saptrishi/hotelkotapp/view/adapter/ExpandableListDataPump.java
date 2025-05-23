package com.saptrishi.hotelkotapp.view.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableListDataPump {
    public static HashMap<String, List<String>> getData() {
        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();

        List<String> cricket = new ArrayList<String>();
        cricket.add("Item 1 of 1");
        cricket.add("Item 2 of 1");
        cricket.add("Item 3 of 1");
        cricket.add("Item 4 of 1");
        cricket.add("Item 5 of 1");

        List<String> football = new ArrayList<String>();
        football.add("Item 1 of 2");
        football.add("Item 2 of 2");
        football.add("Item 3 of 2");
        football.add("Item 4 of 2");
        football.add("Item 5 of 2");

        List<String> basketball = new ArrayList<String>();
        basketball.add("Item 1 of 3");
        basketball.add("Item 2 of 3");
        basketball.add("Item 3 of 3");
        basketball.add("Item 4 of 3");
        basketball.add("Item 5 of 3");

        expandableListDetail.put("KOT Number 1", cricket);
        expandableListDetail.put("KOT Number 2", football);
        expandableListDetail.put("KOT Number 3", basketball);
        return expandableListDetail;
    }
}
