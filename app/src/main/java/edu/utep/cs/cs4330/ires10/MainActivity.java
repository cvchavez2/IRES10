package edu.utep.cs.cs4330.ires10;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements LocationListener {

    static double lat, lon;
    protected LocationManager locationManager;
    Toolbar myToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        myToolbar.setTitle("Help");

        loadFragment(new AlertFragment());

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION}, 255);
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 2, this);

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_shop:
                    myToolbar.setTitle("Help");
                    fragment = new AlertFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_gifts:
                    myToolbar.setTitle("List");
                    fragment = new ReportListFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_cart:
                    myToolbar.setTitle("Map");
                    fragment = new MapFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_profile:
                    myToolbar.setTitle("Add");
                    Intent intent = new Intent(MainActivity.this, AddReport.class);
                    startActivity(intent);
                    return true;
            }
            return false;
        }
    };

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onLocationChanged(Location location) {
        lat = location.getLatitude();
        lon = location.getLongitude();
        try {
            // Geocoder translates coordinates into a street name
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
//            List<Address> addressList = geocoder.getFromLocation(lat, lon, 1);
//            addressTextV.setText(addressTextV.getText() + "\n"+addressList.get(0).getAddressLine(0));
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        //Create options menu
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_options_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        //respond to menu item selection
        switch (item.getItemId()) {
            case R.id.viewReports:
                startActivity(new Intent(this, AddReport.class));
                break;
            case R.id.about:
//                startActivity(new Intent(this, About.class));
                break;
            case R.id.help:
//                startActivity(new Intent(this, Help.class));
                break;
            case R.id.exit:
                System.exit(1);
        }
        return super.onOptionsItemSelected(item);
    }
}
