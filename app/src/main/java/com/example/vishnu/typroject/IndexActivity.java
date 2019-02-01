package com.example.vishnu.typroject;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.github.javiersantos.materialstyleddialogs.enums.Style;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class IndexActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    NavigationView navigationView = null;
    Toolbar toolbar = null;
    FirebaseAuth mAuth;
    TextView name, email;
    CircleImageView profilePic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        mAuth = FirebaseAuth.getInstance();

//        name = findViewById(R.id.usernamenh);
//        email = findViewById(R.id.emailnh);
//        profilePic = findViewById(R.id.profile_image);

//        HomeFragment home = new HomeFragment();
//        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//        fragmentTransaction.replace(R.id.fragment_container, home);
//        fragmentTransaction.commit();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TabLayout tabLayout = findViewById(R.id.tabs);
        ViewPager viewPager = findViewById(R.id.viewpager);

        TabPagerAdapter tabPagerAdapter = new TabPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(tabPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Snackbar.make(view, "Vishnu! This is a snackbar", Snackbar.LENGTH_SHORT)
                        .setAction("OKAY", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Snackbar.make(view, "Done!", Snackbar.LENGTH_SHORT).show();
                            }
                        }).setActionTextColor(Color.YELLOW).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        name = (TextView) navigationView.getHeaderView(0).findViewById(R.id.usernamenh);
        email = (TextView) navigationView.getHeaderView(0).findViewById(R.id.emailnh);
        profilePic = (CircleImageView) navigationView.getHeaderView(0).findViewById(R.id.profile_image);

        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MobileProfileActivity.class));
            }
        });

        loadUserInfo();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            new MaterialStyledDialog.Builder(this)
                    .setTitle("Exit!")
                    .setDescription("Are You Sure?")
                    .setCancelable(false)
                    .setIcon(R.drawable.ams_red_2)
                    .setHeaderDrawable(R.drawable.dialog_background)
                    .setStyle(Style.HEADER_WITH_ICON)
                    .withDialogAnimation(true)
                    .setPositiveText("Yes")
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            finish();
                        }
                    })
                    .setNegativeText("No")
                    .onNegative(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            dialog.cancel();
                        }
                    })
                    .setNeutralText("Cancel")
                    .onNeutral(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            dialog.cancel();
                        }
                    })
                    .show();
            //super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu_profile; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sidenav_right, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the HomeFragment/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

//        if (id == R.id.nav_camera) {
//            // Handle the camera action
//        } else if (id == R.id.nav_gallery) {
//
//        } else if (id == R.id.nav_slideshow) {
//
//        } else if (id == R.id.nav_manage) {
//
//        } else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
//        }

        if (id == R.id.qrCodeScan) {
            startActivity(new Intent(this, ScanQRCode.class));
        } else if (id == R.id.qrCodeGenerate) {
            startActivity(new Intent(this, GenerateQRCode.class));
        } else if (id == R.id.signOutUser) {
            FirebaseAuth.getInstance().signOut();
            finish();
            startActivity(new Intent(this, MainActivity.class));
        } else if (id == R.id.uploadimages) {
//            HomeFragment home = new HomeFragment();
//            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//            fragmentTransaction.replace(R.id.fragment_container, home);
//            fragmentTransaction.commit();
//            Toast.makeText(getApplicationContext(),"You clicked HomeFragment",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, UploadImagesActivity.class));

        } else if (id == R.id.allusers) {
//            HomeFragment home = new HomeFragment();
//            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//            fragmentTransaction.replace(R.id.fragment_container, home);
//            fragmentTransaction.commit();
//            Toast.makeText(getApplicationContext(),"You clicked HomeFragment",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, AllUsers.class));

        } else if (id == R.id.timetable) {
//            HomeFragment home = new HomeFragment();
//            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//            fragmentTransaction.replace(R.id.fragment_container, home);
//            fragmentTransaction.commit();
//            Toast.makeText(getApplicationContext(),"You clicked HomeFragment",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, TimetableActivity.class));

        } else if (id == R.id.showuploadsmenu) {
//            HomeFragment home = new HomeFragment();
//            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//            fragmentTransaction.replace(R.id.fragment_container, home);
//            fragmentTransaction.commit();
//            Toast.makeText(getApplicationContext(),"You clicked HomeFragment",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, UploadedImagesActivity.class));

        }
        else if (id == R.id.attendance) {
//            HomeFragment home = new HomeFragment();
//            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//            fragmentTransaction.replace(R.id.fragment_container, home);
//            fragmentTransaction.commit();
//            Toast.makeText(getApplicationContext(),"You clicked HomeFragment",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, AttendanceMainActivity.class));

        }

        else if (id == R.id.browser) {
            if (isInternetConnection())
                startActivity(new Intent(this, BrowserActivity.class));
            else
                Toast.makeText(getApplicationContext(), "Internet is unavailable at the moment!", Toast.LENGTH_SHORT).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public boolean isInternetConnection() {

        boolean connected;
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            connected = true;
        } else
            connected = false;
        return connected;
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (mAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }
    }

    private void loadUserInfo() {

        final FirebaseUser user = mAuth.getCurrentUser();

        if (user != null) {
            if (user.getPhotoUrl() != null) {
                Picasso.with(this).load(user.getPhotoUrl()).placeholder(R.drawable.man_profile_image).noFade().into(profilePic);
            }
            if (user.getDisplayName() != null) {
                name.setText(user.getDisplayName());
            }

            if (user.getEmail() != null) {
                email.setText(user.getEmail());
            } else if (user.getPhoneNumber() != null) {
                email.setText(user.getPhoneNumber());
            }
        }
    }
}
