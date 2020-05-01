package com.witnovus.placefinder.location

import android.util.Log
import com.google.android.gms.location.LocationResult
import com.google.maps.model.LatLng
import com.sprotte.geolocator.tracking.service.LocationTrackerUpdateIntentService
import com.witnovus.placefinder.event.LocationEvent
import org.greenrobot.eventbus.EventBus

class CurrentLocationTrackerService : LocationTrackerUpdateIntentService()
{
    override fun onLocationResult(locationResult: LocationResult)
    {
        Log.d("locationservices","onLocationResult Services calling ")

        var latitude:Double = locationResult.locations.get(0).latitude;
        var longitude:Double = locationResult.locations.get(0).longitude
        var latlng = LatLng(latitude,longitude)
        var locatinEvent = LocationEvent(latlng = latlng)
        EventBus.getDefault().postSticky(locatinEvent)

        Log.d("locationservices","locationservices")
        Log.d("LocationServices", "Lat:- " + latitude.toString())
        Log.d("LocationServices", "Lng:- " + longitude.toString())
    }
}