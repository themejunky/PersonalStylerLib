package com.themejunky.personalstylerlib.utils;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.themejunky.personalstylerlib.R;
import com.themejunky.personalstylerlib.customviews.CustomButton;
import com.themejunky.personalstylerlib.customviews.CustomInput;

public class BoardingDialog  implements CustomButton.Custom_Button_Interface, View.OnClickListener {


    /* click listener interface */
    public interface CustomDialog_Interface {
        void CustomDialog_PhoneVerify(String nCode);
    }

    private CustomDialog_Interface mListener;

    private AlertDialog.Builder mBuilder;
    private LinearLayout mView_LinearLayout;
    private Context mContext;
    private AlertDialog mDialog;

    private TextView mErrorMessage;
    private CustomInput mCodeInput;

    public BoardingDialog(Context nContext) {
        mBuilder = new AlertDialog.Builder(nContext);
        mContext = nContext;
    }

    /**
     * Set the current layout for the current dialog popup
     *
     * @param nLayoutRef - R.layout.resource
     * @return - current instance
     */
    public BoardingDialog setContent(int nLayoutRef) {
        LayoutInflater factory = LayoutInflater.from(mContext);
        View DialogInflateView = factory.inflate(nLayoutRef, null);
        mView_LinearLayout = DialogInflateView.findViewById(R.id.nContainer);
        mBuilder.setView(mView_LinearLayout);
        return this;
    }

    /**
     * FetchViews and inits for the Reset_Password_PopUp
     */
    public void setAndShow_Reset_Layout(CustomInput nInput) {
        String next = String.format(mContext.getString(R.string.reset_popup_messages), "<font color='#880cff'><b>" + ((EditText) nInput.findViewById(R.id.nInput)).getText().toString() + "</b></font>");
        ((TextView) mView_LinearLayout.findViewById(R.id.nMessage)).setText(Html.fromHtml(next));

        showDialog();
    }

    /**
     * FetchViews and inits for the Validation_Code_PopUp
     */
//    public BoardingDialog setAndShow_RegisterPhone_Layout(CustomInput nInput, CustomDialog_Interface nListener) {
//
//        mListener = nListener;
//
//        ((TextView) mView_LinearLayout.findViewById(R.id.nClose)).setOnClickListener(this);
//        mCodeInput = mView_LinearLayout.findViewById(R.id.nCode);
//
//        String next = String.format(mContext.getString(R.string.register_phone_text), "<font color='#880cff'><b>" + ((EditText) nInput.findViewById(R.id.nInput)).getText().toString() + "</b></font>");
//        ((TextView) mView_LinearLayout.findViewById(R.id.nMessage)).setText(Html.fromHtml(next));
//
//        mView_LinearLayout.findViewById(R.id.nModifica).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mDialog.dismiss();
//            }
//        });
//
//        mErrorMessage = mView_LinearLayout.findViewById(R.id.nError);
//        showDialog();
//
//        return this;
//    }

    private void finishValidationCodePopUp() {
        mDialog.dismiss();
        mErrorMessage.setVisibility(View.GONE);
        mListener.CustomDialog_PhoneVerify(((EditText) mCodeInput.findViewById(R.id.nInput)).getText().toString());
    }

    @Override
    public void onCustomButtonClick(View nView) {
        switch (nView.getTag().toString()) {
            case Constants.DIALOG_BOARDING_RESET_POPUP:
                mDialog.dismiss();
                break;
            case Constants.DIALOG_BOARDING_REGISTER_PHONE: {
                if (((EditText) mCodeInput.findViewById(R.id.nInput)).getText().toString().toString().length() == 6) {
                    finishValidationCodePopUp();
                } else {
                    setCodeError(mContext.getResources().getString(R.string.onboarding_step_1_code_error));
                }
                break;
            }
        }
    }

    @Override
    public void onClick(View nView) {
        switch (nView.getTag().toString()) {
            case Constants.DIALOG_BOARDING_REGISTER_PHONE: {
                if (((EditText) mCodeInput.findViewById(R.id.nInput)).getText().toString().length() == 6) {
                    finishValidationCodePopUp();
                } else {
                    setCodeError(mContext.getResources().getString(R.string.onboarding_step_1_code_error));
                }
                break;
            }
        }
    }

    /**
     * Create and show up current dialog
     */
    private void showDialog() {
        mDialog = mBuilder.create();
        mDialog.show();
    }

    /**
     * Set and show the error in dialog
     *
     * @param nErrorText - string error code
     */
    public BoardingDialog setCodeError(String nErrorText) {
        mErrorMessage.setText(nErrorText);
        mErrorMessage.setVisibility(View.VISIBLE);
        return this;
    }

    public void dismiss() {
        mDialog.dismiss();
    }


}