package com.themejunky.personalstylerlib.bases.animations;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;

public class ScrollAnimation implements Animation.AnimationListener {

    private AnimationSet mAnimationSet;
    private View mView;
    private int mCounts = 0;

    public ScrollAnimation(View nView) {

        mView = nView;

        mCounts = 0;

        mAnimationSet = new AnimationSet(true);

        Animation mAnimation1 = new TranslateAnimation(0, 0, 0, -250);
        mAnimation1.setDuration(3000);

        mAnimationSet.addAnimation(mAnimation1);

        Animation mAnimation2 = new AlphaAnimation(1.0f, 0.0f);
        mAnimation2.setDuration(3000);

        mAnimationSet.addAnimation(mAnimation2);

        mAnimationSet.setAnimationListener(this);

        mView.startAnimation(mAnimationSet);
    }

    @Override
    public void onAnimationStart(Animation animation) {
        mView.setVisibility(View.GONE);
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        mView.setVisibility(View.GONE);
        mCounts++;

        if (mCounts<2) {
            mView.startAnimation(mAnimationSet);
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {
        mView.setVisibility(View.GONE);
    }
}
