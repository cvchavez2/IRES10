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
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements LocationListener {

    private double lat, lon;
    private TextView addressTextV;
    protected LocationManager locationManager;
    final private static String CRIME = "crime";
    final private static String SUSPICIOUS_ACTIVITY = "suspicious";
    final private static String ENVIRONMENT = "environment";
    final private static String INFRASTRUCTURE = "infrastructure";
    final private static String MOBILITY = "mobility";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addressTextV = findViewById(R.id.addressTextV);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION}, 255);
            return;
        }

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 2, this);
    }

    public void crimeReport(View view){
        startDoReportActivity(CRIME);
    }

    public void suspiciousReport(View view){

    }

    public void environmentReport(View view){
        startDoReportActivity(ENVIRONMENT);
    }

    public void infrastructureReport(View view){
        startDoReportActivity(INFRASTRUCTURE);
    }

    public void mobilityReport(View view){

    }

    public void startDoReportActivity(String type){
        Intent intent = new Intent(MainActivity.this, DoReport.class);
        intent.putExtra("type", type);
        intent.putExtra("latitude", lat);
        intent.putExtra("longitude", lon);
        startActivity(intent);
    }

    public void sendPanicAlert(View view){
        String type = "alert";
        new MyAsyncTask(this).execute(type, lat, lon, System.currentTimeMillis());
    }

    @Override
    public void onLocationChanged(Location location) {
        lat = location.getLatitude();
        lon = location.getLongitude();
        try {
            // Geocoder translates coordinates into a street name
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addressList = geocoder.getFromLocation(lat, lon, 1);
            addressTextV.setText(addressTextV.getText() + "\n"+addressList.get(0).getAddressLine(0));
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
            case R.id.about:
//                startActivity(new Intent(this, About.class));
                break;
            case R.id.help:
//                startActivity(new Intent(this, Help.class));
                break;
            case R.id.viewReports:
                startActivity(new Intent(this, ViewReports.class));
                break;
            case R.id.viewReportsMap:
                startActivity(new Intent(this, ViewMap.class));
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}
