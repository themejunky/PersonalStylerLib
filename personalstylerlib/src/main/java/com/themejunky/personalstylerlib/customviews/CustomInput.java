package com.themejunky.personalstylerlib.customviews;

import android.content.Context;
import android.text.InputType;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.themejunky.personalstylerlib.R;


/**
 * Default Custom Input used in all forms
 *
 * @app:ci_type - set the type of the input (spinner or edit text)
 * @app:ci_keyboard - set the input type of the keyboard text (text,min_text,email,phone,password,numbers)
 * @app:ci_title_style - set text style class for title [R.style.ci_default_style]
 * @app:ci_style - set text style class for edittext [R.style.ci_default_style]
 * @app:ci_font - set the font name of input (edittext)
 * @app:ci_title_font - set the font name
 * @app:ci_title_text - set the title text of the input
 * @app:ci_imei_option - imei option ( "done" - close the keyboard ; "next" move cursor to the next input )
 * @app:ci_botton_line_default - set bottom line color (default) [R.color.CustomInput_line_default]
 * @app:ci_botton_line_active - set bottom line color (active) [R.color.CustomService_default_border_color_inactive]
 * @app:ci_botton_line_error - set bottom line color (error) [R.color.ci_botton_line_error]
 * @app:ci_hint - set edittext hint
 * @app:ci_error_mandatory - set the "*" to flag up the field is mandatory
 * @app:ci_error_message - set the error message if validation not succeed
 */
public class CustomInput extends BaseCustom_LinearLayout implements View.OnFocusChangeListener {

    private EditText mInput, mInputBorder;
    private String mInputType, mInputKeyboard, mGravity, mScrollbar;
    private TextView mError;
    private int mDefaultBackgroundColor, mDefaultBorderRadius, mDefaultBorderStroke, mDefaultBorderColorFocus, mDefaultBorderColorUnFocus;

    public CustomInput(Context nContext, AttributeSet nAttrs) {

        super(nContext, nAttrs);
        Log.d("asdadaadtest123",""+nContext);
        TAG = "CustomInput";
        try {
            mTypedarray = nContext.obtainStyledAttributes(nAttrs, R.styleable.CustomInput);
            Log.d("asdadaadtest123", "1");
            inflate(nContext, R.layout.custom_input_edit_text, this);
            Log.d("asdadaadtest123", "2");
            mInputType = mTypedarray.getString(R.styleable.CustomInput_ci_type);
            Log.d("asdadaadtest123", "3");
        } catch (Exception e) {
            Log.wtf("asdadaadtest123", " ERR : "+e.getMessage());
            Log.wtf("asdadaadtest123", " ERR : "+e.getLocalizedMessage());
        }
        mInput = findViewById(R.id.nInput);Log.d("asdadaadtest123","4");
        TextView mTitle = findViewById(R.id.nTitle);Log.d("asdadaadtest123","5");
        setStyle(mTitle, R.styleable.CustomInput_ci_title_style, R.style.ci_default_style);Log.d("asdadaadtest123","6");
        setFontFamily(mTitle, mTypedarray.getString(R.styleable.CustomInput_ci_title_font), true);Log.d("asdadaadtest123","7");
        setTitle(mTitle, R.styleable.CustomInput_ci_title_text);Log.d("asdadaadtest123","8");

        mDefaultBackgroundColor = getResources().getColor(R.color.CustomInput_default_background_color);Log.d("asdadaadtest123","9");
        mDefaultBorderColorUnFocus = getResources().getColor(R.color.CustomInput_default_border_color_unfocus);Log.d("asdadaadtest123","10");
        mDefaultBorderColorFocus = getResources().getColor(R.color.CustomInput_default_border_color_focus);Log.d("asdadaadtest123","11");
        mDefaultBorderRadius = getResources().getInteger(R.integer.ci_radius);Log.d("asdadaadtest123","12");
        mDefaultBorderStroke = getResources().getInteger(R.integer.ci_stroke);Log.d("asdadaadtest123","13");


        if (mInputType.equals("1")) {Log.d("asdadaadtest123","14");

            mInput.setVisibility(View.VISIBLE);Log.d("asdadaadtest123","15");
            setEditTextParameters(mInput);Log.d("asdadaadtest123","16");
        } else if (mInputType.equals("2")) {Log.d("asdadaadtest123","17");
            CustomSpinner mSpinner = findViewById(R.id.nCustomSpinner);Log.d("asdadaadtest123","18");
            mSpinner.setVisibility(View.VISIBLE);Log.d("asdadaadtest123","19");
            mSpinner.setOnFocusChangeListener(this);Log.d("asdadaadtest123","20");
            setEditTextParameters(mInput);Log.d("asdadaadtest123","21");
        } else if (mInputType.equals("3")) {Log.d("asdadaadtest123","22");
            mInput.setVisibility(View.VISIBLE);Log.d("asdadaadtest123","23");
            setEditTextParameters(mInput);Log.d("asdadaadtest123","24");

            setMinLines(mInput, R.styleable.CustomInput_ci_min_lines, getResources().getInteger(R.integer.ci_min_lines));Log.d("asdadaadtest123","25");
            setMaxLines(mInput, R.styleable.CustomInput_ci_max_lines, getResources().getInteger(R.integer.ci_max_lines));Log.d("asdadaadtest123","26");
            setGravity(mInput, R.styleable.CustomInput_ci_gravity, Gravity.TOP | Gravity.LEFT);Log.d("asdadaadtest123","27");
            setScrollBar(mInput, R.styleable.CustomInput_ci_scrollbars);Log.d("asdadaadtest123","28");
            mInput.setImeOptions(EditorInfo.IME_FLAG_NO_ENTER_ACTION);Log.d("asdadaadtest123","29");
            mInput.setSingleLine(false);Log.d("asdadaadtest123","30");
        }


        mError = findViewById(R.id.nError);Log.d("asdadaadtest123","31");
        setTitle(mError, R.styleable.CustomInput_ci_error_message);Log.d("asdadaadtest123","32");
        TextView mMandatory = findViewById(R.id.nMandatory);Log.d("asdadaadtest123","33");
        setMandatory(mMandatory, R.styleable.CustomInput_ci_error_mandatory);Log.d("asdadaadtest123","34");
    }

