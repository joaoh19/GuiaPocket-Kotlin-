package com.example.guiabairro.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.guiabairro.data.database.AppDatabase
import com.example.guiabairro.databinding.ActivityCadastroBinding
import com.example.guiabairro.models.Estabelecimento
import kotlinx.coroutines.launch

class CadastroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCadastroBinding
    private var fotoUri: String = ""

    private val pickImageLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            try {
                val takeFlags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                contentResolver.takePersistableUriPermission(it, takeFlags)

                fotoUri = it.toString()
                binding.imgFoto.setImageURI(it)
                Toast.makeText(this, "Imagem selecionada", Toast.LENGTH_SHORT).show()
            } catch (e: SecurityException) {
                Toast.makeText(this, "Permissão negada para imagem", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCadastroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finish()
            }
        })

        configurarBotoes()
    }

    private fun configurarBotoes() {
        binding.btnSelecionarImagem.setOnClickListener {
            pickImageLauncher.launch("image/*")
        }

        binding.imgFoto.setOnClickListener {
            pickImageLauncher.launch("image/*")
        }

        binding.btnSalvar.setOnClickListener {
            salvarEstabelecimento()
        }

        binding.btnCancelar.setOnClickListener {
            finish()
        }
    }

    private fun salvarEstabelecimento() {
        val nome = binding.edtNome.text.toString().trim()
        val descricao = binding.edtDescricao.text.toString().trim()
        val telefone = binding.edtTelefone.text.toString().trim()
        val endereco = binding.edtEndereco.text.toString().trim()
        val horario = binding.edtHorario.text.toString().trim()

        if (nome.isEmpty() || descricao.isEmpty() || telefone.isEmpty() ||
            endereco.isEmpty() || horario.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
            return
        }

        val estabelecimento = Estabelecimento(
            nome = nome,
            tipo = "Comércio",
            descricao = descricao,
            telefone = telefone ,
            endereco = endereco,
            horarioFuncionamento = horario,
            imagemUri = fotoUri
        )

        lifecycleScope.launch {
            try {
                AppDatabase.getInstance(this@CadastroActivity)
                    .estabelecimentoDao()
                    .insert(estabelecimento)

                Toast.makeText(this@CadastroActivity, "Salvo com sucesso!", Toast.LENGTH_SHORT).show()
                finish()
            } catch (e: Exception) {
                Toast.makeText(this@CadastroActivity, "Erro ao salvar", Toast.LENGTH_SHORT).show()
            }
        }
    }
}