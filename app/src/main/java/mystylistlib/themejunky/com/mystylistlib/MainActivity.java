package mystylistlib.themejunky.com.mystylistlib;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.themejunky.personalstylerlib.customdialogs.hour.ScheduleTimeDialog;
import com.themejunky.personalstylerlib.customviews.CustomButtonBorder;
import com.themejunky.personalstylerlib.customviews.CustomInput;
import com.themejunky.personalstylerlib.customviews.CustomServices;
import com.themejunky.personalstylerlib.customviews.CustomTextSwitch;


public class MainActivity extends AppCompatActivity implements CustomServices.Custom_Service_Interface {


    private CustomInput mSpinner;
    private CustomTextSwitch mSwitch,mSwitcher;
    private CustomButtonBorder apasa;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((CustomServices) findViewById(R.id.nMen)).setListener(this);
        ((CustomServices) findViewById(R.id.nMen)).setClickListenerMovement(true);
    }





    @Override
    public void onCustomServiceClick(View view, String nType) {
            Log.d("adadas","este : "+nType);
        Toast.makeText(this,"este : "+nType,Toast.LENGTH_LONG).show();
    }
}

