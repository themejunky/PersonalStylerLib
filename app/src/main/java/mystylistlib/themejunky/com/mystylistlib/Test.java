package mystylistlib.themejunky.com.mystylistlib;


import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.themejunky.personalstylerlib.bases.activities.photo.Photo;
import com.themejunky.personalstylerlib.bases.activities.photo.PhotoBase;
import com.themejunky.personalstylerlib.bases.model.PhotoModel;
import com.themejunky.personalstylerlib.customdialogs.customQuestion.CustomQuestion;
import com.themejunky.personalstylerlib.customdialogs.infiniteLoading.InfiniteLoading;
import com.themejunky.personalstylerlib.customdialogs.photo.take.TakePhoto;
import com.themejunky.personalstylerlib.customviews.CustomServices;
import com.themejunky.personalstylerlib.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class Test extends Photo implements Photo._Interface, InfiniteLoading._Interface, CustomQuestion._Interface {


    CustomQuestion adad;
    CustomServices mService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        mActivatePhotoInternalAPI(this,8,"1.8",(RelativeLayout) findViewById(R.id.nContai));

        findViewById(R.id.nApasa).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TakePhoto.getInstance().refreshContent(Test.this, Constants.TAKE_PHOTO_BOTH,Test.this);
            }
        });

        findViewById(R.id.nAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add();
            }
        });
        findViewById(R.id.nUpload).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nUpload();
            }
        });

    }

    public void add() {
        List<PhotoModel> newStuff = new ArrayList<>();
        newStuff.add(new PhotoModel("https://firebasestorage.googleapis.com/v0/b/mystylist-e8d65.appspot.com/o/images%2Foriginal_ee764457-cbdc-4226-afd2-34761b8b1607?alt=media&token=4214b7ac-c7cb-4546-a4b0-714f5e7d2d35","https://firebasestorage.googleapis.com/v0/b/mystylist-e8d65.appspot.com/o/images%2Fcropped_ee764457-cbdc-4226-afd2-34761b8b1607?alt=media&token=1da4b246-048d-40fa-98b6-bcac72485091"));
        newStuff.add(new PhotoModel("https://firebasestorage.googleapis.com/v0/b/mystylist-e8d65.appspot.com/o/images%2Foriginal_87897399-abb4-42f6-9ee7-fe19fe69ac06?alt=media&token=25330ad2-a69c-4130-b29c-cd356bfab2a0","https://firebasestorage.googleapis.com/v0/b/mystylist-e8d65.appspot.com/o/images%2Fcropped_a8a6f41e-4401-4771-8ccf-5c0f07edb400?alt=media&token=c90d8e83-311d-44bb-a687-ddfb18a1ddb2"));

        mPhotos = newStuff;
        refreshPhotos();
    }

    public void nUpload() {
        for (PhotoModel item: mPhotos) {

            Log.d("uploaddd",""+item.mPhotoFrom);
            Log.d("uploaddd",""+item.mCroppedFilePath);
            Log.d("uploaddd",""+item.mFilePath);
            Log.d("uploaddd","-------");
        }
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
    public void onCustomServiceClick(View view,String nType) {
        Log.d("dadadasdasdasd","1123 "+nType);
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