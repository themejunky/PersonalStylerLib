package com.themejunky.personalstylerlib.bases.tools;

import android.content.Context;

public class Tools extends ToolsBase {

    public static Tools getInstance(Context nContext) {
        mContext = nContext;

        if (mInstance == null)
            mInstance = new Tools(nContext);
        return mInstance;
    }

    private Tools(Context nContext) {
        mContext = nContext;
        mRowHeight = (int) (70*getDensity());
    }
}
