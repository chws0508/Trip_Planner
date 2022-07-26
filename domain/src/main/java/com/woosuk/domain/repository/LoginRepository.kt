package com.woosuk.domain.repository

import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val auth: FirebaseAuth,
    private val googleSignInOptions: GoogleSignInOptions,
):Repository {

    fun googleSignIn(data:Intent?):Task<AuthResult>?{
        val task=GoogleSignIn.getSignedInAccountFromIntent((data))
        return try {
            var account = task.getResult(ApiException::class.java)!!
            val idToken=account.idToken
            val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
            auth.signInWithCredential(firebaseCredential)

        }catch (e: ApiException){
            Log.d("GoogleLogin", "Google sign in failed "+ e.message)
            null
        }
    }

    fun provideGoogleSignInClient(activity: Activity):GoogleSignInClient{
        return  GoogleSignIn.getClient(activity,googleSignInOptions)
    }
}