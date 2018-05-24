package com.themejunky.personalstylerlib.bases.activities.cropping;

public class CroppingPhotoContract {
    public interface BaseView {
        void fetchViews();
        void initViews();
        void start();
        void unexpectedError();
    }
}
