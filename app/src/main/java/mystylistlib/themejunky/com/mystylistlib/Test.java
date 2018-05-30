package mystylistlib.themejunky.com.mystylistlib;


import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.themejunky.personalstylerlib.bases.activities.photo.Photo;
import com.themejunky.personalstylerlib.bases.activities.photo.PhotoBase;
import com.themejunky.personalstylerlib.customdialogs.customQuestion.CustomQuestion;
import com.themejunky.personalstylerlib.customdialogs.infiniteLoading.InfiniteLoading;
import com.themejunky.personalstylerlib.customdialogs.photo.take.TakePhoto;
import com.themejunky.personalstylerlib.utils.Constants;

public class Test extends Photo implements Photo._Interface, InfiniteLoading._Interface, CustomQuestion._Interface {


    CustomQuestion adad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

         adad = CustomQuestion.getInstance();
        adad.refreshContent(this,this,R.string.text_titlu,R.string.text_intrebare,R.string.text_pozitiv,R.string.text_negativ,"yo");
        adad.setCustomIntegerVariable(1);

        adad.getDialog().show();


//        mActivatePhotoInternalAPI(this,8, "1.8", (RelativeLayout) findViewById(R.id.nContai));
//
//        findViewById(R.id.nApasa).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                TakePhoto.getInstance().refreshContent(Test.this, Constants.TAKE_PHOTO_BOTH,Test.this);
//            }
//        });
//



      //  InfiniteLoading.getInstance().refreshContent(this,this);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPhotosRefreshAvailable(String nPhotoType) {
        Toast.makeText(this,"AV : "+nPhotoType+"- TOTAL : "+mPhotos.size(),Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPhotosError() {
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


    @Override
    public void onCustomQuestion_Negative(AlertDialog.Builder buider, String nType) {
        adad.refreshContent(this,this,R.string.time_ago_seconds,R.string.text_intrebare,R.string.text_pozitiv,R.string.text_negativ,"yo" );
        adad.getDialog().show();
    }

    @Override
    public void onCustomQuestion_Positive(AlertDialog.Builder buider, String nType) {

    }
}