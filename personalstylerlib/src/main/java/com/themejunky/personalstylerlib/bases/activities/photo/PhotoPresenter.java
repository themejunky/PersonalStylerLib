package com.themejunky.personalstylerlib.bases.activities.photo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.themejunky.personalstylerlib.R;
import com.themejunky.personalstylerlib.bases.model.PhotoModel;
import com.themejunky.personalstylerlib.bases.tools.Tools;
import com.themejunky.personalstylerlib.utils.Constants;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class PhotoPresenter {

    private CompositeDisposable mCompositeDisposable;
    private Tools mTools;
    private PhotoActivity mPhotoActivity;
    private _Interface mListener;

    public interface _Interface {
        void onPhotoPresenter_LayoutReady(List<PhotoModel> mViews);
        void onPhotoPresenter_PhotoReady(PhotoModel mViews, String nType);
        Uri onPhotoPresenter_GetBasePhotoUri();
    }

    PhotoPresenter(PhotoActivity nPhotoActivity, PhotoPresenter._Interface nListener) {
        mCompositeDisposable = new CompositeDisposable();
        mTools = Tools.getInstance(nPhotoActivity);
        mPhotoActivity = nPhotoActivity;
        mListener = nListener;
    }


    public void mSetLayout(ViewGroup nViewGroup, int nNoPic, View.OnClickListener nOnClickListener) {
        mCompositeDisposable.add(mPrepareLayout(nViewGroup, nNoPic, nOnClickListener).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<List<PhotoModel>>() {
            @Override
            public void accept(List<PhotoModel> nViews) {
                if (mPhotoActivity != null && mListener != null) {
                    mListener.onPhotoPresenter_LayoutReady(nViews);
                }
            }
        }));
    }


    public void mPreparePhotoGallery(Intent nReturnedIntent) {
        mCompositeDisposable.add(mPreparePhotoGalleryCore(nReturnedIntent).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<PhotoModel>() {
            @Override
            public void accept(PhotoModel nPhotoModel) {
                if (mPhotoActivity != null && mListener != null) {
                    mListener.onPhotoPresenter_PhotoReady(nPhotoModel,Constants.TAKE_PHOTO_GALLERY);
                }
            }
        }));
    }

    public void mPreparePhotoCamera(Intent nReturnedIntent) {
        mCompositeDisposable.add(mPreparePhotoCameraCore(nReturnedIntent).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<PhotoModel>() {
            @Override
            public void accept(PhotoModel nPhotoModel) {
                if (mPhotoActivity != null && mListener != null) {
                    mListener.onPhotoPresenter_PhotoReady(nPhotoModel,Constants.TAKE_PHOTO_CAMERA);
                }
            }
        }));
    }


    private Observable<List<PhotoModel>> mPrepareLayout(final ViewGroup nViewGroup, final int nNoPic, final View.OnClickListener nOnClickListener) {

        return Observable.fromCallable(new Callable<List<PhotoModel>>() {
            @Override
            public List<PhotoModel> call() {

                LinearLayout nContainer = mTools.getMainContainerAfterInflate(nViewGroup, R.layout.custom_pictures).findViewById(R.id.nPicMainContainer);

                /* initiate mViews(will hold references to all pictures) */
                List<PhotoModel> mViews = new ArrayList<>();
                RelativeLayout nItem;
                for (int i = 0; i < nNoPic; i++) {
                    /* inflate new picture box */
                    nItem = (RelativeLayout) mTools.getMainContainerAfterInflate(nContainer, R.layout.custom_pictures_item);
                    nItem.setTag(String.valueOf(i));
                    nItem.setOnClickListener(nOnClickListener);
                    mViews.add(new PhotoModel(nItem));
                }

                return mViews;
            }
        });
    }


    private Observable<PhotoModel> mPreparePhotoGalleryCore(final Intent nReturnedIntent) {

        return Observable.fromCallable(new Callable<PhotoModel>() {
            @Override
            public PhotoModel call() {
                PhotoModel nPhoto = new PhotoModel();
                nPhoto.mFilePath = nReturnedIntent.getData();
                nPhoto.mPhotoFrom = Constants.TAKE_PHOTO_GALLERY;
                return nPhoto;
            }
        });
    }

    private Observable<PhotoModel> mPreparePhotoCameraCore(final Intent nReturnedIntent) {

        return Observable.fromCallable(new Callable<PhotoModel>() {
            @Override
            public PhotoModel call() {
                PhotoModel nPhoto = new PhotoModel();
                Bitmap nBitmap = null;

//            if (nReturnedIntent != null && nReturnedIntent.getExtras() != null) {
//                Bundle extras = nReturnedIntent.getExtras();
//                if (extras.containsKey("data")) {
//                    nBitmap = (Bitmap) extras.get("data");
//                } else {
//                    nBitmap = optimizeBitmapFromUri(mBasePhotoUri);
//                }
//            } else {
//                nBitmap = optimizeBitmapFromUri(mBasePhotoUri);
//            }

                try {
                    nBitmap = mTools.handleSamplingAndRotationBitmap(mPhotoActivity, mListener.onPhotoPresenter_GetBasePhotoUri());
//            } catch (IOException e) {} try {
                    File outputFile = mTools.createImageFile(mPhotoActivity);
                    FileOutputStream fos = new FileOutputStream(outputFile);
                    nBitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                    nPhoto.mFilePath = Uri.fromFile(outputFile);
                } catch (Exception e) {
                }

                nPhoto.mPhotoFrom = Constants.TAKE_PHOTO_CAMERA;

                mTools.mBitmapTransformers.recycle();
                nBitmap.recycle();

                return nPhoto;
            }
        });
    }
}
