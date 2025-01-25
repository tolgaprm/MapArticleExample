package com.prmto.map_article

import android.content.Context
import android.content.res.Resources
import android.util.DisplayMetrics
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.maps.android.clustering.Cluster
import com.google.maps.android.clustering.algo.NonHierarchicalViewBasedAlgorithm

class MarkerManager {

    private lateinit var clusterManager: CarClusterManager<CarClusterItem>
    private var googleMap: GoogleMap? = null

    fun initClusterManager(context: Context, map: GoogleMap) {
        clusterManager = CarClusterManager<CarClusterItem>(context, map)
        googleMap = map

        clusterManager.apply {
            setAlgorithm()
            renderer = CarClusterRenderer(context, map, this)
            map.setOnCameraIdleListener(this)
            setOnClusterClickListener { onClickedCluster(it, map) }
            setOnClusterItemClickListener { item ->
                onClickedClusterItem(item)
            }
        }
    }

    private fun setAlgorithm() {
        val metrics: DisplayMetrics = Resources.getSystem().displayMetrics
        val widthDp = (metrics.widthPixels / metrics.density).toInt()
        val heightDp = (metrics.heightPixels / metrics.density).toInt()
        clusterManager.algorithm = NonHierarchicalViewBasedAlgorithm(widthDp, heightDp)
    }

    private fun onClickedCluster(item: Cluster<CarClusterItem>, map: GoogleMap): Boolean {
        map.animateCamera(
            CameraUpdateFactory.newLatLngZoom(
                item.position,
                map.cameraPosition.zoom + 1
            )
        )
        return true
    }

    private fun onClickedClusterItem(
        item: CarClusterItem
    ): Boolean {
        val newItem = if (item.isSelected) {
            item.copy(isSelected = false)
        } else {
            item.copy(isSelected = true)
        }
        clusterManager.removeItem(item)
        clusterManager.addItem(newItem)
        clusterManager.cluster()
        googleMap?.animateCamera(
            CameraUpdateFactory.newLatLngZoom(
                newItem.position,
                googleMap?.cameraPosition?.zoom ?: 0f
            )
        )
        return true
    }

    fun addSampleMarkers() {
        for (i in 0..2000) {
            val lat = 40.989010 + (Math.random() - 0.5) / 10
            val lng = 29.022949 + (Math.random() - 0.5) / 10
            val carClusterItem = CarClusterItem(
                lat = lat,
                lng = lng,
                isSelected = false
            )
            clusterManager.addItem(carClusterItem)
            clusterManager.cluster()
        }
    }
}