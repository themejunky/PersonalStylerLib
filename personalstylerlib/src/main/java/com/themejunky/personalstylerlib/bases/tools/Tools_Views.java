package com.themejunky.personalstylerlib.bases.tools;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.themejunky.personalstylerlib.R;
import com.themejunky.personalstylerlib.utils.Constants;

import java.io.InputStream;


public class Tools_Views extends Tools_Photos {

    /**
     * Create a parent linearlayout with tag to identify if can be deleted when refresh is request
     * @return parent vertical-linearlayout-match-match
     */
    public synchronized LinearLayout generateNewParent_Vertical_MatchMatch() {
        LinearLayout nParent = new LinearLayout(mContext);
        nParent.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        nParent.setOrientation(LinearLayout.VERTICAL);
        nParent.setTag(Constants.OBJECT_TYPE_DELETEBLE);
        return nParent;
    }

    /**
     * Create a parent linearlayout with tag to identify if can be deleted when refresh is request
     * @return parent vertical-linearlayout-match-wrap
     */
    public synchronized LinearLayout generateNewParent_Vertical_MatchWrap() {
        LinearLayout nParent = new LinearLayout(mContext);
        nParent.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        nParent.setOrientation(LinearLayout.VERTICAL);
        nParent.setTag(Constants.OBJECT_TYPE_DELETEBLE);
        return nParent;
    }

    /**
     * Create a parent linearlayout with tag to identify if can be deleted when refresh is request
     * @return parent horizontal-linearlayout-match-wrap
     */
    public synchronized LinearLayout generateNewParent_Horizontal_MatchWrap() {
        LinearLayout nParent = new LinearLayout(mContext);
        nParent.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        nParent.setOrientation(LinearLayout.HORIZONTAL);
        nParent.setTag(Constants.OBJECT_TYPE_DELETEBLE);
        return nParent;
    }

    /**
     * Return the insertet view after inflating into viewgrup parent
     * @param nParent - container in witch the new layout is insertet
     * @param nLayout - the new layout to be inserted into the nParent
     * @return main root of the inserted layout
     */
    public synchronized View getMainContainerAfterInflate(ViewGroup nParent, int nLayout) {
        View.inflate(mContext,nLayout,nParent);
        return nParent.getChildAt(nParent.getChildCount()-1);
    }


    public synchronized void mAddDividerToParent(ViewGroup nParent) {
        View nMenuItemRow = getMainContainerAfterInflate(nParent, R.layout.row_horizontal_divider);
    }

    public synchronized void insertSimpleString(ViewGroup nParent, int nLayout, String nText) {
        View.inflate(mContext, nLayout,nParent);
        View nView = nParent.getChildAt(nParent.getChildCount()-1);
        ((TextView) nView).setText(nText);
    }

    /**
     * Inflate a new menu left item into the provided parent and populate
     * @param nParent - parent in with the new row should be added
     * @param nIcon - icon to be set
     * @param nName - name to be set
     * @param nTag - tag to be set on the menu item to identify the onClickListener ( there is a custom method for click listener that is set into the layout !! )
     */
    public void mCreateMenuItem(ViewGroup nParent, int nIcon, int nName, int nTag, View.OnClickListener click) {
        View nMenuItemRow = getMainContainerAfterInflate(nParent, R.layout.row_menu_item);

        nMenuItemRow.setOnClickListener(click);


        nMenuItemRow.setTag(mContext.getResources().getString(nTag));
        ((ImageView) nMenuItemRow.findViewById(R.id.nIcon)).setImageDrawable(mContext.getResources().getDrawable(nIcon));
        ((TextView) nMenuItemRow.findViewById(R.id.nName)).setText(mContext.getResources().getString(nName));
    }
}
