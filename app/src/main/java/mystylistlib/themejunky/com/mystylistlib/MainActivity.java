package mystylistlib.themejunky.com.mystylistlib;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.themejunky.personalstylerlib.bases.tools.Tools;
import com.themejunky.personalstylerlib.customdialogs.hour.ScheduleTimeDialog;
import com.themejunky.personalstylerlib.customviews.CustomButtonBorder;
import com.themejunky.personalstylerlib.customviews.CustomInput;
import com.themejunky.personalstylerlib.customviews.CustomServices;
import com.themejunky.personalstylerlib.customviews.CustomTextSwitch;


public class MainActivity extends AppCompatActivity implements CustomServices.Custom_Service_Interface, CustomButtonBorder.CustomButtonBorderInterface {

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((CustomButtonBorder) findViewById(R.id.nHaveLocation)).setListener(this);
        ((CustomButtonBorder) findViewById(R.id.nNoLocation)).setListener(this);


        Tools.getInstance(this).showSimpleSnackBar(findViewById(R.id.nMainContainer),"ceva");
    }

    @Override
    public void onCustomServiceClick(View view, String nType) {
        Toast.makeText(this,"este : "+nType,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onCustomButtonBorderClick(View view) {

    }
}



