package com.woosuk.trip_planner.login

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.woosuk.domain.repository.LoginRepository
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository
) : ViewModel() {

    private var _googleSignInSuccess = MutableLiveData<Boolean>()
    val googleSignInSuccess: LiveData<Boolean> get() = _googleSignInSuccess

    fun googleSignIn(data: Intent?){
       val googleSignIn= loginRepository.googleSignIn(data)
        if(googleSignIn!=null){
            googleSignIn.addOnCompleteListener{task->
                if(task.isSuccessful){
                    _googleSignInSuccess.postValue(true)
                }else
                {
                    _googleSignInSuccess.postValue(false)
                }
            }
        }
    }

    fun provideGoogleSignInClient(activity: Activity):GoogleSignInClient{
        return loginRepository.provideGoogleSignInClient(activity)
    }
}