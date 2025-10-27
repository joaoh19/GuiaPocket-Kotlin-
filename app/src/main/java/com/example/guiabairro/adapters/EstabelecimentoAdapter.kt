package com.example.guiabairro.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.guiabairro.databinding.ItemEstabelecimentoBinding
import com.example.guiabairro.models.Estabelecimento

class EstabelecimentoAdapter(
    private val context: Context,
    private val estabelecimentos: List<Estabelecimento>
) : BaseAdapter() {

    override fun getCount(): Int = estabelecimentos.size
    override fun getItem(position: Int): Estabelecimento = estabelecimentos[position]
    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding: ItemEstabelecimentoBinding

        if (convertView == null) {
            binding = ItemEstabelecimentoBinding.inflate(LayoutInflater.from(context), parent, false)
        } else {
            binding = ItemEstabelecimentoBinding.bind(convertView)
        }

        val estabelecimento = estabelecimentos[position]

        binding.tvNome.text = estabelecimento.nome
        binding.tvTipo.text = estabelecimento.tipo
        binding.ivEstabelecimento.setImageResource(estabelecimento.imagemResId)

        return binding.root
    }
}