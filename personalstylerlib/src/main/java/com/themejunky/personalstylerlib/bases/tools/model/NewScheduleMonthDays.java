package com.themejunky.personalstylerlib.bases.tools.model;

import java.util.Calendar;

public class NewScheduleMonthDays {
    public String mHead; /* this  */
    public int mDayWeekNumber;
    public int mDay;
    public String mDayType;
    public Long mCalendarMil;

    public NewScheduleMonthDays(Long nCalendarMill,String nDayType) {
        if (nCalendarMill!=null) {

            this.mCalendarMil = nCalendarMill;

            Calendar nCalendar = Calendar.getInstance();
            nCalendar.setTimeInMillis(nCalendarMill);

            this.mDay = nCalendar.get(Calendar.DAY_OF_MONTH);
            this.mDayWeekNumber = nCalendar.get(Calendar.DAY_OF_WEEK);
        }
        this.mDayType = nDayType;
    }

    public NewScheduleMonthDays(String nHead, String nDayType) {
        mHead = nHead;
        mDayType = nDayType;
    }
}
