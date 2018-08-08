package com.themejunky.personalstylerlib.bases.activities.photo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.themejunky.personalstylerlib.R;
import com.themejunky.personalstylerlib.bases.activities.custom.CustomActivity;
import com.themejunky.personalstylerlib.bases.model.PhotoModel;
import com.themejunky.personalstylerlib.bases.tools.Tools;
import com.themejunky.personalstylerlib.customdialogs.photo.edit.EditPhoto;
import com.themejunky.personalstylerlib.customdialogs.photo.take.TakePhoto;
import com.themejunky.personalstylerlib.utils.Constants;
import com.themejunky.personalstylerlib.utils.GlideApp;

import java.util.ArrayList;
import java.util.List;

public abstract class PhotoBase extends CustomActivity implements PhotoContract.BaseView, TakePhoto._Interface, View.OnClickListener, PhotoPresenter._Interface, EditPhoto._Interface {

    protected List<PhotoModel> mPhotos = new ArrayList<>();
    protected List<PhotoModel> mViews = new ArrayList<>();
    protected Uri mBasePhotoUri;
    protected PhotoPresenter mPresenter;
    protected _Interface mPhotoActivityInterface;
    protected final int ACTION_CAMERA = 1;
    protected final int ACTION_CAMERA_PERMISSION = 10;
    protected final int ACTION_GALLERY = 2;
    protected final int ACTION_CROP = 3;

    public interface _Interface {
        void onPhotosRefreshAvailable(String nType);
        void onPhotosError();
    }

     /**
     * Open gallery intent to pick photo
     */
    protected void mPickGallery() {
        Intent mIntentGallery = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if (mIntentGallery.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(mIntentGallery, ACTION_GALLERY);
        }
    }

    /**
     * Open camera intent to take photo (check if permission was granded)
     */
    protected void mPickCamera() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, ACTION_CAMERA_PERMISSION);
            return;
        }

        try {
            Intent nIntentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (nIntentCamera.resolveActivity(getPackageManager()) != null) {
                mBasePhotoUri = FileProvider.getUriForFile(this, getPackageName() + ".provider", mTools.createImageFile(this));
                nIntentCamera.putExtra(MediaStore.EXTRA_OUTPUT, mBasePhotoUri);
                startActivityForResult(nIntentCamera, ACTION_CAMERA);
            }
        } catch (Exception e) {
            mTools.showSimpleSnackBar(findViewById(R.id.nContainer),R.string.unexpected_error);
            mPhotoActivityInterface.onPhotosError();
        }
    }

    @Override
    public Uri onPhotoPresenter_GetBasePhotoUri() {
        return mBasePhotoUri;
    }

    @Override
    public void onPhotoPresenter_LayoutReady(List<PhotoModel> nViews) {
        mViews = nViews;
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

    protected void refreshPhotos() {
        int mCount = 0;

            for (PhotoModel item : mPhotos) {

                if (!item.mPhotoFrom.equals(Constants.TAKE_PHOTO_LOADING)) {
                 //   Picasso.get().load(item.mCroppedFilePath).placeholder(R.drawable.ic_service_placeholder).memoryPolicy(MemoryPolicy.NO_CACHE,MemoryPolicy.NO_STORE).into((ImageView) mViews.get(mCount).mImageContainer.getChildAt(0));
                      GlideApp.with(this).load(item.mCroppedFilePath).placeholder(R.drawable.ic_service_placeholder).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)).into((ImageView) mViews.get(mCount).mImageContainer.getChildAt(0));
                } else {
                  //  Picasso.get().load(R.drawable.ic_service_placeholder).memoryPolicy(MemoryPolicy.NO_CACHE,MemoryPolicy.NO_STORE).into((ImageView) mViews.get(mCount).mImageContainer.getChildAt(0));
                     GlideApp.with(this).load(R.drawable.ic_service_placeholder).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)).into((ImageView) mViews.get(mCount).mImageContainer.getChildAt(0));
                }
                mCount++;
            }
        resetPhotoBox();
    }
}
