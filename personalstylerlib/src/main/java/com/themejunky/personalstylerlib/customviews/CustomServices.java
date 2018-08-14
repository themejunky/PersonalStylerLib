package com.themejunky.personalstylerlib.customviews;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.themejunky.personalstylerlib.R;


/**
 * Default Custom View used in Add Service ( male female, my place, client place )
 * @app:cbs_image                   - set the image (SVG)
 * @app:cbs_text                    - set text (text at the bottom)
 * @app:cbs_style                   - set text style class (text on the button) [R.style.cs_default_style]
 * @app:cbs_backgroundColor         - set the background color [R.color.CustomService_default_background_color]
 * @app:cbs_border_radius           - set the border corrner radius [R.integer.cs_radius]
 * @app:cbs_border_stroke           - set the border stroke [R.integer.cs_stroke]
 * @app:cbs_border_default          - set active color (border & icon & text) [R.color.CustomService_default_border_color_active]
 * @app:cbs_border_pressed          - set pressed color (border & icon & text) [R.color.CustomService_default_border_color_pressed]
 * @app:cbs_border_inactive_state   - set inactive color (border & icon & text) [R.color.CustomService_default_border_color_inactive]
 */
public class CustomServices extends BaseCustom_LinearLayout implements ViewTreeObserver.OnGlobalLayoutListener, View.OnClickListener {

    private int mDefaultBackgroundColor,mDefaultBorderRadius, mDefaultBorderStroke, mDefaultBorderColorActive,mDefaultBorderColorPressed,mDefaultBorderColorInactive;

    /* click listener interface */
    public interface Custom_Service_Interface {
        void onCustomServiceClick(View view, String nType);
    }
    /* setter for listener */
    public void setListener(CustomServices.Custom_Service_Interface nListener) {
        mListener = nListener;
    }
    private CustomServices.Custom_Service_Interface mListener;
    private String mTag;
    private RelativeLayout mPicContainer;
    private TextView mText;
    private int mSavedSize;
    private ImageView mPic;
    private LinearLayout mContainer;
    private Boolean mStateOfPress = false;
    private Boolean mStateOfActivation = true;
    private Boolean mStateClickListenerMovement = false;

    public CustomServices(Context nContext, AttributeSet nAttrs) {
        super(nContext, nAttrs);

        /* load default values */
        mDefaultBackgroundColor = getResources().getColor(R.color.CustomService_default_background_color);
        mDefaultBorderColorActive = getResources().getColor(R.color.CustomService_default_border_color_active);
        mDefaultBorderColorPressed = getResources().getColor(R.color.CustomService_default_border_color_pressed);
        mDefaultBorderColorInactive = getResources().getColor(R.color.CustomService_default_border_color_inactive);
        mDefaultBorderRadius = getResources().getInteger(R.integer.ci_radius);
        mDefaultBorderStroke = getResources().getInteger(R.integer.ci_stroke);

        TAG = "CustomService";
        mTypedarray = nContext.obtainStyledAttributes(nAttrs, R.styleable.CustomButtonService);
        inflate(nContext, R.layout.custom_services, this);
        mPic = findViewById(R.id.nPic);
            mPic.setImageDrawable(mTypedarray.getDrawable(R.styleable.CustomButtonService_cbs_image));






        mContainer = findViewById(R.id.nContainer);
            mContainer.setTag(mTypedarray.getString(R.styleable.CustomButtonService_cbs_tag));
        mPicContainer = findViewById(R.id.nPicContainer);
            setPadding(mPicContainer,R.styleable.CustomButtonService_cbs_image_padding, mContainer.getResources().getInteger(R.integer.cbs_padding));
        mText = findViewById(R.id.nText);

        mTag = setTags(R.styleable.CustomButtonService_cbs_tag);

        Log.d("dadadasdasdasd","este : "+mTag);

        setTitle(mText, R.styleable.CustomButtonService_cbs_text);

        setDefaultState();

       /*to be able to ajust as a square to the screen */
        mText.getViewTreeObserver().addOnGlobalLayoutListener(this);
        mContainer.getViewTreeObserver().addOnGlobalLayoutListener(this);
        mPicContainer.getViewTreeObserver().addOnGlobalLayoutListener(this);

        mContainer.setOnClickListener(this);
    }

    @Override
    public void onMeasure(int widthSpec, int heightSpec) {
        super.onMeasure(widthSpec, heightSpec);
        mSavedSize = Math.min(getMeasuredWidth(), getMeasuredHeight());
        setMeasuredDimension(getMeasuredWidth(), getMeasuredHeight());
    }

