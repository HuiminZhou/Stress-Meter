package com.example.huimin_zhou.huimin_zhou_stressmeter;

import android.Manifest;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.app.FragmentManager;
import android.provider.Settings;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Fragment mImageFragment = null;
    private Fragment mResultFragment = null;
    public static int NOTIFICATION_ID = 1;
    public static NotificationManager manager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get write and read permission
        getPermission();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        initialView();
        navigationView.setNavigationItemSelectedListener(this);


        // call the schedule activity and trigger the notification sound
        setSchedule();
    }

    private void setSchedule() {
        // Notification sound
        long[] pattern = new long[] {1000, 1000};
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.fish_normal017)
                .setVibrate(pattern)
                .setSound(Settings.System.DEFAULT_ALARM_ALERT_URI);
        Notification notification = mBuilder.build();
        manager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        notification.flags |= Notification.FLAG_INSISTENT;
        notification.flags |= Notification.FLAG_SHOW_LIGHTS;
        manager.notify(NOTIFICATION_ID, notification);

        Scheduler.setSchedule(this);
    }

    // show the view before any navigation option is selected, i.e., when app starts
    private void initialView() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        try {
            mImageFragment = ImageFragment.class.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        ft.replace(R.id.content_fragment, mImageFragment);
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        manager.cancel(NOTIFICATION_ID);
        int id = item.getItemId();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        try {
            if (id == R.id.navi_image) { // show the first fragment
                ft.replace(R.id.content_fragment, mImageFragment);
            } else if (id == R.id.navi_result) { // show the second fragment
                if (mResultFragment == null) {
                    mResultFragment = ResultFragment.class.newInstance();
                }
                ft.replace(R.id.content_fragment, mResultFragment);
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }


        ft.addToBackStack(null);
        ft.commit();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    // get permission for writing and reading external storage
    private void getPermission() {
        if (Build.VERSION.SDK_INT < 23) {
            return;
        }
        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED ||
                checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
        }
    }
    @Override
    public void onPause() {
        super.onPause();
        manager.cancel(NOTIFICATION_ID); // stop the notification sound
    }

}
