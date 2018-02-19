package com.themejunky.personalstylerlib.customviews;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.themejunky.personalstylerlib.R;

/**
 * Created by Junky2 on 2/19/2018.
 */

public class CustomButton extends BaseCustom_LinearLayout implements View.OnTouchListener{
    private TextView mButton, mShadow;
    private Custom_Button_Interface mListener;

    /* click listener interface */
    public interface Custom_Button_Interface {
        void onCustomButtonClick(View view);
    }

    /* setter for listener */
    public void setListener(Custom_Button_Interface nListener) {
        mListener = nListener;
    }


    public CustomButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TAG = "CustomButton";
        mTypedarray = context.obtainStyledAttributes(attrs, R.styleable.CustomButton);
        inflate(context, R.layout.custom_button, this);
        mButton = findViewById(R.id.nButton);
        setTitle(mButton, R.styleable.CustomButton_cb_text);

    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }
}
