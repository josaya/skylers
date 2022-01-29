package com.example.skylers.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Base64;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.UnsupportedEncodingException;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class AppUtils {

    public static final String PREF_NAME = "skylers";

    public static  String baseUrl = "https://osalist.net/Api/v1/";

    static boolean isDebug = true;

    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;
    public static final int MY_PERMISSIONS_CAMERA = 124;
    public static ProgressDialog ProgressDialog;

    public static SweetAlertDialog sweetAlertDialog, sweetErrorAlertDialog;


    public static void ToastMessage(String msg, Context ctx) {

        Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show();
    }

    public static final void Log(String tag, String message) {
        if (isDebug) {
            android.util.Log.i(tag, "-"+message + "");
        }
    }

    public static void askForPermission(String strPermission, int perCode, final Activity _a) {
        ActivityCompat.requestPermissions(_a, new String[]{strPermission}, perCode);
    }

    public static boolean confirmIfPermissionGranted(String strPermission, Context context) {
        int result = ContextCompat.checkSelfPermission(context, strPermission);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    public static void loadProgressDialog(Context context, String title,
                                                String msg, boolean isCancelable) {
        try {
            if (ProgressDialog == null) {
                ProgressDialog = ProgressDialog.show(context, title, msg);
                ProgressDialog.setCancelable(isCancelable);
            }

            if (!ProgressDialog.isShowing()) {
                ProgressDialog.show();
            }

        } catch (IllegalArgumentException ie) {
            ie.printStackTrace();
        } catch (RuntimeException re) {
            re.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void dismissProgressDialog() {
        try {
            if (ProgressDialog != null) {
                if (ProgressDialog.isShowing()) {
                    ProgressDialog.dismiss();
                    ProgressDialog = null;
                }
            }
        } catch (IllegalArgumentException ie) {
            ie.printStackTrace();

        } catch (RuntimeException re) {
            re.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    // Base64 Encrypting
    public static String Base64Encrypt(String requestString) {
        byte[] dataByte = null;
        String base64EncryptedString = "";
        try {
            dataByte = requestString.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try {
            base64EncryptedString = Base64.encodeToString(dataByte, Base64.DEFAULT);
        } catch (Exception e) {
            base64EncryptedString = requestString;
        }
        return base64EncryptedString.replace("\n", "");
    }

    // Base64 Decrypting
    public static String Base64Decrypt(String responseString) {
        byte[] dataByte = Base64.decode(responseString, Base64.DEFAULT);
        String clearText = "";
        try{
            clearText = new String(dataByte, "UTF-8");
        }catch (Exception e){
            clearText = "";
        }
        return clearText;
    }

    public static void SuccessDialog(Activity activity, String message) {
        if (sweetAlertDialog != null) {
            if (sweetAlertDialog.isShowing()) {
                return;
            }
        }

        sweetAlertDialog = new SweetAlertDialog(activity, SweetAlertDialog.SUCCESS_TYPE);
        sweetAlertDialog.setTitleText("Success");
        sweetAlertDialog.setContentText(message);
        sweetAlertDialog.show();
    }

    public static void ErrorDialog(Activity activity, String message) {
        if (sweetAlertDialog != null) {
            if (sweetAlertDialog.isShowing()) {
                return;
            }
        }

        sweetErrorAlertDialog = new SweetAlertDialog(activity, SweetAlertDialog.ERROR_TYPE);
        sweetErrorAlertDialog.setTitleText("Error!");
        sweetErrorAlertDialog.setContentText(message);
        sweetErrorAlertDialog .show();
    }
}
