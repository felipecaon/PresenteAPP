package br.com.cwi.presente.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.Toast
import br.com.cwi.presente.R
import br.com.cwi.presente.R.id.fab
import br.com.cwi.presente.adapters.AulaAdapter
import br.com.cwi.presente.fragments.dialogs.CreateNewClassDialog
import br.com.cwi.presente.models.Aula
import br.com.cwi.presente.services.AulaService
import br.com.cwi.presente.utils.UserHolder
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView4.text = UserHolder.userInfo?.nome
        textView6.text = UserHolder.userInfo?.materia
        Picasso.get().load(UserHolder.userInfo?.foto).into(avatarProfessor)


        fab.setOnClickListener {
            val dialog = CreateNewClassDialog()
            dialog.show(supportFragmentManager, "CreateNewClassDialog")
        }

        val recyclerView = findViewById<RecyclerView>(R.id.listaAulas)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        val adapter = AulaAdapter(AulaService.retrieveAulasFromFirebase(), ::recebeAula, ::iniciarChamada, ::registroPresenca)
        recyclerView.adapter = adapter

    }

    fun recebeAula(aula: Aula){
        val intent = Intent(this, ListaAlunosActivity::class.java)
        intent.putExtra("nome",aula.nome)
        intent.putExtra("uid",aula.uid)
        intent.putExtra("professor", aula.professor.nome)
        startActivity(intent)
    }

    fun iniciarChamada(aula: Aula){
        val intent = Intent(this, IniciarChamadaActivity::class.java)
        intent.putExtra("nome",aula.nome)
        startActivity(intent)
    }

    fun registroPresenca(aula: Aula){
        val intent = Intent(this, RegistroActivity::class.java)
        intent.putExtra("nome",aula.nome)
        startActivity(intent)
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
