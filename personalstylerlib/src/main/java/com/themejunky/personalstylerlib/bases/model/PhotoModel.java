package com.themejunky.personalstylerlib.bases.model;

import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.themejunky.personalstylerlib.utils.Constants;

import java.net.URL;

public class PhotoModel {
    public String mfirebase_storage_id;
    public String mName;
    public String mPhotoFrom; //ex : gallery or camera
    public Uri mFilePath;
    public Uri mCroppedFilePath;
    public String mFilePathString;
    public Bitmap mBitmap;
    public int mPosition;
    public String mWhichToUpload;

    public String mNameFolder;
    public String mNameCroppedImage;
    public String mNameOriginamImage;

    public String mDownloadFirebaseUrlCroppedImage;
    public String mDownloadFirebaseUrlOriginalImage;
    public RelativeLayout mImageContainer;

    public PhotoModel() {}

    public PhotoModel(RelativeLayout nImageContainer) {
        mImageContainer = nImageContainer;
    }

    public PhotoModel(String mDownloadFirebaseUrlOriginalImage,String mDownloadFirebaseUrlCroppedImage,String mfirebase_storage_id) {
        this.mDownloadFirebaseUrlOriginalImage = mDownloadFirebaseUrlOriginalImage;
        this.mDownloadFirebaseUrlCroppedImage = mDownloadFirebaseUrlCroppedImage;
        this.mfirebase_storage_id = mfirebase_storage_id;
        try {
            URL url = new URL(mDownloadFirebaseUrlCroppedImage);
            mCroppedFilePath = Uri.parse(url.toURI().toString());

            url = new URL(mDownloadFirebaseUrlOriginalImage);
            mFilePath = Uri.parse(url.toURI().toString());
        } catch (Exception ignore) {
        }
        this.mPhotoFrom = Constants.TAKE_PHOTO_WS;
    }
}