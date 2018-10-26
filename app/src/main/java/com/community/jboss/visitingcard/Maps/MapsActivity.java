package com.community.jboss.visitingcard.Maps;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.community.jboss.visitingcard.R;
import com.community.jboss.visitingcard.VisitingCard.ViewVisitingCard;
import com.community.jboss.visitingcard.VisitingCard.VisitingCardActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final int RC_MAPS = 1002;
    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationClient;
    private Location userLocation;
    private LocationRequest locationRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        View bottomSheet = findViewById(R.id.bottom_sheet);
        final BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);

        // TODO: Replace the TextView with a ListView containing list of Visiting cards in that locality using geo-fencing

        // TODO: List item click should result in launching of ViewVisitingCard Acitivity with the info of the tapped Visiting card.

        TextView list_item = findViewById(R.id.list_item);
        list_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toVisitingCardView = new Intent(MapsActivity.this, ViewVisitingCard.class);
                startActivity(toVisitingCardView);
            }
        });

        //TODO: Create Custom pins for the selected location
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //TODO: Implement geo-fencing(NOT AS A WHOLE) just visual representation .i.e., a circle of an arbitrary radius with the PIN being the centre of it.
        //TODO: Make the circle color as @color/colorAccent
    }

    @SuppressLint("MissingPermission")
    private void updateLocation() {
        locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_LOW_POWER)
                .setInterval(4_000).setFastestInterval(2_000);

        mFusedLocationClient.requestLocationUpdates(locationRequest, new LocationCallback(){
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                if(locationResult.getLocations().get(0) != null)
                    userLocation = locationResult.getLocations().get(0);
                CameraUpdateFactory.zoomTo(20.0f);
                mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(userLocation.getLatitude(), userLocation.getLongitude())));

            }
        }, null);
    }

    // TODO: Replace the stating location with user's current location.


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == RC_MAPS){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        updateLocation();
                    }
                }, 1000);
            } else {
                Toast.makeText(this, "Sorry! We can't show you nearby locations.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, RC_MAPS);
                Toast.makeText(this, "You need to enable permissions first!", Toast.LENGTH_SHORT).show();
            }else updateLocation();
        }else updateLocation();

        LatLng london = new LatLng(51.504350, -0.087130);
        LatLng frankfurt = new LatLng(50.111268, 8.673362);
        LatLng paris = new LatLng(48.858219, 2.294294);
        LatLng newYork = new LatLng(40.712974, -74.013334);
        LatLng seoul = new LatLng(37.551182, 126.988000);
        LatLng taipei = new LatLng(25.036848, 121.515685);
        mMap.addMarker(new MarkerOptions().position(london).title("Marker in London"));
        mMap.addMarker(new MarkerOptions().position(frankfurt).title("Marker in Frankfurt"));
        mMap.addMarker(new MarkerOptions().position(paris).title("Marker in Paris"));
        mMap.addMarker(new MarkerOptions().position(newYork).title("Marker in New York"));
        mMap.addMarker(new MarkerOptions().position(seoul).title("Marker in Seoul"));
        mMap.addMarker(new MarkerOptions().position(taipei).title("Marker in Taipei"));
    }
}
