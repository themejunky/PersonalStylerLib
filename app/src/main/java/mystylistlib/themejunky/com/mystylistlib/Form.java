package mystylistlib.themejunky.com.mystylistlib;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.themejunky.personalstylerlib.bases.activities.custom.CustomActivity;
import com.themejunky.personalstylerlib.bases.model.Item;
import com.themejunky.personalstylerlib.customviews.CustomButton;
import com.themejunky.personalstylerlib.customviews.CustomInput;
import com.themejunky.personalstylerlib.utils.FormClickValidator;
import com.themejunky.personalstylerlib.utils.SimpleSpinnerLeftAdapter;

import java.util.ArrayList;
import java.util.List;

public class Form extends CustomActivity implements CustomButton.Custom_Button_Interface, FormClickValidator.FormClickValidatorInterface {
    protected ArrayList<CustomInput> mCustomInputs;
    protected CustomInput mEmail,mDescriere,mTimp;
    protected CustomButton mReset;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form);

       // mEmail = findViewById(R.id.nEmail);
      //  mDescriere = findViewById(R.id.nDescriere);
      //  mTimp = findViewById(R.id.nTimp);
     //   mReset = findViewById(R.id.nResetButton);

//       String[] arraySpinner = new String[] {"Alege orasul", "Bucuresti", "Teleorman", "Timisoara", "Voluntari"};
//        Spinner s = ((CustomInput) findViewById(R.id.nTimp)).findViewById(R.id.nSpinner);
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
//                android.R.layout.simple_spinner_item, arraySpinner);
//        adapter.setDropDownViewResource(android.R.layout.simple_spin‌​ner_dropdown_item);
//        s.setAdapter(adapter);

        List<Item> mData = new ArrayList<>();
        mData.add(new Item("1","2"));
        mData.add(new Item("1","3"));
        mData.add(new Item("1","4"));
        ((Spinner)( findViewById(R.id.nTimp)).findViewById(R.id.nSpinner)).setAdapter(new SimpleSpinnerLeftAdapter(this, mData, false));

        mCustomInputs = new ArrayList<CustomInput>() {{add(mEmail);add(mDescriere);add(mTimp);}};
      //  mReset.setListener(this);
    }

    @Override
    public void onCustomButtonClick(View nView) {
        if (mCO(nView, R.string.reset_key_button_reset)) {
            new FormClickValidator(this, mCustomInputs, this);
        }
    }

    @Override
    public void onFormClickValidatorFinish() {

    }
}
