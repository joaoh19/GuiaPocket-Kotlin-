package com.example.guiabairro.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.guiabairro.R
import com.example.guiabairro.adapters.EstabelecimentoAdapter
import com.example.guiabairro.data.database.AppDatabase
import com.example.guiabairro.models.Estabelecimento
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var btnAddEstabelecimento: Button
    private lateinit var tvTituloBairro: TextView
    private lateinit var adapter: EstabelecimentoAdapter
    private val estabelecimentos = mutableListOf<Estabelecimento>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvTituloBairro = findViewById(R.id.tvTituloBairro)
        recyclerView = findViewById(R.id.recyclerViewEstabelecimentos)
        btnAddEstabelecimento = findViewById(R.id.btnAddEstabelecimento)

        tvTituloBairro.text = "Guia do Bairro"


        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = EstabelecimentoAdapter(estabelecimentos) { estabelecimento ->
            val intent = Intent(this, DetalhesActivity::class.java).apply {
                putExtra("ESTABELECIMENTO_ID", estabelecimento.id)
            }
            startActivity(intent)
        }
        recyclerView.adapter = adapter

        // Botão adicionar
        btnAddEstabelecimento.setOnClickListener {
            startActivity(Intent(this, CadastroActivity::class.java))
        }

        carregarEstabelecimentos()
    }

    private fun carregarEstabelecimentos() {
        lifecycleScope.launch {
            try {
                val db = AppDatabase.getInstance(this@MainActivity)
                val listaFlow = db.estabelecimentoDao().getAll()

                listaFlow.collect { itens ->
                    estabelecimentos.clear()
                    estabelecimentos.addAll(itens)
                    adapter.notifyDataSetChanged()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        carregarEstabelecimentos()
    }
}