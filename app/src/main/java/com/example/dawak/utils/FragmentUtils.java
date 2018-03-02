package com.example.dawak.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by Mohamed Fakhry on 26/02/2018.
 */

public class FragmentUtils {

    private FragmentUtils() {
    }

    public static void replaceFragment(FragmentManager fragmentManager, Fragment fragment, int layout, boolean save) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(layout, fragment);
        if (save) transaction.addToBackStack(null);
        transaction.commit();
    }
}
