package com.simple.sample;

import android.Manifest;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.simple.permission.Request;
import com.simple.permission.SimpleActivity;
import java.util.List;

@Request(permissions = {
        Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION
})
public class SampleActivity extends SimpleActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Button button = (Button) findViewById(R.id.button_show);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (findViewById(R.id.fragment_container) != null) {
                    if (savedInstanceState != null) {
                        return;
                    }
                    SampleFragment firstFragment = new SampleFragment();
                    firstFragment.setArguments(getIntent().getExtras());

                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.fragment_container, firstFragment)
                            .commit();
                }
            }
        });
    }

    @Override
    public void onPermissionGranted() {
        // TODO: 03/07/2017
    }

    @Override
    public void onPermissionDenied(List<String> deniedPermissions) {
        // TODO: 03/07/2017
    }
}
