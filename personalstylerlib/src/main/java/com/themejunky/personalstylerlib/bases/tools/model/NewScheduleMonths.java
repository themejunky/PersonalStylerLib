package com.themejunky.personalstylerlib.bases.tools.model;

import java.util.List;

public class NewScheduleMonths {
    public long mCalendarSave;
    public String mMonth;
    public int mYear;
    public List<NewScheduleMonthDays> mDays;
    public Boolean status;

    //because calculated calendar contains day-names and previsious month days
    public int mCurrentMonthOffsetDays=0;
}
