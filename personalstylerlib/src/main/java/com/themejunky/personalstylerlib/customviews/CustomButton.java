package com.themejunky.personalstylerlib.customviews;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.themejunky.personalstylerlib.R;
import com.themejunky.personalstylerlib.utils.Constants;


/**
 * Default Custom View for all submit/commit buttons
 * @app:cb_tag                  - set tag (string)
 * @app:cb_text                 - set text (text on the button)
 * @app:cb_text_style           - set text style class (text on the button) [R.style.cb_default_style]
 * @app:cb_text_font            - set text font name (text on the button)
 * @app:cb_active_pressed       - set button background color for active state (pressed)
 * @app:cb_active_unpressed     - set button background color for active state (unpressed)
 * @app:cb_active_shadow        - set button background color for active state (shadow)
 * @app:cb_inactive_pressed     - set button background color for inactive state (pressed)
 * @app:cb_inactive_unpressed   - set button background color for inactive state (unpressed)
 * @app:cb_inactive_shadow      - set button background color for inactive state (shadow)
 * @app:cb_form_validation      - set validation expectation ( if the form that current button submit should have a validation "true" or not "false" )
 */
public class CustomButton extends BaseCustom_LinearLayout implements View.OnTouchListener {


    /* click listener interface */
    public interface Custom_Button_Interface {
        void onCustomButtonClick(View view);
    }

    /* setter for listener */
    public void setListener(Custom_Button_Interface nListener) {
        mListener = nListener;
    }


    private TextView mButton, mShadow;
    private Custom_Button_Interface mListener;
    private RelativeLayout mContainer;
    public CustomButton(Context nContext, AttributeSet nAttrs) {
        super(nContext, nAttrs);

        /* start-up stuff*/
        TAG = "CustomButton";
        mTypedarray = nContext.obtainStyledAttributes(nAttrs, R.styleable.CustomButton);
        inflate(nContext, R.layout.custom_button, this);

        mButton = findViewById(R.id.nButton);
            setStyle(mButton, R.styleable.CustomButton_cb_text_style, R.style.cb_default_style);
            setFontFamily(mButton, mTypedarray.getString(R.styleable.CustomButton_cb_text_font), false);
            setTitle(mButton, R.styleable.CustomButton_cb_text);
            setTextColorButton(mButton, R.styleable.CustomButton_cb_text_color, R.color.CustomButton_default_text_color);

        mShadow = findViewById(R.id.nShadow);

        mContainer = findViewById(R.id.nContainer);
            mContainer.setTag(mTypedarray.getString(R.styleable.CustomButton_cb_tag));
            mContainer.setOnTouchListener(this);

            if(mTypedarray.getString(R.styleable.CustomButton_cb_form_validation)!=null){
                if (mTypedarray.getString(R.styleable.CustomButton_cb_form_validation).equals("true")) {
                    setInactive();
                } else {
                    setActive();
                }
            }

    }

    /**
     * Trigger the pressed/unpressed state of the button and on ACTION_UP trigger the onClick action throw listener;
     * If the listener is null a toast-message will popUp;
     */
    @Override
    public boolean onTouch(View view, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            mShadow.setVisibility(View.GONE);

            if(this.getTag()!=null) {
                switch (this.getTag().toString()) {
                    case Constants.CUSTOM_BUTTON_ACTIVE:
                        setmButtonDrawableColor((GradientDrawable) mButton.getBackground(), R.styleable.CustomButton_cb_active_pressed);
                        break;
                    case Constants.CUSTOM_BUTTON_INACTIVE:
                        setmButtonDrawableColor((GradientDrawable) mButton.getBackground(), R.styleable.CustomButton_cb_inactive_pressed);
                        break;
                }
            }

            return true;
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            mShadow.setVisibility(View.VISIBLE);
            if(this.getTag()!=null) {
                switch (this.getTag().toString()) {
                    case Constants.CUSTOM_BUTTON_ACTIVE:
                        setmButtonDrawableColor((GradientDrawable) mButton.getBackground(), R.styleable.CustomButton_cb_active_unpressed);
                        break;
                    case Constants.CUSTOM_BUTTON_INACTIVE:
                        setmButtonDrawableColor((GradientDrawable) mButton.getBackground(), R.styleable.CustomButton_cb_inactive_unpressed);
                        break;
                }
            }

            try {
                mListener.onCustomButtonClick(view);
            } catch (Exception e) {
                Log.w(TAG, e.getMessage());
            }
        }
        return false;
    }

    /**
     * Set tag for the current custom view for onClick
     */
    public void setTag(int nText) {
        mContainer.setTag(nText);
    }

    /**
     * Activate current button
     */
    public void setActive() {
        setmButtonDrawableColor((GradientDrawable) mButton.getBackground(), R.styleable.CustomButton_cb_active_unpressed);
        setmButtonDrawableColor((GradientDrawable) mShadow.getBackground(), R.styleable.CustomButton_cb_active_shadow);
        this.setTag(Constants.CUSTOM_BUTTON_ACTIVE);
    }

    /**
     * Inactivate current button
     */
    public void setInactive() {
        setmButtonDrawableColor((GradientDrawable) mButton.getBackground(), R.styleable.CustomButton_cb_inactive_unpressed);
        setmButtonDrawableColor((GradientDrawable) mShadow.getBackground(), R.styleable.CustomButton_cb_inactive_shadow);
        this.setTag(Constants.CUSTOM_BUTTON_INACTIVE);
    }

    /**
     * Change text on the button
     * @param nText - string resource
     */
    public void setText(int nText) {
        mButton.setText(nText);
    }
}
