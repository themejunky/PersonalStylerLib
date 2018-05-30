package com.themejunky.personalstylerlib.customdialogs.customQuestion;

import android.view.View;
import android.widget.TextView;

import com.themejunky.personalstylerlib.R;
import com.themejunky.personalstylerlib.customdialogs.BaseDialog;

public  class CustomQuestionBase extends BaseDialog {

    protected TextView mTitle, mQuestion, mPositive, mNegative;

    void fetchViewsAndInitViews() {

        mTitle = mContainer.findViewById(R.id.nTitle);
        mQuestion = mContainer.findViewById(R.id.nQuestion);
        mNegative = mContainer.findViewById(R.id.nNegative);
        mPositive = mContainer.findViewById(R.id.nPositive);

        mNegative.setOnClickListener(this);
        mPositive.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

    }
}
