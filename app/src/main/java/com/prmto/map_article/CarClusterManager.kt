package com.prmto.map_article

import android.content.Context
import com.google.android.gms.maps.GoogleMap
import com.google.maps.android.clustering.ClusterItem
import com.google.maps.android.clustering.ClusterManager

class CarClusterManager<T : ClusterItem>(
    private val context: Context,
    private val map: GoogleMap
) : ClusterManager<T>(context, map) {

    companion object {
        private const val CLUSTER_MAX_ZOOM_LEVEL = 14
    }

    private var _shouldClusterZoom: Boolean = true
    val shouldClusterZoom get() = _shouldClusterZoom

    override fun onCameraIdle() {
        super.onCameraIdle()
        _shouldClusterZoom = map.cameraPosition.zoom < CLUSTER_MAX_ZOOM_LEVEL
    }
}