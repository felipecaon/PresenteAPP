package br.com.cwi.presente.activities

import android.os.Bundle
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.Toast
import br.com.cwi.presente.R
import br.com.cwi.presente.adapters.AlunoAdapter
import br.com.cwi.presente.fragments.dialogs.CreateNewAlunoDialog
import br.com.cwi.presente.models.Aluno
import br.com.cwi.presente.services.AlunoService
import br.com.cwi.presente.utils.UserHolder
import br.com.cwi.presente.utils.UserHolder.aulaNome

import kotlinx.android.synthetic.main.activity_lista_alunos.*

class ListaAlunosActivity : AppCompatActivity() {

    val alunos: MutableList<Aluno> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_alunos)
        this@ListaAlunosActivity.title = "Lista de Alunos"


        val extras = intent.extras
        if (extras != null) {
            textView16.text = extras.getString ("nome")
            // textView18.text = extras.getString("professor")
            UserHolder.aulaNome = extras.getString ("nome")
        }

        adicionarAluno.setOnClickListener {
            val dialog = CreateNewAlunoDialog()
            dialog.show(supportFragmentManager, "CreateNewAlunoDialog")
        }

        var adapter = AlunoAdapter(alunos)
        AlunoService.retrieveAlunosByAulaFromFirebase(aulaNome, adapter, alunos)

        var recyclerView = findViewById<RecyclerView>(R.id.listaAlunos)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        recyclerView.adapter = adapter

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.logout) {
            UserHolder.logOut(this)
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        return super.onOptionsItemSelected(item)
    }


}