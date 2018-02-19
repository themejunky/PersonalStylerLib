package com.themejunky.personalstylerlib.customviews;

import android.app.Notification;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.themejunky.personalstylerlib.R;

/**
 * Created by Junky2 on 2/19/2018.
 */

public class BaseCustom_LinearLayout extends LinearLayout {
    protected String TAG = "CustomView";
    protected Context mContext;
    protected TypedArray mTypedarray;

    public BaseCustom_LinearLayout(Context context) {
        super(context);
    }

    public BaseCustom_LinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }


    /**
     * Set the specific font by his name in assets folder. If that font is not found then default font will be applyed
     *
     * @param nView         - the view on witch will be the font apply ( textview or edittext )
     * @param nFontResource - name of the font in assets folder
     * @param nDefaultFont  - deafult value that should be set. True = bold / False = regular
     */
    protected void setFontFamily(TextView nView, String nFontResource, boolean nDefaultFont){
        try{
            nView.setTypeface(Typeface.createFromAsset(mContext.getAssets(),nFontResource));
        }catch (Exception e){
            Log.d(TAG, ""+e.getMessage());
            if(nDefaultFont){
                nView.setTypeface(Typeface.createFromAsset(mContext.getAssets(),mContext.getResources().getString(R.string.default_font_bold)));
            }else {
                nView.setTypeface(Typeface.createFromAsset(mContext.getAssets(), mContext.getResources().getString(R.string.default_font_regular)));
            }
        }
    }

    /**
     * Set text color
     *
     * @param nView         - the TextView on witch will be the color applyed
     * @param nColor        - the color that will be applyed
     * @param nDefaultColor - the defacult color if none is provided
     */
    protected void setTextColor(TextView nView, int nColor, int nDefaultColor) {
        try {
            nView.setTextColor(getColorWithDefaultPreloaded(nColor, nDefaultColor));
        } catch (Exception e) {
            Log.d(TAG,""+e.getMessage());
        }
    }

    /**
     * Set the specific title
     *
     * @param nView                 - the TextView on witch will be the title apply
     * @param nResourceReference    - string reference to be set
     */
    protected void setTitle(TextView nView, int nResourceReference) {
        try {
            if (mTypedarray.getString(nResourceReference)!=null) {
                nView.setText(mTypedarray.getString(nResourceReference));
            } else {
                nView.setVisibility(View.GONE);
            }
        } catch (Exception e) { }
    }


    public int getColorWithDefaultPreloaded(int nResourceReference, int nDefault) {
        return mTypedarray.getColor(nResourceReference, nDefault);
    }

}
