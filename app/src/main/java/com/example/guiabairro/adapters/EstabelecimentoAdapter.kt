package com.example.guiabairro.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.guiabairro.R
import com.example.guiabairro.models.Estabelecimento
import java.io.File

class EstabelecimentoAdapter(
    private val estabelecimentos: List<Estabelecimento>,
    private val onItemClick: (Estabelecimento) -> Unit
) : RecyclerView.Adapter<EstabelecimentoAdapter.EstabelecimentoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EstabelecimentoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_estabelecimento, parent, false)
        return EstabelecimentoViewHolder(view)
    }

    override fun onBindViewHolder(holder: EstabelecimentoViewHolder, position: Int) {
        val estabelecimento = estabelecimentos[position]
        holder.bind(estabelecimento)

        holder.itemView.setOnClickListener {
            onItemClick(estabelecimento)
        }
    }

    override fun getItemCount() = estabelecimentos.size

    class EstabelecimentoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvNome: TextView = itemView.findViewById(R.id.tvNome)
        private val tvTipo: TextView = itemView.findViewById(R.id.tvTipo)
        private val ivEstabelecimento: ImageView = itemView.findViewById(R.id.ivEstabelecimento)

        fun bind(estabelecimento: Estabelecimento) {
            tvNome.text = estabelecimento.nome
            tvTipo.text = estabelecimento.tipo

            // CARREGAR IMAGEM - CORREÇÃO COMPLETA
            if (!estabelecimento.imagemUri.isNullOrEmpty()) {
                try {
                    // Verifica se é caminho de arquivo (imagem salva internamente)
                    if (estabelecimento.imagemUri.startsWith("/")) {
                        val imageFile = File(estabelecimento.imagemUri)
                        if (imageFile.exists()) {
                            ivEstabelecimento.setImageURI(Uri.fromFile(imageFile))
                        } else {
                            // Fallback para imagem padrão
                            ivEstabelecimento.setImageResource(android.R.drawable.ic_menu_gallery)
                        }
                    } else {
                        // É uma URI (content:// ou http://)
                        ivEstabelecimento.setImageURI(Uri.parse(estabelecimento.imagemUri))
                    }
                } catch (e: Exception) {
                    ivEstabelecimento.setImageResource(android.R.drawable.ic_menu_gallery)
                }
            } else {
                // Sem imagem
                ivEstabelecimento.setImageResource(android.R.drawable.ic_menu_gallery)
            }
        }
    }
}