package com.wang.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
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

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.BaseViewHolder> {
    private String TAG = CalendarAdapter.class.getSimpleName();
    private List<PointTimeBean> hadAlarmMap = new ArrayList<>();
    private CalendarBean currentCalendar;
    private List<CalendarBean> dataList = new ArrayList<>();
    private Context mContext;
    private CalendarBean selectCalendar;
    private boolean enableSelectDay = true;

    public CalendarAdapter(Context context, List<CalendarBean> data) {
        mContext = context;
        int currentYear = CalendarUtil.getYear(Calendar.getInstance());
        int currentMonth = CalendarUtil.getMonth(Calendar.getInstance());
        int currentDay = CalendarUtil.getDay(Calendar.getInstance());
        currentCalendar = new CalendarBean();
        currentCalendar.year = currentYear;
        currentCalendar.month = currentMonth;
        currentCalendar.day = currentDay;
        selectCalendar = currentCalendar;
        selectCalendar.isSelect = true;
        dataList.clear();
        if (data != null) {
            dataList.addAll(data);
        }
    }

    public String getYmdKey(CalendarBean calendar) {
        return String.format("%02d-%02d-%02d", calendar.year, calendar.month, calendar.day);
    }

    public int[] getTime() {
        int time[] = {selectCalendar.year, selectCalendar.month, selectCalendar.day};
        return time;
    }

    public void setPointDays(List<PointTimeBean> days) {
        hadAlarmMap.clear();
        hadAlarmMap.addAll(days);
        notifyDataSetChanged();
    }

    private OnDayClickLinstener mLinstener;

    public void setOnDayClickLinstener(OnDayClickLinstener linstener) {
        mLinstener = linstener;
    }

    public void setEnableSelectDay(boolean enableSelectDay) {
        this.enableSelectDay = enableSelectDay;
    }

    public void setData(List<CalendarBean> data) {
        dataList.clear();
        if (data != null) {
            dataList.addAll(data);
        }
        notifyDataSetChanged();
    }

    public CalendarBean getItem(int position) {
        if (position >= 0 && position < dataList.size()) {
            return dataList.get(position);
        }
        return null;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_calendar, parent, false);
        return new BaseViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder baseViewHolder, final int position) {
        CalendarBean calendar = dataList.get(position);
        baseViewHolder.setText(R.id.tv_day, calendar.day + "");
        String key = getYmdKey(calendar);// 2020-09-26
        if (hasPointTime(key)) {
            baseViewHolder.setVisible(R.id.point_view, true);
        } else {
            baseViewHolder.setVisible(R.id.point_view, false);
        }

        if (calendar.year == selectCalendar.year && calendar.month == selectCalendar.month && calendar.day == selectCalendar.day) {
            baseViewHolder.setTextColor(R.id.tv_day, ContextCompat.getColor(mContext, R.color.text_Color));
            baseViewHolder.setVisible(R.id.view_circle, true);
        } else {
            baseViewHolder.setVisible(R.id.view_circle, false);
            if (calendar.year == currentCalendar.year && calendar.month == currentCalendar.month && calendar.day == currentCalendar.day) {
                //
                baseViewHolder.setTextColor(R.id.tv_day, ContextCompat.getColor(mContext, R.color.blue_color));
            } else {
                if (calendar.isCenterDay) {
                    baseViewHolder.setTextColor(R.id.tv_day, ContextCompat.getColor(mContext, R.color.text_Color));
                } else {
                    baseViewHolder.setTextColor(R.id.tv_day, ContextCompat.getColor(mContext, R.color.gray_color));
                }
            }
        }

        baseViewHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (enableSelectDay) {
                    selectCalendar = getItem(position);
                    notifyDataSetChanged();
                    if (mLinstener != null) {
                        mLinstener.onDayClick(selectCalendar.year, selectCalendar.month, selectCalendar.day);
                    }
                }
            }
        });
    }

    public static class BaseViewHolder extends RecyclerView.ViewHolder {
        public BaseViewHolder(@NonNull View itemView) {
            super(itemView);

        }

        public void setText(int id, String text) {
            TextView textView = itemView.findViewById(id);
            textView.setText(text);
        }

        public void setTextColor(int id, int color) {
            TextView textView = itemView.findViewById(id);
            textView.setTextColor(color);
        }

        public void setVisible(int id, boolean visibility) {
            itemView.findViewById(id).setVisibility(visibility ? View.VISIBLE : View.GONE);
        }

        public void setOnClickListener(View.OnClickListener listener) {
            itemView.findViewById(R.id.item_click_lay).setOnClickListener(listener);
        }
    }

    public boolean hasPointTime(String ymdKey) {
        for (PointTimeBean pointTimeBean : hadAlarmMap) {
            if (pointTimeBean.containsKey(ymdKey)) {
                return true;
            }
        }

        return false;
    }
}
