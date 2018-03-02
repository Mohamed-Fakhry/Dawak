package com.example.dawak.ui;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationAdapter;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationViewPager;
import com.example.dawak.R;
import com.example.dawak.ui.base.BaseActivity;
import com.example.dawak.ui.cart.CartFragment;
import com.example.dawak.ui.map.MapFragment;
import com.example.dawak.ui.signup.SignUpActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity
        implements AHBottomNavigation.OnTabSelectedListener {

    @BindView(R.id.viewPager)
    AHBottomNavigationViewPager viewPager;
    @BindView(R.id.bottomNavigation)
    AHBottomNavigation bottomNavigation;

    @Inject
    ViewPagerAdapter pagerAdapter;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getActivityComponent().inject(this);
        setUnBinder(ButterKnife.bind(this));
        setUp();
    }

    @Override
    protected void setUp() {
        setupViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        pagerAdapter.addFragment(MapFragment.newInstance("first"));
        pagerAdapter.addFragment(CartFragment.newInstance("Second"));
        viewPager.setAdapter(pagerAdapter);

        AHBottomNavigationAdapter navigationAdapter = new AHBottomNavigationAdapter(this, R.menu.bottom_navigation_menu);
        bottomNavigation.setTranslucentNavigationEnabled(true);
        navigationAdapter.setupWithBottomNavigation(bottomNavigation);
        bottomNavigation.setAccentColor(ContextCompat.getColor(this, R.color.colorPrimary));
        bottomNavigation.setOnTabSelectedListener(this);
    }

    @Override
    public boolean onTabSelected(int position, boolean wasSelected) {
        viewPager.setCurrentItem(position, true);
        return true;
    }
}
