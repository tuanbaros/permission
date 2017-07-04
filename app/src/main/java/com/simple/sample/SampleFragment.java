package com.simple.sample;

import android.Manifest;
import com.simple.permission.Request;
import com.simple.permission.SimpleFragment;
import java.util.List;

/**
 * Created by FRAMGIA\nguyen.thanh.tuan on 03/07/2017.
 */
@Request(permissions = {
        Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION
})
public class SampleFragment extends SimpleFragment {

    @Override
    public void onPermissionGranted() {
        // TODO: 03/07/2017  
    }

    @Override
    public void onPermissionDenied(List<String> deniedPermissions) {
        // TODO: 03/07/2017
    }
}
