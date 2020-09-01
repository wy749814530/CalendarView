package com.wang.wcalendarview;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowMetrics;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.wang.widget.CalendarUtil;
import com.wang.widget.OnDayClickLinstener;
import com.wang.widget.SlideCalendarView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by WYU on 2020/8/31.
 * Describe
 */
public class CalendarDialog extends Dialog implements OnDayClickLinstener {
    private String TAG = "MainActivity";
    private OnDayClickLinstener mListener;
    private SlideCalendarView slideCalendarView;
    private TextView tvTitleTime;
    private Calendar currentCalendar;
    int currentYear;
    int currentMonth;

    public CalendarDialog(@NonNull Context context) {
        this(context, R.style.Theme_dialog);
    }

    public CalendarDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        setContentView(R.layout.dialog_calendar);
        slideCalendarView = findViewById(R.id.slideCalendarView);
        tvTitleTime = findViewById(R.id.tv_title_time);

        findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        findViewById(R.id.tv_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dismiss();
            }
        });

        currentCalendar = Calendar.getInstance();
        currentYear = CalendarUtil.getYear(currentCalendar);
        currentMonth = CalendarUtil.getMonth(currentCalendar);

        tvTitleTime.setText(CalendarUtil.covertToString(currentYear, currentMonth, 0));
        slideCalendarView.setOnDayClickLinstener(this);

        setCancelable(true);
        setCanceledOnTouchOutside(true);

        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.gravity = Gravity.BOTTOM;
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
    }

    public CalendarDialog setOnDayClickLinstener(OnDayClickLinstener linstener) {
        mListener = linstener;
        return this;
    }

    @Override
    public void onDayClick(int year, int month, int day) {
        if (mListener != null) {
            mListener.onDayClick(year, month, day);
        }
    }

    @Override
    public void onYearMonthChanged(int year, int month) {
        currentYear = year;
        currentMonth = month;
        tvTitleTime.setText(CalendarUtil.covertToString(currentYear, currentMonth, 0));
        if (mListener != null) {
            mListener.onYearMonthChanged(year, month);
        }
    }
}