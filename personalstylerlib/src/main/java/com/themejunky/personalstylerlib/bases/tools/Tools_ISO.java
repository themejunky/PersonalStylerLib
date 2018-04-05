package com.themejunky.personalstylerlib.bases.tools;

import android.annotation.SuppressLint;
import android.content.res.Resources;

import com.themejunky.personalstylerlib.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class Tools_ISO extends ToolsBase {


    /**
     * Transform Calendar to ISO8601
     * @param calendar - calendar to transform
     * @return ISO Date
     */
    public String ISO8601fromCalendar(final Calendar calendar) {
        Date date = calendar.getTime();
        TimeZone tz = TimeZone.getTimeZone("EET");
        java.text.DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", getLocale());
        df.setTimeZone(tz);

        return df.format(date);
    }

    public Calendar fromISO8601UTC(String dateStr) {
        Calendar nCalendar = Calendar.getInstance();
        TimeZone tz = TimeZone.getTimeZone("EET");
        java.text.DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", getLocale());
        df.setTimeZone(tz);

        try {
            nCalendar.setTime(df.parse(dateStr));
            return nCalendar;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String timeAgo(Date date) {
        return timeAgo(date.getTime());
    }

    @SuppressLint("StringFormatInvalid")
    public String timeAgo(long millis) {
        long diff = new Date().getTime() - millis;

        Resources r = mContext.getResources();

        String prefix = r.getString(R.string.time_ago_prefix);
        String suffix = r.getString(R.string.time_ago_suffix);

        double seconds = Math.abs(diff) / 1000;
        double minutes = seconds / 60;
        double hours = minutes / 60;
        double days = hours / 24;
        double years = days / 365;

        String words;

        if (seconds < 45) {
            words = r.getString(R.string.time_ago_seconds, Math.round(seconds));
        } else if (seconds < 90) {
            words = r.getString(R.string.time_ago_minute, 1);
        } else if (minutes < 45) {
            words = r.getString(R.string.time_ago_minutes, Math.round(minutes));
        } else if (minutes < 90) {
            words = r.getString(R.string.time_ago_hour, 1);
        } else if (hours < 24) {
            words = r.getString(R.string.time_ago_hours, Math.round(hours));
        } else if (hours < 42) {
            words = r.getString(R.string.time_ago_day, 1);
        } else if (days < 30) {
            words = r.getString(R.string.time_ago_days, Math.round(days));
        } else if (days < 45) {
            words = r.getString(R.string.time_ago_month, 1);
        } else if (days < 365) {
            words = r.getString(R.string.time_ago_months, Math.round(days / 30));
        } else if (years < 1.5) {
            words = r.getString(R.string.time_ago_year, 1);
        } else {
            words = r.getString(R.string.time_ago_years, Math.round(years));
        }

        StringBuilder sb = new StringBuilder();

        if (prefix != null && prefix.length() > 0) {
            sb.append(prefix).append(" ");
        }

        sb.append(words);

        if (suffix != null && suffix.length() > 0) {
            sb.append(" ").append(suffix);
        }

        return sb.toString().trim();
    }
}
