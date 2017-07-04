package com.simple.permission;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by FRAMGIA\nguyen.thanh.tuan on 29/06/2017.
 */

public class Permission {
    private static final int REQUEST_PERMISSION = 10;

    private static final int INDEX_THIS_CLASS = 3;

    private Response mResponse;

    private Activity mActivity;

    public Permission(Activity activity) {
        mActivity = activity;
    }

    // get list denied permissions for only method
    private List<String> getRequestPermissions(Class<?> main, String methodName, Class[] c) {
        try {
            Method method = main.getDeclaredMethod(methodName, c);
            Request request = method.getAnnotation(Request.class);
            if (request == null) {
                return null;
            }
            return getDeniedPermissions(request.permissions());
        } catch (NoSuchMethodException | NullPointerException e) {
            e.printStackTrace();
        }
        return null;
    }

    // get list denied permission from request permissions
    private List<String> getDeniedPermissions(String[] perms) {
        List<String> permissions = new ArrayList<>();
        for (String s : perms) {
            if (ContextCompat.checkSelfPermission(mActivity, s)
                == PackageManager.PERMISSION_DENIED) {
                permissions.add(s);
            }
        }
        return permissions;
    }

    // show dialog request permissions
    private void showRequestPermissionDialog(@Nullable Fragment fragment, List<String> permissions) {
        if (permissions == null || permissions.isEmpty()) {
            mResponse.onPermissionGranted();
            return;
        }
        if (fragment == null) {
            ActivityCompat.requestPermissions(mActivity,
                    permissions.toArray(new String[permissions.size()]), REQUEST_PERMISSION);
            return;
        }
        fragment.requestPermissions(permissions.toArray(new String[permissions.size()]),
                REQUEST_PERMISSION);
    }

    // handle onRequestPermissionsResult() of activity/fragment
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != REQUEST_PERMISSION) {
            return;
        }
        if (mResponse == null) {
            return;
        }
        List<String> deniedPermissions = new ArrayList<>();
        if (grantResults.length > 0) {
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    deniedPermissions.add(permissions[i]);
                }
            }
            if (deniedPermissions.isEmpty()) {
                mResponse.onPermissionGranted();
                return;
            }
            mResponse.onPermissionDenied(deniedPermissions);
            return;
        }
        Collections.addAll(deniedPermissions, permissions);
        mResponse.onPermissionDenied(deniedPermissions);
    }

    // request permission for activity/fragment
    void requestPermission(@Nullable Fragment fragment, Response response) {
        mResponse = response;
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.M) {
            mResponse.onPermissionGranted();
            return;
        }
        Request request =
                (fragment == null ? mActivity : fragment).getClass().getAnnotation(Request.class);
        if (request == null) {
            response.onPermissionGranted();
            return;
        }
        String[] permissions = request.permissions();
        List<String> deniedPermissions = getDeniedPermissions(permissions);
        if (fragment == null) {
            showRequestPermissionDialog(null, deniedPermissions);
            return;
        }
        showRequestPermissionDialog(fragment, deniedPermissions);
    }

    // request permission for method
    public void requestPermissions(Class[] params, Response response) {
        mResponse = response;
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.M) {
            mResponse.onPermissionGranted();
            return;
        }
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        try {
            Class main = Class.forName(stackTraceElements[INDEX_THIS_CLASS].getClassName());
            String methodName = stackTraceElements[INDEX_THIS_CLASS].getMethodName();
            List<String> permissions = getRequestPermissions(main, methodName, params);
            showRequestPermissionDialog(null, permissions);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            mResponse.onPermissionGranted();
        }
    }
}
