package mystylistlib.themejunky.com.mystylistlib;


import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.themejunky.personalstylerlib.bases.PhotoActivity;
import com.themejunky.personalstylerlib.customdialogs.photo.TakePhoto;
import com.themejunky.personalstylerlib.utils.Constants;

public class Test extends PhotoActivity implements PhotoActivity._Interface{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
///dasdasdas

        mPhotoActivityInterface = this;
        findViewById(R.id.nApasa).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TakePhoto.getInstance().refreshContent(Test.this, Constants.TAKE_PHOTO_BOTH,Test.this);
            }
        });
    }

    @Override
    public void onPhotosRefreshAvailable(String nPhotoType) {
        Toast.makeText(this,"AV : "+nPhotoType+"- TOTAL : "+mPhotos.size(),Toast.LENGTH_LONG).show();
    }
}