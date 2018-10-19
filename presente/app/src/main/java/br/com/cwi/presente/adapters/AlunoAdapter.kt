package br.com.cwi.presente.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import br.com.cwi.presente.R
import br.com.cwi.presente.models.Aluno
import kotlinx.android.synthetic.main.view_aluno.view.*

class AlunoAdapter(val items: MutableList<Aluno>) : RecyclerView.Adapter<AlunoAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        LayoutInflater.from(parent.context).run {
            return ViewHolder(itemView = inflate(
                    R.layout.view_aluno,
                    parent,
                    false))
        }
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        items[position].run {
            holder.alunoNome.text = nome
            holder.alunoEmail.text = email
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val alunoNome: TextView = itemView.alunoNome
        val alunoEmail: TextView = itemView.alunoEmail
    }
}