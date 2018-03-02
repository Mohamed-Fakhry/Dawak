package com.example.dawak;

import android.app.Application;

import com.example.dawak.data.DataManager;
import com.example.dawak.di.component.ApplicationComponent;
import com.example.dawak.di.component.DaggerApplicationComponent;
import com.example.dawak.di.module.ApplicationModule;
import com.google.firebase.FirebaseApp;

import javax.inject.Inject;


/**
 * Created by Mohamed Fakhry on 11/02/2018.
 */

public class App extends Application {

    @Inject
    DataManager mDataManager;

    private ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseApp.initializeApp(this);
        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this)).build();

        mApplicationComponent.inject(this);
    }

    public ApplicationComponent getComponent() {
        return mApplicationComponent;
    }
}
