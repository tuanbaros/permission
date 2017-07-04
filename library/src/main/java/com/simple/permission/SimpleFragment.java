package com.simple.permission;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

/**
 * Created by tuannt on 7/2/17.
 * Project: sample
 * Package: com.simple.permission
 */

public abstract class SimpleFragment extends Fragment implements Response {

    protected Permission mPermission;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPermission = new Permission(getActivity());
        mPermission.requestPermission(this, this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
            @NonNull int[] grantResults) {
        mPermission.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
