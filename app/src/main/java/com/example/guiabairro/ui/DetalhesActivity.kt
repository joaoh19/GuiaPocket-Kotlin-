package com.example.guiabairro.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.guiabairro.databinding.ActivityDetalhesBinding
import com.example.guiabairro.data.database.AppDatabase
import com.example.guiabairro.models.Estabelecimento
import kotlinx.coroutines.launch
import java.io.File

class DetalhesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetalhesBinding
    private var estabelecimentoId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetalhesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        estabelecimentoId = intent.getIntExtra("ESTABELECIMENTO_ID", -1)

        if (estabelecimentoId == -1) {
            Toast.makeText(this, "Erro ao carregar", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finish()
            }
        })

        carregarEstabelecimento()
    }

    private fun carregarEstabelecimento() {
        lifecycleScope.launch {
            try {
                val estabelecimento = AppDatabase.getInstance(this@DetalhesActivity)
                    .estabelecimentoDao()
                    .getById(estabelecimentoId)

                if (estabelecimento != null) {
                    mostrarDetalhes(estabelecimento)
                    configurarBotoes(estabelecimento)
                } else {
                    Toast.makeText(this@DetalhesActivity, "Não encontrado", Toast.LENGTH_SHORT).show()
                    finish()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this@DetalhesActivity, "Erro ao carregar", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    private fun mostrarDetalhes(estabelecimento: Estabelecimento) {
        binding.tvNome.text = estabelecimento.nome
        binding.tvTipo.text = "Tipo: ${estabelecimento.tipo}"
        binding.tvDescricao.text = estabelecimento.descricao
        binding.tvEndereco.text = estabelecimento.endereco
        binding.tvHorario.text = estabelecimento.horarioFuncionamento
        binding.tvTelefone.text = estabelecimento.telefone

        // CORREÇÃO PARA IMAGENS - mesma lógica do adapter
        if (!estabelecimento.imagemUri.isNullOrEmpty()) {
            try {
                if (estabelecimento.imagemUri.startsWith("/")) {
                    val imageFile = File(estabelecimento.imagemUri)
                    if (imageFile.exists()) {
                        binding.ivFotoComercio.setImageURI(Uri.fromFile(imageFile))
                    } else {
                        binding.ivFotoComercio.setImageResource(android.R.drawable.ic_menu_gallery)
                    }
                } else {
                    val uri = Uri.parse(estabelecimento.imagemUri)
                    binding.ivFotoComercio.setImageURI(uri)
                }
            } catch (e: Exception) {
                binding.ivFotoComercio.setImageResource(android.R.drawable.ic_menu_gallery)
            }
        } else {
            binding.ivFotoComercio.setImageResource(android.R.drawable.ic_menu_gallery)
        }
    }

    private fun configurarBotoes(estabelecimento: Estabelecimento) {
        binding.btnLigar.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:${estabelecimento.telefone}")
            }
            startActivity(intent)
        }

        binding.btnSite.setOnClickListener {
            val site = estabelecimento.site
            if (!site.isNullOrEmpty()) {
                var url = site
                if (!url.startsWith("http")) {
                    url = "https://$url"
                }
                val intent = Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse(url)
                }
                startActivity(intent)
            } else {
                Toast.makeText(this, "Site não disponível", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnMapa.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("geo:0,0?q=${Uri.encode(estabelecimento.endereco)}")
            }
            startActivity(intent)
        }

        binding.btnCompartilhar.setOnClickListener {
            val texto = "${estabelecimento.nome}\n${estabelecimento.endereco}\n${estabelecimento.telefone}"
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, texto)
            }
            startActivity(Intent.createChooser(intent, "Compartilhar"))
        }
    }
}