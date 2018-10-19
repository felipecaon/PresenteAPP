package br.com.cwi.presente.services

import android.net.wifi.p2p.WifiP2pDevice
import br.com.cwi.presente.adapters.AlunoAdapter
import br.com.cwi.presente.adapters.ListaPresencaAdapter
import br.com.cwi.presente.adapters.PresencaAdapter
import br.com.cwi.presente.models.Aluno
import br.com.cwi.presente.models.Aula
import br.com.cwi.presente.models.Chamada
import br.com.cwi.presente.utils.UserHolder
import com.google.firebase.database.*
import java.util.*
import kotlin.collections.ArrayList

object ChamadaService {

    val database = FirebaseDatabase.getInstance().reference
    var presencas : ArrayList<String> = arrayListOf()
    var alunosPresentes : ArrayList<String> = arrayListOf()

    fun retrieveAulasFromFirebase(peers: ArrayList<WifiP2pDevice>){


       peers.forEach{
             presencas.add(it.deviceAddress)
        }


        //HARD
        //presencas.add("c2:ee:fb:ea:39:c0")
        //presencas.add("cc:c3:ea:e0:6f:d7")


        presencas.forEach {
            var query = FirebaseDatabase.getInstance().getReference("alunos").orderByChild("macAddress").equalTo(it)
            query.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onDataChange(p0: DataSnapshot) {
                    for(obj in p0.children){
                        val aluno = obj.getValue(Aluno::class.java)
                        alunosPresentes.add(aluno!!.nome)
                    }
                    database.child("chamada").child(UserHolder.aulaNome!!).child(Calendar.getInstance().get(Calendar.DAY_OF_MONTH).toString() + Calendar.getInstance().get(+Calendar.MONTH).toString()).setValue(alunosPresentes)
                }
            })
        }


        presencas.clear()
        alunosPresentes.clear()


    }

    fun retrieveListaPresencaByAulaFromFirebase(aula: String?, adapter: ListaPresencaAdapter, listaPresenca: ArrayList<DataSnapshot>) {
        val query1 = FirebaseDatabase.getInstance().getReference("chamada").child(aula!!)
        query1.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                listaPresenca.clear()
                //Passar os dados para a interface grafica
                for(obj in dataSnapshot.children){
                    listaPresenca.add(obj)
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                //Se ocorrer um erro
            }
        })
    }

    fun retrieveDiasDeAulaFromFirebase(aula: String?, adapter: PresencaAdapter, diasAula: ArrayList<DataSnapshot>) {
        val query1 = FirebaseDatabase.getInstance().getReference("chamada").child(aula!!)
        query1.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                diasAula.clear()
                //Passar os dados para a interface grafica
                for(obj in dataSnapshot.children){
                    diasAula.add(obj)
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                //Se ocorrer um erro
            }
        })
    }
}
