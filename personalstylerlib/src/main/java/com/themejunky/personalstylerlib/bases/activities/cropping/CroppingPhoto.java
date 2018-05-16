package com.themejunky.personalstylerlib.bases.activities.cropping;

import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.squareup.picasso.Picasso;
import com.themejunky.personalstylerlib.R;

public class CroppingPhoto extends AppCompatActivity implements View.OnTouchListener {
    private int _xDelta;
    private int _yDelta;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_cropping_photo);
        Log.d("pusca",""+getIntent().getStringExtra("EXTRA_FILE_PATH"));
        Uri filePath = Uri.parse(getIntent().getStringExtra("EXTRA_FILE_PATH"));


      //  Picasso.with(this).load(filePath).into((ImageView) findViewById(R.id.nPic));


        findViewById(R.id.nWindows).setOnTouchListener(this);


    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        final int X = (int) event.getRawX();
        final int Y = (int) event.getRawY();
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
                _xDelta = X - lParams.leftMargin;
                _yDelta = Y - lParams.topMargin;
                break;
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_MOVE:
               // if (isViewVisible(findViewById(R.id.nWindow)))
                {


                    RelativeLayout.LayoutParams layoutParams1 = (RelativeLayout.LayoutParams) findViewById(R.id.nTop).getLayoutParams();


                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
                    layoutParams.topMargin = Y - _yDelta;


                    Rect scrollBounds = new Rect();
                    findViewById(R.id.nContainer).getDrawingRect(scrollBounds);

                    Rect scrollBounds2 = new Rect();
                    view.getDrawingRect(scrollBounds2);

                    if (scrollBounds.top<layoutParams.topMargin && scrollBounds.bottom>(layoutParams.topMargin+scrollBounds2.bottom)) {
                        Log.d("adasdasdasdsas","TOP 1 : "+(scrollBounds.top<=layoutParams.topMargin)+"/"+(scrollBounds.bottom>(layoutParams.topMargin+scrollBounds2.bottom)) );
                        Log.d("adasdasdasdsas","TOP 1 : "+(scrollBounds.bottom+"<="+(layoutParams.topMargin+scrollBounds2.bottom) ));
                        layoutParams.topMargin = Y - _yDelta;

                        Log.d("esatas","TOP 1 : "+(Y)+"/"+_yDelta+"/"+ layoutParams1.height+"/"+(Y - _yDelta));
                     layoutParams1.height =     layoutParams1.height +  layoutParams.topMargin;


                    } else if (scrollBounds.top>=layoutParams.topMargin) {
                        layoutParams.topMargin = 0;
                    } else {
                        layoutParams.topMargin = scrollBounds.bottom-scrollBounds2.bottom;
                        Log.d("adasdasdasdsas","FALSEEE "+scrollBounds.bottom+"/"+scrollBounds2.bottom);
                    }


                      /*  if (scrollBounds.top>layoutParams.topMargin) {
                        Log.d("adasdasdasdsas","TOP 2");
                        layoutParams.topMargin = 0;
                    } else if (scrollBounds.bottom>(layoutParams.topMargin+scrollBounds2.bottom)) {
                        Log.d("adasdasdasdsas","BUBUI"+(scrollBounds.top-scrollBounds2.bottom));
                        layoutParams.topMargin = scrollBounds.top-scrollBounds2.bottom;
                    }*/

//                    if (scrollBounds.bottom<=(layoutParams.topMargin+scrollBounds2.bottom)) {
//                        layoutParams.topMargin = Y - _yDelta;
//                    } else {
//                        layoutParams.topMargin = scrollBounds.bottom-scrollBounds2.bottom;
//                    }

               //     Log.d("adasdasdasdsa",""+scrollBounds.bottom+"/"+scrollBounds2.bottom);

                   // Log.d("adasdasdasdsa",""+scrollBounds.bottom+"/"+(layoutParams.topMargin+scrollBounds2.bottom));
                    view.setLayoutParams(layoutParams);
                  //  findViewById(R.id.nTop).setLayoutParams(layoutParams1);
                }



//        //}
//
//                if (!isViewVisible(findViewById(R.id.nWindow))) {
//                    RelativeLayout.LayoutParams dasdsa = (RelativeLayout.LayoutParams) view.getLayoutParams();
//                    if (whereItIs(findViewById(R.id.nWindow)).equals("SUS")) {
//                        dasdsa.addRule(RelativeLayout.ALIGN_PARENT_TOP);
//        Log.d("fixfix","fix_sus");
//                        dasdsa.topMargin=5;
//
//                    } else if (whereItIs(findViewById(R.id.nWindow)).equals("JOS")) {
//                        dasdsa.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
//                        Log.d("fixfix","fix_jos");
//                        dasdsa.bottomMargin=5;
//                    }
//
//                    view.setLayoutParams(dasdsa);
//                    view.requestLayout();
//            }

                break;
        }
        return true;
    }


    private boolean isViewVisible(View view) {
        Rect scrollBounds = new Rect();
        findViewById(R.id.nContainer).getDrawingRect(scrollBounds);
        float top = view.getY();
        float bottom = top + view.getHeight();
        if (scrollBounds.top < top && scrollBounds.bottom > bottom) {
            return true; //View is visible.
        } else {
            return false; //View is NOT visible.
        }
    }

    private String whereItIs(View view) {
        Rect scrollBounds = new Rect();
        findViewById(R.id.nContainer).getDrawingRect(scrollBounds);

        float top = view.getY();
        float bottom = top + view.getHeight();

        if (scrollBounds.top >= top) {
            return "SUS";
        } else if (scrollBounds.bottom <= bottom){
            return "JOS";
        }
        return "";
    }
}
