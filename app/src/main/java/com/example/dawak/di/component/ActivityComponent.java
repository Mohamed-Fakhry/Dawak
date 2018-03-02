package com.example.dawak.di.component;


import com.example.dawak.di.PerActivity;
import com.example.dawak.di.module.ActivityModule;
import com.example.dawak.ui.MainActivity;
import com.example.dawak.ui.cart.CartFragment;
import com.example.dawak.ui.login.LoginActivity;
import com.example.dawak.ui.map.MapFragment;
import com.example.dawak.ui.pharmacy.details.PharmacyDetailsActivity;
import com.example.dawak.ui.signup.SignUpActivity;

import dagger.Component;

/**
 * Created by janisharali on 27/01/17.
 */

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(MainActivity activity);

    void inject(LoginActivity activity);

    void inject(SignUpActivity activity);

    void inject(CartFragment fragment);

    void inject(PharmacyDetailsActivity activity);

    void inject(MapFragment fragment);
}
