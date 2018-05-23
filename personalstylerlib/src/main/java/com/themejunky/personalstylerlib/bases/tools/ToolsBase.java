package com.themejunky.personalstylerlib.bases.tools;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.themejunky.personalstylerlib.R;
import com.themejunky.personalstylerlib.bases.model.Item;
import com.themejunky.personalstylerlib.bases.tools.model.NewScheduleMonths;
import com.themejunky.personalstylerlib.customviews.CustomInput;
import com.themejunky.personalstylerlib.utils.SimpleSpinnerLeftAdapter;
import com.themejunky.personalstylerlib.utils.SimpleSpinnerRightAdapter;
import com.themejunky.personalstylerlib.utils.SpeedyLinearLayoutManager;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ToolsBase {

    static Context mContext;
    static Tools mInstance = null;

    public Activity mActivity;
    public View.OnClickListener mViewOnClickListener;

    public int mRowHeight;
    private Snackbar mSnackBar;
    private ProgressDialog mProgressBar;


    public Locale getLocale() {
        return new Locale("ro_RO");
    }
    public float getDensity() {
        return mContext.getResources().getDisplayMetrics().density;
    }
    public int getHourRowHeight() {
        return mRowHeight;
    }

    public void setActivity(Activity nActivity) {
        this.mActivity = nActivity;
    }

    public void setViewListener(View.OnClickListener nViewOnClickListener) {
        this.mViewOnClickListener = nViewOnClickListener;
    }
    public String getString(int nStringResources) {
        return mContext.getResources().getString(nStringResources);
    }

    public int getColor(int nColorResources) {
        return mContext.getResources().getColor(nColorResources);
    }

    /**
     * Chech if provided string is Email
     * @param nEmail - string
     * @return true/false
     */
    public boolean isEmailValid(String nEmail) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(nEmail);
        return matcher.matches();
    }

    /**
     * Chech if provided string is Phone number
     * @param nPhone - string
     * @return true/false
     */
    public boolean isPhoneValid(String nPhone) {
        return android.util.Patterns.PHONE.matcher(nPhone).matches();
    }

    /**
     * Create md5 crytion
     * @param nString - string ( password ) to crypt
     * @return - md5
     */
    public String md5(final String nString) {
        final String MD5 = "MD5";
        try {
            MessageDigest digest = java.security.MessageDigest.getInstance(MD5);
            digest.update(nString.getBytes());
            byte messageDigest[] = digest.digest();
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2) h = "0".concat(h);
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * Find and select the desired value
     * @param nModelWithValues - model with all values
     * @param nMatchToFound - values to found and select
     * @param nSpinner - spinner
     */
    public void setSpinnerValue(List<Item> nModelWithValues, String nMatchToFound, CustomInput nSpinner) {
        int selection=0;
        for (Item nItem : nModelWithValues) {
            if (nItem.getValue().equals(nMatchToFound)) {
                ((Spinner) nSpinner.findViewById(R.id.nSpinner)).setSelection(selection);
            } else {
                selection++;
            }
        }
    }

    public boolean canScroll(ScrollView scrollView) {
        View child = scrollView.getChildAt(0);
        if (child != null) {
            int childHeight = (child).getHeight();
            return scrollView.getHeight() < childHeight + scrollView.getPaddingTop() + scrollView.getPaddingBottom();
        }
        return false;
    }


    /**
     * Sets Grid Manager Horizontal
     * @param list - recyclerview list
     * @param adapter - adapter
     * @param nColumns - nr of columbs
     * @return - Grid Layout Manager for future
     */
    public GridLayoutManager setList_GridLayoutManager_Horizontal(final RecyclerView list, final RecyclerView.Adapter adapter, int nColumns) {
        GridLayoutManager GL = new GridLayoutManager(mContext, nColumns, LinearLayoutManager.HORIZONTAL, false);
        list.setLayoutManager(GL);
        list.setAdapter(adapter);
        return GL;
    }

    /**
     * Sets Grid Manager Vertical
     * @param list - recyclerview list
     * @param adapter - adapter
     * @param nColumns - nr of columbs
     * @return - Grid Layout Manager for future
     */
    public LinearLayoutManager setList_GridLayoutColumnManager_Vertical(final RecyclerView list, final RecyclerView.Adapter adapter, int nColumns) {
        LinearLayoutManager GL = new GridLayoutManager(mContext, nColumns, LinearLayoutManager.VERTICAL, false);
        list.setLayoutManager(GL);
        list.setAdapter(adapter);
        return GL;
    }

    public SpeedyLinearLayoutManager setList_GridLayoutManager_Vertical(final RecyclerView list, final RecyclerView.Adapter adapter) {
        SpeedyLinearLayoutManager GL = new SpeedyLinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        list.setLayoutManager(GL);
        list.setAdapter(adapter);
        return GL;
    }

    /**
     * Method that lets the code add multiple CustomInputs and provide single array containing all
     * @param nCustonInputs - multiple CustomInputs
     * @return - array containing all of them
     */
    public ArrayList<CustomInput> forValidation(CustomInput... nCustonInputs) {
        ArrayList<CustomInput> mReturn = new ArrayList<>();
        Collections.addAll(mReturn, nCustonInputs);
        return mReturn;
    }

    public void mSimpleSetupSpinnerCustom(CustomInput nCustomInput, List<Item> nValues, Boolean nTextBold) {
        ((Spinner) nCustomInput.findViewById(R.id.nSpinner)).setAdapter(new SimpleSpinnerLeftAdapter(mContext,nValues,nTextBold));
    }

    public void mSimpleSetupSpinnerTextRightCustom(Spinner nSpinner, List<Item> nValues, Boolean nTextBold) {
        nSpinner.setAdapter(new SimpleSpinnerRightAdapter(mContext,nValues,nTextBold));
    }

    public void mSimpleSetupSpinnerTextLeftCustom(Spinner nSpinner, List<Item> nValues, Boolean nTextBold) {
        nSpinner.setAdapter(new SimpleSpinnerLeftAdapter(mContext,nValues,nTextBold));
    }

    public void showSimpleSnackBar(View nMainView, String nMessage) {
        mSnackBar = Snackbar.make(nMainView, nMessage, Snackbar.LENGTH_LONG);
        customizeSnackBar(mSnackBar);
        mSnackBar.show();
    }

    public void showSimpleSnackBar(View nMainView, int nMessage) {
        mSnackBar = Snackbar.make(nMainView, nMessage, Snackbar.LENGTH_LONG);
        customizeSnackBar(mSnackBar);
        mSnackBar.show();
    }

    private void customizeSnackBar(Snackbar mSnackBar) {
        View sbView = mSnackBar.getView();
        sbView.setBackgroundColor(mContext.getResources().getColor(R.color.snackbar_background));
        TextView textView = sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(mContext.getResources().getColor(R.color.snackbar_text));
    }

    public void showLoading(String nStringResources) {
        mProgressBar = new ProgressDialog(mContext);
        mProgressBar.setCancelable(false);

        if (nStringResources==null) {
            mProgressBar.setMessage(mContext.getString(R.string.default_loading_message));
        } else {
            mProgressBar.setMessage(nStringResources);
        }
        mProgressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressBar.show();
    }

    public void closeLoading() {
        if (mProgressBar!=null) {
            mProgressBar.dismiss(); }
    }

}
