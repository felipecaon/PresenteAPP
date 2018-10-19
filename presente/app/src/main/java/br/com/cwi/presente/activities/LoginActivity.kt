package br.com.cwi.presente.activities

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import br.com.cwi.presente.R
import br.com.cwi.presente.presenters.LoginPresenter
import br.com.cwi.presente.services.LoginService
import br.com.cwi.presente.utils.UserHolder
import br.com.cwi.presente.views.LoginView
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), LoginView {

    private val presenter: LoginPresenter by lazy {
        LoginPresenter(this)
    }


    override fun onLoginSucceeded() {
        var display: String? = ""
        UserHolder.user?.run {
            display = if (displayName != null) displayName else email
        }
        Toast.makeText(this, "Olá, $display", Toast.LENGTH_SHORT).show()
        goToMainActivity()
    }

    private fun goToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)

        startActivity(intent)
    }

    override fun onLoginFailed() {
        Toast.makeText(this,
                getString(R.string.try_again_login),
                Toast.LENGTH_LONG).show()
    }

    override fun onNormalLoginFailed(reason: String?) {
        Toast.makeText(this, reason, Toast.LENGTH_LONG).show()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        logInButton.setOnClickListener {
            presenter.logIn(
                    emailEditText?.text.toString(),
                    passwordEditText?.text.toString()
            )
        }

        if (UserHolder.isLoggedIn()) {
            goToMainActivity()
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == LoginPresenter.REQUEST_CODE) {
            presenter.handleLoginResult(data)
        }
    }


}