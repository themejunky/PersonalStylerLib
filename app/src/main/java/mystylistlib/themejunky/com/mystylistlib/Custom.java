package mystylistlib.themejunky.com.mystylistlib;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.themejunky.personalstylerlib.bases.CustomActivity;
import com.themejunky.personalstylerlib.customdialogs.CustonScheduleMonthDialog;
import com.themejunky.personalstylerlib.customviews.CustomSettingItem;

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
