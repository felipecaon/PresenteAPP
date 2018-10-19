package br.com.cwi.presente.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.LinearLayout
import br.com.cwi.presente.R
import br.com.cwi.presente.adapters.ListaPresencaAdapter
import br.com.cwi.presente.adapters.PresencaAdapter
import br.com.cwi.presente.services.ChamadaService
import br.com.cwi.presente.utils.UserHolder
import com.google.firebase.database.DataSnapshot
import kotlinx.android.synthetic.main.activity_registro.*

class PresencaActivity : AppCompatActivity() {


    val listaPresenca: ArrayList<DataSnapshot> = arrayListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_presenca)
        this@PresencaActivity.title = "Lista de Presenças"

        val extras = intent.extras
        if (extras != null) {
            UserHolder.aulaNome = extras.getString ("nome")
        }


        var adapter = ListaPresencaAdapter(listaPresenca)
        //ChamadaService.retrievePrsencaByAulaFromFirebase(UserHolder.aulaNome, adapter,chamada)
        ChamadaService.retrieveListaPresencaByAulaFromFirebase(UserHolder.aulaNome, adapter, listaPresenca)

        var recyclerView = findViewById<RecyclerView>(R.id.listaPresenca)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        recyclerView.adapter = adapter


    }
}
