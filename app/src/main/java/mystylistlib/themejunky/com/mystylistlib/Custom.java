package mystylistlib.themejunky.com.mystylistlib;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.themejunky.personalstylerlib.bases.activities.custom.CustomActivity;
import com.themejunky.personalstylerlib.customdialogs.CustonScheduleMonthDialog;
import com.themejunky.personalstylerlib.customdialogs.customQuestion.CustomQuestion;
import com.themejunky.personalstylerlib.customviews.CustomInput;

public class Custom extends CustomActivity implements CustonScheduleMonthDialog.ScheduleMonthDialog_Interface, CustomQuestion._Interface {
    CustomQuestion adad;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom);

      //  CustonScheduleMonthDialog.getInstance().refreshContent(this,this);

        adad = CustomQuestion.getInstance();
        adad.refreshContent(this,this,R.string.time_ago_seconds,R.string.text_intrebare,R.string.text_pozitiv,R.string.text_negativ,"yo" );
        adad.getDialog().show();

        String[] arraySpinner = new String[] {"Alege orasul", "Bucuresti", "Teleorman", "Timisoara", "Voluntari"};
        Spinner s = ((CustomInput) findViewById(R.id.nOptions)).findViewById(R.id.nSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spin‌​ner_dropdown_item);
        s.setAdapter(adapter);
    }


    @Override
    public void onScheduleMonthDialog_Choose(long nSelectedDateInTimeMillis) {

    }

    @Override
    public void onCustomQuestion_Negative(AlertDialog.Builder buider, String nType) {

    }

    @Override
    public void onCustomQuestion_Positive(AlertDialog.Builder buider, String nType) {

    }
}
