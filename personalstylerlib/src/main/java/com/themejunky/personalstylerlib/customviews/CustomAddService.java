package com.themejunky.personalstylerlib.customviews;

import android.content.Context;
import android.util.AttributeSet;

import com.themejunky.personalstylerlib.R;


public class CustomAddService extends BaseCustom_LinearLayout {

    public CustomAddService(Context nContext, AttributeSet nAttrs) {
        super(nContext, nAttrs);
        mContext = nContext;
        inflate(nContext, R.layout.custom_add_service, this);

        
    }
}
