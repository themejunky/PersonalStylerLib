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
import com.themejunky.personalstylerlib.customviews.CustomButtonBorderImage;
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


        ((CustomButtonBorderImage) findViewById(R.id.nPicture)).setDisable();

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
        newStuff.add(new PhotoModel("https://storage.googleapis.com/test-a1c65.appspot.com/images/cropped_473425b07dbfcb78f515332082664.jpg?GoogleAccessId=firebase-adminsdk-yx7fs@test-a1c65.iam.gserviceaccount.com&Expires=1593896400&Signature=fG%2BhBKgEr2Er3KSo%2BYA7Vmq7HFKY2D9uU3k2Fe2UNjKMIRY5NRHPuQNPLpfFQ2Z2UcdBccjDnMJJylRVs%2B8Jw1wEbHfRFYzmjvIHXDjes3TvVv8Xxj3dqkws1Yw9YrD4QTmCv42qve52i4NfdfpW9L26COHDxn2YcJjSdkQ5BPzUPqWg8NHv0F6Ja%2Ft4BeMGzdgTUeY3yJeSrIjPNBJGDEAoH%2FVC8Bk8lbyPSBFpbPXYgjBi03mkcqNhQoCI5FZvGLU4gxqb36ALbh0%2FGtQhU%2Fj4Bnlo%2BKBcQI6yhEoS1K0NVBpndqba5FDzjMOijBEs3jgoAHMwfjaS2eaxntsimw%3D%3D","https://storage.googleapis.com/test-a1c65.appspot.com/images/cropped_473425b07dbfcb78f515332082664.jpg?GoogleAccessId=firebase-adminsdk-yx7fs@test-a1c65.iam.gserviceaccount.com&Expires=1593896400&Signature=fG%2BhBKgEr2Er3KSo%2BYA7Vmq7HFKY2D9uU3k2Fe2UNjKMIRY5NRHPuQNPLpfFQ2Z2UcdBccjDnMJJylRVs%2B8Jw1wEbHfRFYzmjvIHXDjes3TvVv8Xxj3dqkws1Yw9YrD4QTmCv42qve52i4NfdfpW9L26COHDxn2YcJjSdkQ5BPzUPqWg8NHv0F6Ja%2Ft4BeMGzdgTUeY3yJeSrIjPNBJGDEAoH%2FVC8Bk8lbyPSBFpbPXYgjBi03mkcqNhQoCI5FZvGLU4gxqb36ALbh0%2FGtQhU%2Fj4Bnlo%2BKBcQI6yhEoS1K0NVBpndqba5FDzjMOijBEs3jgoAHMwfjaS2eaxntsimw%3D%3D","473425b07dbfcb78f515332082664"));

        mPhotos = newStuff;
        refreshPhotos();
    }

    public void nUpload() {
        for (PhotoModel item: mPhotos) {
            Log.d("uploaddd",""+item.mPhotoFrom);
            Log.d("uploaddd",""+item.mCroppedFilePath);
            Log.d("uploaddd",""+item.mFilePath);
            Log.d("uploaddd",""+item.mfirebase_storage_id);
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