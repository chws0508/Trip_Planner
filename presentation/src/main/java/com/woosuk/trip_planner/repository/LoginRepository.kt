package com.woosuk.trip_planner.repository

import android.content.Intent
import android.util.Log
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.functions.FirebaseFunctions
import com.kakao.sdk.user.UserApiClient
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val auth: FirebaseAuth,
    private val firebaseFunctions: FirebaseFunctions
) : Repository {

    fun googleSignIn(idToken: String?): Task<AuthResult> {
        val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
        return auth.signInWithCredential(firebaseCredential)
    }

    fun facebookSignIn(accessToken: AccessToken): Task<AuthResult> {
        val credential = FacebookAuthProvider.getCredential(accessToken.token)
        return auth.signInWithCredential(credential)
    }

    fun kakaoSignIn(customToken: String): Task<AuthResult> {
        return auth.signInWithCustomToken(customToken)
    }

    fun getCustomTokenFromKakao(accessToken: String): Task<String> {
        val data = hashMapOf(
            "access_token" to accessToken
        )
        return firebaseFunctions
            .getHttpsCallable("kakaoToken")
            .call(data)
            .continueWith { task ->
                val result = task.result?.data as String
                result
            }
    }
}