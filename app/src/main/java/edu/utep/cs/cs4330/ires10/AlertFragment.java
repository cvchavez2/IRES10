package edu.utep.cs.cs4330.ires10;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class AlertFragment extends Fragment implements LocationListener {

    private double lat, lon;
    private TextView addressTextV;
    protected LocationManager locationManager;
    private ImageButton ib;

    public AlertFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_alert, container, false);
        ib = view.findViewById(R.id.panic_button);

        // Sends alert with location and timestamp
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ALERT = "alert";
                new MyAsyncTask(getActivity()).execute(ALERT, lat, lon, System.currentTimeMillis());
            }
        });
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        addressTextV = view.findViewById(R.id.addressTextV);

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION}, 255);
            return null;
        }

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 2, this);

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onLocationChanged(Location location) {
        lat = location.getLatitude();
        lon = location.getLongitude();
        try {
            // Geocoder translates coordinates into a street name
            Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
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
}
