package com.example.guiabairro.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "estabelecimentos")
data class Estabelecimento(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nome: String,
    val tipo: String,
    val descricao: String,
    val telefone: String,
    val endereco: String,
    val site: String? = null,
    val horarioFuncionamento: String,
    val imagemUri: String = ""
)