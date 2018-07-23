package com.themejunky.personalstylerlib.bases.tools;

import android.content.Context;

public class Tools extends Tools_Schedule {

    public static Tools getInstance(Context nContext) {
        mContext = nContext;

        if (mInstance == null)
            mInstance = new Tools(nContext);
        return mInstance;
    }

    private Tools(Context nContext) {
        mContext = nContext;
        mRowHeightMax = (int) (90*getDensity());
    }

    public Context getContext() {
        return mContext;
    }
}
