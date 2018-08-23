package mystylistlib.themejunky.com.mystylistlib;

import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.themejunky.personalstylerlib.bases.activities.custom.CustomActivity;
import com.themejunky.personalstylerlib.customviews.CustomInput;

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

            String[] arraySpinner = new String[] {"Alege orasul", "Bucuresti", "Teleorman", "Timisoara", "Voluntari"};
            Spinner s = ((CustomInput) findViewById(R.id.nOptions)).findViewById(R.id.nSpinner);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, arraySpinner);
            adapter.setDropDownViewResource(android.R.layout.simple_spin‌​ner_dropdown_item);
            s.setAdapter(adapter);

        //   findViewById(R.id.nText1).setPadding(defaultPadding,(int) (0.15f*14)+defaultPadding,defaultPadding,defaultPadding);
         //   findViewById(R.id.nText2).setPadding(defaultPadding,(int) (0.15f*14)+defaultPadding,defaultPadding,defaultPadding);
         //   findViewById(R.id.nText3).setPadding(defaultPadding,(int) (0.15f*14)+defaultPadding,defaultPadding,defaultPadding);
        }
        catch (Exception e) {
            Log.d("eroares"," da : "+e.getMessage());
        }

    }
}
