package com.rcorp.restomenu.ui.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import org.koin.java.KoinJavaComponent.inject

class RegisterViewModel: ViewModel() {

    private val firebaseAuth: FirebaseAuth by inject(FirebaseAuth::class.java)

    val state = MutableLiveData(RegisterState.INIT)
    val email = MutableLiveData("")
    val password = MutableLiveData("")
    val error = MutableLiveData("")

    @SuppressLint("CheckResult")
    fun register() {
        if (email.value.isNullOrBlank() || password.value.isNullOrBlank())
            return
        state.value = RegisterState.PENDING
        firebaseAuth.createUserWithEmailAndPassword(email.value ?: return, password.value ?: return).addOnCompleteListener { task ->
            if (task.isSuccessful)
                state.value = RegisterState.DONE
            else {
                state.value = RegisterState.ERROR
                error.value = task.exception?.message
                Log.e(TAG, "Error while login", task.exception)
            }
        }
    }

    enum class RegisterState {
        DONE,
        INIT,
        PENDING,
        ERROR
    }

    companion object {
        private val TAG = this::class.java.simpleName
    }
}