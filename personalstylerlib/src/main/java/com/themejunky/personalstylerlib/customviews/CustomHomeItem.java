package com.themejunky.personalstylerlib.customviews;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.themejunky.personalstylerlib.R;


/**
 * Default Custom View for all submit/commit buttons
 * @app:chi_background_color    - set the background color of container
 * @app:chi_radius              - set the radius for all 4 corrners
 * @app:chi_stroke              - set the stroke width ( border )
 * @app:chi_border_default      - set default border color  ( has default fallback )
 * @app:chi_title_text          - set title text
 * @app:chi_title_color         - set title color  ( has default fallback )
 * @app:chi_number_text         - set number text
 * @app:chi_number_color        - set number color  ( has default fallback )
 * @app:chi_text_text           - set text text
 * @app:chi_text_color          - set text color ( has default fallback )
 */
public class CustomHomeItem extends BaseCustom_LinearLayout  {

    /* default values*/
    private int mDefaultTitleColor,mDefaultNumberColor, mDefaultTextColor,mDefaultBackgroundColor,mDefaultBorderRadius,mDefaultBorderStroke,mDefaultBorderColor;

    /* click listener interface */
    public interface CustomHomeItem_Interface {
        void onItemClicked(View view);
    }

    public CustomHomeItem(Context nContext, AttributeSet nAttrs) {
        super(nContext, nAttrs);

        /* load default values */
        mDefaultTitleColor = getResources().getColor(R.color.CustomHomeItem_default_title_color);
        mDefaultNumberColor = getResources().getColor(R.color.CustomHomeItem_default_number_color);
        mDefaultTextColor = getResources().getColor(R.color.CustomHomeItem_default_text_color);
        mDefaultBackgroundColor = getResources().getColor(R.color.CustomHomeItem_default_background_color);
        mDefaultBorderRadius = getResources().getInteger(R.integer.chi_radius);
        mDefaultBorderStroke = getResources().getInteger(R.integer.chi_stroke);
        mDefaultBorderColor= getResources().getColor(R.color.CustomHomeItem_default_border_color);

        mTypedarray = nContext.obtainStyledAttributes(nAttrs, R.styleable.CustomHomeItem);
        inflate(nContext, R.layout.custom_homeitem, this);

        @SuppressLint("WrongViewCast") LinearLayout mContainer = findViewById(R.id.nContainer);
        setBorderColorAndRadius(mContainer,
                R.styleable.CustomHomeItem_chi_background_color,mDefaultBackgroundColor,
                R.styleable.CustomHomeItem_chi_radius,mDefaultBorderRadius,
                R.styleable.CustomHomeItem_chi_stroke,mDefaultBorderStroke,
                R.styleable.CustomHomeItem_chi_border_default,mDefaultBorderColor);

        TextView mTitle = findViewById(R.id.nTitle);
            setTitle(mTitle, R.styleable.CustomHomeItem_chi_title_text);
            setTextColor(mTitle,R.styleable.CustomHomeItem_chi_title_color,mDefaultTitleColor);

        TextView mNr = findViewById(R.id.nNr);
            setTitle(mNr, R.styleable.CustomHomeItem_chi_number_text);
            setTextColor(mNr,R.styleable.CustomHomeItem_chi_number_color,mDefaultNumberColor);


        TextView mText = findViewById(R.id.nText);
            setTitle(mText, R.styleable.CustomHomeItem_chi_text_text);
            setTextColor(mText,R.styleable.CustomHomeItem_chi_text_color,mDefaultTextColor);

    }
}
