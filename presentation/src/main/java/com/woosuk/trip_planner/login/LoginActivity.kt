package com.woosuk.trip_planner.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.facebook.login.Login
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.woosuk.trip_planner.R
import com.woosuk.trip_planner.databinding.ActivityLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity: AppCompatActivity() {

    private lateinit var binding:ActivityLoginBinding
    private lateinit var activityLauncher: ActivityResultLauncher<Intent>
    private lateinit var googleSignInClient:GoogleSignInClient

    private val loginViewModel by viewModels<LoginViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this, R.layout.activity_login)

        googleSignInClient=loginViewModel.provideGoogleSignInClient(activity = this)

        activityLauncher=registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result: ActivityResult ->
            if(result.resultCode == RESULT_OK){
                loginViewModel.googleSignIn(result.data)
            }
        }

        Glide.with(this).load(R.drawable.giphy).into(binding.gifImage)
        init()
    }

    fun init(){
        googleButtonClick()
    }

    fun kakaotalkButtonCLick(){
        binding.kakaotalkLoginButton.setOnClickListener{

        }
    }

    fun googleButtonClick() {
        binding.googleLoginButton.setOnClickListener {
         activityLauncher.launch(googleSignInClient!!.signInIntent)
        }
    }
}