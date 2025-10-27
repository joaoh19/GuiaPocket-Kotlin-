package com.example.guiabairro

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.example.guiabairro.databinding.ActivityDetalhesBinding
import com.example.guiabairro.models.Estabelecimento
import android.content.res.Configuration
import java.util.Locale
import android.content.SharedPreferences

class DetalhesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetalhesBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {

        sharedPreferences = getSharedPreferences("AppPreferences", MODE_PRIVATE)
        aplicarConfiguracoesSalvas()

        super.onCreate(savedInstanceState)
        binding = ActivityDetalhesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        configurarBotoes()
        carregarDadosEstabelecimento()
    }

    private fun aplicarConfiguracoesSalvas() {

        val idiomaInglesAtivo = sharedPreferences.getBoolean("idioma_ingles", false)
        val temaEscuroAtivo = sharedPreferences.getBoolean("tema_escuro", false)


        if (temaEscuroAtivo) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }


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


        atualizarTextosBotoes()
    }

    private fun atualizarTextosBotoes() {
        val temaEscuroAtivo = AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES
        val idiomaInglesAtivo = sharedPreferences.getBoolean("idioma_ingles", false)

        binding.btnTema.text = if (temaEscuroAtivo) getString(R.string.modo_claro) else getString(R.string.modo_escuro)
        binding.btnIdioma.text = if (idiomaInglesAtivo) getString(R.string.portugues) else getString(R.string.ingles)
    }

    private fun carregarDadosEstabelecimento() {
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

    private fun setupUI(estabelecimento: Estabelecimento) {

        val idiomaInglesAtivo = sharedPreferences.getBoolean("idioma_ingles", false)

        if (idiomaInglesAtivo) {

            when (estabelecimento.nome) {
                "Vivo Store - Jaraguá Shopping" -> {
                    binding.tvNome.text = "Vivo Store - Jaraguá Shopping"
                    binding.tvTipo.text = "Mobile Store"
                    binding.tvDescricao.text = "Official Vivo store offering customer service and technical support."
                    binding.tvEndereco.text = "2270 Alberto Benassi Ave – Store 128, Jardim Bandeirantes, Araraquara SP"
                    binding.tvHorario.text = "Mon to Sat: 10am – 10pm, Sun: 12pm – 8pm"
                }
                "ADGL Restaurant" -> {
                    binding.tvNome.text = "ADGL Restaurant"
                    binding.tvTipo.text = "Restaurant"
                    binding.tvDescricao.text = "Restaurant with varied dishes and cozy atmosphere."
                    binding.tvEndereco.text = "Example Street, 123, Jardim Bandeirantes, Araraquara SP"
                    binding.tvHorario.text = "Every day: 11am–11pm"
                }
                "A.M. Ferreira Restaurant / Stock Bar" -> {
                    binding.tvNome.text = "A.M. Ferreira Restaurant / Stock Bar"
                    binding.tvTipo.text = "Restaurant / Bar"
                    binding.tvDescricao.text = "Traditional restaurant and bar with local cuisine."
                    binding.tvEndereco.text = "Example Street, 456, Jardim Bandeirantes, Araraquara SP"
                    binding.tvHorario.text = "Mon to Sat: 6pm–2am"
                }
                "Passarinho Horifruti Goevry / Freio Pachez Malveti" -> {
                    binding.tvNome.text = "Passarinho Horifruti Goevry"
                    binding.tvTipo.text = "Grocery Store"
                    binding.tvDescricao.text = "Fresh fruits, vegetables and grocery products."
                    binding.tvEndereco.text = "Example Street, 789, Jardim Bandeirantes, Araraquara SP"
                    binding.tvHorario.text = "Mon to Sat: 7am–8pm, Sun: 7am–1pm"
                }
                "ANXO Motors Venios Decanado" -> {
                    binding.tvNome.text = "ANXO Motors"
                    binding.tvTipo.text = "Car Dealership"
                    binding.tvDescricao.text = "Car sales and automotive services."
                    binding.tvEndereco.text = "Example Street, 101, Jardim Bandeirantes, Araraquara SP"
                    binding.tvHorario.text = "Mon to Fri: 8am–6pm, Sat: 8am–12pm"
                }
                else -> {

                    binding.tvNome.text = estabelecimento.nome
                    binding.tvTipo.text = estabelecimento.tipo
                    binding.tvDescricao.text = estabelecimento.descricao
                    binding.tvEndereco.text = estabelecimento.endereco
                    binding.tvHorario.text = estabelecimento.horarioFuncionamento
                }
            }
        } else {

            binding.tvNome.text = estabelecimento.nome
            binding.tvTipo.text = estabelecimento.tipo
            binding.tvDescricao.text = estabelecimento.descricao
            binding.tvEndereco.text = estabelecimento.endereco
            binding.tvHorario.text = estabelecimento.horarioFuncionamento
        }

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
            ${binding.tvNome.text}
            ${binding.tvDescricao.text}
            Telefone: ${estabelecimento.telefone}
            Endereço: ${binding.tvEndereco.text}
            Horário: ${binding.tvHorario.text}
        """.trimIndent()

            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, shareText)
            }
            startActivity(Intent.createChooser(intent, "Compartilhar via"))
        }
    }

    private fun alternarTema() {
        val temaEscuroAtivo = AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES
        val novoTema = !temaEscuroAtivo


        sharedPreferences.edit().putBoolean("tema_escuro", novoTema).apply()

        if (novoTema) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            Toast.makeText(this, "Modo escuro ativado", Toast.LENGTH_SHORT).show()
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            Toast.makeText(this, "Modo claro ativado", Toast.LENGTH_SHORT).show()
        }


        binding.btnTema.text = if (novoTema) getString(R.string.modo_claro) else getString(R.string.modo_escuro)
    }

    private fun alternarIdioma() {
        val idiomaInglesAtivo = sharedPreferences.getBoolean("idioma_ingles", false)
        val novoIdioma = !idiomaInglesAtivo


        sharedPreferences.edit().putBoolean("idioma_ingles", novoIdioma).apply()

        if (novoIdioma) {
            aplicarIdioma(Locale.ENGLISH)
            Toast.makeText(this, "Idioma alterado para Inglês", Toast.LENGTH_SHORT).show()
        } else {
            aplicarIdioma(Locale("pt", "BR"))
            Toast.makeText(this, "Idioma alterado para Português", Toast.LENGTH_SHORT).show()
        }


        binding.btnIdioma.text = if (novoIdioma) getString(R.string.portugues) else getString(R.string.ingles)
    }

    private fun aplicarIdioma(locale: Locale) {
        val resources = resources
        val configuration = Configuration(resources.configuration)
        configuration.setLocale(locale)
        resources.updateConfiguration(configuration, resources.displayMetrics)


        recreate()
    }
}