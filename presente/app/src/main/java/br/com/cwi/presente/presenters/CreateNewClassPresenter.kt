package br.com.cwi.presente.presenters

import br.com.cwi.presente.utils.UserHolder
import br.com.cwi.presente.views.CreateNewClassView
import com.google.firebase.auth.FirebaseAuth
import br.com.cwi.presente.R.string.email
import android.R.attr.name
import br.com.cwi.presente.activities.MainActivity
import br.com.cwi.presente.models.Aula
import com.google.firebase.FirebaseApp
import com.google.firebase.database.FirebaseDatabase
import java.util.*


class CreateNewClassPresenter(private val view: CreateNewClassView){

    val database = FirebaseDatabase.getInstance().reference

    fun createNewClass(className: String) {
        if (!className.isBlank()) {
                val aula = Aula(className, UserHolder.userInfo!!)
                aula.uid = UUID.randomUUID().toString()
                    database.child("aulas").child(aula.uid).setValue(aula)
                    view.onCreateNewClassSucceeded()
            } else {
                view.onCreateNewClassFailed("Dados inválidos, verifique-os e então tente novamente.")
            }
    }

}