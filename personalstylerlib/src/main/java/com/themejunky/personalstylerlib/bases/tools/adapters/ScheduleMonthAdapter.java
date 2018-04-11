package com.themejunky.personalstylerlib.bases.tools.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.themejunky.personalstylerlib.R;
import com.themejunky.personalstylerlib.bases.tools.model.NewScheduleMonthDays;
import com.themejunky.personalstylerlib.utils.Constants;

import java.util.List;

public class ScheduleMonthAdapter extends RecyclerView.Adapter<ScheduleMonthAdapter.DealsViewHolder> {

    private List<NewScheduleMonthDays> mMonthDays;
    private Context mContext;
    private int mCurrentClickedPosition = -1;

    public ScheduleMonthAdapter(Context nContext) {
        this.mContext = nContext;
    }

    public void setData(List<NewScheduleMonthDays> nMonthDays) {
        this.mMonthDays = nMonthDays;
        mCurrentClickedPosition = -1;
        notifyDataSetChanged();
    }

    public int getCurrentSelection() {
        return mCurrentClickedPosition;
    }

    @Override
    public ScheduleMonthAdapter.DealsViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        return new ScheduleMonthAdapter.DealsViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_schedule_month, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(ScheduleMonthAdapter.DealsViewHolder holder, final int position) {
        final NewScheduleMonthDays item = mMonthDays.get(position);

        holder.mCurrent.setVisibility(View.GONE);

        switch (item.mDayType) {
            case Constants.SCHEDULE_MONTH_DAY_TYPE_HEADER:
                holder.mHeader.setVisibility(View.VISIBLE);
                holder.mHeader.setText(item.mHead);
                break;
            case Constants.SCHEDULE_MONTH_DAY_TYPE_BEFORE:
                holder.mBefore.setVisibility(View.VISIBLE);
                holder.mBefore.setText(String.valueOf(item.mDay));
                break;
            case Constants.SCHEDULE_MONTH_DAY_TYPE_CURRENT:
                holder.mCurrent.setVisibility(View.VISIBLE);
                holder.mCurrent.setText(String.valueOf(item.mDay));
                holder.mCurrent.setBackground(null);
                holder.mCurrent.setTextColor(mContext.getResources().getColor(R.color.Schedule_MonthPicker_current));
                holder.mCurrent.setTag(String.valueOf(position));
                holder.mCurrent.setOnClickListener(new DayClick());
                break;
        }
    }

    static class DealsViewHolder extends RecyclerView.ViewHolder {
        TextView mHeader, mBefore, mCurrent;
        RelativeLayout mContainer;

        DealsViewHolder(View itemView) {
            super(itemView);
            mHeader = itemView.findViewById(R.id.nHeader);
            mBefore = itemView.findViewById(R.id.nBefore);
            mCurrent = itemView.findViewById(R.id.nCurrent);
            mContainer = itemView.findViewById(R.id.nContainer);
        }
    }

    @Override
    public int getItemCount() {
        return mMonthDays.size();
    }

    public class DayClick implements View.OnClickListener {

        @Override
        public void onClick(View nView) {
            /* if we have any click befor*/
            if (mCurrentClickedPosition > 0) {
                notifyItemChanged(mCurrentClickedPosition);
            }
            /* save the current clicked position */
            mCurrentClickedPosition = Integer.parseInt(nView.getTag().toString());
            /* color the view - press state */
            nView.setBackground(mContext.getResources().getDrawable(R.drawable.text_view_circle_violet));
            ((TextView) nView).setTextColor(mContext.getResources().getColor(R.color.base_white));
        }
    }
}
