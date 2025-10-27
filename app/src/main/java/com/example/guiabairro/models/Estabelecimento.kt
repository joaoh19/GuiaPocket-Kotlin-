package com.example.guiabairro.models

data class Estabelecimento(
    val id: Int,
    val nome: String,
    val tipo: String,
    val descricao: String,
    val telefone: String,
    val endereco: String,
    val site: String? = null,
    val horarioFuncionamento: String,
    val imagemResId: Int
)