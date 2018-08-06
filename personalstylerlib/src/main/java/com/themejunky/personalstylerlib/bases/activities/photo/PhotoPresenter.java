package com.themejunky.personalstylerlib.bases.activities.photo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.Uri;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
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
    private Photo mPhotoActivity;
    private _Interface mListener;
    private int position;
    public interface _Interface {
        void onPhotoPresenter_LayoutReady(List<PhotoModel> mViews);

        int onPhotoPresenter_PhotoReady(PhotoModel mViews, String nTypem,int nPosition);

        Uri onPhotoPresenter_GetBasePhotoUri();
    }

    /**
     * Constructor
     *
     * @param nPhotoActivity - instance of activity
     * @param nListener      - listener implementation
     */
    PhotoPresenter(Photo nPhotoActivity, PhotoPresenter._Interface nListener) {
        mCompositeDisposable = new CompositeDisposable();
        mTools = Tools.getInstance(nPhotoActivity);
        mPhotoActivity = nPhotoActivity;
        mListener = nListener;
    }

    /**
     * @param nViewGroup       - ViewGroup in witch all the photos will appear (app viewgroup)
     * @param nNoPic           - number of max pictures
     * @param nOnClickListener - click listener
     */
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

    /**
     * Prepare foto from gallery and return with neccesary data as PhotoModel
     *
     * @param nReturnedIntent - returned intent when user came back from gallery
     */

    public void mPreparePhotoGallery(final Intent nReturnedIntent) {

        position = Constants.TAKE_PHOTO;

        mCompositeDisposable.add(mPreparePhotoLoadingPlace().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<PhotoModel>() {
            @Override
            public void accept(PhotoModel nPhotoModel) {
                if (mPhotoActivity != null && mListener != null) {
                    position = mListener.onPhotoPresenter_PhotoReady(nPhotoModel, Constants.TAKE_PHOTO_BEFORE_CROP, position);

                    mCompositeDisposable.add(mPreparePhotoGalleryCore(nReturnedIntent).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<PhotoModel>() {
                        @Override
                        public void accept(PhotoModel nPhotoModel) {
                            if (mPhotoActivity != null && mListener != null) {
                                nPhotoModel.mPosition = position;
                                mListener.onPhotoPresenter_PhotoReady(nPhotoModel, Constants.TAKE_PHOTO_GALLERY,position);
                            }
                        }
                    }));

                }
            }
        }));


    }

    private Observable<PhotoModel> mPreparePhotoGalleryCore(final Intent nReturnedIntent) {

        return Observable.fromCallable(new Callable<PhotoModel>() {
            @Override
            public PhotoModel call() {
                PhotoModel nPhoto = new PhotoModel();
//                nPhoto.mFilePath = nReturnedIntent.getData();
//                nPhoto.mFilePathString = nReturnedIntent.getData().getPath();
//                nPhoto.mPhotoFrom = Constants.TAKE_PHOTO_GALLERY;
//                return nPhoto;


                //  PhotoModel nPhoto = new PhotoModel();
                Bitmap nBitmap = null;

                try {
                    nBitmap = mTools.handleSamplingAndRotationBitmap(mPhotoActivity, nReturnedIntent.getData());
                    File outputFile = mTools.createImageFile(mPhotoActivity);
                    FileOutputStream fos = new FileOutputStream(outputFile);
                    nBitmap.compress(Bitmap.CompressFormat.JPEG, 90, fos);
                    nPhoto.mFilePath = Uri.fromFile(outputFile);
                    nPhoto.mFilePathString = nPhoto.mFilePath.getPath();
                } catch (Exception e) {
                }

                nPhoto.mPhotoFrom = Constants.TAKE_PHOTO_GALLERY;

return nPhoto;
            }

        });
    }

    /**
     * Prepare foto from camera and return with neccesary data as PhotoModel
     *
     * @param nReturnedIntent - returned intent when user came back from camera
     */
    public void mPreparePhotoCamera(final Intent nReturnedIntent) {

        position = Constants.TAKE_PHOTO;

        mCompositeDisposable.add(mPreparePhotoLoadingPlace().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<PhotoModel>() {
            @Override
            public void accept(PhotoModel nPhotoModel) {
                if (mPhotoActivity != null && mListener != null) {
                    position = mListener.onPhotoPresenter_PhotoReady(nPhotoModel, Constants.TAKE_PHOTO_BEFORE_CROP, position);

                    mCompositeDisposable.add(mPreparePhotoCameraCore(nReturnedIntent).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<PhotoModel>() {
                        @Override
                        public void accept(PhotoModel nPhotoModel) {
                            if (mPhotoActivity != null && mListener != null) {
                                nPhotoModel.mPosition = position;
                                mListener.onPhotoPresenter_PhotoReady(nPhotoModel, Constants.TAKE_PHOTO_CAMERA,position);
                            }
                        }
                    }));

                }
            }
        }));


    }

    private Observable<PhotoModel> mPreparePhotoCameraCore(final Intent nReturnedIntent) {

        return Observable.fromCallable(new Callable<PhotoModel>() {
            @Override
            public PhotoModel call() {
                PhotoModel nPhoto = new PhotoModel();
                Bitmap nBitmap = null;

                try {
                    nBitmap = mTools.handleSamplingAndRotationBitmap(mPhotoActivity, mListener.onPhotoPresenter_GetBasePhotoUri());
                    File outputFile = mTools.createImageFile(mPhotoActivity);
                    FileOutputStream fos = new FileOutputStream(outputFile);
                    nBitmap.compress(Bitmap.CompressFormat.JPEG, 90, fos);
                    nPhoto.mFilePath = Uri.fromFile(outputFile);
                } catch (Exception e) {
                }

                nPhoto.mPhotoFrom = Constants.TAKE_PHOTO_CAMERA;
                if (mTools!=null && mTools.mBitmapTransformers!=null) {
                    Log.d("asdadasa","e null");
                     mTools.mBitmapTransformers.recycle(); } else {
                    Log.d("asdadasa","nu e null");
                }
                     if (nBitmap!=null) {
                         Log.d("asdadasa","e null");
                     nBitmap.recycle(); } else {
                         Log.d("asdadasa","nu e null");
                     }

                return nPhoto;
            }
        });
    }

   /* private Observable<PhotoModel> mPreparePhotoCameraCore(final Intent nReturnedIntent) {

        return Observable.fromCallable(new Callable<PhotoModel>() {
            @Override
            public PhotoModel call() {
                PhotoModel nPhoto = new PhotoModel();
                Bitmap nBitmap = null;

                try {

                    int targetW = 1080;
                    int targetH = 600;
                    // Get the dimensions of the bitmap
                    BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                    bmOptions.inJustDecodeBounds = true;
                    BitmapFactory.decodeFile( mListener.onPhotoPresenter_GetBasePhotoUri().getPath(), bmOptions);
                    int photoW = bmOptions.outWidth;
                    int photoH = bmOptions.outHeight;
                    // Determine how much to scale down the image
                    int scaleFactor = Math.min(photoW/targetW, photoH/targetH);
                    // Decode the image file into a Bitmap sized to fill the View
                    bmOptions.inJustDecodeBounds = false;
                    bmOptions.inSampleSize = scaleFactor;
                    bmOptions.inPurgeable = true;
                    Bitmap bitmap = BitmapFactory.decodeFile(mListener.onPhotoPresenter_GetBasePhotoUri().getPath(), bmOptions);


                   // nBitmap = mTools.handleSamplingAndRotationBitmap(mPhotoActivity, mListener.onPhotoPresenter_GetBasePhotoUri());
                    File outputFile = mTools.createImageFile(mPhotoActivity);
                    FileOutputStream fos = new FileOutputStream(outputFile);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fos);
                    nPhoto.mFilePath = Uri.fromFile(outputFile);
                } catch (Exception e) {
                }

                nPhoto.mPhotoFrom = Constants.TAKE_PHOTO_CAMERA;

           //     mTools.mBitmapTransformers.recycle();
             //   nBitmap.recycle();

                return nPhoto;
            }
        });
    }
*/
    /**
     * Prepare foto that was cropped and return with neccesary data as PhotoModel
     *
     * @param nReturnedIntent - returned intent when user came back from cropping
     */
    public void mPreparePhotoCropped(Intent nReturnedIntent) {

        mCompositeDisposable.add(mPreparePhotoCroppedCore(nReturnedIntent).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<PhotoModel>() {
            @Override
            public void accept(PhotoModel nPhotoModel) {
                if (mPhotoActivity != null && mListener != null) {
                    mListener.onPhotoPresenter_PhotoReady(nPhotoModel, Constants.TAKE_PHOTO_CROPPED,0);
                }
            }
        }));
    }

    private Observable<PhotoModel> mPreparePhotoCroppedCore(final Intent nReturnedIntent) {

        return Observable.fromCallable(new Callable<PhotoModel>() {
            @Override
            public PhotoModel call() {
                PhotoModel nPhoto = new PhotoModel();
                nPhoto.mCroppedFilePath = Uri.parse(nReturnedIntent.getStringExtra(Constants.TAKE_PHOTO_FILE_CROP));
                nPhoto.mFilePathString = Uri.parse(nReturnedIntent.getStringExtra(Constants.TAKE_PHOTO_FILE_CROP)).getPath();
                nPhoto.mFilePath =  Uri.parse(nReturnedIntent.getStringExtra(Constants.TAKE_PHOTO_FILE));
                nPhoto.mPosition =  Integer.parseInt(nReturnedIntent.getStringExtra(Constants.TAKE_PHOTO_POSITION));
                nPhoto.mPhotoFrom = Constants.TAKE_PHOTO_CROPPED;
                return nPhoto;
            }
        });
    }

    private Observable<PhotoModel> mPreparePhotoLoadingPlace() {
        return Observable.fromCallable(new Callable<PhotoModel>() {
            @Override
            public PhotoModel call() {
                PhotoModel nPhoto = new PhotoModel();
                nPhoto.mPhotoFrom = Constants.TAKE_PHOTO_LOADING;
                return nPhoto;
            }
        });
    }




    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
            inSampleSize *=2;
            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public static Point getScreenDimensions(Context context){
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Point point = new Point();
        Display display = windowManager.getDefaultDisplay();
        display.getSize(point);
        return point;
    }

    public static Bitmap decodeSampledFile(File file, Context context){

        final BitmapFactory.Options options = new BitmapFactory.Options();
        try {
            int reqWidth = getScreenDimensions(context).x;
            int reqHeight = getScreenDimensions(context).y;
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(file.getAbsolutePath(), options);
            options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
            options.inJustDecodeBounds = false;
            return BitmapFactory.decodeFile(file.getAbsolutePath(), options);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}
