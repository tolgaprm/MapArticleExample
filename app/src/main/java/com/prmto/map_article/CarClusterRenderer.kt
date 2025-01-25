package com.prmto.map_article

import android.content.Context
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.Cluster
import com.google.maps.android.clustering.view.DefaultClusterRenderer

class CarClusterRenderer(
    private val context: Context,
    private val map: GoogleMap?,
    private val clusterManager: CarClusterManager<CarClusterItem>?,
) : DefaultClusterRenderer<CarClusterItem>(context, map, clusterManager) {

    override fun onBeforeClusterItemRendered(
        item: CarClusterItem,
        markerOptions: MarkerOptions,
    ) {
        super.onBeforeClusterItemRendered(item, markerOptions)
        markerOptions.icon(
            IconHelper.getCarIcon(
                item = item,
                context = context
            )
        )
    }

    override fun onClusterItemUpdated(item: CarClusterItem, marker: Marker) {
        super.onClusterItemUpdated(item, marker)
        marker.setIcon(
            IconHelper.getCarIcon(
                item = item,
                context = context
            )
        )
    }

    override fun onBeforeClusterRendered(
        cluster: Cluster<CarClusterItem>,
        markerOptions: MarkerOptions,
    ) {
        super.onBeforeClusterRendered(cluster, markerOptions)
        markerOptions.icon(IconHelper.getClusterIcon(context = context, cluster = cluster))
    }

    override fun onClusterUpdated(cluster: Cluster<CarClusterItem>, marker: Marker) {
        super.onClusterUpdated(cluster, marker)
        marker.setIcon(IconHelper.getClusterIcon(context = context, cluster = cluster))
    }

    override fun shouldRenderAsCluster(cluster: Cluster<CarClusterItem>): Boolean =
        cluster.size >= 4 && clusterManager?.shouldClusterZoom == true
}