package mystylistlib.themejunky.com.mystylistlib;

import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.themejunky.personalstylerlib.bases.activities.custom.CustomActivity;

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

        //   findViewById(R.id.nText1).setPadding(defaultPadding,(int) (0.15f*14)+defaultPadding,defaultPadding,defaultPadding);
         //   findViewById(R.id.nText2).setPadding(defaultPadding,(int) (0.15f*14)+defaultPadding,defaultPadding,defaultPadding);
         //   findViewById(R.id.nText3).setPadding(defaultPadding,(int) (0.15f*14)+defaultPadding,defaultPadding,defaultPadding);
        }
        catch (Exception e) {
            Log.d("eroares"," da : "+e.getMessage());
        }

    }
}
