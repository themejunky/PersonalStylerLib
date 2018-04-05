package com.themejunky.personalstylerlib.utils;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

import com.themejunky.personalstylerlib.R;
import com.themejunky.personalstylerlib.bases.tools.Tools;
import com.themejunky.personalstylerlib.customviews.CustomButton;
import com.themejunky.personalstylerlib.customviews.CustomInput;

import java.util.ArrayList;
import java.util.List;

/**
 * Validate form inputs from provided inputs and activate/deactivate form button
 */
public class FormValidator implements TextWatcher, Spinner.OnItemSelectedListener {

    /* model that hold editext with input type */
    private List<EditTextChangeListenerModel> mEditTextViews;
    /* model that hold spinners with input type */
    private List<SpinnerChangeListenerModel> mSpinnerViews;
    /* form button */
    private CustomButton mSubmitButton;
    private int mValidationScore;
    private Tools mTools;

    public FormValidator(Context nContext, ArrayList<CustomInput> nViews, CustomButton nSubmitButton) {
        mSubmitButton = nSubmitButton;
        mEditTextViews = new ArrayList<>();
        mSpinnerViews = new ArrayList<>();
        mTools = Tools.getInstance(nContext);

        for (CustomInput nView : nViews) {
            if (nView.findViewById(R.id.nInput) instanceof EditText && nView.findViewById(R.id.nInput).getVisibility()== View.VISIBLE) {
                ((EditText) nView.findViewById(R.id.nInput)).addTextChangedListener(this);
                mEditTextViews.add(new EditTextChangeListenerModel(nView));
            } else if (nView.findViewById(R.id.nSpinner) instanceof Spinner && nView.findViewById(R.id.nSpinner).getVisibility()== View.VISIBLE) {
                ((Spinner) nView.findViewById(R.id.nSpinner)).setOnItemSelectedListener(this);
                mSpinnerViews.add(new SpinnerChangeListenerModel(nView));
            }
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void afterTextChanged(Editable editable) {
        mCoreEngine();
    }

    /* EditText-Password - validation */
    private void mValidatePassword(EditTextChangeListenerModel nView) {
        if (nView.mEditText.getText().toString().length() >= 5) {
            nView.mCustomInput.hideError();
            mValidationScore++;
        }
    }

    /* EditText-Numbers - validation */
    private void mValidateNumbers(EditTextChangeListenerModel nView) {
        if (nView.mEditText.getText().toString().length() >= 1) {
            nView.mCustomInput.hideError();
            mValidationScore++;
        }
    }

    /* EditText-Text - validation */
    private void mValidateText(EditTextChangeListenerModel nView) {
        if (nView.mEditText.getText().toString().length() >= 3) {
            nView.mCustomInput.hideError();
            mValidationScore++;
        }
    }

    /* EditText-minText - validation */
    private void mValidateMinText(EditTextChangeListenerModel nView) {
        if (nView.mEditText.getText().toString().length() >= 1) {
            nView.mCustomInput.hideError();
            mValidationScore++;
        }
    }

    /* EditText-Email - validation */
    private void mValidateEmail(EditTextChangeListenerModel nView) {
        if (mTools.isEmailValid(nView.mEditText.getText().toString())) {
            nView.mCustomInput.hideError();
            mValidationScore++;
        }
    }

    /* EditText-Phone - validation */
    private void mValidatePhone(EditTextChangeListenerModel nView) {
        if (mTools.isPhoneValid(nView.mEditText.getText().toString())) {
            nView.mCustomInput.hideError();
            mValidationScore++;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        mCoreEngine();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    /* EditText - model information */
    class EditTextChangeListenerModel {
        EditText mEditText;
        String mType;
        CustomInput mCustomInput;

        EditTextChangeListenerModel(CustomInput nCustomInput) {
            mEditText = nCustomInput.findViewById(R.id.nInput);
            mType = nCustomInput.getInputType();
            mCustomInput = nCustomInput;
        }
    }

    /* Spinner - model information */
    class SpinnerChangeListenerModel {
        Spinner mSpinner;
        CustomInput mCustomInput;

        SpinnerChangeListenerModel(CustomInput nCustomInput) {
            mSpinner = nCustomInput.findViewById(R.id.nSpinner);
            mCustomInput = nCustomInput;
        }
    }


    private void mCoreEngine() {
        mValidationScore = 0;

        /* loop all edittext and count validated edittext */
        for (EditTextChangeListenerModel nView : mEditTextViews) {
            switch (nView.mType) {
                case "1":
                    mValidateText(nView);
                    break;
                case "12":
                    mValidateMinText(nView);
                    break;
                case "2":
                    mValidateEmail(nView);
                    break;
                case "3":
                    mValidatePhone(nView);
                    break;
                case "4":
                    mValidatePassword(nView);
                    break;
                case "5":
                    mValidateNumbers(nView);
                    break;
            }
        }

        /* loop all spinners and count validated spinners */
        for (SpinnerChangeListenerModel nView : mSpinnerViews) {
            if (nView.mSpinner.getSelectedItemPosition()!=0) {
                mValidationScore++;
                nView.mCustomInput.hideError();
            }
        }

        /* if numbers of valid CustomInputs != total of them ..... shit happends */
        if (mValidationScore == (mEditTextViews.size()+mSpinnerViews.size())) {
            mSubmitButton.setActive();
        } else {
            mSubmitButton.setInactive();
        }
    }
}
