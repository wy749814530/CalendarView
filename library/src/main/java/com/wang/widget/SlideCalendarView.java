package com.wang.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import androidx.viewpager.widget.ViewPager;

import com.wang.library.R;
import com.wang.widget.bean.PointTimeBean;
import com.wang.widget.frament.ViewPageAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by WYU on 2020/8/31.
 * Describe
 */
public class SlideCalendarView extends RelativeLayout {
    private String TAG = getClass().getSimpleName();
    RelativeLayout.LayoutParams layoutParams;
    AutoViewPager mViewPager;
    List<CalendarView> calendarList = new ArrayList<>();
    List<PointTimeBean> pointDayList = new ArrayList<>();
    ViewPageAdapter mAdapter;
    private Calendar currentCalendar;
    int currentYear;
    int currentMonth;

    public SlideCalendarView(Context context) {
        this(context, null);
    }

    public SlideCalendarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlideCalendarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (attrs != null) {

        }
        LayoutInflater.from(context).inflate(R.layout.view_alide_calendar, this);
        mViewPager = findViewById(R.id.view_pager);
        layoutParams = (RelativeLayout.LayoutParams) mViewPager.getLayoutParams();
        currentCalendar = Calendar.getInstance();
        currentYear = CalendarUtil.getYear(currentCalendar);
        currentMonth = CalendarUtil.getMonth(currentCalendar);


        if (currentMonth == 1) {
            CalendarView calendarView = new CalendarView(getContext());
            calendarView.setTime(currentYear - 1, 12);
            calendarList.add(calendarView);
        } else {
            CalendarView calendarView = new CalendarView(getContext());
            calendarView.setTime(currentYear, currentMonth - 1);
            calendarList.add(calendarView);
        }
        CalendarView calendarViewc = new CalendarView(getContext());
        calendarViewc.setTime(currentYear, currentMonth);
        calendarList.add(calendarViewc);
        if (currentMonth == 12) {
            CalendarView calendarView = new CalendarView(getContext());
            calendarView.setTime(currentYear + 1, 1);
            calendarList.add(calendarView);
        } else {
            CalendarView calendarView = new CalendarView(getContext());
            calendarView.setTime(currentYear, currentMonth + 1);
            calendarList.add(calendarView);
        }
        mAdapter = new ViewPageAdapter(calendarList);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(1);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            int currentPosition = 1;

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentPosition = position;
                int[] currentYMD = calendarList.get(position).getCurrentYM();
                Log.i(TAG, currentYMD[0] + "" + currentYMD[1]);
                if (mLinstener != null) {
                    mLinstener.onYearMonthChanged(currentYMD[0], currentYMD[1]);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                // ViewPager.SCROLL_STATE_IDLE 标识的状态是当前页面完全展现，并且没有动画正在进行中，如果不
                // 是此状态下执行 setCurrentItem 方法回在首位替换的时候会出现跳动！
                Log.i(TAG, "state = " + state);
                if (state != ViewPager.SCROLL_STATE_IDLE) return;
                Log.i(TAG, "currentPosition = " + currentPosition);
                // 当视图在第一个时，将页面号设置为图片的最后一张。
                if (currentPosition == 0) {
                    Log.i(TAG, "左 - -");
                    for (CalendarView pagerFragment : calendarList) {
                        pagerFragment.lastMonth();
                    }
                    mViewPager.setCurrentItem(1/*calendarList.size() - 2*/, false);
                    Log.i(TAG, "====");
                } else if (currentPosition == calendarList.size() - 1) {
                    Log.i(TAG, "右 - -");
                    // 当视图在最后一个是,将页面号设置为图片的第一张。
                    for (CalendarView pagerFragment : calendarList) {
                        pagerFragment.nextMonth();
                    }
                    mViewPager.setCurrentItem(1, false);
                }
            }
        });
    }

    private OnDayClickLinstener mLinstener;

    public void setOnDayClickLinstener(OnDayClickLinstener linstener) {
        mLinstener = linstener;
        for (CalendarView calendarView : calendarList) {
            calendarView.setOnDayClickLinstener(linstener);
        }
    }

    public void setPointDays(List<PointTimeBean> days) {
        pointDayList.clear();
        if (days != null) {
            pointDayList.addAll(days);
        }

        for (CalendarView calendarView : calendarList) {
            calendarView.setPointDays(pointDayList);
        }
    }
}