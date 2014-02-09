package com.personal.maps.kenyapp;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class KenyaActivity extends FragmentActivity implements ProvinceFragment.OnProvinceSelectedListener,
        GoogleMap.OnMapLongClickListener{
    /**
     * Note that this may be null if the Google Play services APK is not available.
     */
    private GoogleMap mMap;
    public static String province;
    public static String coords;
    public static List<Long> final_coords;
    public static MarkerOptions marker;
    public TextView history;
    public TextView languages;
    public TextView tweets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        setUpMapIfNeeded();

        history = (TextView)findViewById(R.id.history);
        languages = (TextView)findViewById(R.id.languages);
        tweets = (TextView)findViewById(R.id.tweets);
        FragmentManager fm = getFragmentManager();
        Log.d("INSIDE ", "ONCREATE Fragment Manager");
        if (fm.findFragmentById(R.id.container) == null) {
            ProvinceFragment list = new ProvinceFragment();
            fm.beginTransaction().add(R.id.container, list).commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        setUpMapIfNeeded();
    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            mMap.setOnMapLongClickListener(this);

            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        Log.d("INSIDE ", "SETUP MAP");
        mMap.setBuildingsEnabled(true);
        float mCameraZoom = 6.00f;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(CameraPosition.fromLatLngZoom(new LatLng(1.2833, 36.8167), mCameraZoom)));
        mMap.addMarker(new MarkerOptions().position(new LatLng(1.2833, 36.8167)).title("Nairobi Kenya")).showInfoWindow();


    }

    @Override
    public void loadProvinceDetails(ArrayList<String> details) {

        marker = new MarkerOptions().position(new LatLng(1.2833,36.8167)).title("KENYA");

        province = details.get(0);
        coords = details.get(1);
        final_coords = splitPayload(coords);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.setBuildingsEnabled(true);
        float mCameraZoom = 10.00f;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        if(marker != null){

            marker.position(new LatLng(final_coords.get(0), final_coords.get(1))).title(province);
            mMap.addMarker(marker).showInfoWindow();
        }else{
            marker = new MarkerOptions().position(new LatLng(final_coords.get(0), final_coords.get(1))).title(province);
            mMap.addMarker(marker).showInfoWindow();
        }

        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(CameraPosition.fromLatLngZoom(new LatLng(final_coords.get(0), final_coords.get(1)), mCameraZoom)));



    }

    public List<Long> splitPayload(String payload){
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        String[] lngLat = payload.split(" ");
        List<Long> f_cords = new ArrayList<Long>();
        try{
            f_cords.add(numberFormat.parse(lngLat[0]).longValue());
            f_cords.add(numberFormat.parse(lngLat[1]).longValue());

        }catch (Exception e){
            e.getMessage();
        }
        return f_cords;

    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        Toast.makeText(this, "Location "+ latLng, Toast.LENGTH_LONG).show();
    }
}
