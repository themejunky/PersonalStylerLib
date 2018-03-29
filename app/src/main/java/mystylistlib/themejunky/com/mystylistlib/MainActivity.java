package mystylistlib.themejunky.com.mystylistlib;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Spinner;

import com.themejunky.personalstylerlib.InitCaligraphy;
import com.themejunky.personalstylerlib.customviews.CustomButton;
import com.themejunky.personalstylerlib.customviews.CustomButtonBorder;
import com.themejunky.personalstylerlib.customviews.CustomButtonBorderImage;
import com.themejunky.personalstylerlib.customviews.CustomInput;
import com.themejunky.personalstylerlib.customviews.CustomTextSwitch;

import java.util.ArrayList;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity  implements CustomButtonBorder.CustomButtonBorderInterface {


    private CustomInput mSpinner;
    private CustomTextSwitch mSwitch,mSwitcher;
    private CustomButtonBorder apasa;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder().setDefaultFontPath("Catamaran-Regular.ttf").setFontAttrId(R.attr.fontPath).build());
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
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onCustomButtonBorderClick(View view) {
        Log.d("awaea","click 1");

        if (view.getTag().equals("tag")){
                Log.d("awaea","click 2");

        }
    }
}

