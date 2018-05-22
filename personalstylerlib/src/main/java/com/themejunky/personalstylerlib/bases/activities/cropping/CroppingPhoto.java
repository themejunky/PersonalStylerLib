package com.themejunky.personalstylerlib.bases.activities.cropping;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.squareup.picasso.Picasso;
import com.themejunky.personalstylerlib.R;
import com.themejunky.personalstylerlib.bases.tools.Tools;
import com.themejunky.personalstylerlib.utils.Constants;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

public class CroppingPhoto extends AppCompatActivity implements View.OnTouchListener, ViewTreeObserver.OnGlobalLayoutListener, View.OnClickListener {
    private int _yDelta;
    private RelativeLayout mTop;
    private int mViewTopStartHeight,mWindowHeight;
    private Boolean mViewStartModification = false;
    private RelativeLayout mWindows;
    private RelativeLayout.LayoutParams paramsTop;
    private Uri mUriPic;
    private Tools mTools;

    @Override
    public void onClick(View v) {
        mCropPhoto();
    }

    public interface Interface {
        void onCroppingPhoto_Finished();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_cropping_photo);
        mTools = Tools.getInstance(this);
        mUriPic = Uri.parse(getIntent().getStringExtra("EXTRA_FILE_PATH"));

        Picasso.with(this).load(mUriPic).into((ImageView) findViewById(R.id.nPic));

        mWindows = findViewById(R.id.nWindows);
        mWindows.setOnTouchListener(this);
        mWindows.getViewTreeObserver().addOnGlobalLayoutListener(this);

        mTop = findViewById(R.id.nTop);

        findViewById(R.id.nCrop).setOnClickListener(this);
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        int Y = (int) event.getRawY();
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
                _yDelta = Y - lParams.topMargin;
                break;
            case MotionEvent.ACTION_UP:
                mViewStartModification = false;
                break;
            case MotionEvent.ACTION_MOVE: {

                if (!mViewStartModification) {
                    paramsTop = (RelativeLayout.LayoutParams) mTop.getLayoutParams();
                    mViewTopStartHeight = mTop.getHeight();
                    mViewStartModification = true;
                    mWindowHeight = mWindows.getHeight();
                }

              if (findViewById(R.id.nPic).getHeight()-(mViewTopStartHeight+(Y - _yDelta))>mWindowHeight) {
                  paramsTop.height = mViewTopStartHeight+(Y - _yDelta);
                  mTop.setLayoutParams(paramsTop);
              } else {
                  paramsTop.height = findViewById(R.id.nPic).getHeight()-mWindowHeight;
                  mTop.setLayoutParams(paramsTop);
              }
            }

            break;
        }
        return true;
    }

    @Override
    public void onGlobalLayout() {
        RelativeLayout.LayoutParams mWindowParam = (RelativeLayout.LayoutParams) mWindows.getLayoutParams();
        mWindowParam.height = (int) (mWindows.getWidth() / 1.8);
        mWindows.setLayoutParams(mWindowParam);
        mWindows.requestLayout();
        mWindows.getViewTreeObserver().removeOnGlobalLayoutListener(this);
    }

    public void mCropPhoto() {
        Drawable drawing = ((ImageView) findViewById(R.id.nPic)).getDrawable();
        Bitmap   bitmap = ((BitmapDrawable) drawing).getBitmap();
        Bitmap output = Bitmap.createBitmap(bitmap, 0,(int) (mTop.getHeight()*((double) bitmap.getHeight()/findViewById(R.id.nPic).getHeight())), bitmap.getWidth(),(int) (bitmap.getWidth()/1.8));
        ((ImageView) findViewById(R.id.nPic)).setImageBitmap(output);
Log.d("adsasdasdsa","gata ?");


Uri calea = null;
        try {
            File outputFile = mTools.createImageFile(this);
            FileOutputStream fos = new FileOutputStream(outputFile);
            output.compress(Bitmap.CompressFormat.PNG, 100, fos);
            calea = Uri.fromFile(outputFile);
        } catch (Exception e) {
        }

        //nPhoto.mPhotoFrom = Constants.TAKE_PHOTO_CAMERA;

        mTools.mBitmapTransformers.recycle();
      //  nBitmap.recycle();

        Intent returnIntent = new Intent();
        returnIntent.putExtra("EXTRA_FILE_PATH", calea);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();



    }
}
