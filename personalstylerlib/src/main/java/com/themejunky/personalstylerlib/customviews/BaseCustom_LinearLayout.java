package com.themejunky.personalstylerlib.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.themejunky.personalstylerlib.R;


public class BaseCustom_LinearLayout extends LinearLayout {

    protected Context mContext;
    protected TypedArray mTypedarray;
    protected String TAG = "CustomView";

    public BaseCustom_LinearLayout(Context context) {
        super(context);
    }

    public BaseCustom_LinearLayout(Context nContext, AttributeSet nAttrs) {
        super(nContext, nAttrs);
        this.mContext = nContext;
    }

    /**
     * Set the specific font by his name in assets folder. If that font is not found then default font will be applyed
     *
     * @param nView         - the view on witch will be the font apply ( textview or edittext )
     * @param nFontResource - name of the font in assets folder
     * @param nDefaulfFont  - deafult value that should be set. True = bold / False = regular
     */
    protected void setFontFamily(TextView nView, String nFontResource, Boolean nDefaulfFont) {
        try {
            nView.setTypeface(Typeface.createFromAsset(mContext.getAssets(), nFontResource));
        } catch (Exception e) {
            Log.d(TAG, "" + e.getMessage());
            if (nDefaulfFont) {
                nView.setTypeface(Typeface.createFromAsset(mContext.getAssets(), mContext.getResources().getString(R.string.default_font_bold)));
            } else {
                nView.setTypeface(Typeface.createFromAsset(mContext.getAssets(), mContext.getResources().getString(R.string.default_font_regular)));
            }
        }
    }

    /**
     * Set the specific style class.
     *
     * @param nView              - the view on witch will be the style class apply ( textview or edittext )
     * @param nResourceReference - custom styleable references
     * @param nDefault           - default style if none is provided
     */
    protected void setStyle(TextView nView, int nResourceReference, int nDefault) {
        try {
            nView.setTextAppearance(mContext, getStyle(nResourceReference, nDefault));
        } catch (Exception e) {
            Log.d(TAG, "" + e.getMessage());
        }
    }

    /**
     * Set the specific padding with default fallback if padding not provided
     *
     * @param nView              - the view on witch will be the padding apply ( textview or edittext )
     * @param nResourceReference - custom styleable references
     * @param nDefaultDim        - default style if none is provided
     */
    protected void setPadding(View nView, int nResourceReference, int nDefaultDim) {
        try {
            nView.setPadding(0, getDimension(nResourceReference, nDefaultDim),0, getDimension(nResourceReference, nDefaultDim));
        } catch (Exception e) {
            Log.d(TAG, "" + e.getMessage());
        }
    }

    protected void setPadding(View nView, int nResourceReference) {
        try {
            nView.setPadding(nResourceReference, nResourceReference, nResourceReference, nResourceReference);
        } catch (Exception e) {
            Log.d(TAG, "" + e.getMessage());
        }
    }

    /**
     * Set the specific title
     *
     * @param nView              - the TextView on witch will be the title apply
     * @param nResourceReference - string reference to be set
     */
    protected void setTitle(TextView nView, int nResourceReference) {
        try {
            if (mTypedarray.getString(nResourceReference) != null) {
                nView.setText(mTypedarray.getString(nResourceReference));
            } else {
                nView.setVisibility(View.GONE);
            }
        } catch (Exception e) {
        }
    }

    /**
     * Set the tag
     *
     * @param nResourceReference - string reference to be set
     */
    protected String setTags(int nResourceReference) {
        try {
            if (mTypedarray.getString(nResourceReference) != null) {
                return mTypedarray.getString(nResourceReference);
            }
        } catch (Exception e) {
            return "";
        }
        return "";
    }

    protected void setTitleFallbackInvisible(TextView nView, int nResourceReference) {
        try {
            if (mTypedarray.getString(nResourceReference) != null) {
                nView.setText(mTypedarray.getString(nResourceReference));
            } else {
                nView.setVisibility(View.INVISIBLE);
            }
        } catch (Exception e) {
        }
    }

    /**
     * Set text color
     *
     * @param nView         - the TextView on witch will be the color applyed
     * @param nColor        - the color that will be applyed
     * @param nDefaultColor - the defacult color if none is provided
     */
    protected void setTextColor(TextView nView, int nColor, int nDefaultColor) {
        try {
            nView.setTextColor(getColorWithDefaultPreloaded(nColor, nDefaultColor));
        } catch (Exception e) {
            nView.setTextColor(nDefaultColor);
            Log.d(TAG, "" + e.getMessage());
        }
    }

