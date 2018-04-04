package com.themejunky.personalstylerlib.bases.tools;


import android.content.Context;
import android.view.View;
import android.widget.ScrollView;
import android.widget.Spinner;

import com.themejunky.personalstylerlib.R;
import com.themejunky.personalstylerlib.bases.model.Item;
import com.themejunky.personalstylerlib.customviews.CustomInput;

import java.util.List;
import java.util.Locale;

public class ToolsBase {

    static Context mContext;
    static Tools mInstance = null;

    int mRowHeight;


    public Locale getLocale() {
        return new Locale("ro_RO");
    }
    public float getDensity() {
        return mContext.getResources().getDisplayMetrics().density;
    }
    public int getHourRowHeight() {
        return mRowHeight;
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

}
