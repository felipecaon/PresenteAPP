package br.com.cwi.presente.fragments.dialogs

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import br.com.cwi.presente.R
import br.com.cwi.presente.activities.MainActivity
import br.com.cwi.presente.presenters.CreateNewClassPresenter
import br.com.cwi.presente.utils.UserHolder
import br.com.cwi.presente.views.CreateNewClassView
import kotlinx.android.synthetic.main.fragment_create_new_class_dialog.*

class CreateNewClassDialog : DialogFragment(), CreateNewClassView {

    private val presenter: CreateNewClassPresenter by lazy {
        CreateNewClassPresenter(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_create_new_class_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        criarNovaAulaButton.setOnClickListener {
            presenter.createNewClass(
                    materiaEditText.text.toString()
            )
        }
    }

    override fun onCreateNewClassSucceeded() {
        Toast.makeText(context, "Dados salvos com sucesso.", Toast.LENGTH_SHORT).show()
        val intent = Intent(context, MainActivity::class.java)
        startActivity(intent)
    }

    override fun onCreateNewClassFailed(reason: String?) {
        Toast.makeText(context, reason, Toast.LENGTH_LONG).show()
    }
}
