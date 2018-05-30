package com.themejunky.personalstylerlib.customdialogs.customQuestion;

import android.widget.TextView;

import com.themejunky.personalstylerlib.R;
import com.themejunky.personalstylerlib.customdialogs.BaseDialog;

public abstract class CustomQuestionBase extends BaseDialog {

    protected TextView mTitle, mQuestion, mPositive, mNegative;

    void fetchViewsAndInitViews() {

        mTitle = mContainer.findViewById(R.id.nTitle);
        mQuestion = mContainer.findViewById(R.id.nQuestion);
        mNegative = mContainer.findViewById(R.id.nNegative);
        mPositive = mContainer.findViewById(R.id.nPositive);

        mNegative.setOnClickListener(this);
        mPositive.setOnClickListener(this);
    }
}
