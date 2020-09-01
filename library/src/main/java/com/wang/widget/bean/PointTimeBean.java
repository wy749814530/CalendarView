package com.wang.widget.bean;

import android.text.TextUtils;
import android.widget.TextView;

public class PointTimeBean {
    public String yyyyMMdd = "";
    public int day;
    public int month;
    public int year;

    public PointTimeBean(int year, int month, int day) {
        this.day = day;
        this.month = month;
        this.year = year;
        yyyyMMdd = String.format("%04d-%02d-%02d", year, month, day);
    }

    public boolean containsKey(String ymdKey) {
        if (TextUtils.isEmpty(yyyyMMdd) || TextUtils.isEmpty(ymdKey)) {
            return false;
        }

        if (yyyyMMdd.equals(ymdKey)) {
            return true;
        }

        return false;
    }
}
