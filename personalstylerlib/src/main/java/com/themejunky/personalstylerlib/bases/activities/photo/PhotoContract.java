package com.themejunky.personalstylerlib.bases.activities.photo;

public class PhotoContract {
    public interface BaseView {
        void fetchViews();
        void initViews();
        void start();
        void unexpectedError();
    }
}
