package com.themejunky.personalstylerlib.bases.tools;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.themejunky.personalstylerlib.bases.model.PhotoModel;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Tools_Photos extends ToolsBase {

    public Bitmap mBitmapTransformers;
    private FirebaseStorage mStorage;
    private StorageReference mStorageReference;

    public Bitmap optimizeBitmapFromUri(Activity nActivity, Uri nPhotoUri) {
        try {
            int MAX_HEIGHT = 1024;
            int MAX_WIDTH = 1024;

            // First decode with inJustDecodeBounds=true to check dimensions
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            InputStream imageStream = nActivity.getContentResolver().openInputStream(nPhotoUri);
            BitmapFactory.decodeStream(imageStream, null, options);
            imageStream.close();

            // Calculate inSampleSize
            options.inSampleSize = calculateInSampleSize(options, MAX_WIDTH, MAX_HEIGHT);

            // Decode bitmap with inSampleSize set
            options.inJustDecodeBounds = false;
            imageStream = nActivity.getContentResolver().openInputStream(nPhotoUri);
            return BitmapFactory.decodeStream(imageStream, null, options);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public Bitmap handleSamplingAndRotationBitmap(Activity nActivity, Uri selectedImage) throws IOException {
        mBitmapTransformers = optimizeBitmapFromUri(nActivity,selectedImage);
        mBitmapTransformers = rotateImageIfRequired(nActivity, mBitmapTransformers, selectedImage);
        return mBitmapTransformers;
    }
    public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
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

    public Bitmap rotateImageIfRequired(Context context, Bitmap img, Uri selectedImage) throws IOException {

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
            case ExifInterface.ORIENTATION_UNDEFINED:
                if (img.getWidth() > img.getHeight()) {
                    return rotateImage(img, 90);
                } else {
                    return rotateImage(img, 0);
                }
            default:
                return img;
        }
    }

    public Bitmap rotateImage(Bitmap img, int degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        Bitmap rotatedImg = Bitmap.createBitmap(img, 0, 0, img.getWidth(), img.getHeight(), matrix, true);
        return rotatedImg;
    }

    public File createImageFile(Activity nActivity) throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        File nFile = File.createTempFile("IMG_" + timeStamp + "_", ".jpg", nActivity.getExternalFilesDir(Environment.DIRECTORY_PICTURES));
        nFile.deleteOnExit();
        return nFile;
    }

    public List<PhotoModel> mUploadImagesToFirebase(Context nContext, List<PhotoModel> nPhotos) {

        mStorage = FirebaseStorage.getInstance();
        mStorageReference = mStorage.getReference();

        for (PhotoModel nPhoto: nPhotos) {

            if (nPhoto.mCroppedFilePath!=null) {
              //  mUploadImage(nPhoto.mNameFolder,nPhoto.mCroppedFilePath,nPhoto.mNameCroppedImage);

                mUploadImage(nPhoto);
            }

        }

        return nPhotos;
    }

    public void mUploadImage(final PhotoModel nPhoto) {
        if(nPhoto.mCroppedFilePath != null)
        {
            StorageReference ref = mStorageReference.child(nPhoto.mNameFolder).child(nPhoto.mNameCroppedImage);
            ref.putFile(nPhoto.mCroppedFilePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Log.d("new_service","poza firebase2 : "+taskSnapshot.getDownloadUrl());
                            nPhoto.mDownloadFirebaseUrlCroppedImage = taskSnapshot.getDownloadUrl().getPath();
                            //progressDialog.dismiss();
                            //Toast.makeText(RegisterAddServices.this, "Uploaded", Toast.LENGTH_SHORT).show();

//                            Log.d("sdaddas","1: "+taskSnapshot.getUploadSessionUri());
//                            Log.d("sdaddas","2: "+taskSnapshot.getBytesTransferred());
//                            Log.d("sdaddas","3: "+taskSnapshot.getMetadata());
//                            Log.d("sdaddas","4: "+taskSnapshot.getTotalByteCount());
//                            Log.d("sdaddas","4: "+taskSnapshot.getDownloadUrl());
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
//                            progressDialog.dismiss();
//                            Toast.makeText(RegisterAddServices.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
//                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });
        }
    }


}
