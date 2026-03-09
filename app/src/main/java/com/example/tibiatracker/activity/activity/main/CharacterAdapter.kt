package com.example.tibiatracker.activity.activity.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tibiatracker.R
import pl.droidsonroids.gif.GifImageView

class CharacterAdapter(
    private val lista: List<String>?
) : RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>() {

    class CharacterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val icone: GifImageView = itemView.findViewById(R.id.icone_char)
        val nome: TextView = itemView.findViewById(R.id.tv_nome_char)
        val server: TextView = itemView.findViewById(R.id.tv_server_name)
        val level: TextView = itemView.findViewById(R.id.tv_lvl_char)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.char_unidade, parent, false)

        return CharacterViewHolder(view)
    }

    override fun getItemCount(): Int {
        return lista!!.size
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {

        val character = lista!![position]

        holder.nome.text = character
        holder.server.visibility = View.GONE
        holder.level.visibility = View.GONE
//        holder.icone.setImageResource(character.icone)

    }
}