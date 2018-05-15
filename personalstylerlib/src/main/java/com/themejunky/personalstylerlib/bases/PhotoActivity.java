package com.themejunky.personalstylerlib.bases;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Camera;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.themejunky.personalstylerlib.R;
import com.themejunky.personalstylerlib.bases.model.PhotoModel;
import com.themejunky.personalstylerlib.bases.tools.Tools;
import com.themejunky.personalstylerlib.customdialogs.photo.TakePhoto;
import com.themejunky.personalstylerlib.utils.Constants;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PhotoActivity extends AppCompatActivity implements TakePhoto._Interface, View.OnClickListener {
    public Tools mTools;
    private Uri mBasePhotoUri;
    Bitmap mBitmapTransformers;
    @Override
    public void onClick(View nView) {
        mPhotos.remove(Integer.parseInt(nView.getTag().toString()));
        internal();
    }

    public interface _Interface {
        void onPhotosRefreshAvailable(String nType);

        void onPhotosError();
    }

    protected Uri mFilePath;
    protected FirebaseStorage mFireBaseStorage;
    protected StorageReference storageReference;
    protected List<PhotoModel> mPhotos = new ArrayList<>();
    protected List<PhotoModel> mViews = new ArrayList<>();
    protected _Interface mPhotoActivityInterface;
    private PhotoModel nPhoto;
    private int ACTION_CAMERA = 1;
    private int ACTION_GALLERY = 2;
    private int ACTION_CAMERA_REQUEST = 3;
    File outputFiletest = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTools = Tools.getInstance(this);
        ///  mFireBaseStorage = FirebaseStorage.getInstance();
        // storageReference = mFireBaseStorage.getReference();
    }

    String imageFilePath;


    /**
     * Initiate take photo (gallery and/or camera) with UI
     *
     * @param nListener  - listener to comunicate with the activity that triggers this process
     * @param nNoPic     - number of desired pictures
     * @param nViewGroup - view group in witch the UI will be deployed
     */
    protected void mActivatePhotoInternalAPI(_Interface nListener, int nNoPic, ViewGroup nViewGroup) {

        mPhotoActivityInterface = nListener;

        /* internal container witch also contains HorizontalScrollView */
        LinearLayout nContainer = mTools.getMainContainerAfterInflate(nViewGroup, R.layout.custom_pictures).findViewById(R.id.nPicMainContainer);

        /* initiate mViews(will hold references to all pictures) */
        mViews = new ArrayList<>();
        RelativeLayout nItem;
        for (int i = 0; i < nNoPic; i++) {
            /* inflate new picture box */
            nItem = (RelativeLayout) mTools.getMainContainerAfterInflate(nContainer, R.layout.custom_pictures_item);
            nItem.setTag(String.valueOf(i));
            nItem.setOnClickListener(this);
            mViews.add(new PhotoModel(nItem));
        }
        resetPhotoBox();
    }

    /**
     * Reset all photo boxes on every delete or add photo
     */
    private void resetPhotoBox() {

        for (PhotoModel item : mViews) {
            item.mImageContainer.setVisibility(View.GONE);
        }

        if (mPhotos != null && mPhotos.size() > 0) {
            for (int i = 0; i < mPhotos.size(); i++) {
                mViews.get(i).mImageContainer.setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     * Interface from pop-up picker dialog - form camera option
     */
    @Override
    public void onTakePhoto_Camera() {
        try {
            if (mPhotos.size() <= mViews.size() - 1) {
                mPickCamera();
            }
        } catch (Exception e) {
            mPhotoActivityInterface.onPhotosError();
        }
    }

    /**
     * Interface from pop-up picker dialog - form gallery option
     */
    @Override
    public void onTakePhoto_Gallery() {
        try {
            if (mPhotos.size() <= mViews.size() - 1) {
                mPickGallery();
            }
        } catch (Exception e) {
            mPhotoActivityInterface.onPhotosError();
        }
    }

    /**
     * Open gallery intent to pick photo
     */
    private void mPickGallery() {
        Intent mIntentGallery = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if (mIntentGallery.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(mIntentGallery, ACTION_GALLERY);
        }
    }

    /**
     * Open camera intent to take photo
     */
    private void mPickCamera() throws Exception {
        Intent nIntentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (nIntentCamera.resolveActivity(getPackageManager()) != null) {
            mBasePhotoUri = FileProvider.getUriForFile(this, getPackageName() + ".provider", createImageFile());
            nIntentCamera.putExtra(MediaStore.EXTRA_OUTPUT, mBasePhotoUri);
            startActivityForResult(nIntentCamera, ACTION_CAMERA);
        }
    }
//
//    protected void onActivityResult(int nRequestCode, int nResultCode, Intent nIntentReturned) {
//        super.onActivityResult(nRequestCode, nResultCode, nIntentReturned);
//        if (nRequestCode == ACTION_GALLERY && nResultCode == RESULT_OK) {
//            try {
//                nPhoto = new PhotoModel();
//                nPhoto.mFilePath = nIntentReturned.getData();
//                nPhoto.mPhotoFrom = Constants.TAKE_PHOTO_GALLERY;
//                mPhotos.add(nPhoto);
//                mPhotoActivityInterface.onPhotosRefreshAvailable(Constants.TAKE_PHOTO_GALLERY);
//                internal();
//            } catch (Exception e) {
//                mPhotoActivityInterface.onPhotosError();
//            }
//        } else if (nRequestCode == ACTION_CAMERA && nResultCode == RESULT_OK) {
//            nPhoto = new PhotoModel();
//
//            Bitmap nBitmap;
//
//            if (nIntentReturned != null && nIntentReturned.getExtras() != null) {
//                if (nIntentReturned.getExtras().containsKey("data")) {
//                    nBitmap = (Bitmap) nIntentReturned.getExtras().get("data");
//                } else {
//                    nBitmap = getBitmapFromUri();
//                }
//            } else {
//                nBitmap = getBitmapFromUri();
//            }
//
//            try {
//                nBitmap = handleSamplingAndRotationBitmap(this, mBasePhotoUri);
//            } catch (IOException e) {
//            }
//
//            try {
//                File outputDir = this.getCacheDir(); // context being the Activity pointer
//                File outputFile = File.createTempFile("prefix", ".jpg", outputDir);
//                FileOutputStream fos = new FileOutputStream(outputFile);
//                nBitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
//                nPhoto.mFilePath = Uri.fromFile(outputFile);
//                Log.d("dasdasdasa","BUN : "+nPhoto.mFilePathString);
//            } catch (Exception e ) {
//
//                Log.d("dasdasdasa",""+e.getMessage());
//            }
//
//            nPhoto.mBitmap = nBitmap;
//
//            nPhoto.mPhotoFrom = Constants.TAKE_PHOTO_CAMERA;
//            mPhotos.add(nPhoto);
//
//            internal();
//
//            mPhotoActivityInterface.onPhotosRefreshAvailable(Constants.TAKE_PHOTO_CAMERA);
//            Log.d("adadad", "camera 5 : ");
//        }
//    }
protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
    super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
    Log.d("adadad","camera 2 ");
    if (requestCode==ACTION_GALLERY && resultCode == RESULT_OK) {
        if(resultCode == RESULT_OK){
            Log.d("samsung","gallery : "+imageReturnedIntent.getData());
            nPhoto = new PhotoModel();
            nPhoto.mFilePath = imageReturnedIntent.getData();
            nPhoto.mPhotoFrom = Constants.TAKE_PHOTO_GALLERY;
            mPhotos.add(nPhoto);
            internal();
            mPhotoActivityInterface.onPhotosRefreshAvailable(Constants.TAKE_PHOTO_GALLERY);
            Log.d("adadad","gallery 2 ");
        }
    } else if (requestCode==ACTION_CAMERA  && resultCode == RESULT_OK) {

        nPhoto = new PhotoModel();

        Bitmap nBitmap;
        // getBitmapFromUri
        if (imageReturnedIntent != null && imageReturnedIntent.getExtras()!=null) {
            Bundle extras = imageReturnedIntent.getExtras();
            if (extras.containsKey("data")) {
                Log.d("adadadadasda","data 1");
                nBitmap = (Bitmap) extras.get("data");
            }
            else {
                Log.d("adadadadasda","data 2");
                nBitmap = optimizeBitmapFromUri(mBasePhotoUri);
            }
        }
        else {
            Log.d("adadadadasda","data 3");
            nBitmap = optimizeBitmapFromUri(mBasePhotoUri);
        }

        try {
            nBitmap = handleSamplingAndRotationBitmap(this,mBasePhotoUri);
        } catch (IOException e) {
            Log.d("errr"," 2 : "+e.getMessage());
        }

        try {
                File outputDir = this.getCacheDir(); // context being the Activity pointer
                File outputFile = File.createTempFile("prefix", ".jpg", outputDir);
                FileOutputStream fos = new FileOutputStream(outputFile);
            nBitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                nPhoto.mFilePath = Uri.fromFile(outputFile);
            } catch (Exception e ) {

                Log.d("errr"," 1 : "+e.getMessage());
            }

        nPhoto.mPhotoFrom = Constants.TAKE_PHOTO_CAMERA;
        mPhotos.add(nPhoto);
        mBitmapTransformers.recycle();
        nBitmap.recycle();
        internal();
        mPhotoActivityInterface.onPhotosRefreshAvailable(Constants.TAKE_PHOTO_CAMERA);
    }
}

    private Bitmap optimizeBitmapFromUri(Uri nPhotoUri) {
        try {
            int MAX_HEIGHT = 1024;
            int MAX_WIDTH = 1024;

            // First decode with inJustDecodeBounds=true to check dimensions
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            InputStream imageStream = getContentResolver().openInputStream(nPhotoUri);
            BitmapFactory.decodeStream(imageStream, null, options);
            imageStream.close();

            // Calculate inSampleSize
            options.inSampleSize = calculateInSampleSize(options, MAX_WIDTH, MAX_HEIGHT);

            // Decode bitmap with inSampleSize set
            options.inJustDecodeBounds = false;
            imageStream = getContentResolver().openInputStream(nPhotoUri);
            return BitmapFactory.decodeStream(imageStream, null, options);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        imageFilePath = image.getAbsolutePath();
        return image;
    }


    public Bitmap handleSamplingAndRotationBitmap(Context context, Uri selectedImage) throws IOException {
        int MAX_HEIGHT = 1024;
        int MAX_WIDTH = 1024;

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        InputStream imageStream = context.getContentResolver().openInputStream(selectedImage);
        BitmapFactory.decodeStream(imageStream, null, options);
        imageStream.close();

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, MAX_WIDTH, MAX_HEIGHT);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        imageStream = context.getContentResolver().openInputStream(selectedImage);
        mBitmapTransformers = BitmapFactory.decodeStream(imageStream, null, options);
        mBitmapTransformers = rotateImageIfRequired(context, mBitmapTransformers, selectedImage);
        return mBitmapTransformers;
    }

    private static int calculateInSampleSize(BitmapFactory.Options options,int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            // Calculate ratios of height and width to requested height and width
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            // Choose the smallest ratio as inSampleSize value, this will guarantee a final image
            // with both dimensions larger than or equal to the requested height and width.
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;

            // This offers some additional logic in case the image has a strange
            // aspect ratio. For example, a panorama may have a much larger
            // width than height. In these cases the total pixels might still
            // end up being too large to fit comfortably in memory, so we should
            // be more aggressive with sample down the image (=larger inSampleSize).

            final float totalPixels = width * height;

            // Anything more than 2x the requested pixels we'll sample down further
            final float totalReqPixelsCap = reqWidth * reqHeight * 2;

            while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
                inSampleSize++;
            }
        }
        return inSampleSize;
    }

    private static Bitmap rotateImageIfRequired(Context context, Bitmap img, Uri selectedImage) throws IOException {

        InputStream input = context.getContentResolver().openInputStream(selectedImage);
        ExifInterface ei;
        if (Build.VERSION.SDK_INT > 23) {
            Log.d("adadadadasda","test 1");
            ei = new ExifInterface(input); }
        else {
        Log.d("adadadadasda","test 2");
            ei = new ExifInterface(selectedImage.getPath()); }

        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

        Log.d("adadadadasda","test 3 : "+orientation+"/"+img.getWidth()+"/"+img.getHeight());

        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                return rotateImage(img, 90);
            case ExifInterface.ORIENTATION_ROTATE_180:
                return rotateImage(img, 180);
            case ExifInterface.ORIENTATION_ROTATE_270:
                return rotateImage(img, 270);
            case ExifInterface.ORIENTATION_UNDEFINED:
                if (img.getWidth()>img.getHeight()) {
                return rotateImage(img, 90); }
               else { return rotateImage(img,0); }
            default:
                return img;
        }
    }

    private static Bitmap rotateImage(Bitmap img, int degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        Bitmap rotatedImg = Bitmap.createBitmap(img, 0, 0, img.getWidth(), img.getHeight(), matrix, true);
        return rotatedImg;
    }

    public static Bitmap rotateBitmap(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

    private void internal() {
        int mCount = 0;

        for (PhotoModel item : mViews) {
            ((ImageView) item.mImageContainer.findViewWithTag("image")).setImageResource(R.drawable.ic_service_placeholder);
        }

        for (PhotoModel item : mPhotos) {
            showImage((ImageView) mViews.get(mCount).mImageContainer.findViewWithTag("image"), item);
            mCount++;
        }

        resetPhotoBox();
    }

    private void showImage(ImageView nImageView, PhotoModel nPhotoModel) {
        switch (nPhotoModel.mPhotoFrom) {
            case Constants.TAKE_PHOTO_CAMERA:
              Picasso.with(this).load(nPhotoModel.mFilePath).into(nImageView);
           //     nImageView.setImageBitmap(nPhotoModel.mBitmap);
                break;
            case Constants.TAKE_PHOTO_GALLERY:
                Picasso.with(this).load(nPhotoModel.mFilePath).into(nImageView);
                break;
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public static int getImageOrientation(String imagePath) {
        int rotate = 0;
        try {
            ExifInterface exif = new ExifInterface(imagePath);
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate = 270;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate = 90;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("camera222", "ROATIEEE : " + e.getMessage());
        }
        return rotate;
    }

//    public Uri getImageUri(Context inContext, Bitmap inImage) {
//        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
//        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
//        return Uri.parse(path);
//    }
//
//    private void uploadImage() {
//
//        if(mFilePath != null)
//        {
//            final ProgressDialog progressDialog = new ProgressDialog(this);
//            progressDialog.setTitle("Uploading...");
//            progressDialog.show();
//
//            StorageReference ref = storageReference.child("images/ceva");
//
//            Log.d("asdadadas",""+ref);
//
//            ref.putFile(mFilePath)
//                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                            progressDialog.dismiss();
//                            Toast.makeText(PhotoActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
//
//                            Log.d("sdaddas","1: "+taskSnapshot.getUploadSessionUri());
//                            Log.d("sdaddas","2: "+taskSnapshot.getBytesTransferred());
//                            Log.d("sdaddas","3: "+taskSnapshot.getMetadata());
//                            Log.d("sdaddas","4: "+taskSnapshot.getTotalByteCount());
//                            Log.d("sdaddas","4: "+taskSnapshot.getDownloadUrl());
//                        }
//                    })
//                    .addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            progressDialog.dismiss();
//                            Toast.makeText(PhotoActivity.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                    })
//                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
//                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
//                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
//                        }
//                    });
//        }
//    }


}
