package com.themejunky.personalstylerlib.bases.tools;


import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ScrollView;
import android.widget.Spinner;

import com.themejunky.personalstylerlib.R;
import com.themejunky.personalstylerlib.bases.model.Item;
import com.themejunky.personalstylerlib.customviews.CustomInput;

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
    protected String getString(int nStringResources) {
        return mContext.getResources().getString(nStringResources);
    }

    protected int getColor(int nColorResources) {
        return mContext.getResources().getColor(nColorResources);
    }

    public boolean isEmailValid(String nEmail) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(nEmail);
        return matcher.matches();
    }

    public boolean isPhoneValid(String nPhone) {
        return android.util.Patterns.PHONE.matcher(nPhone).matches();
    }

    public String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
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
     * @return - Grid Layout Manager for future
     */
    public GridLayoutManager setList_GridLayoutManager_Horizontal(final RecyclerView list, final RecyclerView.Adapter adapter) {
        GridLayoutManager GL = new GridLayoutManager(mContext, 1, LinearLayoutManager.HORIZONTAL, false);
        list.setLayoutManager(GL);
        list.setAdapter(adapter);
        return GL;
    }

    /**
     * Sets Grid Manager Vertical
     * @param list - recyclerview list
     * @param adapter - adapter
     * @return - Grid Layout Manager for future
     */
    public GridLayoutManager setList_GridLayoutManager_Vertical( final RecyclerView list, final RecyclerView.Adapter adapter) {
        GridLayoutManager GL = new GridLayoutManager(mContext, 1, LinearLayoutManager.VERTICAL, false);
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


}
