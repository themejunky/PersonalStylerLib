package com.themejunky.personalstylerlib.customviews;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.themejunky.personalstylerlib.R;
import com.themejunky.personalstylerlib.utils.DataBaseAppointmentsStatus;


/**
 * Default layout for custom status in order details
 */

public class CustomStatus extends BaseCustom_LinearLayout {

    public CustomStatus(Context nContext) {
        super(nContext);

        /* start-up stuff*/
        TAG = "CustomStatus";
        mContext = nContext;
        inflate(nContext, R.layout.custom_status, this);
    }

    public CustomStatus setStatus(DataBaseAppointmentsStatus nStatus, Boolean nLastLineVisibility) {
        setTime(nStatus.hour);
        setHour(nStatus.day);
        setStatus(nStatus.long_text);

        if (!nLastLineVisibility) {
            findViewById(R.id.nLastLine).setVisibility(View.GONE);
        }


        return this;
    }

    private void setTime(String nTime) {
        ((TextView) findViewById(R.id.nTime)).setText(nTime);
    }

    private void setHour(String nHour) {
        ((TextView) findViewById(R.id.nDate)).setText(nHour);
    }

    private void setStatus(String nStatus) {
        ((TextView) findViewById(R.id.nStatus)).setText(nStatus);
    }
}
