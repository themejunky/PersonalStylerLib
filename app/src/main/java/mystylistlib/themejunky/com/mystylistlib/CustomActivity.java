package mystylistlib.themejunky.com.mystylistlib;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.themejunky.personalstylerlib.customviews.CustomSettingItem;

public class CustomActivity extends AppCompatActivity implements CustomSettingItem.CustomSettingItem_Interface {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom);


        ((CustomSettingItem) findViewById(R.id.nView)).setListener(this);
    }


    @Override
    public void onCustomSettingItemClick(View nView) {

    }
}
