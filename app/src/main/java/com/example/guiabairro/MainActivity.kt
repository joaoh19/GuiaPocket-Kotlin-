package com.example.guiabairro

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.guiabairro.adapters.EstabelecimentoAdapter
import com.example.guiabairro.databinding.ActivityMainBinding
import com.example.guiabairro.models.Estabelecimento

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var estabelecimentoAdapter: EstabelecimentoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListView()
    }

    private fun setupListView() {
        val estabelecimentos = listOf(
            Estabelecimento(
                id = 1,
                nome = getString(R.string.adgl_nome),
                tipo = getString(R.string.adgl_tipo),
                descricao = getString(R.string.adgl_descricao),
                telefone = "(16) 3301-1996",
                endereco = getString(R.string.adgl_endereco),
                site = "https://comida.net.br/sobre/adgl---restaurante-ltda-araraquara-sp-31786958",
                horarioFuncionamento = getString(R.string.adgl_horario),
                imagemResId = R.drawable.adgl_restaurante
            ),
            Estabelecimento(
                id = 2,
                nome = getString(R.string.amferreira_nome),
                tipo = getString(R.string.amferreira_tipo),
                descricao = getString(R.string.amferreira_descricao),
                telefone = "(16) 3303-4216",
                endereco = getString(R.string.amferreira_endereco),
                site = "https://comida.net.br/sobre/am-ferreira-ltda-araraquara-sp-33491394",
                horarioFuncionamento = getString(R.string.amferreira_horario),
                imagemResId = R.drawable.alpine_restaurant
            ),
            Estabelecimento(
                id = 3,
                nome = getString(R.string.vivo_nome),
                tipo = getString(R.string.vivo_tipo),
                descricao = getString(R.string.vivo_descricao),
                telefone = "(16) 99621-9668",
                endereco = getString(R.string.vivo_endereco),
                site = "https://lojas.vivo.com.br/loja-vivo-jardim-bandeirantes-araraquara-sp",
                horarioFuncionamento = getString(R.string.vivo_horario),
                imagemResId = R.drawable.vivo
            ),
            Estabelecimento(
                id = 4,
                nome = getString(R.string.passarinho_nome),
                tipo = getString(R.string.passarinho_tipo),
                descricao = getString(R.string.passarinho_descricao),
                telefone = "(16) 3331-6677",
                endereco = getString(R.string.passarinho_endereco),
                site = "https://passarinhohortifruti.com.br",
                horarioFuncionamento = getString(R.string.passarinho_horario),
                imagemResId = R.drawable.passarinho
            ),
            Estabelecimento(
                id = 5,
                nome = getString(R.string.aiko_nome),
                tipo = getString(R.string.aiko_tipo),
                descricao = getString(R.string.aiko_descricao),
                telefone = "(16) 3333-8899",
                endereco = getString(R.string.aiko_endereco),
                site = "https://empresadois.com.br/cnpj/aiko-12398481000114",
                horarioFuncionamento = getString(R.string.aiko_horario),
                imagemResId = R.drawable.aiko
            )
        )

        estabelecimentoAdapter = EstabelecimentoAdapter(this, estabelecimentos)
        binding.listViewEstabelecimentos.adapter = estabelecimentoAdapter

        binding.listViewEstabelecimentos.setOnItemClickListener { _, _, position, _ ->
            val estabelecimento = estabelecimentos[position]
            val intent = Intent(this, DetalhesActivity::class.java).apply {
                putExtra("ID", estabelecimento.id)
                putExtra("NOME", estabelecimento.nome)
                putExtra("TIPO", estabelecimento.tipo)
                putExtra("DESCRICAO", estabelecimento.descricao)
                putExtra("TELEFONE", estabelecimento.telefone)
                putExtra("ENDERECO", estabelecimento.endereco)
                putExtra("SITE", estabelecimento.site)
                putExtra("HORARIO", estabelecimento.horarioFuncionamento)
                putExtra("IMAGEM", estabelecimento.imagemResId)
            }
            startActivity(intent)
        }
    }
}
