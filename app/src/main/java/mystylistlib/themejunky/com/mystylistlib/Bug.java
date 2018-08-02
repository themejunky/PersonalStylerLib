package mystylistlib.themejunky.com.mystylistlib;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.themejunky.personalstylerlib.customdialogs.customQuestion.CustomQuestion;

public class Bug extends AppCompatActivity implements CustomQuestion._Interface {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);

        CustomQuestion mDeletePopup = CustomQuestion.getInstance();
        mDeletePopup.refreshContent(this, this, R.string.login_email_title, R.string.add_service_add, R.string.text_pozitiv, R.string.text_negativ, "delete_service");
        mDeletePopup.getDialog().show();
    }

    @Override
    public void onCustomQuestion_Negative(AlertDialog.Builder buider, String nType) {

    }

    @Override
    public void onCustomQuestion_Positive(AlertDialog.Builder buider, String nType) {

    }
}
