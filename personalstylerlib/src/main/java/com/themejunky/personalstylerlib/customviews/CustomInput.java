package com.themejunky.personalstylerlib.customviews;

import android.content.Context;
import android.graphics.Color;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
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
    private TextView mError,mTitle;
    private int mDefaultBackgroundColor, mDefaultBorderRadius, mDefaultBorderStroke, mDefaultBorderColorFocus, mDefaultBorderColorUnFocus,mDefaultBorderColorError;
    private int defaultPaddingTopBottom,fontPaddingCorrection,defaultPaddingLeftRight;

    public CustomInput(Context nContext, AttributeSet nAttrs) {

        super(nContext, nAttrs);

            TAG = "CustomInput";
            mTypedarray = nContext.obtainStyledAttributes(nAttrs, R.styleable.CustomInput);
            inflate(nContext, R.layout.custom_input_edit_text, this);
            mInputType = mTypedarray.getString(R.styleable.CustomInput_ci_type);
            mInput = findViewById(R.id.nInput);
             mTitle = findViewById(R.id.nTitle);
        //  setStyle(mTitle, R.styleable.CustomInput_ci_title_style, R.style.ci_default_style);
             setFontFamily(mTitle, mTypedarray.getString(R.styleable.CustomInput_ci_title_font), true);
             setTitle(mTitle, R.styleable.CustomInput_ci_title_text);

             defaultPaddingTopBottom = (int) (5*mDensity + 0.5f);
             defaultPaddingLeftRight = (int) (10*mDensity + 0.5f);
             fontPaddingCorrection = defaultPaddingTopBottom+(int) (0.15f*14);

            mDefaultBackgroundColor = getResources().getColor(R.color.CustomInput_default_background_color);
            mDefaultBorderColorUnFocus = getResources().getColor(R.color.CustomInput_default_border_color_unfocus);
            mDefaultBorderColorFocus = getResources().getColor(R.color.CustomInput_default_border_color_focus);
            mDefaultBorderRadius = getResources().getInteger(R.integer.ci_radius);
            mDefaultBorderStroke = getResources().getInteger(R.integer.ci_stroke);
            mDefaultBorderColorError = getResources().getColor(R.color.default_error);


            if (mInputType.equals("1")) {
                mInput.setVisibility(View.VISIBLE);

                setEditTextParameters(mInput);

            }

            else if (mInputType.equals("2")) {
                    CustomSpinner mSpinner = findViewById(R.id.nCustomSpinner);
                    mSpinner.setVisibility(View.VISIBLE);
                    mSpinner.setOnFocusChangeListener(this);
                    setEditTextParameters(mInput);
                 //   setPadding(findViewById(R.id.nSpinnerContainer), ));
                    //setPadding(findViewById(R.id.nSpinnerContainer),defaultPaddingLeftRight,fontPaddingCorrection,defaultPaddingLeftRight,defaultPaddingTopBottom);
                    setBorderColorAndRadius(findViewById(R.id.nSpinnerContainer), R.styleable.CustomInput_ci_backgroundColor, mDefaultBackgroundColor, R.styleable.CustomInput_ci_border_radius, mDefaultBorderRadius, R.styleable.CustomInput_ci_border_stroke, mDefaultBorderStroke, R.styleable.CustomInput_ci_border_unpressed, mDefaultBorderColorUnFocus);

            } else if (mInputType.equals("3")) {
                mInput.setVisibility(View.VISIBLE);
                setEditTextParameters(mInput);

                setMinLines(mInput, R.styleable.CustomInput_ci_min_lines, getResources().getInteger(R.integer.ci_min_lines));
                setMaxLines(mInput, R.styleable.CustomInput_ci_max_lines, getResources().getInteger(R.integer.ci_max_lines));
                setGravity(mInput, R.styleable.CustomInput_ci_gravity, Gravity.TOP | Gravity.LEFT);
                setScrollBar(mInput, R.styleable.CustomInput_ci_scrollbars);
                mInput.setImeOptions(EditorInfo.IME_FLAG_NO_ENTER_ACTION);
                mInput.setSingleLine(false);
            }


            //if there is no title text... hide
            if (mTitle.getText().toString().length()==0) {
                ((ViewGroup) mTitle.getParent()).setVisibility(View.GONE);
            }
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


    public void setTitle(String nText) {
        mTitle.setText(nText);
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
        if (mInput != null) {
            setmError(mInput);
            setmError(findViewById(R.id.nSpinnerContainer));
        }
    }

    /**
     * Hide error : TextView and BottomLine
     */
    public void hideError() {
        findViewById(R.id.nError).setVisibility(View.GONE);
        if (mInput != null ) {
            setDefaultState(mInput);
            setDefaultState(findViewById(R.id.nSpinnerContainer));
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


        setPadding(nTextView,defaultPaddingLeftRight,fontPaddingCorrection,defaultPaddingLeftRight,defaultPaddingTopBottom);
        setStyle(nTextView, R.styleable.CustomInput_ci_style, R.style.ci_default_style);
        setFontFamily(nTextView, mTypedarray.getString(R.styleable.CustomInput_ci_font), false);
        setInputHint(nTextView, R.styleable.CustomInput_ci_hint);
        setDefaultState(nTextView);

        nTextView.setOnFocusChangeListener(this);

       if (mInputKeyboard != null) {
            switch (mInputKeyboard) {
                case "1":
                    nTextView.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
                    break;
                case "12":
                    nTextView.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
                    break;
                case "2":
                    nTextView.setInputType(InputType.TYPE_TEXT_VARIATION_WEB_EMAIL_ADDRESS);
                    break;
                case "3":
                    nTextView.setInputType(InputType.TYPE_CLASS_NUMBER);
                    break;
                case "4":
                    nTextView.setTransformationMethod(PasswordTransformationMethod.getInstance());
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

        mError = findViewById(R.id.nError);
        setTitle(mError, R.styleable.CustomInput_ci_error_message);
        TextView mMandatory = findViewById(R.id.nMandatory);
        setMandatory(mMandatory, R.styleable.CustomInput_ci_error_mandatory);
        mInput.setHintTextColor(Color.parseColor("#cbcbcb"));
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

    public void setmError(View editText) {
        Log.d("adaw", "setDefaultState2");
        setBorderColorAndRadius(editText,
                R.styleable.CustomInput_ci_backgroundColor, mDefaultBackgroundColor,
                R.styleable.CustomInput_ci_border_radius, mDefaultBorderRadius,
                R.styleable.CustomInput_ci_border_stroke, mDefaultBorderStroke,
                R.styleable.CustomInput_ci_border_unpressed, mDefaultBorderColorError);
    }
}