    protected void setTextColorButton(TextView nView, int nColor, int nDefaultColor) {
        try {
            nView.setTextColor(getColorWithDefaultPreloaded(nColor, mContext.getResources().getColor(nDefaultColor)));
        } catch (Exception e) {
            nView.setTextColor(nDefaultColor);
            Log.d(TAG, "" + e.getMessage());
        }
    }

    protected void setTextColor(TextView nView, int nColor) {
        try {
            nView.setTextColor(getResources().getColor(nColor));
        } catch (Exception e) {
            try {
                nView.setTextColor(nColor);
            } catch (Exception e2) {
            Log.d(TAG, "" + e2.getMessage());}
        }
    }


    /**
     * Try setting resource image to imageview
     * @param nImage - imageview in witch to set the resource
     * @param nResourceReference - resource
     */
    protected void setImageToImageView(ImageView nImage, int nResourceReference) {
        try {
            nImage.setImageDrawable(mTypedarray.getDrawable(nResourceReference));
            nImage.setVisibility(View.GONE);
        } catch (Exception e) {
            nImage.setVisibility(View.GONE);
        }
    }

    /**
     * Trigger "*" to appear and show up to the user that this field is mandatory
     *
     * @param nView              - the TextView that contain "*" to be shown/hidden
     * @param nResourceReference - show/hide the TextView
     */
    protected void setMandatory(TextView nView, int nResourceReference) {
        try {
            if (mTypedarray.getBoolean(nResourceReference, false)) {
                nView.setVisibility(View.VISIBLE);
            } else {
                nView.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            Log.d(TAG, "" + e.getMessage());
        }
    }

    protected void setViewVisibility(View nView, int nResourceReference) {
        try {
            if (mTypedarray.getBoolean(nResourceReference, true)) {
                nView.setVisibility(View.VISIBLE);
            } else {
                nView.setVisibility(View.INVISIBLE);
            }
        } catch (Exception e) {
            Log.d(TAG, "" + e.getMessage());
        }
    }

    /**
     * Set the bottom color of the EditText
     *
     * @param nView              - the desired EditText to modify bottom color
     * @param nResourceReference - the desired color to be set
     * @param nDefaultColor      - the default color if none is provided
     */
    protected void setInputBottomLineColor(TextView nView, int nResourceReference, int nDefaultColor) {
        try {
            nView.getBackground().setColorFilter(getColor(nResourceReference, nDefaultColor), PorterDuff.Mode.SRC_ATOP);
        } catch (Exception e) {
            Log.d(TAG, "" + e.getMessage());
        }
    }

    /**
     * Set the solid color of the Button
     *
     * @param nDrawable          - the Drawable of the button on witch will be set the solid color
     * @param nResourceReference - the solid color to be set
     */
    protected void setmButtonDrawableColor(GradientDrawable nDrawable, int nResourceReference) {
        try {
            nDrawable.setColor(getColor(nResourceReference, 0));
        } catch (Exception e) {
            Log.d(TAG, "" + e.getMessage());
        }
    }

    /**
     * Set the hint text of the EditText
     *
     * @param nView              - EditText on witch will be set
     * @param nResourceReference - Desired hint
     */
    protected void setInputHint(TextView nView, int nResourceReference) {
        try {
            nView.setHint(mTypedarray.getString(nResourceReference));
        } catch (Exception e) {
            Log.d(TAG, "" + e.getMessage());
        }
    }

    /**
     * Set the border color, stroke and radius of the EditText throw GradiendDrawable of the view
     *
     * @param nView               - EditText on witch will be applyed
     * @param nColor              - solid background color
     * @param nRadius             - radius of the alll 4 corners
     * @param nStroke             - stroke of the border
     * @param nBorderColor        - border color
     * @param nDefaultBorderColor - default border color if none is provided
     */
    public void setBorderColorAndRadius(View nView, int nColor, int nDefaultColor, int nRadius, int nDefaultRadius, int nStroke, int nDefaultStroke, int nBorderColor, int nDefaultBorderColor) {
        try {
            GradientDrawable mGD = new GradientDrawable();
            mGD.setColor(mTypedarray.getInt(nColor, nDefaultColor));
            mGD.setCornerRadius(mTypedarray.getFloat(nRadius, nDefaultRadius));
            mGD.setStroke(mTypedarray.getInt(nStroke, nDefaultStroke), mTypedarray.getColor(nBorderColor, nDefaultBorderColor));
            nView.setBackground(mGD);
        } catch (Exception e) {
            Log.d(TAG, "" + e.getMessage());
        }
    }

    public void setBorderColorAndRadius(View nView, int nColor, int nRadius, int nStroke, int nBorderColor) {
        try {
            GradientDrawable mGD = new GradientDrawable();
            mGD.setColor(getResources().getColor(nColor));
            mGD.setCornerRadius(nRadius);
            mGD.setStroke(nStroke, getResources().getColor(nBorderColor));
            nView.setBackground(mGD);
        } catch (Exception e) {
            Log.d(TAG, "" + e.getMessage());
        }
    }

    /**
     * Get the style items from resource
     *
     * @param nResourceReference - styleable references
     * @param nDefault           - default color if none is provided
     * @return - style item as int
     */
    private int getStyle(int nResourceReference, int nDefault) {
        return mTypedarray.getResourceId(nResourceReference, nDefault);
    }

    /**
     * Get the dimension
     *
     * @param nResourceReference - styleable references
     * @param nDefault           - default dimension if none is provided
     * @return - style item as int
     */
    private int getDimension(int nResourceReference, int nDefault) {
        return (int) (mTypedarray.getDimension(nResourceReference, nDefault));
    }


    /**
     * Get the color from resources
     *
     * @param nResourceReference - styleable references
     * @param nDefault           - default color if none is provided
     * @return - color item as int
     */
    public int getColor(int nResourceReference, int nDefault) {
        if (nDefault != 0) {
            return mTypedarray.getColor(nResourceReference, mContext.getResources().getColor(nDefault));
        } else {
            return mTypedarray.getColor(nResourceReference, 0);
        }
    }

    public int getColorWithDefaultPreloaded(int nResourceReference, int nDefault) {
        return mTypedarray.getColor(nResourceReference, nDefault);
    }

    protected void setPaddingLeft(EditText nView, int nResourceReference, int nDefaultDim) {
        try {
            nView.setPadding(mTypedarray.getInt(nResourceReference, nDefaultDim), 0, 0, 0);
        } catch (Exception e) {
            Log.d(TAG, "" + e.getMessage());
        }
    }

    protected void setLines(EditText nView, int nResourceReference, int nDefaultDim) {
        try {
            nView.setLines(mTypedarray.getInt(nResourceReference, nDefaultDim));
        } catch (Exception e) {
            Log.d(TAG, "" + e.getMessage());
        }
    }

    protected void setMinLines(EditText nView, int nResourceReference, int nDefaultDim) {
        try {
            nView.setMinLines(mTypedarray.getInt(nResourceReference, nDefaultDim));
        } catch (Exception e) {
            Log.d(TAG, "" + e.getMessage());
        }
    }

    protected void setMaxLines(EditText nView, int nResourceReference, int nDefaultDim) {
        try {
            nView.setMaxLines(mTypedarray.getInt(nResourceReference, nDefaultDim));
        } catch (Exception e) {
            Log.d(TAG, "" + e.getMessage());
        }
    }

    protected void setGravity(EditText nView, int nResourceReference, int nDefaultDim) {
        try {
            nView.setGravity(mTypedarray.getInt(nResourceReference, nDefaultDim));
        } catch (Exception e) {
            Log.d(TAG, "" + e.getMessage());
        }
    }

    protected void setScrollBar(EditText nView, int nResourceReference) {
        try {
            switch (nResourceReference){
                case 1:
                    nView.setVerticalScrollBarEnabled(true);
                    break;
                case 2:
                    nView.setHorizontallyScrolling(true);
                    break;
                case 3:
                    nView.setVerticalScrollBarEnabled(false);
                    nView.setHorizontallyScrolling(false);
                    break;
                    default: nView.setVerticalScrollBarEnabled(true);
            }
        } catch (Exception e) {
            Log.d(TAG, "" + e.getMessage());
        }
    }




    protected void setDevider(View nView, int nResourceReference) {
        try {
            LayoutParams params = (LayoutParams) nView.getLayoutParams();
            params.width = nResourceReference;
            nView.setLayoutParams(params);
        } catch (Exception e) {
            Log.d(TAG, "" + e.getMessage());
        }
    }


}
