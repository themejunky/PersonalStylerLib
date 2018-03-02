package com.themejunky.personalstylerlib.customviews;

import android.content.Context;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.themejunky.personalstylerlib.R;


/**
 * Default Custom Input used in all forms
 * @app:ci_type                 - set the type of the input (spinner or edit text)
 * @app:ci_keyboard             - set the input type of the keyboard text (text,min_text,email,phone,password,numbers)
 * @app:ci_title_style          - set text style class for title [R.style.ci_default_style]
 * @app:ci_style                - set text style class for edittext [R.style.ci_default_style]
 * @app:ci_font                 - set the font name of input (edittext)
 * @app:ci_title_font           - set the font name
 * @app:ci_title_text           - set the title text of the input
 * @app:ci_imei_option          - imei option ( "done" - close the keyboard ; "next" move cursor to the next input )
 * @app:ci_botton_line_default  - set bottom line color (default) [R.color.CustomInput_line_default]
 * @app:ci_botton_line_active   - set bottom line color (active) [R.color.CustomService_default_border_color_inactive]
 * @app:ci_botton_line_error    - set bottom line color (error) [R.color.ci_botton_line_error]
 * @app:ci_hint                 - set edittext hint
 * @app:ci_error_mandatory      - set the "*" to flag up the field is mandatory
 * @app:ci_error_message        - set the error message if validation not succeed
 */
public class CustomInput extends BaseCustom_LinearLayout implements View.OnFocusChangeListener {

    private EditText mInput;
    private String mInputType, mInputKeyboard;
    private TextView mError;

    public CustomInput(Context nContext, AttributeSet nAttrs) {
        super(nContext, nAttrs);
        TAG = "CustomInput";
        mTypedarray = nContext.obtainStyledAttributes(nAttrs, R.styleable.CustomInput);
        inflate(nContext, R.layout.custom_input_edit_text, this);

        mInputType = mTypedarray.getString(R.styleable.CustomInput_ci_type);

        TextView mTitle = findViewById(R.id.nTitle);
            setStyle(mTitle, R.styleable.CustomInput_ci_title_style, R.style.ci_default_style);
            setFontFamily(mTitle, mTypedarray.getString(R.styleable.CustomInput_ci_title_font), true);
            setTitle(mTitle, R.styleable.CustomInput_ci_title_text);

        if (mInputType.equals("1")) {

            mInputKeyboard = mTypedarray.getString(R.styleable.CustomInput_ci_keyboard);
            mInput = findViewById(R.id.nInput);

            mInput.setVisibility(View.VISIBLE);
                setStyle(mInput, R.styleable.CustomInput_ci_style, R.style.ci_default_style);
                setFontFamily(mInput, mTypedarray.getString(R.styleable.CustomInput_ci_font), false);
                setInputHint(mInput, R.styleable.CustomInput_ci_hint);
                setInputBottomLineColor(mInput, R.styleable.CustomInput_ci_botton_line_default, R.color.CustomInput_line_default);
                mInput.setOnFocusChangeListener(this);

            switch (mInputKeyboard) {
                case "1":
                    mInput.setInputType(InputType.TYPE_CLASS_TEXT);
                    break;
                case "12":
                    mInput.setInputType(InputType.TYPE_CLASS_TEXT);
                    break;
                case "2":
                    mInput.setInputType(InputType.TYPE_TEXT_VARIATION_WEB_EMAIL_ADDRESS);
                    break;
                case "3":
                    mInput.setInputType(InputType.TYPE_CLASS_NUMBER);
                    break;
                case "4":
                    mInput.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    break;
                case "5":
                    mInput.setInputType(InputType.TYPE_CLASS_NUMBER);
                    break;
            }

            switch (mTypedarray.getString(R.styleable.CustomInput_ci_imei_option)) {
                case "1":
                    mInput.setImeOptions(EditorInfo.IME_ACTION_NEXT);
                    break;
                case "0":
                    mInput.setImeOptions(EditorInfo.IME_ACTION_DONE);
                    break;
            }

        } else if (mInputType.equals("2")) {
            CustomSpinner mSpinner = findViewById(R.id.nCustomSpinner);
            mSpinner.setVisibility(View.VISIBLE);
        }

        mError = findViewById(R.id.nError);
        setTitle(mError, R.styleable.CustomInput_ci_error_message);

        TextView mMandatory = findViewById(R.id.nMandatory);
        setMandatory(mMandatory, R.styleable.CustomInput_ci_error_mandatory);


    }

    /**
     * If the view has focus the EditText bottom border will be green;
     * else default gray
     */
    @Override
    public void onFocusChange(View view, boolean b) {
        if (b) {
            setInputBottomLineColor((TextView) findViewById(R.id.nInput), R.styleable.CustomInput_ci_botton_line_active, R.color.CustomInput_line_active);
        } else {
            setInputBottomLineColor((TextView) findViewById(R.id.nInput), R.styleable.CustomInput_ci_botton_line_default, R.color.CustomInput_line_default);
        }
    }

    public void setText(String nText) {
        mInput.setText(nText);
    }

    /**
     * Set a new error message and shows-up
     */
    public void setErrorMessage(String nErrorMessage) {
        mError.setText(nErrorMessage);
        showError();
    }

    /**
     * Show error : TextView and BottomLine
     */
    public void showError() {
        findViewById(R.id.nError).setVisibility(View.VISIBLE);

        if (mInput != null && mInputType.equals("input")) {
            setInputBottomLineColor(mInput, R.styleable.CustomInput_ci_botton_line_error, R.color.default_error);
        }
    }

    /**
     * Hide error : TextView and BottomLine
     */
    public void hideError() {
        findViewById(R.id.nError).setVisibility(View.GONE);

        if (mInput != null && mInputType.equals("input")) {
            setInputBottomLineColor(mInput, R.styleable.CustomInput_ci_botton_line_default, R.color.CustomInput_line_default);
        }
    }

    /**
     * This method return the type of the current input ( spinner or edittext ) for validation proceess
     *
     * @return : input or spinner
     */
    public String getInputType() {
        return mInputKeyboard;
    }
}