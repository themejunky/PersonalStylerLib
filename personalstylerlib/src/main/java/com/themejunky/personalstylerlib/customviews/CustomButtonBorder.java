package com.themejunky.personalstylerlib.customviews;

import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.themejunky.personalstylerlib.R;


/**
 * Custom button for onBoarding Location
 * button with white background color and border
 * this button has 2 states : pressed and unpressed
 */
public class CustomButtonBorder extends BaseCustom_LinearLayout implements View.OnClickListener  {

    /* default values*/
    private int mDefaultBackgroundColor,mDefaultBorderRadius, mDefaultBorderStroke, mDefaultBorderColor;
    /* the state of the button ; by default is no pressed = false */
    private Boolean mStateOfPress = false;

    /* click listener interface */
    public interface CustomButtonBorderInterface {
        void onCustomButtonBorderClick(View view);
    }

    /* setter for listener */
    public void setListener(CustomButtonBorderInterface nListener) {
        mListener = nListener;
    }

    private RelativeLayout mContainer;
    private TextView mText;
    private CustomButtonBorderInterface mListener;

    public CustomButtonBorder(Context nContext, AttributeSet nAttrs) {
        super(nContext, nAttrs);
        Log.d("defaff", " 0");
        /* load default values */
        mDefaultBackgroundColor = getResources().getColor(R.color.CustomButtonBorder_default_background_color);
        mDefaultBorderColor = getResources().getColor(R.color.CustomButtonBorder_default_border_color);
        mDefaultBorderRadius = getResources().getInteger(R.integer.cbb_radius);
        mDefaultBorderStroke = getResources().getInteger(R.integer.cbb_stroke);

        /* start-up stuff*/
        mTypedarray = nContext.obtainStyledAttributes(nAttrs, R.styleable.CustomButtonBorder);
        inflate(nContext, R.layout.custom_button_color_border, this);

        mContainer = findViewById(R.id.nContainer);
        mContainer.setTag(mTypedarray.getString(R.styleable.CustomButtonBorder_cbb_tag));
        setPadding(mContainer, R.styleable.CustomButtonBorder_cbb_padding, mContainer.getResources().getInteger(R.integer.cbb_padding));
        mContainer.setOnClickListener(this);

        mText = findViewById(R.id.nText);

        setUnpressed();
        setTitle(mText, R.styleable.CustomButtonBorder_cbb_text);
        setTextColor(mText, R.styleable.CustomButtonBorder_cbb_text_color, R.color.CustomButtonBorder_default_text_color);

    }

    @Override
    public void onClick(View view) {
        if (mListener!=null) {
            if (!mStateOfPress) {
                setPressed(); }
            else { setUnpressed(); }
            mListener.onCustomButtonBorderClick(view); }
    }


    /**
     * Default state of the button : white background with gray border (by default)
     */
    public void setUnpressed() {

        mStateOfPress = false;

        setBorderColorAndRadius(mContainer,
                R.styleable.CustomButtonBorder_cbb_backgroundColor, mDefaultBackgroundColor,
                R.styleable.CustomButtonBorder_cbb_border_radius,mDefaultBorderRadius,
                R.styleable.CustomButtonBorder_cbb_border_stroke,mDefaultBorderStroke,
                R.styleable.CustomButtonBorder_cbb_border_default,mDefaultBorderColor);

        setTextColor(mText,R.styleable.CustomButtonBorder_cbb_border_default,R.color.CustomButtonBorder_default_text_color);
    }

    /**
     * Pressed state of the button ( when user clicks on )
     */
    public void setPressed() {

        mStateOfPress = true;

        setBorderColorAndRadius(mContainer,
                R.styleable.CustomButtonBorder_cbb_backgroundColor, mDefaultBackgroundColor,
                R.styleable.CustomButtonBorder_cbb_border_radius, mDefaultBorderRadius,
                R.styleable.CustomButtonBorder_cbb_border_stroke, mDefaultBorderStroke,
                R.styleable.CustomButtonBorder_cbb_border_pressed, mDefaultBorderColor);

        setTextColor(mText, R.styleable.CustomButtonBorder_cbb_border_pressed, R.color.CustomButtonBorder_default_text_color);
    }

    /**
     * Set the desired text on the button
     * @param nText - the new text to show on the button
     */

    public void setCustomText(String nText, Boolean nStrikeFlag) {
        mText.setText(nText);
        mText.setVisibility(View.VISIBLE);

        if (nStrikeFlag) {
        mText.setPaintFlags(mText.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG); }

        mContainer.invalidate();
        mText.invalidate();
        invalidate();
    }

    /**
     * Get the current state of the button ( pressed or unpressed )
     * @return - boolean (true = pressed ; false = unpressed )
     */
    public Boolean getStateOfPress() { return mStateOfPress; }
}
