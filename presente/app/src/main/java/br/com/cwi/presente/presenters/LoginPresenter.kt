package br.com.cwi.presente.presenters

import android.content.Intent
import android.util.Log
import br.com.cwi.presente.services.LoginService
import br.com.cwi.presente.utils.UserHolder
import br.com.cwi.presente.views.LoginView
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class LoginPresenter(private val view: LoginView) {

    companion object {
        const val REQUEST_CODE = 9000
        const val TAG = "CWIFlix.LoginPresenter"
    }

    private val firebaseAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    fun logIn(email: String, password: String) {
        if (!email.isBlank() && !password.isBlank()) {
            firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            UserHolder.user = firebaseAuth.currentUser
                            LoginService.checkIfUserExists(view, UserHolder.user?.email)
                        } else {
                            view.onNormalLoginFailed(it.exception?.localizedMessage)
                        }
                    }
        } else {
            view.onLoginFailed()
        }
    }


    fun handleLoginResult(data: Intent?) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)

        try {
            val account = task.getResult(ApiException::class.java)
            val credential = GoogleAuthProvider.getCredential(account.idToken, null)

            firebaseAuth.signInWithCredential(credential)
                    .addOnCompleteListener {
                        if (task.isSuccessful) {
                            UserHolder.user = firebaseAuth.currentUser
                            view.onLoginSucceeded()
                        } else {
                            Log.e(TAG, "LoginPresenter.handleLoginResult@signInWithCredential: " + task.exception?.localizedMessage, task.exception)
                            view.onLoginFailed()
                        }
                    }

        } catch (exception: ApiException) {
            Log.e(TAG, "LoginPresenter.handleLoginResult: " + exception.localizedMessage, exception)
            view.onLoginFailed()
        }
    }



}