    /**
     * If the view has focus the EditText bottom border will be green;
     * else default gray
     */
    @Override
    public void onFocusChange(View nView, boolean b) {
        if (b) {
           setFocusState(nView);
        } else {
           setUnFocusState(nView);
        }
    }

    public void setText(String nText) {
        mInput.setText(nText);
    }

    public void setHint(String nText) {
        mInput.setHint(nText);
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

    public void setEditTextParameters(TextView nTextView) {
        mInputKeyboard = mTypedarray.getString(R.styleable.CustomInput_ci_keyboard);

        setPadding(nTextView, R.styleable.CustomInput_ci_padding, getResources().getInteger(R.integer.ci_padding));
        setStyle(nTextView, R.styleable.CustomInput_ci_style, R.style.ci_default_style);
        setFontFamily(nTextView, mTypedarray.getString(R.styleable.CustomInput_ci_font), false);
        setInputHint(nTextView, R.styleable.CustomInput_ci_hint);
        setDefaultState(nTextView);

        nTextView.setOnFocusChangeListener(this);

       if (mInputKeyboard != null) {
            switch (mInputKeyboard) {
                case "1":
                    nTextView.setInputType(InputType.TYPE_CLASS_TEXT);
                    break;
                case "12":
                    nTextView.setInputType(InputType.TYPE_CLASS_TEXT);
                    break;
                case "2":
                    nTextView.setInputType(InputType.TYPE_TEXT_VARIATION_WEB_EMAIL_ADDRESS);
                    break;
                case "3":
                    nTextView.setInputType(InputType.TYPE_CLASS_NUMBER);
                    break;
                case "4":
                    nTextView.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    /* correction for input type password */
                    setPadding(nTextView, R.styleable.CustomInput_ci_padding, getResources().getInteger(R.integer.ci_password_padding));
                    break;
                case "5":
                    nTextView.setInputType(InputType.TYPE_CLASS_NUMBER);
                    break;
            }
        }

        if (mTypedarray.getString(R.styleable.CustomInput_ci_imei_option) != null) {
            switch (mTypedarray.getString(R.styleable.CustomInput_ci_imei_option)) {
                case "1":
                    nTextView.setImeOptions(EditorInfo.IME_ACTION_NEXT);
                    break;
                case "0":
                    nTextView.setImeOptions(EditorInfo.IME_ACTION_DONE);
                    break;
            }
        }
    }

    public void setFocusState(View editText) {
        Log.d("adaw", "setFocusState");
        setBorderColorAndRadius(editText,
                R.styleable.CustomInput_ci_backgroundColor, mDefaultBackgroundColor,
                R.styleable.CustomInput_ci_border_radius, mDefaultBorderRadius,
                R.styleable.CustomInput_ci_border_stroke, mDefaultBorderStroke,
                R.styleable.CustomInput_ci_border_pressed, mDefaultBorderColorFocus);
    }

    public void setUnFocusState(View editText) {
        Log.d("adaw", "setUnFocusState");
        setBorderColorAndRadius(editText,
                R.styleable.CustomInput_ci_backgroundColor, mDefaultBackgroundColor,
                R.styleable.CustomInput_ci_border_radius, mDefaultBorderRadius,
                R.styleable.CustomInput_ci_border_stroke, mDefaultBorderStroke,
                R.styleable.CustomInput_ci_border_unpressed, mDefaultBorderColorUnFocus);
    }

    public void setDefaultState(View editText) {
        Log.d("adaw", "setDefaultState");
        setBorderColorAndRadius(editText,
                R.styleable.CustomInput_ci_backgroundColor, mDefaultBackgroundColor,
                R.styleable.CustomInput_ci_border_radius, mDefaultBorderRadius,
                R.styleable.CustomInput_ci_border_stroke, mDefaultBorderStroke,
                R.styleable.CustomInput_ci_border_unpressed, mDefaultBorderColorUnFocus);
    }
}
