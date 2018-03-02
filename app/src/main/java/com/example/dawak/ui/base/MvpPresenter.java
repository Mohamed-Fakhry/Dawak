
package com.example.dawak.ui.base;


public interface MvpPresenter<V extends MvpView> {

    void onAttach(V mvpView);

    void onDetach();

//    void handleApiError(ANError error);

    void setUserAsLoggedOut();
}
