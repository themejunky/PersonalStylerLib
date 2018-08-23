package mystylistlib.themejunky.com.mystylistlib;

import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.themejunky.personalstylerlib.bases.activities.custom.CustomActivity;
import com.themejunky.personalstylerlib.bases.model.Item;
import com.themejunky.personalstylerlib.bases.tools.Tools;
import com.themejunky.personalstylerlib.customviews.CustomInput;

import java.util.ArrayList;
import java.util.List;

public class Fonts extends CustomActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fonts);

        try {
            Typeface font = Typeface.createFromAsset(getAssets(), "Regular.ttf");
            ((EditText) findViewById(R.id.nText1)).setTypeface(font);
            ((EditText) findViewById(R.id.nText2)).setTypeface(font);
            ((EditText) findViewById(R.id.nText3)).setTypeface(font);

            float scale = getResources().getDisplayMetrics().density;

         int defaultPadding = (int) (5*scale + 0.5f);



            List<Item> mData = new ArrayList<>();
            mData.add(new Item("0","Alege Durata"));
            mData.add(new Item("0","Bucurestia"));
            mData.add(new Item("0","Teleorman"));

            Tools.getInstance(this).mSimpleSetupSpinnerCustom(((CustomInput) findViewById(R.id.nOptions)),mData, false);

        //   findViewById(R.id.nText1).setPadding(defaultPadding,(int) (0.15f*14)+defaultPadding,defaultPadding,defaultPadding);
         //   findViewById(R.id.nText2).setPadding(defaultPadding,(int) (0.15f*14)+defaultPadding,defaultPadding,defaultPadding);
         //   findViewById(R.id.nText3).setPadding(defaultPadding,(int) (0.15f*14)+defaultPadding,defaultPadding,defaultPadding);
        }
        catch (Exception e) {
            Log.d("eroares"," da : "+e.getMessage());
        }

    }
}
