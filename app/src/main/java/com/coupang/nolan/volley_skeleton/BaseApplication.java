package com.coupang.nolan.volley_skeleton;

import android.app.Application;
import android.content.SharedPreferences;

import com.coupang.nolan.volley_skeleton.utils.Constants;
import com.coupang.nolan.volley_skeleton.utils.VolleyHelper;

public class BaseApplication extends Application {
    private SharedPreferences mSharedPreferences;

    @Override
    public void onCreate() {
        super.onCreate();
        mSharedPreferences = getSharedPreferences(Constants.SHARED_PREF_NAME, MODE_PRIVATE);

        VolleyHelper.init(this);
    }

    public SharedPreferences getLocalPreference() {
        return mSharedPreferences;
    }
}
