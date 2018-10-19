package br.com.cwi.presente.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import br.com.cwi.presente.R
import br.com.cwi.presente.models.Aula
import kotlinx.android.synthetic.main.view_aulas.view.*

class AulaAdapter(val items: ArrayList<Aula>, val clickGoToAula: (aula: Aula) -> Unit, val clickTeste: (aula: Aula) -> Unit, val clickGoToRegistro: (aula: Aula) -> Unit) : RecyclerView.Adapter<AulaAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        LayoutInflater.from(parent.context).run {
            return ViewHolder(itemView = inflate(
                    R.layout.view_aulas,
                    parent,
                    false))
        }
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        items[position].run {
            holder.nomeAula.text = nome

            holder.adicionarAluno.setOnClickListener {
                clickGoToAula(this)
            }

            holder.criarChamada.setOnClickListener {
                clickTeste(this)
            }

            holder.registroPresenca.setOnClickListener {
                clickGoToRegistro(this)
            }
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nomeAula: TextView = itemView.nomeAula
        val adicionarAluno: ImageButton = itemView.btAlunos
        val criarChamada: ImageButton = itemView.imageButton
        val registroPresenca: ImageButton = itemView.btRegistro
    }
}