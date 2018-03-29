package com.themejunky.personalstylerlib.customviews;

import android.content.Context;
import android.support.v7.widget.SwitchCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.themejunky.personalstylerlib.R;

public class CustomTextSwitch extends BaseCustom_LinearLayout implements CompoundButton.OnCheckedChangeListener {
    private TextView mText,mSubtext;
    private SwitchCompat mSwitch;
    private CustomTextSwitch_Interface mListener;

    /* click listener interface */
    public interface CustomTextSwitch_Interface {
        void onCustomTextSwitchClick(CompoundButton compoundButton,Boolean isChecked);
    }

    /* setter for listener */
    public void setListener(CustomTextSwitch_Interface nListener) {
        mListener = nListener;
    }

    public CustomTextSwitch(Context nContext, AttributeSet nAttrs) {
        super(nContext, nAttrs);
        mTypedarray = nContext.obtainStyledAttributes(nAttrs, R.styleable.CustomTextSwitch);

        inflate(nContext, R.layout.custom_text_switch, this);

        mText = findViewById(R.id.nText);
        mSubtext = findViewById(R.id.nSubtext);
        mSwitch = findViewById(R.id.nSwitch);

        setTitle(mText,R.styleable.CustomTextSwitch_cts_text);
        setTitle(mSubtext,R.styleable.CustomTextSwitch_cts_subtext);
        setTextColor(mText,R.styleable.CustomTextSwitch_cts_text_color,getResources().getColor(R.color.switch_text_color));
        setTextColor(mSubtext,R.styleable.CustomTextSwitch_cts_subtext_color,getResources().getColor(R.color.switch_subtext_color));

        mSwitch.setOnCheckedChangeListener(this);
    }

    private void setTitle(int nTitle) {
        mText.setText(nTitle);
    }

    private void setText(String nText) {
        mSubtext.setText(nText);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        mListener.onCustomTextSwitchClick(compoundButton,b);
    }

}
