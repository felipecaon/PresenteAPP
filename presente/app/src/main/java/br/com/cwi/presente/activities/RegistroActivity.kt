package br.com.cwi.presente.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.LinearLayout
import br.com.cwi.presente.R
import br.com.cwi.presente.adapters.AlunoAdapter
import br.com.cwi.presente.adapters.PresencaAdapter
import br.com.cwi.presente.models.Aluno
import br.com.cwi.presente.models.Aula
import br.com.cwi.presente.models.Chamada
import br.com.cwi.presente.services.AlunoService
import br.com.cwi.presente.services.ChamadaService
import br.com.cwi.presente.utils.UserHolder
import com.google.firebase.database.DataSnapshot
import kotlinx.android.synthetic.main.activity_registro.*

class RegistroActivity : AppCompatActivity() {

    val chamada: MutableList<Chamada> = mutableListOf()
    val diasDeAula: ArrayList<DataSnapshot> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)
        this@RegistroActivity.title = "Registro de Presenças"

        val extras = intent.extras
        if (extras != null) {
            textAula.text = extras.getString ("nome")
            UserHolder.aulaNome = extras.getString ("nome")
        }

        var adapter = PresencaAdapter(diasDeAula, ::gotolista)
        //ChamadaService.retrievePrsencaByAulaFromFirebase(UserHolder.aulaNome, adapter,chamada)
        ChamadaService.retrieveDiasDeAulaFromFirebase(UserHolder.aulaNome, adapter, diasDeAula)

        val aaa =  diasDeAula

        var recyclerView = findViewById<RecyclerView>(R.id.listaPresencas)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        recyclerView.adapter = adapter

    }

    fun gotolista(dataSnapshot: DataSnapshot){
        val intent = Intent(this, PresencaActivity::class.java)
        intent.putExtra("nome", UserHolder.aulaNome)
        startActivity(intent)
    }

}
