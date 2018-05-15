package mystylistlib.themejunky.com.mystylistlib;


import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.themejunky.personalstylerlib.bases.PhotoActivity;
import com.themejunky.personalstylerlib.customdialogs.photo.TakePhoto;
import com.themejunky.personalstylerlib.utils.Constants;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
        Log.d("adadad","onCreate");
        findViewById(R.id.nApasa).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TakePhoto.getInstance().refreshContent(Test.this, Constants.TAKE_PHOTO_BOTH,Test.this);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("adadad","onResume");

    }

    @Override
    public void onPhotosRefreshAvailable(String nPhotoType) {
        Toast.makeText(this,"AV : "+nPhotoType+"- TOTAL : "+mPhotos.size(),Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPhotosError() {

    }

/*

    private Camera getCameraInstance() {
        Camera camera = null;
        try {
            camera = Camera.open();
        } catch (Exception e) {
            // cannot get camera or does not exist
        }
        return camera;
    }

    android.hardware.Camera.PictureCallback mPicture = new android.hardware.Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, android.hardware.Camera camera) {
            File pictureFile = getOutputMediaFile();
            if (pictureFile == null) {
                return;
            }
            try {
                FileOutputStream fos = new FileOutputStream(pictureFile);
                fos.write(data);
                fos.close();
            } catch (FileNotFoundException e) {

            } catch (IOException e) {
            }
        }
    };

    private static File getOutputMediaFile() {
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                "MyCameraApp");
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }
        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(new Date());
        File mediaFile;
        mediaFile = new File(mediaStorageDir.getPath() + File.separator
                + "IMG_" + timeStamp + ".jpg");

        return mediaFile;
    }

*/
}