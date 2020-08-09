package com.rcorp.restomenu.ui.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import org.koin.java.KoinJavaComponent.inject

class LoginViewModel: ViewModel() {

    private val firebaseAuth: FirebaseAuth by inject(FirebaseAuth::class.java)

    val state = MutableLiveData(LoginState.NOT_CONNECTED)
    val email = MutableLiveData("")
    val password = MutableLiveData("")
    val error = MutableLiveData("")

    init {
        state.value = if (firebaseAuth.currentUser == null) LoginState.NOT_CONNECTED else LoginState.CONNECTED
    }

    @SuppressLint("CheckResult")
    fun login() {
        if (email.value.isNullOrBlank() || password.value.isNullOrBlank())
            return
        state.value = LoginState.PENDING
        firebaseAuth.signInWithEmailAndPassword(email.value ?: return, password.value ?: return).addOnCompleteListener { task ->
            if (task.isSuccessful)
                state.value = LoginState.CONNECTED
            else {
                state.value = LoginState.ERROR
                error.value = task.exception?.message
                Log.e(TAG, "Error while login", task.exception)
            }
        }
    }

    enum class LoginState {
        CONNECTED,
        NOT_CONNECTED,
        PENDING,
        ERROR
    }

    companion object {
        private val TAG = this::class.java.simpleName
    }
}