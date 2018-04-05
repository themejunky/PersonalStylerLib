package com.themejunky.personalstylerlib.utils;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.themejunky.personalstylerlib.R;
import com.themejunky.personalstylerlib.bases.tools.Tools;
import com.themejunky.personalstylerlib.customviews.CustomInput;

import java.util.ArrayList;


public class FormClickValidator {

    /* click listener interface */
    public interface FormClickValidatorInterface {
        void onFormClickValidatorFinish();
    }

    private Tools mTools;
    private int mCountErrors;

    public FormClickValidator(Context nContext, ArrayList<CustomInput> nViews, FormClickValidatorInterface nListener) {

        mTools = Tools.getInstance(nContext);
        mCountErrors = 0;

        for (CustomInput nView : nViews) {


            if (nView.findViewById(R.id.nInput) instanceof EditText && nView.findViewById(R.id.nInput).getVisibility() == View.VISIBLE) {

                EditText mEditText = (nView.findViewById(R.id.nInput));

                switch (nView.getInputType()) {
                    case "1":
                        mValidateText(mEditText, nView);
                        break;
                    case "12":
                        mValidateMinText(mEditText, nView);
                        break;
                    case "2":
                        mValidateEmail(mEditText, nView);
                        break;
                    case "3":
                        mValidatePhone(mEditText, nView);
                        break;
                    case "4":
                        mValidatePassword(mEditText, nView);
                        break;
                    case "5":
                        mValidateNumber(mEditText, nView);
                        break;
                }
            } else if (nView.findViewById(R.id.nSpinner) instanceof Spinner && nView.findViewById(R.id.nSpinner).getVisibility() == View.VISIBLE) {
                Spinner mSpinner = (nView.findViewById(R.id.nSpinner));
                if (mSpinner.getSelectedItemPosition()!=0) {
                    nView.hideError();
                } else {
                    nView.showError();
                    mCountErrors++;
                }
            } else {
            }
        }



        if (mCountErrors == 0) {
            nListener.onFormClickValidatorFinish();
        }
    }

    /* EditText-Password - validation */
    private void mValidatePassword(EditText nEditText, CustomInput nCustomInput) {
        if (nEditText.getText().toString().length() >= 5) {
            nCustomInput.hideError();
        } else {
            nCustomInput.showError();
            mCountErrors++;
        }
    }

    /* EditText-Numbers - validation */
    private void mValidateNumber(EditText nEditText, CustomInput nCustomInput) {
        if (nEditText.getText().toString().length() >= 1) {
            nCustomInput.hideError();
        } else {
            nCustomInput.showError();
            mCountErrors++;
        }
    }


    /* EditText-Text - validation */
    private void mValidateText(EditText nEditText, CustomInput nCustomInput) {
        if (nEditText.getText().toString().length() >= 3) {
            nCustomInput.hideError();
        } else {
            nCustomInput.showError();
            mCountErrors++;
        }
    }

    /* EditText-customText - validation */
    private void mValidateMinText(EditText nEditText, CustomInput nCustomInput) {
        if (nEditText.getText().toString().length() >= 1) {
            nCustomInput.hideError();
        } else {
            nCustomInput.showError();
            mCountErrors++;
        }
    }

    /* EditText-Email - validation */
    private void mValidateEmail(EditText nEditText, CustomInput nCustomInput) {
        if (mTools.isEmailValid(nEditText.getText().toString())) {
            nCustomInput.hideError();
        } else {
            nCustomInput.showError();
            mCountErrors++;
        }
    }

    /* EditText-phone - validation */
    private void mValidatePhone(EditText nEditText, CustomInput nCustomInput) {
        if (mTools.isPhoneValid(nEditText.getText().toString())) {
            nCustomInput.hideError();
        } else {
            nCustomInput.showError();
            mCountErrors++;
        }
    }

}
