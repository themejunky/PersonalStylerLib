package com.themejunky.personalstylerlib.customdialogs;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.themejunky.personalstylerlib.R;
import com.themejunky.personalstylerlib.bases.tools.Tools;

public abstract class BaseDialog implements View.OnClickListener {
    protected Context mContext;

    protected AlertDialog.Builder mBuilder;
    protected AlertDialog mDialog;
    protected Tools mTools;
    protected LinearLayout mContainer;

    /**
     * Create the mContainer view with the desired custom layout
     * @param nCustomLayout - desired layout to be inflated
     */
    protected void createView(int nCustomLayout) {
        LayoutInflater factory = LayoutInflater.from(mContext);
        View DialogInflateView = factory.inflate(nCustomLayout, null);
        mContainer = DialogInflateView.findViewById(R.id.nContainer);
    }

    /**
     * Create and show up current dialog
     */
    public void showDialog() {
        mBuilder.setView(mContainer);
        mDialog = mBuilder.create();
        mDialog.show();
    }

    public AlertDialog.Builder returnDialog() {
        mBuilder.setView(mContainer);

        return mBuilder;
    }
}
