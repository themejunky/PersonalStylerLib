package com.themejunky.personalstylerlib.customdialogs.hour;

import android.content.Context;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.themejunky.personalstylerlib.R;
import com.themejunky.personalstylerlib.bases.tools.Tools;
import com.themejunky.personalstylerlib.customdialogs.BaseDialog;
import com.themejunky.personalstylerlib.utils.SpeedyLinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

public class ScheduleTimeDialog extends BaseDialog {
    private ScheduleTimeDialog_Interface mListener;
    private static ScheduleTimeDialog mInstance = null;

    public interface ScheduleTimeDialog_Interface {
        void onScheduleTimeDialog_Choose(String nTimeSet,View nView);
    }

    private View mView;

    private SpeedyLinearLayoutManager mManagerLeft,mManagerRight;
    private List<ScheduleTimeModel> mHoursModel,mMinuteModel;
    private String mCurrentTime[];
    private int mHourPosition,mMinutePosition;
    private RecyclerView mHoursList,mMinuteList;

    public static ScheduleTimeDialog getInstance() {
        if (mInstance == null)
            mInstance = new ScheduleTimeDialog();
        return mInstance;
    }

    private ScheduleTimeDialog() {}
    /**
     * Default constructor;
     * @param nContext - context used for this dialog
     * @param nView - contains information about the input (in getTag) : witch input ( start/end time interval ) and the time allready set
     */

    public void refreshContent(Context nContext, View nView, ScheduleTimeDialog_Interface nListener) {
        /* inits*/
        mContext = nContext;
        mListener = nListener;
        mBuilder = new AlertDialog.Builder(mContext);

        mView = nView;
        mTools = Tools.getInstance(mContext);
        mCurrentTime = nView.getTag(R.id.schedule_days_key_info).toString().split(":");

        /* create view*/
        createView(R.layout.custom_dialog_time);

        /*fetch views and inits*/
        mHoursList = mContainer.findViewById(R.id.nHours);
        mMinuteList = mContainer.findViewById(R.id.nMinute);
        ScheduleTimeAdapter mHourAdapter = new ScheduleTimeAdapter(setHourModel());
        ScheduleTimeAdapter mMinuteAdapter = new ScheduleTimeAdapter(setMinuteModel());
        mManagerLeft =  mTools.setList_GridLayoutManager_Vertical(mHoursList,mHourAdapter);
        mManagerRight =  mTools.setList_GridLayoutManager_Vertical(mMinuteList,mMinuteAdapter);

        /* listeners */
        mHoursList.addOnScrollListener(new ScheduleRecyclerListener(mManagerLeft));
        mMinuteList.addOnScrollListener(new ScheduleRecyclerListener(mManagerRight));
        mContainer.findViewById(R.id.nSave).setOnClickListener(this);

        showDialog();
    }

    /**
     * Create and show up current dialog
     */
    @Override
    public void showDialog() {
        super.showDialog();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable(){
            @Override
            public void run(){
                mHoursList.smoothScrollToPosition(mHourPosition+2);
                mMinuteList.smoothScrollToPosition(mMinutePosition+2);
            }
        }, 500);
    }

    /**
     * Prepare the model with "hours"
     * @return - model with all hours included at top and bottom with 2 "null" sequance
     */
    private List<ScheduleTimeModel> setHourModel() {
        mHoursModel = new ArrayList<>();
        String nHour;
        addNulls(mHoursModel);
        for (int i=0; i<=24;i++) {
            if (i<10){
                nHour="0"+i;
            } else {
                nHour = String.valueOf(i);
            }
            if (nHour.equals(mCurrentTime[0])) { mHourPosition = i; }
            mHoursModel.add(new ScheduleTimeModel(nHour));
        }
        addNulls(mHoursModel);
        return mHoursModel;
    }

    /**
     * Prepare the model with "minutes"
     * @return - model with all minute (0,15,30,45) included at top and bottom with 2 "null" sequance
     */
    private List<ScheduleTimeModel> setMinuteModel() {
        mMinuteModel = new ArrayList<>();
        addNulls(mMinuteModel);
        mMinuteModel.add(new ScheduleTimeModel("00"));
        mMinuteModel.add(new ScheduleTimeModel("15"));
        mMinuteModel.add(new ScheduleTimeModel("30"));
        mMinuteModel.add(new ScheduleTimeModel("45"));

        switch (mCurrentTime[1]) {
            case "00" : mMinutePosition = 0; break;
            case "15" : mMinutePosition = 1; break;
            case "30" : mMinutePosition = 2; break;
            case "45" : mMinutePosition = 3; break;
        }
        addNulls(mMinuteModel);
        return mMinuteModel;
    }

    /**
     * Add 2 "null" do be able to center last and fist hour/minute
     * @param mModel - list where to insert "null"'s
     */
    private void addNulls(List<ScheduleTimeModel> mModel) {
        mModel.add(new ScheduleTimeModel("null"));
    }

    public void onClick(View nView) {
        if (nView.getId()==R.id.nSave) {
            String nTime = mHoursModel.get(mManagerLeft.findFirstVisibleItemPosition()+1).mTime.concat(":").concat(mMinuteModel.get(mManagerRight.findFirstVisibleItemPosition()+1).mTime);
            mListener.onScheduleTimeDialog_Choose(nTime,mView);
            mDialog.dismiss();
        }
        else if (nView.getId()==R.id.nCancel) {
            mDialog.dismiss();
        }
    }
}
