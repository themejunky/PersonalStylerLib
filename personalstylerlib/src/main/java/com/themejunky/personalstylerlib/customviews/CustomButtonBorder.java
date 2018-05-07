package com.themejunky.personalstylerlib.customviews;

import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.themejunky.personalstylerlib.R;


/**
 * Custom button for onBoarding Location
 * button with white background color and border
 * this button has 2 states : pressed and unpressed
 */
public class CustomButtonBorder extends BaseCustom_LinearLayout implements View.OnClickListener {

    /* default values*/
    private int mDefaultBackgroundColor, mDefaultBorderRadius, mDefaultBorderStroke, mDefaultBorderColor, mTextColor;
    private int mDefaultPressedBackgroundColor, mDefaultPressedBorderRadius, mDefaultPressedBorderStroke, mDefaultPressedBorderColor, mTextPressedColor;
    /* the state of the button ; by default is no pressed = false */
    private Boolean mStateOfPress = false;
    private boolean mStateOfActivation=true;
    private boolean mStateFillBackgroundOnClick=false;

    /* click listener interface */
    public interface CustomButtonBorderInterface {
        void onCustomButtonBorderClick(View view);
    }

    /* setter for listener */
    public void setListener(CustomButtonBorderInterface nListener) {
        mListener = nListener;
    }

    private LinearLayout mContainer;
    private TextView mText;
    private ImageView mIcon;
    private CustomButtonBorderInterface mListener;

    public CustomButtonBorder(Context nContext, AttributeSet nAttrs) {
        super(nContext, nAttrs);
        Log.d("defaff", " 0");
        /* load default values */

        /* start-up stuff*/
        mTypedarray = nContext.obtainStyledAttributes(nAttrs, R.styleable.CustomButtonBorder);
        inflate(nContext, R.layout.custom_button_color_border, this);


        mTextColor = getColorWithDefaultPreloaded(R.styleable.CustomButtonBorder_cbb_text_color,R.color.CustomButtonBorder_default_text_color);
        mTextPressedColor = getColorWithDefaultPreloaded(R.styleable.CustomButtonBorder_cbb_text_color_pressed,R.color.CustomButtonBorder_default_text_color);

        mDefaultBackgroundColor = getColorWithDefaultPreloaded(R.styleable.CustomButtonBorder_cbb_backgroundColor,R.color.CustomButtonBorder_default_background_color);
        mDefaultPressedBackgroundColor = getColorWithDefaultPreloaded(R.styleable.CustomButtonBorder_cbb_backgroundColor_pressed,R.color.CustomButtonBorder_default_background_color);

        mDefaultBorderColor = getColorWithDefaultPreloaded(R.styleable.CustomButtonBorder_cbb_borderColor,R.color.CustomButtonBorder_default_border_color);
        mDefaultPressedBorderColor = getColorWithDefaultPreloaded(R.styleable.CustomButtonBorder_cbb_borderColor_pressed,R.color.CustomButtonBorder_default_border_color);

        mDefaultBorderRadius = mDefaultPressedBorderRadius = getResources().getInteger(R.integer.cbb_radius);
        mDefaultBorderStroke = mDefaultPressedBorderStroke = getResources().getInteger(R.integer.cbb_stroke);


        mContainer = findViewById(R.id.nContainer);
        mContainer.setTag(mTypedarray.getString(R.styleable.CustomButtonBorder_cbb_tag));
        setPadding(mContainer, R.styleable.CustomButtonBorder_cbb_padding, mContainer.getResources().getInteger(R.integer.cbb_padding));
        mContainer.setOnClickListener(this);

        mText = findViewById(R.id.nText);
        mIcon = findViewById(R.id.nIcon);

        setImageToImageView(mIcon,R.styleable.CustomButtonBorder_cbb_icon);

        setUnpressed();
        setTitle(mText, R.styleable.CustomButtonBorder_cbb_text);
        setStyle(mText, R.styleable.CustomButtonBorder_cbb_style, R.style.ci_default_style);
        setTextColor(mText, R.styleable.CustomButtonBorder_cbb_text_color, R.color.CustomButtonBorder_default_text_color);
    }

    @Override
    public void onClick(View view) {

       if (mStateOfActivation) {
            if (mListener != null) {
                if (!mStateOfPress) {
                    setPressed();
                } else {
                    setUnpressed();
                }
            }
        }else {
            setUnpressed();
        }
        mListener.onCustomButtonBorderClick(view);
    }


    public void setAsAppointmentButton() {
        mStateOfActivation=false;
    }


    /**
     * Default state of the button : white background with gray border (by default)
     */
    public void setUnpressed() {

        mStateOfPress = false;

        setBorderColorAndRadius(mContainer,
                R.styleable.CustomButtonBorder_cbb_backgroundColor, mDefaultBackgroundColor,
                R.styleable.CustomButtonBorder_cbb_border_radius, mDefaultBorderRadius,
                R.styleable.CustomButtonBorder_cbb_border_stroke, mDefaultBorderStroke,
                R.styleable.CustomButtonBorder_cbb_border_default, mDefaultBorderColor);

        setTextColor(mText, mTextColor);
    }

    /**
     * Pressed state of the button ( when user clicks on )
     */
    public void setPressed() {
Log.d("adadadada","124");
        mStateOfPress = true;

        setBorderColorAndRadius(mContainer,
                R.styleable.CustomButtonBorder_cbb_backgroundColor_pressed, mDefaultPressedBackgroundColor,
                R.styleable.CustomButtonBorder_cbb_border_radius, mDefaultPressedBorderRadius,
                R.styleable.CustomButtonBorder_cbb_border_stroke, mDefaultPressedBorderStroke,
                R.styleable.CustomButtonBorder_cbb_border_pressed, mDefaultPressedBorderColor);

        setTextColor(mText, mTextPressedColor);
    }

    /**
     * Set the desired text on the button
     *
     * @param nText - the new text to show on the button
     */

    public void setCustomText(String nText, Boolean nStrikeFlag) {
        mText.setText(nText);
        mText.setVisibility(View.VISIBLE);

        if (nStrikeFlag) {
            mText.setPaintFlags(mText.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }

        mContainer.invalidate();
        mText.invalidate();
        invalidate();
    }

    /**
     * Get the current state of the button ( pressed or unpressed )
     *
     * @return - boolean (true = pressed ; false = unpressed )
     */
    public Boolean getStateOfPress() {
        return mStateOfPress;
    }
}
