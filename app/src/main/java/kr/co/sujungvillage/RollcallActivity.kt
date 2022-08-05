package kr.co.sujungvillage

import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Looper
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import kr.co.sujungvillage.databinding.ActivityRollcallBinding
import android.location.Address
import android.location.LocationManager
import java.io.IOException
import java.util.*
import android.Manifest
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.widget.Toast
import kr.co.sujungvillage.base.BaseActivity
import kr.co.sujungvillage.data.RollcallCreateDTO
import kr.co.sujungvillage.data.RollcallCreateResultDTO
import kr.co.sujungvillage.retrofit.RetrofitBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream


class RollcallActivity : BaseActivity() {

    val binding by lazy { ActivityRollcallBinding.inflate(layoutInflater) }


    var locationManager: LocationManager? = null
    private val REQUEST_CODE_LOCATION: Int = 2
    var currentLocation: String = ""
    var latitude: Double? = null
    var longitude: Double? = null

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

    private fun getLatLng(): Location {
        var currentLatLng: Location? = null
        if (ActivityCompat.checkSelfPermission(
                applicationContext,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(
                applicationContext,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                this.REQUEST_CODE_LOCATION
            )
            getLatLng()
        } else {
            val locationProvider = LocationManager.GPS_PROVIDER
            currentLatLng = locationManager?.getLastKnownLocation(locationProvider)
        }
        return currentLatLng!!
    }

    val PERM_CAMERA = 100 // 카메라 권한 처리
    val REQ_CAMERA = 101 // 카메라 촬영 요청
    var imgBitmap = ""


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


        // 카메라 버튼 연결
        binding.btnGetImg.setOnClickListener {
            requirePermissions(arrayOf(Manifest.permission.CAMERA), PERM_CAMERA)
        }

        // 인증 버튼 연결 : 점호 신청 후 액티비티 종료
        binding.btnSubmit.setOnClickListener {
            val user_id = "20180001"
            if (imgBitmap.isEmpty()) {
                Toast.makeText(this, "점호 사진을 촬영해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val imgUrl = imgBitmap
            val location = "서울특별시 성북구 보문로 34가길 2"
            var rollcallInfo = RollcallCreateDTO(imgUrl, location)

            // 점호 신청 API 연결
            RetrofitBuilder.rollcallApi.rollcallCreate(user_id, rollcallInfo)
                .enqueue(object : Callback<RollcallCreateResultDTO> {
                    override fun onResponse(
                        call: Call<RollcallCreateResultDTO>,
                        response: Response<RollcallCreateResultDTO>
                    ) {
                        Log.d("ROLLCALL_CREATE", "id : " + response.body()?.id.toString())
                        Log.d("ROLLCALL_CREATE", "user id : " + response.body()?.userId)
                        Log.d("ROLLCALL_CREATE", "image url : " + response.body()?.imgUrl)
                        Log.d("ROLLCALL_CREATE", "location : " + response.body()?.location)
                        Log.d("ROLLCALL_CREATE", "time : " + response.body()?.time)
                        Log.d("ROLLCALL_CREATE", "state : " + response.body()?.state)

                        Toast.makeText(this@RollcallActivity, "점호가 완료되었습니다.", Toast.LENGTH_SHORT)
                            .show()
                        finish()
                    }

                    override fun onFailure(call: Call<RollcallCreateResultDTO>, t: Throwable) {
                        Toast.makeText(this@RollcallActivity, "오류가 발생하였습니다.", Toast.LENGTH_SHORT)
                            .show()
                        Log.d("ROLLCALL_CREATE", "점호 신청 실패")
                    }
                })
        }
    }

    // 권한 승인
    override fun permissionGranted(requestCode: Int) {
        when (requestCode) {
            PERM_CAMERA -> openCamera()
        }
    }

    // 권한 거부
    override fun permissionDenied(requestCode: Int) {
        when (requestCode) {
            PERM_CAMERA -> {
                Toast.makeText(baseContext, "카메라 권한을 승인해야 카메라를 사용할 수 있습니다.", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    // 카메라 요청
    fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, REQ_CAMERA)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK)
            when (requestCode) {
                REQ_CAMERA -> {
                    if (data?.extras?.get("data") != null) {
                        val bitmap = data?.extras?.get("data") as Bitmap
                        binding.btnGetImg.setImageBitmap(bitmap)

                        // bitmap을 base64 기반의 string으로 변환(인코딩)
                        val baos = ByteArrayOutputStream()
                        bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                        imgBitmap = Base64.encodeToString(baos.toByteArray(), Base64.NO_WRAP)
                        Log.d("IMG_BITMAP_LENGTH", "image bitmap : " + imgBitmap)
                        Log.d(
                            "IMG_BITMAP_LENGTH",
                            "image bitmap length" + imgBitmap.length.toString()
                        )
                    }
                }
            }
    }
}

