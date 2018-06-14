package com.themejunky.personalstylerlib.bases.activities.photo;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.themejunky.personalstylerlib.R;
import com.themejunky.personalstylerlib.bases.activities.cropping.CroppingPhoto;
import com.themejunky.personalstylerlib.bases.model.PhotoModel;
import com.themejunky.personalstylerlib.customdialogs.photo.edit.EditPhoto;
import com.themejunky.personalstylerlib.utils.Constants;


public class Photo extends PhotoBase {

    private String mRatio;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onClick(View nView) {
        EditPhoto.getInstance().refreshContent(this,this,Integer.parseInt(nView.getTag().toString()));
    }

    @Override
    public int onPhotoPresenter_PhotoReady(PhotoModel nPhoto, String nType, int nPosition) {

        int mReturn = mPhotos.size();

            if (nType.equals(Constants.TAKE_PHOTO_BEFORE_CROP) && nPosition==Constants.TAKE_PHOTO) {
                 nPhoto.mPosition = mReturn;
                 mPhotos.add(nPhoto);
            } else if (nType.equals(Constants.TAKE_PHOTO_GALLERY)) {
                mPhotos.set(nPosition,nPhoto);
                mStartCropping(nPhoto);
            } else if (nType.equals(Constants.TAKE_PHOTO_CAMERA) ) {
                mPhotos.set(nPosition,nPhoto);
                mStartCropping(nPhoto);
            } else if (nType.equals(Constants.TAKE_PHOTO_CROPPED)) {


                nPhoto.mfirebase_storage_id = mPhotos.get(nPhoto.mPosition).mfirebase_storage_id;

                Log.d("transfer","asdasd "+nPhoto.mPosition+"/"+ nPhoto.mfirebase_storage_id );
                mPhotos.set(nPhoto.mPosition,nPhoto);

            }
            refreshPhotos();
            mPhotoActivityInterface.onPhotosRefreshAvailable(nType);
            mTools.closeLoading();
            return mReturn;
    }

    protected void mStartCropping(PhotoModel nPhoto) {
        Intent intent = new Intent(this, CroppingPhoto.class);
        intent.putExtra(Constants.TAKE_PHOTO_FILE, nPhoto.mFilePath.toString());
        intent.putExtra(Constants.TAKE_PHOTO_RATIO, mRatio);
        intent.putExtra(Constants.TAKE_PHOTO_POSITION, String.valueOf(nPhoto.mPosition));
        startActivityForResult(intent, ACTION_CROP);
    }

    /**
     * Initiate take photo (gallery and/or camera) with UI
     *
     * @param nListener  - listener to comunicate with the activity that triggers this process
     * @param nNoPic     - number of desired pictures
     * @param nViewGroup - view group in witch the UI will be deployed
     */
    protected void mActivatePhotoInternalAPI(_Interface nListener, int nNoPic, String nRatio, ViewGroup nViewGroup) {
        mRatio = nRatio;
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
                    mPresenter.mPreparePhotoCropped(nReturnedIntent);
                    Log.d("transfer","asdas "+mPhotos.get(Integer.parseInt(nReturnedIntent.getStringExtra(Constants.TAKE_PHOTO_POSITION))).mfirebase_storage_id);
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

    @Override
    public void onEditPhoto_Edit(int nPhotoPositionFromModel) {
        Log.d("transfer",""+ mPhotos.get(nPhotoPositionFromModel).mfirebase_storage_id);
        mPhotos.get(nPhotoPositionFromModel).mPosition = nPhotoPositionFromModel;
        mStartCropping(mPhotos.get(nPhotoPositionFromModel));

    }

    @Override
    public void onEditPhoto_Delete(int nPhotoPositionFromModel) {
        mPhotos.remove(nPhotoPositionFromModel);
        refreshPhotos();
    }
}