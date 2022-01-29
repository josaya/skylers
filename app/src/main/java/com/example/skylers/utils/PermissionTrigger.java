package com.example.skylers.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import javax.inject.Inject;

import dagger.hilt.android.qualifiers.ApplicationContext;


public class PermissionTrigger {

        protected Activity activity;
        protected Context context;

        @Inject
        public PermissionTrigger(Activity activity, @ApplicationContext Context context) {
            this.activity = activity;
            this.context = context;
        }

        public boolean checkPermission(String strPermission) {
            int result = ContextCompat.checkSelfPermission(context, strPermission);
            if (result == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                return false;
            }
        }

        public void requestPermission(String strPermission, int permissionCode) {
            ActivityCompat.requestPermissions(activity, new String[]{strPermission}, permissionCode);
        }

}
