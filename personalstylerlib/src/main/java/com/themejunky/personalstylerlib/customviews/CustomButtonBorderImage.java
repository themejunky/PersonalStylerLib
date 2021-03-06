package com.themejunky.personalstylerlib.customviews;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.themejunky.personalstylerlib.R;

public class CustomButtonBorderImage extends BaseCustom_LinearLayout implements View.OnTouchListener {

    protected View mDevider;
    protected TextView mText;
    protected ImageView mImageView;
    protected LinearLayout mContainer;
    protected Boolean mDisable = false;
    private Custom_BorderImage_Interface mListener;

    private int mDefaultBackgroundColor,mDefaultBorderRadius, mDefaultBorderStroke,mDefaultBorderColorPressed,mDefaultBorderColorUnPressed;

    /* click listener interface */
    public interface Custom_BorderImage_Interface {
        void onCustomBorderImageClick(View view);
    }
    /* setter for listener */
    public void setListener(CustomButtonBorderImage.Custom_BorderImage_Interface nListener) {
        mListener = nListener;
    }

    public CustomButtonBorderImage(Context context) {
        super(context);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            setPressedState();
            return true;
        }else if (event.getAction() == MotionEvent.ACTION_UP){
            setUnPressedState();
            try {
                mListener.onCustomBorderImageClick(v);
            } catch (Exception e) {
                Log.w(TAG, e.getMessage());
            }
        }
        return false;

    }

    public CustomButtonBorderImage(Context nContext, AttributeSet nAttrs) {
        super(nContext, nAttrs);

        mTypedarray = nContext.obtainStyledAttributes(nAttrs, R.styleable.CustomButtonBorderImage);
        inflate(nContext, R.layout.custom_button_border_image, this);

        mDevider = findViewById(R.id.nDevider);
        mText = findViewById(R.id.nText);
        mImageView = findViewById(R.id.nImageView);
        mContainer = findViewById(R.id.nContainer);

        mContainer.setOnTouchListener(this);

        if (mTypedarray.getBoolean(R.styleable.CustomButtonBorderImage_cbbi_container_gravity_center,false)) {
            mContainer.setGravity(Gravity.CENTER);
        }

        if (mTypedarray.getBoolean(R.styleable.CustomButtonBorderImage_cbbi_container_gravity_left_vertical,false)) {
            mContainer.setGravity(Gravity.CENTER | Gravity.LEFT);
        }

        mContainer.setTag(mTypedarray.getString(R.styleable.CustomButtonBorderImage_cbbi_tag));

        mDefaultBackgroundColor = mTypedarray.getColor(R.styleable.CustomButtonBorderImage_cbbi_default_background_color,getResources().getColor(R.color.CustomButtonBorderImage_default_background_color));
        mDefaultBorderColorUnPressed = mTypedarray.getColor(R.styleable.CustomButtonBorderImage_cbbi_default_border_unpressed_color,getResources().getColor(R.color.CustomButtonBorderImage_default_border_color_unpresssed));
        mDefaultBorderColorPressed = mTypedarray.getColor(R.styleable.CustomButtonBorderImage_cbbi_default_border_pressed_color,getResources().getColor(R.color.CustomButtonBorderImage_default_border_color_pressed));

        mDefaultBorderRadius = getResources().getInteger(R.integer.ci_radius);
        mDefaultBorderStroke = getResources().getInteger(R.integer.ci_stroke);

        setDefaultState();

        LayoutParams params = (LayoutParams) mDevider.getLayoutParams();
        params.width =  (int) (mTypedarray.getInt(R.styleable.CustomButtonBorderImage_cbbi_devider_witdh,0)*mDensity);
        mDevider.setLayoutParams(params);

        setTitle(mText,R.styleable.CustomButtonBorderImage_cbbi_text);

        setStyle(mText, R.styleable.CustomButtonBorderImage_cbbi_text_style, R.style.ci_default_style);
      //  setTextColor(mText,R.styleable.CustomButtonBorderImage_cbbi_default_text_color,R.color.Appoint_Trigger_Border);

        mImageView.setImageDrawable(mTypedarray.getDrawable(R.styleable.CustomButtonBorderImage_cbbi_image));
        mImageView.setColorFilter(getColorWithDefaultPreloaded(R.styleable.CustomButtonBorderImage_cbbi_image_color,R.color.CustomButtonBorder_default_text_color));
        mContainer.setPadding((int) (mTypedarray.getInt(R.styleable.CustomButtonBorderImage_cbbi_padding,0)*mDensity),(int) (mTypedarray.getInt(R.styleable.CustomButtonBorderImage_cbbi_padding,0)*mDensity),(int) (mTypedarray.getInt(R.styleable.CustomButtonBorderImage_cbbi_padding,0)*mDensity),(int) (mTypedarray.getInt(R.styleable.CustomButtonBorderImage_cbbi_padding,0)*mDensity));
        //setPadding(mContainer,mTypedarray.getInt(R.styleable.CustomButtonBorderImage_cbbi_padding,0),getResources().getInteger(R.integer.ci_padping));
    }

    public void setPressedState(){
        setBorderColorAndRadius(mContainer,
                R.styleable.CustomButtonBorderImage_cbbi_backgroundColor,mDefaultBackgroundColor,
                R.styleable.CustomButtonBorderImage_cbbi_border_radius,mDefaultBorderRadius,
                R.styleable.CustomButtonBorderImage_cbbi_border_stroke,mDefaultBorderStroke,
                R.styleable.CustomButtonBorderImage_cbbi_border_pressed,mDefaultBorderColorPressed);
    }

    public void setUnPressedState(){
        setBorderColorAndRadius(mContainer,
                R.styleable.CustomButtonBorderImage_cbbi_backgroundColor,mDefaultBackgroundColor,
                R.styleable.CustomButtonBorderImage_cbbi_border_radius,mDefaultBorderRadius,
                R.styleable.CustomButtonBorderImage_cbbi_border_stroke,mDefaultBorderStroke,
                R.styleable.CustomButtonBorderImage_cbbi_border_unpressed,mDefaultBorderColorUnPressed);
    }

    public void setDefaultState() {
        setBorderColorAndRadius(mContainer,
                R.styleable.CustomButtonBorderImage_cbbi_backgroundColor,mDefaultBackgroundColor,
                R.styleable.CustomButtonBorderImage_cbbi_border_radius,mDefaultBorderRadius,
                R.styleable.CustomButtonBorderImage_cbbi_border_stroke,mDefaultBorderStroke,
                R.styleable.CustomButtonBorderImage_cbbi_border_unpressed,mDefaultBorderColorUnPressed);
    }

    public void setDisable() {

        mDisable = true;

        setStyle(mText, R.styleable.CustomButtonBorderImage_cbbi_disable_text_style, R.style.ci_default_style);

        mImageView.setColorFilter(getColorWithDefaultPreloaded(R.styleable.CustomButtonBorderImage_cbbi_disable_image_color,R.color.CustomButtonBorder_default_text_color));

        setBorderColorAndRadius(mContainer,
                R.styleable.CustomButtonBorderImage_cbbi_disable_background_color,getResources().getColor(R.color.CustomButtonBorderImage_default_background_color),
                R.styleable.CustomButtonBorderImage_cbbi_border_radius,mDefaultBorderRadius,
                R.styleable.CustomButtonBorderImage_cbbi_border_stroke,mDefaultBorderStroke,
                R.styleable.CustomButtonBorderImage_cbbi_disable_border_color,getResources().getColor(R.color.CustomButtonBorderImage_default_border_color_unpresssed));
    }

    public void setActive() {

        mDisable = false;

        setStyle(mText, R.styleable.CustomButtonBorderImage_cbbi_text_style, R.style.ci_default_style);

        mImageView.setColorFilter(getColorWithDefaultPreloaded(R.styleable.CustomButtonBorderImage_cbbi_image_color,R.color.CustomButtonBorder_default_text_color));

        setBorderColorAndRadius(mContainer,
                R.styleable.CustomButtonBorderImage_cbbi_default_background_color,getResources().getColor(R.color.CustomButtonBorderImage_default_background_color),
                R.styleable.CustomButtonBorderImage_cbbi_border_radius,mDefaultBorderRadius,
                R.styleable.CustomButtonBorderImage_cbbi_border_stroke,mDefaultBorderStroke,
                R.styleable.CustomButtonBorderImage_cbbi_border_unpressed,getResources().getColor(R.color.CustomButtonBorderImage_default_border_color_unpresssed));
    }

    public Boolean getmDisable() {
        return mDisable;
    }
}
