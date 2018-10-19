package br.com.cwi.presente.services

import br.com.cwi.presente.models.Aula
import br.com.cwi.presente.models.Professor
import com.google.firebase.FirebaseError
import com.google.firebase.database.*
import java.util.*
import android.widget.TextView
import br.com.cwi.presente.utils.UserHolder
import br.com.cwi.presente.views.LoginView
import kotlinx.android.synthetic.main.activity_main.*


object LoginService {

    fun checkIfUserExists(view: LoginView, email: String?){

        val database = FirebaseDatabase.getInstance().reference

        val encode = email!!.toByteArray(Charsets.UTF_8)
        val encodedValue = android.util.Base64.encodeToString(encode, android.util.Base64.DEFAULT).trim()

        //val decode = email!!.toByteArray(Charsets.UTF_8)
        //val decodedValue = android.util.Base64.encodeToString(decode, android.util.Base64.DEFAULT).trim()
        //val rootRef = database.child("professores").orderByChild(decodedValue)
        val postRef = database.child("professores")

        postRef.orderByChild("email").equalTo(email)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {
                        TODO("not implemented")
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        if(p0.exists()){
                            val professor = p0.child(encodedValue).getValue(Professor::class.java)
                            UserHolder.userInfo = professor
                            view.onLoginSucceeded()
                        } else {
                            val professor = Professor()
                            professor.uid = UUID.randomUUID().toString()
                            professor.email = email
                            professor.materia = "Biologia"
                            professor.foto = "https://st2.depositphotos.com/1007566/12304/v/950/depositphotos_123041468-stock-illustration-avatar-man-cartoon.jpg"
                            professor.nome = "Luis Jaeger"
                            database.child("professores").child(encodedValue).setValue(professor)
                            UserHolder.userInfo = professor
                        }
                    }
                })
    }


}