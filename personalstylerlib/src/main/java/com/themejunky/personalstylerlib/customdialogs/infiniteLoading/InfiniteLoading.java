package com.themejunky.personalstylerlib.customdialogs.infiniteLoading;

import android.content.Context;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.rw.loadingdialog.LoadingView;
import com.themejunky.personalstylerlib.R;
import com.themejunky.personalstylerlib.bases.tools.Tools;
import com.themejunky.personalstylerlib.customdialogs.BaseDialog;

public class InfiniteLoading extends BaseDialog {

    private static InfiniteLoading mInstance = null;
    private InfiniteLoading._Interface mListener;

    public interface _Interface {
    }


    public static InfiniteLoading getInstance() {
        if (mInstance == null)
            mInstance = new InfiniteLoading();
        return mInstance;
    }

    public void refreshContent(Context nContext, InfiniteLoading._Interface nListener) {
        /* inits*/
        mContext = nContext;
        mListener = nListener;
        mBuilder = new AlertDialog.Builder(mContext);
        mTools = Tools.getInstance(mContext);
        /* create view*/
        createView(R.layout.custom_dialog_infinite_loading);






Log.d("adadasdadas","cont : "+mContainer);
        final LoadingView loadingView = new LoadingView.Builder(mContext).setBackgroundColor(Color.GRAY).attachTo((RelativeLayout) mContainer.findViewById(R.id.nFrameasdasd));
        loadingView.show();

        showDialog();
    }

    @Override
    public void onClick(View v) {

    }
}
