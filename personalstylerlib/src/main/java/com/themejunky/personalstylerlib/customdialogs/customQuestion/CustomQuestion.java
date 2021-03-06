package com.themejunky.personalstylerlib.customdialogs.customQuestion;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.themejunky.personalstylerlib.R;
import com.themejunky.personalstylerlib.customviews.CustomInput;

public class CustomQuestion extends CustomQuestionBase {

    private static CustomQuestion mInstance = null;
    private CustomQuestion._Interface mListener;
    /* if in one window more than one custom dialogs will be used ... this will make the difference */
    private String mType;
    /* just to pass some info */
    private int mCustomIntegerVariable;
    private String mCustomStringVariable;

    public interface _Interface {
        void onCustomQuestion_Negative(AlertDialog.Builder buider,String nType);
        void onCustomQuestion_Positive(AlertDialog.Builder buider,String nType);
    }

    public static CustomQuestion getInstance() {
        if (mInstance == null)
            mInstance = new CustomQuestion();
        return mInstance;
    }

    private CustomQuestion() {
    }

    /**
     * By this method we refresh and trigger the pop-uo
     *
     * @param nContext  - context in witch the pop will be shown
     * @param nListener - listener that will respond
     * @param nTitle - resource int - title
     * @param nQuestion - resource int - question
     * @param nPositive - resource int - positive
     * @param nNegative - resource int - negative
     * @param nType - type difference
     *
     */
    public void refreshContent(Context nContext, CustomQuestion._Interface nListener, int nTitle, int nQuestion, int nPositive, int nNegative, String nType) {
        /* inits*/
        mListener = nListener;
        mContext = nContext;
        mBuilder = new AlertDialog.Builder(mContext);
        mType = nType;
        /* create view*/
        createView(R.layout.dialog_custom);

        fetchViewsAndInitViews();

        mTitle.setText(mContext.getResources().getString(nTitle));
        mQuestion.setText(mContext.getResources().getString(nQuestion));
        mPositive.setText(mContext.getResources().getString(nPositive));
        mNegative.setText(mContext.getResources().getString(nNegative));

        prepareDialog();

      //  return returnDialog();
    }

    public void setTitle(int nTitle) {
        mTitle.setText(mContext.getResources().getString(nTitle));
    }

    public void setQuestion(int nQuestion) {
        mQuestion.setText(mContext.getResources().getString(nQuestion));
    }

    public void setPositive(int nPositive) {
        mPositive.setText(mContext.getResources().getString(nPositive));
    }

    public void setNegative(int nNegative) {
        mNegative.setText(mContext.getResources().getString(nNegative));
    }

    public void setType(String nType) {
        mType = nType;
    }

    /***
     * Passing some infor
     * @param nCustonIntegerVariable - anything you want to pass
     */
      public void setCustomIntegerVariable(int nCustonIntegerVariable) {
        this.mCustomIntegerVariable = nCustonIntegerVariable;
    }
    public void setCustomStringVariable(String nCustonStringVariable) {
        this.mCustomStringVariable = nCustonStringVariable;
    }
    public String getmCustomStringVariable() {
        return mCustomStringVariable;
    }

    public int getmCustomIntegerVariable() {
        return mCustomIntegerVariable;
    }

    public String getmType() {
        return mType;
    }
    public AlertDialog getDialog() {
        return mDialog;
    }

    @Override
    public void onClick(View nView) {
        if (nView.getId() == R.id.nPositive) {
            mDialog.dismiss();
            mListener.onCustomQuestion_Positive(mBuilder,mType);
        } else if (nView.getId() == R.id.nNegative) {
            mDialog.dismiss();
            mListener.onCustomQuestion_Negative(mBuilder,mType);
        }
    }
}
