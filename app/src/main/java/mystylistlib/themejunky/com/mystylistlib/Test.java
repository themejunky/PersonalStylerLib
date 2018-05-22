package mystylistlib.themejunky.com.mystylistlib;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.themejunky.personalstylerlib.bases.activities.photo.PhotoActivity;
import com.themejunky.personalstylerlib.customdialogs.infiniteLoading.InfiniteLoading;
import com.themejunky.personalstylerlib.customdialogs.photo.TakePhoto;
import com.themejunky.personalstylerlib.utils.Constants;

public class Test extends PhotoActivity implements PhotoActivity._Interface, InfiniteLoading._Interface {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        mActivatePhotoInternalAPI(this,8,(RelativeLayout) findViewById(R.id.nContai));

        findViewById(R.id.nApasa).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TakePhoto.getInstance().refreshContent(Test.this, Constants.TAKE_PHOTO_BOTH,Test.this);
            }
        });




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
}