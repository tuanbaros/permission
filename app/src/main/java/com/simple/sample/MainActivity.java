package com.simple.sample;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import com.simple.permission.Permission;
import com.simple.permission.Request;
import com.simple.permission.Response;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Permission mPermission;

    @Request(permissions = {
            Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION
    })
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPermission = new Permission(this);
        setContentView(R.layout.activity_main);
        TextView textView = (TextView) findViewById(R.id.text_view);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), SampleActivity.class));
            }
        });

        mPermission.requestPermissions(new Class[] { Bundle.class }, new Response() {
            @Override
            public void onPermissionGranted() {
                // TODO: 03/07/2017  
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                // TODO: 03/07/2017
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
            @NonNull int[] grantResults) {
        mPermission.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
