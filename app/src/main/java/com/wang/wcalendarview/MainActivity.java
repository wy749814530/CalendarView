package com.wang.wcalendarview;

import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

import com.wang.widget.CalendarUtil;
import com.wang.widget.OnDayClickLinstener;
import com.wang.widget.bean.PointTimeBean;
import com.wang.widget.SlideCalendarView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    TextView tvTitle;
    SlideCalendarView slideCalendarView;
    private String TAG = "MainActivity";

    private Calendar currentCalendar;
    int currentYear;
    int currentMonth;
    List<PointTimeBean> pointDayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        slideCalendarView = findViewById(R.id.slideCalendarView);
        tvTitle = findViewById(R.id.tv_title);
        currentCalendar = Calendar.getInstance();
        currentYear = CalendarUtil.getYear(currentCalendar);
        currentMonth = CalendarUtil.getMonth(currentCalendar);

        String monthKey = DateUtil.getStringDateByLong(System.currentTimeMillis(), DateUtil.DEFAULT_yyyy_MM_FORMAT);
        tvTitle.setText(monthKey);

        tvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new CalendarDialog(MainActivity.this).show();
            }
        });


        slideCalendarView.setOnDayClickLinstener(new OnDayClickLinstener() {
            @Override
            public void onDayClick(int year, int month, int day) {
                Log.i(TAG, year + ", " + month + " , " + day);
            }

            @Override
            public void onYearMonthChanged(int year, int month) {
//                Log.i(TAG, year + ", " + month);
                tvTitle.setText(String.format("%02d:%02d", year, month));
            }
        });

        pointDayList.add(new PointTimeBean(2020, 9, 1));

        pointDayList.add(new PointTimeBean(2020, 8, 7));
        pointDayList.add(new PointTimeBean(2020, 8, 10));
        pointDayList.add(new PointTimeBean(2020, 8, 12));
        pointDayList.add(new PointTimeBean(2020, 8, 14));
        pointDayList.add(new PointTimeBean(2020, 8, 17));

        pointDayList.add(new PointTimeBean(2020, 7, 7));
        pointDayList.add(new PointTimeBean(2020, 7, 10));
        pointDayList.add(new PointTimeBean(2020, 6, 12));
        pointDayList.add(new PointTimeBean(2020, 6, 14));
        pointDayList.add(new PointTimeBean(2020, 5, 17));

        slideCalendarView.setPointDays(pointDayList);
    }


}
