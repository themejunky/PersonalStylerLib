package com.themejunky.personalstylerlib.customviews;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.themejunky.personalstylerlib.R;

public class CustomSettingItem extends BaseCustom_LinearLayout {

    public CustomSettingItem(Context nContext, AttributeSet nAttrs) {
        super(nContext, nAttrs);
        mTypedarray = nContext.obtainStyledAttributes(nAttrs, R.styleable.CustomSettingItem);

        inflate(nContext, R.layout.custom_setting_item, this);

        setTitleFallbackInvisible(((TextView) findViewById(R.id.nTitle)),R.styleable.CustomSettingItem_csi_title);
        setTitleFallbackInvisible(((TextView) findViewById(R.id.nSubtext)),R.styleable.CustomSettingItem_csi_subtext);
        setViewVisibility(findViewById(R.id.nArrow), R.styleable.CustomSettingItem_csi_arrow_visibility);
    }
}
