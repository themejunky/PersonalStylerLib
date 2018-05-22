package com.themejunky.personalstylerlib.bases.activities.photo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.themejunky.personalstylerlib.R;
import com.themejunky.personalstylerlib.bases.activities.cropping.CroppingPhoto;
import com.themejunky.personalstylerlib.bases.model.PhotoModel;
import com.themejunky.personalstylerlib.bases.tools.Tools;
import com.themejunky.personalstylerlib.customdialogs.photo.TakePhoto;
import com.themejunky.personalstylerlib.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class PhotoActivity extends AppCompatActivity implements TakePhoto._Interface, View.OnClickListener, PhotoPresenter._Interface {
    public Tools mTools;
    private Uri mBasePhotoUri;
    private PhotoPresenter mPresenter;


    ProgressDialog progressBar;
    @Override
    public void onClick(View nView) {
        mPhotos.remove(Integer.parseInt(nView.getTag().toString()));
        internal();
    }

    @Override
    public void onPhotoPresenter_LayoutReady(List<PhotoModel> nViews) {
        mViews = nViews;
        resetPhotoBox();
    }

    @Override
    public int onPhotoPresenter_PhotoReady(PhotoModel nPhoto, String nType, int nPosition) {

        if (nPosition == Constants.TAKE_PHOTO) {
            mPhotos.add(nPhoto);
            showDialog();
        } else {
            mPhotos.set(nPosition, nPhoto);

            closeDialog();
            Intent intent = new Intent(this, CroppingPhoto.class);
            intent.putExtra("EXTRA_FILE_PATH", nPhoto.mFilePath.toString());
            startActivityForResult(intent,ACTION_CROP);
        }
    //    internal();

        mPhotoActivityInterface.onPhotosRefreshAvailable(nType);


        return mPhotos.size();
    }

    @Override
    public Uri onPhotoPresenter_GetBasePhotoUri() {
        return mBasePhotoUri;
    }


    public interface _Interface {
        void onPhotosRefreshAvailable(String nType);

        void onPhotosError();
    }

    protected List<PhotoModel> mPhotos = new ArrayList<>();
    protected List<PhotoModel> mViews = new ArrayList<>();
    protected _Interface mPhotoActivityInterface;
    private int ACTION_CAMERA = 1;
    private int ACTION_GALLERY = 2;
    private int ACTION_CROP = 3;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTools = Tools.getInstance(this);
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
            mBasePhotoUri = FileProvider.getUriForFile(this, getPackageName() + ".provider", mTools.createImageFile(this));
            nIntentCamera.putExtra(MediaStore.EXTRA_OUTPUT, mBasePhotoUri);
            startActivityForResult(nIntentCamera, ACTION_CAMERA);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent nReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, nReturnedIntent);
        if (requestCode == ACTION_GALLERY && resultCode == RESULT_OK) {

            mPresenter.mPreparePhotoGallery(nReturnedIntent);
        } else if (requestCode == ACTION_CAMERA && resultCode == RESULT_OK) {

            mPresenter.mPreparePhotoCamera(nReturnedIntent);
        }
    }

    private void internal() {
        int mCount = 0;

        for (PhotoModel item : mViews) {
            ((ImageView) item.mImageContainer.findViewWithTag("image")).setImageResource(R.drawable.ic_service_placeholder);
        }

        for (PhotoModel item : mPhotos) {
            if (!item.mPhotoFrom.equals(Constants.TAKE_PHOTO_LOADING)) {
                Picasso.with(this).load(item.mFilePath).placeholder(R.drawable.ic_pic_delete).into((ImageView) mViews.get(mCount).mImageContainer.findViewWithTag("image"));
            } else {
                Picasso.with(this).load(R.drawable.ic_pic_delete).into((ImageView) mViews.get(mCount).mImageContainer.findViewWithTag("image"));
            }
            mCount++;
        }
        resetPhotoBox();
    }



    private void showDialog() {
        progressBar = new ProgressDialog(this);
        progressBar.setCancelable(false);
        progressBar.setMessage("Please wait ...");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.show();
    }

    private void closeDialog() {
        progressBar.dismiss();
    }
}