package com.themejunky.personalstylerlib.customdialogs;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.themejunky.personalstylerlib.R;
import com.themejunky.personalstylerlib.bases.tools.Tools;
import com.themejunky.personalstylerlib.bases.tools.adapters.ScheduleMonthAdapter;

import java.util.Calendar;

public class CustonScheduleMonthDialog extends BaseDialog {

    private static CustonScheduleMonthDialog mInstance = null;
    private ScheduleMonthDialog_Interface mListener;

    public interface ScheduleMonthDialog_Interface {
        void onScheduleMonthDialog_Choose(long nSelectedDateInTimeMillis);
    }

    private int mCurrentMonthPosition;
    private ScheduleMonthAdapter mMonthAdapter;

    public static CustonScheduleMonthDialog getInstance() {
        if (mInstance == null)
            mInstance = new CustonScheduleMonthDialog();
        return mInstance;
    }
    private CustonScheduleMonthDialog() { }

    /**
     * By this method we refresh and trigger the pop-uo
     * @param nContext - context in witch the pop will be shown
     * @param nListener - listener that will respond
     */
    public void refreshContent(Context nContext, ScheduleMonthDialog_Interface nListener) {
        mTools = Tools.getInstance(nContext);
        /* inits*/
        mListener = nListener;
        mContext = nContext;
        mBuilder = new AlertDialog.Builder(mContext);

        mCurrentMonthPosition = 0 ;
        mTools.mNextDaySortedByMonth();
        /* create view*/
        createView(R.layout.custom_dialog_months);

        /*fetch views and inits*/
        mMonthAdapter = new ScheduleMonthAdapter(mContext);
        mTools.setList_GridLayoutColumnManager_Vertical((RecyclerView) mContainer.findViewById(R.id.nList),mMonthAdapter,7);

        /* listeners*/
        mContainer.findViewById(R.id.nLeftArrow).setOnClickListener(this);
        mContainer.findViewById(R.id.nRightArrow).setOnClickListener(this);
        mContainer.findViewById(R.id.nCancel).setOnClickListener(this);
        mContainer.findViewById(R.id.nSave).setOnClickListener(this);

        refreshInformation();
        showDialog();
    }

    @Override
    public void onClick(View nView) {
        if (nView.getId()==R.id.nLeftArrow) {
            loadPrevMonth();
        } else if (nView.getId()==R.id.nRightArrow) {
            loadNextMonth();
        } else if (nView.getId()==R.id.nCancel) {
            mDialog.dismiss();
        } else if (nView.getId()==R.id.nSave) {
            if (mMonthAdapter.getCurrentSelection()!=-1) {
                Calendar nCalendar = Calendar.getInstance();
                nCalendar.setTimeInMillis(mTools.mNextDaySortedByMonth().get(mCurrentMonthPosition).mCalendarSave);

                int day = mTools.mNextDaySortedByMonth().get(mCurrentMonthPosition).mDays.get( mMonthAdapter.getCurrentSelection()).mDay;
                nCalendar.set(Calendar.DAY_OF_MONTH,day);

               /* yee! we choose a new date */
                mListener.onScheduleMonthDialog_Choose(nCalendar.getTimeInMillis());
                mDialog.dismiss();
            }
        }
    }

    private void loadPrevMonth() {
        if (mCurrentMonthPosition-1>=0) {
            mCurrentMonthPosition--;
            refreshInformation();
        }
    }

    private void loadNextMonth() {
        if (mCurrentMonthPosition+1<mTools.mNextDaySortedByMonth().size()) {
            mCurrentMonthPosition++;
            refreshInformation();
        }
    }

    private void refreshInformation() {
        /* remove all the view from the grid to avoid item recycler bug*/
        ((RecyclerView) mContainer.findViewById(R.id.nList)).removeAllViews();
        /* set the current month to show in the grid */
        mMonthAdapter.setData(mTools.mNextDaySortedByMonth().get(mCurrentMonthPosition).mDays);
        /* set the title for the current selected month */
        ((TextView) mContainer.findViewById(R.id.nMonth)).setText(mTools.mNextDaySortedByMonth().get(mCurrentMonthPosition).mMonth);
        ((TextView) mContainer.findViewById(R.id.nYear)).setText(String.valueOf(mTools.mNextDaySortedByMonth().get(mCurrentMonthPosition).mYear));
    }
}