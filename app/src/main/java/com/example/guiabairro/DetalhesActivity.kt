package com.example.guiabairro

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatDelegate
import com.example.guiabairro.databinding.ActivityDetalhesBinding
import com.example.guiabairro.models.Estabelecimento
import android.widget.Toast
import android.content.res.Configuration
import java.util.Locale
import android.content.SharedPreferences // ✅ IMPORT ADICIONADO

class DetalhesActivity : AppCompatActivity() {

    private var temaEscuroAtivo = false
    private var idiomaInglesAtivo = false
    private lateinit var binding: ActivityDetalhesBinding
    private lateinit var sharedPreferences: SharedPreferences 

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        sharedPreferences = getSharedPreferences("AppPreferences", MODE_PRIVATE)
        carregarPreferencias()

        binding = ActivityDetalhesBinding.inflate(layoutInflater)
        setContentView(binding.root)


        configurarBotoes()

        val id = intent.getIntExtra("ID", 0)
        val nome = intent.getStringExtra("NOME") ?: ""
        val tipo = intent.getStringExtra("TIPO") ?: ""
        val descricao = intent.getStringExtra("DESCRICAO") ?: ""
        val telefone = intent.getStringExtra("TELEFONE") ?: ""
        val endereco = intent.getStringExtra("ENDERECO") ?: ""
        val site = intent.getStringExtra("SITE")
        val horario = intent.getStringExtra("HORARIO") ?: ""
        val imagemResId = intent.getIntExtra("IMAGEM", android.R.drawable.ic_dialog_info)

        val estabelecimento = Estabelecimento(
            id = id,
            nome = nome,
            tipo = tipo,
            descricao = descricao,
            telefone = telefone,
            endereco = endereco,
            site = site,
            horarioFuncionamento = horario,
            imagemResId = imagemResId
        )

        setupUI(estabelecimento)
    }

    private fun carregarPreferencias() {

        idiomaInglesAtivo = sharedPreferences.getBoolean("idioma_ingles", false)
        temaEscuroAtivo = sharedPreferences.getBoolean("tema_escuro", false)


        if (temaEscuroAtivo) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        aplicarIdiomaSalvo()
    }

    private fun aplicarIdiomaSalvo() {
        val locale = if (idiomaInglesAtivo) Locale.ENGLISH else Locale("pt", "BR")
        val resources = resources
        val configuration = Configuration(resources.configuration)
        configuration.setLocale(locale)
        resources.updateConfiguration(configuration, resources.displayMetrics)
    }

    private fun configurarBotoes() {
        binding.btnVoltar.setOnClickListener {
            finish()
        }

        binding.btnTema.setOnClickListener {
            alternarTema()
        }

        binding.btnIdioma.setOnClickListener {
            alternarIdioma()
        }

        // ✅ DEFINIR TEXTOS INICIAIS DOS BOTÕES
        binding.btnTema.text = if (temaEscuroAtivo) getString(R.string.modo_claro) else getString(R.string.modo_escuro)
        binding.btnIdioma.text = if (idiomaInglesAtivo) getString(R.string.portugues) else getString(R.string.ingles)
    }

    private fun setupUI(estabelecimento: Estabelecimento) {
        binding.tvNome.text = estabelecimento.nome
        binding.tvTipo.text = estabelecimento.tipo
        binding.tvDescricao.text = estabelecimento.descricao
        binding.tvEndereco.text = estabelecimento.endereco
        binding.tvHorario.text = estabelecimento.horarioFuncionamento
        binding.ivEstabelecimento.setImageResource(estabelecimento.imagemResId)

        binding.btnLigar.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:${estabelecimento.telefone}")
            }
            startActivity(intent)
        }

        if (estabelecimento.site != null) {
            binding.btnSite.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse(estabelecimento.site)
                }
                startActivity(intent)
            }
        } else {
            binding.btnSite.visibility = View.GONE
        }

        binding.btnCompartilhar.setOnClickListener {
            val shareText = """
                ${estabelecimento.nome}
                ${estabelecimento.descricao}
                Telefone: ${estabelecimento.telefone}
                Endereço: ${estabelecimento.endereco}
                Horário: ${estabelecimento.horarioFuncionamento}
            """.trimIndent()

            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, shareText)
            }
            startActivity(Intent.createChooser(intent, "Compartilhar via"))
        }
    }

    private fun alternarTema() {
        temaEscuroAtivo = !temaEscuroAtivo


        sharedPreferences.edit().putBoolean("tema_escuro", temaEscuroAtivo).apply()

        if (temaEscuroAtivo) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            binding.btnTema.text = getString(R.string.modo_claro)
            Toast.makeText(this, "Modo escuro ativado", Toast.LENGTH_SHORT).show()
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            binding.btnTema.text = getString(R.string.modo_escuro)
            Toast.makeText(this, "Modo claro ativado", Toast.LENGTH_SHORT).show()
        }
    }

    private fun alternarIdioma() {
        idiomaInglesAtivo = !idiomaInglesAtivo


        sharedPreferences.edit().putBoolean("idioma_ingles", idiomaInglesAtivo).apply()

        if (idiomaInglesAtivo) {
            alterarIdiomaApp(Locale.ENGLISH)
            binding.btnIdioma.text = getString(R.string.portugues)
            Toast.makeText(this, "Idioma alterado para Inglês", Toast.LENGTH_SHORT).show()
        } else {
            alterarIdiomaApp(Locale("pt", "BR"))
            binding.btnIdioma.text = getString(R.string.ingles)
            Toast.makeText(this, "Idioma alterado para Português", Toast.LENGTH_SHORT).show()
        }
    }

    private fun alterarIdiomaApp(locale: Locale) {
        val resources = resources
        val configuration = Configuration(resources.configuration)
        configuration.setLocale(locale)
        resources.updateConfiguration(configuration, resources.displayMetrics)
        recreate()
    }
}