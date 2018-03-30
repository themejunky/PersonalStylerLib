package com.themejunky.personalstylerlib.customviews;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.themejunky.personalstylerlib.R;

public class CustomHeaderOneText extends BaseCustom_LinearLayout {

    public CustomHeaderOneText(Context nContext, AttributeSet nAttrs) {
        super(nContext, nAttrs);
        mTypedarray = nContext.obtainStyledAttributes(nAttrs, R.styleable.CustomHeaderOneText);

        inflate(nContext, R.layout.custom_header_one_text, this);

        setTitle(((TextView) findViewById(R.id.nText)),R.styleable.CustomHeaderOneText_chot_text);
    }
}
