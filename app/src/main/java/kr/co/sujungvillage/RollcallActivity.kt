package kr.co.sujungvillage

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import kr.co.sujungvillage.base.BaseActivity
import kr.co.sujungvillage.base.toByteArray
import kr.co.sujungvillage.data.RollcallCreateDTO
import kr.co.sujungvillage.data.RollcallCreateResultDTO
import kr.co.sujungvillage.databinding.ActivityRollcallBinding
import kr.co.sujungvillage.retrofit.RetrofitBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.util.*

class RollcallActivity : BaseActivity() {

    val binding by lazy { ActivityRollcallBinding.inflate(layoutInflater) }

    private val PERM_CAMERA = 100 // 카메라 권한 처리
    private val REQ_CAMERA = 101 // 카메라 촬영 요청
    private val REQUEST_CODE_LOCATION: Int = 2 // 위치 반환 요청

    var imgByteArr: ByteArray? = null
    var currentLocation: String = ""
    var latitude: Double? = null
    var longitude: Double? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // 재사생 학번 불러오기
        val shared = this.getSharedPreferences("SujungVillage", Context.MODE_PRIVATE)
        val studentNum = shared?.getString("studentNum", "error").toString()
        val token = shared?.getString("token", "error").toString()

        // 뒤로가기 버튼 연결
        binding.btnBack.setOnClickListener { finish() }

        // 카메라 버튼 연결
        binding.btnGetImg.setOnClickListener {
            requirePermissions(arrayOf(Manifest.permission.CAMERA), PERM_CAMERA)
        }

        // 위치 새로고침 버튼 연결
        binding.btnLocationRefresh.setOnClickListener { getCurrentLoc() }
        binding.textLocationRefresh.setOnClickListener { getCurrentLoc() }
        binding.linearLocationRefresh.setOnClickListener { getCurrentLoc() }

        // 제출하기 버튼 연결 : 점호 신청 후 액티비티 종료
        binding.btnSubmit.setOnClickListener {
            // 이미지를 촬영하지 않은 경우
            if (imgByteArr == null) {
                Toast.makeText(this, "점호 사진을 촬영해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            // 현재 위치 주소를 등록하지 않은 경우
            if (binding.textLocation.text == "현재 위치 정보가 없습니다.") {
                Toast.makeText(this, "위치 새로고침을 해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val imgUrl = imgByteArr
            val location = binding.textLocation.text.toString()
            var rollcallInfo = RollcallCreateDTO(imgUrl!!, location)

            // 점호 신청 API 연결
            RetrofitBuilder.rollcallApi.rollcallCreate(token, rollcallInfo)
                .enqueue(object : Callback<RollcallCreateResultDTO> {
                    override fun onResponse(
                        call: Call<RollcallCreateResultDTO>,
                        response: Response<RollcallCreateResultDTO>
                    ) {
                        Log.d("ROLLCALL_CREATE", "점호 신청 성공")
                        Log.d("ROLLCALL_CREATE", "code : " + response.code().toString())
                        Log.d("ROLLCALL_CREATE", "error body : " + response.errorBody().toString())
                        Log.d("ROLLCALL_CREATE", "message : " + response.message())
                        Log.d("ROLLCALL_CREATE", "response : " + response.body().toString())

                        Toast.makeText(this@RollcallActivity, "점호가 완료되었습니다.", Toast.LENGTH_SHORT)
                            .show()
                        finish()
                    }

                    override fun onFailure(call: Call<RollcallCreateResultDTO>, t: Throwable) {
                        Toast.makeText(this@RollcallActivity, "오류가 발생하였습니다.", Toast.LENGTH_SHORT)
                            .show()
                        Log.e("ROLLCALL_CREATE", "점호 신청 실패")
                        Log.e("ROLLCALL_CREATE", t.message.toString())
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
            PERM_CAMERA -> Toast.makeText(
                baseContext,
                "카메라 권한을 승인해야 카메라를 사용할 수 있습니다.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    // 카메라 요청
    fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, REQ_CAMERA)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                REQ_CAMERA -> {
                    if (data?.extras?.get("data") != null) {
                        val bitmap = data?.extras?.get("data") as Bitmap
                        binding.btnGetImg.setImageBitmap(bitmap)

                        imgByteArr = bitmap.toByteArray()

                        // bitmap을 Base64 기반의 String으로 변환 (인코딩)
//                        val baos = ByteArrayOutputStream()
//                        bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, baos)
//                        imgBitmap = Base64.encodeToString(baos.toByteArray(), Base64.NO_WRAP)
//                        Log.d("IMG_BITMAP", "image bitmap : " + imgBitmap)
//                        Log.d(
//                            "IMG_BITMAP_LENGTH",
//                            "image bitmap length" + imgBitmap.length.toString()
//                        )
                    }
                }
            }
        }
    }

    // 현재 위치 반환 함수
    private fun getCurrentLoc() {
        var userLocation: Location? = getLatLng()
        if (userLocation != null) {
            latitude = userLocation.latitude
            longitude = userLocation.longitude
            Log.d("CheckCurrentLocation", "현재 내 위치 값: $latitude, $longitude")

            var mGeocoder = Geocoder(applicationContext, Locale.KOREAN)

            var mResultList: List<Address>? = null
            try {
                mResultList = mGeocoder.getFromLocation(latitude!!, longitude!!, 1)
                Log.d("CheckCurrentLocation", "mResultList : $mResultList")
            } catch (e: IOException) {
                e.printStackTrace()
                Log.d("CheckCurrentLocation", "오류발생")
                Log.d("CheckCurrentLocation", "mResultList : $mResultList")
            } catch (e: Exception) {
                binding.textLocation.text = "오류 발생"
                return
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

    private fun getLatLng(): Location? {
        var currentLatLng: Location? = null
        if (ActivityCompat.checkSelfPermission(
                applicationContext,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                    applicationContext,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                this.REQUEST_CODE_LOCATION
            )
            getLatLng()
        } else {
            val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
            val locationProvider = LocationManager.GPS_PROVIDER
            currentLatLng = locationManager.getLastKnownLocation(locationProvider)
            if (currentLatLng == null) {
                binding.textLocation.text = "서울 성북구 보문로34가길 8"
            }
        }
        return currentLatLng
    }
}
