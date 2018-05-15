package com.themejunky.personalstylerlib.bases.activities.custom;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.themejunky.personalstylerlib.R;
import com.themejunky.personalstylerlib.bases.activities.photo.PhotoActivity;
import com.themejunky.personalstylerlib.bases.animations.ScrollAnimation;
import com.themejunky.personalstylerlib.customviews.CustomButton;
import com.themejunky.personalstylerlib.customviews.CustomButtonBorder;
import com.themejunky.personalstylerlib.customviews.CustomServices;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class CustomActivity extends PhotoActivity implements View.OnClickListener,CustomButton.Custom_Button_Interface,CustomButtonBorder.CustomButtonBorderInterface,CustomServices.Custom_Service_Interface {


    protected String user_id,service_id;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    /**
     * Check if the provided ScrollView has scroll. If so .... triger hand-animation
     * @param nScrollView - ScrollView on witch check is made
     */
    protected void mSetScrollAttention(final ScrollView nScrollView, final ImageView nImage) {
        ViewTreeObserver observer = nScrollView.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                nScrollView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                if (mTools.canScroll(nScrollView)) {
                    new ScrollAnimation(nImage);
                } else {
                    nImage.setVisibility(View.GONE);
                }
            }
        });
    }

    /**
     * Check if the current view tag match the object ( resource string from xml)
     * @param nView - view to check tag against
     * @param nObject - string object resources
     * @return
     */
    public boolean mCO(View nView, int nObject) {
        return nView.getTag().equals(getString(nObject));
    }

    /**
     * Method that administrate top bar
     * @param nVisibleBackIcon -  if the back-icon should be visible
     * @param nVisibleTitle  - if the title  should be visible
     * @param nVisibleGhid-  if the guide  should be visible
     * @param nTitle - text of the title
     */
    public void setBar(boolean nVisibleBackIcon, boolean nVisibleTitle, boolean nVisibleGhid, String nTitle) {

        if (nVisibleBackIcon) {
            findViewById(R.id.nBack).setVisibility(View.VISIBLE);
            findViewById(R.id.nBack).setOnClickListener(this);
        } else {
            findViewById(R.id.nBack).setVisibility(View.GONE);
        }

        if (nVisibleTitle) {
            findViewById(R.id.nTitle).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.nTitle).setVisibility(View.GONE);
        }

        if (nVisibleGhid) {
            findViewById(R.id.nGhid).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.nGhid).setVisibility(View.GONE);
        }

        ((TextView) findViewById(R.id.nTitle)).setText(nTitle);
    }

    /**
     * Get user_id and service_id form getIntent()
     */
    public void getUserId_ServiceId() {
        Bundle mBundle = getIntent().getExtras();
        if(mBundle != null)  {
            user_id = mBundle.getString("user_id");
            service_id = mBundle.getString("service_id");
        }
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onClick(View nView) {
        if (mCO(nView, R.string.reset_key_button_back)) {
            super.onBackPressed();
        }
    }

    @Override
    public void onCustomButtonBorderClick(View view) { }

    @Override
    public void onCustomButtonClick(View view) { }

    @Override
    public void onCustomServiceClick(View view, String nType) { }
}
