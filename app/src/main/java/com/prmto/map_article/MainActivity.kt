package com.prmto.map_article

import android.content.res.Resources
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.algo.NonHierarchicalViewBasedAlgorithm

class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val supportMapFragment = supportFragmentManager.findFragmentById(R.id.mapView) as
                SupportMapFragment
        supportMapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        val istanbul = LatLng(40.989010, 29.022949)
        val lat = 40.989010 + (Math.random() - 0.5) / 10
        val lng = 29.022949 + (Math.random() - 0.5) / 10

        googleMap.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                istanbul,
                15f
            )
        )
        val markerManager = MarkerManager()

        markerManager.initClusterManager(context = this, map = googleMap)

        markerManager.addSampleMarkers()
    }
}