package com.themejunky.personalstylerlib.customdialogs.hour;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.themejunky.personalstylerlib.R;

import java.util.List;

public class ScheduleTimeAdapter extends RecyclerView.Adapter<ScheduleTimeAdapter.DealsViewHolder>  {
    private List<ScheduleTimeModel> mTimeModel;

    ScheduleTimeAdapter(List<ScheduleTimeModel> nTimeModel) {
        this.mTimeModel = nTimeModel;
        notifyDataSetChanged();
    }

    @Override
    public ScheduleTimeAdapter.DealsViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        return new ScheduleTimeAdapter.DealsViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_schedule_time_dialog, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(ScheduleTimeAdapter.DealsViewHolder holder, int position) {
        final ScheduleTimeModel item = mTimeModel.get(position);
        if (item.mTime.equals("null")) {
            holder.mTime.setText("");
        } else {
            holder.mTime.setText(item.mTime);
        }
    }

    static class DealsViewHolder extends RecyclerView.ViewHolder {
        TextView mTime;

        DealsViewHolder(View itemView) {
            super(itemView);
            mTime = itemView.findViewById(R.id.nTime);
        }
    }

    @Override
    public int getItemCount() {
        return mTimeModel.size();

    }
}
