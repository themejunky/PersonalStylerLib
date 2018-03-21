package mystylistlib.themejunky.com.mystylistlib;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.themejunky.personalstylerlib.InitCaligraphy;
import com.themejunky.personalstylerlib.customviews.CustomButton;
import com.themejunky.personalstylerlib.customviews.CustomButtonBorder;
import com.themejunky.personalstylerlib.customviews.CustomButtonBorderImage;

public class MainActivity extends AppCompatActivity  {

    private CustomButtonBorderImage button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new InitCaligraphy("Catamaran-Regular.ttf");
        /*
        button = findViewById(R.id.test);
        button.setTooltipText();*/
    }

}

