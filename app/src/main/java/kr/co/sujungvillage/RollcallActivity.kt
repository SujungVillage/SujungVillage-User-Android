package kr.co.sujungvillage

import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import kr.co.sujungvillage.databinding.ActivityRollcallBinding
import android.location.Address
import android.location.LocationManager
import java.io.IOException
import java.util.*

class RollcallActivity : AppCompatActivity() {

    val binding by lazy { ActivityRollcallBinding.inflate(layoutInflater) }


    var locationManager : LocationManager? = null
    private val REQUEST_CODE_LOCATION : Int = 2
    var currentLocation : String = ""
    var latitude : Double? = null
    var longitude : Double? = null

    private fun getCurrentLoc() {
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager?
        var userLocation: Location = getLatLng()
        if (userLocation != null) {
            latitude = userLocation.latitude
            longitude = userLocation.longitude
            Log.d("CheckCurrentLocation", "현재 내 위치 값: $latitude, $longitude")

            var mGeocoder = Geocoder(applicationContext, Locale.KOREAN)

            /*  if (mGeocoder != null) {
                  Log.d("CheckCurrentLocation", "null 아님")
              }else {
                  Log.d("CheckCurrentLocation", "null 값임")
              }*/
            var mResultList: List<Address>? = null
            try {
                mResultList = mGeocoder.getFromLocation(
                    latitude!!, longitude!!, 1,
                )
                Log.d("CheckCurrentLocation", "mResultList : $mResultList")

            } catch (e: IOException) {
                e.printStackTrace()
                Log.d("CheckCurrentLocation", "오류발생")
                Log.d("CheckCurrentLocation", "mResultList : $mResultList")

            }
            if (mResultList != null) {
                Log.d("CheckCurrentLocation", mResultList[0].getAddressLine(0))
                currentLocation = mResultList[0].getAddressLine(0)
                currentLocation = currentLocation.substring(11)
                binding.textLocation.text = currentLocation
                Log.d("CheckCurrentLocation", "현재 내 주소 : $currentLocation")
            }
        }
    }

    private fun getLatLng() : Location {
        var currentLatLng: Location? = null
        if (ActivityCompat.checkSelfPermission(applicationContext, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(applicationContext, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), this.REQUEST_CODE_LOCATION)
            getLatLng()
        } else {
            val locationProvider = LocationManager.GPS_PROVIDER
            currentLatLng = locationManager?.getLastKnownLocation(locationProvider)
        }
        return currentLatLng!!
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        // 뒤로가기 버튼 연결



        binding.btnBack.setOnClickListener { finish() }
        binding.btnSubmit.setOnClickListener {
            //제출버튼 구현
        }
        binding.btnLocationRefresh.setOnClickListener {
            getCurrentLoc()
        }
    }



}

