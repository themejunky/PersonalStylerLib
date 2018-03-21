package com.themejunky.personalstylerlib.customviews;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.themejunky.personalstylerlib.R;

/**
 * Created by Junky2 on 3/21/2018.
 */

public class CustomButtonBorderImage extends BaseCustom_LinearLayout implements View.OnTouchListener {

    protected View mDevider;
    protected TextView mText;
    protected ImageView mImageView;
    protected LinearLayout mContainer;
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

        mDefaultBackgroundColor = getResources().getColor(R.color.CustomButtonBorderImage_default_background_color);
        mDefaultBorderColorUnPressed = getResources().getColor(R.color.CustomButtonBorderImage_default_border_color_unpresssed);
        mDefaultBorderColorPressed = getResources().getColor(R.color.CustomButtonBorderImage_default_border_color_pressed);
        mDefaultBorderRadius = getResources().getInteger(R.integer.cbbi_radius);
        mDefaultBorderStroke = getResources().getInteger(R.integer.cbbi_stroke);

        setDefaultState();



        LayoutParams params = (LayoutParams) mDevider.getLayoutParams();
        params.width =  mTypedarray.getInt(R.styleable.CustomButtonBorderImage_cbbi_devider,0);
        mDevider.setLayoutParams(params);

        setTitle(mText,R.styleable.CustomButtonBorderImage_cbbi_text);
        setTextColor(mText,R.styleable.CustomButtonBorderImage_cbbi_text_color,R.color.Appoint_Trigger_Border);
        mImageView.setImageDrawable(mTypedarray.getDrawable(R.styleable.CustomButtonBorderImage_cbbi_image));
        mContainer.setPadding(mTypedarray.getInt(R.styleable.CustomButtonBorderImage_cbbi_padding,0),mTypedarray.getInt(R.styleable.CustomButtonBorderImage_cbbi_padding,0),mTypedarray.getInt(R.styleable.CustomButtonBorderImage_cbbi_padding,0),mTypedarray.getInt(R.styleable.CustomButtonBorderImage_cbbi_padding,0));
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
}
