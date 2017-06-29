# Permission

Permission is an Android library that simplifies the process of requesting permissions at runtime (only applicable for Android M or higher).

Require
-----

* API 14 or higher

Usage
-----

### Dependency

Include the library in your ``app/build.gradle``

```groovy
dependencies{
    compile project (':permission')
}
```

### Request permission for your method in 3 steps

1. Init a Permission instance in your activity

```java
	private Permission mPermission;
	    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mPermission = new Permission(this);
		setContentView(R.layout.activity_main);
	}
```

2. Override ``onRequestPermissionsResult`` in your activity

```java
	@Override
    	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
        	    @NonNull int[] grantResults) {
        	mPermission.onRequestPermissionsResult(requestCode, permissions, grantResults);
    	}
```

3. Add permission for method need request permission
```java
	@Request(permissions = {
		Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION
	    })
	private void yourMethod(String s) {
		mPermission.requestPermissions(new Class[] { String.class },
                new Response() {
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
``` 

### Request permission when open activity / fragment

1. For Activity

* Extend ``com.simple.permission.SimpleActivity``

* Register permissions

* Sample

```java

	@Request(permissions = {
		Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION
	})
	public class SampleActivity extends SimpleActivity {

	    @Override
	    protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);
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

```

2. For Fragment

* Extend ``com.simple.permission.SimpleFragment``

* Register permissions

* Sample

```java
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

```

### Note

* If your method have no params, you parse ``new Class[] {}``
	 
* If your method have params, you parse ``new Class[]{ TypeOfParam1.class, TypeOfParam2.class, ... , TypeOfParamN.class }``

* If your activity extends ``com.simple.permission.SimpleActivity`` or your fragment extends ``com.simple.permission.SimpleFragment``, you mustn't implement step 1 & 2 for method

* Of course, you need add permissions in your ``AndroidManifest.xml``





