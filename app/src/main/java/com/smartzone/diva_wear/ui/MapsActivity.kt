package com.smartzone.diva_wear.ui

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.View
import android.widget.EditText
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.common.api.Status
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.smartzone.diva_wear.R
import com.smartzone.diva_wear.databinding.ActivityMapsBinding
import com.smartzone.diva_wear.ui.base.BaseActivity
import com.smartzone.diva_wear.ui.base.BaseViewModel
import com.smartzone.diva_wear.utilis.AppUtils

import com.smartzone.diva_wear.utilis.LocationHelper
import se.arbitur.geocoding.Callback
import se.arbitur.geocoding.Response
import se.arbitur.geocoding.ReverseGeocoding
import java.io.IOException
import java.util.*

class MapsActivity : BaseActivity<ActivityMapsBinding>(), OnMapReadyCallback,
    LocationHelper.LocationListner {
    val REQUEST_COARSE_LOCATION=1
    private lateinit var mMap: GoogleMap
    lateinit var binding: ActivityMapsBinding
    var mLat = 0.0
    var mLon = 0.0
    var mAddress = ""
    var mCity = ""
    var mCountry = ""
    lateinit var location:LocationHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=getViewDataBinding()
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        Places.initialize(applicationContext, getString(R.string.google_maps_key))
        location= LocationHelper(this,this)
        showLoading()
        location.init()
//        val autocompleteFragment =
//            supportFragmentManager.findFragmentById(R.id.autocomplete_fragment) as AutocompleteSupportFragment?
//        autocompleteFragment!!.setPlaceFields(
//            Arrays.asList(
//                Place.Field.ID,
//                Place.Field.LAT_LNG,
//                Place.Field.NAME
//            )
//        )
//        autocompleteFragment.setCountry("EG")
//        if (autocompleteFragment.view != null) (autocompleteFragment.requireView()
//            .findViewById<View>(R.id.places_autocomplete_search_input) as EditText).textSize = 13.0f
//        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
//            override fun onPlaceSelected(place: Place) {
//                val cameraUpdate =
//                    CameraUpdateFactory.newLatLngZoom(place.latLng, 16.0f)
//                mMap.animateCamera(cameraUpdate)
//            }
//
//            override fun onError(status: Status) {}
//        })

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        binding.buttonConfirrm.setOnClickListener {
            if (mLat != 0.0 && mLon != 0.0) {
                val returnIntent = Intent()
                returnIntent.putExtra("lat", mLat)
                returnIntent.putExtra("lon", mLon)
                setResult(Activity.RESULT_OK, returnIntent)
                finish()
            }
        }
    }
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            googleMap.isMyLocationEnabled = true
        }
        googleMap.uiSettings.isRotateGesturesEnabled = false
        googleMap.uiSettings.isMyLocationButtonEnabled = false
//        mMap.setOnCameraIdleListener {
//            val latLng = mMap.cameraPosition.target
//            mLat = latLng.latitude
//            mLon = latLng.longitude
//
//            binding.address.setText(getAddress(latLng))
//        }

    }

    companion object{
        fun getIntent(context: Context):Intent=Intent(context,MapsActivity::class.java)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_maps
    }

    override fun getViewModel(): BaseViewModel? {
        return null
    }

//    fun getAddress(latLng: LatLng): String? {
//        ReverseGeocoding(
//            latLng.latitude,
//            latLng.longitude,
//            getString(R.string.google_maps_key)
//        )
//            .setLanguage("en")
//            .fetch(object : Callback {
//                override fun onResponse(response: Response) {
//                    mAddress = response.results[0].formattedAddress
//                    binding.address.setText(mAddress)
//                }
//                override fun onFailed(
//                    response: Response,
//                    e: IOException
//                ) {
//                }
//            })
//        return mAddress
//    }


    override fun getLocation(location: Location?) {
        if (location!=null){
            hideLoading()
            mLat = location.latitude
            mLon = location.longitude
            val latLng = LatLng(mLat, mLon)
            val cameraUpdate =
                CameraUpdateFactory.newLatLngZoom(latLng, 16.0f)
            mMap.animateCamera(cameraUpdate)
            this.location.stopLocationUpdate(this)
        }
    }

    override fun sendRequsestPermission() {
        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_COARSE_LOCATION)
        hideLoading()
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == REQUEST_COARSE_LOCATION) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                showLoading()
                location.init()
            }
        }
    }
}