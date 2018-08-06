package com.themejunky.personalstylerlib.customviews;

import android.content.Context;
import android.util.AttributeSet;

import com.themejunky.personalstylerlib.R;

/**
 * CustomSpinner that is used in CustomInput
 */
public class CustomSpinner extends BaseCustom_LinearLayout {

    public CustomSpinner(Context nContext, AttributeSet nAttrs) {
        super(nContext, nAttrs);

        mContext = nContext;

        mTypedarray = nContext.obtainStyledAttributes(nAttrs, R.styleable.CustomInput);
        inflate(nContext, R.layout.custom_spinner, this);

    }
}
