package br.com.cwi.presente.fragments.dialogs

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.app.Fragment
import android.content.Intent
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import br.com.cwi.presente.R
import br.com.cwi.presente.activities.ListaAlunosActivity
import br.com.cwi.presente.activities.MainActivity
import br.com.cwi.presente.presenters.CreateNewAlunoPresenter
import br.com.cwi.presente.utils.UserHolder
import br.com.cwi.presente.views.CreateNewAlunoView
import kotlinx.android.synthetic.main.fragment_create_new_aluno_dialog.*

class CreateNewAlunoDialog : DialogFragment(), CreateNewAlunoView {
    private val presenter: CreateNewAlunoPresenter by lazy {
        CreateNewAlunoPresenter(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_create_new_aluno_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        criarNovoAlunoButton.setOnClickListener {
            presenter.createNewAluno(
                    nomeEditText.text.toString(),
                    emailEditText.text.toString()
            )
        }
    }

    override fun onCreateNewAlunoSucceeded() {
        Toast.makeText(context, "Dados salvos com sucesso.", Toast.LENGTH_SHORT).show()
        val intent = Intent(context, ListaAlunosActivity::class.java)
        startActivity(intent)
    }

    override fun onCreateNewAlunoFailed(reason: String?) {
        Toast.makeText(context, reason, Toast.LENGTH_LONG).show()
    }

}
