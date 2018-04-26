package mystylistlib.themejunky.com.mystylistlib;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.themejunky.personalstylerlib.customdialogs.hour.ScheduleTimeDialog;
import com.themejunky.personalstylerlib.customviews.CustomButtonBorder;
import com.themejunky.personalstylerlib.customviews.CustomInput;
import com.themejunky.personalstylerlib.customviews.CustomTextSwitch;


public class MainActivity extends AppCompatActivity  implements CustomButtonBorder.CustomButtonBorderInterface, ScheduleTimeDialog.ScheduleTimeDialog_Interface {


    private CustomInput mSpinner;
    private CustomTextSwitch mSwitch,mSwitcher;
    private CustomButtonBorder apasa;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main)

        findViewById(R.id.nCeas).setTag(R.id.schedule_days_key_info,"11:30");
        ScheduleTimeDialog.getInstance().refreshContent(this,findViewById(R.id.nCeas),this);

       /* mSpinner=  findViewById(R.id.nSpinnerTest);
        List<String> list = new ArrayList<String>();
        list.add("list 1");
        list.add("list 2");
        list.add("list 3");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ((Spinner) mSpinner.findViewById(R.id.nSpinner)).setAdapter(dataAdapter);
        mSwitch = findViewById(R.id.nSwitchr);
        mSwitcher = findViewById(R.id.nSwitcher);
        mSwitch.setListener(this);
        mSwitcher.setListener(this);*/
        apasa = findViewById(R.id.apasaId);
        apasa.setListener(this);
        apasa.setAsAppointmentButton();



    }

    @Override
    public void onCustomButtonBorderClick(View view) {
        Log.d("awaea","click 1");

        if (view.getTag().equals("tag")){
                Log.d("awaea","click 2");

        }
    }

    @Override
    public void onScheduleTimeDialog_Choose(String nTimeSet, View nView) {

    }
}

