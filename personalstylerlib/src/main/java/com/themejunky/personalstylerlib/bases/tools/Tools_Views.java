package com.themejunky.personalstylerlib.bases.tools;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.themejunky.personalstylerlib.R;
import com.themejunky.personalstylerlib.utils.Constants;


public class Tools_Views extends ToolsBase {

    /**
     * Create a parent linearlayout with tag to identify if can be deleted when refresh is request
     * @return parent vertical-linearlayout-match-match
     */
    protected synchronized LinearLayout generateNewParent_Vertical_MatchMatch() {
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
    protected synchronized LinearLayout generateNewParent_Vertical_MatchWrap() {
        LinearLayout nParent = new LinearLayout(mContext);
        nParent.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        nParent.setOrientation(LinearLayout.VERTICAL);
        nParent.setTag(Constants.OBJECT_TYPE_DELETEBLE);
        return nParent;
    }

    /**
     * Return the insertet view after inflating into viewgrup parent
     * @param nParent - container in witch the new layout is insertet
     * @param nLayout - the new layout to be inserted into the nParent
     * @return main root of the inserted layout
     */
    protected synchronized View getMainContainerAfterInflate(ViewGroup nParent, int nLayout) {
        View.inflate(mContext,nLayout,nParent);
        return nParent.getChildAt(nParent.getChildCount()-1);
    }


    protected synchronized void mAddDividerToParent(ViewGroup nParent) {
        View nMenuItemRow = getMainContainerAfterInflate(nParent, R.layout.row_horizontal_divider);
    }

    protected synchronized void insertSimpleString(ViewGroup nParent, int nLayout, String nText) {
        View.inflate(mContext, nLayout,nParent);
        View nView = nParent.getChildAt(nParent.getChildCount()-1);
        ((TextView) nView).setText(nText);
    }
}
