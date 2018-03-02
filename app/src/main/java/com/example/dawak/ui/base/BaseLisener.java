package com.example.dawak.ui.base;

/**
 * Created by Mohamed Fakhry on 23/02/2018.
 */

public interface BaseLisener<T, E> {

    void onSuccess(T data);

    void onFail(E error);
}
