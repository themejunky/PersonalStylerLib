package com.themejunky.personalstylerlib.customdialogs.photo.take;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;

import com.themejunky.personalstylerlib.R;
import com.themejunky.personalstylerlib.bases.tools.Tools;
import com.themejunky.personalstylerlib.customdialogs.BaseDialog;
import com.themejunky.personalstylerlib.utils.Constants;

public class TakePhoto extends BaseDialog {
    private TakePhoto._Interface mListener;
    private TextView mChooseCamera, mChooseGallery;
    private static TakePhoto mInstance = null;

    public interface _Interface {
        void onTakePhoto_Camera();
        void onTakePhoto_Gallery();
    }

    public static TakePhoto getInstance() {
        if (mInstance == null)
            mInstance = new TakePhoto();
        return mInstance;
    }

    public void refreshContent(Context nContext, String nOptions, TakePhoto._Interface nListener) {
        /* inits*/
        mContext = nContext;
        mListener = nListener;
        mBuilder = new AlertDialog.Builder(mContext);

        mTools = Tools.getInstance(mContext);

        /* create view*/
        createView(R.layout.custom_dialog_take_photo);

        mChooseCamera = mContainer.findViewById(R.id.nChooseCamera);
        mChooseGallery = mContainer.findViewById(R.id.nChooseGalery);
        switch (nOptions) {
            case Constants.TAKE_PHOTO_CAMERA: mChooseCamera.setVisibility(View.VISIBLE); break;
            case Constants.TAKE_PHOTO_GALLERY: mChooseGallery.setVisibility(View.VISIBLE); break;
            case Constants.TAKE_PHOTO_BOTH:  mChooseCamera.setVisibility(View.VISIBLE); mChooseGallery.setVisibility(View.VISIBLE); break;
        }

        mChooseCamera.setOnClickListener(this);
        mChooseGallery.setOnClickListener(this);

        showDialog();
    }

    @Override
    public void onClick(View nView) {
        if (nView.equals(mChooseCamera)) {
            mListener.onTakePhoto_Camera();
            mDialog.dismiss();
        } else if (nView.equals(mChooseGallery)) {
            mListener.onTakePhoto_Gallery();
            mDialog.dismiss();
        }
    }
}