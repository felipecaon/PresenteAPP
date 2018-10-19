package br.com.cwi.presente.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import br.com.cwi.presente.R
import br.com.cwi.presente.models.Aluno
import br.com.cwi.presente.models.Aula
import br.com.cwi.presente.models.Chamada
import br.com.cwi.presente.utils.UserHolder
import com.google.firebase.database.DataSnapshot
import kotlinx.android.synthetic.main.view_aluno.view.*
import kotlinx.android.synthetic.main.view_datapresenca.view.*

class PresencaAdapter(val items: MutableList<DataSnapshot> , val clickGoToLista: (dataSnapshot: DataSnapshot) -> Unit) : RecyclerView.Adapter<PresencaAdapter.ViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        LayoutInflater.from(parent.context).run {
            return ViewHolder(itemView = inflate(
                    R.layout.view_datapresenca,
                    parent,
                    false))
        }
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        items[position].run {
            holder.data.text = key.toString()


            holder.button.setOnClickListener {
                clickGoToLista(this)
            }
        }


    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val data: TextView = itemView.textdata
        val button: Button = itemView.gotolista
    }
}