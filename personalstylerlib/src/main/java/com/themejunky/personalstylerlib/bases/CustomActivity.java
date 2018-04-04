package com.themejunky.personalstylerlib.bases;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.themejunky.personalstylerlib.R;
import com.themejunky.personalstylerlib.bases.animations.ScrollAnimation;
import com.themejunky.personalstylerlib.bases.tools.Tools;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class CustomActivity extends AppCompatActivity {

    public Tools mTools;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mTools = Tools.getInstance(this);
    }

    /**
     * Check if the provided ScrollView has scroll. If so .... triger hand-animation
     * @param nScrollView - ScrollView on witch check is made
     */
    protected void mSetScrollAttention(final ScrollView nScrollView) {
        ViewTreeObserver observer = nScrollView.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                nScrollView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                ImageView myImage = findViewById(R.id.nIcon);
                if (mTools.canScroll(nScrollView)) {
                    new ScrollAnimation(myImage);
                } else {
                    myImage.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
