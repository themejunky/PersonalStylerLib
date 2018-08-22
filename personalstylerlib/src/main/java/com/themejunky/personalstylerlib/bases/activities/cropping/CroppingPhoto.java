package com.themejunky.personalstylerlib.bases.activities.cropping;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import com.themejunky.personalstylerlib.R;
import com.themejunky.personalstylerlib.utils.Constants;

public class CroppingPhoto extends CroppingPhotoBase {
    private int _yDelta;
    private int mTopStartHeight,mCropFrameHeight;
    /* save when new sliding is active */
    private Boolean mStartSliding = false;
    /* parameters for top relative layout (about cropp frame) */
    private RelativeLayout.LayoutParams mParamsTop;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_cropping_photo);

        fetchViews();
        initViews();
    }

    @Override
    public void onResume() {
        super.onResume();
        start();
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        int Y = (int) event.getRawY();
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
                _yDelta = Y - lParams.topMargin;
                break;
            case MotionEvent.ACTION_UP:
                mStartSliding = false;
                break;
            case MotionEvent.ACTION_MOVE: {
                if (!mStartSliding) {
                    mParamsTop = (RelativeLayout.LayoutParams) mTop.getLayoutParams();
                    mTopStartHeight = mTop.getHeight();
                    mStartSliding = true;
                    mCropFrameHeight = mCropFrame.getHeight();
                }

              if (mImage.getHeight()-(mTopStartHeight+(Y - _yDelta))>mCropFrameHeight) {
                  mParamsTop.height = mTopStartHeight+(Y - _yDelta);
                  mTop.setLayoutParams(mParamsTop);
              } else {
                  mParamsTop.height = mImage.getHeight()-mCropFrameHeight;
                  mTop.setLayoutParams(mParamsTop);
              }
            }
            break;
        }
        return true;
    }

    @Override
    public void onGlobalLayout() {
        RelativeLayout.LayoutParams mWindowParam = (RelativeLayout.LayoutParams) mCropFrame.getLayoutParams();
        mWindowParam.height = (int) (mCropFrame.getWidth() / getRatio());
        mCropFrame.setLayoutParams(mWindowParam);
        mCropFrame.requestLayout();
        mCropFrame.getViewTreeObserver().removeOnGlobalLayoutListener(this);

        mTools.closeLoading();
        mCropFrame.setOnTouchListener(this);
    }

    @Override
    public void onCroppingPhotoPresenter_PhotoCopped(Uri nUriCropped) {
        mTools.closeLoading();

        Intent returnIntent = new Intent();
        returnIntent.putExtra(Constants.TAKE_PHOTO_FILE ,getIntent().getStringExtra(Constants.TAKE_PHOTO_FILE));
        returnIntent.putExtra(Constants.TAKE_PHOTO_POSITION ,getIntent().getStringExtra(Constants.TAKE_PHOTO_POSITION));
        returnIntent.putExtra(Constants.TAKE_PHOTO_FILE_CROP , nUriCropped.toString());
        setResult(Activity.RESULT_OK, returnIntent);

        finish();
    }

    @Override
    public void onCustomButtonClick(View nView) {
        if (mCO(nView,R.string.crop_action)) {
            //mTools.showLoading(null);
            mPresenter.mCroppingImage();
        }
    }
}