package com.ziyad.recruitingspring.utility;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class JSON {
    public static List<String> jsonToList(JSONArray jsonArray) {
        List<String> list = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            list.add(jsonArray.getString(i));
        }

        return list;
    }
}
