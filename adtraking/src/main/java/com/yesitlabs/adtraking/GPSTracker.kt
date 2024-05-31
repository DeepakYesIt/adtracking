package com.yesitlabs.adtraking

import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle


class GPSTracker(private val mContext: Context) : LocationListener {

    // flag for GPS status
    private var isGPSEnabled = false

    // flag for network status
    private var isNetworkEnabled = false

    // flag for GPS status
    private var canGetLocation = false

    private var location: Location? = null // location
    private var latitude: Double = 0.0 // latitude
    private var longitude: Double = 0.0 // longitude

    // The minimum distance to change Updates in meters
    private val MINDISTANCECHANGEFORUPDATES: Long = 1 // 10 meters

    // The minimum time between updates in milliseconds
    private val MINTIMEBWUPDATES: Long = 1 // 1 minute

    // Declaring a Location Manager
    private var locationManager: LocationManager? = null

    init {
        getLocation()
    }

    /**
     * Function to get the user's current location
     */
    @JvmName("getLocation1")
    fun getLocation(): Location? {
        try {
            locationManager = mContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager?

            // getting GPS status
            isGPSEnabled = locationManager?.isProviderEnabled(LocationManager.GPS_PROVIDER) ?: false

            // getting network status
            isNetworkEnabled = locationManager?.isProviderEnabled(LocationManager.NETWORK_PROVIDER) ?: false

            if (!isGPSEnabled && !isNetworkEnabled) {
                // no network provider is enabled
            } else {
                canGetLocation = true
                if (isNetworkEnabled) {
                    location = null
                    locationManager?.let {
                        if (androidx.core.app.ActivityCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_FINE_LOCATION) != android.content.pm.PackageManager.PERMISSION_GRANTED && androidx.core.app.ActivityCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_COARSE_LOCATION) != android.content.pm.PackageManager.PERMISSION_GRANTED) {
                            return null
                        }
                        it.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MINTIMEBWUPDATES,
                            MINDISTANCECHANGEFORUPDATES.toFloat(), this
                        )
                        location = it.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                        location?.let { loc ->
                            latitude = loc.latitude
                            longitude = loc.longitude
                        }
                    }
                }
                // if GPS Enabled get lat/long using GPS Services
                if (isGPSEnabled) {
                    location = null
                    locationManager?.let {
                        if (location == null) {
                            it.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                MINTIMEBWUPDATES,
                                MINDISTANCECHANGEFORUPDATES.toFloat(), this
                            )
                            location = it.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                            location?.let { loc ->
                                latitude = loc.latitude
                                longitude = loc.longitude
                            }
                        }
                    }
                }
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
        return location
    }

    /**
     * Function to get latitude
     */
    @JvmName("getLatitude1")
    fun getLatitude(): Double {
        location?.let { latitude = it.latitude }
        return latitude
    }

    /**
     * Function to get longitude
     */
    @JvmName("getLongitude1")
    fun getLongitude(): Double {
        location?.let { longitude = it.longitude }
        return longitude
    }

    override fun onLocationChanged(location: Location) {}

    override fun onProviderDisabled(provider: String) {}

    override fun onProviderEnabled(provider: String) {}

    @Deprecated("Deprecated in Java")
    override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}

}