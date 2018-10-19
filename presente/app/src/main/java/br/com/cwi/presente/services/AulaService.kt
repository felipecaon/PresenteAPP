package br.com.cwi.presente.services

import br.com.cwi.presente.models.Aluno
import br.com.cwi.presente.models.Aula
import br.com.cwi.presente.services.AulaService.database
import br.com.cwi.presente.utils.UserHolder
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

object AulaService {

    val database = FirebaseDatabase.getInstance().reference
    var aulas: ArrayList<Aula> = ArrayList()

    fun retrieveAulasFromFirebase() : ArrayList<Aula>{
        database.child("aulas").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot) {
             //   aulas.clear()
                for(obj in p0.children){
                    val aula = obj.getValue(Aula::class.java)
                    aulas.add(aula!!)
                }
                aulas = checkIfAulaIsBindedToTeacher(aulas)
            }
        })

        return aulas
    }

    fun checkIfAulaIsBindedToTeacher(aulas: ArrayList<Aula>) : ArrayList<Aula>{

        var manterAula =  ArrayList<Aula>()
        aulas.forEach {
            if(it.professor == UserHolder.userInfo){
                manterAula.add(it)
            }
        }

        return manterAula
    }
}