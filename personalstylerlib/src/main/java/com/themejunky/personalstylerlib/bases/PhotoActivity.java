package com.themejunky.personalstylerlib.bases;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class PhotoActivity extends CustomActivity {

    protected Uri mFilePath;
    protected FirebaseStorage mFireBaseStorage;
    protected  StorageReference storageReference;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFireBaseStorage = FirebaseStorage.getInstance();
        storageReference = mFireBaseStorage.getReference();
    }

    protected void pickMe() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto , 1);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch(requestCode) {
            case 0:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = imageReturnedIntent.getData();
                    //imageview.setImageURI(selectedImage);
                }

                break;
            case 1:
                if(resultCode == RESULT_OK){
                    mFilePath = imageReturnedIntent.getData();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), mFilePath);
                        uploadImage();
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }


                    // imageview.setImageURI(selectedImage);
                }
                break;
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
}
