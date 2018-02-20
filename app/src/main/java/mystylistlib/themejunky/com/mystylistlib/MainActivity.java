package mystylistlib.themejunky.com.mystylistlib;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.themejunky.personalstylerlib.InitCaligraphy;
import com.themejunky.personalstylerlib.customviews.CustomButton;
import com.themejunky.personalstylerlib.customviews.CustomButtonBorder;

public class MainActivity extends AppCompatActivity implements CustomButton.Custom_Button_Interface {

    CustomButton bb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new InitCaligraphy("Catamaran-Regular.ttf");
        bb = findViewById(R.id.bb);
        bb.setListener(this);
    }



    @Override
    public void onCustomButtonClick(View view) {

    }
}
