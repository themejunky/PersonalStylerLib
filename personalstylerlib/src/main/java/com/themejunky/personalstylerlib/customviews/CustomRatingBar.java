package com.themejunky.personalstylerlib.customviews;

import android.content.Context;

import com.themejunky.personalstylerlib.R;


public class CustomRatingBar extends BaseCustom_LinearLayout {

    public CustomRatingBar(Context nContext) {
        super(nContext);

        inflate(nContext, R.layout.custom_rating_bar, this);
    }
}
