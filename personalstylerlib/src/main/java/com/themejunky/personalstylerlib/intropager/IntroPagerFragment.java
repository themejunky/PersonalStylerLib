package com.themejunky.personalstylerlib.intropager;


import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.themejunky.personalstylerlib.R;
import com.themejunky.personalstylerlib.customviews.CustomButton;

import junit.framework.Test;

public class IntroPagerFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "IntroPager";
    private View mRootView;
    private TextView next, skip;

    public IntroPagerFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.view_pager_fragment, container, false);
        mRootView = rootView;
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        refresh((ViewPagerModel) getArguments().getSerializable("model"));
    }

    public void refresh(ViewPagerModel nPage) {


        if (nPage.getIsLastPage()) {
            ((ViewStub) mRootView.findViewById(R.id.finishViewStub)).inflate();

            mRootView.findViewById(R.id.nFinish).setOnClickListener(this);
        } else {
            ((ViewStub) mRootView.findViewById(R.id.nextViewStub)).inflate();

            mRootView.findViewById(R.id.nNext).setOnClickListener(this);
            mRootView.findViewById(R.id.nSkip).setOnClickListener(this);
        }



        (mRootView).findViewById(R.id.nContainerLinear).setBackground(setGradient(getContext(), nPage.getColorStart(), nPage.getColorCenter(), nPage.getColorEnd()));
        (mRootView).findViewById(R.id.nContainerPager).setBackground(getResources().getDrawable(nPage.getColorEnd()));

        ((TextView) mRootView.findViewById(R.id.nTitle)).setText(nPage.getTitle());
        ((TextView) mRootView.findViewById(R.id.nText)).setText(nPage.getText());
       ((ImageView)mRootView.findViewById(R.id.nImage)).setBackgroundResource(nPage.getImage());




    }

    public GradientDrawable setGradient(Context context, int color1, int color2, int color3) {
        return new GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM,
                new int[]{ContextCompat.getColor(context, color1),
                        ContextCompat.getColor(context, color2),
                        ContextCompat.getColor(context, color3),
                });
    }


    @Override
    public void onClick(View v) {

        if (getActivity() == null) return;

        ViewPager viewPager = ((IntroPagerActivity) getActivity()).viewPager;
        if (v.getId() == R.id.nNext) {
            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
        } else if (v.getId() == R.id.nSkip) {
            ((IntroPagerActivity) getActivity()).skipIt();
        } else if (v.getId() == R.id.nFinish) {
            ((IntroPagerActivity) getActivity()).skipIt();
        }
    }
}
