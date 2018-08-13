package com.themejunky.personalstylerlib.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.themejunky.personalstylerlib.R;
import com.themejunky.personalstylerlib.bases.model.Item;

import java.util.ArrayList;
import java.util.List;


public class SimpleSpinnerLeftAdapter  extends BaseAdapter {

    private Context mContext;
    private List<Item> mValues;
    private LayoutInflater inflter;
    private Boolean mTextBold;
    private Typeface mBoldFont;

    public SimpleSpinnerLeftAdapter(Context applicationContext, List<Item> nValues, Boolean nTextBold) {
        this.mContext = applicationContext;
        this.mValues = new ArrayList<>();
        this.mValues.addAll(nValues);
        this.mTextBold = nTextBold;
        this.mBoldFont = Typeface.createFromAsset(applicationContext.getAssets(),"Bold.ttf");

        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return mValues.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.row_simple_left_spinner, null);

        TextView mText =  view.findViewById(R.id.nText);
        mText.setText(mValues.get(i).getValue());
        if (mTextBold) { mText.setTypeface(mBoldFont);}

        return view;
    }
}