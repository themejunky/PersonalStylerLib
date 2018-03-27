package com.themejunky.personalstylerlib.intropager;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.themejunky.personalstylerlib.R;

import java.util.List;

public class IntroPagerActivity extends AppCompatActivity {
    private IntroPagerFragmentAdapter adapterTabViewPager;
    private ViewPager viewPager;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager);

        fetchViews();
        init();
    }

    public void fetchViews() {
        viewPager = findViewById(R.id.viewpager);
    }

    public void setContent(List<ViewPagerModel> nContent)
    {
        Bundle args;
        IntroPagerFragment mFrag;

        for (ViewPagerModel nPage: nContent) {

            args = new Bundle();
                args.putSerializable("model",nPage);

            mFrag = new IntroPagerFragment();
                mFrag.setArguments(args);

            adapterTabViewPager.addFragment(mFrag);
        }

        viewPager.setAdapter(adapterTabViewPager);
    }

    private void init() {
        adapterTabViewPager = new IntroPagerFragmentAdapter(getSupportFragmentManager());
    }


}
