package br.com.cwi.presente.utils

import android.app.Activity
import android.os.Handler
import br.com.cwi.presente.models.Aula
import br.com.cwi.presente.models.Professor
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

object UserHolder {
    var user: FirebaseUser? = null

    var userInfo: Professor? = null

    var aulaNome: String? = null

    var signInOptions: GoogleSignInOptions? = null

    fun isLoggedIn() = user != null

    fun logOut(activity: Activity) {
        FirebaseAuth.getInstance().signOut()

        signInOptions?.let {
            val client = GoogleSignIn.getClient(activity, it)
            client.signOut()
        }

        user = null
    }
}