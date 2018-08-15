package com.themejunky.personalstylerlib.customdialogs.photo.edit;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.themejunky.personalstylerlib.R;
import com.themejunky.personalstylerlib.bases.tools.Tools;
import com.themejunky.personalstylerlib.customdialogs.BaseDialog;

public class EditPhoto extends BaseDialog {
    private EditPhoto._Interface mListener;
    private static EditPhoto mInstance = null;
    private int mPhotoPositionFromModel;
    public interface _Interface {
        void onEditPhoto_Edit(int nPhotoPositionFromModel);
        void onEditPhoto_Delete(int nPhotoPositionFromModel);
    }

    public static EditPhoto getInstance() {
        if (mInstance == null)
            mInstance = new EditPhoto();
        return mInstance;
    }

    public void refreshContent(Context nContext, EditPhoto._Interface nListener, int nPhotoPositionFromModel) {
        /* inits*/
        mContext = nContext;
        mListener = nListener;
        mBuilder = new AlertDialog.Builder(mContext);
        mPhotoPositionFromModel = nPhotoPositionFromModel;

        mTools = Tools.getInstance(mContext);

        /* create view*/
        createView(R.layout.custom_dialog_edit_photo);

        mContainer.findViewById(R.id.nChooseDelete).setOnClickListener(this);
        mContainer.findViewById(R.id.nChooseEdit).setOnClickListener(this);
        mContainer.findViewById(R.id.nCancel).setOnClickListener(this);
        showDialog();
    }

    public void disableEdit() {
        mContainer.findViewById(R.id.nChooseEdit).setVisibility(View.GONE);
        mContainer.findViewById(R.id.nOptionDivider).setVisibility(View.GONE);
    }


    public void enableEdit() {
        mContainer.findViewById(R.id.nChooseEdit).setVisibility(View.VISIBLE);
        mContainer.findViewById(R.id.nOptionDivider).setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View nView) {
        if (nView.getId()==R.id.nChooseDelete) {
            mListener.onEditPhoto_Delete(mPhotoPositionFromModel);
            mDialog.dismiss();
        } else if (nView.getId()==R.id.nChooseEdit) {
            mListener.onEditPhoto_Edit(mPhotoPositionFromModel);
            mDialog.dismiss();
        } else if (nView.getId()==R.id.nCancel) {
            mDialog.dismiss();
        }
    }
}
