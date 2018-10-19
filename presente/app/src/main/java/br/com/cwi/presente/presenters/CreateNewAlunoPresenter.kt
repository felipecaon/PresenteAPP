package br.com.cwi.presente.presenters

import br.com.cwi.presente.models.Aluno
import br.com.cwi.presente.utils.UserHolder
import br.com.cwi.presente.views.CreateNewAlunoView
import com.google.firebase.database.FirebaseDatabase
import java.util.*
import kotlin.collections.ArrayList

class CreateNewAlunoPresenter(private val view: CreateNewAlunoView){

    val database = FirebaseDatabase.getInstance().reference

    fun createNewAluno(nome: String, email: String) {
        if (!nome.isBlank() && !email.isBlank()) {

            val aluno = Aluno(nome, email)
            aluno.uid = UUID.randomUUID().toString()
            aluno.aula = UserHolder.aulaNome!!
            aluno.macAddress = email
            database.child("alunos").child(aluno.uid).setValue(aluno)
            view.onCreateNewAlunoSucceeded()
        } else {
            view.onCreateNewAlunoFailed("Dados inválidos, verifique-os e então tente novamente.")
        }
    }

}