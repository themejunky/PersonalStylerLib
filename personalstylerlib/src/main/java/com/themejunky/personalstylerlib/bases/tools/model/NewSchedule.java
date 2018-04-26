package com.themejunky.personalstylerlib.bases.tools.model;


import com.themejunky.personalstylerlib.bases.tools.Tools;

import java.util.Calendar;

public class NewSchedule {
    public Long mCalendarSave;

    public Boolean mActive = false;
    public int mDay;
    public String mMonth;
    public String mDayWeek;
    public String mDayWeekLong;
    public int mYear;

    public NewSchedule(Calendar nCalendarDay, Tools nTools) {
        this.mCalendarSave = nCalendarDay.getTimeInMillis();

        this.mYear = nCalendarDay.get(Calendar.YEAR);
        this.mMonth = nCalendarDay.getDisplayName(Calendar.MONTH, Calendar.LONG, nTools.getLocale());
        this.mDay = (nCalendarDay.get(Calendar.DAY_OF_MONTH));
        this.mDayWeek = nCalendarDay.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT,  nTools.getLocale());
        this.mDayWeekLong = nCalendarDay.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG,  nTools.getLocale());
    }
}
