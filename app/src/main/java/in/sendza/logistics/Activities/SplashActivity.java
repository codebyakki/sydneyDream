package in.sendza.logistics.Activities;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import in.sendza.logistics.R;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import pub.devrel.easypermissions.PermissionRequest;

public class SplashActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {

    FirebaseAnalytics firebaseAnalytics;
    FirebaseAuth firebaseAuth;
    private final int RC_CAMERA_LOCATION_AND_CONTACTS=101;
    private long SPLASH_TIME_OUT=3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        firebaseAnalytics=FirebaseAnalytics.getInstance(this);
        firebaseAuth=FirebaseAuth.getInstance();


        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                methodRequiresTwoPermission();

            }
        }, SPLASH_TIME_OUT);








    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
    @Override
    public void onPermissionsGranted(int requestCode, List<String> list) {
        // Some permissions have been granted
        // ...
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> list) {
        // Some permissions have been denied
        // ...
    }

    private void navigateUser()
    {
        if (firebaseAuth.getCurrentUser()!=null)
        {
            //user is there navigate to Dashboard
            Intent intent=new Intent(SplashActivity.this,Dashboard.class);
            startActivity(intent);
            finish();

        }
        else {
            //user is not there Navigate to login
            Intent intent=new Intent(SplashActivity.this,Login.class);
            startActivity(intent);
            finish();
        }
    }

    @AfterPermissionGranted(RC_CAMERA_LOCATION_AND_CONTACTS)
    private void methodRequiresTwoPermission() {
        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.READ_CONTACTS};
        if (EasyPermissions.hasPermissions(this, perms)) {
            // Already have permission, do the thing
            // ...
            navigateUser();
            Log.d(SplashActivity.class.getSimpleName(),"Already have permissions then do magic here");
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(
                    new PermissionRequest.Builder(this, RC_CAMERA_LOCATION_AND_CONTACTS, perms)
                            .setRationale(R.string.camera_and_location_rationale)
                            .setPositiveButtonText(R.string.rationale_ask_ok)
                            .setNegativeButtonText(R.string.rationale_ask_cancel)
                            .build());
        }
    }


}
