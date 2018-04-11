package com.themejunky.personalstylerlib.bases.tools;

import com.themejunky.personalstylerlib.bases.tools.model.NewSchedule;
import com.themejunky.personalstylerlib.bases.tools.model.NewScheduleMonthDays;
import com.themejunky.personalstylerlib.bases.tools.model.NewScheduleMonths;
import com.themejunky.personalstylerlib.utils.Constants;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

/**
 * Created by ThemeJunky on 4/5/2018.
 */

public class Tools_Schedule extends Tools_ISO {

    protected List<NewScheduleMonths> mNextMonthsAndDayList;

    /**
     * This method is user in Schedule Month Pop-up
     * @return - full information for adapter that contains the next 600 days order by months
     */
    public List<NewScheduleMonths> mNextDaySortedByMonth() {

        if (mNextMonthsAndDayList != null) {
            return mNextMonthsAndDayList;
        } else {
        /* prepare the head of the grid */
            List<NewScheduleMonthDays> mHeadArray = new ArrayList<>();
            mHeadArray.add(new NewScheduleMonthDays("Lu", Constants.SCHEDULE_MONTH_DAY_TYPE_HEADER));
            mHeadArray.add(new NewScheduleMonthDays("Ma", Constants.SCHEDULE_MONTH_DAY_TYPE_HEADER));
            mHeadArray.add(new NewScheduleMonthDays("Mi", Constants.SCHEDULE_MONTH_DAY_TYPE_HEADER));
            mHeadArray.add(new NewScheduleMonthDays("Jo", Constants.SCHEDULE_MONTH_DAY_TYPE_HEADER));
            mHeadArray.add(new NewScheduleMonthDays("Vi", Constants.SCHEDULE_MONTH_DAY_TYPE_HEADER));
            mHeadArray.add(new NewScheduleMonthDays("Sa", Constants.SCHEDULE_MONTH_DAY_TYPE_HEADER));
            mHeadArray.add(new NewScheduleMonthDays("Du", Constants.SCHEDULE_MONTH_DAY_TYPE_HEADER));

        /* find the current date and substrac the current day number to get the first day of current month*/
            //TODO : aici se poate add o nou constanta reprezentatnd zilel trecute din luna curenta; pt a face mai usor sa disable onclicklistenerul din adapter
            List<NewSchedule> mFirstFindAllDays = new ArrayList<>();
            Calendar mCalendar = Calendar.getInstance();
            int mCurrentDay = mCalendar.get(Calendar.DAY_OF_MONTH);
            mCalendar.add(Calendar.DAY_OF_MONTH, -mCurrentDay + 1);

        /* first find all next 600 days from now */
            for (int i = 0; i < 600; i++) {
                mFirstFindAllDays.add(new NewSchedule(mCalendar, mInstance));
                mCalendar.add(Calendar.DAY_OF_MONTH, 1);
            }

        /* Now arange all those 600 days by months */
            mNextMonthsAndDayList = new ArrayList<>();
            NewScheduleMonths mMonths = new NewScheduleMonths();
            mMonths.mDays = new ArrayList<>();
            List<NewScheduleMonthDays> mMonthsDays = new ArrayList<>();
            String mCurrentMonth = "";

            int nSize = mFirstFindAllDays.size();
            NewSchedule nItem;
            for (int i = 0; i < nSize; i++) {

                nItem = mFirstFindAllDays.get(i);
                if (!mCurrentMonth.equals(nItem.mMonth)) {
                    if (mMonthsDays.size() > 0 && mMonthsDays.size() > 0) {
                        mMonths.mDays.addAll(mMonthsDays);
                        mNextMonthsAndDayList.add(mMonths);
                    }
                    mMonths = new NewScheduleMonths();
                    mMonths.mCalendarSave = nItem.mCalendarSave;
                    mMonths.mMonth = nItem.mMonth;
                    mMonths.mYear = nItem.mYear;
                    mMonths.mDays = new ArrayList<>();
                    mMonthsDays = new ArrayList<>();
                    mCurrentMonth = nItem.mMonth;
                }
                mMonthsDays.add(new NewScheduleMonthDays(nItem.mCalendarSave, Constants.SCHEDULE_MONTH_DAY_TYPE_CURRENT));
            }

        /*
        In this loop we will add the missing ex-month days to show a nice grid of days
        1) find in with day of week is each first day of every month "born" to find out how many days before from the ex-month we should add; (reverse them before)
        2) then add 7 new entrys at the beginning of each month representing the head of the grid (ex : Lu,Ma,Mi,Jo,Vi,Sa,Du)
         */
            nSize = mNextMonthsAndDayList.size();
            for (int i = 0; i < nSize; i++) {

            /*(1)*/
                mCalendar.setTimeInMillis(mNextMonthsAndDayList.get(i).mDays.get(0).mCalendarMil);
                int max = 0;
                switch (String.valueOf(mNextMonthsAndDayList.get(i).mDays.get(0).mDayWeekNumber)) {
                    case "1":
                        max = 6;
                        break;
                    case "3":
                        max = 1;
                        break;
                    case "4":
                        max = 2;
                        break;
                    case "5":
                        max = 3;
                        break;
                    case "6":
                        max = 4;
                        break;
                    case "7":
                        max = 5;
                        break;
                }

                List<NewScheduleMonthDays> mSlaveArray = new ArrayList<>();
                for (int k = 0; k < max; k++) {
                    mCalendar.add(Calendar.DAY_OF_MONTH, -1);
                    mSlaveArray.add(new NewScheduleMonthDays(mCalendar.getTimeInMillis(), Constants.SCHEDULE_MONTH_DAY_TYPE_BEFORE));
                }
                Collections.reverse(mSlaveArray);
                mNextMonthsAndDayList.get(i).mDays.addAll(0, mSlaveArray);
            /*(2)*/
                mNextMonthsAndDayList.get(i).mDays.addAll(0, mHeadArray);
            }
            return mNextMonthsAndDayList;
        }
    }

}
