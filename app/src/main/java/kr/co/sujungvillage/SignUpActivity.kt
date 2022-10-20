package kr.co.sujungvillage

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.core.content.ContextCompat
import kr.co.sujungvillage.base.hideKeyboard
import kr.co.sujungvillage.data.SignUpDTO
import kr.co.sujungvillage.databinding.ActivitySignUpBinding
import kr.co.sujungvillage.fragment.CommFragment
import kr.co.sujungvillage.retrofit.RetrofitBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpActivity : AppCompatActivity() {
    val binding by lazy { ActivitySignUpBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        //변수들
        var id_overlap_check=0 //0: 중복확인 필요, 1: 아이디 사용 가능, 2: 아이디 사용 불가능
        var id=""
        var password=""
        var passwordCheck=""
        var name=""
        var number=""
        var number1=""
        var number2=""
        var number3=""
        var dormitory=""
        var address=""

        //키보드 내리기
        binding.signUpLayout.setOnClickListener { hideKeyboard() }
        binding.linearSignUp.setOnClickListener { hideKeyboard() }

        //포커스 체인지 리스너
        binding.editId.setOnFocusChangeListener(object : View.OnFocusChangeListener {
            override fun onFocusChange(view: View, hasFocus: Boolean) {
                if (hasFocus) {
                    changeToSelect(view)
                } else {
                    changeToUnselect(view)
                }
            }
        })
        binding.editPassword.setOnFocusChangeListener(object : View.OnFocusChangeListener {
            override fun onFocusChange(view: View, hasFocus: Boolean) {
                if (hasFocus) {
                    changeToSelect(view)
                } else {
                    changeToUnselect(view)
                }
            }
        })
        binding.editPasswordCheck.setOnFocusChangeListener(object : View.OnFocusChangeListener {
            override fun onFocusChange(view: View, hasFocus: Boolean) {
                if (hasFocus) {
                    changeToSelect(view)
                } else {
                    changeToUnselect(view)
                }
            }
        })
        binding.editName.setOnFocusChangeListener(object : View.OnFocusChangeListener {
            override fun onFocusChange(view: View, hasFocus: Boolean) {
                if (hasFocus) {
                    changeToSelect(view)
                } else {
                    changeToUnselect(view)
                }
            }
        })
        binding.editNumber1.setOnFocusChangeListener(object : View.OnFocusChangeListener {
            override fun onFocusChange(view: View, hasFocus: Boolean) {
                if (hasFocus) {
                    changeToSelect(view)
                } else {
                    changeToUnselect(view)
                }
            }
        })
        binding.editNumber2.setOnFocusChangeListener(object : View.OnFocusChangeListener {
            override fun onFocusChange(view: View, hasFocus: Boolean) {
                if (hasFocus) {
                    changeToSelect(view)
                } else {
                    changeToUnselect(view)
                }
            }
        })
        binding.editNumber3.setOnFocusChangeListener(object : View.OnFocusChangeListener {
            override fun onFocusChange(view: View, hasFocus: Boolean) {
                if (hasFocus) {
                    changeToSelect(view)
                } else {
                    changeToUnselect(view)
                }
            }
        })
        binding.editAddress.setOnFocusChangeListener(object : View.OnFocusChangeListener {
            override fun onFocusChange(view: View, hasFocus: Boolean) {
                if (hasFocus) {
                    changeToSelect(view)
                } else {
                    changeToUnselect(view)
                }
            }
        })
        //아이디
        binding.editId.addTextChangedListener ( object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                id_overlap_check=0
                idCheckVisibility(id_overlap_check)
            }
            override fun afterTextChanged(p0: Editable?) {
            }
        })
        //중복확인 버튼 api
        binding.textIdOverlapCheck.setOnClickListener{
            id=binding.editId.text.toString().trim()//공백제거
            if(id.isEmpty()){
                Toast.makeText(this,"아이디를 입력하세요.",Toast.LENGTH_SHORT).show()
            }
            else{
                RetrofitBuilder.signupApi.idCheck(id).enqueue(object : Callback<String>{
                    override fun onResponse(call: Call<String>, response: Response<String>) {
                        Log.d("ID_OVERLAP",response.body().toString())
                        if(response.body().toString() == "true"){//사용가능한 아이디
                            id_overlap_check=1
                            idCheckVisibility(id_overlap_check)
                        }
                        else{
                            id_overlap_check=2
                            idCheckVisibility(id_overlap_check)
                        }
                    }
                    override fun onFailure(call: Call<String>, t: Throwable) {
                        Log.e("ID_OVERLAP",t.message.toString())
                    }
                })
            }
        }

        //비밀번호
        binding.editPassword.addTextChangedListener ( object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                password=binding.editPassword.text.toString().trim()
                passwordCheck(password,passwordCheck)
            }
            override fun afterTextChanged(p0: Editable?) {
            }
        })
        binding.editPasswordCheck.addTextChangedListener ( object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                passwordCheck=binding.editPasswordCheck.text.toString().trim()
                passwordCheck(password,passwordCheck)
            }
            override fun afterTextChanged(p0: Editable?) {
            }
        })
        //스피너 커스텀
        CommFragment.dormitory = binding.spinnerDormitory.getSelectedItem().toString()
        var data=listOf("전체","성미료","성미관","풍림","엠시티","그레이스","이율","장수","운정빌")
        binding.spinnerDormitory.onItemSelectedListener=object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                CommFragment.dormitory =data.get(p2)
                dormitory=CommFragment.dormitory
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
        //회원가입 버튼
        binding.textSubmit.setOnClickListener{
            name=binding.editName.text.toString().trim()
            number1=binding.editNumber1.text.toString().trim()
            number2=binding.editNumber2.text.toString().trim()
            number3=binding.editNumber3.text.toString().trim()
            number=number1+number2+number3
            address=binding.editAddress.text.toString().trim()
            if(id_overlap_check==1){
                if(binding.textPasswordCheckResult.visibility.toString()!="0"){
                    if(password!=""&&name!="" && number1 !="" && number2 !="" && number3 !="" && address !=""){
                        val signUpInfo=SignUpDTO(id,password,name,dormitory,address,number)
                        RetrofitBuilder.signupApi.signUp(signUpInfo).enqueue(object :Callback<Void>{
                            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                                Log.d("SIGN_UP",response.body().toString())
                            }
                            override fun onFailure(call: Call<Void>, t: Throwable) {
                                Log.e("SIGN_UP",t.message.toString())
                            }
                        })
                    }
                    else{
                        Log.d("SIGN_UP","모든 칸을 작성해주세요.")
                    }
                }
                else{
                    Log.d("SIGN_UP","비밀번호를 확인해주세요")
                }
            }
            else{
                Log.d("SIGN_UP","id를 확인해주세요")

            }
        }
    }
    fun passwordCheck(a:String,b:String){
        if(a.equals(b)){//비밀번호 일치
            binding.textPasswordCheckResult.visibility=View.INVISIBLE
        }
        else{//비밀번호 불일치
            binding.textPasswordCheckResult.visibility=View.VISIBLE
        }
    }

    fun idCheckVisibility(a:Int){
        if(a==0){//중복확인 필요
            binding.textIdOverlapCheckResult.text="중복확인을 해주세요."
            binding.textIdOverlapCheckResult.setTextColor(Color.parseColor("#FF0000"))
        }
        else if(a==1){//아아디 사용 가능
            binding.textIdOverlapCheckResult.text="사용 가능한 아이디입니다."
            binding.textIdOverlapCheckResult.setTextColor(ContextCompat.getColor(this,R.color.primary))
        }
        else{//아이디 사용 불가능
            binding.textIdOverlapCheckResult.text="이미 존재하는 아이디입니다."
            binding.textIdOverlapCheckResult.setTextColor(Color.parseColor("#FF0000"))
        }
    }

    fun changeToUnselect(view:View){
        view.background=ContextCompat.getDrawable(this@SignUpActivity,R.drawable.style_signup_unselected)

    }
    fun changeToSelect(view:View){
        view.background=ContextCompat.getDrawable(this@SignUpActivity,R.drawable.style_signup_selected)

    }
}