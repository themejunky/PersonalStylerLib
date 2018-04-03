package com.themejunky.personalstylerlib.customviews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.themejunky.personalstylerlib.R;

public class CustomSettingItem extends BaseCustom_LinearLayout implements View.OnClickListener {

    private CustomSettingItem_Interface mListener;

    /* click listener interface */
    public interface CustomSettingItem_Interface {
        void onCustomSettingItemClick(View nView);
    }

    /* setter for listener */
    public void setListener(CustomSettingItem.CustomSettingItem_Interface nListener) {
        mListener = nListener;
    }

    public CustomSettingItem(Context nContext, AttributeSet nAttrs) {
        super(nContext, nAttrs);
        mTypedarray = nContext.obtainStyledAttributes(nAttrs, R.styleable.CustomSettingItem);

        inflate(nContext, R.layout.custom_setting_item, this);

        setTitleFallbackInvisible(((TextView) findViewById(R.id.nTitle)),R.styleable.CustomSettingItem_csi_title);
        setTitleFallbackInvisible(((TextView) findViewById(R.id.nSubtext)),R.styleable.CustomSettingItem_csi_subtext);
        setViewVisibility(findViewById(R.id.nArrow), R.styleable.CustomSettingItem_csi_arrow_visibility);

        try {
            ((ImageView) findViewById(R.id.nIcon)).setImageDrawable(mTypedarray.getDrawable(R.styleable.CustomSettingItem_csi_icon));
        } catch (Exception ignored) {
            findViewById(R.id.nIcon).setVisibility(View.GONE);
        }

        findViewById(R.id.nContainer).setTag(mTypedarray.getString(R.styleable.CustomSettingItem_csi_tag));
        findViewById(R.id.nContainer).setOnClickListener(this);
    }

    @Override
    public void onClick(View nView) {
        if (mListener!=null) {
            mListener.onCustomSettingItemClick(nView);
        }
    }
}
