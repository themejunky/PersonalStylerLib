package mystylistlib.themejunky.com.mystylistlib;


import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.themejunky.personalstylerlib.bases.PhotoActivity;
import com.themejunky.personalstylerlib.bases.model.PhotoModel;
import com.themejunky.personalstylerlib.customdialogs.photo.TakePhoto;
import com.themejunky.personalstylerlib.utils.Constants;

import java.util.ArrayList;

public class Test extends PhotoActivity implements PhotoActivity._Interface{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);


//         mViews = new ArrayList<>();
//            mViews.add(new PhotoModel((RelativeLayout) findViewById(R.id.nPic1_container)));
//            mViews.add(new PhotoModel((RelativeLayout) findViewById(R.id.nPic2_container)));
//            mViews.add(new PhotoModel((RelativeLayout) findViewById(R.id.nPic3_container)));
//            mViews.add(new PhotoModel((RelativeLayout) findViewById(R.id.nPic4_container)));

        mActivatePhotoInternalAPI(this,8,(RelativeLayout) findViewById(R.id.nContai));

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