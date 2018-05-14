package com.themejunky.personalstylerlib.bases;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
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
    @Override
    public void onClick(View nView) {
        mPhotos.remove(Integer.parseInt(nView.getTag().toString()));
        internal();
    }

    public interface _Interface {
        void onPhotosRefreshAvailable(String nType);
    }

    protected Uri mFilePath;
    protected FirebaseStorage mFireBaseStorage;
    protected  StorageReference storageReference;
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

    Uri photoUri;
    protected void mActivatePhotoInternalAPI(_Interface nListener, int nNoPic, ViewGroup nViewGroup) {
        mPhotoActivityInterface = nListener;
        Log.d("adadad","xxx 1 ");
        LinearLayout nContainer = mTools.getMainContainerAfterInflate(nViewGroup,R.layout.custom_pictures).findViewById(R.id.nPicMainContainer);

        mViews = new ArrayList<>();
        RelativeLayout nItem;
        for (int i=0;i<nNoPic;i++) {

            nItem = (RelativeLayout) mTools.getMainContainerAfterInflate(nContainer,R.layout.custom_pictures_item);
            nItem.setTag(String.valueOf(i));
            mViews.add(new PhotoModel(nItem));
        }
        Log.d("adadad","xxx 2 ");
//
//
//
//        for(int i=0;i< ((LinearLayout) nViewGroup.findViewById(R.id.nPicMainContainer)).getChildCount();i++) {
//
//            if (i<nNoPic) {
//                mViews.add(new PhotoModel((RelativeLayout) ((LinearLayout) nViewGroup.findViewById(R.id.nPicMainContainer)).getChildAt(i)));
//            }
//
//        }
//
        activation();
    }

    protected void activation() {

        for (PhotoModel item: mViews) {
            item.mImageContainer.setVisibility(View.GONE);
        }

        if (mPhotos!=null && mPhotos.size()>0) {
            for(int i=0;i<mPhotos.size();i++) {
                mViews.get(i).mImageContainer.setVisibility(View.VISIBLE);
            }
        }

        for (PhotoModel item: mViews) {
            item.mImageContainer.setOnClickListener(this);
        }
    }
    public Bitmap getBitmapFromUri() {

        getContentResolver().notifyChange(photoUri, null);
        ContentResolver cr = getContentResolver();
        Bitmap bitmap;

        try {
            bitmap = android.provider.MediaStore.Images.Media.getBitmap(cr, photoUri);
            return bitmap;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    protected void mPickCamera() {


try {
    Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    File outputDir = this.getCacheDir(); // context being the Activity pointer
    outputFiletest = File.createTempFile("prefix", ".jpg", outputDir);

    //   output=new File(dir, "CameraContentDemo.jpeg");
    i.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(outputFiletest));

    startActivityForResult(i, ACTION_CAMERA);
    Log.d("adada555d", "camera merge");
}
catch (Exception e) {
    Log.d("adada555d", "camera naspa : "+e.getMessage());
}
    }



    protected void mPickGallery() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto , ACTION_GALLERY);
    }

    @Override
    public void onTakePhoto_Camera() {
        if ( mPhotos.size()<=mViews.size()-1) {
            openCameraIntent(); }
    }

    @Override
    public void onTakePhoto_Gallery() {
        if ( mPhotos.size()<=mViews.size()-1) {
        mPickGallery(); }
    }

    private void openCameraIntent() {
        Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (pictureIntent.resolveActivity(getPackageManager()) != null) {

            File photoFile = null;
            try {
                photoFile = createImageFile();
            }
            catch (IOException e) {
                e.printStackTrace();
                return;
            }
             photoUri = FileProvider.getUriForFile(this, getPackageName() +".provider", photoFile);
            pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            startActivityForResult(pictureIntent, ACTION_CAMERA);
        }
    }


    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName,".jpg",storageDir);
        imageFilePath = image.getAbsolutePath();
        return image;
    }

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

           // getBitmapFromUri
            if (imageReturnedIntent != null && imageReturnedIntent.getExtras()!=null) {
                Bundle extras = imageReturnedIntent.getExtras();
                if (extras.containsKey("data")) {
                    nPhoto.mBitmap = (Bitmap) extras.get("data");
                }
                else {
                    nPhoto.mBitmap = getBitmapFromUri();
                }
            }
            else {
                nPhoto.mBitmap = getBitmapFromUri();
            }

            try {
                nPhoto.mBitmap = handleSamplingAndRotationBitmap(this,photoUri);
                Log.d("adasdadad","BINE : ");
            } catch (IOException e) {
                Log.d("adasdadad","NASPA : "+e.getMessage());
            }



            // imageView.setImageBitmap(bitmap);
            //imageView.setImageURI(photoUri);
            /*



            FileOutputStream fout;
            try {
//                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), photoUri);
//                bitmap = rotateBitmap(bitmap,45);
//
//                fout = new FileOutputStream(photoUri.getPath());
//              //  bitmap.compress(Bitmap.CompressFormat.PNG, 70, fout);



                BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
                bitmapOptions.inSampleSize = 4;
                Bitmap bmOriginal = MediaStore.Images.Media.getBitmap(this.getContentResolver(), photoUri);

                Matrix matrix = new Matrix();

                matrix.postRotate(getImageOrientation(new File(photoUri.getPath()).getAbsolutePath()));

                bmOriginal = Bitmap.createBitmap(bmOriginal, 0, 0, bmOriginal.getWidth(), bmOriginal.getHeight(), matrix, true);

                nPhoto.mBitmap = bmOriginal;


                Log.d("camera222","BINEEE : "+getImageOrientation(new File(photoUri.getPath()).getAbsolutePath()));
            } catch (Exception e) {
                Log.d("camera222","BINENASPA : "+e.getMessage());
            }
*/


                nPhoto.mFilePathString = imageFilePath;
