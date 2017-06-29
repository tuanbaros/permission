package com.simple.permission;

import java.util.List;

/**
 * Created by FRAMGIA\nguyen.thanh.tuan on 29/06/2017.
 */

public interface Response {
    void onPermissionGranted();
    void onPermissionDenied(List<String> deniedPermissions);
}
