package com.themejunky.personalstylerlib.customviews;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.themejunky.personalstylerlib.R;

/**
 * Created by Junky2 on 3/21/2018.
 */

public class CustomButtonBorderImage extends BaseCustom_LinearLayout {

    protected View mDevider;
    protected TextView mText;
    protected ImageView mImageView;
    protected LinearLayout mContainer;

    public CustomButtonBorderImage(Context context) {
        super(context);
    }

    public CustomButtonBorderImage(Context nContext, AttributeSet nAttrs) {
        super(nContext, nAttrs);

        mTypedarray = nContext.obtainStyledAttributes(nAttrs, R.styleable.CustomButtonBorderImage);
        inflate(nContext, R.layout.custom_button_border_image, this);

        mDevider = findViewById(R.id.nDevider);
        mText = findViewById(R.id.nText);
        mImageView = findViewById(R.id.nImageView);
        mContainer = findViewById(R.id.nContainer);



        LayoutParams params = (LayoutParams) mDevider.getLayoutParams();
        params.width =  mTypedarray.getInt(R.styleable.CustomButtonBorderImage_cbbi_devider,0);
        mDevider.setLayoutParams(params);

        setTitle(mText,R.styleable.CustomButtonBorderImage_cbbi_text);
        setTextColor(mText,R.styleable.CustomButtonBorderImage_cbbi_text_color,R.color.Appoint_Trigger_Border);
        mImageView.setImageDrawable(mTypedarray.getDrawable(R.styleable.CustomButtonBorderImage_cbbi_image));
        mContainer.setBackground(mTypedarray.getDrawable(R.styleable.CustomButtonBorderImage_cbbi_background));
        mContainer.setPadding(mTypedarray.getInt(R.styleable.CustomButtonBorderImage_cbbi_padding,0),mTypedarray.getInt(R.styleable.CustomButtonBorderImage_cbbi_padding,0),mTypedarray.getInt(R.styleable.CustomButtonBorderImage_cbbi_padding,0),mTypedarray.getInt(R.styleable.CustomButtonBorderImage_cbbi_padding,0));
        //setPadding(mContainer,mTypedarray.getInt(R.styleable.CustomButtonBorderImage_cbbi_padding,0),getResources().getInteger(R.integer.ci_padping));





    }
}