//            FileOutputStream fos = null;
//            Log.d("samsung","camera 3 : "+nPhoto.mBitmap);
//try {
//    File outputDir = this.getCacheDir(); // context being the Activity pointer
//    File outputFile = File.createTempFile("prefix", "extension", outputDir);
//   // outputDir.deleteOnExit();
//    fos = new FileOutputStream(outputFile);
//    ((Bitmap) imageReturnedIntent.getExtras().get("data")).compress(Bitmap.CompressFormat.PNG, 100, fos);
//
//    nPhoto.mFilePathString = outputFile.getAbsolutePath();
//    nPhoto.mFilePath = Uri.fromFile(outputFile);
//    Log.d("adadad","camera 4 ");
//}catch (Exception e) {
//    Log.d("adadad","camera 41 : "+e.getMessage());
//} finally {
//    try {
//        fos.close();
//    } catch (Exception e) {
//        Log.d("adadad","camera 42 : "+e.getMessage());
//    }
//}
            Log.d("adadad","02 : ");
                nPhoto.mPhotoFrom = Constants.TAKE_PHOTO_CAMERA;
                mPhotos.add(nPhoto);

            internal();

                mPhotoActivityInterface.onPhotosRefreshAvailable(Constants.TAKE_PHOTO_CAMERA);
            Log.d("adadad","camera 5 : ");
        }


    }
    public static Bitmap handleSamplingAndRotationBitmap(Context context, Uri selectedImage)
            throws IOException {
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
        Bitmap img = BitmapFactory.decodeStream(imageStream, null, options);

        img = rotateImageIfRequired(context, img, selectedImage);
        return img;
    }

    private static int calculateInSampleSize(BitmapFactory.Options options,
                                             int reqWidth, int reqHeight) {
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
        if (Build.VERSION.SDK_INT > 23)
            ei = new ExifInterface(input);
        else
            ei = new ExifInterface(selectedImage.getPath());

        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                return rotateImage(img, 90);
            case ExifInterface.ORIENTATION_ROTATE_180:
                return rotateImage(img, 180);
            case ExifInterface.ORIENTATION_ROTATE_270:
                return rotateImage(img, 270);
            default:
                return img;
        }
    }

    private static Bitmap rotateImage(Bitmap img, int degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        Bitmap rotatedImg = Bitmap.createBitmap(img, 0, 0, img.getWidth(), img.getHeight(), matrix, true);
        img.recycle();
        return rotatedImg;
    }
    public static Bitmap rotateBitmap (Bitmap source, float angle){
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return  Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),matrix,true);
    }

    private void internal() {
        int mCount =0;

        for (PhotoModel item: mViews) {
            ((ImageView) item.mImageContainer.findViewWithTag("image")).setImageResource(R.drawable.ic_service_placeholder);
        }

        for (PhotoModel item : mPhotos) {
            showImage((ImageView) mViews.get(mCount).mImageContainer.findViewWithTag("image"),item);
            mCount++;
        }

        activation();
    }

    private void showImage(ImageView nImageView, PhotoModel nPhotoModel) {
        switch (nPhotoModel.mPhotoFrom) {
            case Constants.TAKE_PHOTO_CAMERA: Log.d("nasoale2","afiseaza : "+nPhotoModel.mFilePathString); /* Picasso.with(this).load(photoUri).into(nImageView); */ nImageView.setImageBitmap(nPhotoModel.mBitmap);  break;
            case Constants.TAKE_PHOTO_GALLERY : Picasso.with(this).load(nPhotoModel.mFilePath).into(nImageView);  break;
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
            Log.d("camera222","ROATIEEE : "+e.getMessage());
        }
        return rotate;
    }
//
//    public File ceva (Bitmap mBitmap) {
//        File f3=new File(Environment.getExternalStorageDirectory()+"/inpaint/");
//        if(!f3.exists())
//            f3.mkdirs();
//        OutputStream outStream = null;
//        File file = new File(Environment.getExternalStorageDirectory() + "/inpaint/"+System.currentTimeMillis()+".png");
//        try {
//            outStream = new FileOutputStream(file);
//            mBitmap.compress(Bitmap.CompressFormat.PNG, 85, outStream);
//            outStream.close();
//            Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return file;
//    }
//
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
