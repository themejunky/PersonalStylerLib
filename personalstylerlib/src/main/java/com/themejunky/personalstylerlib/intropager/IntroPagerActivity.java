package com.themejunky.personalstylerlib.intropager;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.themejunky.personalstylerlib.R;

import java.util.List;

public class IntroPagerActivity extends AppCompatActivity {
    protected IntroPagerFragmentAdapter adapterTabViewPager;
    protected ViewPager viewPager;
    protected Intent mWhereDoYouGoMyLovely;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager);

        fetchViews();
        init();
    }

    public void fetchViews() {
        viewPager = findViewById(R.id.viewpager);
    }

    public void setContent(List<ViewPagerModel> nContent, Intent nWhereDoYouGoMyLovely) {

        this.mWhereDoYouGoMyLovely = nWhereDoYouGoMyLovely;

        Bundle args;
        IntroPagerFragment mFrag;

        for (ViewPagerModel nPage : nContent) {

            args = new Bundle();
            args.putSerializable("model", nPage);

            mFrag = new IntroPagerFragment();
            mFrag.setArguments(args);

            adapterTabViewPager.addFragment(mFrag);
        }

        viewPager.setAdapter(adapterTabViewPager);
        TabLayout tabLayout = findViewById(R.id.tabDotss);
        tabLayout.setupWithViewPager(viewPager, true);

    }

    public void skipIt() {
        startActivity(mWhereDoYouGoMyLovely);
    }

    private void init() {
        adapterTabViewPager = new IntroPagerFragmentAdapter(getSupportFragmentManager());
    }
}
