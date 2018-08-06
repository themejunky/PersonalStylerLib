package com.themejunky.personalstylerlib.bases.activities.cropping;

import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.squareup.picasso.Picasso;
import com.themejunky.personalstylerlib.R;
import com.themejunky.personalstylerlib.bases.activities.custom.CustomActivity;
import com.themejunky.personalstylerlib.bases.tools.Tools;
import com.themejunky.personalstylerlib.customviews.CustomButton;
import com.themejunky.personalstylerlib.utils.Constants;

public abstract class CroppingPhotoBase extends CustomActivity implements View.OnTouchListener, ViewTreeObserver.OnGlobalLayoutListener, CroppingPhotoContract.BaseView, CroppingPhotoPresenter._Interface, CustomButton.Custom_Button_Interface {

    protected ImageView mImage;
    protected RelativeLayout mCropFrame,mTop;
    protected CroppingPhotoPresenter mPresenter;
    protected CustomButton mCrop;
    protected Double mCropRation;
    protected Tools mTools;

    @Override
    public void fetchViews() {
        /* ImageView */
        mImage = findViewById(R.id.nImage);
        /* RelativeLayouts */
        mCropFrame = findViewById(R.id.nCropFrame);
        mTop = findViewById(R.id.nTop);
        /* CustomButton */
        mCrop =  findViewById(R.id.nCrop);
    }

    @Override
    public void initViews() {
        mTools = Tools.getInstance(this);
        mPresenter = new CroppingPhotoPresenter(this,this);

        //mTools.showLoading(null);
        mCropFrame.getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    @Override
    public void start() {

        if (getIntent()!=null && getIntent().getStringExtra(Constants.TAKE_PHOTO_FILE)!=null) {

            mCropRation = Double.parseDouble(getIntent().getStringExtra(Constants.TAKE_PHOTO_RATIO));

            Picasso.with(this).load(Uri.parse(getIntent().getStringExtra(Constants.TAKE_PHOTO_FILE))).into(mImage, new com.squareup.picasso.Callback() {
                @Override
                public void onSuccess() {
                    mCrop.setListener(CroppingPhotoBase.this);
                }

                @Override
                public void onError() {
                    mShowError();
                }
            });
        }
    }

    @Override
    public void unexpectedError() {
        mShowError();
    }

    @Override
    public void unexpectedPresenterError() {
        mShowError();
    }

    @Override
    public double getRatio() {
        Log.d("dasdasda","aa"+mCropRation);
        return 1.8;
    }

    @Override
    public ImageView getImageToCropFrom() {
        return mImage;
    }

    @Override
    public RelativeLayout getTopContainer() {
        return mTop;
    }

    private void mShowError() {
        mTools.showSimpleSnackBar(findViewById(R.id.nContainer),R.string.unexpected_error);
    }
}
