package study.kotin.my.mycenter.common

import android.location.Location
import android.location.LocationListener
import android.os.Bundle

/**
 * Creat by blackwolf
 * 2019/1/17
 * system username : Administrator
 */
open class MyLocationListener: LocationListener {
    override fun onLocationChanged(location: Location?) {
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
    }

    override fun onProviderEnabled(provider: String?) {
    }

    override fun onProviderDisabled(provider: String?) {
    }
}