package com.wang.widget;

import android.content.Context;

import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;


import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wang.library.R;
import com.wang.widget.bean.CalendarBean;
import com.wang.widget.bean.PointTimeBean;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * Created by Administrator on 2018/11/20 0020.
 */

public class CalendarView extends RelativeLayout implements CalendarAction {
    private String TAG = CalendarView.class.getSimpleName();
    Context mContext;
    RecyclerView mRecycler;
    CalendarAdapter mAdapter;
    LinearLayout.LayoutParams layoutParams;
    private List<CalendarBean> calendarBeans = new ArrayList<>();

    private int currentYear, currentMonth, currentDay;

    public CalendarView(Context context) {
        this(context, null);
    }

    public CalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.calendar_layout, this);
        mRecycler = findViewById(R.id.mRecycler);
        mRecycler.setLayoutManager(new GridLayoutManager(context, 7));
        mAdapter = new CalendarAdapter(context, calendarBeans);
        mRecycler.setAdapter(mAdapter);
        currentYear = CalendarUtil.getYear(Calendar.getInstance());
        currentMonth = CalendarUtil.getMonth(Calendar.getInstance());
        currentDay = CalendarUtil.getDay(Calendar.getInstance());
        layoutParams = (LinearLayout.LayoutParams) mRecycler.getLayoutParams();
        initDate();
    }


    public void setGestureListener() {
        // 是要监听的视图 mAlertImageViewD

        mRecycler.setOnTouchListener(new OnTouchListener() {
            float mPosX, mPosY, mCurPosX, mCurPosY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mPosX = event.getX();
                        mPosY = event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        mCurPosX = event.getX();
                        mCurPosY = event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        if (mCurPosX - mPosX > 0
                                && (Math.abs(mCurPosX - mPosX) > 25)) {
                            //向左滑動
                            Log.i(TAG, " --- 向左");
                            lastMonth();
                        } else if (mCurPosX - mPosX < 0
                                && (Math.abs(mCurPosX - mPosX) > 25)) {
                            //向右滑动
                            Log.i(TAG, " --- 向右");
                            nextMonth();
                        }
                        break;
                }
                return true;
            }

        });
    }

    private void initDate() {
        Log.i(TAG, "=======================" + currentYear + "-" + currentMonth + "==============================");
        Calendar fristCalendar = CalendarUtil.getFristDayForMonth(currentYear, currentMonth);
        Calendar lastCalendar = CalendarUtil.getLastDayForMonth(currentYear, currentMonth);

        int monthDays = CalendarUtil.getDaysCountOfMonth(currentYear, currentMonth);
        int fristWeek = CalendarUtil.getWeekday(currentYear, currentMonth, 1);
        int finalWeek = CalendarUtil.getWeekday(currentYear, currentMonth, CalendarUtil.getDay(lastCalendar));

        calendarBeans.clear();
        fillingFristDay(currentYear, currentMonth, fristWeek);
        fillingCenterDay(currentYear, currentMonth, monthDays);
        fillingFinallyDay(currentYear, currentMonth, finalWeek);
        int row = calendarBeans.size() / 7;
        int height = DensityUtil.dip2px(getContext(), 45 * row);
        layoutParams.height = height;
        mRecycler.setLayoutParams(layoutParams);

//        Log.i(TAG, currentYear + " 0 " + currentMonth + " , size: " + calendarBeans.size() + " , 行数： " + row);
//        String times = "";
//        for (CalendarBean calendarBean : calendarBeans) {
//            times = times + " ；" + CalendarUtil.covertToString(calendarBean.year, calendarBean.month, calendarBean.day);
//        }
//        Log.i(TAG, "calendarBean :" + times);
        mAdapter.setData(calendarBeans);
    }

    private void fillingFinallyDay(int year, int month, int week) {
        if (week == 0) {
            if (calendarBeans.size() != 42) {
                if (month == 12) {
                    year++;
                    month = 1;
                } else {
                    month++;
                }
                for (int i = 1; i <= 7; i++) {
                    CalendarBean bean = new CalendarBean();
                    bean.year = year;
                    bean.month = month;
                    bean.day = i;
                    bean.isCenterDay = false;
                    calendarBeans.add(bean);
                }
            }
            return;
        }
        if (month == 12) {
            year++;
            month = 1;
        } else {
            month++;
        }

        int len = 7 - week;
        for (int i = 1; i <= len; i++) {
            CalendarBean bean = new CalendarBean();
            bean.year = year;
            bean.month = month;
            bean.day = i;
            bean.isCenterDay = false;
            calendarBeans.add(bean);
        }

        if (calendarBeans.size() != 42) {
            for (int i = len + 1; i <= len + 7; i++) {
                CalendarBean bean = new CalendarBean();
                bean.year = year;
                bean.month = month;
                bean.day = i;
                bean.isCenterDay = false;
                calendarBeans.add(bean);
            }
        }
    }

    private void fillingCenterDay(int year, int month, int monthDays) {
        for (int i = 1; i <= monthDays; i++) {
            CalendarBean bean = new CalendarBean();
            bean.year = year;
            bean.month = month;
            bean.day = i;
            bean.isCenterDay = true;
            calendarBeans.add(bean);
        }
    }

    // 1,2,3,4,5,6,0
    // 一,二,三,四,五,六,日
    private void fillingFristDay(int year, int month, int week) {
        Log.i(TAG, year + "-" + month + " , 最后一天周 " + week);
        if (week == 1) { // week == 0 周一
            return;
        } else if (week == 0) {
            week = 7;
        }
        if (month == 1) {
            year = year - 1;
            month = 12;
        } else {
            month--;
        }

        int lastDays = CalendarUtil.getDaysCountOfMonth(year, month);
        Log.i(TAG, month + " 月天数 = " + lastDays + " , 最后一天周 " + (week - 1));
        for (int i = (week - 2); i >= 0; i--) {
            CalendarBean bean = new CalendarBean();
            bean.year = year;
            bean.month = month;
            bean.day = lastDays - i;
            bean.isCenterDay = false;
            calendarBeans.add(bean);
        }
    }


    public int getColor(int color) {
        return ContextCompat.getColor(mContext, color);
    }

    public void setPointDays(List<PointTimeBean> days) {
        mAdapter.setPointDays(days);
    }

    public void lastMonth() {
        if (currentMonth == 1) {
            currentYear--;
            currentMonth = 12;
        } else {
            currentMonth--;
        }
        currentDay = 1;
        initDate();
    }

    public void nextMonth() {
        if (currentMonth == 12) {
            currentYear++;
            currentMonth = 1;
        } else {
            currentMonth++;
        }
        currentDay = 1;
        initDate();
    }

    public void setEnableSelectDay(boolean enable) {
        mAdapter.setEnableSelectDay(enable);
    }

    public void setOnDayClickLinstener(OnDayClickLinstener linstener) {
        mAdapter.setOnDayClickLinstener(linstener);
    }

    public void setTime(int year, int month) {
        currentYear = year;
        currentMonth = month;
        initDate();
    }

    public int[] getCurrentYM() {
        return new int[]{currentYear, currentMonth};
    }

    public int[] getTime() {
        return mAdapter.getTime();
    }
}


