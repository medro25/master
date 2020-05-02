package com.witnovus.placefinder.ui.searchplace

import android.Manifest
import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.sprotte.geolocator.tracking.LocationTracker
import com.witnovus.placefinder.R
import com.witnovus.placefinder.event.LocationEvent
import com.witnovus.placefinder.location.CurrentLocationTrackerService
import com.witnovus.placefinder.utils.AppUtils
import com.witnovus.placefinder.utils.Constants
import com.witnovus.placefinder.utils.GpsUtils
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class SearchPlaceActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private var isGPS: Boolean = false
    private  var latitude: Double = 0.0
    private  var longitude: Double = 0.0

    private var isLocationSearch :Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_place)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        checkLocationPermission();
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {


        // Add a marker in Sydney and move the camera
       /* val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))*/

        if (googleMap != null) {
            mMap = googleMap
        }


    }



    /**
     * check Location Permission
     */

    fun checkLocationPermission(): Boolean {

        if (ContextCompat.checkSelfPermission(
               this@SearchPlaceActivity,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            // Should we show an explanation?
            if (!ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                AlertDialog.Builder(this)
                    .setMessage(getString(R.string.lbl_give_location_permission))
                    .setPositiveButton(
                        getString(R.string.lbl_ok),
                        DialogInterface.OnClickListener { dialogInterface, i ->
                            //Prompt the user once explanation has been shown
                            ActivityCompat.requestPermissions(
                                this,
                                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                                Constants.MY_PERMISSIONS_REQUEST_LOCATION
                            )
                        })
                    .create()
                    .show()

            } else {

                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    Constants.MY_PERMISSIONS_REQUEST_LOCATION
                )
            }
            return false
        } else {
            GpsUtils(this).turnGPSOn(object : GpsUtils.onGpsListener {
                override fun gpsStatus(isGPSEnable: Boolean) {
                    isGPS = isGPSEnable;
                    if (!isGPS) {
                        AppUtils.showDialogMessage(
                           this@SearchPlaceActivity,
                            getString(R.string.msg_location_service_enable)
                        )
                    } else {
                        registerLocationUpdateEvents()
                    }
                }
            })
            return true
        }
    }

    /**
     * location update call LocationTrackerService
     */
    fun registerLocationUpdateEvents() {
        LocationTracker.requestLocationUpdates(
           this,
            CurrentLocationTrackerService::class.java
        )
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }


    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public fun onMessageEvent(locationEvent: LocationEvent) {
        Log.d("eventBus", "eventBus Called")
        if (AppUtils.checkConnection(this@SearchPlaceActivity)) {
            latitude = locationEvent.latlng.lat
            longitude = locationEvent.latlng.lng

            if(!isLocationSearch){
                val contains = mMap.projection.visibleRegion.latLngBounds.contains(
                    LatLng(
                        latitude,
                        longitude
                    )
                )
                if (!contains) {
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(LatLng(latitude, longitude)))
                }
            }
        } else {
            showInternetAlertDialog()
        }
    }


    private fun showInternetAlertDialog() {
        val builder = AlertDialog.Builder(this@SearchPlaceActivity)
        builder.setMessage(resources.getString(R.string.msg_on_internet))

        builder.setPositiveButton(resources.getString(R.string.lbl_yes)) { dialog, which ->
            startActivity(Intent(Settings.ACTION_WIFI_SETTINGS))
        }
        builder.setNegativeButton(resources.getString(R.string.lbl_no)) { dialog, which ->

        }
        val dialog: AlertDialog = builder.create()

        dialog.setOnShowListener {
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE)
                .setTextColor(ContextCompat.getColor(this@SearchPlaceActivity, R.color.colorPrimary))
            dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                .setTextColor(ContextCompat.getColor(this@SearchPlaceActivity, R.color.colorPrimary))
        }
        dialog.show()

    }

}
