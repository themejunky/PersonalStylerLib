package com.themejunky.personalstylerlib.bases;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.themejunky.personalstylerlib.R;
import com.themejunky.personalstylerlib.bases.model.PhotoModel;
import com.themejunky.personalstylerlib.bases.tools.Tools;
import com.themejunky.personalstylerlib.customdialogs.photo.TakePhoto;
import com.themejunky.personalstylerlib.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class PhotoActivity extends AppCompatActivity implements TakePhoto._Interface, View.OnClickListener {
    public Tools mTools;
    @Override
    public void onClick(View nView) {
        mPhotos.remove(Integer.parseInt(nView.getTag().toString()));
        internal();
    }

    public interface _Interface {
        void onPhotosRefreshAvailable(String nType);
    }

    protected Uri mFilePath;
    protected FirebaseStorage mFireBaseStorage;
    protected  StorageReference storageReference;
    protected List<PhotoModel> mPhotos = new ArrayList<>();
    protected List<PhotoModel> mViews = new ArrayList<>();
    protected _Interface mPhotoActivityInterface;
    private PhotoModel nPhoto;
    private int ACTION_CAMERA = 1;
    private int ACTION_GALLERY = 2;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTools = Tools.getInstance(this);
      ///  mFireBaseStorage = FirebaseStorage.getInstance();
       // storageReference = mFireBaseStorage.getReference();
    }

    protected void mActivatePhotoInternalAPI(_Interface nListener, int nNoPic, ViewGroup nViewGroup) {
        mPhotoActivityInterface = nListener;
        mTools.getMainContainerAfterInflate(nViewGroup,R.layout.custom_pictures);


        for(int i=0;i< ((LinearLayout) nViewGroup.findViewById(R.id.nPicMainContainer)).getChildCount();i++) {

            if (i<nNoPic) {
                mViews.add(new PhotoModel((RelativeLayout) ((LinearLayout) nViewGroup.findViewById(R.id.nPicMainContainer)).getChildAt(i)));
            }

        }

        activation();
    }

    protected void activation() {

        for (PhotoModel item: mViews) {
            item.mImageContainer.setVisibility(View.GONE);
        }

        if (mPhotos!=null && mPhotos.size()>0) {
            for(int i=0;i<mPhotos.size();i++) {
                mViews.get(i).mImageContainer.setVisibility(View.VISIBLE);
            }
        }

        for (PhotoModel item: mViews) {
            item.mImageContainer.setOnClickListener(this);
        }
    }

    protected void mPickCamera() {
        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePicture, ACTION_CAMERA);//zero can be replaced with any action code
    }

    protected void mPickGallery() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto , ACTION_GALLERY);
    }

    @Override
    public void onTakePhoto_Camera() {
        if ( mPhotos.size()<=mViews.size()-1) {
        mPickCamera(); }
    }

    @Override
    public void onTakePhoto_Gallery() {
        if ( mPhotos.size()<=mViews.size()-1) {
        mPickGallery(); }
    }



    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        if (requestCode==ACTION_GALLERY && resultCode == RESULT_OK) {
            if(resultCode == RESULT_OK){
                 nPhoto = new PhotoModel();
                 nPhoto.mFilePath = imageReturnedIntent.getData();
                 nPhoto.mPhotoFrom = Constants.TAKE_PHOTO_GALLERY;
                 mPhotos.add(nPhoto);
                internal();
                mPhotoActivityInterface.onPhotosRefreshAvailable(Constants.TAKE_PHOTO_GALLERY);
            }
        } else if (requestCode==ACTION_CAMERA  && resultCode == RESULT_OK) {
                nPhoto = new PhotoModel();
                nPhoto.mBitmap = ((Bitmap) imageReturnedIntent.getExtras().get("data"));
                nPhoto.mPhotoFrom = Constants.TAKE_PHOTO_CAMERA;
                mPhotos.add(nPhoto);

            internal();

                mPhotoActivityInterface.onPhotosRefreshAvailable(Constants.TAKE_PHOTO_CAMERA);
        }
    }

    private void internal() {
        int mCount =0;

        for (PhotoModel item: mViews) {
            ((ImageView) item.mImageContainer.findViewWithTag("image")).setImageResource(R.drawable.ic_service_placeholder);
        }

        for (PhotoModel item : mPhotos) {
            showImage((ImageView) mViews.get(mCount).mImageContainer.findViewWithTag("image"),item);
            mCount++;
        }

        activation();
    }

    private void showImage(ImageView nImageView, PhotoModel nPhotoModel) {
        switch (nPhotoModel.mPhotoFrom) {
            case Constants.TAKE_PHOTO_CAMERA: nImageView.setImageBitmap(nPhotoModel.mBitmap); break;
            case Constants.TAKE_PHOTO_GALLERY : nImageView.setImageURI(nPhotoModel.mFilePath); break;
        }
    }

//
//    public File ceva (Bitmap mBitmap) {
//        File f3=new File(Environment.getExternalStorageDirectory()+"/inpaint/");
//        if(!f3.exists())
//            f3.mkdirs();
//        OutputStream outStream = null;
//        File file = new File(Environment.getExternalStorageDirectory() + "/inpaint/"+System.currentTimeMillis()+".png");
//        try {
//            outStream = new FileOutputStream(file);
//            mBitmap.compress(Bitmap.CompressFormat.PNG, 85, outStream);
//            outStream.close();
//            Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return file;
//    }
//
//    public Uri getImageUri(Context inContext, Bitmap inImage) {
//        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
//        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
//        return Uri.parse(path);
//    }
//
//    private void uploadImage() {
//
//        if(mFilePath != null)
//        {
//            final ProgressDialog progressDialog = new ProgressDialog(this);
//            progressDialog.setTitle("Uploading...");
//            progressDialog.show();
//
//            StorageReference ref = storageReference.child("images/ceva");
//
//            Log.d("asdadadas",""+ref);
//
//            ref.putFile(mFilePath)
//                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                            progressDialog.dismiss();
//                            Toast.makeText(PhotoActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
//
//                            Log.d("sdaddas","1: "+taskSnapshot.getUploadSessionUri());
//                            Log.d("sdaddas","2: "+taskSnapshot.getBytesTransferred());
//                            Log.d("sdaddas","3: "+taskSnapshot.getMetadata());
//                            Log.d("sdaddas","4: "+taskSnapshot.getTotalByteCount());
//                            Log.d("sdaddas","4: "+taskSnapshot.getDownloadUrl());
//                        }
//                    })
//                    .addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            progressDialog.dismiss();
//                            Toast.makeText(PhotoActivity.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                    })
//                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
//                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
//                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
//                        }
//                    });
//        }
//    }


}
