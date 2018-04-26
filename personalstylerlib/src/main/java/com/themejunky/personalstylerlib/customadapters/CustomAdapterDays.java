package com.themejunky.personalstylerlib.customadapters;

import android.app.Activity;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.themejunky.personalstylerlib.R;
import com.themejunky.personalstylerlib.bases.tools.Tools;
import com.themejunky.personalstylerlib.bases.tools.model.NewSchedule;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CustomAdapterDays extends RecyclerView.Adapter<CustomAdapterDays.DealsViewHolder> implements View.OnClickListener {

    private WeakReference<Activity> mActivity;
    private List<NewSchedule> mDays;
    private GradientDrawable mActiveItem = new GradientDrawable() , mInActiveItem = new GradientDrawable();
    private int textColorActive, textColorInactive;
    private MainScheduleFragmentAdapterDays_Interface mListener;

    private int mPrevSelected = 0;

    public interface MainScheduleFragmentAdapterDays_Interface {
        void onNewDayClick(String nDate);
    }

    public CustomAdapterDays(WeakReference<Activity> nActivity, MainScheduleFragmentAdapterDays_Interface nListener) {
        this.mActivity = nActivity;

        mDays = new ArrayList<>();
        Calendar mCalendar = Calendar.getInstance();
        for (int i = 0; i < 600; i++) {
            mDays.add(new NewSchedule(mCalendar, Tools.getInstance(nActivity.get())));
            mCalendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        mDays.get(0).mActive = true;


        this.mListener = nListener;

        mActiveItem.setColor(mActivity.get().getResources().getColor(R.color.Schedule_active_background));
        int mRadius = 10;
        mActiveItem.setCornerRadius(mRadius);
        int mStroke = 2;
        mActiveItem.setStroke(mStroke, mActivity.get().getResources().getColor(R.color.Schedule_active_border));

        mInActiveItem.setColor(mActivity.get().getResources().getColor(R.color.Schedule_inactive_background));
        mInActiveItem.setCornerRadius(mRadius);
        mInActiveItem.setStroke(mStroke, mActivity.get().getResources().getColor(R.color.Schedule_inactive_border));

        textColorActive = mActivity.get().getResources().getColor(R.color.Schedule_active_text);
        textColorInactive = mActivity.get().getResources().getColor(R.color.Schedule_inactive_text);

        notifyDataSetChanged();
    }

    public List<NewSchedule> getDaysList() {
        return mDays;
    }

    public void setDefaultPosition(int nDefaultPosition) {
        mPrevSelected = nDefaultPosition;
    }

    @Override
    public CustomAdapterDays.DealsViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        return new DealsViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_schedule_days, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(CustomAdapterDays.DealsViewHolder holder, int position) {
        final NewSchedule item = mDays.get(position);

        holder.mContainer.setTag(R.id.schedule_day_position,position);
        holder.mContainer.setTag(R.id.schedule_day_date,item.mCalendarSave);

        if (!item.mActive) {
            holder.mContainer.setBackground(mInActiveItem);
            holder.mDayNumber.setTextColor(textColorInactive);
            holder.mDayWeek.setTextColor(textColorInactive);
        } else {
            holder.mContainer.setBackground(mActiveItem);
            holder.mDayNumber.setTextColor(textColorActive);
            holder.mDayWeek.setTextColor(textColorActive);
        }

        holder.mDayNumber.setText(String.valueOf(item.mDay));
        holder.mDayWeek.setText(item.mDayWeek);

        holder.mContainer.setOnClickListener(this);
    }

    @Override
    public void onClick(View nView) {
        mDays.get(mPrevSelected).mActive = false;

        int mCurrentPosition = Integer.parseInt(String.valueOf(nView.getTag(R.id.schedule_day_position)));

        notifyItemChanged(mPrevSelected);
        mDays.get(mCurrentPosition).mActive = true;

        mPrevSelected = mCurrentPosition;
        notifyItemChanged(mCurrentPosition);

        mListener.onNewDayClick(nView.getTag(R.id.schedule_day_date).toString());
    }

    protected static class DealsViewHolder extends RecyclerView.ViewHolder {
        TextView mDayNumber,mDayWeek;
        LinearLayout mContainer;


        DealsViewHolder(View itemView) {
            super(itemView);
            mContainer = itemView.findViewById(R.id.nContainer);
            mDayNumber = itemView.findViewById(R.id.nDayNumber);
            mDayWeek = itemView.findViewById(R.id.nDayWeek);
        }
    }

    @Override
    public int getItemCount() {
        return mDays.size();
    }
}