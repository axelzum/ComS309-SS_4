package com.ss4.opencampus.dataViews.uspots;

import android.os.Bundle;
import android.content.Intent;

import com.ss4.opencampus.R;


public class USpotDetailActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_activity_single_uspot);
        ButterKnife.bind(this);

        uspotList = Parcels.unwrap(getIntent().getParcelableExtra(Constants.EXTRA_KEY_RESTAURANTS));
        int startingPosition =getIntent().getIntExtra(Constants.EXTRA_KEY_POSITION, 0);

        adapterViewPager = new USpotPagerAdapter(getSupportFragmentManager(), uspotList);
        mViewPager.setAdapter(adapterViewPager);
        mViewPager.setCurrentItem(startingPosition);
    }
}
