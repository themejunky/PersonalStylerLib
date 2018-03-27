package com.themejunky.personalstylerlib.intropager;


import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.themejunky.personalstylerlib.R;

public  class IntroPagerFragment extends Fragment {
    private View mRootView;

    public IntroPagerFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("adadas","1");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.view_pager_fragment,container,false);
        Log.d("adadas","2");
        mRootView = rootView;
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d("adadas","3");

        refresh((ViewPagerModel) getArguments().getSerializable("model"));
    }

    public void refresh(ViewPagerModel nPage) {
        IntroPagerFragment fragmentFirst = new IntroPagerFragment();
        Log.d("adadas","4");
        ((LinearLayout)mRootView).findViewById(R.id.nContainerLinear).setBackground(setGradient(getContext(),nPage.getColorStart(),nPage.getColorCenter(),nPage.getColorEnd()));
        ((LinearLayout)mRootView).findViewById(R.id.nContainerPager).setBackground(getResources().getDrawable(nPage.getColorEnd()));
        ((TextView) mRootView.findViewById(R.id.nTitle)).setText(nPage.getTitle());
        ((TextView) mRootView.findViewById(R.id.nText)).setText(nPage.getText());
    }


    public GradientDrawable setGradient(Context context,int color1, int color2, int color3){
        GradientDrawable gradientDrawable = new GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM,
                new int[]{ContextCompat.getColor(context, color1),
                        ContextCompat.getColor(context, color2),
                        ContextCompat.getColor(context, color3),
                });
        return gradientDrawable;
    }
    public GradientDrawable setGradient(Context context,int color1){
        GradientDrawable gradientDrawable = new GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM,
                new int[]{ContextCompat.getColor(context, color1),
                });
        return gradientDrawable;
    }
}
