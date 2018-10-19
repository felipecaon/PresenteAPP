package br.com.cwi.presente.services

import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import br.com.cwi.presente.activities.ListaAlunosActivity
import br.com.cwi.presente.adapters.AlunoAdapter
import br.com.cwi.presente.models.Aluno
import com.google.firebase.database.*

object AlunoService {

    val database = FirebaseDatabase.getInstance().reference
    /*
        fun retrieveAlunosFromFirebase() : ArrayList<Aluno>{
            AlunoService.database.child("alunos").addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onDataChange(p0: DataSnapshot) {
                    //AlunoService.alunos.clear()
                    for(obj in p0.children){
                        val aluno = obj.getValue(Aluno::class.java)
                            AlunoService.alunos.add(aluno!!)
                    }
                }
            })

            return alunos
        }
    */
    fun retrieveAlunosByAulaFromFirebase(aula: String?, adapter: AlunoAdapter, alunos: MutableList<Aluno>) {
        val query1 = database.child("alunos").orderByChild("aula").equalTo(aula)
        query1.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                alunos.clear()
                //Passar os dados para a interface grafica
                for(obj in dataSnapshot.children){
                    val aluno = obj.getValue(Aluno::class.java)
                    alunos.add(aluno!!)
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                //Se ocorrer um erro
            }
        })

    }
}