    @Override
    public void onClick(View view) {
        if (mStateOfActivation) {
            if (!mStateOfPress) {
                setActive();
            } else {
                mStateOfPress = false;
                setDefaultState();
            }

            if (mStateClickListenerMovement && mListener!=null) {
                mListener.onCustomServiceClick(view,mTag);
            }

        } else {
            mListener.onCustomServiceClick(view,mTag);
        }
    }

    @Override
    public void onGlobalLayout() {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mPicContainer.getLayoutParams();
        params.width = mSavedSize;
        params.height = mSavedSize;
        mPicContainer.setLayoutParams(params);

        if (mText.getHeight()!=0) mText.getViewTreeObserver().removeOnGlobalLayoutListener(this);
        if (mContainer.getHeight()!=0)  mContainer.getViewTreeObserver().removeOnGlobalLayoutListener(this);
        if (mPicContainer.getHeight()!=0)  mPicContainer.getViewTreeObserver().removeOnGlobalLayoutListener(this);
    }

    /**
     * The default state ; the start-up state of the customview
     */
    public void setDefaultState() {
        setBorderColorAndRadius(mPicContainer,
                R.styleable.CustomButtonService_cbs_backgroundColor,mDefaultBackgroundColor,
                R.styleable.CustomButtonService_cbs_border_radius,mDefaultBorderRadius,
                R.styleable.CustomButtonService_cbs_border_stroke,mDefaultBorderStroke,
                R.styleable.CustomButtonService_cbs_border_default,mDefaultBorderColorActive);

        mPic.setColorFilter(getColorWithDefaultPreloaded(R.styleable.CustomButtonService_cbs_image_color, mDefaultBorderColorActive));

        setStyle(mText,R.styleable.CustomButtonService_cbs_style,R.style.cs_default_style);
    }


    /**
     * Activate button
     */
    public void setActive() {
        mStateOfPress=true;

        setBorderColorAndRadius(mPicContainer,
                R.styleable.CustomButtonService_cbs_backgroundColor,mDefaultBackgroundColor,
                R.styleable.CustomButtonService_cbs_border_radius,mDefaultBorderRadius,
                R.styleable.CustomButtonService_cbs_border_stroke,mDefaultBorderStroke,
                R.styleable.CustomButtonService_cbs_border_pressed, mDefaultBorderColorPressed);

        mPic.setColorFilter(getColorWithDefaultPreloaded(R.styleable.CustomButtonService_cbs_border_pressed, mDefaultBorderColorPressed));
        setTextColor(mText, R.styleable.CustomButtonService_cbs_border_pressed, mDefaultBorderColorPressed);
    }

    /**
    Disable button (no click actions)
     */
    public void setInActive() {

        mStateOfPress=false;
        mStateOfActivation=false;

        setBorderColorAndRadius(mPicContainer,
                R.styleable.CustomButtonService_cbs_backgroundColor,mDefaultBackgroundColor,
                R.styleable.CustomButtonService_cbs_border_radius,mDefaultBorderRadius,
                R.styleable.CustomButtonService_cbs_border_stroke,mDefaultBorderStroke,
                R.styleable.CustomButtonService_cbs_border_inactive_state,mDefaultBorderColorInactive);

        mPic.setColorFilter(getColorWithDefaultPreloaded(R.styleable.CustomButtonService_cbs_border_inactive_state, mDefaultBorderColorInactive));
        setTextColor(mText, R.styleable.CustomButtonService_cbs_border_inactive_state, mDefaultBorderColorInactive);
    }

    public void setAsAppointmentButton() {
        mStateOfPress=false;
        mStateOfActivation=false;
        mContainer.setPadding(0,0,0,20);
    }

    public void setTag(String nTag) {
        mTag = nTag;
    }

    /**
     * Get the current state of the button
     * @return true if pressed or false if not
     */
    public boolean getSateOfPress() {
        return mStateOfPress;
    }

    /**
     * Set the current state for the button
     * @param nState false = disable or true = active
     */
    public void setStateOfActivation(Boolean nState)
    {
        mStateOfActivation=nState;
        if (!nState) {
            setInActive();
        }
    }

    /**
     * if is set to true the on click listener si triggered
     * @param nState - state of the click listener movement
     * @return
     */
    public boolean setClickListenerMovement(boolean nState) {
        return mStateClickListenerMovement = nState;
    }
}
