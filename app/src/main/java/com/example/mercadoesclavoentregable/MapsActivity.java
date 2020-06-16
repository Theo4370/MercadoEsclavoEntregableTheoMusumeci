package com.example.mercadoesclavoentregable;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.mercadoesclavoentregable.model.Producto;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private String latitud;
    private String longitud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Producto producto = (Producto) bundle.getSerializable(MainActivity.PRODUCTO);

        latitud = producto.getSellerAdress().getLatitud();
        longitud = producto.getSellerAdress().getLongitud();
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        Double latitudDouble = Double.parseDouble(latitud);
        Double longitudDouble = Double.parseDouble(longitud);

        LatLng vendedor = new LatLng(latitudDouble, longitudDouble);
        mMap.addMarker(new MarkerOptions().position(vendedor).title("Vendedor"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(vendedor));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(vendedor,14f));

    }
}