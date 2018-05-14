package mystylistlib.themejunky.com.mystylistlib;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

public class CameraPreviewCustom extends AppCompatActivity implements SurfaceHolder.Callback {
        private Camera camera;
        public static SurfaceView surfaceView;
        public static SurfaceHolder surfaceHolder;
        public static Camera.PictureCallback jpegCallback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main2);
        surfaceView = (SurfaceView) findViewById(R.id.surfaceView);
    }
        @Override
        public void onResume()
        {
                super.onResume();



                surfaceHolder = surfaceView.getHolder();
                surfaceHolder.addCallback(this);
                surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

                jpegCallback = new Camera.PictureCallback() {
                        public void onPictureTaken(byte[] data, Camera camera) {
                                Camera.CameraInfo info = new Camera.CameraInfo();
                                Camera.getCameraInfo(Camera.CameraInfo.CAMERA_FACING_BACK, info);
                                int rotation = getWindowManager().getDefaultDisplay().getRotation();




                            FileOutputStream fos = null;

                            if (data != null) {

                                try {
//                                    File outputDir = CameraPreviewCustom.this.getCacheDir(); // context being the Activity pointer
//                                    File outputFile = File.createTempFile("prefix", "extension", outputDir);
//                                    // outputDir.deleteOnExit();
//                                    fos = new FileOutputStream(outputFile);
//                                    (BitmapFactory.decodeByteArray(data, 0, data.length)).compress(Bitmap.CompressFormat.PNG, 100, fos);
//
//                                  //  nPhoto.mFilePathString = outputFile.getAbsolutePath();
//                                  //  nPhoto.mFilePath = Uri.fromFile(outputFile);



                                    String filename = "bitmap.png";
                                    FileOutputStream stream = CameraPreviewCustom.this.openFileOutput(filename, Context.MODE_PRIVATE);
                                    (BitmapFactory.decodeByteArray(data, 0, data.length)).compress(Bitmap.CompressFormat.PNG, 100, stream);

                                    //Cleanup
                                    stream.close();
                                    (BitmapFactory.decodeByteArray(data, 0, data.length)).recycle();


                                    Toast.makeText(CameraPreviewCustom.this,"POZA! PA",Toast.LENGTH_LONG).show();
                                    Intent returnIntent = new Intent();
                                    returnIntent.putExtra("image",filename);
                                    setResult(Activity.RESULT_OK, returnIntent);
                                    finish();
                                    Log.d("adadad","camera 4 ");
                                }catch (Exception e) {
                                    Log.d("adadad","camera 41 : "+e.getMessage());
                                }

                            }
//                                Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
//
//                                if (bitmap != null)
//                                {
//                                    try
//                                    {
//
//                                    }


                        }
                };


                findViewById(R.id.mFlash).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        camera.takePicture(null, null, jpegCallback);
                    }
                });
        }


        @Override
        public void surfaceCreated(SurfaceHolder holder) {
                try
                {
                        camera = Camera.open();
                }
                catch (RuntimeException e)
                {
                        return;
                }

                Camera.Parameters param;
                param = camera.getParameters();
                param.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
                Camera.Size bestSize = null;
                List<Camera.Size> sizeList = camera.getParameters().getSupportedPreviewSizes();
                bestSize = sizeList.get(0);

                for(int i = 1; i < sizeList.size(); i++)
                {
                        // Log.d("ceva","rez : "+sizeList.get(i).width + "/"+ sizeList.get(i).height);
                        if((sizeList.get(i).width * sizeList.get(i).height) >   (bestSize.width * bestSize.height))
                        {
                                bestSize = sizeList.get(i);

                                Log.d("bestzie",""+bestSize);
                        }
                }

                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
                {
                        param.setRotation(90);
                        //TODO: da eroare pe alcatel
                        camera.setParameters(param);
                        camera.setDisplayOrientation(90);
                }

                try
                {
                        camera.setPreviewDisplay(surfaceHolder);
                        camera.startPreview();
                }
                catch (Exception e)
                {
                        //  Log.d("cameranotok", "6 - " + e.getMessage());
                        return;
                }
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
                camera.stopPreview();
                camera.release();
                camera = null;
        }
}
