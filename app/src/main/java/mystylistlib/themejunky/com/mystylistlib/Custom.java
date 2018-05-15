package mystylistlib.themejunky.com.mystylistlib;

import android.os.Bundle;

import com.themejunky.personalstylerlib.bases.activities.custom.CustomActivity;
import com.themejunky.personalstylerlib.customdialogs.CustonScheduleMonthDialog;

public class Custom extends CustomActivity implements CustonScheduleMonthDialog.ScheduleMonthDialog_Interface {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom);

        CustonScheduleMonthDialog.getInstance().refreshContent(this,this);
    }


    @Override
    public void onScheduleMonthDialog_Choose(long nSelectedDateInTimeMillis) {

    }
}
