package com.themejunky.personalstylerlib.bases;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.themejunky.personalstylerlib.bases.model.PhotoModel;
import com.themejunky.personalstylerlib.customdialogs.photo.TakePhoto;
import com.themejunky.personalstylerlib.utils.Constants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PhotoActivity extends AppCompatActivity implements TakePhoto._Interface{

    public interface _Interface {
        void onPhotosRefreshAvailable(String nType);
    }

    protected Uri mFilePath;
    protected FirebaseStorage mFireBaseStorage;
    protected  StorageReference storageReference;
    protected List<PhotoModel> mPhotos = new ArrayList<>();

    protected _Interface mPhotoActivityInterface;
    private PhotoModel nPhoto;
    private int ACTION_CAMERA = 1;
    private int ACTION_GALLERY = 2;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFireBaseStorage = FirebaseStorage.getInstance();
        storageReference = mFireBaseStorage.getReference();
    }

    protected void mPickCamera() {
        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePicture, ACTION_CAMERA);//zero can be replaced with any action code
    }

    protected void mPickGallery() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto , ACTION_GALLERY);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        if (requestCode==ACTION_GALLERY && resultCode == RESULT_OK) {
            if(resultCode == RESULT_OK){
                 nPhoto = new PhotoModel();
                 nPhoto.mFilePath = imageReturnedIntent.getData();
                 nPhoto.mPhotoFrom = Constants.TAKE_PHOTO_GALLERY;
                 mPhotos.add(nPhoto);

                mPhotoActivityInterface.onPhotosRefreshAvailable(Constants.TAKE_PHOTO_GALLERY);
            }
        } else if (requestCode==ACTION_CAMERA  && resultCode == RESULT_OK) {
                nPhoto = new PhotoModel();
                nPhoto.mFilePath = imageReturnedIntent.getData();
                mPhotos.add(nPhoto);
                nPhoto.mPhotoFrom = Constants.TAKE_PHOTO_CAMERA;
                mPhotoActivityInterface.onPhotosRefreshAvailable(Constants.TAKE_PHOTO_CAMERA);


//                try {
//                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), mFilePath);
//                    uploadImage();
//                }
//                catch (IOException e)
//                {
//                    e.printStackTrace();
//                }
                // imageview.setImageURI(selectedImage);
        }
    }

    private void uploadImage() {

        if(mFilePath != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference ref = storageReference.child("images/ceva");

            Log.d("asdadadas",""+ref);

            ref.putFile(mFilePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(PhotoActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();

                            Log.d("sdaddas","1: "+taskSnapshot.getUploadSessionUri());
                            Log.d("sdaddas","2: "+taskSnapshot.getBytesTransferred());
                            Log.d("sdaddas","3: "+taskSnapshot.getMetadata());
                            Log.d("sdaddas","4: "+taskSnapshot.getTotalByteCount());
                            Log.d("sdaddas","4: "+taskSnapshot.getDownloadUrl());
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(PhotoActivity.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });
        }
    }

    @Override
    public void onTakePhoto_Camera() {
        mPickCamera();
    }

    @Override
    public void onTakePhoto_Gallery() {
        mPickGallery();
    }
}
