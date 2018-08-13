package mystylistlib.themejunky.com.mystylistlib;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.themejunky.personalstylerlib.bases.activities.custom.CustomActivity;
import com.themejunky.personalstylerlib.customdialogs.CustonScheduleMonthDialog;
import com.themejunky.personalstylerlib.customviews.CustomInput;

public class Custom extends CustomActivity implements CustonScheduleMonthDialog.ScheduleMonthDialog_Interface {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom);

        CustonScheduleMonthDialog.getInstance().refreshContent(this,this);


        String[] arraySpinner = new String[] {"Alege orasul", "Bucuresti", "Teleorman", "Timisoara", "Voluntari"};
        Spinner s = ((CustomInput) findViewById(R.id.nEmail3)).findViewById(R.id.nSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spin‌​ner_dropdown_item);
        s.setAdapter(adapter);
    }


    @Override
    public void onScheduleMonthDialog_Choose(long nSelectedDateInTimeMillis) {

    }
}
