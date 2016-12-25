package com.brachometer;

import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by odelya_krief on 09-Jun-16.
 *
 * used to check the permissions and ask for them if needed
 */
class PermissionAsker {
    /**
     *
     * @param activity the activity to handle the callback
     * @param allPermissions for example -
     *                       List<String> allPermissions = Arrays.asList(
     *                          Manifest.permission.WRITE_CONTACTS,
     *                          Manifest.permission.READ_CONTACTS
     *                       );
     * @return if authorised, return true. else - return false
     */
    static boolean isAuthorised(AppCompatActivity activity, List<String> allPermissions)
    {
        ArrayList<String> neededPermissions = new ArrayList<>();
        for (String permission : allPermissions) {
            int res = ContextCompat.checkSelfPermission(activity, permission);
            if(res != PackageManager.PERMISSION_GRANTED) {
                neededPermissions.add(permission);
            }
        }

        return neededPermissions.size() == 0;
    }

    /**
     *
     * @param activity the activity to check. needs to implement "onRequestPermissionsResult"
     * @param allPermissions for example -
     *                       List<String> allPermissions = Arrays.asList(
     *                          Manifest.permission.WRITE_CONTACTS,
     *                          Manifest.permission.READ_CONTACTS
     *                       );
     */
    static void askPermissions(AppCompatActivity activity, List<String> allPermissions, int permissionRequestId) {
        ArrayList<String> neededPermissions = new ArrayList<>();
        for (String permission : allPermissions) {
            int res = ContextCompat.checkSelfPermission(activity, permission);
            if(res != PackageManager.PERMISSION_GRANTED) {
                neededPermissions.add(permission);
            }
        }
        ActivityCompat.requestPermissions(activity,
                neededPermissions.toArray(new String[neededPermissions.size()]),
                permissionRequestId);
    }
}
