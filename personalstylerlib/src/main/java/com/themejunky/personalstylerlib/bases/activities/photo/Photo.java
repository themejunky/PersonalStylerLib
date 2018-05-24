package com.themejunky.personalstylerlib.bases.activities.photo;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

import com.themejunky.personalstylerlib.R;
import com.themejunky.personalstylerlib.bases.activities.cropping.CroppingPhoto;
import com.themejunky.personalstylerlib.bases.model.PhotoModel;
import com.themejunky.personalstylerlib.utils.Constants;


public class Photo extends PhotoBase {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fetchViews();
        initViews();
    }

    @Override
    public void onResume() {
        super.onResume();
        start();
    }

    @Override
    public void onClick(View nView) {
        mPhotos.remove(Integer.parseInt(nView.getTag().toString()));
        refreshPhotos();
    }

    @Override
    public int onPhotoPresenter_PhotoReady(PhotoModel nPhoto, String nType) {
        if (nType.equals(Constants.TAKE_PHOTO_CROPPED)) {
            mPhotos.add(nPhoto);
            refreshPhotos();
        } else {
            Intent intent = new Intent(this, CroppingPhoto.class);
            intent.putExtra(Constants.TAKE_PHOTO_FILE, nPhoto.mFilePath.toString());
            intent.putExtra(Constants.TAKE_PHOTO_RATIO, "1.8");
            startActivityForResult(intent, ACTION_CROP);
        }
        mTools.closeLoading();
        mPhotoActivityInterface.onPhotosRefreshAvailable(nType);
        return mPhotos.size();
    }

    /**
     * Initiate take photo (gallery and/or camera) with UI
     *
     * @param nListener  - listener to comunicate with the activity that triggers this process
     * @param nNoPic     - number of desired pictures
     * @param nViewGroup - view group in witch the UI will be deployed
     */
    protected void mActivatePhotoInternalAPI(_Interface nListener, int nNoPic, ViewGroup nViewGroup) {
        mPhotoActivityInterface = nListener;
        mPresenter = new PhotoPresenter(this, this);
        mPresenter.mSetLayout(nViewGroup, nNoPic, this);
    }

    /**
     * Interface from pop-up picker dialog - form camera option
     */
    @Override
    public void onTakePhoto_Camera() {
        if (mPhotos.size() <= mViews.size() - 1) {
            mPickCamera();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent nReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, nReturnedIntent);
        if (resultCode == RESULT_OK) {
            mTools.showLoading(null);
            switch (requestCode) {
                case ACTION_GALLERY:
                    mPresenter.mPreparePhotoGallery(nReturnedIntent);
                    break;
                case ACTION_CAMERA:
                    mPresenter.mPreparePhotoCamera(nReturnedIntent);
                    break;
                case ACTION_CROP:
                    mPresenter.mPreparePhotoCropped(Uri.parse(nReturnedIntent.getStringExtra(Constants.TAKE_PHOTO_FILE)));
                    break;
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == ACTION_CAMERA_PERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mPickCamera();
            } else {
                mTools.showSimpleSnackBar(findViewById(R.id.nContainer), R.string.camera_permission_error);
            }
        }
    }
}