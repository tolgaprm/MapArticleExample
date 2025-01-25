package com.prmto.map_article

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem
import java.util.UUID

data class CarClusterItem(
    val lat: Double,
    val lng: Double,
    val isSelected: Boolean,
    val clusterId: String = UUID.randomUUID().toString()
) : ClusterItem {

    override fun getPosition(): LatLng {
        return LatLng(lat, lng)
    }

    override fun getTitle(): String? {
        return null
    }

    override fun getSnippet(): String? {
        return null
    }

    override fun getZIndex(): Float? {
        return null
    }
}
