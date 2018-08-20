package mystylistlib.themejunky.com.mystylistlib;

import android.os.Bundle;
import android.view.View;

import com.themejunky.personalstylerlib.bases.activities.custom.CustomActivity;
import com.themejunky.personalstylerlib.customviews.CustomButton;
import com.themejunky.personalstylerlib.customviews.CustomInput;
import com.themejunky.personalstylerlib.utils.FormClickValidator;

import java.util.ArrayList;

public class Form extends CustomActivity implements CustomButton.Custom_Button_Interface, FormClickValidator.FormClickValidatorInterface {
    protected ArrayList<CustomInput> mCustomInputs;
    protected CustomInput mEmail;
    protected CustomButton mReset;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form);

        mEmail = findViewById(R.id.nEmail);
        mReset = findViewById(R.id.nResetButton);
        mCustomInputs = new ArrayList<CustomInput>() {{add(mEmail);}};
        mReset.setListener(this);
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
