package com.themejunky.personalstylerlib.bases.activities.cropping;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.themejunky.personalstylerlib.bases.tools.Tools;

import java.io.File;
import java.io.FileOutputStream;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class CroppingPhotoPresenter {

    private CompositeDisposable mCompositeDisposable;
    private Tools mTools;
    private CroppingPhotoBase mCroppingPhotoActivity;
    private CroppingPhotoPresenter._Interface mListener;

    private Bitmap mBitmap, mCroppedImage;
    private FileOutputStream mFOS;

    public interface _Interface {
        ImageView getImageToCropFrom();

        RelativeLayout getTopContainer();

        double getRatio();

        void onCroppingPhotoPresenter_PhotoCopped(Uri nUriCropped);

        void unexpectedPresenterError();
    }

    /**
     * Constructor
     * @param nCroppingPhotoActivity - instance of activity
     * @param nListener - listener implementation
     */
    CroppingPhotoPresenter(CroppingPhotoBase nCroppingPhotoActivity, CroppingPhotoPresenter._Interface nListener) {
        mCompositeDisposable = new CompositeDisposable();
        mTools = Tools.getInstance(nCroppingPhotoActivity);
        mCroppingPhotoActivity = nCroppingPhotoActivity;
        mListener = nListener;
    }

    /**
     * Cropping Image
     */
    public void mCroppingImage() {
        mCompositeDisposable.add(mCroppingImageCore().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Uri>() {
            @Override
            public void accept(Uri nCroppedUri) {
                if (mCroppingPhotoActivity != null && mListener != null && nCroppedUri != null) {
                    mListener.onCroppingPhotoPresenter_PhotoCopped(nCroppedUri);
                } else {
                    mListener.unexpectedPresenterError();
                }
            }
        }));
    }

    /**
     * Crop the Bitmap and save;
     * @return - uri of bitmap cropped
     */
    private Observable<Uri> mCroppingImageCore() {

        return Observable.fromCallable(new Callable<Uri>() {
            @Override
            public Uri call() {

                try {

                    Drawable mDrawing = mListener.getImageToCropFrom().getDrawable();
                    mBitmap = ((BitmapDrawable) mDrawing).getBitmap();
                    /* create crop :
                     mTop.getHeight() -> height of the above capture
                     mBitmap.getHeight() / mImage.getHeight() -> real image height / imageView height -> coresponding ration between real image size and what user see in our imageview
                    */
                    mCroppedImage = Bitmap.createBitmap(mBitmap, 0, (int) (mListener.getTopContainer().getHeight() * ((double) mBitmap.getHeight() / mListener.getImageToCropFrom().getHeight())), mBitmap.getWidth(), (int) (mBitmap.getWidth() / mListener.getRatio()));

                    /* create new file and save the cropped bitmap */
                    File outputFile = mTools.createImageFile(mCroppingPhotoActivity);
                    mFOS = new FileOutputStream(outputFile);
                    mCroppedImage.compress(Bitmap.CompressFormat.PNG, 100, mFOS);


                    mFOS.close();

                    return Uri.fromFile(outputFile);

                } catch (Exception e) {
                    return null;
                }
            }
        });
    }
}