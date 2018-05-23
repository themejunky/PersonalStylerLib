package com.themejunky.personalstylerlib.bases.model;

import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class PhotoModel {
    public String mName;
    public String mPhotoFrom; //ex : gallery or camera
    public Uri mFilePath;
    public Uri mCroppedFilePath;
    public String mFilePathString;
    public Bitmap mBitmap;

    public RelativeLayout mImageContainer;

    public PhotoModel() {}

    public PhotoModel(RelativeLayout nImageContainer) {
        mImageContainer = nImageContainer;
    }
}